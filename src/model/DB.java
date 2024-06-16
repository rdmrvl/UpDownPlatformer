// Saya [Marvel Ravindra Dioputra 2200481] mengerjakan evaluasi Tugas Masa Depan
// dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
// maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package model;      // deklarasi DB ada di folder model
import java.sql.*;  // importjava.sql untuk koneksi dengan database

public class DB {
    // menggunakan jbdc connetctor untuk menghubungkan game dengan database
    private static final String URL = "jdbc:mysql://localhost:3306/updown";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // method untuk add username pada tabel tsccore
    public static void addUser(String username) {
        String query = "INSERT INTO tscore (username, score, up, down) VALUES (?, 0, 0, 0)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method untuk mengambil data user yang ditampilkan pada main menu
    public static ResultSet getUsers() {
        String query = "SELECT * FROM tscore";
        try {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // method untuk mengupdate score, up, down berdasarkan username yang bermain jika score leboh dari score sebelumnya
    public static void updateScore(String username, int score, int up, int down) {
        String query = "UPDATE tscore SET score = ?, up = ?, down = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, score);
            pstmt.setInt(2, up);
            pstmt.setInt(3, down);
            pstmt.setString(4, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method untuk mengambil highscore user
    public static int getHighScore(String username) {
        String query = "SELECT MAX(score) AS highscore FROM tscore WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("highscore");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // jika belum ada highscore
    }

    // close connection
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
