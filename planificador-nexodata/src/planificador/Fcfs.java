package planificador;

import java.util.List;

public class Fcfs extends Planificador{

    @Override protected Proceso elegir(List<Proceso> listos) {
        return listos.get(0);
    }

    @Override protected boolean agotaTurno(int usadoEnTurno) {
        return false;
    }

    @Override public String nombre() {
        return "FCFS";
    }

}
