/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Assertion;

import MCommon.Global;
import MDataProcessing.Individual.ConceptIndividuals;
import java.util.Objects;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

/**
 *
 * @author tdminh
 */
public class ConceptAssertion 
{

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.iriConcept);
        hash = 41 * hash + Objects.hashCode(this.individuals);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConceptAssertion other = (ConceptAssertion) obj;
        if (!Objects.equals(this.iriConcept, other.iriConcept)) {
            return false;
        }
        if (!Objects.equals(this.individuals, other.individuals)) {
            return false;
        }
        return true;
    }
    private IRI iriConcept;
    private ConceptIndividuals individuals;
    
    public ConceptAssertion(IRI iriConcept, ConceptIndividuals individuals)
    {
        this.iriConcept = iriConcept;
        this.individuals = individuals;
    }
    
    public IRI getIRIConcept()
    {
        return this.iriConcept;
    }
    
    public String getConceptName()
    {
        return Global.cutNameOfIRI(this.iriConcept.toString());        
    }
    
    public ConceptIndividuals getIndividuals()
    {
        return this.individuals;
    }
}
