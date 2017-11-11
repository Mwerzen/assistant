package com.mikewerzen.automation.assistant.client.google.util

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.gax.core.CredentialsProvider
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.io.IOException
import java.security.GeneralSecurityException
import java.util.*


@Component
class GoogleUtil
{
	@Value("\${google.client.key}")
	private val clientKeyPath: String? = null

	@Value("\${google.sheets.application.name}")
	val applicationName: String? = null

	@Value("\${google.api.key}")
	val apiKey: String? = null

	fun getScopes(): Collection<String>
	{
		val scopes = ArrayList<String>()
		scopes.add(SheetsScopes.SPREADSHEETS_READONLY)
		//scopes.addAll(CloudNaturalLanguageAPIScopes.all())
		return scopes
	}


	fun getGoogleCredential(): GoogleCredential
	{
		return GoogleCredential.fromStream(FileInputStream(clientKeyPath)).createScoped(getScopes())
	}

	fun getGoogleAppDefaultCredentialsProvider(): CredentialsProvider
	{
		return FixedCredentialsProvider.create(GoogleCredentials.getApplicationDefault())
	}

	fun getHttpTransport(): HttpTransport?
	{
		try
		{
			return GoogleNetHttpTransport.newTrustedTransport()
		} catch (e: GeneralSecurityException)
		{
			// TODO Auto-generated catch block
			e.printStackTrace()
			return null
		} catch (e: IOException)
		{
			e.printStackTrace()
			return null
		}

	}

	fun getJsonFactory(): JsonFactory
	{
		return JacksonFactory.getDefaultInstance()
	}

	fun getNoOpHttpRequestInitializer(): HttpRequestInitializer
	{
		return object : HttpRequestInitializer
		{
			override fun initialize(req: HttpRequest?)
			{
			}

		}
	}

}