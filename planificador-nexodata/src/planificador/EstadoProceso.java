package planificador;

public enum EstadoProceso {

    NUEVO,       // aún no ha llegado al sistema
    LISTO,       // en la cola, esperando CPU
    EJECUCION,   // usando la CPU
    BLOQUEADO,   // esperando E/S (el simulador no lo usa, pero es parte del modelo)
    TERMINADO    // ha completado su ráfaga
}
