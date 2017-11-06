package com.mikewerzen.automation.assistant.client.google.sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.sheets.v4.SheetsScopes
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.IOException
import java.util.*

@Service
class GoogleSecurityUtil
{
	@Value("\${google.client.key}")
	private val clientKeyPath: String? = null

	private val SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY)

	fun authorize(): Credential
	{
		return GoogleCredential.fromStream(FileInputStream(clientKeyPath)).createScoped(SCOPES)
	}
}