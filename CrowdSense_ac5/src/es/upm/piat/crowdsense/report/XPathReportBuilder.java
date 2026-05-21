package es.upm.piat.crowdsense.report;

import java.io.File;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Genera un informe XML con los resultados de las consultas XPath.
 *
 * Usa DOM para CONSTRUIR (no para leer) el XML de salida.
 * Esto ilustra el uso bidireccional de DOM:
 *   - En XPathQueryEngine lo usamos para LEER un XML existente.
 *   - Aquí lo usamos para CREAR un XML nuevo desde cero.
 *
 * Estructura del informe generado:
 *
 * <informeXPath generadoEn="...">
 *   <fuente>crowdsense-project.xml</fuente>
 *   <seccion nivel="1" titulo="Navegación Básica">
 *     <consulta>
 *       <expresion>//cs:sonda/cs:nombre</expresion>
 *       <descripcion>Nombres de todas las sondas</descripcion>
 *       <resultado tipo="lista">
 *         <item>Sonda-L01</item>
 *         <item>Sonda-A02</item>
 *       </resultado>
 *     </consulta>
 *     ...
 *   </seccion>
 *   ...
 * </informeXPath>
 */
public class XPathReportBuilder {

    private final Document doc;
    private final Element root;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================

    public XPathReportBuilder(String fuenteXml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.doc = db.newDocument();

        // Elemento raíz del informe
        this.root = doc.createElement("informeXPath");
        root.setAttribute("generadoEn", LocalDateTime.now().toString());
        root.setAttribute("herramienta", "javax.xml.xpath");
        doc.appendChild(root);

        // Fuente de datos
        Element fuente = doc.createElement("fuente");
        fuente.setTextContent(fuenteXml);
        root.appendChild(fuente);
    }

    // =========================================================================
    // API PÚBLICA
    // =========================================================================

    /**
     * Añade una sección al informe (agrupa consultas del mismo nivel).
     * Devuelve la propia sección para poder añadirle consultas encadenadamente.
     */
    public Element addSeccion(String nivel, String titulo) {
        Element seccion = doc.createElement("seccion");
        seccion.setAttribute("nivel", nivel);
        seccion.setAttribute("titulo", titulo);
        root.appendChild(seccion);
        return seccion;
    }

    /**
     * Añade una consulta con resultado de texto simple (un solo valor).
     */
    public void addConsultaScalar(Element seccion, String expresion,
                                   String descripcion, String resultado) {
        Element consulta = buildConsultaBase(seccion, expresion, descripcion);

        Element res = doc.createElement("resultado");
        res.setAttribute("tipo", "escalar");
        res.setTextContent(resultado);
        consulta.appendChild(res);
    }

    /**
     * Añade una consulta con resultado numérico.
     */
    public void addConsultaNumero(Element seccion, String expresion,
                                   String descripcion, double resultado) {
        Element consulta = buildConsultaBase(seccion, expresion, descripcion);

        Element res = doc.createElement("resultado");
        res.setAttribute("tipo", "numero");
        // Si es entero lo mostramos sin decimales
        if (resultado == Math.floor(resultado) && !Double.isInfinite(resultado)) {
            res.setTextContent(String.valueOf((long) resultado));
        } else {
            res.setTextContent(String.format("%.2f", resultado));
        }
        consulta.appendChild(res);
    }

    /**
     * Añade una consulta con resultado de lista (múltiples nodos).
     */
    public void addConsultaLista(Element seccion, String expresion,
                                  String descripcion, List<String> items) {
        Element consulta = buildConsultaBase(seccion, expresion, descripcion);

        Element res = doc.createElement("resultado");
        res.setAttribute("tipo", "lista");
        res.setAttribute("count", String.valueOf(items.size()));

        for (String item : items) {
            Element e = doc.createElement("item");
            e.setTextContent(item);
            res.appendChild(e);
        }
        consulta.appendChild(res);
    }

    /**
     * Serializa el informe DOM a un fichero XML con formato legible.
     */
    public void writeToFile(File outputFile) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        transformer.transform(new DOMSource(doc), new StreamResult(outputFile));
    }

    /**
     * Serializa el informe DOM a String (útil para preview en consola).
     */
    public String writeToString() throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StringWriter sw = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }

    // =========================================================================
    // INTERNOS
    // =========================================================================

    private Element buildConsultaBase(Element seccion, String expresion, String descripcion) {
        Element consulta = doc.createElement("consulta");

        Element expr = doc.createElement("expresion");
        expr.setTextContent(expresion);
        consulta.appendChild(expr);

        Element desc = doc.createElement("descripcion");
        desc.setTextContent(descripcion);
        consulta.appendChild(desc);

        seccion.appendChild(consulta);
        return consulta;
    }
}
