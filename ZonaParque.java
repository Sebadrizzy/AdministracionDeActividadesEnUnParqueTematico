import java.util.ArrayList;
import java.util.List;

public class ZonaParque {
    private String nombre;
    private List<Atraccion> atracciones; //2da colección

    public ZonaParque(String nombre) {
        this.nombre = nombre;
        this.atracciones = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public List<Atraccion> getAtracciones() { return atracciones; }

    //Sobrecarga
    public void agregarAtraccion(Atraccion a) { atracciones.add(a); }
    
    public void agregarAtraccion(String nombre, int cap) {
        atracciones.add(new Atraccion(nombre, cap, true));
    }
}
