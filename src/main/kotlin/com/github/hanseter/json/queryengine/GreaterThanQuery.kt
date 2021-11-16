package com.github.hanseter.json.queryengine

class GreaterThanQuery (private val attributePath: AttributePath,
	private val lowerBound: String
) : Query {
	override fun matches(data: QueryableData): Boolean {
		val value = attributePath.getValue(data.data) ?: return false
		return value >= lowerBound
	}
	
	class GreaterThanQueryBuilder : QueryBuilder<GreaterThanQuery> {
		private var attributePath: AttributePath? = null
		private var lowerBound: String = ""

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

		override fun isComplete(): Boolean = attributePath != null

		override fun build(): GreaterThanQuery? {
			val attributePath = this.attributePath ?: return null
			return GreaterThanQuery(attributePath, lowerBound)
		}
	}
}