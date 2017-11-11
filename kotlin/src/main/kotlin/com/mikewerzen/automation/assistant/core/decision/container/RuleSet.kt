package com.mikewerzen.automation.assistant.core.decision.container


import com.mikewerzen.automation.assistant.core.nlp.container.syntax.ClauseType

import java.util.ArrayList

import com.mikewerzen.automation.assistant.core.action.Action.*
import com.mikewerzen.automation.assistant.core.decision.container.Field.*
import com.mikewerzen.automation.assistant.core.action.Action

object RuleSet
{
	internal var rules: MutableList<Rule> = ArrayList()

	private val playMusic = Rule(PlayMusic).addField(Subject, "Automation").addField(Verb, "play").addField(DirectObject, "song")
	private val playVideo = Rule(PlayVideo).addField(Subject, "Automation").addField(Verb, "play").addField(DirectObject, "video")
	private val playYoutube = Rule(PlayYoutube).addField(Subject, "Automation").addField(Verb, "play")
	private val playGeneric = Rule(PlayYoutube).addField(SentenceType, ClauseType.Command.toString()).addField(Verb, "play")
	private val answerQuestion = Rule(AnswerQuestion).addField(SentenceType, ClauseType.WhInterrogative.toString())
	private val genericQuestion = Rule(AnswerQuestion).addField(SentenceType, ClauseType.Question.toString())
	private val define = Rule(AnswerQuestion).addField(Verb, "define")

	private val show = Rule(ShowPicture)
			.addField(Target, "Automation")
			.addField(Verb, "show")
			.addField(Verb, "display")
			.addField(DirectObject, "picture")
			.addField(DirectObject, "pictures")


	fun matchRule(searchRule: Rule): Action
	{
		var bestMatchRule = rules[0]
		var bestMatchPercentage = 0f

		for (rule in rules)
		{
			val matchPercentage = rule.getMatchPercentage(searchRule)
			println("ActionExecutor: " + rule.actionToTake + " Perc: " + matchPercentage)
			if (matchPercentage > bestMatchPercentage)
			{
				bestMatchRule = rule
				bestMatchPercentage = matchPercentage
			}
		}

		return bestMatchRule.actionToTake!!
	}
}
