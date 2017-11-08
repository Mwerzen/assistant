package com.mikewerzen.automation.assistant.core.nlp.container.syntax

enum class DependencyType private constructor(private val tag: String)
{
	Any("*"),
	Root("ROOT"),
	AdjectivalComplement("ACOMP"),
	Adposition("ADP"),
	ClausalComplementOfAdposition("ADPCOMP"),
	AdpositionalModifier("ADPMOD"),
	NominalComplement("ADPOBJ"),
	AdverbialClauseModifier("ADVCL"),
	AdverbialModifier("ADVMOD"),
	AdjectivalModifier("AMOD"),
	AppositionalModifier("APPOS"),
	NominalPredicativeComplement("ATTR"),
	AuxiliaryVerb("AUX"),
	AuxiliaryVerbInPassiveConstruction("AUXPASS"),
	CoordinationConjunction("CC"),
	ClausalComplement("CCOMP"),
	CompoundModifier("COMPMOD"),
	Conjunct("CONJ"),
	CopulaVerb("COP"),
	ClausalSubject("CSUBJ"),
	ClausalSubjectInPassiveConstruction("CSUBJPASS"),
	UnclassifiableDependent("DEP"),
	Determiner("DET"),
	Discourse("DISCOURSE"),
	DirectObject("DOBJ"),
	ExpletiveSubject("EXPL"),
	InfinitivalModifier("INFMOD"),
	IndirectObject("IOBJ"),
	SubordinatingConjunctionOrEquivelent("MARK"),
	MultiwordExpression("MWE"),
	NamedNoun("NN"),
	Negation("NEG"),
	NominalModifier("NMOD"),
	NominalPAdjectiveModifier("NPADVMOD"),
	NominalSubject("NSUBJ"),
	NominalSubjectInPassiveConstruction("NSUBJPASS"),
	Numeral("NUM"),
	Punctional("P"),
	Preposition("PREP"),
	ClauselikeStructureDependentOnPrecedingClause("PARATAXIS"),
	ParticipialModifier("PARTMOD"),
	PossessiveModifier("POSS"),
	PossessiveObject("POBJ"),
	WHATISPS("PS"),
	VerbParticle("PRT"),
	RelativeClauseModifier("RCMOD"),
	RelativePronounWithUnidentifiableFunction("REL"),
	TimeModifier("TMOD"),
	VerbalModifier("VMOD"),
	NonfiniteClauselikeComplement("XCOMP");

	fun isEquivelentTo(other: DependencyType): Boolean
	{
		return this == other || this == Any || other == Any
	}

	fun isInArray(vararg types: DependencyType): Boolean
	{
		for (type in types)
		{
			if (type.isEquivelentTo(this))
				return true
		}

		return false
	}

	companion object
	{

		fun getTypeForTag(tag: String): DependencyType
		{
			for (existingDependencyType in DependencyType.values())
			{
				if (existingDependencyType.tag == tag.toUpperCase())
					return existingDependencyType
			}

			throw RuntimeException("Could not determine matching DependencyType for tag: " + tag)
		}
	}
}
