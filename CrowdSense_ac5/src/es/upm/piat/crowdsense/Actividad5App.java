package es.upm.piat.crowdsense;

import java.io.File;
import java.util.List;

import es.upm.piat.crowdsense.report.XPathReportBuilder;
import es.upm.piat.crowdsense.xpath.CrowdSenseQueries;
import es.upm.piat.crowdsense.xpath.XPathQueryEngine;

import org.w3c.dom.Element;

/**
 * Aplicación principal de la Actividad 5: Consultas XPath sobre el XML de CrowdSense.
 *
 * Flujo:
 *   1. Cargar el XML generado por la Actividad 3
 *   2. Ejecutar consultas XPath de tres niveles de dificultad
 *   3. Mostrar resultados por consola
 *   4. Generar un informe XML con todas las consultas y sus resultados
 */
public class Actividad5App {

    private static final String INPUT_XML   = "crowdsense-project.xml";
    private static final String OUTPUT_XML  = "crowdsense-xpath-report.xml";

    public static void main(String[] args) {

        System.out.println("=== SISTEMA CROWDSENSE 5.0 - XPATH ===");
        System.out.println();

        try {
            File xmlFile = new File(INPUT_XML);
            if (!xmlFile.exists()) {
                System.err.println("ERROR: No se encuentra '" + INPUT_XML + "'");
                System.err.println("       Ejecuta primero la Actividad 3 para generarlo.");
                return;
            }

            // Inicializar el motor XPath y el generador de informe
            XPathQueryEngine engine = new XPathQueryEngine(xmlFile);
            XPathReportBuilder report = new XPathReportBuilder(INPUT_XML);

            // =================================================================
            // NIVEL 1: NAVEGACIÓN BÁSICA
            // =================================================================
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║  NIVEL 1 — Navegación Básica                ║");
            System.out.println("╚══════════════════════════════════════════════╝");

            Element sec1 = report.addSeccion("1", "Navegación Básica");

            // -- Nombre del proyecto
            String nombreProyecto = engine.queryString(CrowdSenseQueries.NOMBRE_PROYECTO);
            printScalar("Nombre del proyecto", CrowdSenseQueries.NOMBRE_PROYECTO, nombreProyecto);
            report.addConsultaScalar(sec1, CrowdSenseQueries.NOMBRE_PROYECTO,
                    "Nombre del proyecto", nombreProyecto);

            // -- Versión
            String version = engine.queryString(CrowdSenseQueries.VERSION);
            printScalar("Versión del documento", CrowdSenseQueries.VERSION, version);
            report.addConsultaScalar(sec1, CrowdSenseQueries.VERSION,
                    "Versión del documento", version);

            // -- Responsable
            String responsable = engine.queryString(CrowdSenseQueries.RESPONSABLE);
            printScalar("Responsable", CrowdSenseQueries.RESPONSABLE, responsable);
            report.addConsultaScalar(sec1, CrowdSenseQueries.RESPONSABLE,
                    "Responsable del proyecto", responsable);

            // -- Total campañas (según estadísticas del XML)
            String totalCampanas = engine.queryString(CrowdSenseQueries.TOTAL_CAMPANAS);
            printScalar("Total campañas (estadísticas)", CrowdSenseQueries.TOTAL_CAMPANAS, totalCampanas);
            report.addConsultaScalar(sec1, CrowdSenseQueries.TOTAL_CAMPANAS,
                    "Total campañas según estadísticas", totalCampanas);

            // -- Nombres de sondas
            List<String> nombresSondas = engine.queryList(CrowdSenseQueries.NOMBRES_SONDAS);
            printList("Nombres de sondas", CrowdSenseQueries.NOMBRES_SONDAS, nombresSondas);
            report.addConsultaLista(sec1, CrowdSenseQueries.NOMBRES_SONDAS,
                    "Nombres de todas las sondas", nombresSondas);

            // -- Ubicaciones de sondas
            List<String> ubicaciones = engine.queryList(CrowdSenseQueries.UBICACIONES_SONDAS);
            printList("Ubicaciones de sondas", CrowdSenseQueries.UBICACIONES_SONDAS, ubicaciones);
            report.addConsultaLista(sec1, CrowdSenseQueries.UBICACIONES_SONDAS,
                    "Referencias de ubicación de todas las sondas", ubicaciones);

            // -- Todos los SSIDs
            List<String> ssids = engine.queryList(CrowdSenseQueries.TODOS_SSIDS);
            printList("SSIDs capturados", CrowdSenseQueries.TODOS_SSIDS, ssids);
            report.addConsultaLista(sec1, CrowdSenseQueries.TODOS_SSIDS,
                    "Todos los SSIDs capturados (con repeticiones)", ssids);

            System.out.println();

            // =================================================================
            // NIVEL 2: PREDICADOS Y FILTROS
            // =================================================================
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║  NIVEL 2 — Predicados y Filtros             ║");
            System.out.println("╚══════════════════════════════════════════════╝");

            Element sec2 = report.addSeccion("2", "Predicados y Filtros");

            // -- Sondas activas
            List<String> sondasActivas = engine.queryList(CrowdSenseQueries.SONDAS_ACTIVAS);
            printList("Sondas con status=ACTIVE", CrowdSenseQueries.SONDAS_ACTIVAS, sondasActivas);
            report.addConsultaLista(sec2, CrowdSenseQueries.SONDAS_ACTIVAS,
                    "Sondas en estado ACTIVE", sondasActivas);

            // -- Señal débil (RSSI < -70)
            List<String> senalDebil = engine.queryList(CrowdSenseQueries.PROBES_SENAL_DEBIL);
            printList("MACs con señal débil (RSSI < -70)", CrowdSenseQueries.PROBES_SENAL_DEBIL, senalDebil);
            report.addConsultaLista(sec2, CrowdSenseQueries.PROBES_SENAL_DEBIL,
                    "MACs de probes con señal débil (RSSI < -70 dBm)", senalDebil);

            // -- Buena señal (RSSI >= -65)
            List<String> senalBuena = engine.queryList(CrowdSenseQueries.PROBES_SENAL_BUENA);
            printList("MACs con buena señal (RSSI >= -65)", CrowdSenseQueries.PROBES_SENAL_BUENA, senalBuena);
            report.addConsultaLista(sec2, CrowdSenseQueries.PROBES_SENAL_BUENA,
                    "MACs de probes con buena señal (RSSI >= -65 dBm)", senalBuena);

            // -- Probes en edificio norte
            List<String> edNorte = engine.queryList(CrowdSenseQueries.PROBES_EDIFICIO_NORTE);
            printList("MACs en edificio UPM-N-10", CrowdSenseQueries.PROBES_EDIFICIO_NORTE, edNorte);
            report.addConsultaLista(sec2, CrowdSenseQueries.PROBES_EDIFICIO_NORTE,
                    "MACs detectadas en el edificio UPM-N-10", edNorte);

            // -- Probes buscando eduroam
            List<String> eduroam = engine.queryList(CrowdSenseQueries.PROBES_BUSCANDO_EDUROAM);
            printList("MACs buscando 'eduroam'", CrowdSenseQueries.PROBES_BUSCANDO_EDUROAM, eduroam);
            report.addConsultaLista(sec2, CrowdSenseQueries.PROBES_BUSCANDO_EDUROAM,
                    "MACs de dispositivos buscando la red eduroam", eduroam);

            // -- Sondas con múltiples probes
            List<String> multiProbe = engine.queryList(CrowdSenseQueries.SONDAS_CON_MULTIPLES_PROBES);
            printList("Sondas con más de 1 probe", CrowdSenseQueries.SONDAS_CON_MULTIPLES_PROBES, multiProbe);
            report.addConsultaLista(sec2, CrowdSenseQueries.SONDAS_CON_MULTIPLES_PROBES,
                    "Sondas que han capturado más de un probe", multiProbe);

            System.out.println();

            // =================================================================
            // NIVEL 3: FUNCIONES AGREGADAS
            // =================================================================
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║  NIVEL 3 — Funciones Agregadas              ║");
            System.out.println("╚══════════════════════════════════════════════╝");

            Element sec3 = report.addSeccion("3", "Funciones Agregadas");

            // -- count() de sondas y probes
            double countSondas = engine.queryNumber(CrowdSenseQueries.COUNT_SONDAS);
            printNumber("Sondas en el XML (count)", CrowdSenseQueries.COUNT_SONDAS, countSondas);
            report.addConsultaNumero(sec3, CrowdSenseQueries.COUNT_SONDAS,
                    "Número de sondas contadas directamente en el XML", countSondas);

            double countProbes = engine.queryNumber(CrowdSenseQueries.COUNT_PROBES);
            printNumber("Probes en el XML (count)", CrowdSenseQueries.COUNT_PROBES, countProbes);
            report.addConsultaNumero(sec3, CrowdSenseQueries.COUNT_PROBES,
                    "Número de probes contados directamente en el XML", countProbes);

            // -- min() y max() de RSSI
            // min()/max() son XPath 2.0 y el JDK solo soporta XPath 1.0.
            // Solución: obtenemos todos los valores con XPath y calculamos en Java.
            List<String> todosRssi = engine.queryList(CrowdSenseQueries.TODOS_RSSI);
            int rssiMin = todosRssi.stream().mapToInt(Integer::parseInt).min().orElse(0);
            int rssiMax = todosRssi.stream().mapToInt(Integer::parseInt).max().orElse(0);
            String exprMin = "//cs:probe/cs:rssi → Collections.min() en Java";
            String exprMax = "//cs:probe/cs:rssi → Collections.max() en Java";
            printNumber("RSSI mínimo (peor señal)", exprMin, rssiMin);
            report.addConsultaNumero(sec3, exprMin,
                    "Valor RSSI más bajo (peor señal) en dBm [min() es XPath 2.0: se obtiene con queryList + Java]", rssiMin);
            printNumber("RSSI máximo (mejor señal)", exprMax, rssiMax);
            report.addConsultaNumero(sec3, exprMax,
                    "Valor RSSI más alto (mejor señal) en dBm [max() es XPath 2.0: se obtiene con queryList + Java]", rssiMax);

            // -- Promedio RSSI: sum()/count() son XPath 1.0 → funcionan directamente
            double rssiSum = engine.queryNumber(CrowdSenseQueries.RSSI_SUM);
            double rssiAvg = (countProbes > 0) ? rssiSum / countProbes : 0;
            String exprAvg = "sum(//cs:probe/cs:rssi) div count(//cs:probe/cs:rssi)";
            printNumber("RSSI promedio (sum div count)", exprAvg, rssiAvg);
            report.addConsultaNumero(sec3, exprAvg,
                    "RSSI promedio: sum() y count() son XPath 1.0 nativos", rssiAvg);

            // -- count() de señal débil
            double countDebil = engine.queryNumber(CrowdSenseQueries.COUNT_SENAL_DEBIL);
            printNumber("Probes con señal débil (count)", CrowdSenseQueries.COUNT_SENAL_DEBIL, countDebil);
            report.addConsultaNumero(sec3, CrowdSenseQueries.COUNT_SENAL_DEBIL,
                    "Número de probes con RSSI < -70 dBm", countDebil);

            // -- string-length()
            double longNombre = engine.queryNumber(CrowdSenseQueries.LONGITUD_NOMBRE_PROYECTO);
            printNumber("Longitud del nombre del proyecto", CrowdSenseQueries.LONGITUD_NOMBRE_PROYECTO, longNombre);
            report.addConsultaNumero(sec3, CrowdSenseQueries.LONGITUD_NOMBRE_PROYECTO,
                    "Número de caracteres del nombre del proyecto", longNombre);

            // -- SSIDs distintos: distinct-values() es XPath 2.0 → contamos con Java
            List<String> todosSSIDs = engine.queryList(CrowdSenseQueries.TODOS_SSIDS_PARA_DISTINTOS);
            long ssidsDistintos = todosSSIDs.stream().distinct().count();
            String exprDistinct = "//cs:probe/cs:ssid → stream().distinct().count() en Java";
            printNumber("SSIDs distintos", exprDistinct, ssidsDistintos);
            report.addConsultaNumero(sec3, exprDistinct,
                    "Número de SSIDs únicos [distinct-values() es XPath 2.0: se calcula en Java]", ssidsDistintos);

            System.out.println();

            // =================================================================
            // GENERAR INFORME XML
            // =================================================================
            System.out.println(">>> Generando informe XML...");
            File outputFile = new File(OUTPUT_XML);
            report.writeToFile(outputFile);
            System.out.println("    Fichero: " + outputFile.getName());
            System.out.println("    Tamaño : " + outputFile.length() + " bytes");
            System.out.println();
            System.out.println("=== PROCESO COMPLETADO ===");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================================================================
    // HELPERS DE PRESENTACIÓN EN CONSOLA
    // =========================================================================

    private static void printScalar(String titulo, String expr, String valor) {
        System.out.printf("  %-40s%n", titulo);
        System.out.printf("  XPath : %s%n", expr);
        System.out.printf("  Valor : %s%n%n", valor.isEmpty() ? "(vacío)" : valor);
    }

    private static void printList(String titulo, String expr, List<String> items) {
        System.out.printf("  %-40s%n", titulo);
        System.out.printf("  XPath : %s%n", expr);
        if (items.isEmpty()) {
            System.out.println("  Items : (ninguno)");
        } else {
            System.out.printf("  Items : %d resultado(s)%n", items.size());
            items.forEach(i -> System.out.printf("          · %s%n", i));
        }
        System.out.println();
    }

    private static void printNumber(String titulo, String expr, double valor) {
        System.out.printf("  %-40s%n", titulo);
        System.out.printf("  XPath : %s%n", expr);
        if (valor == Math.floor(valor) && !Double.isInfinite(valor)) {
            System.out.printf("  Valor : %d%n%n", (long) valor);
        } else {
            System.out.printf("  Valor : %.2f%n%n", valor);
        }
    }
}
