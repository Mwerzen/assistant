package com.mikewerzen.automation.assistant.core.nlp.container.sentiment

class Sentiment(polarity: Float, magnitude: Float)
{

	var polarity: Float = 0.toFloat()
		internal set

	var magnitude: Float = 0.toFloat()
		internal set

	init
	{
		this.polarity = polarity
		this.magnitude = magnitude
	}

	override fun toString(): String
	{
		return "Sentiment [polarity=$polarity, magnitude=$magnitude]"
	}

}
