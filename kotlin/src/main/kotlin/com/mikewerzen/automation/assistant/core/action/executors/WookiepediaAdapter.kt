package com.mikewerzen.automation.assistant.core.action.executors

import com.mikewerzen.automation.assistant.client.restaction.RestActionClient
import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.action.ActionExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WookiepediaAdapter : ActionExecutor
{
	@Value("\${actions.wookie.url}")
	lateinit private var url: String

	@Autowired
	lateinit private var client: RestActionClient

	override fun execute(request: AutomationContext): AutomationContext
	{
		return client.get(url, request);
	}


}