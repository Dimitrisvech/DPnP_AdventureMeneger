package DataBackgroundWorkers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import Data.Domain;

/**
 * Created by Dimas on 12-Jul-16.
 */
public class InsertCharacterBW extends AsyncTask<String,Void,String> {

    Context context;
    ProgressDialog pDialog;

    public InsertCharacterBW(Context ctx){this.context = ctx;}

    @Override
    protected String doInBackground(String... params) {


        String link;
        String data;
        String line;
        BufferedReader bufferedReader;
        String result="";

        try {
            data = "?uid=" + URLEncoder.encode(params[0], "UTF-8");
            data += "&cid=" + URLEncoder.encode(params[1], "UTF-8");
            data += "&cName=" + URLEncoder.encode(params[2], "UTF-8");
            data += "&cSurName=" + URLEncoder.encode(params[3], "UTF-8");
            data += "&cRace=" + URLEncoder.encode(params[4], "UTF-8");
            data += "&cOccupation=" + URLEncoder.encode(params[5], "UTF-8");
            data += "&cMaxHealth=" + URLEncoder.encode(params[6], "UTF-8");
            data += "&cMaxMana=" + URLEncoder.encode(params[7], "UTF-8");
            data += "&cAC=" + URLEncoder.encode(params[8], "UTF-8");
            data += "&cStr=" + URLEncoder.encode(params[9], "UTF-8");
            data += "&cDex=" + URLEncoder.encode(params[10], "UTF-8");
            data += "&cCon=" + URLEncoder.encode(params[11], "UTF-8");
            data += "&cInt=" + URLEncoder.encode(params[12], "UTF-8");
            data += "&cWis=" + URLEncoder.encode(params[13], "UTF-8");
            data += "&cCha=" + URLEncoder.encode(params[14], "UTF-8");

            link = Domain.insert_character_url + data;
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
        pDialog.dismiss();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
