package com.mikewerzen.automation.assistant.core

import com.mikewerzen.automation.assistant.client.restaction.RestActionClient
import com.mikewerzen.automation.assistant.core.nlp.NaturalLanguageProcessor
import com.mikewerzen.automation.assistant.endpoint.AutomationRequest
import com.mikewerzen.automation.assistant.endpoint.AutomationResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AutomationCore
{
	private val logger = LoggerFactory.getLogger(this.javaClass)

	@Autowired
	private var nlp : NaturalLanguageProcessor? = null

	fun processRequest(request: AutomationRequest): AutomationResponse
	{
		var context = AutomationContext(request)

		/*		::Parse Natural Language::

		This hits the Google API to parse out the Entities (Objects), Sentiment, and Syntax of a sentence.
		These responses are then mapped from the google response into the automation project's containers.
		This adds the Entities list, Root Token, and Sentiment value into the context.	 */
		nlp!!.analyze(context)

		/*		::Parse Awareness::

		Gather the set of interactors in this request; chiefly trying to determine if the target of the
		sentence is the automation engine itself by using the syntactic analysis from before. This also may
		infact allow the automation engine to determine if there are others in the room.	 */

		/*		::Make Action Decision::

		Uses the NLP analysis and Awareness Analysis to build out a prototype "rule", detailing things of
		the form:
			Rule playMusic = new Rule(PlayMusic).addField(Subject, Automation).addField(VERB, "play").addField(DirectObject, "song");
		These rules are then matched against a list of existing rules to determine which action to take.		*/

		//Execute Action

		//Persist Result into DB

		//Modify Automation State if Necessary (started game, etc)

		//Generate Response


		logger.info(context.toString())

		return AutomationResponse("Hello");
	}


}