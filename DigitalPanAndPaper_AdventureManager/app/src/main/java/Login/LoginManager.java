package Login;

import Interfaces.ILogin;

/**
 * Created by Dimas on 04-May-16.
 * This is the class that will be changed after mocks will not be used anymore.
 */
public class LoginManager {
    /*public static ILogin getLogin(){
        return LoginMock.getInstance();
    }*/
    public static ILogin getLogin(){
        return MySqlLogin.getInstance();
    }
}
