/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDataProcessing;

import MCommon.Global;
import MDataProcessing.Assertion.DataAssertion;
import static MDataProcessing.ConceptProcessing.top;
import MDataProcessing.Individual.DataIndividual;
import MDataProcessing.Individual.DataIndividuals;
import MKnowledge.KnowledgeBase;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 *
 * @author thangdn
 */
public class DataProcessing {
    public static DefaultMutableTreeNode top;
    
    public static boolean isLeaf(String name){
        name+=">";
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
    public static void createFrequentDatasForFullVersion(KnowledgeBase knowledgeBase)
    {
        Global.allFrequentDatasFull = new ArrayList<>();
        OWLDataProperty property = Global.knowledgeBase.getOntologyManager().getOWLDataFactory().getOWLTopDataProperty();
        printDataProperties(property);
        Set<OWLDataProperty> dataProperties = knowledgeBase.getOntology().getDataPropertiesInSignature();

        for (OWLDataProperty owlDataProperty : dataProperties) {
            Global.allFrequentDatasFull.add(CFCFFVgetIndividuals(owlDataProperty));
        }
    }
    public static void printDataProperties(OWLDataProperty property) {
        OWLReasoner reasoner = Global.knowledgeBase.getPelletReasoner();
        reasoner.precomputeInferences(InferenceType.OBJECT_PROPERTY_HIERARCHY);
        top=new DefaultMutableTreeNode("top");
        for (Node<OWLDataProperty> childNode : reasoner.getSubDataProperties(property, true)) {
            printDataProperty(top, reasoner, childNode.getRepresentativeElement());
        }
    }
    private static void printDataProperty(DefaultMutableTreeNode node, OWLReasoner reasoner, OWLDataProperty property) {
        
        if (property.isBottomEntity())
            return;
        DefaultMutableTreeNode newnode= new DefaultMutableTreeNode(Global.cutNameOfIRI(property.toString()));
        node.add(newnode);
        
        for (Node<OWLDataProperty> childNode : reasoner.getSubDataProperties(property, true)) {
            printDataProperty(newnode,reasoner, childNode.getRepresentativeElement());
        }

    }
    
    public static DataAssertion CFCFFVgetIndividuals(OWLDataProperty owlDataProperty)
    {
        Set<OWLClass> setDomainClass = Global.knowledgeBase.getPelletReasoner().getDataPropertyDomains(owlDataProperty, true).getFlattened();

            int countAssertion = 0;
            boolean acceptAddToFrequentData = false;

            //Checking if the number of assetions is greater than or equals iNumberOfMinimumAssertions
            DataIndividuals dataIndividuals = new DataIndividuals();

            for (OWLClass domainClass : setDomainClass) {
                //Get all of Individuals of each class of domain
                Set<OWLNamedIndividual> setDomainIndividuals = Global.knowledgeBase.getPelletReasoner().getInstances(domainClass, false).getFlattened();

                for (OWLNamedIndividual domainIndividual : setDomainIndividuals) {
                    //Get all of range individuals corresponde with domain individuals  
                    Set<OWLLiteral> setRangeIndividuals = Global.knowledgeBase.getPelletReasoner().getDataPropertyValues(domainIndividual, owlDataProperty);

                    if (setRangeIndividuals.size() > 0) {
                        for (OWLLiteral rangeIndividual : setRangeIndividuals) {
                            String strDomainIndividual = Global.cutNameOfIRI(domainIndividual.getIRI().toString());
                            String strRangeIndividual = rangeIndividual.getLiteral().toString();

                            DataIndividual dataIndividual = new DataIndividual(strDomainIndividual, strRangeIndividual);
                            dataIndividuals.addIndividual(dataIndividual);
                            dataIndividuals.addIndividual(strDomainIndividual, strRangeIndividual);
                        }
                    }
                }
            }

            DataAssertion dataAssertion = new DataAssertion(owlDataProperty.getIRI(), dataIndividuals);
        return dataAssertion;
    }
}
