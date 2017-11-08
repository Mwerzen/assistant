package com.mikewerzen.automation.assistant.core.nlp.container.entity

class Entity(name: String, type: EntityType, metadata: Metadata, salience: Salience,
			 entityMentions: List<EntityMention>)
{
	var name: String? = null
		internal set

	var type: EntityType? = null
		internal set

	var metadata = Metadata()
		internal set

	var salience: Salience? = null
		internal set

	var entityMentions: List<EntityMention>? = null
		internal set

	init
	{
		this.name = name
		this.type = type
		this.metadata = metadata
		this.salience = salience
		this.entityMentions = entityMentions
	}

	override fun toString(): String
	{
		return "Entity:\n" +
				"   Name=" + name + "\n" +
				"   Type=" + type + "\n" +
				"   Metadata=" + metadata + "\n" +
				"   Salience=" + salience + "\n" +
				"   EntityMentions=" + entityMentions
	}

}
