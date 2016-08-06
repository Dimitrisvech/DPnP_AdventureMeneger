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

import DataBackgroundWorkers.DeleteCharacterBW;
import DataBackgroundWorkers.InsertCharacterBW;
import DataBackgroundWorkers.InsertItemBW;
import DataBackgroundWorkers.SelectAllCharactersFromUserBW;
import DataBackgroundWorkers.SelectCharacterBW;
import DataBackgroundWorkers.UpdateCharacterBW;
import DataBackgroundWorkers.*;
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
    public static ArrayList<InventoryItem> itemlist;
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
        SelectAllCharactersFromUserBW sbw = new SelectAllCharactersFromUserBW(_context);
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
                    Toast.makeText(_context, "Connection Error.", Toast.LENGTH_LONG).show();
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
    public boolean deleteCharFromUser(int charId) {

        DeleteCharacterBW dbw = new DeleteCharacterBW(_context);
        try {

            String result=dbw.execute(charId+"").get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        return true;
                    } else if (success==0) {
                        Toast.makeText(_context, "No characters found...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(_context, "Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Connection Error.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateOrInsertCharOfUser(Character character) {
        list = new ArrayList<>();
        SelectCharacterBW sbw = new SelectCharacterBW(_context);
        try {
            String selectResult=sbw.execute(character.getCid()+"").get();
            if (selectResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(selectResult);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        //update
                        return updateCharacter(character);
                    } else if (success==0) {
                        //Insert
                        return insertCharacter(character);
                    } else {
                        //Error
                        Toast.makeText(_context, "Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Connection Error.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setLocalTemporaryCharacter(Character character) {
        this._tempChar = character;
    }

    @Override
    public Character getLocalTemporaryCharacter() {
        return this._tempChar;
    }

    private boolean updateCharacter(Character character){
        UpdateCharacterBW ubw = new UpdateCharacterBW(_context);
        try{
            String updateResult=ubw.execute(character.getCid()+"",character.getName(),character.getSurName(),character.getRace(),character.getOccupation(),
                    character.getMaxHealth()+"",character.getMaxMana()+"",character.getAC()+"",character.getStat(Domain.Stat.STR)+"",
                    character.getStat(Domain.Stat.DEX)+"",character.getStat(Domain.Stat.CON)+"",character.getStat(Domain.Stat.WIS)+"",
                    character.getStat(Domain.Stat.INT)+"",character.getStat(Domain.Stat.CHA)+"").get();
            if (updateResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(updateResult);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        return true;
                    } else if (success==0) {
                        Toast.makeText(_context, "Failed to update...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(_context, "Update: Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Update: Connection Error.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean insertCharacter(Character character){
        InsertCharacterBW ibw = new InsertCharacterBW(_context);
        try{
            String insertResult=ibw.execute(character.getUid()+"",character.getCid()+"",character.getName(),character.getSurName(),character.getRace(),
                    character.getOccupation(),character.getMaxHealth()+"",character.getMaxMana()+"",character.getAC()+"",character.getStat(Domain.Stat.STR)+"",
                    character.getStat(Domain.Stat.DEX)+"",character.getStat(Domain.Stat.CON)+"",character.getStat(Domain.Stat.WIS)+"",
                    character.getStat(Domain.Stat.INT)+"",character.getStat(Domain.Stat.CHA)+"").get();
            if (insertResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(insertResult);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        return true;
                    } else if (success==0) {
                        Toast.makeText(_context, "Failed to insert...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(_context, "Insert: Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Insert: Connection Error.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertItem(InventoryItem item){
        InsertItemBW ibw = new InsertItemBW(_context);
        try{
            String insertResult=ibw.execute(item.getCid()+"",item.getArmorClass()+"",item.getEquippedSpot(),item.getHitDie()+"",item.getId()+"",
                    item.getName(),item.getType()).get();
            if (insertResult != null) {
                try {
                    JSONObject jsonObj = new JSONObject(insertResult);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        return true;
                    } else if (success==0) {
                        Toast.makeText(_context, "Failed to insert...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(_context, "Insert: Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Insert: Connection Error.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteItemFromChar(int itemID) {

        DeleteItemBW dbw = new DeleteItemBW(_context);
        try {
            String result=dbw.execute(itemID+"").get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        return true;
                    } else if (success==0) {
                        Toast.makeText(_context, "No items found...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(_context, "Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Connection Error.", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<InventoryItem> getAllItemsByChar(int charID) {
        itemlist = new ArrayList<>();
        SelectAllItemsFromCharacterBW sbw = new SelectAllItemsFromCharacterBW(_context);
        try {
            String result=sbw.execute(charID+"").get();
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    int success = jsonObj.getInt("success");
                    if (success==1) {
                        //TODO success
                        JSONArray jsonItemsArray = jsonObj.getJSONArray("inventory");
                        int arrayLength = jsonItemsArray.length();
                        for (int i = 0; i < arrayLength; i++)
                        {
                            JSONObject jsonItem = jsonItemsArray.getJSONObject(i);
                            InventoryItem im = new InventoryItem(jsonItem.getInt("cid"),jsonItem.getInt("iAC"),jsonItem.getString("iEquippedSpot"),
                                    jsonItem.getInt("iHitDie"),jsonItem.getInt("iid"),jsonItem.getString("iName"),jsonItem.getString("iType"));
                            itemlist.add(im);
                        }
                    } else if (success==0) {
                        //TODO Failed
                        Toast.makeText(_context, "No items found...", Toast.LENGTH_LONG).show();
                    } else {
                        //TODO Error
                        Toast.makeText(_context, "Error.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(_context, "Connection Error.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(_context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Collections.sort(itemlist,InventoryItem.itemNameComparator);
        return itemlist;
    }

}
