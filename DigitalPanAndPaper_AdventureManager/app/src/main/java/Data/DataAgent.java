package Data;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.concurrent.ExecutionException;

import DataBackgroundWorkers.SelectAllFromUserBW;
import Interfaces.IDataAgent;

/**
 * Created by Dimas on 22-May-16.
 */
public class DataAgent implements IDataAgent {

    private static DataAgent ourInstance = new DataAgent();

    public static DataAgent getInstance(Context context) {
        _context = context;
        return ourInstance;
    }

    private static Context _context;
    public static ArrayList<Character> list;
    public static boolean isListUpdated;
    private Character _tempChar;

    private DataAgent(){
        list = new ArrayList<>();
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
    public ArrayList<Character> getAllCharsByUser() {
        list = new ArrayList<>();
        SelectAllFromUserBW sbw = new SelectAllFromUserBW(_context);
        try {
            String result=sbw.execute(Domain.getUser().getUid()+"").get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        //TODO success
                        JSONArray jsonCharactersArray = jsonObj.getJSONArray("characters");
                        int arrayLength = jsonCharactersArray.length();
                        for (int i = 0; i < arrayLength; i++)
                        {
                            JSONObject jsonChar = jsonCharactersArray.getJSONObject(i);
                            EnumMap<Domain.Stat,Integer> stats = new EnumMap<>(Domain.Stat.class);
                            stats.put(Domain.Stat.STR,jsonChar.getInt("cStr"));
                            stats.put(Domain.Stat.DEX,jsonChar.getInt("cDex"));
                            stats.put(Domain.Stat.CON,jsonChar.getInt("cCon"));
                            stats.put(Domain.Stat.INT,jsonChar.getInt("cInt"));
                            stats.put(Domain.Stat.WIS,jsonChar.getInt("cWis"));
                            stats.put(Domain.Stat.CHA,jsonChar.getInt("cCha"));

                            Character ch = new Character(jsonChar.getInt("cid"),jsonChar.getInt("uid"),jsonChar.getString("cName"),jsonChar.getString("cSurName"),
                                    jsonChar.getString("cRace"),jsonChar.getString("cOccupation"),jsonChar.getInt("cMaxHealth"),jsonChar.getInt("cMaxMana"),jsonChar.getInt("cAC"),stats);
                            list.add(ch);
                        }


                    } else if (success==0) {
                        //TODO Failed
                        Toast.makeText(_context, "No characters found...", Toast.LENGTH_LONG).show();

                    } else {
                        //TODO Error
                        Toast.makeText(_context, "Error.", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Connection Error. \n"+result, Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Collections.sort(list,Character.CharNameComparator);
        return list;
    }

    @Override
    public void deleteCharFromUser(String username, String charName) {

    }

    @Override
    public void updateOrInsertCharOfUser(String username, Character character) {

    }



    @Override
    public void setLocalTemporaryCharacter(Character character) {
        this._tempChar = character;
    }

    @Override
    public Character getLocalTemporaryCharacter() {
        return this._tempChar;
    }
}
