/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DemoOntology;

import java.io.File;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 *
 * @author DoTienChi
 */

public class Loading_Ontologies {
    public static final String URL="http://protege.stanford.edu/ontologies/pizza/pizza.owl";
    public static final String strFile="D:\\ProtegeWorkSpace\\XetTotNghiep.owl";
    //Get hold of an ontology manager
    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

    //Load an ontology from the Web
    IRI iriWeb = IRI.create(URL);
    
    File file = new File(strFile);
    IRI iriFile = IRI.create(file);
    OWLOntology pizzaOntology;
    OWLOntology xtnOntology;

    public Loading_Ontologies() throws OWLOntologyCreationException {
        this.pizzaOntology = manager.loadOntologyFromOntologyDocument(iriWeb);
        this.xtnOntology = manager.loadOntologyFromOntologyDocument(iriFile);
        System.out.println("Loaded ontology from web: " + pizzaOntology);
        System.out.println("Loaded ontology from local: " + xtnOntology);
    
    }
    
    public void FactoryOntology(){
        OWLDataFactory factory = this.manager.getOWLDataFactory();
        
        //Create an object to represent a class. In this case, we'll choose
        //http://chidtSemantic.org/owlapi/ontologies/ontology#A 
        //as the IRI for our clas. There are two ways to create class (and other entiies).
        
        //The first is by specifying the full IRI. First we create an IRI object:
        IRI iri = IRI.create("http://chidtSemantic.org/owlapi/ontologies/ontology#A");
        
        //Create the class
        OWLClass clsAMenthodA = factory.getOWLClass(iri);
        
    }
    /*public static void main(String args[]) throws OWLOntologyCreationException {
        Loading_Ontologies load = new Loading_Ontologies();

    }*/
    //Remove the ontology so that we can load a local copy
    //manager.removeOntology(pizzaOntology);
    
    //Load ontologies from files. Create a file object that points to the local copy
    //File file = new File("D:\\ProtegeWorkSpace\\pizza.owl");
    //Load the local copy
    //OWLOntology localPizza = manager.loadOntologyFromOntologyDocument(IRI.create(file));
}
