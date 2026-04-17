public class Atraccion {
    private String nombre;
    private int capacidad;
    private boolean operativa;

    public Atraccion(String nombre, int capacidad, boolean operativa) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.operativa = operativa;
    }

    //Encapsulamiento
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public boolean isOperativa() { return operativa; }
    public void setOperativa(boolean operativa) { this.operativa = operativa; }

    @Override //Sobreescritura
    public String toString() {
        return nombre + " (Capacidad: " + capacidad + ")";
    }
}
