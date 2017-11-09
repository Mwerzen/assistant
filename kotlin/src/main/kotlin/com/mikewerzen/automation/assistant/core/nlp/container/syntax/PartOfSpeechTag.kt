package com.mikewerzen.automation.assistant.core.nlp.container.syntax

enum class PartOfSpeechTag private constructor(private val tag: String)
{
	ANY("*"),
	ADVERB("ADV"),
	ADPOSITION("ADP"),
	ADJECTIVE("ADJ"),
	CONJUNCTION("CONJ"),
	DETERMINER("DET"),
	INTERJECTION("X"),
	NOUN("NOUN"),
	NUMBER("NUM"),
	PRONOUN("PRON"),
	PARTICIPLE("PRT"),
	PUNCTUATION("PUNCT"),
	VERB("VERB");

	fun isEquivelentTo(other: PartOfSpeechTag): Boolean
	{
		return this == other || this == ANY || other == ANY
	}


	fun isInArray(vararg tags: PartOfSpeechTag): Boolean
	{
		for (tag in tags)
		{
			if (tag.isEquivelentTo(this))
			{
				return true
			}
		}

		return false
	}

	companion object
	{

		fun getTypeForTag(tag: String): PartOfSpeechTag
		{
			for (existingPartOfSpeechTag in PartOfSpeechTag.values())
			{
				if (existingPartOfSpeechTag.tag == tag.toUpperCase())
				{
					return existingPartOfSpeechTag
				}
			}

			throw RuntimeException("Could not determine matching PartOfSpeechTag for tag: " + tag)
		}
	}
}
