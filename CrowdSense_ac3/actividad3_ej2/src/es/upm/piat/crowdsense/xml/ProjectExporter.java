package es.upm.piat.crowdsense.xml;

import java.io.File;
import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import es.upm.piat.crowdsense.model.CrowdSenseProject;

/**
 * EJERCICIO 2: Completar el exportador de proyectos a XML.
 * 
 * Pasos:
 * 1. Constructor: crear JAXBContext.newInstance(CrowdSenseProject.class)
 * 2. exportToFile: crear Marshaller, configurar propiedades, marshal a File
 * 3. exportToString: igual pero marshal a StringWriter
 */
public class ProjectExporter {
    
    private final JAXBContext context;
    
    public ProjectExporter() throws JAXBException {
        // TODO: Crear JAXBContext
        this.context = JAXBContext.newInstance(CrowdSenseProject.class);
    }
    
    public void exportToFile(CrowdSenseProject project, File outputFile) throws JAXBException {
        // TODO: Implementar
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(project, outputFile);
    }
    
    public String exportToString(CrowdSenseProject project) throws JAXBException {
        // TODO: Implementar
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(project, writer);
        return writer.toString();
    }
}
