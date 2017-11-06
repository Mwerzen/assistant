package com.mikewerzen.automation.assistant.action.wookiepedia

import com.mikewerzen.automation.assistant.action.Action
import com.mikewerzen.automation.assistant.action.ActionRequest
import com.mikewerzen.automation.assistant.action.ActionResponse
import com.mikewerzen.automation.assistant.client.restaction.RestActionClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WookiepediaAdapter : Action
{
	@Value("\${actions.wookie.url}")
	lateinit private var url: String

	@Autowired
	lateinit private var client: RestActionClient

	override fun performAction(request: ActionRequest): ActionResponse
	{
		return client.get(url, request);
	}


}