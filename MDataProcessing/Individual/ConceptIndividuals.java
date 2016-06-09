/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Individual;

import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 * @author tdminh
 */

public class ConceptIndividuals 
{
    private Set<String> individualsName;
    
    public ConceptIndividuals()
    {
        this.individualsName = new HashSet<String>();
    } 
    
    public Set<String> getIndividualsName()
    {
        return this.individualsName;
    }
    
    public void addIndividual(String strIndividual)
    {        
        this.individualsName.add(strIndividual);
    }
    
    public boolean checkIndividual(String strIndividual)
    {
        return this.individualsName.contains(strIndividual);
    }
    
    public int size()
    {
        return individualsName.size();
    }
}
