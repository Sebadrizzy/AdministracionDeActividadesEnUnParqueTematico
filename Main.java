import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        GestorParque gestor = new GestorParque();
        
        int eleccion = 0; 
        
        if (eleccion == 0) {
            MenuVentanas menu = new MenuVentanas(gestor);
            menu.iniciar();
        } else {
            // Este bloque queda como respaldo para cuando reactives el menú
            JOptionPane.showMessageDialog(null, "Iniciando en modo gráfico por defecto.");
            MenuVentanas menu = new MenuVentanas(gestor);
            menu.iniciar();
        }
        
        //Guardamos los datos antes de cerrar 
        gestor.guardarBatch(); 
        JOptionPane.showMessageDialog(null, "Datos guardados correctamente. ¡Hasta pronto!");
    }
}
