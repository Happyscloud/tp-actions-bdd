import java.sql.*;
public class Students {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "postgres");
    }
    public static int addStudent(Connection conn, String name, double grade) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name, grade) VALUES (?, ?) RETURNING id")) {
            ps.setString(1, name); ps.setDouble(2, grade); ResultSet rs = ps.executeQuery(); rs.next(); return rs.getInt(1);
        }
    }
    public static String[] getStudent(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT name, grade FROM students WHERE id = ?")) {
            ps.setInt(1, id); ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return new String[]{rs.getString("name"), String.valueOf(rs.getDouble("grade"))};
        }
    }
    public static Double averageGrade(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT AVG(grade) FROM students"); rs.next();
            double avg = rs.getDouble(1); return rs.wasNull() ? null : avg;
        }
    }
}
