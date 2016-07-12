package Interfaces;


import java.util.ArrayList;

import Data.Character;

/**
 * Created by Dimas on 04-May-16.
 * Defines the methods of the DataAgent.
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
     * @return Arraylist of character models
     */
    ArrayList<Character> getAllCharsByUser();

    /**
     * Deletes character from a user
     * @param charId The characters id
     * @return True if successful
     */
    boolean deleteCharFromUser(int charId);

    /**
     * Insert or update character of a user
     * @param character The character to insert or update
     */
    boolean updateOrInsertCharOfUser(Character character);

    /**
     * Saves a local temp character
     * @param character the character
     */
    void setLocalTemporaryCharacter(Character character);

    /**
     * Gets the local temp character or null if not exists
     * @return Temp character saved by setLocalTemporaryCharacter()
     */
    Character getLocalTemporaryCharacter();
}
