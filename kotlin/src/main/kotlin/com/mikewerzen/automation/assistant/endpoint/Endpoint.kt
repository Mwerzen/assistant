package com.mikewerzen.automation.assistant.endpoint

import com.mikewerzen.automation.assistant.action.ActionRequest
import com.mikewerzen.automation.assistant.action.wookiepedia.WookiepediaAdapter
import com.mikewerzen.automation.assistant.client.google.sheets.SheetsClient
import com.mikewerzen.automation.assistant.util.SecurityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
public class EndpointController
{

	@Autowired
	lateinit private var wookie: WookiepediaAdapter;

	@Autowired
	lateinit private var sheets: SheetsClient;

	@Autowired
	lateinit private var securityUtil : SecurityUtil;

	@RequestMapping("/auto")
	public fun submitAutomationRequest(@RequestHeader(name = "token") token : String, @RequestBody request: AutomationRequest) : AutomationResponse
	{
		auth(token);


		val actionRequest = ActionRequest("search", Arrays.asList("Test"), "Test");

		//val actionResponse = ActionResponse("", sheets.data, "")

		val actionResponse = wookie.performAction(actionRequest);

		return AutomationResponse("Hello");
	}


	private fun auth(token : String)
	{
		securityUtil.validateToken(token);
	}
}
