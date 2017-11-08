package com.mikewerzen.automation.assistant.core.nlp.container.syntax

class PartOfSpeech(tag: PartOfSpeechTag)
{
	var tag: PartOfSpeechTag? = null
		internal set

	init
	{
		this.tag = tag
	}

	override fun toString(): String
	{
		return "PartOfSpeech [tag=$tag]"
	}


}
