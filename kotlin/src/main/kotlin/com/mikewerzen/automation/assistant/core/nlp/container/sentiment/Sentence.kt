package com.mikewerzen.automation.assistant.core.nlp.container.sentiment

import com.google.cloud.language.v1.TextSpan

data class Sentence(
		var text: TextSpan? = null,
		var sentiment: Sentiment? = null
)