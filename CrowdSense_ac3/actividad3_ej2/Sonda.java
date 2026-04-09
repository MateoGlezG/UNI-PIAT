package es.upm.piat.crowdsense.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.upm.piat.crowdsense.xml.LocalDateAdapter;

/**
 * Representa una sonda/sensor de captura WiFi.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"nombre", "ubicacionRef", "macSonda", "fechaInstalacion", "probes"})
public class Sonda {
    
    @XmlAttribute(name = "id", required = true)
    private String id;
    
    @XmlAttribute(name = "status")
    private String status = "ACTIVE";
    
    @XmlElement(required = true)
    private String nombre;
    
    @XmlElement(required = true)
    private String ubicacionRef;
    
    @XmlElement(required = true)
    private String macSonda;
    
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaInstalacion;
    
    @XmlElementWrapper(name = "probes")
    @XmlElement(name = "probe")
    private List<WifiProbe> probes = new ArrayList<>();
    
    public Sonda() {}
    
    public Sonda(String ubicacionRef) {
        this.id = UUID.randomUUID().toString();
        this.ubicacionRef = ubicacionRef;
        this.nombre = "Sonda-" + ubicacionRef.substring(ubicacionRef.lastIndexOf('.') + 1);
        this.macSonda = String.format("AA:BB:CC:%02X:%02X:%02X",
                (int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
        this.fechaInstalacion = LocalDate.now();
    }
    
    public void addProbe(WifiProbe probe) {
        probe.setSondaRef(this.id);
        probe.setSecuencia(probes.size() + 1);
        probes.add(probe);
    }
    
    // Getters
    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getNombre() { return nombre; }
    public String getUbicacionRef() { return ubicacionRef; }
    public String getMacSonda() { return macSonda; }
    public LocalDate getFechaInstalacion() { return fechaInstalacion; }
    public List<WifiProbe> getProbes() { return probes; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUbicacionRef(String ubicacionRef) { this.ubicacionRef = ubicacionRef; }
    public void setMacSonda(String macSonda) { this.macSonda = macSonda; }
    public void setFechaInstalacion(LocalDate fechaInstalacion) { this.fechaInstalacion = fechaInstalacion; }
    public void setProbes(List<WifiProbe> probes) { this.probes = probes; }
}
