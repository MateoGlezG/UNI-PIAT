package es.upm.piat.crowdsense.xml;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * EJERCICIO 1: Completar el adaptador JAXB para LocalDateTime.
 * 
 * JAXB no soporta LocalDateTime nativamente. Este adaptador traduce:
 * - unmarshal: String (XML) -> LocalDateTime (Java)
 * - marshal: LocalDateTime (Java) -> String (XML)
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public LocalDateTime unmarshal(String xmlValue) throws Exception {
        // TODO: Implementar
        // 1. Si xmlValue es null o vacío, return null
        // 2. Quitar 'Z' si existe: xmlValue.replace("Z", "")
        // 3. Parsear: LocalDateTime.parse(normalized, FORMATTER)
    	LocalDateTime fecha;
    	if(xmlValue.isEmpty() || xmlValue == null) {
    		fecha = null;
    	}else {
    		xmlValue.replace("Z", "");
    		fecha=LocalDateTime.parse(xmlValue, FORMATTER); //doy el valor a la fecha parseando el string a una fecha 
    	}
    	return fecha;
    }
    
    @Override
    public String marshal(LocalDateTime javaValue) throws Exception {
        // TODO: Implementar
        // 1. Si javaValue es null, return null
        // 2. Formatear: javaValue.format(FORMATTER)
    	String string ;
    	if(javaValue==null) {
    		string = null;
    	}else {
    		string = javaValue.format(FORMATTER);
    	}
        return string;
    }
}
