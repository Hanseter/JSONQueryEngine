package io.github.hanseter.json.queryengine

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class QueryExecutor<K : QueryableData, R : QueryExecutionContext<K>>(
	private val query: Query,
	val executionContext: R,
	private val executor: ExecutorService,
	val tasksInParallel: Int
) {
	private var futures: List<Future<*>> = emptyList()
	@Volatile
	private var finishedTasks = 0
	@Volatile
	private var paused = false

	fun pause() {
		synchronized(this) {
			futures.forEach { it.cancel(true) }
			futures = emptyList()
			paused = true
		}
	}

	fun resume() {
		synchronized(this) {
			paused = false
			finishedTasks = 0
			futures = (0..tasksInParallel).map {
				executor.submit {
					while (!Thread.currentThread().isInterrupted && !paused) {
						try {
						    val element = executionContext.getNextElement()
						            if (element == null) {
						                taskFinished()
						                break;
						            } else {
						                if (query.matches(element)) {
						                    executionContext.addMatch(element)
						                }
						            }
						} catch(e: Exception) {
							e.printStackTrace()
						}

					}
				}
			}
		}
	}

	private fun taskFinished() {
		synchronized(this) {
			finishedTasks++
			if (finishedTasks >= tasksInParallel && !paused) {
			    executionContext.executionFinished()
			}
		}
	}
}