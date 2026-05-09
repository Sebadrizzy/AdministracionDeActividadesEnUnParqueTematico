import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        GestorParque gestor = new GestorParque();
        
        String[] opciones = {"Ventanas", "Consola"};
        int eleccion = JOptionPane.showOptionDialog(null, 
                "¿Qué interfaz desea utilizar para gestionar el parque?", 
                "Selección de Interfaz (SIA-10)", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, opciones, opciones[0]);

        if (eleccion == 0) {
            MenuVentanas menuV = new MenuVentanas(gestor);
            menuV.iniciar();
        } else if (eleccion == 1) {
            MenuConsola menuC = new MenuConsola(gestor);
            menuC.iniciar();
        }
        
        gestor.guardarBatch(); 
        
        JOptionPane.showMessageDialog(null, 
            "Sistema cerrado. Datos guardados en 'datos.csv'.", 
            "Cierre", JOptionPane.INFORMATION_MESSAGE);
    }
}