package com.mikewerzen.automation.assistant.core.decision.container

import com.mikewerzen.automation.assistant.core.action.Action
import java.util.HashMap

class Rule
{
	var actionToTake: Action? = null

	private val attributes = HashMap<String, MutableList<String>>()

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
		return addField(field.toString(), value);
	}

	fun addField(field: Field, value: Value): Rule
	{
		return addField(field.toString(), value.toString());
	}

	fun addField(field: Object, value: Value): Rule
	{
		return addField(field.toString(), value.toString());
	}

	fun addField(field: String, value: String) : Rule
	{
		if(attributes.get(field.toUpperCase()) == null)
		{
			attributes.set(field.toUpperCase(), ArrayList<String>())
		}

		attributes.get(field.toUpperCase())!!.add(value.toUpperCase())
		return this
	}

	fun getMatchPercentage(ruleToMatch: Rule): Float
	{
		var match = 0f

		for (attribute in attributes)
		{
			val otherValue = ruleToMatch.attributes[attribute.key]

			for(myValue in attribute.value)
			{
				if (otherValue != null && otherValue.contains(myValue))
				{
					match++
				}
			}
		}

		return if (attributes.size != 0)
		{
			match
			//	match / attributes.size
		}
		else
			0f
	}

	fun getValueForField(field: Field): MutableList<String>?
	{
		return attributes[field.toString().toUpperCase()]
	}

	override fun toString(): String
	{
		return "Rule(actionToTake=$actionToTake, attributes=$attributes)"
	}


}
