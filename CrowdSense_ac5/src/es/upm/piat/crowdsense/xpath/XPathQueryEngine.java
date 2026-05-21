package es.upm.piat.crowdsense.xpath;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Motor de consultas XPath sobre el XML de CrowdSense.
 *
 * A diferencia de SAX (Act.4), aquí usamos DOM: el XML se carga
 * entero en memoria como un árbol de nodos. Esto es necesario porque
 * XPath necesita poder navegar el árbol en cualquier dirección
 * (axes: parent, ancestor, following-sibling...), algo imposible
 * con un parser de eventos como SAX que solo avanza hacia adelante.
 *
 * Flujo interno:
 *   XML → DocumentBuilder (DOM) → Document → XPath.evaluate() → resultado
 */
public class XPathQueryEngine {

    // Namespace del XML generado por la Actividad 3
    private static final String NS_URI    = "http://upm.es/piat/crowdsense";
    private static final String NS_PREFIX = "cs";

    private final Document document;
    private final XPath xpath;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================

    public XPathQueryEngine(File xmlFile) throws Exception {

        // 1. Parsear el XML a DOM (con namespace awareness)
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true); // CRÍTICO: sin esto, XPath no ve los namespaces
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.document = db.parse(xmlFile);

        // 2. Configurar el motor XPath con el namespace de CrowdSense
        XPathFactory xpf = XPathFactory.newInstance();
        this.xpath = xpf.newXPath();
        this.xpath.setNamespaceContext(buildNamespaceContext());
    }

    // =========================================================================
    // MÉTODOS PÚBLICOS DE CONSULTA
    // =========================================================================

    /**
     * Evalúa una expresión XPath y devuelve el resultado como String.
     * Útil para: texto de un nodo, valor de atributo, resultado numérico.
     *
     * Ejemplos:
     *   queryString("/cs:crowdSenseProject/cs:metadata/cs:nombre") → "Análisis WiFi"
     *   queryString("count(//cs:sonda)")                           → "3"
     */
    public String queryString(String expression) throws Exception {
        XPathExpression expr = xpath.compile(expression);
        return expr.evaluate(document);
    }

    /**
     * Evalúa una expresión XPath y devuelve todos los nodos como lista de Strings.
     * Útil para: colecciones de elementos (todos los SSIDs, todas las MACs...).
     *
     * Ejemplos:
     *   queryList("//cs:probe/cs:ssid") → ["eduroam", "UPM-Guest", "eduroam", ...]
     */
    public List<String> queryList(String expression) throws Exception {
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

        List<String> results = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            results.add(nodes.item(i).getTextContent().trim());
        }
        return results;
    }

    /**
     * Evalúa una expresión XPath y devuelve el resultado como número.
     * Útil para: count(), sum(), min(), max(), string-length()...
     */
    public double queryNumber(String expression) throws Exception {
        XPathExpression expr = xpath.compile(expression);
        return (Double) expr.evaluate(document, XPathConstants.NUMBER);
    }

    /**
     * Evalúa una expresión XPath y devuelve true/false.
     * Útil para: existencia de nodos, comparaciones booleanas.
     *
     * Ejemplo: queryBoolean("//cs:sonda[@status='ACTIVE']") → true
     */
    public boolean queryBoolean(String expression) throws Exception {
        XPathExpression expr = xpath.compile(expression);
        return (Boolean) expr.evaluate(document, XPathConstants.BOOLEAN);
    }

    // =========================================================================
    // NAMESPACE CONTEXT
    // =========================================================================

    /**
     * Registra el namespace del XML de CrowdSense bajo el prefijo "cs".
     *
     * Sin esto, XPath no puede resolver las etiquetas con namespace y
     * todas las consultas devuelven resultados vacíos. Es el error más
     * común al trabajar con XPath sobre XML con namespaces.
     */
    private NamespaceContext buildNamespaceContext() {
        return new NamespaceContext() {

            @Override
            public String getNamespaceURI(String prefix) {
                if (NS_PREFIX.equals(prefix)) return NS_URI;
                return javax.xml.XMLConstants.NULL_NS_URI;
            }

            @Override
            public String getPrefix(String namespaceURI) {
                if (NS_URI.equals(namespaceURI)) return NS_PREFIX;
                return null;
            }

            @Override
            public Iterator<String> getPrefixes(String namespaceURI) {
                return List.of(NS_PREFIX).iterator();
            }
        };
    }
}
