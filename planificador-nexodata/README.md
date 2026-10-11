# Simulador de planificación · NexoData

> PSP · Tema 2 · Procesos · 2.º A DAM · **[Tu nombre]**
> Sustituye todo lo que aparece entre corchetes y borra estas indicaciones cuando lo completes.

## 1. Qué hace

[Tres o cuatro líneas.]

## 2. Cómo compilar y ejecutar

**Desde la terminal**, en la carpeta del proyecto:

```bash
javac -encoding UTF-8 -d out src/planificador/*.java
java -cp out planificador.Main datos/ejemplo_clase.csv rr 2 --traza
```

**Desde IntelliJ IDEA:** [explica los pasos que has seguido: JDK, configuración de ejecución, argumentos y *working directory*.]

![Mi programa en ejecución](capturas/[nombre-de-tu-captura].png)

## 3. Diseño

[Qué clases hay, qué hace cada una y por qué las has organizado así.]


## 4. Verificación a mano (tarea 4)

Resolví `verificacion.csv` (P1;0;6 · P2;1;4 · P3;2;2 · P4;4;5 · P5;6;1) a mano, antes de ejecutar el programa.
La suma de ráfagas es 18 y nunca hay CPU ociosa, porque siempre hay algún proceso listo cuando la CPU queda libre.

### FCFS

```
t:    0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17
CPU:  P1 P1 P1 P1 P1 P1 P2 P2 P2 P2 P3 P3 P4 P4 P4 P4 P4 P5
```

| Proceso | Llegada | Ráfaga | Fin | Retorno | Espera | Respuesta |
|---|---|---|---|---|---|---|
| P1 | 0 | 6 | 6 | 6 | 0 | 0 |
| P2 | 1 | 4 | 10 | 9 | 5 | 5 |
| P3 | 2 | 2 | 12 | 10 | 8 | 8 |
| P4 | 4 | 5 | 17 | 13 | 8 | 8 |
| P5 | 6 | 1 | 18 | 12 | 11 | 11 |

Medias: retorno 10,00 | espera 6,40 | respuesta 6,40
Cambios de contexto: 4 (P1→P2→P3→P4→P5)

### Round Robin (q=2)

Cola de listos en cada instante en que el planificador decide:

| t | Qué pasa | Cola antes de elegir | Elige | Cola después |
|---|---|---|---|---|
| 0 | llega P1 | [P1] | P1 | [] |
| 2 | llega P3; P1 agota el quantum (le quedan 4) y entra después de P3 | [P2, P3, P1] | P2 | [P3, P1] |
| 4 | llega P4; P2 agota el quantum (le quedan 2) y entra después de P4 | [P3, P1, P4, P2] | P3 | [P1, P4, P2] |
| 6 | llega P5; P3 termina | [P1, P4, P2, P5] | P1 | [P4, P2, P5] |
| 8 | P1 agota el quantum (le quedan 2) | [P4, P2, P5, P1] | P4 | [P2, P5, P1] |
| 10 | P4 agota el quantum (le quedan 3) | [P2, P5, P1, P4] | P2 | [P5, P1, P4] |
| 12 | P2 termina | [P5, P1, P4] | P5 | [P1, P4] |
| 13 | P5 termina | [P1, P4] | P1 | [P4] |
| 15 | P1 termina | [P4] | P4 | [] |
| 17 | P4 agota el quantum con la cola vacía: sigue con quantum nuevo (regla 4) | [] | P4 (sin cambio) | [] |
| 18 | P4 termina | | | |

```
t:    0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17
CPU:  P1 P1 P2 P2 P3 P3 P1 P1 P4 P4 P2 P2 P5 P1 P1 P4 P4 P4
```

| Proceso | Llegada | Ráfaga | Fin | Retorno | Espera | Respuesta |
|---|---|---|---|---|---|---|
| P1 | 0 | 6 | 15 | 15 | 9 | 0 |
| P2 | 1 | 4 | 12 | 11 | 7 | 1 |
| P3 | 2 | 2 | 6 | 4 | 2 | 2 |
| P4 | 4 | 5 | 18 | 14 | 9 | 4 |
| P5 | 6 | 1 | 13 | 7 | 6 | 6 |

Medias: retorno 10,20 | espera 6,60 | respuesta 2,60
Cambios de contexto: 8. Hay 9 turnos (P1, P2, P3, P1, P4, P2, P5, P1, P4) y todos cambian de proceso respecto al anterior. En t=17 P4 continúa sin cambio de contexto.

Reglas aplicadas: en t=2 y t=4 el proceso que llega entra en la cola antes que el desalojado (regla 3). En t=17 se aplica la regla 4.

### Comparación con el programa

- **FCFS:** coincide en Gantt, métricas, medias y cambios de contexto (4).
- **Round Robin (q=2):** coincide en Gantt, métricas, medias (10,20 / 6,60 / 2,60) y cambios de contexto (8). Comprobé en la traza los instantes críticos: en t=2 y t=4 el proceso que llega (P3, P4) entra en la cola antes que el desalojado (regla 3), y en t=17 P4 sigue en la CPU sin cambio de contexto porque nadie espera (regla 4).
- **Discrepancias:** no hubo ninguna entre mi resolución a mano y el programa, así que no hubo que corregir código.

### hueco.csv

X llega en t=0 y termina en t=2. Y y Z no llegan hasta t=5, así que entre los instantes 2 y 5 no hay ningún proceso listo y la CPU está ociosa. El diagrama lo refleja con un guion `-` en las casillas 2, 3 y 4. Cuando Y y Z llegan a la vez en t=5, entra antes en la cola el que aparece antes en el fichero (regla 2). En SJF manda la ráfaga más corta, así que Z se ejecuta antes que Y.

## 5. Análisis y recomendación a NexoData (tarea 5)

| nocturno.csv | Retorno medio | Espera media | Respuesta media | Cambios de contexto |
|---|---|---|---|---|
| FCFS | | | | |
| SJF | | | | |
| RR q=1 | | | | |
| RR q=2 | | | | |
| RR q=4 | | | | |

1. [Respuesta 1]
2. [Respuesta 2]
3. [Respuesta 3]
4. [Respuesta 4]
5. [Respuesta 5]
6. [Respuesta 6, con la captura comentada de los estados de tu sistema]

### Recomendación
[De 5 a 10 líneas.]
