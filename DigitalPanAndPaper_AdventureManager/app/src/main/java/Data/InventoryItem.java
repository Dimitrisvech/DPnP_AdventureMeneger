package Data;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Arik on 04-Aug-16.
 */
public class InventoryItem implements Serializable{
    //region Properties
    private int cid;
    private int armorClass;
    private String EquippedSpot;
    private int hitDie;
    private int id;
    private String Name;
    private String Type;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public String getEquippedSpot() {
        return EquippedSpot;
    }

    public void setEquippedSpot(String equippedSpot) {
        EquippedSpot = equippedSpot;
    }

    public int getHitDie() {
        return hitDie;
    }

    public void setHitDie(int hitDie) {
        this.hitDie = hitDie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public InventoryItem(int cid, int armorClass, String equippedSpot, int hitDie, int id, String name, String type) {
        this.cid = cid;
        this.armorClass = armorClass;
        EquippedSpot = equippedSpot;
        this.hitDie = hitDie;
        this.id = id;
        Name = name;
        Type = type;
    }

    public InventoryItem() {
    }

    /*Comparator for sorting the list by item Name*/
    public static Comparator<InventoryItem> itemNameComparator = new Comparator<InventoryItem>() {

        public int compare(InventoryItem c1, InventoryItem c2) {
            String itemName1 = c1.getName().toUpperCase();
            String itemName2 = c2.getName().toUpperCase();

            //ascending order
            return itemName1.compareTo(itemName2);

            //descending order
            //return CharacterName2.compareTo(CharacterName1);
        }};
    @Override
    public String toString(){
        return getName()+" ("+getType()+")";
    }
}
