package com.mikewerzen.automation.assistant.core.nlp.container.syntax

class DependencyEdge(parentTokenIndex: Int, dependencyType: DependencyType)
{
	var parentTokenIndex: Int = 0
		internal set

	var dependencyType: DependencyType? = null
		internal set

	init
	{
		this.parentTokenIndex = parentTokenIndex
		this.dependencyType = dependencyType
	}

	override fun toString(): String
	{
		return "DependencyEdge [parentTokenIndex=$parentTokenIndex, dependencyType=$dependencyType]"
	}


}
