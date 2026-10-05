package planificador;

import java.util.ArrayList;
import java.util.List;

public class Resultado {

    public record Transicion(int t, String proceso, EstadoProceso origen,
                             EstadoProceso destino, String motivo) {}

    private final String algoritmo;
    private final List<Proceso> procesos;
    private final List<String> gantt = new ArrayList<>();
    private final List<Transicion> transiciones = new ArrayList<>();
    private int cambiosContexto;

    public Resultado(String algoritmo, List<Proceso> procesos) {
        this.algoritmo = algoritmo;
        this.procesos = procesos;
    }

    public void anotarGantt(String casilla) {
        gantt.add(casilla);
    }

    public void anotarTransicion(int t, Proceso p, EstadoProceso origen,
                                 EstadoProceso destino, String motivo) {
        transiciones.add(new Transicion(t, p.getNombre(), origen, destino, motivo));
    }

    public void setCambiosContexto(int n) {
        this.cambiosContexto = n;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public List<String> getGantt() {
        return gantt;
    }

    public List<Transicion> getTransiciones() {
        return transiciones;
    }

    public int getCambiosContexto() {
        return cambiosContexto;
    }

}
