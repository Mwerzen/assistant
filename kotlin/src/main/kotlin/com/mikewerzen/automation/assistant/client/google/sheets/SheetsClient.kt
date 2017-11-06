package com.mikewerzen.automation.assistant.client.google.sheets


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class SheetsClient
{
	@Autowired
	private val googleSecurityUtil: GoogleSecurityUtil? = null

	private val JSON_FACTORY: JsonFactory

	private var HTTP_TRANSPORT: HttpTransport? = null

	@Value("\${google.sheets.application.name}")
	private val applicationName: String? = null

	@Value("\${google.sheets.spreadhseet.id}")
	private val spreadsheetId: String? = null

	init
	{
		JSON_FACTORY = JacksonFactory.getDefaultInstance()

		try
		{
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
		} catch (e: Exception)
		{
			throw RuntimeException(e)
		}

	}

	fun getDataInSheetRange(sheetName: String, range: String): List<List<Any>>
	{
		try
		{
			val service = sheetsService

			val queryRange = sheetName + "!" + range

			val response = service.spreadsheets().values()
					.get(spreadsheetId!!, queryRange)
					.execute()

			return response.getValues()

		} catch (e: Exception)
		{
			throw RuntimeException(e)
		}

	}

	private val sheetsService: Sheets
		@Throws(IOException::class)
		get()
		{
			val credential = googleSecurityUtil!!.authorize()
			return Sheets.Builder(HTTP_TRANSPORT!!, JSON_FACTORY, credential)
					.setApplicationName(applicationName)
					.build()
		}
}
