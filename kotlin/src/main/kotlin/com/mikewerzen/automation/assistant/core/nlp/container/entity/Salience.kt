package com.mikewerzen.automation.assistant.core.nlp.container.entity

class Salience(salience: Float)
{
	var salience: Float = 0.toFloat()
		internal set


	init
	{
		this.salience = salience
	}

	override fun toString(): String
	{
		return "Salience [salience=$salience]"
	}


}
