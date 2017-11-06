package com.mikewerzen.automation.assistant.client.google.sheets;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SheetsClient
{
	@Autowired
	private GoogleSecurityUtil googleSecurityUtil;

	private final JsonFactory JSON_FACTORY;

	private HttpTransport HTTP_TRANSPORT;

	@Value("${google.sheets.application.name}")
	private String applicationName;

	@Value("${google.sheets.spreadhseet.id}")
	private String spreadsheetId;


	public SheetsClient()
	{
		JSON_FACTORY = JacksonFactory.getDefaultInstance();

		try
		{
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<List<Object>> getDataInSheetRange(String sheetName, String range)
	{
		try
		{
			Sheets service = getSheetsService();

			String queryRange = sheetName + "!" + range;

			ValueRange response = service.spreadsheets().values()
					.get(spreadsheetId, queryRange)
					.execute();

			return response.getValues();

		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private Sheets getSheetsService() throws IOException
	{
		Credential credential = googleSecurityUtil.authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(applicationName)
				.build();
	}
}
