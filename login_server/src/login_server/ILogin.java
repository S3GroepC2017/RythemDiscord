package login_server;

import java.rmi.Remote;

public interface ILogin extends Remote {

    Boolean checkLogin(String username, String hashedpassword);

}
