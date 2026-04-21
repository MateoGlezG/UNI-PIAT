package es.upm.piat.crowdsense.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import es.upm.piat.crowdsense.xml.LocalDateTimeAdapter;

/**
 * EJERCICIO 1: A

import es.upm.piat.crowdsense.xml.LocalDateTimeAdapter;ñadir anotaciones JAXB a esta clase de la Actividad 1.
 * 
 * Además hay que añadir 2 campos nuevos para el XML:
 * - sondaRef: referencia a la sonda (atributo XML)
 * - secuencia: número de secuencia (atributo XML)
 * 
 * Anotaciones a usar:
 * - @XmlAccessorType(XmlAccessType.FIELD)
 * - @XmlType(propOrder = {"timestamp", "location", "rssi", "macAddress", "ssid"})
 * - @XmlAttribute para id, sondaRef, secuencia
 * - @XmlElement para timestamp, location, rssi, macAddress, ssid
 * - @XmlJavaTypeAdapter(LocalDateTimeAdapter.class) para timestamp
 */
// TODO: Añadir @XmlAccessorType(XmlAccessType.FIELD)
// TODO: Añadir @XmlType(propOrder = {"timestamp", "location", "rssi", "macAddress", "ssid"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"timestap", "location", "rssi", "macAddress", "ssid"})
public class WifiProbe {
    
    // TODO: Añadir @XmlAttribute(name = "id", required = true)
	@XmlAttribute(name = "id", required=true)
    private String id;
    
    // TODO: Añadir campo sondaRef con @XmlAttribute(name = "sondaRef", required = true)
	@XmlAttribute(name="sondaRef", required=true)
    private String sondaRef;
    
    // TODO: Añadir campo secuencia con @XmlAttribute(name = "secuencia")
	@XmlAttribute(name="secuencia")
    private Integer secuencia;
    
    // TODO: Añadir @XmlElement(required = true) y @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	@XmlElement(required = true)
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class) //adapto el dato usando esa clase
    private LocalDateTime timestamp;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required = true)
    private LocationID location;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required = true)
    private int rssi;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required = true)
    private String macAddress;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required = true)
    private String ssid;

    public WifiProbe() {}

    public WifiProbe(LocalDateTime timestamp, LocationID location, int rssi, String macAddress, String ssid) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = timestamp;
        this.location = location;
        this.rssi = rssi;
        this.macAddress = macAddress;
        this.ssid = ssid;
    }

    // Getters
    public String getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public LocationID getLocation() { return location; }
    public int getRssi() { return rssi; }
    public String getMacAddress() { return macAddress; }
    public String getSsid() { return ssid; }
    
    // TODO: Añadir getters para sondaRef y secuencia
    // public String getSondaRef() { return sondaRef; }
    // public Integer getSecuencia() { return secuencia; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setLocation(LocationID location) { this.location = location; }
    public void setRssi(int rssi) { this.rssi = rssi; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public void setSsid(String ssid) { this.ssid = ssid; }
    
    // TODO: Añadir setters para sondaRef y secuencia
    // public void setSondaRef(String sondaRef) { this.sondaRef = sondaRef; }
    // public void setSecuencia(Integer secuencia) { this.secuencia = secuencia; }

    @Override
    public String toString() {
        String red = ssid.isEmpty() ? "<Oculta>" : ssid;
        return String.format("[%s] MAC:%s | Señal:%d dBm | %s | Buscando: '%s'", 
                timestamp, macAddress, rssi, location, red);
    }
}
