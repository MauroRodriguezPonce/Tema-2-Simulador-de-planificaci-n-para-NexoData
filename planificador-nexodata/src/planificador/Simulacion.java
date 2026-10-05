package planificador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulacion {

    public static void ejecutar(String ruta, String algoritmo, int quantum, boolean traza) {

        List<Proceso> procesos;

        try {

            procesos = LectorProcesos.leer(ruta);

        }

        catch (IOException e) {

            System.err.println("No se pudo leer el fichero: " + e.getMessage());
            return;

        }

        catch (FormatoInvalidoException e) {
            System.err.println("Fichero no válido: " + e.getMessage());
            return;

        }

        List<Planificador> algoritmos = new ArrayList<>();
        switch (algoritmo.toLowerCase()) {
            case "fcfs" -> algoritmos.add(new Fcfs());
            case "sjf"  -> algoritmos.add(new Sjf());
            case "rr"   -> algoritmos.add(new RoundRobin(quantum));
            case "todos" -> {
                algoritmos.add(new Fcfs());
                algoritmos.add(new Sjf());
                algoritmos.add(new RoundRobin(quantum));
            }
            default -> {
                System.err.println("Algoritmo desconocido: " + algoritmo);
                return;
            }
        }

        // Cada simulación trabaja sobre copias, así que todos usan los mismos datos originales
        for (Planificador p : algoritmos) {
            Informe.imprimir(p.simular(procesos), traza);
        }
    }
}
