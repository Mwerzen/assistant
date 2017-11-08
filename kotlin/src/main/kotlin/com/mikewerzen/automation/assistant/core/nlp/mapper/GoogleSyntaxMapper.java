package com.mikewerzen.automation.assistant.core.nlp.mapper;

import com.google.api.services.language.v1beta1.model.DependencyEdge;
import com.google.api.services.language.v1beta1.model.PartOfSpeech;
import com.google.api.services.language.v1beta1.model.TextSpan;
import com.google.api.services.language.v1beta1.model.Token;
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyType;
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Lemma;
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeechTag;
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Text;

import java.util.ArrayList;
import java.util.List;

public class GoogleSyntaxMapper
{

	public static List<com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token> mapGoogleTokensToDomainTokens(List<Token> googleTokens)
	{
		List<com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token> domainTokens = new ArrayList<com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token>();

		for (int index = 0; index < googleTokens.size(); index++)
		{
			domainTokens.add(mapGoogleTokenToDomainToken(googleTokens, index));
		}

		updateDomainTokenRoots(domainTokens);

		return domainTokens;
	}

	public static void updateDomainTokenRoots(List<com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token> domainTokens)
	{
		for (com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token token : domainTokens)
		{
			if (!token.getDependencyType().equals(DependencyType.Root))
			{
				com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token parent = domainTokens.get(token.getDependencyEdge().getParentTokenIndex());
				token.setParent(parent);
				parent.addChild(token);
			}
		}
	}

	public static com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token mapGoogleTokenToDomainToken(List<Token> googleTokens, int index)
	{
		Token googleToken = googleTokens.get(index);

		com.mikewerzen.automation.assistant.core.nlp.container.syntax.Text text = mapGoogleTextSpanToDomainText(googleToken.getText());

		com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeech partOfSpeech =
				mapGooglePartOfSpeechToDomainPartOfSpeech(googleToken.getPartOfSpeech());

		com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyEdge dependencyEdge = mapGoogleDependencyEdgeToDomainDependencyEdge(googleToken.getDependencyEdge());

		com.mikewerzen.automation.assistant.core.nlp.container.syntax.Lemma lemma = new com.mikewerzen.automation.assistant.core.nlp.container.syntax.Lemma(googleToken.getLemma());

		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token(index, text, partOfSpeech, dependencyEdge, lemma);
	}

	private static com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyEdge mapGoogleDependencyEdgeToDomainDependencyEdge(DependencyEdge googleDependencyEdge)
	{
		int headTokenIndex = googleDependencyEdge.getHeadTokenIndex();
		DependencyType type = com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyType.Companion.getTypeForTag(googleDependencyEdge.getLabel());
		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyEdge(headTokenIndex, type);
	}

	private static com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeech mapGooglePartOfSpeechToDomainPartOfSpeech(PartOfSpeech googlePartOfSpeech)
	{
		com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeechTag partOfSpeechTag =
				com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeechTag.Companion.getTypeForTag(googlePartOfSpeech.getTag());

		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeech(partOfSpeechTag);
	}

	private static Text mapGoogleTextSpanToDomainText(TextSpan googleTextSpan)
	{
		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.Text(googleTextSpan.getContent(), googleTextSpan.getBeginOffset());
	}

}
