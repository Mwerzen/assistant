package com.mikewerzen.automation.assistant.core.nlp.mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.language.v1beta1.model.Entity;
import com.google.api.services.language.v1beta1.model.EntityMention;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityType;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.Metadata;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.MetadataKey;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.Salience;
import com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan;

public class GoogleEntityMapper
{
	
	public static List<com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity> mapGoogleEntitiesToDomainEntities(List<Entity> googleEntities)
	{
		List<com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity> domainEntities = new ArrayList<com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity>();
		
		for(Entity googleEntity : googleEntities)
		{
			domainEntities.add(mapGoogleEntityToDomainEntity(googleEntity));
		}
		
		return domainEntities;
	}

	public static com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity mapGoogleEntityToDomainEntity(Entity googleEntity)
	{
		String name = googleEntity.getName();
		
		EntityType entityType = EntityType.Companion.getTypeForTag(googleEntity.getType());
		
		Metadata metadata = mapMetadata(googleEntity);
		
		Salience salience = new Salience(googleEntity.getSalience());
		
		List<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention> mentions = mapEntityMentions(googleEntity);

		return new com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity(name, entityType, metadata, salience, mentions);
	}

	private static List<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention> mapEntityMentions(Entity googleEntity)
	{
		List<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention> mentions = new ArrayList<com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention>();
		
		for(EntityMention googleMention : googleEntity.getMentions())
		{
			mapGoogleEntityMentionToDomainEntityMention(googleMention);
		}
		
		return mentions;
	}

	private static void mapGoogleEntityMentionToDomainEntityMention(EntityMention googleMention)
	{
		TextSpan domainTextSpan = new TextSpan(googleMention.getText().getContent(), googleMention.getText().getBeginOffset());
		new com.mikewerzen.automation.assistant.core.nlp.container.entity.EntityMention(domainTextSpan);
	}

	private static Metadata mapMetadata(Entity googleEntity)
	{
		Metadata metadata = new Metadata();
		
		for(String key : googleEntity.getMetadata().keySet())
		{
			mapGoogleMetadataToDomainMetadata(googleEntity, metadata, key);
		}
		
		return metadata;
	}

	private static void mapGoogleMetadataToDomainMetadata(Entity googleEntity, Metadata metadata, String key)
	{
		MetadataKey metadataKey = MetadataKey.Companion.getTypeForTag(key);
		String value = googleEntity.getMetadata().get(key);
		metadata.addMetadata(metadataKey, value);
	}

}
