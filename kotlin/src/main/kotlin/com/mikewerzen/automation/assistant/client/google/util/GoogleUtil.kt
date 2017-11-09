package com.mikewerzen.automation.assistant.client.google.util

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.gax.core.CredentialsProvider
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.util.*


@Component
class GoogleUtil
{
	@Value("\${google.client.key}")
	private val clientKeyPath: String? = null

	@Value("\${google.sheets.application.name}")
	val applicationName: String? = null


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

}