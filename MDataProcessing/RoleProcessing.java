/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. */
package MDataProcessing;

import MCommon.Global;
import MDataProcessing.Assertion.RoleAssertion;
import MDataProcessing.Individual.RoleIndividual;
import MDataProcessing.Individual.RoleIndividuals;
import MKnowledge.KnowledgeBase;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 *
 * @author tdminh
 */
public class RoleProcessing {

    public static DefaultMutableTreeNode top;
    public static final String UNDEFINED = "Undefined";
    //Getting all of roles that has assertions greater than or equal iNumberOfMinimumAssertions
    //These roles is put into the static variable RoleProcessing.allFrequentRolesFull    
    public static void createFrequentRolesForFullVersion(KnowledgeBase knowledgeBase) {
        Global.allFrequentRolesFull = new ArrayList<>();
        OWLObjectProperty property = Global.knowledgeBase.getOntologyManager().getOWLDataFactory().getOWLTopObjectProperty();
        printObjectProperties(property);
        Set<OWLObjectProperty> objectProperties = knowledgeBase.getOntology().getObjectPropertiesInSignature();

        for (OWLObjectProperty owlObjectProperty : objectProperties) {
            Global.allFrequentRolesFull.add(CFCFFVgetIndividuals(owlObjectProperty));
        }
    }
    
    public static void printObjectProperties(OWLObjectProperty property) {
        OWLReasoner reasoner = Global.knowledgeBase.getPelletReasoner();
        reasoner.precomputeInferences(InferenceType.OBJECT_PROPERTY_HIERARCHY);
        top=new DefaultMutableTreeNode("top");
        for (Node<OWLObjectPropertyExpression> childNode : reasoner.getSubObjectProperties(property, true)) {
            printObjectProperty(top, reasoner, childNode.getRepresentativeElement());
        }


    }
    
    private static void printObjectProperty(DefaultMutableTreeNode node, OWLReasoner reasoner, OWLObjectPropertyExpression property) {
        
        if (property.isBottomEntity())
            return;
        DefaultMutableTreeNode newnode= new DefaultMutableTreeNode(Global.cutNameOfIRI(property.toString()));
        node.add(newnode);
        
        for (Node<OWLObjectPropertyExpression> childNode : reasoner.getSubObjectProperties(property, true)) {
            printObjectProperty(newnode,reasoner, childNode.getRepresentativeElement());
        }

    }
    
    public static RoleAssertion CFCFFVgetIndividuals(OWLObjectProperty owlObjectProperty)
    {
        Set<OWLClass> setDomainClass = Global.knowledgeBase.getPelletReasoner().getObjectPropertyDomains(owlObjectProperty, true).getFlattened();

            int countAssertion = 0;
            boolean acceptAddToFrequentRole = false;

            //Checking if the number of assetions is greater than or equals iNumberOfMinimumAssertions
            RoleIndividuals roleIndividuals = new RoleIndividuals();

            for (OWLClass domainClass : setDomainClass) {
                //Get all of Individuals of each class of domain
                Set<OWLNamedIndividual> setDomainIndividuals = Global.knowledgeBase.getPelletReasoner().getInstances(domainClass, false).getFlattened();

                for (OWLNamedIndividual domainIndividual : setDomainIndividuals) {
                    //Get all of range individuals corresponde with domain individuals  
                    Set<OWLNamedIndividual> setRangeIndividuals = Global.knowledgeBase.getPelletReasoner().getObjectPropertyValues(domainIndividual, owlObjectProperty).getFlattened();

                    if (setRangeIndividuals.size() > 0) {
                        for (OWLNamedIndividual rangeIndividual : setRangeIndividuals) {
                            String strDomainIndividual = Global.cutNameOfIRI(domainIndividual.getIRI().toString());
                            String strRangeIndividual = Global.cutNameOfIRI(rangeIndividual.getIRI().toString());

                            RoleIndividual roleIndividual = new RoleIndividual(strDomainIndividual, strRangeIndividual);
                            roleIndividuals.addIndividual(roleIndividual);
                            roleIndividuals.addIndividual(strDomainIndividual, strRangeIndividual);
                        }
                    }
                }
            }

            RoleAssertion roleAssertion = new RoleAssertion(owlObjectProperty.getIRI(), roleIndividuals);
        return roleAssertion;
    }
    
    
    
    //Get assertions of a role that is respesented by iriRole
    public static RoleAssertion getRoleAssertionFromFrequentRolesFull(IRI iriRole) {
        for (RoleAssertion ra : Global.allFrequentRolesFull) {
            if (ra.getIRIRole().equals(iriRole)) {
                return ra;
            }
        }
        return null;
    }

    //Get assertions of a role that is respesented by Role name
    public static RoleAssertion getRoleAssertionFromFrequentRoles(String srtRoleName) {
        for (RoleAssertion ra : Global.allFrequentRolesFull) {
            if (ra.getRoleName().equals(srtRoleName)) {
                return ra;
            }
        }
        return null;
    }
    public static void addIndividualRole(String DomaninnameIndividual,String RangenameIndividual, String nameClass)
    {
        for(RoleAssertion ca : Global.allFrequentRolesFull)        
            if (Global.cutNameOfIRI(ca.getIRIRole().toString()).equals(nameClass))
            {
                ca.getIndividuals().addIndividual(DomaninnameIndividual,RangenameIndividual);
                return;
            }
    }
}
