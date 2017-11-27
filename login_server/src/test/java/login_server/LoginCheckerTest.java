package login_server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginCheckerTest {

    //Fields
    LoginChecker loginChecker;

    @BeforeEach
    void setUp() throws RemoteException {
        loginChecker = new LoginChecker();
    }

    @Test
    void checkLogin() {

        assertTrue(loginChecker.checkLogin("joe","admin"));

    }

}