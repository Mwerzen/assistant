package com.mikewerzen.automation.assistant.client.restaction

import com.mikewerzen.automation.assistant.action.ActionRequest
import com.mikewerzen.automation.assistant.action.ActionResponse
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
	lateinit var securityUtil : SecurityUtil

	val restTemplate = RestTemplate()

	fun get(url: String, request: ActionRequest) : ActionResponse
	{
		val restRequest = RestActionRequest(request.text, request.args, request.location);

		val restResponse = get(url, restRequest);

		return ActionResponse(restResponse.short, restResponse.long, restResponse.page, restResponse.image);
	}

	fun get(url : String,  request: RestActionRequest) : RestActionResponse
	{
		val restRequest = RestActionRequest(request.text, request.args, request.location);

		val entity = HttpEntity<RestActionRequest>(restRequest, securityUtil.generateSecureHttpHeaders())

		return restTemplate.postForObject(URI(url), entity, RestActionResponse(null, null, null, null, null).javaClass)

	}

}