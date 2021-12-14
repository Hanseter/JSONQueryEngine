package com.github.hanseter.json.queryengine

import org.json.JSONObject

class AttributePath(stringRepresentation: String) {
    private val path: List<String> = stringRepresentation.split(".")

    fun getValue(data: JSONObject): Any? =
        getSubSection(data)?.opt(getAttributeName())

    fun getString(data: JSONObject): String? =
        getSubSection(data)?.optString(getAttributeName(), null)

    fun getNumber(data: JSONObject): Number? =
        getSubSection(data)?.optNumber(getAttributeName(), null)

    fun getBoolean(data: JSONObject): Boolean? =
        getSubSection(data)?.optBooleanNull(getAttributeName())

    fun getSubSection(data: JSONObject): JSONObject? = getSubSection(data, path.dropLast(1))

    private fun getSubSection(data: JSONObject, path: List<String>): JSONObject? =
        path.fold(data as JSONObject?) { acc, it -> acc?.optJSONObject(it) }

    fun getAttributeName(): String = path.last()

    fun findPossibleAttributePaths(fieldNames: JSONObject): Sequence<String> =
        getSubSection(fieldNames)?.keySet()?.asSequence()
            ?.filter { it.startsWith(getAttributeName()) }?.distinct()
            ?.map { (path.dropLast(1) + it).joinToString(".") } ?: emptySequence()
//		objects.asSequence().map { getSubSection(it) }.filterNotNull().flatMap { it.keySet().asSequence() }
//			.filter { it.startsWith(getAttributeName()) }
//			.distinct().map { (path.dropLast(1) + it).joinToString(".") }

    override fun toString(): String = path.joinToString(".")

    private fun JSONObject.optBooleanNull(key: String): Boolean? =
        when (val value = opt(key)) {
            JSONObject.NULL -> null
            is Boolean -> value
            else -> try {
                this.getBoolean(key)
            } catch (e: Exception) {
                null
            }
        }
}