package com.mikewerzen.automation.assistant.core.nlp.container.entity

import java.util.HashMap

class Metadata
{

	private val data = HashMap<MetadataKey, String>()


	val metadataKeys: Set<MetadataKey>
		get() = data.keys

	fun getMetadataValue(key: MetadataKey): String?
	{
		return data[key]
	}

	fun addMetadata(key: MetadataKey, value: String): Metadata
	{
		data.put(key, value)
		return this
	}

	override fun toString(): String
	{
		return "Metadata [data=$data]"
	}

}
