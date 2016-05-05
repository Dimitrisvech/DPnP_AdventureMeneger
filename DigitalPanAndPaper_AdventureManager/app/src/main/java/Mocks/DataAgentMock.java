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

    private DataAgentMock() {
    }

    @Override
    public Character getCharByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Character> getAllCharsByUser(String username) {
        ArrayList<Character> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Character tempChar = new Character("Sponge"+i,"Bob", "Sponger", "Idiot_"+i, 100, 100, 10);
            World tempWorld = new World("Earth_"+i,"abc");
            tempChar.setWorld(tempWorld);
            list.add(tempChar);
        }
        return list;
    }
}
