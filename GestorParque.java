import java.util.*;
import java.io.*;

public class GestorParque {
    private Map<String, ZonaParque> mapaZonas; //1ra colección (Mapa)
    private final String PATH = "datos.csv";

    public GestorParque() {
        mapaZonas = new HashMap<>();
        cargarBatch(); //Carga al iniciar
    }

    // CRUD Básico (SIA-7, SIA-8)
    public void addZona(String n) { mapaZonas.put(n, new ZonaParque(n)); }
    public ZonaParque getZona(String n) { return mapaZonas.get(n); }
    public Map<String, ZonaParque> getAll() { return mapaZonas; }
    public void removeZona(String n) { mapaZonas.remove(n); }

    //Funcionalidad Propia - Asignador de Eventos Masivos
    public String asignarGrupoMasivo(int tamanoGrupo) {
        StringBuilder resultado = new StringBuilder("Análisis para grupo de " + tamanoGrupo + " personas:\n");
        boolean zonaEncontrada = false;

        // Iteramos sobre la 1ra colección (Mapa)
        for (ZonaParque zona : mapaZonas.values()) {
            
            //FILTRO POR CRITERIO: Solo sumamos la capacidad de las atracciones que están OPERATIVAS
            int capacidadDisponibleEnZona = zona.getAtracciones().stream()
                    .filter(Atraccion::isOperativa)
                    .mapToInt(Atraccion::getCapacidad).sum();

            // Lógica de negocio: ¿Puede esta zona recibir al grupo completo sin superar el 80% de su capacidad total?
            // (Evitamos llenar la zona al 100% para no generar colapsos)
            if (capacidadDisponibleEnZona * 0.8 >= tamanoGrupo) {
                resultado.append("- La zona '").append(zona.getNombre())
                         .append("' es ÓPTIMA. (Capacidad operativa: ")
                         .append(capacidadDisponibleEnZona).append(")\n");
                zonaEncontrada = true;
            }
        }

        if (!zonaEncontrada) {
            return "ALERTA: Ninguna zona por sí sola puede recibir a " + tamanoGrupo + 
                   " personas de forma segura en este momento. Se sugiere dividir el grupo.";
        }

        return resultado.toString();
    }

    public void guardarBatch() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PATH))) {
            for (ZonaParque z : mapaZonas.values()) {
                for (Atraccion a : z.getAtracciones()) {
                    pw.println(z.getNombre() + "," + a.getNombre() + "," + a.getCapacidad());
                }
            }
        } catch (IOException e) { System.err.println("Error al guardar"); }
    }

    private void cargarBatch() {
        File f = new File(PATH);
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");
                if (!mapaZonas.containsKey(d[0])) addZona(d[0]);
                mapaZonas.get(d[0]).agregarAtraccion(d[1], Integer.parseInt(d[2]));
            }
        } catch (Exception e) { System.err.println("Error al cargar"); }
    }
}
