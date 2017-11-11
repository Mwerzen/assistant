package com.mikewerzen.automation.assistant.core.action.executors

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.action.ActionExecutor
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import java.net.URISyntaxException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class BingImageSearch : ActionExecutor
{

	internal var bingURL = "http://www.bing.com/images/search?"
	internal var queryParam = "q="
	internal var formParamAndValue = "&FORM=HDRSC2"


	override fun execute(context: AutomationContext): AutomationContext
	{
		var searchTerms = context.root!!.directObjectOrUnclassifiableDependentsString()


		val bingSearchURL = bingURL + queryParam + URLEncoder.encode(searchTerms, StandardCharsets.UTF_8.toString()) + formParamAndValue

		try
		{
			context.response = AutomationResponse("Okay, here's  " + searchTerms)
			context.response!!.page = bingSearchURL;
		} catch (e: URISyntaxException)
		{
			println(e)
			context.response = AutomationResponse("Something failed")
		}

		return context
	}
}