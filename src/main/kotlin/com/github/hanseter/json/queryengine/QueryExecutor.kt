package com.github.hanseter.json.queryengine

import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.ExecutorService
import kotlin.jvm.Volatile

class QueryExecutor<K:QuerieableData, R : QueryExecutionContext<K>>(
	private val query: Query,
	val executionContext: R,
	private val executor: ExecutorService,
	val tasksInParallel: Int
) {
	var futures: List<Future<*>> = emptyList()
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
			futures = (0..tasksInParallel).map {
				executor.submit {
					while (!Thread.currentThread().isInterrupted() && !paused) {
						val element = executionContext.getNextElement()
						if (element == null) {
							pause()
							executionContext.executionFinished()
						} else {
							if (query.matches(element)) { 
								executionContext.addMatch(element)
							}
						}

					}
				}
			}
		}
	}
}