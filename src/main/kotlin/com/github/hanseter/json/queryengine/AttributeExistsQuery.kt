package com.github.hanseter.json.queryengine

class AttributeExistsQuery(private val attributePath: AttributePath) : Query {

    override fun matches(data: QueryableData): Boolean = attributePath.getValue(data.data) != null

    class AttributeExistsQueryBuilder : QueryBuilder<AttributeExistsQuery> {
        private var attributePath: AttributePath? = null

        fun withAttributePath(path: String) {
            if (path.isEmpty()) {
                this.attributePath = null
            } else {
                this.attributePath = AttributePath(path)
            }
        }

        override fun isComplete(): Boolean = attributePath != null

        override fun build(): AttributeExistsQuery? {
            val attributePath = this.attributePath ?: return null
            return AttributeExistsQuery(attributePath)
        }
    }
}