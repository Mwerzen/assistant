package com.mikewerzen.automation.assistant.client.restaction

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import com.mikewerzen.automation.assistant.util.SecurityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class RestActionClient
{
	@Autowired
	lateinit var securityUtil: SecurityUtil

	val restTemplate = RestTemplate()

	fun get(url: String, context: AutomationContext): AutomationContext
	{
		val restRequest = RestActionRequest(context.request.inputText, listOf(context.root!!.trailingChildrenPhrase), context.request.location.toString());

		val restResponse = get(url, restRequest);

		context.response = AutomationResponse(restResponse.short!!, restResponse.long, restResponse.page, restResponse.image)
		return context
	}

	fun get(url: String, request: RestActionRequest): RestActionResponse
	{
		val restRequest = RestActionRequest(request.text, request.args, request.location);

		val entity = HttpEntity<RestActionRequest>(restRequest, securityUtil.generateSecureHttpHeaders())

		return restTemplate.postForObject(URI(url), entity, RestActionResponse(null, null, null, null, null).javaClass)

	}

}