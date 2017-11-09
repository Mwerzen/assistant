package com.mikewerzen.automation.assistant.endpoint

import com.mikewerzen.automation.assistant.action.ActionRequest
import com.mikewerzen.automation.assistant.action.wookiepedia.WookiepediaAdapter
import com.mikewerzen.automation.assistant.client.google.sheets.SheetsClient
import com.mikewerzen.automation.assistant.core.AutomationCore
import com.mikewerzen.automation.assistant.util.SecurityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
public class EndpointController
{

	@Autowired
	private var core: AutomationCore? = null

	@Autowired
	lateinit private var securityUtil : SecurityUtil;

	@RequestMapping("/auto")
	public fun submitAutomationRequest(@RequestHeader(name = "token") token : String, @RequestBody request: AutomationRequest) : AutomationResponse
	{
		auth(token);

		return core!!.processRequest(request)
	}


	private fun auth(token : String)
	{
		securityUtil.validateToken(token);
	}
}
