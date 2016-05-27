package Mocks;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import Data.Character;
import Data.Domain;

/**
 * Created by Dimas on 04-May-16.
 */
public class DataAgentMock {
    private static DataAgentMock ourInstance = new DataAgentMock();

    public static DataAgentMock getInstance(Context ctx) {
        return ourInstance;
    }
    ArrayList<Character> list;

    private DataAgentMock() {
        list = new ArrayList<>();
        for(int i=0;i<7;i++){
            Character tempChar = new Character(1,1,"Bob_"+i,"Sponge", "Sponger", "Idiot_"+i, 100, 100, 10);
            tempChar.setStat(Domain.Stat.STR,12);
            tempChar.setStat(Domain.Stat.DEX,12);
            tempChar.setStat(Domain.Stat.CON,12);
            tempChar.setStat(Domain.Stat.INT,12);
            tempChar.setStat(Domain.Stat.WIS,12);
            tempChar.setStat(Domain.Stat.CHA,12);

            list.add(tempChar);
        }
    }

    //@Override
    public Character getCharByName(Context context, String name) {

        for (Character c:list) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    //@Override
    public ArrayList<Character> getAllCharsByUser(Context context,String username) {
        Collections.sort(list,Character.CharNameComparator);
        return list;
    }

    //@Override
    public void deleteCharFromUser(Context context,String username, String charName) {
        int j=-1,i=0;
        for (Character c:list) {
            if (c.getName().equals(charName))
                j=i;
            i++;
        }
        if(j>=0)
            list.remove(j);
    }

    //@Override
    public void updateOrInsertCharOfUser(Context context,String username, Character character) {
        Character c = getCharByName(context,character.getName());
        if (c!=null)
            deleteCharFromUser(context,username,c.getName());
        list.add(character);
    }

    private Character _tempChar;

    //@Override
    public void setLocalTemporaryCharacter(Character character) {
        this._tempChar = character;
    }

    //@Override
    public Character getLocalTemporaryCharacter() {
        return this._tempChar;
    }
}
