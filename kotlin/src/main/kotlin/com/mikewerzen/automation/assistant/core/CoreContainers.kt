package com.mikewerzen.automation.assistant.core

import com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity
import com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token
import com.mikewerzen.automation.assistant.endpoint.AutomationRequest
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse

data class AutomationContext(
		val request : AutomationRequest,
		var response: AutomationResponse? = null,

		//NLP
		var entities : List<Entity>? = null,
		var root : Token? = null,
		var sentiment: Sentiment? = null

		//Decision


		)