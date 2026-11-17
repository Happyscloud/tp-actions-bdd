import org.junit.jupiter.api.*; import java.sql.*; import static org.junit.jupiter.api.Assertions.*;
public class StudentsTest {
    static Connection conn;
    @BeforeAll static void setup() throws SQLException { conn = Students.getConnection(); }
    @BeforeEach void clear() throws SQLException { try (Statement st = conn.createStatement()) { st.execute("TRUNCATE students RESTART IDENTITY"); } }
    @Test void testAddAndGet() throws SQLException {
        int id = Students.addStudent(conn, "Ali", 15); String[] s = Students.getStudent(conn, id);
        assertEquals("Ali", s[0]); assertEquals(15.0, Double.parseDouble(s[1]));
    }
    @Test void testGetMissing() throws SQLException { assertNull(Students.getStudent(conn, 9999)); }
    @Test void testAverage() throws SQLException {
        Students.addStudent(conn, "Amel", 10); Students.addStudent(conn, "Karim", 14);
        assertEquals(12.0, Students.averageGrade(conn));
    }
}
