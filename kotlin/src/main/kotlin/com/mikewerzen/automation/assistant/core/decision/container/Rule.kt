package com.mikewerzen.automation.assistant.core.decision.container

import java.util.HashMap

class Rule
{
	var actionToTake: Action? = null

	private val attributes = HashMap<Field, String>()

	constructor()
	{
	}

	internal constructor(actionToTake: Action)
	{
		this.actionToTake = actionToTake
		RuleSet.rules.add(this) //TODO: THIS IS SNEAKY. LETS NOT FORGET THIS.
	}

	fun addField(field: Field, value: String): Rule
	{
		attributes.put(field, value)
		return this
	}

	fun addField(field: Field, value: Value): Rule
	{
		attributes.put(field, value.toString())
		return this
	}

	fun getMatchPercentage(ruleToMatch: Rule): Float
	{
		var match = 0f

		for ((key, value) in attributes)
		{
			val attributeValueToMatch = ruleToMatch.attributes[key]

			if (attributeValueToMatch != null && value.toUpperCase() == attributeValueToMatch.toUpperCase())
			{
				match++
			}
		}

		return if (attributes.size != 0)
			match / attributes.size
		else
			0f
	}

	fun getValueForField(field: Field): String?
	{
		return attributes[field]
	}

	override fun toString(): String
	{
		return "Rule(actionToTake=$actionToTake, attributes=$attributes)"
	}


}
