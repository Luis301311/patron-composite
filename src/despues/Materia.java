package despues;

/**
 * Leaf (hoja): elemento simple, no contiene otros elementos.
 * Es donde está el dato real, por eso responde directamente.
 */
public class Materia extends ComponenteAcademico {

    private int creditos;

    public Materia(String nombre, int creditos) {
        super(nombre);
        this.creditos = creditos;
    }

    @Override
    public int getCreditos() {
        return creditos;
    }

    @Override
    public void mostrar(int nivel) {
        System.out.println("  ".repeat(nivel) + "- " + nombre + " (" + creditos + " cr)");
    }
}
