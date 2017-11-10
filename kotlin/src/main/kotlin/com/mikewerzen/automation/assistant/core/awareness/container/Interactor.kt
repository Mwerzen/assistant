package com.mikewerzen.automation.assistant.core.awareness.container

import com.mikewerzen.automation.assistant.core.nlp.container.entity.Entity


class Interactor
{
	var name: String = "Unknown"
	var entity: Entity? = null
	var type: InteractorType? = null
	var role: InteractorRole? = null


	companion object
	{
		fun getListeners(allInteractors: List<Interactor>): List<Interactor>
		{
			return allInteractors.filter { InteractorRole.Listener.equals(it.role) || InteractorRole.Target.equals(it.role) }
		}


		fun getSpeaker(allInteractors: List<Interactor>): Interactor?
		{
			allInteractors.forEach { if(InteractorRole.Speaker.equals(it.role)) return it }
			return null;
		}

		fun getTarget(allInteractors: List<Interactor>): Interactor?
		{
			allInteractors.forEach { if(InteractorRole.Target.equals(it.role)) return it }
			return null;
		}
	}
}