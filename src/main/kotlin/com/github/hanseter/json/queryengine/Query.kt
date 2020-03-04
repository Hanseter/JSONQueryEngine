package com.github.hanseter.json.queryengine

import org.json.JSONObject

interface Query {
	fun matches(data: QuerieableData) : Boolean
}