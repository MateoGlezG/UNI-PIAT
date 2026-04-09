package es.upm.piat.crowdsense.xml;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador JAXB para convertir entre LocalDateTime y String.
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public LocalDateTime unmarshal(String xmlValue) throws Exception {
        if (xmlValue == null || xmlValue.trim().isEmpty()) {
            return null;
        }
        String normalized = xmlValue.replace("Z", "");
        return LocalDateTime.parse(normalized, FORMATTER);
    }
    
    @Override
    public String marshal(LocalDateTime javaValue) throws Exception {
        if (javaValue == null) {
            return null;
        }
        return javaValue.format(FORMATTER);
    }
}
