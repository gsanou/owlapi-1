/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Individual;

/**
 *
 * @author thangdn
 */
public class DataIndividual {
    private String strDomain, strRange;

    public DataIndividual(String strDomain, String strRange) {
        this.strDomain = strDomain;
        this.strRange = strRange;
    }

    public String getDomain() {
        return strDomain;
    }

    public String getRange() {
        return strRange;
    }
    
    public String getId(){
        return this.strDomain + this.strRange;
    }
    
}
