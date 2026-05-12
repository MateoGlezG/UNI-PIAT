package es.upm.piat.crowdsense;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import es.upm.piat.crowdsense.sax.XmlToJsonConverter;

/**
 * Aplicación principal de la Actividad 4: XML → JSON con SAX.
 *
 * Flujo:
 *   1. Leer el XML generado por la Actividad 3 (crowdsense-project.xml)
 *   2. Parsearlo con SAX (orientado a eventos, sin cargarlo entero en memoria)
 *   3. Construir el JSON resultante
 *   4. Guardarlo en disco (listo para enviar por red en Act.5)
 */
public class Actividad4App {

    public static void main(String[] args) {

        String inputXml  = "crowdsense-project.xml";   // Salida de Act.3
        String outputJson = "crowdsense-payload.json"; // Entrada para Act.5

        System.out.println("=== SISTEMA CROWDSENSE 4.0 - SAX + JSON ===");
        System.out.println();

        try {
            File xmlFile = new File(inputXml);
            if (!xmlFile.exists()) {
                System.err.println("ERROR: No se encuentra '" + inputXml + "'");
                System.err.println("       Ejecuta primero la Actividad 3 para generarlo.");
                return;
            }

            // FASE 1: Parsear XML con SAX
            System.out.println(">>> FASE 1: Parseando XML con SAX...");
            System.out.println("    Fichero entrada : " + xmlFile.getAbsolutePath());
            System.out.println("    Tamaño XML      : " + xmlFile.length() + " bytes");

            XmlToJsonConverter converter = new XmlToJsonConverter();
            String json = converter.convert(xmlFile);

            System.out.println("    Parsing completado.");
            System.out.println();

            // FASE 2: Guardar JSON en disco
            System.out.println(">>> FASE 2: Guardando JSON...");
            File jsonFile = new File(outputJson);
            try (FileWriter fw = new FileWriter(jsonFile)) {
                // Guardamos con pretty-print para que sea legible e inspeccionable
                fw.write(XmlToJsonConverter.prettyPrint(json));
            }
            System.out.println("    Fichero salida  : " + jsonFile.getAbsolutePath());
            System.out.println("    Tamaño JSON     : " + jsonFile.length() + " bytes");
            System.out.println();

            // FASE 3: Preview de las primeras líneas en consola
            System.out.println(">>> FASE 3: Preview del JSON generado (primeras 20 líneas):");
            System.out.println("    " + "-".repeat(60));
            Files.lines(jsonFile.toPath())
                 .limit(20)
                 .forEach(line -> System.out.println("    " + line));
            System.out.println("    ...");
            System.out.println("    " + "-".repeat(60));
            System.out.println();

            System.out.println("=== PROCESO COMPLETADO ===");
            System.out.println("JSON listo en: " + outputJson);
            System.out.println("Siguiente paso (Act.5): enviar por red con HttpClient o XPath queries.");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
