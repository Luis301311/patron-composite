package despues;

/**
 * Component: clase abstracta común a hojas y compuestos.
 * Define QUÉ se le puede pedir a cualquier elemento del árbol,
 * sin decir CÓMO; cada subclase decide cómo responder.
 */
public abstract class ComponenteAcademico {

    protected String nombre;

    public ComponenteAcademico(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract int getCreditos();

    public abstract void mostrar(int nivel);
}
