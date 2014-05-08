package org.swrlapi.core.arguments.impl;

import java.net.URI;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.OWLLiteralFactory;
import org.swrlapi.core.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.xsd.XSDDate;
import org.swrlapi.xsd.XSDDateTime;
import org.swrlapi.xsd.XSDDuration;
import org.swrlapi.xsd.XSDTime;

public class DefaultSWRLBuiltInArgumentFactory implements SWRLBuiltInArgumentFactory
{
	private final OWLIRIResolver owlIRIResolver;
	private final OWLLiteralFactory owlLiteralFactory;

	public DefaultSWRLBuiltInArgumentFactory(OWLIRIResolver owlIRIResolver, OWLLiteralFactory owlLiteralFactory)
	{
		this.owlIRIResolver = owlIRIResolver;
		this.owlLiteralFactory = owlLiteralFactory;
	}

	@Override
	public SWRLVariableBuiltInArgument getUnboundVariableBuiltInArgument(IRI variableIRI)
	{
		String variablePrefixedName = getOWLIRIResolver().iri2PrefixedName(variableIRI);
		SWRLVariableBuiltInArgument argument = new SWRLVariableBuiltInArgumentImpl(variableIRI, variablePrefixedName);
		argument.setUnbound();
		return argument;
	}

	@Override
	public SWRLVariableBuiltInArgument getVariableBuiltInArgument(IRI variableIRI)
	{
		String variablePrefixedName = getOWLIRIResolver().iri2PrefixedName(variableIRI);
		return new SWRLVariableBuiltInArgumentImpl(variableIRI, variablePrefixedName);
	}

	@Override
	public SWRLClassBuiltInArgument getClassBuiltInArgument(OWLClass cls)
	{
		return new SWRLClassBuiltInArgumentImpl(cls);
	}

	@Override
	public SWRLObjectPropertyBuiltInArgument getObjectPropertyBuiltInArgument(OWLObjectProperty property)
	{
		return new SWRLObjectPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLDataPropertyBuiltInArgument getDataPropertyBuiltInArgument(OWLDataProperty property)
	{
		return new SWRLDataPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLAnnotationPropertyBuiltInArgument getAnnotationPropertyBuiltInArgument(OWLAnnotationProperty property)
	{
		return new SWRLAnnotationPropertyBuiltInArgumentImpl(property);
	}

	@Override
	public SWRLDatatypeBuiltInArgument getDatatypeBuiltInArgument(OWLDatatype datatype)
	{
		return new SWRLDatatypeBuiltInArgumentImpl(datatype);
	}

	@Override
	public SWRLNamedIndividualBuiltInArgument getNamedIndividualBuiltInArgument(OWLNamedIndividual individual)
	{
		return new SWRLNamedIndividualBuiltInArgumentImpl(individual);
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(OWLLiteral literal)
	{
		return new SWRLLiteralBuiltInArgumentImpl(literal);
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(String s)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(s));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(boolean b)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(int i)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(i));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(long l)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(l));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(float f)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(f));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(double d)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(d));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(byte b)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(b));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(URI uri)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(uri));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDate date)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(date));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDTime time)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(time));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDateTime datetime)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(datetime));
	}

	@Override
	public SWRLLiteralBuiltInArgument getLiteralBuiltInArgument(XSDDuration duration)
	{
		return new SWRLLiteralBuiltInArgumentImpl(getOWLLiteralFactory().getOWLLiteral(duration));
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI)
	{
		String variablePrefixedName = getOWLIRIResolver().iri2PrefixedName(variableIRI);

		return new SWRLMultiValueVariableBuiltInArgumentImpl(variableIRI, variablePrefixedName);
	}

	@Override
	public SWRLMultiValueVariableBuiltInArgument getMultiValueVariableBuiltInArgument(IRI variableIRI,
			List<SWRLBuiltInArgument> arguments)
	{
		String variablePrefixedName = getOWLIRIResolver().iri2PrefixedName(variableIRI);

		return new SWRLMultiValueVariableBuiltInArgumentImpl(variableIRI, variablePrefixedName, arguments);
	}

	@Override
	public SQWRLCollectionVariableBuiltInArgument getSQWRLCollectionVariableBuiltInArgument(IRI variableIRI,
			String queryName, String collectionName, String collectionGroupID)
	{
		String variablePrefixedName = getOWLIRIResolver().iri2PrefixedName(variableIRI);

		return new SQWRLCollectionVariableBuiltInArgumentImpl(variableIRI, variablePrefixedName, queryName, collectionName,
				collectionGroupID);
	}

	private OWLLiteralFactory getOWLLiteralFactory()
	{
		return this.owlLiteralFactory;
	}

	private OWLIRIResolver getOWLIRIResolver()
	{
		return this.owlIRIResolver;
	}
}
