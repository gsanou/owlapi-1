/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Assertion;

import MCommon.Global;
import MDataProcessing.Individual.RoleIndividuals;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 * @author tdminh
 */

public class RoleAssertion 
{
    private IRI iriRole;
    private RoleIndividuals individuals;
    
    public RoleAssertion(IRI iriRole, RoleIndividuals individuals)
    {
        this.iriRole = iriRole;
        this.individuals = individuals;
    }
    
    public IRI getIRIRole()
    {
        return this.iriRole;
    }
    
    public String getRoleName()
    {
        return Global.cutNameOfIRI(this.iriRole.toString());        
    }
    
    public RoleIndividuals getIndividuals()
    {
        return this.individuals;
    }
}
