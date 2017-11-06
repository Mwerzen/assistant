package com.mikewerzen.automation.assistant.action

interface Action
{
	fun performAction(request: ActionRequest): ActionResponse;
}

data class ActionRequest(val text: String?, val args: List<String>?, val location : String?);

data class ActionResponse(val shortResponse: String?, val longResponse: String?,  val link: String?, val image: String?);