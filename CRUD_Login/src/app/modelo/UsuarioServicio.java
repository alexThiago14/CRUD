    package app.modelo;

    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.HashMap;

    public class UsuarioServicio {
        private HashMap<String, String> usuarios = new HashMap<>();

        public UsuarioServicio() {
            cargarUsuariosDesdeArchivo();
        }

        private void cargarUsuariosDesdeArchivo() {
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println("Leyendo línea: " + linea); // 👈 Agrega esto para debug
                    String[] partes = linea.split(";");
                    if (partes.length == 2) {
                        String usuario = partes[0].trim();
                        String clave = partes[1].trim();
                        usuarios.put(usuario, clave);
                        System.out.println("Registrado: " + usuario + " -> " + clave);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
            }
        }


        public boolean validar(String usuario, String clave) {
            return usuarios.containsKey(usuario) && usuarios.get(usuario).equals(clave);
        }

    }

