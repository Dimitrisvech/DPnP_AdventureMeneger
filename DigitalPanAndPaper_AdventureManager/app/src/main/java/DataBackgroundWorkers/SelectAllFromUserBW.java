package DataBackgroundWorkers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager.CharChooserActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.EnumMap;

import Data.Character;
import Data.Domain;
import Data.User;
import Data.DataAgent;

/**
 * Created by Dimas on 22-May-16.
 */
public class SelectAllFromUserBW extends AsyncTask<String,Void,String> {
    Context context;
    ProgressDialog pDialog;


    String uid;





    public SelectAllFromUserBW(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {


        uid = params[0];


        String link;
        String data;
        String line;
        BufferedReader bufferedReader;
        String result="";

        try {
            data = "?uid=" + URLEncoder.encode(uid, "UTF-8");

            link = Domain.select_all_characters_url + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((line = bufferedReader.readLine())!= null) {
                result += line + "\n";
            }

            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Connecting...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {

//        if (result != null) {
//            try {
//                JSONObject jsonObj = new JSONObject(result);
//                int success = jsonObj.getInt("success");
//                if (success==1) {
//                    //TODO success
//                    JSONArray jsonCharactersArray = jsonObj.getJSONArray("characters");
//                    int arrayLength = jsonCharactersArray.length();
//                    for (int i = 0; i < arrayLength; i++)
//                    {
//                        JSONObject jsonUser = jsonCharactersArray.getJSONObject(i);
//                        EnumMap<Domain.Stat,Integer> stats = new EnumMap<>(Domain.Stat.class);
//                        stats.put(Domain.Stat.STR,jsonUser.getInt("cStr"));
//                        stats.put(Domain.Stat.DEX,jsonUser.getInt("cDex"));
//                        stats.put(Domain.Stat.CON,jsonUser.getInt("cCon"));
//                        stats.put(Domain.Stat.INT,jsonUser.getInt("cInt"));
//                        stats.put(Domain.Stat.WIS,jsonUser.getInt("cWis"));
//                        stats.put(Domain.Stat.CHA,jsonUser.getInt("cCha"));
//
//                        Character ch = new Character(jsonUser.getInt("cid"),jsonUser.getInt("uid"),jsonUser.getString("cName"),jsonUser.getString("cSurName"),
//                                jsonUser.getString("cRace"),jsonUser.getString("cOccupation"),jsonUser.getInt("cMaxHealth"),jsonUser.getInt("cMaxMana"),jsonUser.getInt("cAC"),stats);
//                        DataAgent.list.add(ch);
//                    }
//                    DataAgent.isListUpdated = true;
//
//                } else if (success==0) {
//                    //TODO Failed
//                    Toast.makeText(context, "No characters found...", Toast.LENGTH_LONG).show();
//
//                } else {
//                    //TODO Error
//                    Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                DataAgent.isListUpdated = true;
//                Toast.makeText(context, "Connection Error. \n"+result, Toast.LENGTH_LONG).show();
//
//            }
//
//        } else {
//            DataAgent.isListUpdated = true;
//            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
//
//        }
//        DataAgent.isListUpdated = true;
        pDialog.dismiss();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
