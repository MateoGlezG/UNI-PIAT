package es.upm.piat.crowdsense.model;

import jakarta.xml.bind.annotation.*;

/**
 * EJERCICIO 1: Añadir anotaciones JAXB a esta clase de la Actividad 1.
 * 
 * Anotaciones necesarias:
 * - @XmlAccessorType(XmlAccessType.FIELD) -> en la clase, dice como se deba acceder a la informacion, FILD -> debe mirar los campos directamente para mapearlos
 * - @XmlType(propOrder = {"edificio", "bloque", "planta", "sala"}) -> en la clase, orden de los elementos
 * - @XmlElement(required = true) -> en cada campo, obligatorio
 */
// TODO: Añadir @XmlAccessorType(XmlAccessType.FIELD)
// TODO: Añadir @XmlType(propOrder = {"edificio", "bloque", "planta", "sala"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"edificio", "bloque", "planta", "sala"})
public class LocationID {
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required=true)
    private String edificio;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required=true)
    private String bloque;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required=true)
    private String planta;
    
    // TODO: Añadir @XmlElement(required = true)
	@XmlElement(required=true)
    private String sala;

    public LocationID() {}

    public LocationID(String edificio, String bloque, String planta, String sala) {
        this.edificio = edificio;
        this.bloque = bloque;
        this.planta = planta;
        this.sala = sala;
    }

    // Getters
    public String getEdificio() { return edificio; }
    public String getBloque() { return bloque; }
    public String getPlanta() { return planta; }
    public String getSala() { return sala; }
    
    // Setters
    public void setEdificio(String edificio) { this.edificio = edificio; }
    public void setBloque(String bloque) { this.bloque = bloque; }
    public void setPlanta(String planta) { this.planta = planta; }
    public void setSala(String sala) { this.sala = sala; }

    @Override
    public String toString() {
        return String.format("%s [Bloque %s, Planta %s, Sala %s]", edificio, bloque, planta, sala);
    }
    
    public String toCompactString() {
        return edificio + "." + bloque + "." + planta + "." + sala;
    }
}
