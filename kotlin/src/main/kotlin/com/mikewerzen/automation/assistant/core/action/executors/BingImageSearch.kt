package com.mikewerzen.automation.assistant.core.action.executors

import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.action.ActionExecutor
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.URISyntaxException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class BingImageSearch : ActionExecutor
{
	private val logger = LoggerFactory.getLogger(this.javaClass)

	internal var bingURL = "http://www.bing.com/images/search?"
	internal var queryParam = "q="
	internal var formParamAndValue = "&FORM=HDRSC2"


	override fun execute(context: AutomationContext): AutomationContext
	{
		var searchTerms = context.root!!.directObjectOrUnclassifiableDependentsString()

		logger.info("Searching for image with terms: " + searchTerms)

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