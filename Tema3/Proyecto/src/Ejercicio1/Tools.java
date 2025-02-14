package Ejercicio1;

import java.io.*;

public class Tools {

    public static File obtenerArchivo() {
        File archivo = new File("bbdd.txt");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        }
        return archivo;
    }

    public static boolean insertarEnArchivo(String id, String nombre, String apellido) {
        File archivo = obtenerArchivo();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(id)) {
                    return false;
                }
            }
            pw.println(id + "," + nombre + "," + apellido);
            return true;
        } catch (IOException e) {
            System.out.println("Error al insertar en el archivo: " + e.getMessage());
            return false;
        }
    }

    public static String buscarEnArchivo(String id) {
        File archivo = obtenerArchivo();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(id)) {
                    return linea;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar en el archivo: " + e.getMessage());
        }
        return null;
    }

    public static boolean eliminarDeArchivo(String id) {
        File archivo = obtenerArchivo();
        File archivoTemp = new File("bbdd_temp.txt");
        boolean eliminado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             PrintWriter pw = new PrintWriter(new FileWriter(archivoTemp))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (!partes[0].equals(id)) {
                    pw.println(linea);
                } else {
                    eliminado = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar en el archivo: " + e.getMessage());
            return false;
        }

        if (archivo.delete() && archivoTemp.renameTo(archivo)) {
            return eliminado;
        } else {
            System.out.println("Error al reemplazar el archivo");
            return false;
        }
    }

}
