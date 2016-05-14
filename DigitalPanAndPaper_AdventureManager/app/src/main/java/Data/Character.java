package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;


/**
 * Created by BlackDragon on 04/04/2016.
 */
public class Character implements Serializable {
    //region Properties
    private String Name;
    private String SurName;
    private String Race;
    private String Occupation;



    private int Exp;
    private int Health;
    private int MaxHealth;
    private int Mana;
    private int MaxMana;
    private int AC;

    private World world;
    private EnumMap<Domain.Stat,Integer> Stats;

    //TODO Need to add  Equipped items, Inventory...

    //region Getters & Setters
    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String race) {
        Race = race;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public int getMaxHealth() {
        return MaxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        MaxHealth = maxHealth;
    }

    public int getMana() {
        return Mana;
    }

    public void setMana(int mana) {
        Mana = mana;
    }

    public int getMaxMana() {
        return MaxMana;
    }

    public void setMaxMana(int maxMana) {
        MaxMana = maxMana;
    }

    public int getAC() {
        return AC;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

    public int getExp() {return Exp;}

    public void setExp(int exp) {Exp = exp;}

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    //endregion

    //endregion
    //region Constructors

    public Character(String name, String surName, String race, String occupation, int maxHealth, int maxMana, int ac) {
        Name = name;
        SurName = surName;
        Race = race;
        Occupation = occupation;
        Health = maxHealth;
        MaxHealth = maxHealth;
        Mana = maxMana;
        MaxMana = maxMana;
        AC = ac;
        Stats=new EnumMap<Domain.Stat,Integer>(Domain.Stat.class);
        world=new World();
        Exp=0;
    }

    public Character(String name, String surName, String race, String occupation, int maxHealth, int maxMana, int ac,EnumMap<Domain.Stat,Integer> stats){
        this(name,  surName,  race,  occupation,  maxHealth,  maxMana,  ac);
        initStats(stats);
    }

    public Character(String name, String surName, String race, String occupation,EnumMap<Domain.Stat,Integer> stats){
        this(name,  surName,  race,  occupation,  1,  1,  1);
        initStats(stats);
    }
    //Constructor for NPC's
    public Character(String race, String occupation,String NpcNumber, int maxHealth, int maxMana, int ac) {
        this(race+occupation+"_"+NpcNumber,"",race,  occupation,  maxHealth,  maxMana,  ac);
    }

    //endregion

    //region Stats & Updates
    public void initStats(EnumMap<Domain.Stat,Integer> stats){
        Stats=stats;
    }
    public void setStat(Domain.Stat stat,int value){
        Stats.put(stat, value);
    }
    public int getStat(Domain.Stat stat){
        return Stats.get(stat);
    }

    public EnumMap<Domain.Stat,Integer> getAllStats(){
        return Stats;
    }

    public void updateMaxHealthWithStat(Domain.Stat stat,int healthPerStatPoint,int baseHealth){
        int statValue=getStat(stat);
        MaxHealth=baseHealth+(statValue*healthPerStatPoint);
        Health=MaxHealth;
    }
    public void updateMaxManaWithStat(Domain.Stat stat,int manaPerStatPoint,int baseMana){
        int statValue=getStat(stat);
        MaxMana=baseMana+(statValue*manaPerStatPoint);
        Mana=MaxMana;
    }
    public void updateAcWithStat(Domain.Stat stat,int acPerStatPoint,int baseAc){
        int statValue=getStat(stat);
        AC=baseAc+(statValue*acPerStatPoint);
    }

    //endregion
    /*Comparator for sorting the list by Student Name*/
    public static Comparator<Character> CharNameComparator = new Comparator<Character>() {

        public int compare(Character c1, Character c2) {
            String CharacterName1 = c1.getName().toUpperCase();
            String CharacterName2 = c2.getName().toUpperCase();

            //ascending order
            return CharacterName1.compareTo(CharacterName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
