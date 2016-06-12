/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Individual;

import MDataProcessing.Individual.RoleIndividual;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author DoTienChi
 */
public class RoleIndividuals {
    private Set<RoleIndividual> individuals;
    private Set<RoleIndividual> invert_individuals;
    
    private ArrayList<String> domainIndividuals;
    private ArrayList<String> rangeIndividuals;
    
    public RoleIndividuals(){
        this.individuals = new LinkedHashSet<RoleIndividual>();
        this.invert_individuals = new LinkedHashSet<RoleIndividual>();
        this.domainIndividuals = new ArrayList<String>();
        this.rangeIndividuals = new ArrayList<String>();
    }
    
    public Set<RoleIndividual> getIndividuals(){
        return this.individuals;
    }
    
    public Set<RoleIndividual> getInvertIndividuals(){
        return this.invert_individuals;
    }
    
    public ArrayList<String> getDomainIndividuals(){
        return this.domainIndividuals;
    }
    
    public ArrayList<String> getRangeIndividuals(){
        return this.rangeIndividuals;
    }
    //Get a list of ranges from this.individuals that has domain is strRangeIndividual
    public Set<String> getRangeIndividuals(String strDomainIndividual){
        Set<String> listRangeIndividuals = new LinkedHashSet<String>();
        for(Iterator<RoleIndividual> it = this.getIndividuals().iterator(); it.hasNext();)
        {
            RoleIndividual roleIndividual = it.next();
            if (roleIndividual.getRange().equals(strDomainIndividual))
                listRangeIndividuals.add(roleIndividual.getDomain());
        }
        
        return listRangeIndividuals;
    }
    public void addIndividual(RoleIndividual roleIndividual)
    {
        this.individuals.add(roleIndividual);        
        
        RoleIndividual invertRoleIndividual = new RoleIndividual(roleIndividual.getRange(), roleIndividual.getDomain());
        this.invert_individuals.add(invertRoleIndividual);
    }
    
    public void addIndividual(String strDomain, String strRange)
    {
        this.domainIndividuals.add(strDomain);
        this.rangeIndividuals.add(strRange);
        this.individuals.add(new RoleIndividual(strDomain, strRange));
    }
    
    //Check if individuals contains roleIndividual
    public boolean checkIndividual(RoleIndividual roleIndividual)
    {
        return individuals.contains(roleIndividual);
    }
    
    public int size()
    {
        return individuals.size();
    }
}
