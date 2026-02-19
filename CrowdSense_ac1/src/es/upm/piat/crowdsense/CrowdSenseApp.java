package es.upm.piat.crowdsense;

import es.upm.piat.crowdsense.ingestion.LogIngestor;
import es.upm.piat.crowdsense.model.WifiProbe;
import java.util.List;
import java.util.Map;

public class CrowdSenseApp {

    public static void main(String[] args) {
        // Asegúrate de que este fichero está en la carpeta raíz de tu proyecto
        String file = "raw_datalake.log"; 
        
        LogIngestor ingestor = new LogIngestor();

        // 1. Fase de Ingesta
        System.out.println("=== SISTEMA CROWDSENSE 1.0 ===");
        ingestor.processFile(file);

        // 2. Verificación de Resultados
        Map<String, List<WifiProbe>> db = ingestor.getDatabase();
        
        if (!db.isEmpty()) {
            // Imprimir ejemplo del primer dispositivo encontrado
            String sampleMac = db.keySet().iterator().next();
            System.out.println("\n--- Ejemplo de Datos Estructurados ---");
            System.out.println("Historial del dispositivo: " + sampleMac);
            
            List<WifiProbe> history = db.get(sampleMac);
            // Mostrar las primeras 3 detecciones
            for (int i = 0; i < Math.min(3, history.size()); i++) {
                System.out.println("   " + history.get(i));
            }
        }
    }
}