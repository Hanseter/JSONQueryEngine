package com.github.hanseter.json.queryengine

class BetweenQuery(
	private val attributePath: AttributePath,
	private val lowerBound: String,
	private val upperBound: String
) : Query {
	override fun matches(data: QuerieableData): Boolean {
		val value = attributePath.getValue(data.data)
		if (value == null) return false
		if (lowerBound.compareTo(value) >= 0) return false
		if (upperBound.compareTo(value) <= 0) return false
		return true
	}

	public class BetweenQueryBuilder : QueryBuilder<BetweenQuery> {
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
			val attributePath = this.attributePath
			if (attributePath == null) {
				return null
			}
			return BetweenQuery(attributePath, lowerBound, upperBound)
		}
	}
}