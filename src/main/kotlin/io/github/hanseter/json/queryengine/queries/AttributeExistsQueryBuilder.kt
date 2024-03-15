package io.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query


class AttributeExistsQueryBuilder : AttributeQueryBuilder<AttributeExistsQueryBuilder>() {

    init {
        queryCreator = { path ->
            Query { data -> path.getValue(data.data) != null }
        }
    }
}