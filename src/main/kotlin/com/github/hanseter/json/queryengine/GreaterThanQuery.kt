package com.github.hanseter.json.queryengine

class GreaterThanQuery (private val attributePath: AttributePath,
	private val lowerBound: String
) : Query {
	override fun matches(data: QuerieableData): Boolean {
		val value = attributePath.getValue(data.data)
		if (value == null) return false
		if (lowerBound.compareTo(value) >= 0) return false
		return true
	}
	
	public class GreaterThanQueryBuilder : QueryBuilder<GreaterThanQuery> {
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
			val attributePath = this.attributePath
			if (attributePath == null) {
				return null
			}
			return GreaterThanQuery(attributePath, lowerBound)
		}
	}
}