package GameLogic;

/**
 * Created by Dimas on 11-May-16.
 */
public class CharacterLogic {

    //TODO make an Interface and a Manager to support different rule sets.
    public static int MaxStatValue = 100;
    public static int MinStatValue = 0;
    public static int ZeroBonusValue = 10;

    /**
     * Calculate the bonus for a stat
     * @param stat the value for the stat
     * @return the bonus
     */
    public static int getBonusFromStat(int stat){
        int res = stat-ZeroBonusValue;
        if (stat<ZeroBonusValue)
            res--;
        return res/2;
    }

    /**
     * Calculate fields (hp/mana/AC) from stats
     * @param stat the stat
     * @param base the base value of the field
     * @param multiplier the number that will multiply the stat bonus
     * @return the field value
     */
    public static int calcFieldFromStat(int stat,int base,int multiplier){
        return base + (getBonusFromStat(stat)* multiplier);
    }
}
