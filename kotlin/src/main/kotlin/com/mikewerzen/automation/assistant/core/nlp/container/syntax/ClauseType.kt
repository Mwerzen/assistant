package com.mikewerzen.automation.assistant.core.nlp.container.syntax

import com.mikewerzen.automation.assistant.core.nlp.container.syntax.ClauseCategory.*

enum class ClauseType private constructor(val supertype: ClauseCategory)
{
	YesNoIterrogative(Interrogative),
	AuxiliaryInterrogative(Interrogative),
	AlternativeInterrogative(Interrogative),
	WhInterrogative(Interrogative),
	Question(Interrogative),
	Exclamation(Exclamatory),
	ExclamatoryDeclaration(Exclamatory),
	Command(Imperative),
	Statement(Declarative),
	Unknown(ClauseCategory.Unknown);


	companion object
	{


		fun getClauseTypeForToken(root: Token?): ClauseType
		{

			if (isFirstTokenOfType(root, DependencyType.AuxiliaryVerb))
				return ClauseType.AuxiliaryInterrogative

			if (root != null && (issubjectBeforeRoot(root) || isFirstTokenOfType(root, DependencyType.Determiner) || isFirstTokenOfType(root, DependencyType.AuxiliaryVerb)))
				return ClauseType.WhInterrogative

			return if (root!!.subjects.isEmpty()) ClauseType.Command else ClauseType.Statement

		}

		fun isFirstTokenOfType(root: Token?, type: DependencyType): Boolean
		{
			return root!!.firstTokenInSubtree.dependencyType == type
		}


		fun issubjectBeforeRoot(root: Token): Boolean
		{
			return root.subjects != null && !root.subjects.isEmpty() && root.subjects[0].positionInSentence > root.positionInSentence
		}
	}
}
