package es.upm.piat.crowdsense;

import java.io.File;

import es.upm.piat.crowdsense.ingestion.LogIngestor;
import es.upm.piat.crowdsense.model.CrowdSenseProject;
import es.upm.piat.crowdsense.xml.ProjectBuilder;
import es.upm.piat.crowdsense.xml.ProjectExporter;

public class Actividad3App {
    
    public static void main(String[] args) {
        String file = "raw_datalake.log";
        
        System.out.println("=== SISTEMA CROWDSENSE 3.0 - JAXB ===");
        System.out.println();
        
        try {
            // FASE 1: Cargar datos (igual que Act.1)
            System.out.println(">>> FASE 1: Procesando log...");
            LogIngestor ingestor = new LogIngestor();
            ingestor.processFile(file);
            System.out.println();
            
            // FASE 2: Construir proyecto XML
            System.out.println(">>> FASE 2: Construyendo proyecto XML...");
            ProjectBuilder builder = new ProjectBuilder(
                    "Análisis WiFi Edificio Norte",
                    "Estudiante PIAT",
                    "estudiante@alumnos.upm.es"
            );
            CrowdSenseProject project = builder.build(ingestor.getProbes());
            
            System.out.println("    Proyecto: " + project.getMetadata().getNombre());
            System.out.println("    Campañas: " + project.getCampanas().size());
            System.out.println("    Sondas: " + project.getEstadisticas().getTotalSondas());
            System.out.println("    Probes: " + project.getEstadisticas().getTotalProbes());
            System.out.println();
            
            // FASE 3: Exportar a XML
            System.out.println(">>> FASE 3: Exportando a XML...");
            ProjectExporter exporter = new ProjectExporter();
            File outputFile = new File("crowdsense-project.xml");
            exporter.exportToFile(project, outputFile);
            
            System.out.println("    Fichero: " + outputFile.getName());
            System.out.println("    Tamaño: " + outputFile.length() + " bytes");
            System.out.println();
            
            System.out.println("=== PROCESO COMPLETADO ===");
            System.out.println("Valida con: xmllint --schema crowdsense-project.xsd crowdsense-project.xml --noout");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
