package com.mikewerzen.automation.assistant.core.decision.container

enum class Value
{
	Auto;

	fun equals(other: String?): Boolean
	{
		return if (other == null)
			false
		else
			this.toString().toUpperCase() == other.toUpperCase()
	}
}
