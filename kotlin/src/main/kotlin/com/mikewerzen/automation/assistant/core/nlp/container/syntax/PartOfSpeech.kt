package com.mikewerzen.automation.assistant.core.nlp.container.syntax

data class PartOfSpeech(

	var tag: PartOfSpeechTag? = null,
	var aspect: Aspect? = null,
	var case: Case? = null,
	var form: Form? = null,
	var gender: Gender? = null,
	var mood: Mood? = null,
	var number: Number? = null,
	var person: Person? = null,
	var proper: Proper? = null,
	var reciprocity: Reciprocity? = null,
	var tense: Tense? = null,
	var voice: Voice? = null

)
