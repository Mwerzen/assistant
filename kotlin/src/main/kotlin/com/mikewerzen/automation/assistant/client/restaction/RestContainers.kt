package com.mikewerzen.automation.assistant.client.restaction

data class RestActionRequest(val text: String?, val args: List<String>?, val location : String?);

data class RestActionResponse(val code: Integer?, val short : String?, val long: String?, val page : String?, val image: String?);