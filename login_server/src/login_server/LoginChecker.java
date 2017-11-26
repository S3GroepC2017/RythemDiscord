package login_server;

import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class LoginChecker extends UnicastRemoteObject implements ILogin {

    private String connectionstring;
    private Connection connection;
    private ResultSet resultSet = null;

    private final String LogincheckQuery = "SELECT TekenObjectArray FROM Tekenobjecten WHERE DrawingName = ?";


    protected LoginChecker() throws RemoteException {
    }

    @Override
    public Boolean checkLogin(String username, String hashedpassword) {
        init();


        return true;
    }

    public Boolean init() {

        Boolean success = false;

        ClassLoader classLoader = getClass().getClassLoader();

        try (FileReader reader = new FileReader(classLoader.getResource("db.properties").getFile())) {

            Properties properties = new Properties();
            properties.load(reader);



            connectionstring = "jdbc:sqlserver://" + properties.getProperty("db.driverconnectiondetails") + "" +
                    ";databaseName=" + properties.getProperty("db.database") + ";"
                    + "user=" + properties.getProperty("db.username") + ";"
                    + "password=" + properties.getProperty("db.password") + ";";

        } catch (Exception e) {
            e.printStackTrace();
            return success;
        }
        try {
            connection = DriverManager.getConnection(connectionstring);
            success = true;
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
            return success;
        }
    }


    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private void closeResultset() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

}
