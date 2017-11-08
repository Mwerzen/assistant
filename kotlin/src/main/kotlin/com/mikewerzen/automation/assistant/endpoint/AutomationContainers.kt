package com.mikewerzen.automation.assistant.endpoint

enum class PrivacyLevel
{ ALONE, PRIVATE, PUBLIC }

enum class Location
{ ROOM, HOME, CAR, WORK, GENERAL }

enum class Device
{ CLI, CLIENT, WEBPAGE, CELL, WATCH, TV, SPEAKER }

data class AutomationRequest(
		val inputText: String,
		var privacyLevel: PrivacyLevel = PrivacyLevel.PUBLIC,
		var location: Location = Location.GENERAL,
		var device: Device = Device.WEBPAGE
)

data class AutomationResponse(
		val text: String,
		val description: String? = null,
		val page: String? = null,
		val image: String? = null
);