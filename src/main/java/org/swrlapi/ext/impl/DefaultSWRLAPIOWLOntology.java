package org.swrlapi.ext.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.core.DefaultSWRLAPIOntologyProcessor;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.core.SWRLAPIOntologyProcessor;
import org.swrlapi.core.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgumentFactory;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.swrlapi.ext.SWRLAPIOWLDataFactory;
import org.swrlapi.ext.SWRLAPIOWLOntology;
import org.swrlapi.ext.SWRLAPIRule;

public class DefaultSWRLAPIOWLOntology implements SWRLAPIOWLOntology
{
	private final OWLOntologyManager ontologyManager;
	private final OWLOntology owlOntology;
	private final DefaultPrefixManager prefixManager;
	private final OWLIRIResolver owlIRIResolver;
	private final SWRLAPIOWLDataFactory swrlapiOWLDataFactory;
	private final SWRLAPIOntologyProcessor swrlapiOntologyProcessor;

	public DefaultSWRLAPIOWLOntology(OWLOntologyManager ontologyManager, OWLOntology owlOntology,
			DefaultPrefixManager prefixManager)
	{
		this.ontologyManager = ontologyManager;
		this.owlOntology = owlOntology;
		this.prefixManager = prefixManager;
		this.owlIRIResolver = new OWLIRIResolver(this.prefixManager);
		this.swrlapiOWLDataFactory = new DefaultSWRLAPIOWLDataFactory(owlIRIResolver);
		this.swrlapiOntologyProcessor = new DefaultSWRLAPIOntologyProcessor(this);
	}

	@Override
	public OWLOntology getOWLOntology()
	{
		return this.owlOntology;
	}

	@Override
	public Set<SWRLAPIRule> getSWRLAPIRules()
	{
		Set<SWRLAPIRule> swrlapiRules = new HashSet<SWRLAPIRule>();

		for (SWRLRule owlapiRule : getOWLOntology().getAxioms(AxiomType.SWRL_RULE)) {
			SWRLAPIRule swrlapiRule = convertOWLAPIRule2SWRLAPIRule(owlapiRule);
			swrlapiRules.add(swrlapiRule);
		}

		return swrlapiRules;
	}

	@Override
	public SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory()
	{
		return this.swrlapiOWLDataFactory;
	}

	@Override
	public OWLDataFactory getOWLDataFactory()
	{
		return ontologyManager.getOWLDataFactory();
	}

	@Override
	public SWRLAPIOntologyProcessor getSWRLAPIOntologyProcessor()
	{
		return this.swrlapiOntologyProcessor;
	}

	@Override
	public OWLIRIResolver getOWLIRIResolver()
	{
		return getSWRLAPIOWLDataFactory().getOWLIRIResolver();
	}

	@Override
	public void startBulkConversion()
	{
		// TODO
	}

	@Override
	public void completeBulkConversion()
	{
		// TODO
	}

	@Override
	public boolean hasOntologyChanged()
	{
		return false; // TODO
	}

	@Override
	public void resetOntologyChanged()
	{
		// TODO
	}

	// TODO We really do not want the following three methods here. They are convenience methods only and are used only by
	// a the temporal built-in library.
	@Override
	public boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI)
	{
		throw new RuntimeException("Not implemented");
	}

	private String getRuleName(SWRLRule owlapiRule)
	{
		String ruleName = UUID.randomUUID().toString(); // TODO Get rule name from annotation property if there.

		// OWLAnnotationProperty labelAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
		// OWLRDFVocabulary.RDFS_LABEL.getIRI());

		return ruleName;
	}

	private boolean getActive(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty enabledAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
				IRI.create("http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled"));

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(enabledAnnotation)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				if (literal.isBoolean())
					return literal.parseBoolean();
			}
		}
		return true;
	}

	private String getComment(SWRLRule owlapiRule)
	{
		OWLAnnotationProperty commentAnnotation = getOWLDataFactory().getOWLAnnotationProperty(
				OWLRDFVocabulary.RDFS_COMMENT.getIRI());

		for (OWLAnnotation annotation : owlapiRule.getAnnotations(commentAnnotation)) {
			if (annotation.getValue() instanceof OWLLiteral) {
				OWLLiteral literal = (OWLLiteral)annotation.getValue();
				return literal.getLiteral(); // TODO We just pick one for the moment
			}
		}
		return "";
	}

	/**
	 * We basically take an OWLAPI {@link SWRLRule} object and for every OWLAPI {@link SWRLBuiltInAtom} in it we create a
	 * SWRLAPI {@link SWRLAPIBuiltInAtom}; all other atoms remain the same.
	 */
	private SWRLAPIRule convertOWLAPIRule2SWRLAPIRule(SWRLRule owlapiRule)
	{
		String ruleName = getRuleName(owlapiRule);
		boolean active = getActive(owlapiRule);
		String comment = getComment(owlapiRule);
		List<SWRLAtom> owlapiBodyAtoms = new ArrayList<SWRLAtom>(owlapiRule.getBody());
		List<SWRLAtom> owlapiHeadAtoms = new ArrayList<SWRLAtom>(owlapiRule.getHead());
		List<SWRLAtom> swrlapiBodyAtoms = new ArrayList<SWRLAtom>();
		List<SWRLAtom> swrlapiHeadAtoms = new ArrayList<SWRLAtom>();

		for (SWRLAtom atom : owlapiBodyAtoms) {
			if (isSWRLBuiltInAtom(atom)) {
				SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
				IRI builtInIRI = builtInAtom.getPredicate();
				String builtInShortName = getOWLIRIResolver().iri2PrefixedName(builtInIRI);
				List<SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
				List<SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
				SWRLBuiltInAtom swrlapiAtom = getSWRLAPIOWLDataFactory().getSWRLAPIBuiltInAtom(ruleName, builtInIRI,
						builtInShortName, swrlBuiltInArguments);
				swrlapiBodyAtoms.add(swrlapiAtom);
			} else
				swrlapiBodyAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
		}

		for (SWRLAtom atom : owlapiHeadAtoms) {
			if (isSWRLBuiltInAtom(atom)) {
				SWRLBuiltInAtom builtInAtom = (SWRLBuiltInAtom)atom;
				IRI builtInIRI = builtInAtom.getPredicate();
				String builtInShortName = getOWLIRIResolver().iri2PrefixedName(builtInIRI);
				List<SWRLDArgument> swrlDArguments = builtInAtom.getArguments();
				List<SWRLBuiltInArgument> swrlBuiltInArguments = convertSWRLDArguments2SWRLBuiltInArguments(swrlDArguments);
				SWRLBuiltInAtom swrlapiAtom = getSWRLAPIOWLDataFactory().getSWRLAPIBuiltInAtom(ruleName, builtInIRI,
						builtInShortName, swrlBuiltInArguments);
				swrlapiHeadAtoms.add(swrlapiAtom);
			} else
				swrlapiHeadAtoms.add(atom); // Only built-in atoms are converted; other atoms remain the same
		}
		return new DefaultSWRLAPIRule(ruleName, swrlapiBodyAtoms, swrlapiHeadAtoms, getOWLIRIResolver(), active, comment);
	}

	/**
	 * Both the OWLAPI and the SWRLAPI use the {@link SWRLBuiltInAtom} class to represent built-in atoms. However, the
	 * SWRLAPI has a richer range of possible argument types. The OWLAPI allows {@link SWRLDArgument} built-in arguments
	 * only, whereas the SWRLAPI has a range of types. These types are represented buy the {@link SWRLBuiltInArgument}
	 * interface.
	 * 
	 * @see SWRLBuiltInArgument
	 */
	private List<SWRLBuiltInArgument> convertSWRLDArguments2SWRLBuiltInArguments(List<SWRLDArgument> swrlDArguments)
	{
		List<SWRLBuiltInArgument> swrlBuiltInArguments = new ArrayList<SWRLBuiltInArgument>();

		for (SWRLDArgument swrlDArgument : swrlDArguments) {
			SWRLBuiltInArgument swrlBuiltInArgument = convertSWRLDArgument2SWRLBuiltInArgument(swrlDArgument);
			swrlBuiltInArguments.add(swrlBuiltInArgument);
		}

		return swrlBuiltInArguments;
	}

	/**
	 * The {@link SWRLBuiltInArgument} interface represents the primary SWRLAPI extension point to the OWLAPI classes to
	 * represent arguments to SWRL built-in atoms.
	 * 
	 * @see SWRLBuiltInArgument, SWRLLiteralArgument, SWRLDArgument
	 */
	private SWRLBuiltInArgument convertSWRLDArgument2SWRLBuiltInArgument(SWRLDArgument swrlDArgument)
	{
		if (swrlDArgument instanceof SWRLLiteralArgument) {
			SWRLLiteralArgument swrlLiteralArgument = (SWRLLiteralArgument)swrlDArgument;
			SWRLBuiltInArgument argument = convertSWRLLiteralArgument2SWRLBuiltInArgument(swrlLiteralArgument);
			return argument;
		} else if (swrlDArgument instanceof SWRLVariable) {
			SWRLVariable swrlVariable = (SWRLVariable)swrlDArgument;
			SWRLVariableBuiltInArgument argument = transformSWRLVariable2SWRLVariableBuiltInArgument(swrlVariable);

			getOWLIRIResolver().recordSWRLVariable(swrlVariable);
			return argument;
		} else
			throw new RuntimeException("Unknown " + SWRLDArgument.class.getName() + " class "
					+ swrlDArgument.getClass().getName());
	}

	/**
	 * The OWLAPI permits only variable and literal arguments to built-ins, which conforms with the SWRL Specification.
	 * The SWRLAPI also permits OWL classes, individuals, properties, and datatypes as arguments. In order to support
	 * these additional argument types in a Specification-conformant way, the SWRLAPI treats URI literal arguments
	 * specially. It a URI literal argument is passed to a built-in we determine if it refers to an OWL named object in
	 * the active ontology and if so we create specific SWRLAPI built-in argument types for it.
	 * <p>
	 * The SWRLAPI allows SQWRL collection built-in arguments (represented by a
	 * {@link SQWRLCollectionVariableBuiltInArgument}) and multi-value variables (represented by a
	 * {@link SWRLMultiValueVariableBuiltInArgument}). These two argument types are not instantiated directly as built-in
	 * argument types. They are created at runtime during rule execution and passed as result values in SWRL variable
	 * built-in arguments.
	 * 
	 * @see SWRLLiteralBuiltInArgument, SWRLClassBuiltInArgument, SWRLIndividualBuiltInArgument,
	 *      SWRLObjectPropertyBuiltInArgument, SWRLDataPropertyBuiltInArgument, SWRLAnnotationPropertyBuiltInArgument,
	 *      SWRLDatatypeBuiltInArgument, SQWRLCollectionBuiltInArgument, SWRLMultiValueBuiltInArgument
	 */
	private SWRLBuiltInArgument convertSWRLLiteralArgument2SWRLBuiltInArgument(SWRLLiteralArgument swrlLiteralArgument)
	{
		OWLLiteral literal = swrlLiteralArgument.getLiteral();
		OWLDatatype datatype = literal.getDatatype();

		if (isURI(datatype)) {
			IRI iri = IRI.create(literal.getLiteral());
			if (getOWLOntology().containsClassInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(iri);
			} else if (getOWLOntology().containsIndividualInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(iri);
			} else if (getOWLOntology().containsObjectPropertyInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(iri);
			} else if (getOWLOntology().containsDataPropertyInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(iri);
			} else if (getOWLOntology().containsAnnotationPropertyInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(iri);
			} else if (getOWLOntology().containsDatatypeInSignature(iri)) {
				return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(iri);
			} else {
				return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
			}
		} else {
			SWRLLiteralBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
			return argument;
		}
	}

	private SWRLVariableBuiltInArgument transformSWRLVariable2SWRLVariableBuiltInArgument(SWRLVariable swrlVariable)
	{
		IRI variableIRI = swrlVariable.getIRI();

		SWRLVariableBuiltInArgument argument = getSWRLBuiltInArgumentFactory().getVariableBuiltInArgument(variableIRI);

		return argument;
	}

	private boolean isSWRLBuiltInAtom(SWRLAtom atom) // TODO
	{
		if (atom instanceof SWRLBuiltInAtom)
			return true;
		else {
			SWRLPredicate predicate = atom.getPredicate();
			if (predicate instanceof IRI) {
				IRI iri = (IRI)predicate;
				return isSWRLBuiltIn(iri);
			}
			return false;
		}
	}

	private boolean isSWRLBuiltIn(IRI iri)
	{
		throw new RuntimeException("isSWRLBuiltIn not implemented");
	}

	private boolean isURI(OWLDatatype datatype)
	{
		return datatype.getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI());
	}

	private SWRLBuiltInArgumentFactory getSWRLBuiltInArgumentFactory()
	{
		return getSWRLAPIOWLDataFactory().getSWRLBuiltInArgumentFactory();
	}
}