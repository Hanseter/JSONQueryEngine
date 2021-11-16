package com.github.hanseter.json.queryengine

class BetweenQuery(
    private val attributePath: AttributePath,
    private val lowerBound: String,
    private val upperBound: String
) : Query {
    override fun matches(data: QueryableData): Boolean {
        val value = attributePath.getValue(data.data) ?: return false
        return value in lowerBound..upperBound
    }

    class BetweenQueryBuilder : QueryBuilder<BetweenQuery> {
        private var attributePath: AttributePath? = null
        private var lowerBound: String = ""
        private var upperBound: String = ""

        fun withAttributePath(path: String) {
            if (path.isEmpty()) {
                this.attributePath = null
            } else {
                this.attributePath = AttributePath(path)
            }
        }

        fun withLowerBound(lowerBound: String) {
            this.lowerBound = lowerBound
        }

        fun withUpperBound(upperBound: String) {
            this.upperBound = upperBound
        }

        override fun isComplete(): Boolean = attributePath != null

        override fun build(): BetweenQuery? {
            val attributePath = this.attributePath ?: return null
            return BetweenQuery(attributePath, lowerBound, upperBound)
        }
    }
}