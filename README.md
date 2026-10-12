# Patrón Composite

> Patrón estructural (GoF). Permite componer objetos en estructuras de árbol y tratar igual a un elemento simple y a un grupo de elementos.

## Estructura del repositorio

```
patron-composite/
├── README.md
└── src/
    └── despues/                  ← código con el patrón aplicado
        ├── ComponenteAcademico.java   (Component)
        ├── Materia.java               (Leaf)
        ├── Agrupacion.java            (Composite)
        └── Main.java                  (Client)
```

## Problemática que resuelve

En muchos sistemas los datos tienen forma de **árbol**: hay elementos simples y grupos que contienen otros elementos, e incluso otros grupos. Un plan de estudios es un buen ejemplo: está formado por semestres, cada semestre tiene materias y dentro de un semestre puede haber un grupo de electivas con más materias.

Sin un patrón, el código que trabaja con esta estructura tiene que preguntar constantemente **"¿esto es una materia o una agrupación?"**:

```java
class Materia { String nombre; int creditos; }
class Agrupacion { String nombre; List<Object> elementos = new ArrayList<>(); }

static int calcularCreditos(Agrupacion a) {
    int total = 0;
    for (Object o : a.elementos) {
        if (o instanceof Materia m) {
            total += m.creditos;
        } else if (o instanceof Agrupacion sub) {
            total += calcularCreditos(sub);
        }
    }
    return total;
}
```

Esto genera varios problemas:

- **No hay un tipo común.** `Materia` y `Agrupacion` no comparten nada, así que la lista tiene que ser de `Object`. En ella cabe cualquier cosa, incluso datos que no deberían estar ahí.
- **El cliente depende de todas las clases concretas.** Tiene que conocer cada tipo y preguntar con `instanceof` antes de usarlo.
- **La lógica se repite.** Cada operación nueva (mostrar el plan, contar materias, etc.) necesita otro método con los mismos `if`.
- **El código queda cerrado al cambio.** Si aparece un tipo nuevo, por ejemplo `Laboratorio`, hay que modificar todos los métodos que recorren la estructura. Esto viola el principio Abierto/Cerrado.

**Composite** resuelve esto haciendo que los elementos simples y los grupos compartan la misma interfaz, para que el cliente los trate exactamente igual.

## Lo que se obtiene al aplicar el patrón

Con Composite, `Materia` y `Agrupacion` heredan de una clase abstracta común, `ComponenteAcademico`, y la agrupación guarda una lista de ese tipo común:

```java
abstract class ComponenteAcademico {
    protected String nombre;

    public ComponenteAcademico(String nombre) {
        this.nombre = nombre;
    }

    public abstract int getCreditos();
    public abstract void mostrar(int nivel);
}

class Materia extends ComponenteAcademico {
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

class Agrupacion extends ComponenteAcademico {
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
            total += c.getCreditos();
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
```

El cliente arma el árbol y luego lo usa con **una sola llamada**:

```java
plan.mostrar(0);
int total = plan.getCreditos();
```

Salida:

```
+ Plan Ingeniería de Sistemas (20 cr)
  + Semestre 1 (9 cr)
    - Cálculo Diferencial (4 cr)
    - Programación I (3 cr)
    - Introducción a la Ingeniería (2 cr)
  + Semestre 2 (11 cr)
    - Cálculo Integral (4 cr)
    - Programación II (3 cr)
    + Electivas (4 cr)
      - Ética (2 cr)
      - Inglés I (2 cr)
```

### Beneficios obtenidos

| Antes | Después |
|---|---|
| `List<Object>`: cualquier cosa cabe en la lista | `List<ComponenteAcademico>`: solo elementos del árbol |
| `if` / `instanceof` para saber el tipo | Polimorfismo: cada clase sabe responder |
| Un recorrido manual por cada operación | La agrupación delega en sus hijos (recursión) |
| Agregar un tipo obliga a modificar código existente | Se agrega una clase nueva sin tocar lo demás (Abierto/Cerrado) |

- **Tratamiento uniforme:** el cliente no distingue entre una materia y una agrupación; a ambas les pide lo mismo.
- **Anidamiento sin límite:** como la lista es del tipo común, una agrupación puede contener otras agrupaciones (por ejemplo, *Electivas* dentro de *Semestre 2*) a cualquier profundidad.
- **Cliente simple:** recorrer y calcular todo el árbol se reduce a `plan.getCreditos()` o `plan.mostrar(0)`.
- **Extensible:** un nuevo tipo de componente solo necesita heredar de `ComponenteAcademico` e implementar sus métodos.

## Cómo ejecutar el código "después"

Requisitos: Java 11 o superior (JDK).

Desde la raíz del repositorio:

```bash
javac -encoding UTF-8 -d out src/despues/*.java
java -cp out despues.Main
```

También se puede abrir la carpeta en IntelliJ, NetBeans o VS Code (con el Extension Pack for Java) y ejecutar `Main.java`.
