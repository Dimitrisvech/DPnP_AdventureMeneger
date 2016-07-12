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
    private final static String ip_address = "192.168.1.12";
    public final static String login_url = "http://"+ip_address+"/android_connect/get_user_by_name.php";
    public final static String select_all_characters_url = "http://"+ip_address+"/android_connect/character_scripts/get_all_characters.php";
    public final static String delete_character_url = "http://"+ip_address+"/android_connect/character_scripts/delete_character.php";
    public final static String insert_character_url = "http://"+ip_address+"/android_connect/character_scripts/insert_character.php";
    public final static String update_character_url = "http://"+ip_address+"/android_connect/character_scripts/update_character.php";
    public final static String select_character_url = "http://"+ip_address+"/android_connect/character_scripts/select_character.php";

    private static User _user;
    public static User getUser(){
        return _user;
    }
    public static void setUser(User user){
        _user=user;
    }

}
