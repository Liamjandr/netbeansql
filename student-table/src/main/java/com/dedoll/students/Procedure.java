package com.dedoll.students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Procedure {

    private static final String URL =
            "jdbc:mariadb://localhost:3306/dmsamplederoxasdollentes";

     /*
     * @return a new {@link Connection}
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");
        return DriverManager.getConnection(URL, user, pass);
    }
}

