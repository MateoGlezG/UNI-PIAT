package es.upm.piat.crowdsense.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.*;

import es.upm.piat.crowdsense.model.*;
import es.upm.piat.crowdsense.model.Campana.ResumenCampana;
import es.upm.piat.crowdsense.model.CrowdSenseProject.Estadisticas;
import es.upm.piat.crowdsense.model.CrowdSenseProject.Metadata;
/**
 * EJERCICIO 3:
 *  writeAttribute() debe llamarse INMEDIATAMENTE después de writeStartElement(), antes de
	cualquier writeCharacters() o writeStartElement() hijo. Si lo ponéis después, el XMLStreamWriter
	lanza una excepción.m. 
	Si el elemento no tiene atributos usar el metodo interno de elem()-> parser String.valueOf(//el dato que sea)
 */
public class ProjectExporter {
	
	private static final String NS = "http://upm.es/piat/crowdsense";
	private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private static final DateTimeFormatter D_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    
	//para escribir a fichero
    public void exportToFile(CrowdSenseProject project, File outputFile) throws Exception {
        // TODO: Implementar
    	 XMLStreamWriter w = null;
    	try (FileOutputStream fos = new FileOutputStream(outputFile)) {
    		 w = XMLOutputFactory.newInstance().createXMLStreamWriter(fos, "UTF-8");
    		 writeDocument(w, project);
    		 w.flush();
    	}  
	    finally {
	        if (w != null) {
	            w.close();
	        }
	    }
    }
    
    //metodos auxiliares para los write, privados
    //Elemento
    private void elem(XMLStreamWriter w, String elemento, String texto) throws XMLStreamException {
	    w.writeStartElement(elemento);
	    w.writeCharacters(texto != null ? texto : "");
	    w.writeEndElement();
    }
    
    //Format Date Time
    private String fmt(LocalDateTime dt) {
    	return dt != null ? dt.format(DT_FMT) : "";
    }
    private String fmt(LocalDate d) {
    	return d != null ? d.format(D_FMT) : "";
    }
    
    //deficion del xml
    //elemento raiz del documento xml
    private void writeDocument(XMLStreamWriter w, CrowdSenseProject cs) throws XMLStreamException {
    	
    	w.writeStartDocument("UTF-8", "1.0");
    	
    	w.writeStartElement("crowdSenseProject");
    	w.writeDefaultNamespace(NS); // xmlns="..."
    	w.writeAttribute("version", cs.getVersion());
    	w.writeAttribute("generadoEn", fmt(cs.getGeneradoEn()));
    	
    	writeMetadata(w, cs.getMetadata());
    	writeCampanas(w, cs);
    	writeEstadisticas(w, cs.getEstadisticas());
    	
    	w.writeEndElement(); // </crowdSenseProject>
    	w.writeEndDocument();
    }
    
    //escribir metada 
    private void writeMetadata(XMLStreamWriter w, Metadata m) throws XMLStreamException {
	     w.writeStartElement("metadata");
	     elem(w, "id", m.getId());
	     elem(w, "nombre", m.getNombre());
	     elem(w, "responsable", m.getResponsable());
	     elem(w, "email", m.getEmail());
	     elem(w, "fechaCreacion", fmt(m.getFechaCreacion()));
	     w.writeEndElement(); //</metadata>
    }
    
    //declaracion del elemetno Campanas que tiene campanas dentro
    private void writeCampanas(XMLStreamWriter w, CrowdSenseProject cs) throws XMLStreamException {
    	w.writeStartElement("campanas");
    	for (Campana c : cs.getCampanas()) {
    		writeCampana(w, c); //va rellenando con los elementos campanas que tenga 
    	}
    	w.writeEndElement(); // </campanas>
    }
    
    private void writeCampana(XMLStreamWriter w, Campana c) throws XMLStreamException{
    	w.writeStartElement("campana");
    	w.writeAttribute("id", c.getId()); // atributo ANTES de los hijos, siempre inmediatamende despues de startElement
    	elem(w, "nombre", c.getNombre());
    	elem(w, "fechaInicio", fmt(c.getFechaInicio()));
    	writeSondas(w, c);
    	writeResumen(w, c.getResumen());
    	w.writeEndElement(); // </campana>
    }
    
    //declaracion de los elementos Sondas y Sonda
    private void writeSondas(XMLStreamWriter w, Campana c) throws XMLStreamException {
    	w.writeStartElement("sondas");
    	for (Sonda s : c.getSondas()) {
    		writeSonda(w, s); //va rellenando con los elementos campanas que tenga 
    	}
    	w.writeEndElement(); // </sondas>
    }
    
    private void writeSonda(XMLStreamWriter w, Sonda s) throws XMLStreamException{
    	w.writeStartElement("sonda");
    	w.writeAttribute("id", s.getId()); // atributo ANTES de los hijos, siempre inmediatamende despues de startElement
    	w.writeAttribute("status", s.getStatus());
    	elem(w, "nombre", s.getNombre());
    	elem(w, "ubicacionRef",s.getUbicacionRef());
    	elem(w, "macSonda", s.getMacSonda());
    	elem(w, "fechaInstalacion", fmt(s.getFechaInstalacion()));
    	writeProbes(w, s);
    	w.writeEndElement(); // </campana>
    }
    
    //declaracion de los elemtos Probes y Probe
    private void writeProbes(XMLStreamWriter w, Sonda s) throws XMLStreamException {
	    w.writeStartElement("probes");
	    for (WifiProbe probe : s.getProbes()) {
	    	writeProbe(w, probe);
	    }
	    w.writeEndElement(); // </probes>
	}
    
    private void writeProbe(XMLStreamWriter w, WifiProbe probe)
    		 throws XMLStreamException {
    	w.writeStartElement("probe");
    	w.writeAttribute("id", probe.getId());
    	w.writeAttribute("sondaRef", probe.getSondaRef());
    		 
    	if (probe.getSecuencia() != null) { // secuencia puede ser null
	    	w.writeAttribute("secuencia",
	    	probe.getSecuencia().toString());
    	}
    		 
    	elem(w, "timestamp", fmt(probe.getTimestamp()));
    	writeLocation(w, probe.getLocation());
    	elem(w, "rssi", String.valueOf(probe.getRssi()));
    	elem(w, "macAddress", probe.getMacAddress());
    	elem(w, "ssid", probe.getSsid() != null ? probe.getSsid() : "");
    	w.writeEndElement(); // </probe>
    }
    
    //declaracion del elemtno Location
    private void writeLocation(XMLStreamWriter w, LocationID loc) throws XMLStreamException {
    	w.writeStartElement("location");
    	elem(w, "edificio", loc.getEdificio());
    	elem(w, "bloque", loc.getBloque());
    	elem(w, "planta", loc.getPlanta());
    	elem(w, "sala", loc.getSala());
    	w.writeEndElement(); // </location>
    }
    
    //declaracion de resumen
    private void writeResumen(XMLStreamWriter w, ResumenCampana r)
    		 throws XMLStreamException {
    	if (r == null) return;
    	w.writeStartElement("resumen");
    	elem(w, "totalProbes", r.getTotalProbes() != null ? r.getTotalProbes().toString() : "0");
    	elem(w, "dispositivosUnicos", r.getDispositivosUnicos() != null ? r.getDispositivosUnicos().toString() : "0");
    	elem(w, "rssiPromedio", r.getRssiPromedio() != null ? String.format("%.1f", r.getRssiPromedio()) : "0.0");
   	 	w.writeEndElement(); // </resumen>
    }
    
    //declaracion de estadisticas
    private void writeEstadisticas(XMLStreamWriter w, Estadisticas e) throws XMLStreamException {
    	if (e == null) return;
    	w.writeStartElement("estadisticas");
    	elem(w, "totalCampanas",e.getTotalCampanas() != null ? e.getTotalCampanas().toString() : "0");
	   	elem(w, "totalSondas", e.getTotalSondas() != null ? e.getTotalSondas().toString() : "0");
    	elem(w, "totalProbes", e.getTotalProbes() != null ? e.getTotalProbes().toString() : "0");
    	elem(w, "dispositivosUnicosGlobal", e.getDispositivosUnicosGlobal() != null ? e.getDispositivosUnicosGlobal().toString() : "0");
    	w.writeEndElement(); // </estadisticas>
    }
    
}//fin de la clase
