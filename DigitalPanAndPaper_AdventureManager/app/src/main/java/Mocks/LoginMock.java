package Mocks;

import Interfaces.ILogin;

/**
 * Created by Dimas on 30-Apr-16.
 */
public class LoginMock implements ILogin {
    private static LoginMock ourInstance = new LoginMock();

    public static LoginMock getInstance() {
        return ourInstance;
    }

    private LoginMock() {
    }

    @Override
    public boolean isPassOk(String password) {
        if (password!=null && !password.isEmpty())
            return true;
        return false;
    }

    @Override
    public boolean isUserOk(String username) {
        if (username!=null && !username.isEmpty())
            return true;
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        if (username.contains("a"))
            return true;
        return false;
    }
}
