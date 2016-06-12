/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing;

import MCommon.Global;
import MDataProcessing.Assertion.ConceptAssertion;
import MDataProcessing.Individual.ConceptIndividuals;
import MKnowledge.KnowledgeBase;
import OWLapi.LabelExtractor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.HasAddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 *
 * @author tdminh
 */
public class ConceptProcessing 
{
    //public static JTree topConcept;
    public static DefaultMutableTreeNode top;
    
    public static boolean isLeaf(String name){
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)top.getRoot();
        Enumeration e = root.preorderEnumeration();
        while(e.hasMoreElements()){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if(name.equals(node.getUserObject()))
            {
                return node.isLeaf();
            }
        }
        return false;
    }
    //Getting all of concepts that has assertions greater than or equal iNumberOfMinimumAssertions
    //These concepts is put into the static variable ConceptProcessing.allFrequentConceptsFull
    public static void createFrequentConceptsForFullVersion(KnowledgeBase knowledgeBase)
    {
        //Global.allFrequentConceptsFull = new HashMap<>();
        Global.allFrequentConceptsFull = new ArrayList<>();
        OWLReasoner reasoner = Global.knowledgeBase.getPelletReasoner();
        top=new DefaultMutableTreeNode("Thing");
        printHierarchy(top,reasoner,null,knowledgeBase.getDataFactory().getOWLThing(),0);
        //System.out.print(isLeaf("LCN_2T>"));
    }
    public static ConceptAssertion CFCFFVgetIndividuals(OWLClass owlClass)
    {
        Set<OWLNamedIndividual> setIndividuals = Global.knowledgeBase.getPelletReasoner().getInstances(owlClass, false).getFlattened();
        ConceptIndividuals conceptIndividuals = new ConceptIndividuals();                
                for (OWLNamedIndividual individual : setIndividuals)
                    conceptIndividuals.addIndividual(Global.cutNameOfIRI(individual.getIRI().toString()));
                
                ConceptAssertion conceptAssertion = new ConceptAssertion(owlClass.getIRI(), conceptIndividuals);
        return conceptAssertion;
    }
    
    private static void printHierarchy(DefaultMutableTreeNode node,OWLReasoner reasoner,OWLClass clazzparent, OWLClass clazz, int level)
    {
        if (reasoner.isSatisfiable(clazz)) {
            /*Set<ConceptAssertion> newa;
            if(clazzparent!=null)
                 newa = Global.allFrequentConceptsFull.get(clazzparent);
            else
                newa = new HashSet<>();
            
            if (newa == null) {
                newa = new HashSet<>();
            }*/
            //newa.add(CFCFFVgetIndividuals(clazz));
            
            DefaultMutableTreeNode newchild;
            if(level==0)
            {
                newchild=node;
            }
            else
            {
                newchild = new DefaultMutableTreeNode(Global.cutNameOfIRI(clazz.toString()));
                node.add(newchild);
            }
            
            //Global.allFrequentConceptsFull.put(clazzparent,newa);
            Global.allFrequentConceptsFull.add(CFCFFVgetIndividuals(clazz));
            for (OWLClass child : reasoner.getSubClasses(clazz, true).getFlattened()) {
                if (!child.equals(clazz)) {
                    printHierarchy(newchild,reasoner,clazz, child, level + 1);
                }
            }
        }
    }

    
    //Get assertions of a concept that is respesented by iriConcept
    public static ConceptAssertion getConceptAssertionFromFrequentConceptsFull(IRI iriConcept)
    {
        for(ConceptAssertion ca : Global.allFrequentConceptsFull)        
            if (ca.getIRIConcept().equals(iriConcept))
                return ca;        
        return null;        
    }
    //Get assertions of a concept that is respesented by iriConcept
    public static ConceptAssertion getConceptAssertionFromFrequentConceptsFull(String strConceptName)
    {
        for(ConceptAssertion ca : Global.allFrequentConceptsFull)        
            if (Global.cutNameOfIRI(ca.getIRIConcept().toString()+">").equals(strConceptName))
               return ca; 
        return null;        
    }
    
    public static void addIndividualConcept(String nameIndividual, String nameClass)
    {
        for(ConceptAssertion ca : Global.allFrequentConceptsFull)        
            if (Global.cutNameOfIRI(ca.getIRIConcept().toString()).equals(nameClass))
            {
                ca.getIndividuals().addIndividual(nameIndividual);
                return;
            }
    }
    
}
