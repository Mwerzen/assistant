package com.mikewerzen.automation.assistant.core.action

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.action.executors.BingImageSearch
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import org.springframework.stereotype.Component

@Component
class ActionRouter
{
	var actions: MutableMap<Action, ActionExecutor> = HashMap<Action, ActionExecutor>()

	init
	{
		actions.put(Action.ShowPicture, BingImageSearch())
	}


	fun executeAction(context: AutomationContext): AutomationContext
	{
		if (actions.containsKey(context.action))
		{
			return actions.get(context.action)!!.execute(context)
		}
		else
		{
			context.response = AutomationResponse("Not Yet Implemented")
			return context
		}
	}

}