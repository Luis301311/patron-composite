package antes;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupacion: grupo de materias y de otras agrupaciones.
 * ANTES del patrón: como Materia y Agrupacion no comparten un tipo común,
 * la única lista que admite ambas es List<Object>, y cada recorrido
 * tiene que preguntar con instanceof de qué clase es cada elemento.
 */
public class Agrupacion {

    private String nombre;

    // List<Object>: aquí cabe cualquier cosa, no solo materias y agrupaciones.
    private List<Object> hijos = new ArrayList<>();

    public Agrupacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregar(Object elemento) {
        hijos.add(elemento);
    }

    public void eliminar(Object elemento) {
        hijos.remove(elemento);
    }

    public int getCreditos() {
        int total = 0;
        for (Object o : hijos) {
            if (o instanceof Materia) {
                total += ((Materia) o).getCreditos();
            } else if (o instanceof Agrupacion) {
                total += ((Agrupacion) o).getCreditos();
            }
            // Cualquier otro tipo se ignora en silencio.
            // Si apareciera Laboratorio, habría que agregar otro "else if" aquí...
        }
        return total;
    }

    public void mostrar(int nivel) {
        System.out.println("  ".repeat(nivel) + "+ " + nombre + " (" + getCreditos() + " cr)");
        for (Object o : hijos) {
            if (o instanceof Materia) {
                ((Materia) o).mostrar(nivel + 1);
            } else if (o instanceof Agrupacion) {
                ((Agrupacion) o).mostrar(nivel + 1);
            }
            // ...y otro "else if" aquí, y en cada operación nueva que recorra el árbol.
        }
    }
}
