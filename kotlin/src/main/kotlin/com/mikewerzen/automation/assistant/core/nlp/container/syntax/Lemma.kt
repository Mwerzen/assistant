package com.mikewerzen.automation.assistant.core.nlp.container.syntax

class Lemma(word: String)
{

	var word: String? = null
		internal set

	init
	{
		this.word = word
	}

	override fun toString(): String
	{
		return "Lemma [word=$word]"
	}

}
