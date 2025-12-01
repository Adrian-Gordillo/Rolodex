public class rolodex {

    private static final String CSV_FILE_PATH = "writable/contacts.csv";
    private static final String CSV_HEADER = "Nombre,Telefono,Email";

    public static void main(String[] args) {
        // Inicializar el archivo CSV
        initializeCsvFile();

        // Mostrar mensaje de bienvenida
        displayWelcome();

        // Crear objeto para leer de la consola
        java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(System.in));

        // Bucle principal
        while (true) {
            try {
                // Solicitar nombre
                System.out.print("\nNombre completo (o 'exit' para salir): ");
                String name = reader.readLine();

                // Verificar si el usuario quiere salir
                if (name == null || name.trim().equalsIgnoreCase("exit")) {
                    System.out.println("\n¡Gracias por usar Rolodex Importer!");
                    System.out.println("Los contactos han sido guardados en: " + CSV_FILE_PATH);
                    break;
                }

                // Validar que el nombre no esté vacío
                if (name.trim().isEmpty()) {
                    System.out.println("Error: El nombre no puede estar vacío.");
                    continue;
                }

                // Solicitar teléfono
                System.out.print("Número de teléfono: ");
                String phone = reader.readLine();

                if (phone == null || phone.trim().isEmpty()) {
                    System.out.println("Error: El teléfono no puede estar vacío.");
                    continue;
                }

                // Solicitar email
                System.out.print("Dirección de email: ");
                String email = reader.readLine();

                if (email == null || email.trim().isEmpty()) {
                    System.out.println("Error: El email no puede estar vacío.");
                    continue;
                }

                // Añadir el contacto al CSV
                appendToCSV(name.trim(), phone.trim(), email.trim());

                System.out.println("Contacto añadido exitosamente!");

            } catch (java.io.IOException e) {
                System.err.println("Error al leer la entrada: " + e.getMessage());
            }
        }

        try {
            reader.close();
        } catch (java.io.IOException e) {
            System.err.println("Error al cerrar el lector: " + e.getMessage());
        }
    }

    /**
     * Inicializa el archivo CSV creando el directorio si es necesario
     * y añadiendo el encabezado si el archivo no existe o está vacío
     */
    private static void initializeCsvFile() {
        try {
            // Crear el directorio 'writable' si no existe
            java.io.File directory = new java.io.File("writable");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            java.io.File csvFile = new java.io.File(CSV_FILE_PATH);

            // Si el archivo no existe o está vacío, crear con encabezado
            if (!csvFile.exists() || csvFile.length() == 0) {
                java.io.BufferedWriter writer = new java.io.BufferedWriter(
                        new java.io.FileWriter(csvFile, false));
                writer.write(CSV_HEADER);
                writer.newLine();
                writer.close();
            }

        } catch (java.io.IOException e) {
            System.err.println("Error al inicializar el archivo CSV: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Añade un nuevo contacto al archivo CSV
     */
    private static void appendToCSV(String name, String phone, String email) {
        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter(CSV_FILE_PATH, true));

            // Escapar los campos y construir la línea CSV
            String csvLine = escapeCsvField(name) + "," +
                    escapeCsvField(phone) + "," +
                    escapeCsvField(email);

            writer.write(csvLine);
            writer.newLine();
            writer.close();

        } catch (java.io.IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    /**
     * Escapa los campos CSV que contienen caracteres especiales
     * (comas, comillas dobles, saltos de línea)
     */
    private static String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }

        // Si el campo contiene coma, comilla doble o salto de línea,
        // debe ir entre comillas dobles
        if (field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r")) {
            // Reemplazar cada comilla doble con dos comillas dobles
            String escaped = field.replace("\"", "\"\"");
            // Envolver el campo en comillas dobles
            return "\"" + escaped + "\"";
        }

        return field;
    }

    /**
     * Muestra el mensaje de bienvenida
     */
    private static void displayWelcome() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║     ROLODEX IMPORTER - Digitalizador CSV      ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Introduce los datos de cada contacto.");
        System.out.println("Los datos se guardarán en: " + CSV_FILE_PATH);
        System.out.println("Escribe 'exit' en el nombre para salir.");
        System.out.println();
    }
}