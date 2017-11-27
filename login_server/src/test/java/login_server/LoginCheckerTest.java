package login_server;

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