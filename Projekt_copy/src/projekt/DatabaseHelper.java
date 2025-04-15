package projekt;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:studenti.db";

    // Inicializace databáze a vytvoření tabulky, pokud neexistuje
    public static void inicializujDatabazi() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS studenti (" +
                         "id INTEGER PRIMARY KEY, " +
                         "jmeno TEXT, " +
                         "prijmeni TEXT, " +
                         "rokNarozeni INTEGER, " +
                         "prumer REAL, " +
                         "obor TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Chyba při vytváření DB: " + e.getMessage());
        }
    }

    // Uložení jednotlivého studenta do databáze
    public static void ulozStudenta(Student s) {
        String obor = (s instanceof Telekomunikace) ? "Telekomunikace" :
                      (s instanceof Kyberbezpecnost) ? "Kyberbezpecnost" : "Neznamy";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT OR REPLACE INTO studenti (id, jmeno, prijmeni, rokNarozeni, prumer, obor) VALUES (?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, s.getId());
            ps.setString(2, s.getJmeno());
            ps.setString(3, s.getPrijmeni());
            ps.setInt(4, s.getRokNarozeni());
            ps.setDouble(5, s.getStudijniPrumer());
            ps.setString(6, obor);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Chyba při ukládání studenta: " + e.getMessage());
        }
    }

    // Uložení všech studentů z managera do databáze
    public static void ulozVsechnyStudenty(StudentManager manager) {
        for (Student s : manager.getVsechnyStudenty()) {
            ulozStudenta(s);
        }
    }

    // Načítání studentů z databáze do StudentManager
    public static void nacistStudentyZDatabaze(StudentManager manager) {
        String url = "jdbc:sqlite:studenti.db";
        String sql = "SELECT jmeno, prijmeni, id, vek, obor FROM studenti";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int id = rs.getInt("id");
                int vek = rs.getInt("vek");
                String obor = rs.getString("obor");

                Student student;

                if (obor.equalsIgnoreCase("Telekomunikace")) {
                    student = new Telekomunikace(jmeno, prijmeni, vek, id);
                } else if (obor.equalsIgnoreCase("Kyberbezpecnost") || obor.equalsIgnoreCase("Kyberbezpečnost")) {
                    student = new Kyberbezpecnost(jmeno, prijmeni, vek, id);
                } else {
                    System.out.println("Neznámý obor: " + obor);
                    continue;
                }

                manager.pridejStudenta(student);
            }

            System.out.println("Data byla úspěšně načtena z databáze.");

        } catch (SQLException e) {
            System.out.println("Chyba při načítání dat z databáze: " + e.getMessage());
        }
    }
}

}
