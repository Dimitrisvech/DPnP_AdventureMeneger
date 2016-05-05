package Data;

/**
 * Created by Dimas on 05-May-16.
 */
public class World {

    private String WorldName;
    private String WorldDesc;

    public World(){
        WorldName = "";
        WorldDesc = "";
    }

    public World(String worldName,String worldDesc){
        WorldName = worldName;
        WorldDesc = worldDesc;
    }


    public String getWorldName() {
        return WorldName;
    }

    public void setWorldName(String worldName) {
        WorldName = worldName;
    }

    public String getWorldDesc() {
        return WorldDesc;
    }

    public void setWorldDesc(String worldDesc) {
        WorldDesc = worldDesc;
    }
}
