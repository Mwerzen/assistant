package com.mikewerzen.automation.assistant.core.nlp.container.syntax

import java.util.ArrayList
import java.util.Collections

import com.mikewerzen.automation.assistant.core.nlp.container.syntax.DependencyType.*

class Token(index: Int, text: Text, partOfSpeech: PartOfSpeech, dependencyEdge: DependencyEdge,
			lemma: Lemma) : Comparable<Token>
{
	var positionInSentence: Int = 0
		internal set

	var parent: Token? = null

	protected var children: MutableList<Token> = ArrayList()

	var text: Text? = null
		internal set

	var partOfSpeech: PartOfSpeech? = null
		internal set

	var dependencyEdge: DependencyEdge? = null
		internal set

	var lemma: Lemma? = null
		internal set

	init
	{
		this.positionInSentence = index
		this.text = text
		this.partOfSpeech = partOfSpeech
		this.dependencyEdge = dependencyEdge
		this.lemma = lemma
	}

	val isRoot: Boolean
		get() = parent == null && dependencyType == DependencyType.Root

	val childrenPhrase: String
		get() = getChildrenPhrase(arrayOf(DependencyType.Any), arrayOf(PartOfSpeechTag.Any))

	fun getChildrenPhrase(types: Array<DependencyType>): String
	{
		return getChildrenPhrase(types, arrayOf(PartOfSpeechTag.Any))
	}

	fun getChildrenPhrase(tags: Array<PartOfSpeechTag>): String
	{
		return getChildrenPhrase(arrayOf(DependencyType.Any), tags)
	}

	private fun getChildrenPhrase(types: Array<DependencyType>, tags: Array<PartOfSpeechTag>): String
	{
		val result = StringBuilder()

		getLeadingChildrenPhrase(types, tags, result)

		result.append(text!!.word).append(" ")

		getTrailingChildrenPhrase(types, tags, result)

		return result.toString().trim { it <= ' ' }
	}

	val leadingChildrenPhrase: String
		get()
		{
			val result = StringBuilder()
			getLeadingChildrenPhrase(arrayOf(DependencyType.Any), arrayOf(PartOfSpeechTag.Any), result)
			return result.toString()
		}

	private fun getLeadingChildrenPhrase(types: Array<DependencyType>, tags: Array<PartOfSpeechTag>, result: StringBuilder)
	{
		children
				.filter { it.positionInSentence < positionInSentence && it.dependencyType!!.isInArray(*types) && it.partOfSpeech!!.tag!!.isInArray(*tags) }
				.forEach { result.append(it.getChildrenPhrase(types, tags)).append(" ") }
	}

	val trailingChildrenPhrase: String
		get()
		{
			val result = StringBuilder()
			getTrailingChildrenPhrase(arrayOf(DependencyType.Any), arrayOf(PartOfSpeechTag.Any), result)
			return result.toString()
		}

	private fun getTrailingChildrenPhrase(types: Array<DependencyType>, tags: Array<PartOfSpeechTag>, result: StringBuilder)
	{
		children
				.filter { it.positionInSentence > positionInSentence && it.dependencyType!!.isInArray(*types) && it.partOfSpeech!!.tag!!.isInArray(*tags) }
				.forEach { result.append(it.getChildrenPhrase(types, tags)).append(" ") }
	}

	val isSubjectBeforeVerb: Boolean
		get() = isOtherTypeBefore(NominalSubject, NominalSubjectInPassiveConstruction)

	fun isOtherTypeBefore(vararg types: DependencyType): Boolean
	{
		return directChildren.any { it.positionInSentence < positionInSentence && it.dependencyType!!.isInArray(*types) }
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

	val timeModifiers: List<Token>
		get() = getChildDependenciesOfTypeRecursive(TimeModifier)

	val modifiers: List<Token>
		get()
		{
			val modifiers = children.filter { it.partOfSpeech!!.tag!!.isInArray(PartOfSpeechTag.Adjective, PartOfSpeechTag.Adverb) }

			return modifiers
		}

	private fun getChildDependenciesOfTypeRecursive(vararg types: DependencyType): List<Token>
	{
		return getChildDependenciesOfTypeRecursiveWithFollowLinks(types, arrayOf(Preposition, NonfiniteClauselikeComplement))
	}

	private fun getChildDependenciesOfTypeRecursiveWithFollowLinks(types: Array<out DependencyType>, links: Array<DependencyType>): List<Token>
	{
		val childrenOfSearchType = ArrayList<Token>()

		childrenOfSearchType.addAll(getChildDependenciesOfType(*types))

		val childrenOfLinkTypes = getChildDependenciesOfType(*links)

		for (token in childrenOfLinkTypes)
			childrenOfSearchType.addAll(token.getChildDependenciesOfTypeRecursiveWithFollowLinks(types, links))

		return childrenOfSearchType

	}

	private fun getChildDependenciesOfType(vararg types: DependencyType): List<Token>
	{
		val childrenOfType = ArrayList<Token>()
		for (token in children)
		{
			types.filter { it == token.dependencyType }.forEach { childrenOfType.add(token) }
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
			if (first.positionInSentence == 0)
				return first

			val lowestInSubtree = getFirstTokenInSubtree(child)

			if (lowestInSubtree.positionInSentence < first.positionInSentence)
				first = lowestInSubtree
		}

		return first
	}

	val dependencyType: DependencyType?
		get() = dependencyEdge!!.dependencyType

	override fun toString(): String
	{
		return "Token:\n" +
				"   Index=" + positionInSentence + "\n" +
				"   ParentExists=" + (parent != null) + "\n" +
				"   Text=" + text + "\n" +
				"   PartOfSpeech=" + partOfSpeech + "\n" +
				"   DependencyEdge=" + dependencyEdge + "\n" +
				"   Lemma=" + lemma
	}

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
		get() = text!!.word

	override fun compareTo(other: Token): Int
	{
		return when
		{
			other.positionInSentence < this.positionInSentence -> 1
			other.positionInSentence == this.positionInSentence -> 0
			else -> -1
		}
	}

}
