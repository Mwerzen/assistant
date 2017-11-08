package com.mikewerzen.automation.assistant.core.nlp.container.entity

enum class MetadataKey private constructor(private val tag: String)
{
	Wikipedia("WIKIPEDIA_URL");


	companion object
	{

		fun getTypeForTag(tag: String): MetadataKey
		{
			for (existingMetadataTag in MetadataKey.values())
			{
				if (existingMetadataTag.tag == tag.toUpperCase())
					return existingMetadataTag
			}

			throw RuntimeException("Could not determine matching MetadataKey for tag: " + tag)
		}
	}

}
