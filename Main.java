import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        //Inicialización del motor y carga de datos
        GestorParque gestor = new GestorParque();
        
        //Selección de Interfaz 
        String[] opciones = {"Ventanas", "Consola"};
        int eleccion = JOptionPane.showOptionDialog(null, 
                "¿Qué interfaz desea utilizar para gestionar el parque?", 
                "Selección de Interfaz (SIA-10)", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, opciones, opciones[0]);

        //Delegación según la elección del usuario
        if (eleccion == 0) {
            // Inicia la interfaz gráfica modular
            MenuVentanas menuV = new MenuVentanas(gestor);
            menuV.iniciar();
        } else if (eleccion == 1) {
            // Inicia la interfaz de consola modular
            MenuConsola menuC = new MenuConsola(gestor);
            menuC.iniciar();
        }
        
        //Persistencia de datos antes del cierre (SIA-11)
        gestor.guardarBatch(); 
        
        JOptionPane.showMessageDialog(null, 
            "Sistema cerrado. Datos guardados en 'datos.csv'.", 
            "Cierre", JOptionPane.INFORMATION_MESSAGE);
    }
}
