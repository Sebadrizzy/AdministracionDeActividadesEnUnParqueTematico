import javax.swing.JOptionPane;

public class MenuVentanas {
    private GestorParque gestor;

    public MenuVentanas(GestorParque gestor) {
        this.gestor = gestor;
    }

    public void iniciar() {
        boolean salir = false;
        String menu = "--- GESTIÓN PARQUE TEMÁTICO ---\n\n"
                    + "1. Agregar Zona Nueva\n"
                    + "2. Agregar Atracción a una Zona\n"
                    + "3. Listar Zonas y Atracciones\n"
                    + "4. Buscar Atracción\n"
                    + "5. Editar Capacidad de Atracción\n"
                    + "6. Eliminar Atracción\n"
                    + "7. Evaluar Ingreso de Grupo Masivo (SIA-9)\n"
                    + "8. Salir y Guardar\n\n"
                    + "Opción:";
        
        while (!salir) {
            String opcion = JOptionPane.showInputDialog(null, menu, "Menú", JOptionPane.PLAIN_MESSAGE);
            
            if (opcion == null || opcion.equals("8")) break;

            try {
                switch (opcion) {
                    case "1": uiAgregarZona(); break;
                    case "2": uiAgregarAtraccion(); break;
                    case "3": uiMostrarListado(); break;
                    case "4": 
                        String zb = JOptionPane.showInputDialog("Zona a buscar:");
                        String ab = JOptionPane.showInputDialog("Nombre Atracción:");
                        Atraccion encontrada = gestor.buscarAtraccion(zb, ab);
                        if(encontrada != null) JOptionPane.showMessageDialog(null, "Encontrada: " + encontrada);
                        else throw new ZonaNoEncontradaException("La atracción o zona no existe.");
                        break;
                    case "5":
                        String ze = JOptionPane.showInputDialog("Zona de la atracción:");
                        String ae = JOptionPane.showInputDialog("Nombre Atracción a editar:");
                        int nCap = Integer.parseInt(JOptionPane.showInputDialog("Nueva Capacidad:"));
                        if(nCap <= 0) throw new CapacidadExcedidaException("Capacidad debe ser mayor a 0.");
                        gestor.editarCapacidadAtraccion(ze, ae, nCap);
                        JOptionPane.showMessageDialog(null, "Editado correctamente.");
                        break;
                    case "6": 
                        String zDel = JOptionPane.showInputDialog("Zona de la atracción:");
                        String aDel = JOptionPane.showInputDialog("Nombre Atracción a eliminar:");
                        if(gestor.eliminarAtraccion(zDel, aDel)) JOptionPane.showMessageDialog(null, "Eliminada.");
                        else throw new ZonaNoEncontradaException("No se encontró el elemento para eliminar.");
                        break;
                    case "7": uiEvaluarGrupo(); break; 
                    default: JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            } catch (ZonaNoEncontradaException e) {
                JOptionPane.showMessageDialog(null, "Error de Búsqueda: " + e.getMessage(), "Alerta", 0);
            } catch (CapacidadExcedidaException e) {
                JOptionPane.showMessageDialog(null, "Error de Capacidad: " + e.getMessage(), "Alerta", 0);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debe ingresar un número.", "Alerta", 0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Alerta", 0);
            }
        }
    }


    private void uiAgregarZona() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre de la nueva zona:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            gestor.addZona(nombre);
            JOptionPane.showMessageDialog(null, "Zona '" + nombre + "' agregada.");
        }
    }

    private void uiAgregarAtraccion() throws Exception {
        String zona = JOptionPane.showInputDialog("¿A qué zona desea agregar la atracción?");
        if (zona == null) return;
        
        if (gestor.getZona(zona) == null) {
            throw new ZonaNoEncontradaException("La zona '" + zona + "' no existe.");
        }
        
        String atraccion = JOptionPane.showInputDialog("Nombre de la Atracción:");
        int cap = Integer.parseInt(JOptionPane.showInputDialog("Capacidad Máxima:"));
        
        if (cap <= 0 || cap > 2000) {
            throw new CapacidadExcedidaException("Capacidad inválida.");
        }
        
        gestor.getZona(zona).agregarAtraccion(atraccion, cap);
        JOptionPane.showMessageDialog(null, "Atracción agregada a " + zona + ".");
    }

    private void uiMostrarListado() {
        StringBuilder sb = new StringBuilder("--- Inventario del Parque ---\n\n");
        gestor.getAll().forEach((nombre, zona) -> {
            sb.append("ZONA: ").append(nombre).append("\n");
            zona.getAtracciones().forEach(a -> sb.append("  - ").append(a.toString()).append("\n"));
            sb.append("\n");
        });
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void uiEvaluarGrupo() {
        String input = JOptionPane.showInputDialog("Cantidad de personas del grupo:");
        if (input != null) {
            int tamano = Integer.parseInt(input);
            String analisis = gestor.asignarGrupoMasivo(tamano);
            JOptionPane.showMessageDialog(null, analisis, "Análisis Logístico", 2);
        }
    }
}
