package io.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query

class GreaterThanQueryBuilder : AttributeQueryBuilder<GreaterThanQueryBuilder>() {

    fun withLowerBound(lowerBound: String): GreaterThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getString(data.data) ?: return@Query false
                value >= lowerBound
            }
        }
        return this
    }

    fun withLowerBound(lowerBound: Double): GreaterThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getNumber(data.data) ?: return@Query false
                value.toDouble() >= lowerBound
            }
        }
        return this
    }

    fun withLowerBound(lowerBound: Long): GreaterThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getNumber(data.data) ?: return@Query false
                value.toLong() >= lowerBound
            }
        }
        return this
    }
}