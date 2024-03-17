package io.github.hanseter.json.queryengine

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class QueryExecutor<K : QueryableData, R : QueryExecutionContext<K>>(
    private val query: Query,
    private val executionContext: R,
    private val executor: ExecutorService,
    private val tasksInParallel: Int
) {
    private var futures: List<Future<*>> = emptyList()

    @Volatile
    private var finishedTasks = 0

    @Volatile
    private var paused = false

    fun pause() {
        synchronized(this) {
            paused = true
            futures.forEach { it.cancel(true) }
            futures = emptyList()
        }
    }

    fun resume() {
        synchronized(this) {
            paused = false
            finishedTasks = 0
            futures = (1..tasksInParallel).map {
                executor.submit { filterElements() }
            }
        }
    }

    private fun filterElements() {
        while (!Thread.currentThread().isInterrupted && !paused) {
            try {
                val element = synchronized(this) { executionContext.getNextElement() }
                if (element == null) {
                    taskFinished()
                    break;
                }
                if (query.matches(element)) {
                    executionContext.addMatch(element)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun taskFinished() {
        synchronized(this) {
            finishedTasks++
            if (finishedTasks >= tasksInParallel) {
                executionContext.executionFinished()
            }
        }
    }
}