package com.mikewerzen.automation.assistant.core.action

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.action.executors.BingImageSearch
import com.mikewerzen.automation.assistant.core.action.executors.WookiepediaAdapter
import com.mikewerzen.automation.assistant.core.action.executors.YoutubeVideoSearch
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ActionRouter
{
	var actions: MutableMap<Action, ActionExecutor> = HashMap<Action, ActionExecutor>()

	constructor(@Autowired bingImageSearch: BingImageSearch,
				@Autowired wookiepediaAdapter: WookiepediaAdapter,
				@Autowired youtubeVideoSearch: YoutubeVideoSearch)
	{
		actions.put(Action.ShowPicture, bingImageSearch)
		actions.put(Action.AnswerQuestion, wookiepediaAdapter)
		actions.put(Action.PlayVideo, youtubeVideoSearch)
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