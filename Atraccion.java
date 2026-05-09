import java.util.Objects;

public class Atraccion {
    private String nombre;
    private int capacidad;
    private boolean operativa;

    public Atraccion(String nombre, int capacidad, boolean operativa) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.operativa = operativa;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public boolean isOperativa() { return operativa; }
    public void setOperativa(boolean operativa) { this.operativa = operativa; }

    @Override 
    public String toString() {
        return nombre + " (Cap: " + capacidad + " | Operativa: " + operativa + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Atraccion atraccion = (Atraccion) obj;
        return Objects.equals(nombre.toLowerCase(), atraccion.nombre.toLowerCase());
    }
}