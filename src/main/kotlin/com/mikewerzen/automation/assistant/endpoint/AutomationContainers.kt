package com.mikewerzen.automation.assistant.endpoint

data class AutomationRequest(val text: String, val location: String, val encryption: String?);

data class AutomationResponse(val shortResponse: String?, val longResponse: String?,  val data: String?);