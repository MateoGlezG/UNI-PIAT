package es.upm.piat.crowdsense.util;

import java.util.regex.Pattern;

public class CrowdRegexManager {

	// --- NIVEL 1: ÁTOMOS (Piezas básicas) ---

    // TODO 1: Definir el inicio de línea para filtrar ruido (Debe empezar por [DATA])
    // Pista: Usar ancla de inicio y escapar (escapar es poner un simbolo tal cual que tiene otro significado) corchetes, en java para escapar se usan dos barras \\
    private static final String HEADER_TAG  = "\\[DATA\\]"; 

    private static final String SEP         = "\\s+\\|\\s+";     // Separador " | " (Ya hecho)
    
    // TODO 2: Definir el punto literal (escapado)
    private static final String DOT         = "\\.";              
    
    private static final String SRC_TAG     = "SRC:";

    // TODO 3: Timestamp ISO-8601 (Ej: 2024-02-08T12:00:00Z) -> GRUPO 1
    // Pista: \d{4}-...
    private static final String TIMESTAMP   = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)"; //G1


    // --- DESGLOSE DE UBICACIÓN (Ej: UPM-N-10.B1.P0.L01) ---
    // TODO 4: Definir los grupos de captura para cada parte
    private static final String REGEX_BUILDING = "(UPM-N-\\d{2})"; // G2: Ej. UPM-N-10
    private static final String REGEX_BLOCK    = "(\\w\\d)"; // G3: Ej. B1
    private static final String REGEX_FLOOR    = "(\\w\\d)"; // G4: Ej. P0
    private static final String REGEX_ROOM     = "(\\w\\d{2})"; // G5: Ej. L01, A05, H01...

    
    // --- DATOS TÉCNICOS ---
    // TODO 5: RSSI (Número negativo) y saltar campos FC/SEQ -> GRUPO 6
    private static final String RSSI_SKIP		= "RSSI:(-?\\d{2})"; // Pista: RSSI:...
    private static final String MAC_ADDR    	= "MAC:(([a-zA-Z0-9]{2}:){5}[a-zA-Z0-9]{2})"; // G7: MAC: ...
    // TODO 6: SSID (Puede estar vacío o tener espacios) -> GRUPO 8
    private static final String SSID        	= "SSID:(.*)";


    // --- NIVEL 2: MOLÉCULAS (Combinación de átomos) ---

    /**
     * Construimos la Ubicación sumando sus partes y los puntos separadores.
     * Resultado: SRC:(UPM...)\.(B...)\.(P...)\.([A-Z]...)
     */
    private static final String LOCATION_FULL = SRC_TAG+REGEX_BUILDING+DOT+REGEX_BLOCK+DOT+REGEX_FLOOR+DOT+REGEX_ROOM; //Se trata de concatenar los REGEX de location


    // --- NIVEL 3: EL ORGANISMO (Patrón Final) ---
    public static Pattern buildLogPattern() {
        StringBuilder sb = new StringBuilder();
        
        // TODO 7: String Regex completo usando append de StringBuilder();
        sb.append("^");
        sb.append(HEADER_TAG);
        sb.append("\\s");
        sb.append(TIMESTAMP);
        sb.append(SEP);
        sb.append(LOCATION_FULL);
        sb.append(SEP);
        sb.append(RSSI_SKIP);
        sb.append(SEP).append("FC:0x\\d+").append(SEP).append("SEQ:\\d+");
        sb.append(SEP);
        sb.append(MAC_ADDR);
        sb.append(SEP);
        sb.append(SSID);
        sb.append("$");

        return Pattern.compile(sb.toString());
    }
}