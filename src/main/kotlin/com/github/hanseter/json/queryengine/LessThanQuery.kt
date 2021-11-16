package com.github.hanseter.json.queryengine

class LessThanQuery(
	private val attributePath: AttributePath,
	private val upperBound: String
) : Query {
	override fun matches(data: QueryableData): Boolean {
		val value = attributePath.getValue(data.data) ?: return false
		return value <= upperBound
	}
	
	class LessThanQueryBuilder : QueryBuilder<LessThanQuery> {
		private var attributePath: AttributePath? = null
		private var upperBound: String = ""

		fun withAttributePath(path: String) {
			if (path.isEmpty()) {
				this.attributePath = null
			} else {
				this.attributePath = AttributePath(path)
			}
		}

		fun withUpperBound(upperBound: String) {
			this.upperBound = upperBound
		}

		override fun isComplete(): Boolean = attributePath != null

		override fun build(): LessThanQuery? {
			val attributePath = this.attributePath ?: return null
			return LessThanQuery(attributePath, upperBound)
		}
	}
}