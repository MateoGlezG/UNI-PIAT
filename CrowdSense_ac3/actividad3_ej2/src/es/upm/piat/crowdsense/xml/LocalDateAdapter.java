package es.upm.piat.crowdsense.xml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador JAXB para convertir entre LocalDate y String.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    @Override
    public LocalDate unmarshal(String xmlValue) throws Exception {
        if (xmlValue == null || xmlValue.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(xmlValue, FORMATTER);
    }
    
    @Override
    public String marshal(LocalDate javaValue) throws Exception {
        if (javaValue == null) {
            return null;
        }
        return javaValue.format(FORMATTER);
    }
}
