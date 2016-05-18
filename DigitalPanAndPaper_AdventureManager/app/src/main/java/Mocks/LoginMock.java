package Mocks;

import Data.Domain;
import Data.User;
import Interfaces.ILogin;

/**
 * Created by Dimas on 30-Apr-16.
 */
public class LoginMock {
    private static LoginMock ourInstance = new LoginMock();

    public static LoginMock getInstance() {
        return ourInstance;
    }

    private LoginMock() {
    }


    public boolean isPassOk(String password) {
        if (password!=null && !password.isEmpty())
            return true;
        return false;
    }


    public boolean isUserOk(String username) {
        if (username!=null && !username.isEmpty())
            return true;
        return false;
    }


    public boolean login(String username, String password) {
        if (username.contains("a")){
            Domain.setUser(new User(0,username,password,"a@a.com"));
            return true;
        }
        return false;
    }
}
