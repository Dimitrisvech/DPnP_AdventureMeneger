package Data;

import java.util.ArrayList;

/**
 * Created by BlackDragon on 04/04/2016.
 */
public class Skills {
    //region Data
    public ArrayList<Skill> skillList;
    //endregion

    //region constructors
    public Skills(){
        skillList=new ArrayList<>();
    }
    public Skills(ArrayList<Skill> skills){
        skillList=skills;
    }
    //endregion

    public void clearList(){
        skillList=new ArrayList<>();
    }

    public void addSkill(String name,String desc,int diceType,Domain.Stat stat){
        skillList.add(new Skill( name, desc, diceType, stat));
    }

    public ArrayList<Skill> getSkills(){
        return skillList;
    }
}
//region Skill class
class Skill{
    String skillName;
    String skillDesc;
    int skillDiceType;
    Domain.Stat skillStat;

    public Skill(String name,String desc,int diceType,Domain.Stat stat){
        skillName=name;
        skillDesc=desc;
        skillDiceType=diceType;
        skillStat=stat;
    }

}
//endregion
