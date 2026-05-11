package es.upm.piat.crowdsense.xml;

import java.io.File;
import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import es.upm.piat.crowdsense.model.CrowdSenseProject;

/**
 * EJERCICIO 3:
 *  writeAttribute() debe llamarse INMEDIATAMENTE después de writeStartElement(), antes de
	cualquier writeCharacters() o writeStartElement() hijo. Si lo ponéis después, el XMLStreamWriter
	lanza una excepción.m. 
	Si el elemento no tiene atributos usar el metodo interno de elem()-> parser String.valueOf(//el dato que sea)
 */
public class ProjectExporter {
    
	//para escribir a fichero
	FileOutputStream fos = new FileOutputStream(outputFile);
	XMLStreamWriter w = XMLOutputFactory.newInstance().createXMLStreamWriter(fos, "UTF-8");
	
	//para escribir a String
	tringWriter sw = new StringWriter();
	XMLStreamWriter w = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
	
    
    public ProjectExporter() throws JAXBException {
        
    }
    
    public void exportToFile(CrowdSenseProject project, File outputFile) throws Exception {
        // TODO: Implementar
        
    }
    
    public String exportToString(CrowdSenseProject project) throws JAXBException {
        // TODO: Implementar
        
    }
    
    //metodos auxiliares para los write, privados
    //Elemento
    private void elem(XMLStreamWriter w, String elemento, String texto) throws XMLStreamException {
	    w.writeStartElement(elemento);
	    w.writeCharacters(texto != null ? texto : "");
	    w.writeEndElement();
    }
    
}
