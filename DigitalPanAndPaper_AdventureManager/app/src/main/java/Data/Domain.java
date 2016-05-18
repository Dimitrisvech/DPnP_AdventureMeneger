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

    private static User _user;
    public static User getUser(){
        return _user;
    }
    public static void setUser(User user){
        _user=user;
    }

}
