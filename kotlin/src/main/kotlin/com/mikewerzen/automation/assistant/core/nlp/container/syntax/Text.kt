package com.mikewerzen.automation.assistant.core.nlp.container.syntax

class Text(word: String, offset: Int)
{
	var word: String? = null
		internal set
	var offset: Int = 0
		internal set

	init
	{
		this.word = word
		this.offset = offset
	}

	override fun toString(): String
	{
		return "Text [text=$word, offset=$offset]"
	}


}
