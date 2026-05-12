package es.upm.piat.crowdsense.sax;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

/**
 * Fachada que orquesta la conversión XML → JSON usando SAX.
 *
 * Esta clase no implementa lógica de parsing directamente:
 * delega en CrowdSenseHandler (el handler) y en SAXParser (del JDK).
 */
public class XmlToJsonConverter {

    private final SAXParser parser;

    public XmlToJsonConverter() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // Activar namespace awareness: necesario porque el XML raíz
        // tiene xmlns="http://upm.es/piat/crowdsense"
        factory.setNamespaceAware(true);

        // Seguridad: deshabilitar DTD externas (buena práctica siempre)
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

        this.parser = factory.newSAXParser();
    }

    // =========================================================================
    // MÉTODOS PÚBLICOS
    // =========================================================================

    /**
     * Convierte un fichero XML en JSON.
     *
     * @param xmlFile  Ruta al fichero generado por la Actividad 3
     * @return         String con el JSON resultante
     */
    public String convert(File xmlFile) throws Exception {
        CrowdSenseHandler handler = new CrowdSenseHandler();
        parser.parse(xmlFile, handler);
        return handler.getJson();
    }

    /**
     * Convierte un XML dado como String en JSON.
     * Útil para testing sin necesidad de fichero físico.
     */
    public String convert(String xmlContent) throws Exception {
        CrowdSenseHandler handler = new CrowdSenseHandler();
        InputSource source = new InputSource(new StringReader(xmlContent));
        parser.parse(source, handler);
        return handler.getJson();
    }

    /**
     * Convierte un XML desde un InputStream.
     * Útil si el XML llega por red o desde un recurso classpath.
     */
    public String convert(InputStream xmlStream) throws Exception {
        CrowdSenseHandler handler = new CrowdSenseHandler();
        parser.parse(xmlStream, handler);
        return handler.getJson();
    }

    // =========================================================================
    // UTILIDAD: pretty-print básico del JSON resultante
    // =========================================================================

    /**
     * Formatea el JSON con indentación legible.
     * Implementación sencilla sin dependencias: itera carácter a carácter.
     */
    public static String prettyPrint(String json) {
        StringBuilder sb = new StringBuilder();
        int indent = 0;
        boolean inString = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            // Gestión de strings (no indentamos dentro de valores string)
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
                sb.append(c);
                continue;
            }

            if (inString) {
                sb.append(c);
                continue;
            }

            switch (c) {
                case '{', '[' -> {
                    sb.append(c);
                    sb.append('\n');
                    indent++;
                    appendIndent(sb, indent);
                }
                case '}', ']' -> {
                    sb.append('\n');
                    indent--;
                    appendIndent(sb, indent);
                    sb.append(c);
                }
                case ',' -> {
                    sb.append(c);
                    sb.append('\n');
                    appendIndent(sb, indent);
                }
                case ':' -> sb.append(": ");
                default   -> sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void appendIndent(StringBuilder sb, int level) {
        sb.append("  ".repeat(Math.max(0, level)));
    }
}
