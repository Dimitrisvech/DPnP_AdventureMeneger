package Mocks;

import java.util.ArrayList;

import Data.Character;
import Data.World;
import Interfaces.IDataAgent;

/**
 * Created by Dimas on 04-May-16.
 */
public class DataAgentMock implements IDataAgent {
    private static DataAgentMock ourInstance = new DataAgentMock();

    public static DataAgentMock getInstance() {
        return ourInstance;
    }
    ArrayList<Character> list;
    private DataAgentMock() {
        list = new ArrayList<>();
        for(int i=0;i<7;i++){
            Character tempChar = new Character("Bob_"+i,"Sponge", "Sponger", "Idiot_"+i, 100, 100, 10);
            World tempWorld = new World("Earth_"+i,"abc");
            tempChar.setWorld(tempWorld);
            list.add(tempChar);
        }
    }

    @Override
    public Character getCharByName(String name) {

        for (Character c:list) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    @Override
    public ArrayList<Character> getAllCharsByUser(String username) {
        return list;
    }

    @Override
    public void deleteCharFromUser(String username, String charName) {
        int j=-1,i=0;
        for (Character c:list) {
            if (c.getName().equals(charName))
                j=i;
            i++;
        }
        if(j>=0)
            list.remove(j);
    }

    @Override
    public void updateOrInsertCharOfUser(String username, Character character) {
        Character c = getCharByName(character.getName());
        if (c!=null)
            deleteCharFromUser(username,c.getName());
        list.add(character);
    }
}
