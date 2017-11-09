package com.mikewerzen.automation.assistant.core.nlp

import com.mikewerzen.automation.assistant.core.AutomationContext
import org.springframework.stereotype.Component
import com.mikewerzen.automation.assistant.core.nlp.mapper.GoogleSyntaxMapper
import com.mikewerzen.automation.assistant.core.nlp.mapper.GoogleSentimentMapper
import com.mikewerzen.automation.assistant.core.nlp.mapper.GoogleEntityMapper
import com.mikewerzen.automation.assistant.client.google.nlp.GoogleNLPClient
import org.springframework.beans.factory.annotation.Autowired
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Label
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token
import org.slf4j.LoggerFactory


@Component
class NaturalLanguageProcessor
{
	private val logger = LoggerFactory.getLogger(this.javaClass)

	@Autowired
	private var googleNLPClient : GoogleNLPClient? = null

	fun analyze(context : AutomationContext)
	{
		val googleAnnotatedText = googleNLPClient!!.analyzeEntitiesSentimentAndSyntax(context.request.inputText);

		logger.info(googleAnnotatedText.tokensList.toString())

		context.entities = GoogleEntityMapper.mapGoogleEntitiesToDomainEntities(googleAnnotatedText.entitiesList)
		context.sentiment = GoogleSentimentMapper.mapGoogleSentimentToDomainSentiment(googleAnnotatedText.documentSentiment)
		context.root = findRoot(GoogleSyntaxMapper.mapGoogleTokensToDomainTokens(googleAnnotatedText.tokensList))


	}

	fun findRoot(tokens: List<Token>) : Token?
	{
		for (token in tokens)
			if (token.label!!.equals(Label.Root))
				return token
		return null;
	}
}