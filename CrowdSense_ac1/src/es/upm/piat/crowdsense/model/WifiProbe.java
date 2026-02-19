package es.upm.piat.crowdsense.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Objeto que representa una trama WiFi limpia y validada.
 */
public class WifiProbe {
    private String id;
    private LocalDateTime timestamp;
    private LocationID location;
    private int rssi;
    private String macAddress;
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
    
    // Setters
    public void setId(String id) { this.id = id; }
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
