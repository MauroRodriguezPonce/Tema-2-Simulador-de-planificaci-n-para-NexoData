package planificador;

import java.util.List;
import java.util.Locale;

public class Informe {

    private static final Locale ES = Locale.forLanguageTag("es-ES"); //
    private static final int ANCHO_LINEA = 100;

    public static void imprimir(Resultado r, boolean traza)
    {
        System.out.println("=== " + r.getAlgoritmo() + " ===");
        imprimirGantt(r.getGantt());
        System.out.println();
        imprimirTabla(r.getProcesos());
        System.out.println("Cambios de contexto: " + r.getCambiosContexto());
        if (traza) {

            System.out.println();
            imprimirTraza(r.getTransiciones());
        }
        System.out.println();
    }

    private static void imprimirGantt(List<String> gantt) {

        int ancho = String.valueOf(gantt.size()).length();
        for (String c : gantt) ancho = Math.max(ancho, c.length());
        ancho += 1;
        int porLinea = Math.max(1, ANCHO_LINEA / ancho);
        for (int ini = 0; ini < gantt.size(); ini += porLinea) {

            int fin = Math.min(ini + porLinea, gantt.size());
            StringBuilder tiempos = new StringBuilder(String.format("%-4s", "t"));
            StringBuilder cpu = new StringBuilder(String.format("%-4s", "CPU"));
            for (int i = ini; i < fin; i++) {
                tiempos.append(String.format("%-" + ancho + "d", i));
                cpu.append(String.format("%-" + ancho + "s", gantt.get(i)));
            }

            System.out.println(tiempos);
            System.out.println(cpu);
        }
    }

    private static void imprimirTabla(List<Proceso> procesos) {

        int anchoNombre = "Proceso".length();
        for (Proceso p : procesos) anchoNombre = Math.max(anchoNombre, p.getNombre().length());

        String formato = "%-" + (anchoNombre + 2) + "s%8s%8s%6s%9s%8s%10s%n";
        System.out.printf(formato, "Proceso", "Llegada", "Ráfaga", "Fin", "Retorno", "Espera", "Respuesta");

        double sumaRetorno = 0, sumaEspera = 0, sumaRespuesta = 0;
        for (Proceso p : procesos) {
            System.out.printf(formato, p.getNombre(), p.getLlegada(), p.getRafaga(),
                    p.getFin(), p.retorno(), p.espera(), p.respuesta());
            sumaRetorno += p.retorno();
            sumaEspera += p.espera();
            sumaRespuesta += p.respuesta();
        }

        int n = procesos.size();
        System.out.println(String.format(ES,
                "Medias: retorno %.2f | espera %.2f | respuesta %.2f",
                sumaRetorno / n, sumaEspera / n, sumaRespuesta / n));
    }

    private static void imprimirTraza(List<Resultado.Transicion> transiciones) {

        System.out.println("Traza de estados:");
        int anchoNombre = 1;
        for (Resultado.Transicion tr : transiciones)
            anchoNombre = Math.max(anchoNombre, tr.proceso().length());

        for (Resultado.Transicion tr : transiciones) {
            System.out.printf("  t=%-3d %-" + anchoNombre + "s %-10s -> %-10s (%s)%n",
                    tr.t(), tr.proceso(), tr.origen(), tr.destino(), tr.motivo());
        }
    }
}
