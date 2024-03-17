package io.github.hanseter.json.queryengine.queries

import io.github.hanseter.json.queryengine.Query

class AttributeEqualsQueryBuilder : AttributeQueryBuilder<AttributeEqualsQueryBuilder>() {

    fun withValue(value: String): AttributeEqualsQueryBuilder {
        this.queryCreator = { path ->
            Query { data -> path.getString(data.data) == value }
        }
        return this
    }

    fun withValue(value: Boolean): AttributeEqualsQueryBuilder {
        this.queryCreator = { path ->
            Query { data -> path.getBoolean(data.data) == value }
        }
        return this
    }

    fun withValue(value: Double): AttributeEqualsQueryBuilder {
        this.queryCreator = { path ->
            Query { data -> path.getNumber(data.data)?.toDouble() == value }
        }
        return this
    }

    fun withValue(value: Long): AttributeEqualsQueryBuilder {
        this.queryCreator = { path ->
            Query { data -> path.getNumber(data.data)?.toLong() == value }
        }
        return this
    }
}