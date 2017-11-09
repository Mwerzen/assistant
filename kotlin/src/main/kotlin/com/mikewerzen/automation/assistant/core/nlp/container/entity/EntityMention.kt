package com.mikewerzen.automation.assistant.core.nlp.container.entity

import com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment

data class EntityMention
(
		var text: TextSpan? = null,

		var type: MentionType? = null,

		var sentiment: Sentiment? = null

)

