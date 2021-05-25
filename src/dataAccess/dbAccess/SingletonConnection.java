package dataAccess.dbAccess;

import exception.CommunicationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SingletonConnection {
    private static Connection uniqueConnection;

    public static Connection getInstance() throws CommunicationException {
        if(uniqueConnection == null) {
            try {
                uniqueConnection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/db_disasters_project", "root","premier");
            }
            catch (SQLException exception) {
                throw new CommunicationException(exception.getMessage());
            }
        }
        return uniqueConnection;
    }
}
