package planificador;

import java.util.ArrayList;
import java.util.List;

import static planificador.EstadoProceso.*;

public abstract class Planificador {


    protected abstract Proceso elegir(List<Proceso> listos);

    protected abstract boolean agotaTurno(int usadoEnTurno);

    public abstract String nombre();

    public Resultado simular(List<Proceso> originales) {

        List<Proceso> procesos = new ArrayList<>();
        for (Proceso p : originales) procesos.add(p.copia());

        Resultado res = new Resultado(nombre(), procesos);
        List<Proceso> listos = new ArrayList<>();
        Proceso enCpu = null;
        Proceso ultimo = null;
        int t = 0, terminados = 0, usado = 0, cambios = 0;

        while (terminados < procesos.size()) {

            for (Proceso p : procesos) {
                if (p.getLlegada() == t) {
                    p.setEstado(LISTO);
                    listos.add(p);
                    res.anotarTransicion(t, p, NUEVO, LISTO, "llega al sistema");
                }
            }

            if (enCpu != null) {
                if (enCpu.getRestante() == 0) {
                    enCpu.setFin(t);
                    enCpu.setEstado(TERMINADO);
                    res.anotarTransicion(t, enCpu, EJECUCION, TERMINADO, "termina su ráfaga");
                    terminados++;
                    enCpu = null;
                } else if (agotaTurno(usado)) {
                    if (listos.isEmpty()) {
                        usado = 0;
                    } else {
                        enCpu.setEstado(LISTO);
                        listos.add(enCpu);
                        res.anotarTransicion(t, enCpu, EJECUCION, LISTO, "agota el quantum");
                        enCpu = null;
                    }
                }
            }

            if (terminados == procesos.size()) break;

            if (enCpu == null && !listos.isEmpty()) {
                enCpu = elegir(listos);
                listos.remove(enCpu);
                enCpu.setEstado(EJECUCION);
                usado = 0;
                if (enCpu.getInicio() < 0) enCpu.setInicio(t);
                if (ultimo != null && ultimo != enCpu) cambios++;
                res.anotarTransicion(t, enCpu, LISTO, EJECUCION, "el planificador lo elige");
            }

            if (enCpu != null) {
                res.anotarGantt(enCpu.getNombre());
                enCpu.setRestante(enCpu.getRestante() - 1);
                usado++;
                ultimo = enCpu;
            } else {
                res.anotarGantt("-");
            }
            t++;
        }

        res.setCambiosContexto(cambios);
        return res;
    }
}
