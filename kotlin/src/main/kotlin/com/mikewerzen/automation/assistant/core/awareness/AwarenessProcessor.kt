package com.mikewerzen.automation.assistant.core.awareness

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.awareness.container.Interactor
import com.mikewerzen.automation.assistant.core.awareness.container.InteractorRole
import com.mikewerzen.automation.assistant.core.awareness.container.InteractorType
import com.mikewerzen.automation.assistant.core.nlp.container.syntax.ClauseType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class AwarenessProcessor
{
	@Value("\${hotword}")
	private var automationName: String? = null

	fun analyze(context: AutomationContext): AutomationContext
	{
		context.interactors = processInteractors(context);
		return context
	}

	private fun processInteractors(context: AutomationContext): List<Interactor>
	{
		val interactors = ArrayList<Interactor>()

		val automationInteractor = Interactor()
		automationInteractor.name = "Automation"
		automationInteractor.type = InteractorType.Automation
		interactors.add(automationInteractor)

		val subjects = context.root?.subjects
		val automationNames = Arrays.asList(automationName)
		if (ClauseType.getClauseTypeForToken(context.root) == ClauseType.Command || subjects == null || subjects.isEmpty())
		{
			automationInteractor.role = InteractorRole.Target
		} else
		{
			if (subjects.any { automationNames.contains(it.word) })
				automationInteractor.role = InteractorRole.Target
			else
				automationInteractor.role = InteractorRole.Listener
		}

		return interactors
	}

	fun processInteractors(user: Interactor): List<Interactor>
	{
		val interactors = ArrayList<Interactor>()
		interactors.add(user)
		return interactors
	}

	fun getCurrentTime(): Date
	{
		return Calendar.getInstance().time
	}
}