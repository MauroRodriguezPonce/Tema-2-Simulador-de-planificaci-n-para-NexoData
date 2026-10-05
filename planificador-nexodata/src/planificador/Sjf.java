package planificador;

import java.util.Comparator;
import java.util.List;

public class Sjf extends Planificador {

    private static final Comparator<Proceso> CRITERIO =
            Comparator.comparingInt(Proceso::getRafaga)
                    .thenComparingInt(Proceso::getLlegada)
                    .thenComparingInt(Proceso::getOrden);

    @Override protected Proceso elegir(List<Proceso> listos) {
        return listos.stream().min(CRITERIO).get();
    }

    @Override protected boolean agotaTurno(int usadoEnTurno) {
        return false;
    }

    @Override public String nombre() {
        return "SJF (sin desalojo)";
    }

}
