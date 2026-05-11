package es.upm.piat.crowdsense.xml;

import java.time.LocalDateTime;
import java.util.*;

import es.upm.piat.crowdsense.model.*;

/**
 * Construye un CrowdSenseProject a partir de una lista de WifiProbes.
 */
public class ProjectBuilder {
    
    private final String projectName;
    private final String responsableNombre;
    private final String responsableEmail;
    
    public ProjectBuilder(String projectName, String responsableNombre, String responsableEmail) {
        this.projectName = projectName;
        this.responsableNombre = responsableNombre;
        this.responsableEmail = responsableEmail;
    }
    
    public CrowdSenseProject build(List<WifiProbe> probes) {
        CrowdSenseProject project = new CrowdSenseProject(
                projectName, responsableNombre, responsableEmail);
        
        // Agrupar probes por ubicación
        Map<String, List<WifiProbe>> probesPorUbicacion = new LinkedHashMap<>();
        for (WifiProbe probe : probes) {
            String key = probe.getLocation().toCompactString();
            probesPorUbicacion.computeIfAbsent(key, k -> new ArrayList<>()).add(probe);
        }
        
        // Crear sondas (una por ubicación)
        Map<String, Sonda> sondas = new LinkedHashMap<>();
        for (String ubicacionKey : probesPorUbicacion.keySet()) {
            Sonda sonda = new Sonda(ubicacionKey);
            sondas.put(ubicacionKey, sonda);
        }
        
        // Asignar probes a sus sondas
        for (Map.Entry<String, List<WifiProbe>> entry : probesPorUbicacion.entrySet()) {
            Sonda sonda = sondas.get(entry.getKey());
            for (WifiProbe probe : entry.getValue()) {
                sonda.addProbe(probe);
            }
        }
        
        // Crear campaña
        LocalDateTime fechaInicio = probes.isEmpty() ? LocalDateTime.now() 
                : probes.get(0).getTimestamp();
        Campana campana = new Campana("Captura Automática", fechaInicio);
        
        for (Sonda sonda : sondas.values()) {
            campana.addSonda(sonda);
        }
        campana.calcularResumen();
        
        project.addCampana(campana);
        project.calcularEstadisticas();
        
        return project;
    }
}
