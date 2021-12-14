package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query

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

    fun withValue(value: Number): AttributeEqualsQueryBuilder {
        this.queryCreator = { path ->
            Query { data -> path.getNumber(data.data) == value }
        }
        return this
    }
}