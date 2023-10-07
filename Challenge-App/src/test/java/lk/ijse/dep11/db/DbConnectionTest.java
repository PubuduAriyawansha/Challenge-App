package lk.ijse.dep11.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DbConnectionTest {

    @Test
    void getConnection() {
        assertDoesNotThrow(()->{
            Connection connection1 = DbConnection.getInstance().getConnection();
            Connection connection2 = DbConnection.getInstance().getConnection();
            assertEquals(connection1,connection2);
        });
    }
}