package com.mikewerzen.automation.assistant.core.action.executors

import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.mikewerzen.automation.assistant.client.google.util.GoogleUtil
import com.mikewerzen.automation.assistant.core.AutomationContext
import com.mikewerzen.automation.assistant.core.action.ActionExecutor
import com.mikewerzen.automation.assistant.core.decision.container.Field
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.io.IOException

@Component
class YoutubeVideoSearch : ActionExecutor
{
	private val logger = LoggerFactory.getLogger(this.javaClass)

	@Autowired
	private val googleUtil: GoogleUtil? = null

	override fun execute(context: AutomationContext): AutomationContext
	{
		val videoToPlay = determineVideoToPlayFromTypes(context)

		logger.info("Searching for video with terms: " + videoToPlay)

		val results = getYoutubeSearchResults(videoToPlay)

		if (doesResultHaveItems(results))
		{
			context.response = AutomationResponse("Found a video", null, getVideoURL(results), null)
		} else
		{
			context.response = AutomationResponse("An error occurred", null, null, null)
		}

		return context
	}

	fun determineVideoToPlayFromPosition(context: AutomationContext): String
	{
		return context.root!!.trailingChildrenPhrase
	}

	fun determineVideoToPlayFromTypes(context: AutomationContext): String
	{
		var videoToPlay: String? = null
		videoToPlay = context.root!!.directObjectOrUnclassifiableDependentsString()

		if (videoToPlay == null)
		{
			videoToPlay = context.root!!.possessiveObjectsOrUnclassifiableDependentsString()
		}
		return videoToPlay!!
	}

	fun doesResultHaveItems(results: SearchListResponse?): Boolean
	{
		return results != null && results.items != null && results.items.size > 0
	}

	//	public void playFirstYouTubeVideoInBrowser(SearchListResponse results)
	//	{
	//		try
	//		{
	//			Desktop.getDesktop().browse(getVideoURL(results));
	//		}
	//		catch (IOException e)
	//		{
	//			e.printStackTrace();
	//		}
	//	}

	fun getVideoURL(results: SearchListResponse?): String
	{
		return "https://www.youtube.com/watch?v=" + results!!.items[0].id.videoId
	}

	fun getYoutubeSearchResults(queryTerm: String): SearchListResponse?
	{
		val youtube = constructYouTubeClient()

		val search = createYouTubeSearch(youtube) ?: return null

		setSearchParameters(queryTerm, search)

		return executeYouTubeSearch(search)
	}

	fun constructYouTubeClient(): YouTube
	{
		return YouTube.Builder(
				googleUtil!!.getHttpTransport()!!,
				googleUtil!!.getJsonFactory(),
				googleUtil!!.getNoOpHttpRequestInitializer())
				.setApplicationName("Tessa")
				.build()
	}

	fun executeYouTubeSearch(search: YouTube.Search.List): SearchListResponse?
	{
		try
		{
			return search.execute()
		} catch (e: IOException)
		{
			return null
		}

	}

	fun createYouTubeSearch(youtube: YouTube): YouTube.Search.List?
	{
		try
		{
			return youtube.search().list("id,snippet")
		} catch (e: IOException)
		{
			return null
		}

	}

	fun setSearchParameters(queryTerm: String, search: YouTube.Search.List)
	{
		search.key = googleUtil!!.apiKey
		search.q = queryTerm
		search.type = "video"
	}

}
