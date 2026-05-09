import java.util.*;
import java.io.*;

public class GestorParque {
    private Map<String, ZonaParque> mapaZonas; 
    private final String PATH = "datos.csv";

    public GestorParque() {
        mapaZonas = new HashMap<>();
        cargarBatch(); 
        if(mapaZonas.isEmpty()) {
            inyectarDatosIniciales();
        }
    }

    private void inyectarDatosIniciales() {
        addZona("Adrenalina");
        getZona("Adrenalina").agregarAtraccion("Montaña Rusa", 120);
        getZona("Adrenalina").agregarAtraccion("Kamikaze", 40);
        addZona("Infantil");
        getZona("Infantil").agregarAtraccion("Carrusel", 60);
        getZona("Infantil").agregarAtraccion("Tazas Locas", 30);
        addZona("Acuatica");
        getZona("Acuatica").agregarAtraccion("Tobogan", 50);
    }

    public void addZona(String n) { mapaZonas.put(n.toLowerCase(), new ZonaParque(n)); }
    public void addZona(ZonaParque z) { mapaZonas.put(z.getNombre().toLowerCase(), z); }

    public ZonaParque getZona(String n) { return mapaZonas.get(n.toLowerCase()); }
    public Map<String, ZonaParque> getAll() { return mapaZonas; }
    
    public Atraccion buscarAtraccion(String nombreZona, String nombreAtraccion) {
        ZonaParque zona = getZona(nombreZona);
        if (zona != null) {
            for (Atraccion a : zona.getAtracciones()) {
                if (a.getNombre().equalsIgnoreCase(nombreAtraccion)) return a;
            }
        }
        return null;
    }

    public void editarCapacidadAtraccion(String nomZona, String nomAtraccion, int nuevaCap) {
        Atraccion a = buscarAtraccion(nomZona, nomAtraccion);
        if (a != null) {
            a.setCapacidad(nuevaCap);
        }
    }

    public boolean eliminarAtraccion(String nomZona, String nomAtraccion) {
        ZonaParque zona = getZona(nomZona);
        if (zona != null) {
            Atraccion aEliminar = buscarAtraccion(nomZona, nomAtraccion);
            if(aEliminar != null){
                List<Atraccion> temp = new ArrayList<>(zona.getAtracciones());
                temp.remove(aEliminar);
                mapaZonas.remove(nomZona.toLowerCase());
                ZonaParque nuevaZona = new ZonaParque(zona.getNombre());
                for(Atraccion atr : temp) nuevaZona.agregarAtraccion(atr);
                addZona(nuevaZona);
                return true;
            }
        }
        return false;
    }

    public String asignarGrupoMasivo(int tamanoGrupo) {
        StringBuilder reporte = new StringBuilder("--- ANÁLISIS DE AFORO LOGÍSTICO ---\n");
        boolean asignado = false;

        for (ZonaParque zona : mapaZonas.values()) {
            int capacidadDisponible = zona.getAtracciones().stream()
                    .filter(Atraccion::isOperativa)
                    .mapToInt(Atraccion::getCapacidad).sum();

            double umbralSeguro = capacidadDisponible * 0.8;
            reporte.append("Zona '").append(zona.getNombre()).append("': ");

            if (umbralSeguro >= tamanoGrupo) {
                reporte.append("VIABLE. (Ocupará el ")
                       .append(String.format("%.1f", (tamanoGrupo * 100.0) / capacidadDisponible))
                       .append("% de la capacidad operativa).\n");
                asignado = true;
            } else {
                reporte.append("NO VIABLE. Peligro de hacinamiento.\n");
            }
        }
        
        if(!asignado) reporte.append("\nALERTA CRÍTICA: Se recomienda dividir el grupo masivo.");
        return reporte.toString();
    }

    public void guardarBatch() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PATH))) {
            for (ZonaParque z : mapaZonas.values()) {
                for (Atraccion a : z.getAtracciones()) {
                    pw.println(z.getNombre() + "," + a.getNombre() + "," + a.getCapacidad() + "," + a.isOperativa());
                }
            }
        } catch (IOException e) { System.err.println("Error E/S al guardar."); }
    }

    private void cargarBatch() {
        File f = new File(PATH);
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");
                if(d.length == 4){
                    if (getZona(d[0]) == null) addZona(d[0]);
                    Atraccion a = new Atraccion(d[1], Integer.parseInt(d[2]), Boolean.parseBoolean(d[3]));
                    getZona(d[0]).agregarAtraccion(a);
                }
            }
        } catch (Exception e) { System.err.println("Error E/S al cargar."); }
    }
}