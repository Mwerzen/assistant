package com.mikewerzen.automation.assistant.core.awareness.container

import com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity


data class Interactor
(
	var name: String? = null,
	var entity: Entity? = null,
	var type: InteractorType? = null,
	var role: InteractorRole? = null
)
