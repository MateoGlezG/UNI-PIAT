package es.upm.piat.crowdsense.json;

public class CrowdSenseHandler {
	
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
				json.addKey("metadata");
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
				break;
			case "sondas":
				break;
			case "probes":
				break;
			case "probe":
				if(enSonda) {
					enProbe = true;
					json.startObject();	
					json.addString("id", attrOrEmpty(attrs, "id"));
					json.addString("sondaRef", attrOrEmpty(attrs, "sondaRef"));
					json.addNumber("secuncia", attrOrEmpty(attrs, "secuencia"));
					json.addString("timestamp", attrOrEmpty(attrs, "timestamp"));
					//location
					json.addNumber("rssi", attrOrEmpty(attrs, "rssi"));
					json.addString("macAddres", attrOrEmpty(attrs, "macAddress"));
					json.addString("ssid", attrOrEmpty(attrs, "ssid"));
				}
				break;
			case "location":
				break;
				
		 }//fin del sw
	}
	
	
}//fin clase
