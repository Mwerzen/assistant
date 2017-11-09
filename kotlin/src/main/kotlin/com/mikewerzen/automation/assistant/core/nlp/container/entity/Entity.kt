package com.mikewerzen.automation.assistant.core.nlp.container.entity

import com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment

data class Entity(

	var name: String? = null,

	var type: EntityType? = null,

	var metadata: Map<String, String>? = null,

	var salience: Double? = null,

	var entityMentions: List<EntityMention>? = null,

	var sentiment: Sentiment? = null
)
