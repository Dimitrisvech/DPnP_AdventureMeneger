package Interfaces;


import java.util.ArrayList;

import Data.Character;

/**
 * Created by Dimas on 04-May-16.
 */
public interface IDataAgent {
    /**
     * Gets a character by her name
      * @param name characters name
     * @return Character model
     */
    Character getCharByName(String name);

    /**
     * Gets all characters from the user
     * @param username the username
     * @return Arraylist of character models
     */
    ArrayList<Character> getAllCharsByUser(String username);
}
