package com.mikewerzen.automation.assistant.core.nlp.container.syntax

data class DependencyEdge
(
	var headTokenIndex: Int = 0,

	var label: Label? = null

)
