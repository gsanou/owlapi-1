/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing.Individual;

/**
 *
 * @author DoTienChi
 */
public class RoleIndividual {
    private String strDomain, strRange;

    public RoleIndividual(String strDomain, String strRange) {
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
    
    @Override
    public int hashCode(){
        return this.getId().hashCode();
    }
    
    public boolean equals (Object obj){
        return ((obj instanceof RoleIndividual) && (
                ((RoleIndividual) obj).strDomain.equals(this.strDomain))&&
                ((RoleIndividual)obj).strRange.equals(this.strRange)
                );
    }
    
}
