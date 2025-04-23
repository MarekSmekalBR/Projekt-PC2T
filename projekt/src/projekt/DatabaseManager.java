package projekt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:studenti.db";

    public static void ulozStudentyDoDatabaze(List<Student> studenti) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // Vytvoření tabulky studenti
            String createStudenti = "CREATE TABLE IF NOT EXISTS studenti (" +
                    "id INTEGER PRIMARY KEY," +
                    "jmeno TEXT NOT NULL," +
                    "prijmeni TEXT NOT NULL," +
                    "rokNarozeni INTEGER," +
                    "obor TEXT)";
            conn.createStatement().execute(createStudenti);

            // Vytvoření tabulky znamky
            String createZnamky = "CREATE TABLE IF NOT EXISTS znamky (" +
                    "student_id INTEGER," +
                    "znamka INTEGER," +
                    "FOREIGN KEY (student_id) REFERENCES studenti(id))";
            conn.createStatement().execute(createZnamky);

            conn.createStatement().execute("DELETE FROM znamky");
            conn.createStatement().execute("DELETE FROM studenti");
            
            // Vkládání studentů
            String insertStudent = "INSERT INTO studenti (id, jmeno, prijmeni, rokNarozeni, obor) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertStudent);

            // Vkládání známek
            String insertZnamka = "INSERT INTO znamky (student_id, znamka) VALUES (?, ?)";
            PreparedStatement znamkaStmt = conn.prepareStatement(insertZnamka);

            for (Student s : studenti) {
                pstmt.setInt(1, s.getId());
                pstmt.setString(2, s.getJmeno());
                pstmt.setString(3, s.getPrijmeni());
                pstmt.setInt(4, s.getRokNarozeni());

                String obor = "...";
                if (s instanceof Telekomunikace) {
                    obor = "Telekomunikace";
                } else if (s instanceof Kyberbezpecnost) {
                    obor = "Kyberbezpecnost";
                }
                pstmt.setString(5, obor);
                pstmt.executeUpdate();

                // Uložit známky
                for (int znamka : s.getZnamky()) {
                    znamkaStmt.setInt(1, s.getId());
                    znamkaStmt.setInt(2, znamka);
                    znamkaStmt.executeUpdate();
                }
            }

            pstmt.close();
            znamkaStmt.close();

            System.out.println("Studenti a znamky byly uspesne do databaze.");

        } catch (SQLException e) {
            System.out.println("Chyba pri ukladani do databaze: " + e.getMessage());
        }
    }

    public static List<Student> nactiStudentyZDatabaze() {
        List<Student> studenti = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM studenti")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int rok = rs.getInt("rokNarozeni");
                String obor = rs.getString("obor");

                Student s = null;
                if ("Telekomunikace".equalsIgnoreCase(obor)) {
                    s = new Telekomunikace(jmeno, prijmeni, rok);
                } else if ("Kyberbezpecnost".equalsIgnoreCase(obor)) {
                    s = new Kyberbezpecnost(jmeno, prijmeni, rok);
                }

                if (s != null) {
                    s.setId(id);  // zachovat ID
                    studenti.add(s);
                }
            }

            // Načtení známek a přiřazení studentům
            ResultSet rsZnamky = stmt.executeQuery("SELECT * FROM znamky");
            while (rsZnamky.next()) {
                int studentId = rsZnamky.getInt("student_id");
                int znamka = rsZnamky.getInt("znamka");

                for (Student s : studenti) {
                    if (s.getId() == studentId) {
                        s.pridaniZnamky(znamka);  // zároveň přepočítá průměr
                        break;
                    }
                }
            }

            System.out.println("Studenti a jejich znamky byly nacteny z databaze.");

        } catch (SQLException e) {
            System.out.println("Chyba pri nacitani z databaze: " + e.getMessage());
        }

        return studenti;
    }

}

