package es.upm.piat.crowdsense.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.upm.piat.crowdsense.xml.LocalDateTimeAdapter;

/**
 * Objeto que representa una trama WiFi limpia y validada.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"timestamp", "location", "rssi", "macAddress", "ssid"})
public class WifiProbe {
    
    // Campos añadidos para Act.3 (atributos XML)
    @XmlAttribute(name = "id", required = true)
    private String id;
    
    @XmlAttribute(name = "sondaRef", required = true)
    private String sondaRef;
    
    @XmlAttribute(name = "secuencia")
    private Integer secuencia;
    
    // Campos originales de Act.1 (elementos XML)
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime timestamp;
    
    @XmlElement(required = true)
    private LocationID location;
    
    @XmlElement(required = true)
    private int rssi;
    
    @XmlElement(required = true)
    private String macAddress;
    
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
    public String getSondaRef() { return sondaRef; }
    public Integer getSecuencia() { return secuencia; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public LocationID getLocation() { return location; }
    public int getRssi() { return rssi; }
    public String getMacAddress() { return macAddress; }
    public String getSsid() { return ssid; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setSondaRef(String sondaRef) { this.sondaRef = sondaRef; }
    public void setSecuencia(Integer secuencia) { this.secuencia = secuencia; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setLocation(LocationID location) { this.location = location; }
    public void setRssi(int rssi) { this.rssi = rssi; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public void setSsid(String ssid) { this.ssid = ssid; }

    @Override
    public String toString() {
        String red = ssid.isEmpty() ? "<Oculta>" : ssid;
        return String.format("[%s] MAC:%s | Señal:%d dBm | %s | Buscando: '%s'", 
                timestamp, macAddress, rssi, location, red);
    }
}
