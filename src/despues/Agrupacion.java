package despues;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite (compuesto): contiene una lista de componentes,
 * que pueden ser materias u otras agrupaciones.
 * No tiene datos propios: delega en sus hijos (recursión).
 */
public class Agrupacion extends ComponenteAcademico {

    // La lista es del tipo común, no de Materia:
    // por eso puede guardar hojas u otras agrupaciones.
    private List<ComponenteAcademico> hijos = new ArrayList<>();

    public Agrupacion(String nombre) {
        super(nombre);
    }

    public void agregar(ComponenteAcademico c) {
        hijos.add(c);
    }

    public void eliminar(ComponenteAcademico c) {
        hijos.remove(c);
    }

    @Override
    public int getCreditos() {
        int total = 0;
        for (ComponenteAcademico c : hijos) {
            total += c.getCreditos(); // si c es otra agrupación, hace lo mismo con sus hijos
        }
        return total;
    }

    @Override
    public void mostrar(int nivel) {
        System.out.println("  ".repeat(nivel) + "+ " + nombre + " (" + getCreditos() + " cr)");
        for (ComponenteAcademico c : hijos) {
            c.mostrar(nivel + 1);
        }
    }
}
