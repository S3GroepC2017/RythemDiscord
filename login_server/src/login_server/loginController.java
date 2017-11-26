package login_server;

import java.rmi.RemoteException;

public class loginController implements Runnable {

    private LoginChecker loginChecker;
    @Override
    public void run() {

        try {
            loginChecker = new LoginChecker();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
