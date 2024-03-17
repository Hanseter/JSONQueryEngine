package io.github.hanseter.json.queryengine

import io.github.hanseter.json.queryengine.queries.BetweenQueryBuilder
import org.json.JSONObject
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class QueryExecutorTest {

    private val executor = Executors.newFixedThreadPool(4)

    @Test
    @Disabled("Manual testing for now")
    fun parallel() {
        val query = BetweenQueryBuilder()
            .withAttributePath("long")
            .withBounds(10L, 20L)
            .build()!!
        val data = (1..100).map {
            QuerieableDataStub(it, JSONObject().put("long", 15))
        }

        repeat(50) {
            QueryExecutionContextForTests(data).also {
                QueryExecutor(query, it, executor, 4).resume()
                it.latch.await()
                println("Done with $it")
            }
        }
    }

    private class QueryExecutionContextForTests(items: List<QuerieableDataStub>) :
        QueryExecutionContext<QuerieableDataStub> {
        private val iter = items.iterator()
        private val result = HashSet<QuerieableDataStub>()
        val latch = CountDownLatch(1)
        override fun getNextElement(): QuerieableDataStub? =
            if (iter.hasNext()) iter.next() else null

        override fun executionFinished() {
            if (latch.count == 0L) {
                println("error")
            }
            latch.countDown()
            result.forEach {
                Thread.sleep(1)
            }
        }

        override fun addMatch(match: QuerieableDataStub) {
            result += match
        }

    }
}