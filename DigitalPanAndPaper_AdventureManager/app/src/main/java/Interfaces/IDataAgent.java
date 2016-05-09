package Interfaces;


import java.util.ArrayList;

import Data.Character;

/**
 * Created by Dimas on 04-May-16.
 */
public interface IDataAgent {
    /**
     * Gets a character by her name
      * @param name Characters name
     * @return Character model
     */
    Character getCharByName(String name);

    /**
     * Gets all characters from the user
     * @param username The username
     * @return Arraylist of character models
     */
    ArrayList<Character> getAllCharsByUser(String username);

    /**
     * Deletes character from a user
     * @param username The username
     * @param charName The characters name
     */
    void deleteCharFromUser(String username,String charName);

    /**
     * Insert or update character of a user
     * @param username The username
     * @param character The character to insert or update
     */
    void updateOrInsertCharOfUser(String username,Character character);
}
