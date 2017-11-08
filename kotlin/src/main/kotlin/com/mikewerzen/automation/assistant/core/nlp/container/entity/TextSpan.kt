package com.mikewerzen.automation.assistant.core.nlp.container.entity

class TextSpan(content: String, offset: Int)
{
	var content: String? = null
		internal set
	var offset: Int = 0
		internal set

	init
	{
		this.content = content
		this.offset = offset
	}

	override fun toString(): String
	{
		return "TextSpan [content=$content, offset=$offset]"
	}
}
