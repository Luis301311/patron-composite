package antes;

/**
 * Client ANTES del patrón: arma el mismo plan de estudios, pero para
 * tratar un elemento sin saber su clase tiene que usar instanceof.
 */
public class Main {

    public static void main(String[] args) {
        Agrupacion sem1 = new Agrupacion("Semestre 1");
        sem1.agregar(new Materia("Cálculo Diferencial", 4));
        sem1.agregar(new Materia("Programación I", 3));
        sem1.agregar(new Materia("Introducción a la Ingeniería", 2));

        Agrupacion electivas = new Agrupacion("Electivas");
        electivas.agregar(new Materia("Ética", 2));
        electivas.agregar(new Materia("Inglés I", 2));

        Agrupacion sem2 = new Agrupacion("Semestre 2");
        sem2.agregar(new Materia("Cálculo Integral", 4));
        sem2.agregar(new Materia("Programación II", 3));
        sem2.agregar(electivas); // una agrupación dentro de otra

        Agrupacion plan = new Agrupacion("Plan Ingeniería de Sistemas");
        plan.agregar(sem1);
        plan.agregar(sem2);

        plan.mostrar(0);
        System.out.println();
        System.out.println("Total de créditos: " + plan.getCreditos());

        // ---------------------------------------------------------------
        // Problemas del enfoque sin patrón
        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("--- Problemas sin el patrón ---");

        // 1) La lista acepta cualquier objeto: compila, pero no tiene sentido.
        Agrupacion prueba = new Agrupacion("Semestre de prueba");
        prueba.agregar(new Materia("Cálculo", 4));
        prueba.agregar("Cafetería (esto no es una materia)");
        prueba.agregar(Integer.valueOf(3));
        prueba.mostrar(0);
        System.out.println("Se agregaron 3 elementos, pero solo se muestra 1: "
                + "el compilador no avisó y los intrusos se ignoran en silencio.");

        // 2) Para tratar un elemento "cualquiera" el cliente debe preguntar su clase.
        Object[] elementos = { new Materia("Ética", 2), sem1 };
        for (Object e : elementos) {
            System.out.println("Créditos de un elemento sin saber su clase: " + creditosDe(e));
        }
    }

    // No existe un tipo común con getCreditos(), así que el cliente
    // depende de TODAS las clases concretas y las revisa una por una.
    static int creditosDe(Object elemento) {
        if (elemento instanceof Materia) {
            return ((Materia) elemento).getCreditos();
        } else if (elemento instanceof Agrupacion) {
            return ((Agrupacion) elemento).getCreditos();
        }
        throw new IllegalArgumentException("Tipo no soportado: " + elemento.getClass().getName());
    }
}
