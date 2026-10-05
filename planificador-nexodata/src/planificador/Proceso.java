package planificador;

public class Proceso {

    private final String nombre;
    private final int llegada;
    private final int rafaga;
    private final int orden;      // posición en el fichero (para desempates)

    private int restante;
    private EstadoProceso estado;
    private int inicio;          // primer instante en CPU (-1 = aún no)
    private int fin;             // instante en que termina (-1 = aún no)

    public Proceso(String nombre, int llegada, int rafaga, int orden) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.orden = orden;
        this.restante = rafaga;
        this.estado = EstadoProceso.NUEVO;
        this.inicio = -1;
        this.fin = -1;
    }

    /** Devuelve un proceso nuevo, limpio, con los mismos datos de entrada. */
    public Proceso copia() {
        return new Proceso(nombre, llegada, rafaga, orden);
    }

    // Métricas
    public int retorno() {
        return fin - llegada;
    }

    public int espera() {
        return retorno() - rafaga;
    }

    public int respuesta() {
        return inicio - llegada;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getLlegada() {
        return llegada;
    }

    public int getRafaga() {
        return rafaga;
    }

    public int getOrden() {
        return orden;
    }

    public int getRestante() {
        return restante;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }

    // Setters
    public void setRestante(int restante) {
        this.restante = restante;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

}
