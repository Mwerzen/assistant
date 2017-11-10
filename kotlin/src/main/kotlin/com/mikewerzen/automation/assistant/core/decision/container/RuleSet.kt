package com.mikewerzen.automation.assistant.core.decision.container


import com.mikewerzen.automation.assistant.core.nlp.container.syntax.ClauseType

import java.util.ArrayList

import com.mikewerzen.automation.assistant.core.decision.container.Action.*
import com.mikewerzen.automation.assistant.core.decision.container.Field.*

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
	private val show = Rule(ShowPicture).addField(Verb, "show")
	private val display = Rule(ShowPicture).addField(Verb, "display")
	private val showPicture = Rule(PlayVideo).addField(Subject, "Automation").addField(Verb, "show")


	fun matchRule(searchRule: Rule): Action
	{
		var bestMatchRule = rules[0]
		var bestMatchPercentage = 0f

		for (rule in rules)
		{
			val matchPercentage = rule.getMatchPercentage(searchRule)
			println("Action: " + rule.actionToTake + " Perc: " + matchPercentage)
			if (matchPercentage > bestMatchPercentage)
			{
				bestMatchRule = rule
				bestMatchPercentage = matchPercentage
			}
		}

		return bestMatchRule.actionToTake!!
	}
}
