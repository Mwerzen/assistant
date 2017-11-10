package com.mikewerzen.automation.assistant.core.decision

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.awareness.container.Interactor
import com.mikewerzen.automation.assistant.core.awareness.container.InteractorRole
import com.mikewerzen.automation.assistant.core.awareness.container.InteractorType
import com.mikewerzen.automation.assistant.core.decision.container.Field
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.ClauseType
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Token

import com.mikewerzen.automation.assistant.core.decision.container.Field.*
import com.mikewerzen.automation.assistant.core.decision.container.Rule
import com.mikewerzen.automation.assistant.core.decision.container.RuleSet
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DecisionProcessor
{

	private val logger = LoggerFactory.getLogger(this.javaClass)

	fun analyze(context: AutomationContext): AutomationContext
	{
		val rule = Rule()

		val rootToken = context.root
		extractSentencePartsForVerb(rule, rootToken)

		for (verb in rootToken!!.compliments)
		{
			extractSentencePartsForVerb(rule, verb)
		}


		rule.addField(SentenceType, ClauseType.getClauseTypeForToken(rootToken).toString())


		for (interactor in Interactor.getListeners(context.interactors!!))
		{
			if (interactor.role == InteractorRole.Target)
			{
				rule.addField(Target, interactor.name)
			}
			if (interactor.role == InteractorRole.Listener)
			{
				rule.addField(Listener, interactor.name)
			}
		}

		val interactor = Interactor.getSpeaker(context.interactors!!)?: Interactor()
		rule.addField(Speaker, interactor.name)

		if (interactor.type == InteractorType.Person)
		{
			rule.addField(SpeakerType, InteractorType.Person.toString())
		}
		if (interactor.type == InteractorType.Computer)
		{
			rule.addField(SpeakerType, InteractorType.Computer.toString())
		}
		if (interactor.type == InteractorType.Environment)
		{
			rule.addField(SpeakerType, InteractorType.Environment.toString())
		}
		if (interactor.type == InteractorType.Timer)
		{
			rule.addField(SpeakerType, InteractorType.Timer.toString())
		}


		addIfExists(rule, Privacy, context.request.privacyLevel.toString())

		logger.info(rule.toString());

		context.rule = rule
		context.action = RuleSet.matchRule(rule)

		return context
	}

	fun extractSentencePartsForVerb(rule: Rule, rootToken: Token?)
	{
		addIfExists(rule, Negation, rootToken!!.negations)
		addIfExists(rule, AuxVerb, rootToken.auxiliaryVerbs)
		addIfExists(rule, Subject, rootToken.subjects)
		addIfExistsPhrase(rule, SubjectPhrase, rootToken.subjects)
		addIfExists(rule, Verb, rootToken)
		addIfExists(rule, Compliment, rootToken.compliments)
		addIfExists(rule, Preposition, rootToken.prepositions)
		addIfExists(rule, TimeModifier, rootToken.timeModifiers)
		addIfExists(rule, Modifier, rootToken.modifiers)
		addIfExists(rule, DirectObject, rootToken.directObjectOrUnclassifiableDependents)
		addIfExistsPhrase(rule, DirectObjectPhrase, rootToken.directObjectOrUnclassifiableDependents)
		addIfExists(rule, IndirectObject, rootToken.indirectObjects)
		addIfExistsPhrase(rule, IndirectObjectPhrase, rootToken.indirectObjects)
		addIfExists(rule, PossessiveObject, rootToken.possessiveObjects)
		addIfExistsPhrase(rule, PossessiveObjectPhrase, rootToken.possessiveObjects)
	}

	private fun addIfExistsPhrase(rule: Rule, targetField: Field, tokens: List<Token>)
	{
		for (token in tokens)
			addIfExistsPhrase(rule, targetField, token)
	}

	fun addIfExistsPhrase(rule: Rule, targetField: Field?, token: Token?)
	{
		if (targetField != null && token != null)
		{
			val value = token.childrenPhrase

			if (value != null && !value.trim { it <= ' ' }.isEmpty())
			{
				rule.addField(targetField, value)
			}
		}
	}

	fun addIfExists(rule: Rule, targetField: Field, tokens: List<Token>)
	{
		for (token in tokens)
			addIfExists(rule, targetField, token)
	}

	fun addIfExists(rule: Rule, targetField: Field?, token: Token?)
	{
		if (targetField != null && token != null)
		{
			val value = token.word

			if (value != null && !value.trim { it <= ' ' }.isEmpty())
			{
				rule.addField(targetField, value)
			}
		}
	}

	fun addIfExists(rule: Rule, targetField: Field?, value: String?)
	{
		if (targetField != null && value != null)
		{

			if (value != null && !value.trim { it <= ' ' }.isEmpty())
			{
				rule.addField(targetField, value)
			}
		}
	}

}
