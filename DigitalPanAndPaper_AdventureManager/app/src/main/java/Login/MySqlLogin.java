package Login;


import com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager.LoginActivity;

import Data.User;
import Interfaces.ILogin;

/**
 * Created by Dimas on 18-May-16.
 */
public class MySqlLogin implements ILogin {
    private static MySqlLogin ourInstance = new MySqlLogin();

    public static MySqlLogin getInstance() {
        return ourInstance;
    }

    private MySqlLogin() {
    }

    public static User _user;

    @Override
    public boolean isPassOk(String password) {
        return (password != null && !password.isEmpty());

    }

    @Override
    public boolean isUserOk(String username) {
        return (username != null && !username.isEmpty());

    }

    @Override
    public void login(String username, String password) {
        _user = new User(-1,"","","");
        LoginBackgroundWorker bgw = new LoginBackgroundWorker(LoginActivity.context);
        bgw.execute(username, password);
    }



}
