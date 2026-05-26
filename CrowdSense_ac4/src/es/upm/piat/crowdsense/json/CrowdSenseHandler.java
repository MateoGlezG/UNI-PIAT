package es.upm.piat.crowdsense.json;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class CrowdSenseHandler extends DefaultHandler{
	
	private final StringBuilder buffer = new StringBuilder(); // acumula texto
	private final JsonBuilder json; // construye el JSON
	
	// Flags: dónde estamos en el árbol
	private boolean enMetadata = false;
	private boolean enEstadisticas = false;
	private boolean enCampana = false;
	private boolean enResumenCampana = false;
	private boolean enSonda = false;
	private boolean enProbe = false;
	private boolean enLocation = false;

	public CrowdSenseHandler () {
		this.json = new JsonBuilder();
	}
	
	//metodos
	@Override
	public void startDocument() {
		json.startObject(); //abre la { raiz del JSON
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		buffer.setLength(0); // SIEMPRE limpiar el buffer al entrar
		
		switch (qName) {
			case "crowdSenseProject":
				// Los atributos del raíz van directamente como campos JSON
				json.addString("version", attrOrEmpty(attrs, "version"));
				json.addString("generadoEn", attrOrEmpty(attrs, "generadoEn"));
				break;
			case "metadata":
				enMetadata = true; // activar flag
				json.addKey("metadata"); //Se pone cuando vas a abrir un objeto o un array como valor de una propiedad con nombre, no un clave valor directamente es un clave para un conjunto de valores 
				json.startObject();
				break;
			case "campana":
				enCampana = true;
				json.startObject(); // elemento de array: sin addKey
				json.addString("id", attrOrEmpty(attrs, "id")); // atributo XML
				break;
			case "sonda":
				if (enCampana) {
					enSonda = true;
					json.startObject();
					json.addString("id", attrOrEmpty(attrs, "id"));
					json.addString("status", attrOrEmpty(attrs, "status"));
				}
				break;
			// TODO: implementar el resto de casos:
			// estadisticas, campanas, resumen, sondas, probes, probe, location
			case "estadisticas":
				enEstadisticas = true;
				json.addKey("estadisticas");
				json.startObject();
				break;
			case "resumen":
				enResumenCampana = true;
				json.addKey("resumen");
				json.startObject();
				break;
			case "campanas":
				//los array no tienen flag
				json.addKey("campanas");
				json.startArray();
				break;
			case "sondas":
				if(enCampana) {
					json.addKey("sondas");
					json.startArray();	
				}
				break;
			case "probes":
				if(enSonda) {
					json.addKey("probes");
					json.startArray();
				}
				break;
			case "probe":
				if(enSonda) {
					enProbe = true;
					json.startObject();	
					json.addString("id", attrOrEmpty(attrs, "id"));
					json.addString("sondaRef", attrOrEmpty(attrs, "sondaRef"));
					json.addNumber("secuencia", attrOrEmpty(attrs, "secuencia"));
				}
				break;
			case "location":
				if(enProbe) {
					enLocation = true;
					json.addKey("location");
					json.startObject();
				}
				break;
		 }//fin del sw
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
	 String text = buffer.toString().trim(); // leer el texto acumulado en el buffer de memoria
	 
	 switch (qName) {
	 // --- Cierre de bloques estructurales ---
		 case "crowdSenseProject":
			 json.endObject(); // cierra la raíz
			 break;
		 case "metadata":
			 enMetadata = false; // desactivar flag
			 json.endObject();
			 break;
		 case "campana":
			 enCampana = false;
			 json.endObject();
			 break;
		 // --- Campos de texto ---
		 case "nombre":
			 if (enMetadata) {json.addString("nombre", text);} 
			 else if (enCampana && !enSonda) { json.addString("nombre", text);}
			 else if (enSonda) { json.addString("nombre", text);}
			 break;
		 case "rssi":
			 if (enProbe) json.addNumber("rssi", text); // número, sin comillas
			 break;
	 // TODO: implementar el resto de campos y cierres
		 case "campanas":
			 json.endArray();
			 break;
		 case "sondas":
			 json.endArray();
			 break;
		 case "sonda":
			 enSonda = false;
			 json.endObject();
			 break;
		 case "probes":
			 json.endArray();
			 break;
		 case "probe":
			 enProbe = false;
			 json.endObject();
			 break;
		 case "location":
			 enLocation = false;
			 json.endObject();
			 break;
		 case "resumen":
			 enResumenCampana = false;
			 json.endObject();
			 break;
		//elementos de texto 
		 case "id":
			 if(enMetadata) {
				 json.addString("id", text);
			 }
			 break;
		 case "responsable":
			 if(enMetadata) {
				 json.addString("responsable", text); 
			 }
			 break;
		 case "email":
             if (enMetadata) json.addString("email", text);
             break;

         case "fechaCreacion":
             if (enMetadata) json.addString("fechaCreacion", text);
             break;

         case "fechaInicio":
             if (enCampana) json.addString("fechaInicio", text); //porque este elemento esta dentro de campana pero no dentro de sonda
             break;

         case "ubicacionRef":
             if (enSonda) json.addString("ubicacionRef", text);
             break;

         case "macSonda":
             if (enSonda) json.addString("macSonda", text);
             break;

         case "fechaInstalacion":
             if (enSonda) json.addString("fechaInstalacion", text);
             break;

         case "timestamp":
             if (enProbe) json.addString("timestamp", text);
             break;

         case "ssid":
             if (enProbe) json.addString("ssid", text);
             break;

         case "macAddress":
             if (enProbe) json.addString("macAddress", text);
             break;
         case "edificio":
             if (enLocation) json.addString("edificio", text);
             break;

         case "bloque":
             if (enLocation) json.addString("bloque", text);
             break;

         case "planta":
             if (enLocation) json.addString("planta", text);
             break;

         case "sala":
             if (enLocation) json.addString("sala", text);
             break;

         case "totalProbes":
             if (enResumenCampana || enEstadisticas) {
                 json.addNumber("totalProbes", text);
             }
             break;

         case "dispositivosUnicos":
             if (enResumenCampana) {
                 json.addNumber("dispositivosUnicos", text);
             }
             break;
         case "rssiPromedio":
             if (enResumenCampana) {
                 json.addNumber("rssiPromedio", text);
             }
             break;

         case "totalCampanas":
             if (enEstadisticas) json.addNumber("totalCampanas", text);
             break;

         case "totalSondas":
             if (enEstadisticas) json.addNumber("totalSondas", text);
             break;

         case "dispositivosUnicosGlobal":
             if (enEstadisticas) json.addNumber("dispositivosUnicosGlobal", text);
             break;
         case "estadisticas":
        	 enEstadisticas = false;
        	 json.endObject();
        	 break;

	 }//fin del sw

	 buffer.setLength(0); // limpiar el buffer al salir
	}
	
	// IMPORTANTE: characters() puede llamarse varias veces para el mismo nodo.
	// Por eso usamos append() y no asignación directa.
	// El texto se lee en endElement, donde ya está completo.
	@Override
	public void characters(char[] ch, int start, int length) {
	 buffer.append(ch, start, length); // acumular siempre
	}
	
	//comprueba si existe el atributo
	private String attrOrEmpty(Attributes attrs, String name) {
		 String val = attrs.getValue(name);
		 return val != null ? val : "";
		}

	public String getJson() {
		return json.build(); //llamo a la clase build del jsonbuilder para construir el json
	}
	
	
}//fin clase
