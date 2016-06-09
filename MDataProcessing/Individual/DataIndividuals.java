/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Individual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author thangdn
 */
public class DataIndividuals {
    private Set<DataIndividual> individuals;
    private Set<DataIndividual> invert_individuals;
    
    private ArrayList<String> domainIndividuals;
    private ArrayList<String> rangeIndividuals;
    
    public DataIndividuals(){
        this.individuals = new LinkedHashSet<DataIndividual>();
        this.invert_individuals = new LinkedHashSet<DataIndividual>();
        this.domainIndividuals = new ArrayList<String>();
        this.rangeIndividuals = new ArrayList<String>();
    }
    
    public Set<DataIndividual> getIndividuals(){
        return this.individuals;
    }
    
    public Set<DataIndividual> getInvertIndividuals(){
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
        for(Iterator<DataIndividual> it = this.getIndividuals().iterator(); it.hasNext();)
        {
            DataIndividual dataIndividual = it.next();
            if (dataIndividual.getRange().equals(strDomainIndividual))
                listRangeIndividuals.add(dataIndividual.getDomain());
        }
        
        return listRangeIndividuals;
    }
    public void addIndividual(DataIndividual dataIndividual)
    {
        this.individuals.add(dataIndividual);        
        
        DataIndividual invertDataIndividual = new DataIndividual(dataIndividual.getRange(), dataIndividual.getDomain());
        this.invert_individuals.add(invertDataIndividual);
    }
    
    public void addIndividual(String strDomain, String strRange)
    {
        this.domainIndividuals.add(strDomain);
        this.rangeIndividuals.add(strRange);
    }
    
    //Check if individuals contains roleIndividual
    public boolean checkIndividual(DataIndividual dataIndividual)
    {
        return individuals.contains(dataIndividual);
    }
    
    public int size()
    {
        return individuals.size();
    }
}
