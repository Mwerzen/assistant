package com.mikewerzen.automation.assistant.core.nlp.mapper;


import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.EntityMention;
import com.google.cloud.language.v1.TextSpan;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityType;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.MentionType;
import com.mikewerzen.automation.assistant.core.nlp.container.sentiment.Sentiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoogleEntityMapper
{

	public static List<com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity> mapGoogleEntitiesToDomainEntities(List<Entity> googleEntities)
	{
		List<com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity> domainEntities = new ArrayList<com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity>();

		for (Entity googleEntity : googleEntities)
		{
			domainEntities.add(mapGoogleEntityToDomainEntity(googleEntity));
		}

		return domainEntities;
	}

	public static com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity mapGoogleEntityToDomainEntity(Entity googleEntity)
	{
		String name = googleEntity.getName();

		EntityType entityType = EntityType.Companion.getTypeForTag(googleEntity.getType().toString());

		Map<String, String> metadata = googleEntity.getMetadataMap();

		Double salience = Double.valueOf(googleEntity.getSalience());

		List<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention> mentions = mapEntityMentions(googleEntity);

		Sentiment sentiment = GoogleSentimentMapper.mapGoogleSentimentToDomainSentiment(googleEntity.getSentiment());

		return new com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity(name, entityType, metadata, salience, mentions, sentiment);
	}

	private static List<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention> mapEntityMentions(Entity googleEntity)
	{
		List<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention> mentions = new ArrayList<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention>();

		for (EntityMention googleMention : googleEntity.getMentionsList())
		{
			mapGoogleEntityMentionToDomainEntityMention(googleMention);
		}

		return mentions;
	}

	private static void mapGoogleEntityMentionToDomainEntityMention(EntityMention googleMention)
	{
		com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan textSpan = mapTextSpan(googleMention.getText());
		MentionType type = MentionType.valueOf(googleMention.getType().toString());
		Sentiment sentiment = GoogleSentimentMapper.mapGoogleSentimentToDomainSentiment(googleMention.getSentiment());

		new com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention(textSpan, type, sentiment);
	}

	private static com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan mapTextSpan(TextSpan span)
	{
		return new com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan(span.getContent(), span.getBeginOffset());
	}


}
