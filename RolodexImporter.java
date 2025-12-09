import java.io.*;

public class RolodexImporter {

    private static final String CSV_FILE_PATH = "writable/contacts.csv";
    private static final String CSV_HEADER = "Nombre,Telefono,Email";

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {

            initializeCsvFile();

            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║   IMPORTADOR DE CONTACTOS ROLODEX / CSV    ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("Introduce los datos de cada contacto");
            System.out.println("Escribe 'exit' en el nombre para salir");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println();

            while (true) {

                System.out.print("Nombre completo: ");
                String name = reader.readLine();

                if (name == null || name.trim().equalsIgnoreCase("exit")) {
                    System.out.println();
                    System.out.println("¡Hasta luego! Contactos guardados en: " + CSV_FILE_PATH);
                    break;
                }

                if (name.trim().isEmpty()) {
                    System.out.println("Error: El nombre no puede estar vacío");
                    System.out.println();
                    continue;
                }

                System.out.print("Número de teléfono: ");
                String phone = reader.readLine();

                if (phone == null) {
                    phone = "";
                }

                System.out.print("Dirección de email: ");
                String email = reader.readLine();

                if (email == null) {
                    email = "";
                }

                appendToCSV(name.trim(), phone.trim(), email.trim());

                System.out.println("Contacto guardado exitosamente");
                System.out.println();

            }

        } catch (IOException e) {

            System.err.println("Error al procesar los datos: " + e.getMessage());
        } finally {

            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el lector: " + e.getMessage());
            }
        }
    }

    private static void initializeCsvFile() throws IOException {
        File csvFile = new File(CSV_FILE_PATH);

        File parentDir = csvFile.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!csvFile.exists() || csvFile.length() == 0) {

            FileWriter writer = new FileWriter(csvFile, false);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write(CSV_HEADER);
            bw.newLine();

            bw.close();

            System.out.println("Archivo CSV inicializado: " + CSV_FILE_PATH);
            System.out.println();
        }
    }

    private static void appendToCSV(String name, String phone, String email) throws IOException {

        FileWriter writer = new FileWriter(CSV_FILE_PATH, true);
        BufferedWriter bw = new BufferedWriter(writer);

        String escapedName = escapeCsvField(name);
        String escapedPhone = escapeCsvField(phone);
        String escapedEmail = escapeCsvField(email);

        String csvLine = escapedName + "," + escapedPhone + "," + escapedEmail;

        bw.write(csvLine);
        bw.newLine();

        bw.close();
    }

    private static String escapeCsvField(String field) {

        if (field == null || field.isEmpty()) {
            return "";
        }

        boolean needsEscaping = field.contains(",") ||
                field.contains("\"") ||
                field.contains("\n") ||
                field.contains("\r");

        if (needsEscaping) {

            String escaped = field.replace("\"", "\"\"");

            return "\"" + escaped + "\"";
        }

        return field;
    }
}