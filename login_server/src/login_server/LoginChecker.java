package login_server;

import com.csharp.sharedclasses.ILogin;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class LoginChecker extends UnicastRemoteObject implements ILogin {

    private String connectionstring;
    private Connection connection;
    private ResultSet resultSet = null;

    private final String LogincheckQuery = "SELECT UserName FROM Account WHERE UserName = ? AND Password = ?";


    public LoginChecker() throws RemoteException {
    }

    @Override
    public Boolean checkLogin(String username, String hashedpassword) {
        try {
            init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Boolean succes = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(LogincheckQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedpassword);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                succes = username.contentEquals(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultset();
            closeConnection();
            return succes;
        }

    }

    public Boolean init() throws ClassNotFoundException {

        Boolean success;

        Class.forName("com.mysql.jdbc.Driver");
        connectionstring = "jdbc:sqlserver://PTLoginServer;" +
                "databaseName=LoginDB;"
                + "user=admin;"
                + "password=admin;";
        success = true;

        try {
            connection = DriverManager.getConnection(connectionstring);
            success = true;
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
            return success;
        }

//        ClassLoader classLoader = getClass().getClassLoader();
//        try (FileReader reader = new FileReader(classLoader.getResource("db.properties").getFile())) {
//
//            Properties properties = new Properties();
//            properties.load(reader);
//
//
//            connectionstring = "jdbc:sqlserver://" + properties.getProperty("db.driverconnectiondetails") + "" +
//                    ";databaseName=" + properties.getProperty("db.database") + ";"
//                    + "user=" + properties.getProperty("db.username") + ";"
//                    + "password=" + properties.getProperty("db.password") + ";";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return success;
//        }
//        try {
//            connection = DriverManager.getConnection(connectionstring);
//            success = true;
//            return success;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            closeConnection();
//            return success;
//        }
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
