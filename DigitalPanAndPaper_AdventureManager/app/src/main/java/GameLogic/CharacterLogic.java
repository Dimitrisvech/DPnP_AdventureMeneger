package GameLogic;

/**
 * Created by Dimas on 11-May-16.
 */
public class CharacterLogic {

    public static int MaxStatValue = 100;
    public static int MinStatValue = 0;
    public static int ZeroBonusValue = 10;

    public static int getBonusFromStat(int stat){
        int res = stat-ZeroBonusValue;
        if (stat<ZeroBonusValue)
            res--;
        return res/2;
    }
}
