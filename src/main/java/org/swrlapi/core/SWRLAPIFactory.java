package org.swrlapi.core;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.ext.impl.DefaultSWRLAPIOWLOntology;

public class SWRLAPIFactory
{
	public static SWRLAPIOWLOntology createSWRLAPIOWLOntology(OWLOntologyManager ontologyManager,
			OWLOntology owlOntology, DefaultPrefixManager prefixManager)
	{
		return new DefaultSWRLAPIOWLOntology(ontologyManager, owlOntology, prefixManager);
	}

	public static SWRLRuleEngineFactory createSWRLRuleEngineFactory()
	{
		return new DefaultSWRLRuleEngineFactory();
	}
}
