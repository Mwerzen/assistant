package com.mikewerzen.automation.assistant.core.nlp.mapper;


import com.google.cloud.language.v1.Sentiment;

public class GoogleSentimentMapper
{
	
	public static com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment mapGoogleSentimentToDomainSentiment(Sentiment googleSentiment)
	{
		return new com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment(googleSentiment.getScore(), googleSentiment.getMagnitude());
	}

}
