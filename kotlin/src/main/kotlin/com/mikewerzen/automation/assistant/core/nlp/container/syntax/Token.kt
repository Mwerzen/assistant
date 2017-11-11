package com.mikewerzen.automation.assistant.core.nlp.container.syntax

import com.mikewerzen.automation.assistant.core.nlp.container.entity.TextSpan
import java.util.ArrayList
import java.util.Collections

import com.mikewerzen.automation.assistant.core.nlp.container.syntax.Label.*

class Token(text: TextSpan, partOfSpeech: PartOfSpeech, dependencyEdge: DependencyEdge,
			lemma: String) : Comparable<Token>
{
	var parent: Token? = null

	protected var children: MutableList<Token> = ArrayList()

	var text: TextSpan? = null
		internal set

	var partOfSpeech: PartOfSpeech? = null
		internal set

	var dependencyEdge: DependencyEdge? = null
		internal set

	var lemma: String? = null
		internal set

	init
	{
		this.text = text
		this.partOfSpeech = partOfSpeech
		this.dependencyEdge = dependencyEdge
		this.lemma = lemma
	}

	val isRoot: Boolean
		get() = parent == null && label == Label.Root

	val childrenPhrase: String
		get() = getChildrenPhrase(arrayOf(Label.Any), arrayOf(PartOfSpeechTag.ANY))

	fun getChildrenPhrase(types: Array<Label>): String
	{
		return getChildrenPhrase(types, arrayOf(PartOfSpeechTag.ANY))
	}

	fun getChildrenPhrase(tags: Array<PartOfSpeechTag>): String
	{
		return getChildrenPhrase(arrayOf(Label.Any), tags)
	}

	private fun getChildrenPhrase(types: Array<Label>, tags: Array<PartOfSpeechTag>): String
	{
		val result = StringBuilder()

		getLeadingChildrenPhrase(types, tags, result)

		result.append(text!!.content).append(" ")

		getTrailingChildrenPhrase(types, tags, result)

		return result.toString().trim { it <= ' ' }
	}

	val leadingChildrenPhrase: String
		get()
		{
			val result = StringBuilder()
			getLeadingChildrenPhrase(arrayOf(Label.Any), arrayOf(PartOfSpeechTag.ANY), result)
			return result.toString()
		}

	private fun getLeadingChildrenPhrase(types: Array<Label>, tags: Array<PartOfSpeechTag>, result: StringBuilder)
	{
		children
				.filter { it.text!!.beginOffset!! < text!!.beginOffset!! && it.label!!.isInArray(*types) && it.partOfSpeech!!.tag!!.isInArray(*tags) }
				.forEach { result.append(it.getChildrenPhrase(types, tags)).append(" ") }
	}

	val trailingChildrenPhrase: String
		get()
		{
			val result = StringBuilder()
			getTrailingChildrenPhrase(arrayOf(Label.Any), arrayOf(PartOfSpeechTag.ANY), result)
			return result.toString()
		}

	private fun getTrailingChildrenPhrase(types: Array<Label>, tags: Array<PartOfSpeechTag>, result: StringBuilder)
	{
		children
				.filter { it.text!!.beginOffset!! > text!!.beginOffset!! && it.label!!.isInArray(*types) && it.partOfSpeech!!.tag!!.isInArray(*tags) }
				.forEach { result.append(it.getChildrenPhrase(types, tags)).append(" ") }
	}

	val isSubjectBeforeVerb: Boolean
		get() = isOtherTypeBefore(NominalSubject, NominalSubjectInPassiveConstruction)

	fun isOtherTypeBefore(vararg types: Label): Boolean
	{
		return directChildren.any { it.text!!.beginOffset!! < text!!.beginOffset!! && it.label!!.isInArray(*types) }
	}

	val subjects: List<Token>
		get() = getChildDependenciesOfTypeRecursive(NominalSubject, NominalSubjectInPassiveConstruction)

	val compliments: List<Token>
		get() = getChildDependenciesOfType(NonfiniteClauselikeComplement, NominalPredicativeComplement, ClausalComplement)

	val directObjects: List<Token>
		get() = getChildDependenciesOfTypeRecursive(DirectObject)

	val unclassifiableDependents: List<Token>
		get() = getChildDependenciesOfType(UnclassifiableDependent)

	val directObjectOrUnclassifiableDependents: List<Token>
		get() = getChildDependenciesOfTypeRecursive(DirectObject, UnclassifiableDependent)

	fun directObjectOrUnclassifiableDependentsString(): String
	{
		var res = ""
		directObjectOrUnclassifiableDependents.forEach { res += it.childrenPhrase.trim() + " "}
		return res
	}

	val indirectObjects: List<Token>
		get() = getChildDependenciesOfTypeRecursive(IndirectObject)

	val negations: List<Token>
		get() = getChildDependenciesOfTypeRecursive(Negation)

	val auxiliaryVerbs: List<Token>
		get() = getChildDependenciesOfTypeRecursive(AuxiliaryVerb)

	val prepositions: List<Token>
		get() = getChildDependenciesOfTypeRecursive(Preposition)

	val possessiveObjects: List<Token>
		get() = getChildDependenciesOfTypeRecursive(PossessiveObject)

	fun possessiveObjectsOrUnclassifiableDependentsString(): String
	{
		var res = ""
		getChildDependenciesOfTypeRecursive(PossessiveObject, UnclassifiableDependent).forEach { res += it.childrenPhrase.trim() + " "}
		return res
	}

	val timeModifiers: List<Token>
		get() = getChildDependenciesOfTypeRecursive(TimeModifier)

	val modifiers: List<Token>
		get()
		{
			val modifiers = children.filter { it.partOfSpeech!!.tag!!.isInArray(PartOfSpeechTag.ADJECTIVE, PartOfSpeechTag.ADVERB) }

			return modifiers
		}

	private fun getChildDependenciesOfTypeRecursive(vararg types: Label): List<Token>
	{
		return getChildDependenciesOfTypeRecursiveWithFollowLinks(types, arrayOf(Preposition, NonfiniteClauselikeComplement))
	}

	private fun getChildDependenciesOfTypeRecursiveWithFollowLinks(types: Array<out Label>, links: Array<Label>): List<Token>
	{
		val childrenOfSearchType = ArrayList<Token>()

		childrenOfSearchType.addAll(getChildDependenciesOfType(*types))

		val childrenOfLinkTypes = getChildDependenciesOfType(*links)

		for (token in childrenOfLinkTypes)
			childrenOfSearchType.addAll(token.getChildDependenciesOfTypeRecursiveWithFollowLinks(types, links))

		return childrenOfSearchType

	}

	private fun getChildDependenciesOfType(vararg types: Label): List<Token>
	{
		val childrenOfType = ArrayList<Token>()
		for (token in children)
		{
			types.filter { it == token.label }.forEach { childrenOfType.add(token) }
		}

		return childrenOfType
	}

	val firstTokenInSubtree: Token
		get() = getFirstTokenInSubtree(this)

	private fun getFirstTokenInSubtree(root: Token): Token
	{
		var first = root
		for (child in root.children)
		{
			if (first.text!!.beginOffset!! == 0)
				return first

			val lowestInSubtree = getFirstTokenInSubtree(child)

			if (lowestInSubtree.text!!.beginOffset!! < first.text!!.beginOffset!!)
				first = lowestInSubtree
		}

		return first
	}

	val label: Label?
		get() = dependencyEdge!!.label

	fun addChild(child: Token)
	{
		children.add(child)
	}

	val directChildren: List<Token>
		get() = children

	val allTokensInTree: List<Token>
		get()
		{
			val tree = allChildren
			tree.add(this)
			return tree
		}

	val allChildren: MutableList<Token>
		get()
		{
			val tokens = ArrayList<Token>()

			for (child in directChildren)
			{
				tokens.add(child)
				tokens.addAll(child.allChildren)
			}

			return tokens
		}

	val allChildrenSorted: List<Token>
		get()
		{
			val tokens = allChildren
			Collections.sort(tokens)
			return tokens
		}

	val word: String?
		get() = text!!.content

	override fun compareTo(other: Token): Int
	{
		return when
		{
			other.text!!.beginOffset!! < this.text!!.beginOffset!! -> 1
			other.text!!.beginOffset!! == this.text!!.beginOffset!! -> 0
			else -> -1
		}
	}

	override fun toString(): String
	{
		return "Token(children=$children, text=$text, partOfSpeech=$partOfSpeech, dependencyEdge=$dependencyEdge, lemma=$lemma)"
	}


}
