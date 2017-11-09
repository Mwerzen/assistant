package com.mikewerzen.automation.assistant.core.nlp.container.syntax

enum class Label private constructor(private val tag: String)
{
	Any("*"),
	Root("ROOT"),
	Abbreviation("ABBREV"),
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
	GoesWith("GOESWITH"),
	InfinitivalModifier("INFMOD"),
	IndirectObject("IOBJ"),
	SubordinatingConjunctionOrEquivelent("MARK"),
	MultiwordExpression("MWE"),
	MultiwordVerbalExpression("MWV"),
	NamedNoun("NN"),
	Negation("NEG"),
	NominalModifier("NMOD"),
	NominalPAdjectiveModifier("NPADVMOD"),
	NominalSubject("NSUBJ"),
	NominalSubjectInPassiveConstruction("NSUBJPASS"),
	Numeral("NUM"),
	ElementCompoundModifier("NUMBER"),
	Punctional("P"),
	ComplementOfPrepositionInClause("PCOMP"),
	Preposition("PREP"),
	ClauselikeStructureDependentOnPrecedingClause("PARATAXIS"),
	ParticipialModifier("PARTMOD"),
	PossessiveModifier("POSS"),
	PostverbalNegativeParticle("POSTNEG"),
	PossessiveObject("POBJ"),
	PredicateComplement("PRECOMP"),
	Preconjunt("PRECONJ"),
	Predeterminer("PREDET"),
	Prefix("PREF"),
	RelationshipVerbVerbalMorpheme("PRONL"),
	PossessiveMarker("PS"),
	VerbParticle("PRT"),
	QuantifierModifier("QUANTMOD"),
	RelativeClauseModifier("RCMOD"),
	RelativePronounWithUnidentifiableFunction("REL"),
	TimeModifier("TMOD"),
	VerbalModifier("VMOD"),
	NonfiniteClauselikeComplement("XCOMP"),
	RCMODREL("RCMODREL"),
	RDROP("RDROP"),
	REF("REF"),
	REMNANT("REMNANT"),
	REPARANDUM("REPARANDUM"),
	ROOT("ROOT"),
	SNUM("SNUM"),
	SUFF("SUFF"),
	TMOD("TMOD"),
	TOPIC("TOPIC"),
	VMOD("VMOD"),
	VOCATIVE("VOCATIVE"),
	XCOMP("XCOMP"),
	SUFFIX("SUFFIX"),
	TITLE("TITLE"),
	ADVPHMOD("ADVPHMOD"),
	AUXCAUS("AUXCAUS"),
	AUXVV("AUXVV"),
	DTMOD("DTMOD"),
	FOREIGN("FOREIGN"),
	KW("KW"),
	LIST("LIST"),
	NOMC("NOMC"),
	NOMCSUBJ("NOMCSUBJ"),
	NOMCSUBJPASS("NOMCSUBJPASS"),
	NUMC("NUMC"),
	COP("COP"),
	DISLOCATED("DISLOCATED"),
	ASP("ASP"),
	GMOD("GMOD"),
	GOBJ("GOBJ"),
	INFMOD("INFMOD"),
	MES("MES"),
	NCOMP("NCOMP");

	fun isEquivelentTo(other: Label): Boolean
	{
		return this == other || this == Any || other == Any
	}

	fun isInArray(vararg types: Label): Boolean
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

		fun getTypeForTag(tag: String): Label
		{
			for (existingDependencyType in Label.values())
			{
				if (existingDependencyType.tag == tag.toUpperCase())
					return existingDependencyType
			}

			throw RuntimeException("Could not determine matching Label for tag: " + tag)
		}
	}
}
