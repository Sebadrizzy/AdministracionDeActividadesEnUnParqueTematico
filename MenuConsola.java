import java.util.Scanner;

public class MenuConsola {
    private GestorParque gestor;
    private Scanner sc;

    public MenuConsola(GestorParque gestor) {
        this.gestor = gestor;
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opt = 0;
        do {
            System.out.println("\n--- MODO CONSOLA: PARQUE TEMÁTICO ---");
            System.out.println("1. Agregar Zona");
            System.out.println("2. Agregar Atracción");
            System.out.println("3. Listar Inventario");
            System.out.println("4. Evaluar Grupo Masivo (SIA-9)");
            System.out.println("5. Volver y Salir");
            System.out.print("Seleccione: ");
            
            try {
                opt = Integer.parseInt(sc.nextLine());
                switch (opt) {
                    case 1:
                        System.out.print("Nombre de zona: ");
                        gestor.addZona(sc.nextLine());
                        break;
                    case 2:
                        System.out.print("Zona destino: ");
                        String z = sc.nextLine();
                        if (gestor.getZona(z) == null) throw new Exception("Zona no existe.");
                        System.out.print("Nombre atracción: ");
                        String a = sc.nextLine();
                        System.out.print("Capacidad: ");
                        int c = Integer.parseInt(sc.nextLine());
                        gestor.getZona(z).agregarAtraccion(a, c);
                        break;
                    case 3:
                        gestor.getAll().forEach((k, v) -> System.out.println(k + ": " + v.getAtracciones()));
                        break;
                    case 4:
                        System.out.print("Tamaño del grupo: ");
                        int t = Integer.parseInt(sc.nextLine());
                        System.out.println(gestor.asignarGrupoMasivo(t));
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opt != 5);
    }
}
