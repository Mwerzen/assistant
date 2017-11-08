package com.mikewerzen.automation.assistant.core.nlp.mapper;

import com.google.api.services.language.v1beta1.model.Sentiment;

public class GoogleSentimentMapper
{
	
	public static com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment mapGoogleSentimentToDomainSentiment(Sentiment googleSentiment)
	{
		return new com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment(googleSentiment.getPolarity(), googleSentiment.getMagnitude());
	}

}
