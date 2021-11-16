package com.github.hanseter.json.queryengine

class AttributeEqualsQuery(
    private val attributePath: AttributePath,
    private val expectedValue: String
) : Query {

    override fun matches(data: QueryableData): Boolean =
        attributePath.getValue(data.data) == expectedValue

    class AttributeEqualsQueryBuilder : QueryBuilder<AttributeEqualsQuery> {
        private var attributePath: AttributePath? = null
        private var value: String = ""

        fun withAttributePath(path: String) {
            if (path.isEmpty()) {
                this.attributePath = null
            } else {
                this.attributePath = AttributePath(path)
            }
        }

        fun withValue(value: String) {
            this.value = value
        }

        override fun isComplete(): Boolean = attributePath != null

        override fun build(): AttributeEqualsQuery? {
            val attributePath = this.attributePath ?: return null
            val value = this.value
            return AttributeEqualsQuery(attributePath, value)
        }
    }
}