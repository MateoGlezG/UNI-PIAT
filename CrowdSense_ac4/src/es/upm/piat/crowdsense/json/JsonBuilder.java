package es.upm.piat.crowdsense.json;

/**
 * Constructor de JSON sin dependencias externas.
 *
 * Gestiona automáticamente las comas entre campos, el anidamiento
 * de objetos y arrays, y el escapado de cadenas.
 *
 * Uso típico:
 *   JsonBuilder b = new JsonBuilder();
 *   b.startObject();
 *     b.addString("nombre", "PIAT");
 *     b.addNumber("año", "2025");
 *     b.addKey("datos"); b.startArray();
 *       b.startObject(); b.addString("x", "1"); b.endObject();
 *     b.endArray();
 *   b.endObject();
 *   String json = b.build();
 */
public class JsonBuilder {

    private final StringBuilder sb = new StringBuilder();

    /**
     * Pila de contextos: 'O' = dentro de un Object, 'A' = dentro de un Array.
     * Necesaria para saber cuándo poner coma y cuándo no.
     */
    private final char[] contextStack = new char[64];
    private int stackTop = -1;

    /**
     * firstInContext[i] = true si aún no hemos escrito ningún elemento
     * en el contexto i (no hay que poner coma antes del primero).
     */
    private final boolean[] firstInContext = new boolean[64];

    // =========================================================================
    // ESTRUCTURA
    // =========================================================================

    /** Abre un objeto JSON: { */
    public JsonBuilder startObject() {
        maybeComma();
        sb.append('{');
        push('O');
        return this;
    }

    /** Cierra un objeto JSON: } */
    public JsonBuilder endObject() {
        sb.append('}');
        pop();
        return this;
    }

    /** Abre un array JSON: [ */
    public JsonBuilder startArray() {
        maybeComma();
        sb.append('[');
        push('A');
        return this;
    }

    /** Cierra un array JSON: ] */
    public JsonBuilder endArray() {
        sb.append(']');
        pop();
        return this;
    }

    /**
     * Escribe sólo la clave: "clave":
     * Usar antes de startObject() o startArray() anidados.
     */
    public JsonBuilder addKey(String key) {
        maybeComma();
        sb.append('"').append(escape(key)).append('"').append(':');
        // Marcamos que ya pusimos algo en este nivel, pero el valor
        // (el { o [) se añadirá a continuación sin coma adicional.
        suppressNextComma();
        return this;
    }

    // =========================================================================
    // VALORES
    // =========================================================================

    /** Añade "clave": "valor" — el valor se escapa correctamente */
    public JsonBuilder addString(String key, String value) {
        maybeComma();
        sb.append('"').append(escape(key)).append('"')
          .append(':')
          .append('"').append(escape(value)).append('"');
        return this;
    }

    /**
     * Añade "clave": valor — el valor se escribe como número (sin comillas).
     * Acepta el texto tal como viene del XML; si no es parseable lo pone como null.
     */
    public JsonBuilder addNumber(String key, String rawValue) {
        maybeComma();
        sb.append('"').append(escape(key)).append('"').append(':');
        if (rawValue == null || rawValue.isBlank()) {
            sb.append("null");
        } else {
            try {
                // Normaliza: quita espacios y convierte coma decimal a punto
                String normalized = rawValue.trim().replace(',', '.');
                if (normalized.contains(".")) {
                    double d = Double.parseDouble(normalized);
                    if (Double.isNaN(d) || Double.isInfinite(d)) {
                        sb.append("null");
                    } else {
                        sb.append(d);
                    }
                } else {
                    sb.append(Long.parseLong(normalized));
                }
            } catch (NumberFormatException e) {
                sb.append("null");
            }
        }
        return this;
    }

    // =========================================================================
    // RESULTADO
    // =========================================================================

    /** Devuelve el JSON construido. */
    public String build() {
        return sb.toString();
    }

    // =========================================================================
    // INTERNOS
    // =========================================================================

    private void push(char type) {
        stackTop++;
        contextStack[stackTop] = type;
        firstInContext[stackTop] = true;
    }

    private void pop() {
        if (stackTop >= 0) stackTop--;
    }

    /**
     * Escribe una coma si corresponde (no delante del primer elemento).
     * También actualiza el flag firstInContext para los siguientes.
     */
    private void maybeComma() {
        if (stackTop >= 0) {
            if (!firstInContext[stackTop]) {
                sb.append(',');
            } else {
                firstInContext[stackTop] = false;
            }
        }
    }

    /**
     * Usado tras addKey(): el objeto/array que viene a continuación
     * no debe añadir una coma adicional (la clave ya "consumió" el slot).
     */
    private void suppressNextComma() {
        if (stackTop >= 0) {
            // Restauramos firstInContext a false: el próximo elemento
            // (el { o [) verá que ya hay algo y no pondrá coma.
            // En realidad lo que hacemos es "marcar como no-primero" 
            // para que maybeComma() no ponga coma en el valor inmediato.
            firstInContext[stackTop] = true; // <- el valor del pair es el "primero" en su nivel
        }
    }

    /** Escapa caracteres especiales JSON en una cadena. */
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
    
}
