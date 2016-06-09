package MCommon;

import MDataProcessing.Assertion.ConceptAssertion;
import MDataProcessing.Assertion.DataAssertion;
import MDataProcessing.Assertion.RoleAssertion;
import MKnowledge.KnowledgeBase;
//import MRandom.MyRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tdminh
 */
public class Global 
{
    public static IRI iriFileInput;
    public static KnowledgeBase knowledgeBase;
    public static ArrayList<ConceptAssertion> allFrequentConceptsFull;
    public static ArrayList<RoleAssertion> allFrequentRolesFull;
    public static ArrayList<RoleAssertion> allFrequentRoles;
    public static ArrayList<DataAssertion> allFrequentDatasFull;
    
    public static String cutNameOfIRI(String str)
    {
        str+="#erro";
        String[] parts = str.split("#");
        return parts[1];
    }
}
