package com.mikewerzen.automation.assistant.core.nlp.container.entity

enum class EntityType private constructor(private val tag: String)
{
	Unknown("UNKNOWN"),
	Person("PERSON"),
	Location("LOCATION"),
	Organization("ORGANIZATION"),
	Event("EVENT"),
	Art("WORK_OF_ART"),
	Product("CONSUMER_GOOD"),
	Other("OTHER");


	companion object
	{

		fun getTypeForTag(tag: String): EntityType
		{
			for (existingEntityType in EntityType.values())
			{
				if (existingEntityType.tag == tag.toUpperCase())
					return existingEntityType
			}

			throw RuntimeException("Could not determine matching EntityType for tag: " + tag)
		}
	}
}
