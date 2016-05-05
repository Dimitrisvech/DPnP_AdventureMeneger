package Interfaces;

/**
 * Created by Dimas on 30-Apr-16.
 */
public interface ILogin {

    /**
     * Check if the password is up to standard (Min characters and etc...)
     * @param password
     * @return true - if OK
     */
    boolean isPassOk(String password);

    /**
     * Check if the username is up to standard (Min characters and etc...)
     * @param username
     * @return true - if OK
     */
    boolean isUserOk(String username);

    /**
     * Attempt log in
      * @param username
     * @param password
     * @return true - if successful
     */
    boolean login(String username,String password);

}
