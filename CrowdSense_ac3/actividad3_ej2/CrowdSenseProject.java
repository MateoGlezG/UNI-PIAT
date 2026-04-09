package es.upm.piat.crowdsense.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.upm.piat.crowdsense.xml.LocalDateTimeAdapter;

/**
 * EJERCICIO 2: Completar la clase raíz del documento XML.
 * 
 * Anotaciones necesarias:
 * - @XmlRootElement(name = "crowdSenseProject", namespace = "http://upm.es/piat/crowdsense")
 * - @XmlAccessorType(XmlAccessType.FIELD)
 * - @XmlType(propOrder = {"metadata", "campanas", "estadisticas"})
 * - @XmlAttribute para version y generadoEn
 * - @XmlElementWrapper + @XmlElement para campanas
 * 
 * También hay que implementar calcularEstadisticas()
 */
// TODO: Añadir @XmlRootElement(name = "crowdSenseProject", namespace = "http://upm.es/piat/crowdsense")
// TODO: Añadir @XmlAccessorType(XmlAccessType.FIELD)
// TODO: Añadir @XmlType(propOrder = {"metadata", "campanas", "estadisticas"})
public class CrowdSenseProject {
    
    // TODO: Añadir @XmlAttribute
    private String version = "1.0";
    
    // TODO: Añadir @XmlAttribute y @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime generadoEn;
    
    // TODO: Añadir @XmlElement(required = true)
    private Metadata metadata;
    
    // TODO: Añadir @XmlElementWrapper(name = "campanas")
    // TODO: Añadir @XmlElement(name = "campana")
    private List<Campana> campanas = new ArrayList<>();
    
    // TODO: Añadir @XmlElement
    private Estadisticas estadisticas;
    
    public CrowdSenseProject() {
        this.generadoEn = LocalDateTime.now();
    }
    
    public CrowdSenseProject(String nombre, String responsable, String email) {
        this();
        this.metadata = new Metadata(nombre, responsable, email);
    }
    
    public void addCampana(Campana campana) {
        campanas.add(campana);
    }
    
    /**
     * TODO: Implementar cálculo de estadísticas.
     * 
     * Recorrer: campanas -> sondas -> probes
     * Calcular: totalCampanas, totalSondas, totalProbes, dispositivosUnicosGlobal
     */
    public void calcularEstadisticas() {
        estadisticas = new Estadisticas();
        
        // TODO: Implementar
        // int totalProbes = 0;
        // int totalSondas = 0;
        // Set<String> dispositivosUnicos = new HashSet<>();
        // 
        // for (Campana campana : campanas) {
        //     totalSondas += campana.getSondas().size();
        //     for (Sonda sonda : campana.getSondas()) {
        //         for (WifiProbe probe : sonda.getProbes()) {
        //             totalProbes++;
        //             dispositivosUnicos.add(probe.getMacAddress().toLowerCase());
        //         }
        //     }
        // }
        // 
        // estadisticas.setTotalCampanas(campanas.size());
        // estadisticas.setTotalSondas(totalSondas);
        // estadisticas.setTotalProbes(totalProbes);
        // estadisticas.setDispositivosUnicosGlobal(dispositivosUnicos.size());
    }
    
    // Getters
    public String getVersion() { return version; }
    public LocalDateTime getGeneradoEn() { return generadoEn; }
    public Metadata getMetadata() { return metadata; }
    public List<Campana> getCampanas() { return campanas; }
    public Estadisticas getEstadisticas() { return estadisticas; }
    
    // Setters
    public void setVersion(String version) { this.version = version; }
    public void setGeneradoEn(LocalDateTime generadoEn) { this.generadoEn = generadoEn; }
    public void setMetadata(Metadata metadata) { this.metadata = metadata; }
    public void setCampanas(List<Campana> campanas) { this.campanas = campanas; }
    public void setEstadisticas(Estadisticas estadisticas) { this.estadisticas = estadisticas; }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Metadata {
        @XmlElement(required = true)
        private String id;
        @XmlElement(required = true)
        private String nombre;
        @XmlElement(required = true)
        private String responsable;
        @XmlElement(required = true)
        private String email;
        @XmlElement(required = true)
        @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
        private LocalDateTime fechaCreacion;
        
        public Metadata() {}
        
        public Metadata(String nombre, String responsable, String email) {
            this.id = UUID.randomUUID().toString();
            this.nombre = nombre;
            this.responsable = responsable;
            this.email = email;
            this.fechaCreacion = LocalDateTime.now();
        }
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getResponsable() { return responsable; }
        public void setResponsable(String responsable) { this.responsable = responsable; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public LocalDateTime getFechaCreacion() { return fechaCreacion; }
        public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Estadisticas {
        @XmlElement
        private Integer totalCampanas;
        @XmlElement
        private Integer totalSondas;
        @XmlElement
        private Integer totalProbes;
        @XmlElement
        private Integer dispositivosUnicosGlobal;
        
        public Integer getTotalCampanas() { return totalCampanas; }
        public void setTotalCampanas(Integer totalCampanas) { this.totalCampanas = totalCampanas; }
        public Integer getTotalSondas() { return totalSondas; }
        public void setTotalSondas(Integer totalSondas) { this.totalSondas = totalSondas; }
        public Integer getTotalProbes() { return totalProbes; }
        public void setTotalProbes(Integer totalProbes) { this.totalProbes = totalProbes; }
        public Integer getDispositivosUnicosGlobal() { return dispositivosUnicosGlobal; }
        public void setDispositivosUnicosGlobal(Integer d) { this.dispositivosUnicosGlobal = d; }
    }
}
