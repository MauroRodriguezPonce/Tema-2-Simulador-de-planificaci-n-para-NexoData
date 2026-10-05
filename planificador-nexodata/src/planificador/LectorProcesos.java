package planificador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorProcesos {

    public static List<Proceso> leer(String ruta)
            throws IOException, FormatoInvalidoException {

        List<Proceso> procesos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numLinea = 0;
            int orden = 0;

            while ((linea = br.readLine()) != null) {
                numLinea++;
                linea = linea.trim();

                // Ignorar vacías y comentarios
                if (linea.isEmpty() || linea.startsWith("#")) continue;

                String[] campos = linea.split(";");
                if (campos.length != 3) {
                    throw new FormatoInvalidoException("Línea " + numLinea
                            + ": se esperaban 3 campos (nombre;llegada;ráfaga) y hay "
                            + campos.length + " -> \"" + linea + "\"");
                }

                String nombre = campos[0].trim();
                if (nombre.isEmpty()) {
                    throw new FormatoInvalidoException(
                            "Línea " + numLinea + ": el nombre está vacío");
                }

                int llegada = parsearEntero(campos[1], "llegada", numLinea);
                int rafaga  = parsearEntero(campos[2], "ráfaga", numLinea);

                if (llegada < 0) {
                    throw new FormatoInvalidoException("Línea " + numLinea
                            + ": la llegada no puede ser negativa (se leyó " + llegada + ")");
                }
                if (rafaga <= 0) {
                    throw new FormatoInvalidoException("Línea " + numLinea
                            + ": la ráfaga debe ser mayor que 0 (se leyó " + rafaga + ")");
                }

                procesos.add(new Proceso(nombre, llegada, rafaga, orden++));
            }
        }

        if (procesos.isEmpty()) {
            throw new FormatoInvalidoException("El fichero no contiene ningún proceso");
        }
        return procesos;
    }

    private static int parsearEntero(String texto, String campo, int numLinea)
            throws FormatoInvalidoException {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Línea " + numLinea + ": la " + campo
                    + " no es un número entero (se leyó \"" + texto.trim() + "\")");
        }
    }
}
