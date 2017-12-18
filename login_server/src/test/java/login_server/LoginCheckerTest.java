package login_server;

import org.junit.*;

import java.rmi.RemoteException;

class LoginCheckerTest {

    //Fields
    LoginChecker loginChecker;

    @Before
    void setUp() throws RemoteException {
        loginChecker = new LoginChecker();
    }

    @Test
    void checkLogin() {

        Assert.assertTrue(loginChecker.checkLogin("joe","admin"));

    }

}