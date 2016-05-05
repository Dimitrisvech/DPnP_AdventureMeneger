package Data;

/**
 * Created by BlackDragon on 04/04/2016.
 */
public class Domain {
    //region ENUMs
    public enum Stat{
        STR,DEX,CON,WIS,INT,CHA,LUCK
    }

    //endregion

    private static String _user;
    public static String getUser(){
        return _user;
    }
    public static void setUser(String user){
        _user=user;
    }
}
