package lk.ijse.dep11;

import lk.ijse.dep11.db.DbConnection;
import lk.ijse.dep11.tm.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MainFormControllerTest {

    private MainFormController controller = new MainFormController();

    @CsvSource({"2303111001,true" ,"1203111001,false","ijse,false","2403111001,false"})
    @ParameterizedTest
    void isValid(String id, boolean expected) {
        boolean result = controller.isValid(id);
        assertEquals(result,expected);
    }

    @Nested
    class relatedTest{
        @BeforeEach
        void setUp() throws SQLException {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
        }

        @AfterEach
        void tearDown() throws SQLException {
            DbConnection.getInstance().getConnection().rollback();
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }

        @CsvSource ({"2303111001,kasun,kasuna,true","2303111001,pubudu,pubba,false","2303111002,pasan,pasana,true"})
        @ParameterizedTest
        void addStudent(String id, String name, String card, boolean expccted) {
            assertDoesNotThrow(()->{
                boolean result = controller.addStudent(id, name, card);
                assertEquals(expccted,result);
            });
        }

        @CsvSource ("2303111001,true")
        @ParameterizedTest
        void deleteStudent(String id,boolean expected) {
            assertDoesNotThrow(()->{
                addStudent("2303111001", "kasun", "kasuna",true);
                boolean result = controller.deleteStudent(id);
                assertEquals(result,expected);
            });
        }
    }



}