package com.mikewerzen.automation.assistant.client.google.nlp


import com.google.cloud.language.v1.*
import com.mikewerzen.automation.assistant.client.google.util.GoogleUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class GoogleNLPClient
{

	@Autowired
	private val googleUtil: GoogleUtil? = null

	private var client: LanguageServiceClient? = null

	fun getLanguageAPI(): LanguageServiceClient
	{
		if (client == null)
			client = initCloudNaturalLanguageAPI();

		return client!!
	}

	private fun GoogleNLPClient.initCloudNaturalLanguageAPI(): LanguageServiceClient
	{
		val languageServiceSettings = LanguageServiceSettings.newBuilder()
				.setCredentialsProvider(googleUtil!!.getGoogleAppDefaultCredentialsProvider())
				.build()
		return LanguageServiceClient.create(languageServiceSettings)
	}

	fun analyzeEntities(text: String): List<Entity>
	{
		val doc = Document.newBuilder()
				.setContent(text)
				.setType(Document.Type.PLAIN_TEXT)
				.build()

		val res = getLanguageAPI().analyzeEntities(doc, EncodingType.UTF16)


		return res.entitiesList
	}

	fun analyzeSentiment(text: String): Sentiment
	{
		val doc = Document.newBuilder()
				.setContent(text)
				.setType(Document.Type.PLAIN_TEXT)
				.build()

		val res = getLanguageAPI().analyzeSentiment(doc)


		return res.documentSentiment
	}

	fun analyzeSyntax(text: String): List<Token>
	{
		val doc = Document.newBuilder()
				.setContent(text)
				.setType(Document.Type.PLAIN_TEXT)
				.build()

		val res = getLanguageAPI().analyzeSyntax(doc, EncodingType.UTF16)

		return res.tokensList
	}

	fun analyzeEntitiesSentimentAndSyntax(text: String): AnnotateTextResponse
	{
		val doc = Document.newBuilder()
				.setContent(text)
				.setType(Document.Type.PLAIN_TEXT)
				.build()

		val features = AnnotateTextRequest.Features.newBuilder()
				.setExtractEntities(true)
				.setExtractSyntax(true)
				.setExtractDocumentSentiment(true)
				.build()

		val request = AnnotateTextRequest.newBuilder()
				.setDocument(doc)
				.setFeatures(features)
				.setEncodingType(EncodingType.UTF16)
				.build();

		return getLanguageAPI().annotateText(request);
	}

}
