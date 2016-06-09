/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Assertion;

import MCommon.Global;
import MDataProcessing.Individual.DataIndividuals;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 * @author thangdn
 */
public class DataAssertion {
    private IRI iriData;
    private DataIndividuals individuals;
    
    public DataAssertion(IRI iriData, DataIndividuals individuals)
    {
        this.iriData = iriData;
        this.individuals = individuals;
    }
    
    public IRI getIRIData()
    {
        return this.iriData;
    }
    
    public String getDataName()
    {
        return Global.cutNameOfIRI(this.iriData.toString());        
    }
    
    public DataIndividuals getIndividuals()
    {
        return this.individuals;
    }
}
