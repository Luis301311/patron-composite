package despues;

/**
 * Client: arma el árbol y lo usa a través de ComponenteAcademico,
 * sin preguntar nunca si un elemento es materia o agrupación.
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
    }
}
