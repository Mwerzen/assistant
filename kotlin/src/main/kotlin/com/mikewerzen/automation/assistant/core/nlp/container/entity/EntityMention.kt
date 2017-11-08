package com.mikewerzen.automation.assistant.core.nlp.container.entity

class EntityMention(textSpan: TextSpan)
{
	var textSpan: TextSpan? = null
		internal set

	init
	{
		this.textSpan = textSpan
	}

	override fun toString(): String
	{
		return "EntityMention [textSpan=$textSpan]"
	}


}
