package com.mikewerzen.automation.assistant.core.nlp.mapper;

import com.google.cloud.language.v1.DependencyEdge;
import com.google.cloud.language.v1.PartOfSpeech;
import com.google.cloud.language.v1.TextSpan;
import com.google.cloud.language.v1.Token;
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.*;
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Number;

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
			if (!token.getLabel().equals(Label.Root))
			{
				com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token parent = domainTokens.get(token.getDependencyEdge().getHeadTokenIndex());
				token.setParent(parent);
				parent.addChild(token);
			}
		}
	}

	public static com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token mapGoogleTokenToDomainToken(List<Token> googleTokens, int index)
	{
		Token googleToken = googleTokens.get(index);

		com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan text = mapGoogleTextSpanToDomainText(googleToken.getText());

		com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeech partOfSpeech =
				mapGooglePartOfSpeechToDomainPartOfSpeech(googleToken.getPartOfSpeech());

		com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyEdge dependencyEdge = mapGoogleDependencyEdgeToDomainDependencyEdge(googleToken.getDependencyEdge());

		String lemma = googleToken.getLemma();

		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token(text, partOfSpeech, dependencyEdge, lemma);
	}

	private static com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyEdge mapGoogleDependencyEdgeToDomainDependencyEdge(DependencyEdge googleDependencyEdge)
	{
		int headTokenIndex = googleDependencyEdge.getHeadTokenIndex();
		Label type = com.mikewerzen.automation.assistant.core.nlp.container.syntax.Label.Companion.getTypeForTag(googleDependencyEdge.getLabel().toString());
		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyEdge(headTokenIndex, type);
	}

	private static com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeech mapGooglePartOfSpeechToDomainPartOfSpeech(PartOfSpeech googlePartOfSpeech)
	{
		PartOfSpeechTag tag = PartOfSpeechTag.Companion.getTypeForTag(googlePartOfSpeech.getTag().toString());
		Aspect aspect = Aspect.valueOf(googlePartOfSpeech.getAspect().toString());
		Case caze = Case.valueOf(googlePartOfSpeech.getCase().toString());
		Form form = Form.valueOf(googlePartOfSpeech.getForm().toString());
		Gender gender = Gender.valueOf(googlePartOfSpeech.getGender().toString());
		Mood mood = Mood.valueOf(googlePartOfSpeech.getMood().toString());
		Number number = Number.valueOf(googlePartOfSpeech.getNumber().toString());
		Person person = Person.valueOf(googlePartOfSpeech.getPerson().toString());
		Proper proper = Proper.valueOf(googlePartOfSpeech.getProper().toString());
		Reciprocity reciprocity = Reciprocity.valueOf(googlePartOfSpeech.getReciprocity().toString());
		Tense tense = Tense.valueOf(googlePartOfSpeech.getTense().toString());
		Voice voice = Voice.valueOf(googlePartOfSpeech.getVoice().toString());


		return new com.mikewerzen.automation.assistant.core.nlp.container.syntax.PartOfSpeech(tag, aspect, caze, form, gender, mood, number, person, proper, reciprocity, tense, voice);
	}

	private static com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan mapGoogleTextSpanToDomainText(TextSpan googleTextSpan)
	{
		return new com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan(googleTextSpan.getContent(), googleTextSpan.getBeginOffset());
	}

}
