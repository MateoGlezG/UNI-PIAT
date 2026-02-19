package es.upm.piat.crowdsense.ingestion;

import es.upm.piat.crowdsense.model.LocationID;
import es.upm.piat.crowdsense.model.WifiProbe;
import es.upm.piat.crowdsense.util.CrowdRegexManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIngestor {

    // Base de datos en memoria: Clave = MAC, Valor = Lista de detecciones
    private Map<String, List<WifiProbe>> database;

    public LogIngestor() {
        this.database = new HashMap<>();
    }

    public void processFile(String filePath) {
        Pattern pattern = CrowdRegexManager.buildLogPattern();
        int lineasValidas = 0;
        int ruido = 0;

        System.out.println(">>> Leyendo fichero: " + filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                
                if (m.find()) {
                    // Si entra aquí, la línea es válida y tenemos los datos capturados
                    WifiProbe probe = mapToObj(m);
                    addToDatabase(probe);
                    lineasValidas++;
                } else {
                    // Si no casa, es ruido ([BOOT], [WARN], etc.)
                	if (line.startsWith("[DATA]")) {
                        System.out.println("ALERTA: Línea de DATOS rechazada por la RegEx: " + line);
                    }
                    ruido++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error crítico leyendo fichero: " + e.getMessage());
        }

        System.out.println(">>> Ingesta Finalizada.");
        System.out.println("    - Tramas procesadas: " + lineasValidas);
        System.out.println("    - Líneas descartadas: " + ruido);
        System.out.println("    - Dispositivos únicos: " + database.size());
    }

    /**
     * Convierte los grupos de captura de la RegEx a un objeto Java fuertemente tipado.
     */
    private WifiProbe mapToObj(Matcher m) {
        // TODO 8: Extraer los datos usando m.group(X)
        // Recordad el orden de los paréntesis en CrowdRegexManager, implemenatr extraer la información para lor grupos
        
        // Pista: LocalDateTime.parse(m.group(1), DateTimeFormatter.ISO_DATE_TIME);
    	LocalDateTime ts = null; 
        LocationID loc = null; 
        int rssi = 0;
        String mac = "";
        String ssid = "";

        return new WifiProbe(ts, loc, rssi, mac, ssid);
    }

    private void addToDatabase(WifiProbe probe) {
        // Si la MAC no existe, crea una lista nueva. Si existe, añade a la lista.
        database.computeIfAbsent(probe.getMacAddress(), k -> new ArrayList<>()).add(probe);
    }

    public Map<String, List<WifiProbe>> getDatabase() {
        return database;
    }
}