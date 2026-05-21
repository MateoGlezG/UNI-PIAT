package es.upm.piat.crowdsense.xpath;

/**
 * Catálogo de expresiones XPath sobre el XML de CrowdSense.
 *
 * ESTE ES EL ÚNICO FICHERO QUE TIENES QUE COMPLETAR.
 *
 * Cada constante representa una consulta XPath. Tu tarea es escribir
 * la expresión XPath que satisface el enunciado descrito en el comentario
 * de cada constante. El resto del proyecto (motor, app, generador de
 * informe) ya está hecho y no necesita modificarse.
 *
 * NAMESPACE: el XML declara
 *     xmlns="http://upm.es/piat/crowdsense"
 * y XPathQueryEngine lo registra con el prefijo "cs". Por tanto TODAS
 * las expresiones deben referirse a los elementos como cs:elemento
 * (no como elemento). Si olvidas el prefijo, la consulta devolverá
 * resultado vacío sin lanzar error: es el fallo más típico al empezar.
 *
 * NIVELES:
 *   - Nivel 1: rutas absolutas y acceso a atributos.
 *   - Nivel 2: predicados [condición] para filtrar.
 *   - Nivel 3: funciones agregadas count(), sum(), string-length()...
 *
 * LIMITACIÓN XPATH 1.0: el motor del JDK solo implementa XPath 1.0.
 * Por tanto NO puedes usar min(), max(), distinct-values(), upper-case(),
 * etc. (esas son XPath 2.0). En el Nivel 3 verás un par de consultas que
 * solo extraen una lista de nodos: el cálculo posterior se hace en Java
 * (Actividad5App ya tiene esa parte resuelta para que tú solo te enfoques
 * en escribir el XPath).
 *
 * INSTRUCCIONES:
 *   - Sustituye los TODO por tu expresión XPath.
 *   - Compila y ejecuta Actividad5App.
 *   - Compara la salida XML generada (crowdsense-xpath-report.xml) con
 *     el fichero de referencia (crowdsense-xpath-report-esperado.xml).
 *     Si los <resultado> coinciden, tu XPath es correcto.
 *     
 *     DOM:
 *     <?xml version="1.0"?>
		crowdSenseProject @version @generadoEn
		L metadata
		|	L nombreProyecto
		|	L descripcion
		|	L- responsable
		|
		L estadisticas
		|	L numSondas
		|	L numProbes
		|
		L sondas
			L sonda @id @status
				L nombre
				L location - edificio, planta
				L probes
					L probe @timestamp
						L mac
						L ssid
						L rssi
 *     
 */
public final class CrowdSenseQueries {

    private CrowdSenseQueries() {} // Clase de constantes, no instanciable

    // =========================================================================
    // NIVEL 1 — NAVEGACIÓN BÁSICA
    // Acceso directo a nodos y atributos conocidos a partir de la raíz.
    // =========================================================================

    /**
     * Consulta 1.1: Nombre del proyecto.
     * Devolver el contenido textual del elemento <nombre> que está dentro
     * de <metadata> en la raíz <crowdSenseProject>.
     * Tipo de resultado: escalar (String).
     */
    public static final String NOMBRE_PROYECTO = "/cs:crowdSenseProject/cs:metada/cs:nombre"; // TODO: completar

    /**
     * Consulta 1.2: Versión del documento.
     * Devolver el valor del atributo "version" del elemento raíz
     * <crowdSenseProject>.
     * Tipo de resultado: escalar (String).
     * Pista: en XPath, los atributos se acceden con "@nombreAtributo".
     */
    public static final String VERSION = "/cs:crowdSenseProject@version"; // TODO: completar

    /**
     * Consulta 1.3: Fecha de generación del documento.
     * Devolver el valor del atributo "generadoEn" del elemento raíz.
     * Tipo de resultado: escalar (String).
     */
    public static final String GENERADO_EN = "/cs:crowdSenseProject@generadoEn"; // TODO: completar

    /**
     * Consulta 1.4: Responsable del proyecto.
     * Devolver el contenido del elemento <responsable> dentro de <metadata>.
     * Tipo de resultado: escalar (String).
     */
    public static final String RESPONSABLE = "/cs:crowdSenseProject/cs:metadata/cs:responsable"; // TODO:

    /**
     * Consulta 1.5: Total de campañas según las estadísticas.
     * Devolver el contenido del elemento <totalCampanas> dentro
     * de <estadisticas>.
     * Tipo de resultado: escalar (String, aunque represente un número).
     */
    public static final String TOTAL_CAMPANAS = ""; // TODO: completar

    /**
     * Consulta 1.6: Total de probes según las estadísticas.
     * Devolver el contenido del elemento <totalProbes> dentro
     * de <estadisticas>.
     * Tipo de resultado: escalar.
     */
    public static final String TOTAL_PROBES = "/cs:crowdSenseProject/cs:estadisticas/cs:numProbes"; // TODO: completar

    /**
     * Consulta 1.7: Nombres de TODAS las sondas del documento.
     * Devolver una lista con el contenido de cada elemento <nombre>
     * que cuelgue de cualquier <sonda>, sin importar a qué profundidad
     * esté en el documento.
     * Tipo de resultado: lista de strings.
     * Pista: el operador "//" busca en cualquier parte del árbol.
     */
    public static final String NOMBRES_SONDAS = "/cs:crowdSenseProject/cs:estadisticas/cs:numProbes"; // TODO: completar

    /**
     * Consulta 1.8: Ubicaciones (referencias) de todas las sondas.
     * Devolver el contenido de cada <ubicacionRef> dentro de cada <sonda>.
     * Tipo de resultado: lista.
     */
    public static final String UBICACIONES_SONDAS = "/cs:crowdSenseProject//cs:location"; // TODO: completar

    /**
     * Consulta 1.9: Todos los SSIDs capturados (con repeticiones).
     * Devolver el SSID de cada probe del documento, tal como aparecen
     * (con duplicados si los hay).
     * Tipo de resultado: lista.
     */
    public static final String TODOS_SSIDS = "//cs:probe:cs:ssid"; // TODO: completar // porque no me importa la profundidad en el DOM


    // =========================================================================
    // NIVEL 2 — PREDICADOS Y FILTROS
    // Uso de [condición] para seleccionar nodos que cumplan un criterio.
    // =========================================================================

    /**
     * Consulta 2.1: Sondas que están en estado ACTIVE.
     * Devolver el <nombre> de cada <sonda> cuyo atributo status valga "ACTIVE".
     * Tipo de resultado: lista.
     * Pista: filtros sobre atributos: //elemento[@atributo='valor'].
     */
    public static final String SONDAS_ACTIVAS = ""; // TODO: completar

    /**
     * Consulta 2.2: Probes con señal débil (RSSI menor que -70 dBm).
     * Devolver la <macAddress> de cada <probe> cuyo <rssi> sea menor
     * estrictamente que -70.
     * Tipo de resultado: lista.
     * Pista: comparación numérica dentro del predicado: [hijoNumerico < N].
     */
    public static final String PROBES_SENAL_DEBIL = ""; // TODO: completar

    /**
     * Consulta 2.3: Probes con buena señal (RSSI mayor o igual que -65 dBm).
     * Devolver la <macAddress> de cada <probe> cuyo <rssi> sea >= -65.
     * Tipo de resultado: lista.
     */
    public static final String PROBES_SENAL_BUENA = ""; // TODO: completar

    /**
     * Consulta 2.4: MACs de probes detectados en el edificio "UPM-N-10".
     * Filtrar los probes cuyo <location>/<edificio> sea "UPM-N-10" y
     * devolver su <macAddress>.
     * Tipo de resultado: lista.
     * Pista: el predicado puede contener una ruta hacia un nieto:
     *        //a[b/c='valor'].
     */
    public static final String PROBES_EDIFICIO_NORTE = ""; // TODO: completar

    /**
     * Consulta 2.5: MACs de probes que están buscando la red "eduroam".
     * Devolver la <macAddress> de cada <probe> cuyo <ssid> sea "eduroam".
     * Tipo de resultado: lista.
     */
    public static final String PROBES_BUSCANDO_EDUROAM = ""; // TODO: completar

    /**
     * Consulta 2.6: Sondas que han capturado MÁS DE UN probe.
     * Devolver el <nombre> de cada <sonda> cuyo subárbol <probes> contenga
     * más de 1 elemento <probe>.
     * Tipo de resultado: lista.
     * Pista: la función count() puede usarse dentro de un predicado:
     *        //sonda[count(probes/probe) > 1].
     */
    public static final String SONDAS_CON_MULTIPLES_PROBES = ""; // TODO: completar

    /**
     * Consulta 2.7: Primer probe de cada sonda (atributo secuencia="1").
     * Devolver la <macAddress> de cada <probe> cuyo atributo "secuencia"
     * valga "1".
     * Tipo de resultado: lista.
     */
    public static final String PRIMER_PROBE_CADA_SONDA = ""; // TODO: completar


    // =========================================================================
    // NIVEL 3 — FUNCIONES AGREGADAS
    // count(), sum(), string-length()... (todas son XPath 1.0)
    // min(), max() y distinct-values() son XPath 2.0: para esas, basta con
    // devolver la lista de valores y el cálculo final lo hará Java.
    // =========================================================================

    /**
     * Consulta 3.1: Número total de sondas, contado directamente en el XML.
     * Devolver el resultado de aplicar count() sobre todas las <sonda>.
     * Tipo de resultado: número.
     */
    public static final String COUNT_SONDAS = ""; // TODO: completar

    /**
     * Consulta 3.2: Número total de probes, contado directamente en el XML.
     * Devolver el resultado de count() sobre todos los <probe> del documento.
     * Tipo de resultado: número.
     */
    public static final String COUNT_PROBES = ""; // TODO: completar

    /**
     * Consulta 3.3: Todos los valores RSSI como lista.
     * Devolver el contenido de cada <rssi> de cada <probe> (sin filtrar).
     * Esta lista la usará Java para calcular min() y max() (XPath 2.0,
     * no soportado por el JDK).
     * Tipo de resultado: lista.
     */
    public static final String TODOS_RSSI = ""; // TODO: completar

    /**
     * Consulta 3.4: Suma de TODOS los RSSI.
     * Aplicar sum() sobre el conjunto de elementos <rssi> del documento.
     * Junto con COUNT_PROBES permite calcular el promedio.
     * Tipo de resultado: número.
     */
    public static final String RSSI_SUM = ""; // TODO: completar

    /**
     * Consulta 3.5: Cantidad de probes con señal débil (RSSI < -70).
     * Aplicar count() sobre el conjunto de probes que cumplen el predicado.
     * Tipo de resultado: número.
     * Pista: count() puede recibir cualquier expresión que devuelva un
     *        node-set, incluyendo una con predicado.
     */
    public static final String COUNT_SENAL_DEBIL = ""; // TODO: completar

    /**
     * Consulta 3.6: Longitud (número de caracteres) del nombre del proyecto.
     * Aplicar string-length() sobre el elemento <nombre> de <metadata>.
     * Tipo de resultado: número.
     */
    public static final String LONGITUD_NOMBRE_PROYECTO = ""; // TODO: completar

    /**
     * Consulta 3.7: Todos los SSIDs (lista usada para contar distintos en Java).
     * Es la misma expresión que TODOS_SSIDS del Nivel 1, pero se mantiene
     * aparte para dejar explícito el propósito: distinct-values() no existe
     * en XPath 1.0, así que la deduplicación se hace en Java a partir de
     * esta lista.
     * Tipo de resultado: lista.
     */
    public static final String TODOS_SSIDS_PARA_DISTINTOS = ""; // TODO: completar
}
