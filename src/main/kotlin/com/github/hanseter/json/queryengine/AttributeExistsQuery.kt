package com.github.hanseter.json.queryengine

import org.json.JSONObject

class AttributeExistsQuery(private val attributePath: AttributePath) : Query {

	override fun matches(data: QuerieableData): Boolean = attributePath.getValue(data.data) != null

	public class AttributeExistsQueryBuilder : QueryBuilder<AttributeExistsQuery> {
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
			val attributePath = this.attributePath
			if (attributePath == null) {
				return null
			}
			return AttributeExistsQuery(attributePath)
		}
	}
}