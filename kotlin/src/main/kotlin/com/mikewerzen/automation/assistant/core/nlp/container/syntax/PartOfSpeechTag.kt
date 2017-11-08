package com.mikewerzen.automation.assistant.core.nlp.container.syntax

enum class PartOfSpeechTag private constructor(private val tag: String)
{
	Any("*"),
	Adverb("ADV"),
	Adposition("ADP"),
	Adjective("ADJ"),
	Conjunction("CONJ"),
	Article("DET"),
	Interjection("X"),
	Noun("NOUN"),
	Number("NUM"),
	Pronoun("PRON"),
	Participle("PRT"),
	Punctuation("PUNCT"),
	Verb("VERB");

	fun isEquivelentTo(other: PartOfSpeechTag): Boolean
	{
		return this == other || this == Any || other == Any
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
