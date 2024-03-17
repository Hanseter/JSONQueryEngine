package io.github.hanseter.json.queryengine.queries

import io.github.hanseter.json.queryengine.Query
import java.util.regex.Pattern

class AttributeMatchesRegexQueryBuilder :
    AttributeQueryBuilder<AttributeMatchesRegexQueryBuilder>() {

    fun withRegex(value: Regex): AttributeMatchesRegexQueryBuilder {
        this.queryCreator = { path ->
            Query { data -> path.getString(data.data)?.matches(value) == true }
        }
        return this
    }

    fun withRegex(value: Pattern): AttributeMatchesRegexQueryBuilder = withRegex(value.toRegex())

}