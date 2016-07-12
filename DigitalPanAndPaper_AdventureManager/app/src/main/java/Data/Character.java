package Data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.EnumMap;


/**
 * Created by BlackDragon on 04/04/2016.
 */
public class Character implements Serializable {
    //region Properties
    private int cid;
    private int uid;
    private String name;
    private String surName;
    private String race;
    private String occupation;




    private int Health;
    private int MaxHealth;
    private int Mana;
    private int MaxMana;
    private int AC;


    private EnumMap<Domain.Stat,Integer> Stats;

    //TODO Need to add  Equipped items, Inventory...

    //region Getters & Setters

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
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


    //endregion

    //endregion
    //region Constructors

    public Character(int cid,int uid,String name, String surName, String race, String occupation, int maxHealth, int maxMana, int ac) {
        this.cid = cid;
        this.uid = uid;
        this.name = name;
        this.surName = surName;
        this.race = race;
        this.occupation = occupation;
        this.Health = maxHealth;
        this.MaxHealth = maxHealth;
        this.Mana = maxMana;
        this.MaxMana = maxMana;
        this.AC = ac;
        this.Stats=new EnumMap<>(Domain.Stat.class);

    }

    public Character(int cid,int uid,String name, String surName, String race, String occupation, int maxHealth, int maxMana, int ac,EnumMap<Domain.Stat,Integer> stats){
        this( cid, uid,name,  surName,  race,  occupation,  maxHealth,  maxMana,  ac);
        initStats(stats);
    }

    public Character(int cid,int uid,String name, String surName, String race, String occupation,EnumMap<Domain.Stat,Integer> stats){
        this( cid, uid,name,  surName,  race,  occupation,  1,  1,  1);
        initStats(stats);
    }
    //Constructor for NPC's
    public Character(int cid,String race, String occupation,String NpcNumber, int maxHealth, int maxMana, int ac) {
        this(cid,-1,race+occupation+"_"+NpcNumber,"",race,  occupation,  maxHealth,  maxMana,  ac);
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
            //return CharacterName2.compareTo(CharacterName1);
        }};
}
