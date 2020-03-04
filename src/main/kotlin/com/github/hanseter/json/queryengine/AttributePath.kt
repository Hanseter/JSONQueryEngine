package com.github.hanseter.json.queryengine

import org.json.JSONObject

class AttributePath(stringRepresentation: String) {
	private val path: List<String> = stringRepresentation.split(".")

	fun getValue(data: JSONObject): String? = getSubSection(data)?.optString(getAttributeName(), null)

	fun getSubSection(data: JSONObject): JSONObject? = getSubSection(data, path.dropLast(1))

	private fun getSubSection(data: JSONObject, path: List<String>): JSONObject? = when {
		path.isEmpty() -> data
		else -> data.optJSONObject(path.first())?.let { getSubSection(it, path.drop(1)) }
	}

	fun getAttributeName(): String = path.last()

	fun findPossibleAttributePaths(fieldNames: JSONObject): Sequence<String> =
		getSubSection(fieldNames)?.keySet()?.asSequence()?.filter { it.startsWith(getAttributeName()) }?.distinct()
			?.map { (path.dropLast(1) + it).joinToString(".") } ?: emptySequence()
//		objects.asSequence().map { getSubSection(it) }.filterNotNull().flatMap { it.keySet().asSequence() }
//			.filter { it.startsWith(getAttributeName()) }
//			.distinct().map { (path.dropLast(1) + it).joinToString(".") }

	override fun toString(): String = path.joinToString(".")
} 