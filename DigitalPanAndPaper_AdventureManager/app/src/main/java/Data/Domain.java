package Data;

/**
 * Created by BlackDragon on 04/04/2016.
 */
public class Domain {
    //region ENUMs
    public enum Stat{
        STR,DEX,CON,WIS,INT,CHA
    }

    //endregion
    //local: 192.168.1.12 Global: 37.142.201.27
    private final static String ip_address = "37.142.201.27";
    public final static String login_url = "http://"+ip_address+"/android_connect/get_user_by_name.php";
    public final static String select_all_characters_url = "http://"+ip_address+"/android_connect/character_scripts/get_all_characters.php";

    private static User _user;
    public static User getUser(){
        return _user;
    }
    public static void setUser(User user){
        _user=user;
    }

}
