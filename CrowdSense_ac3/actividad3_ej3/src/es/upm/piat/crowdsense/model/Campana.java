package es.upm.piat.crowdsense.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * Representa una campaña de medición dentro del proyecto.
 */

public class Campana {
    
    private String id;
    
    private String nombre;
    
    private LocalDateTime fechaInicio;

    private List<Sonda> sondas = new ArrayList<>();

    private ResumenCampana resumen;
    
    public Campana() {}
    
    public Campana(String nombre, LocalDateTime fechaInicio) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }
    
    public void addSonda(Sonda sonda) {
        sondas.add(sonda);
    }
    
    public void calcularResumen() {
        resumen = new ResumenCampana();
        int totalProbes = 0;
        double sumaRssi = 0;
        Set<String> dispositivosUnicos = new HashSet<>();
        
        for (Sonda sonda : sondas) {
            for (WifiProbe probe : sonda.getProbes()) {
                totalProbes++;
                sumaRssi += probe.getRssi();
                dispositivosUnicos.add(probe.getMacAddress().toLowerCase());
            }
        }
        
        resumen.setTotalProbes(totalProbes);
        resumen.setDispositivosUnicos(dispositivosUnicos.size());
        resumen.setRssiPromedio(totalProbes > 0 ? sumaRssi / totalProbes : 0);
    }
    
    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public List<Sonda> getSondas() { return sondas; }
    public ResumenCampana getResumen() { return resumen; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setSondas(List<Sonda> sondas) { this.sondas = sondas; }
    public void setResumen(ResumenCampana resumen) { this.resumen = resumen; }

    public static class ResumenCampana {
     
        private Integer totalProbes;
    
        private Integer dispositivosUnicos;
      
        private Double rssiPromedio;
        
        public Integer getTotalProbes() { return totalProbes; }
        public void setTotalProbes(Integer totalProbes) { this.totalProbes = totalProbes; }
        public Integer getDispositivosUnicos() { return dispositivosUnicos; }
        public void setDispositivosUnicos(Integer dispositivosUnicos) { this.dispositivosUnicos = dispositivosUnicos; }
        public Double getRssiPromedio() { return rssiPromedio; }
        public void setRssiPromedio(Double rssiPromedio) { this.rssiPromedio = rssiPromedio; }
    }
}
