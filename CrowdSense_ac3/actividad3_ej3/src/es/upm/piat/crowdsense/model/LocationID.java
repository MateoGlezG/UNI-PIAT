package es.upm.piat.crowdsense.model;

/**
 * Representa la ubicación desglosada de una sonda.
 * Ejemplo: UPM-N-10 (Edificio), B1 (Bloque), P0 (Planta), L01 (Sala)
 */

public class LocationID {
    private String edificio;
    
    private String bloque;
  private String planta;
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
