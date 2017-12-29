package login_server;

import com.csharp.sharedclasses.ILogin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class LoginChecker extends UnicastRemoteObject implements ILogin
{

    private String connectionstring;
    private Connection connection;
    private ResultSet resultSet = null;

    private final String loginCheckQuery = "SELECT UserName FROM Account WHERE UserName = ? AND Password = ?";


    public LoginChecker() throws RemoteException
    {
    }

    @Override
    public boolean checkLogin(String username, String hashedPassword)
    {
        try
        {
            init();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        boolean success = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(loginCheckQuery))
        {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                success = username.contentEquals(resultSet.getString(1));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeResultSet();
            closeConnection();
            return success;
        }

    }

    public boolean init() throws ClassNotFoundException
    {

        boolean success = false;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connectionstring = "jdbc:sqlserver://PTLoginServer;" +
                "databaseName=LoginDB;"
                + "user=admin;"
                + "password=admin;";

        try
        {
            connection = DriverManager.getConnection(connectionstring);
            success = true;
            return success;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            closeConnection();
            return success;
        }
    }


    private void closeConnection()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }

    private void closeResultSet()
    {
        if (resultSet != null)
        {
            try
            {
                resultSet.close();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }

}
