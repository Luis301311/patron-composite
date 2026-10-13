package antes;

/**
 * Materia: elemento simple del plan de estudios.
 * ANTES del patrón: no hereda de nada, así que no comparte
 * ningún tipo con Agrupacion.
 */
public class Materia {

    private String nombre;
    private int creditos;

    public Materia(String nombre, int creditos) {
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void mostrar(int nivel) {
        System.out.println("  ".repeat(nivel) + "- " + nombre + " (" + creditos + " cr)");
    }
}
