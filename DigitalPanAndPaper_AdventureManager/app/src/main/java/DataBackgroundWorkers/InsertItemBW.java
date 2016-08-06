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
 * Created by Arik on 04-Aug-16.
 */
public class InsertItemBW extends AsyncTask<String,Void,String> {
    Context context;
    ProgressDialog pDialog;

    public InsertItemBW(Context ctx){this.context = ctx;}

    @Override
    protected String doInBackground(String... params) {


        String link;
        String data;
        String line;
        BufferedReader bufferedReader;
        String result="";

        try {
            data = "&cid=" + URLEncoder.encode(params[0], "UTF-8");
            data += "&iAC=" + URLEncoder.encode(params[1], "UTF-8");
            data += "&iEquippedSlot=" + URLEncoder.encode(params[2], "UTF-8");
            data += "&iHitDie=" + URLEncoder.encode(params[3], "UTF-8");
            data += "&iid=" + URLEncoder.encode(params[4], "UTF-8");
            data += "&iName=" + URLEncoder.encode(params[5], "UTF-8");
            data += "&iType=" + URLEncoder.encode(params[6], "UTF-8");

            link = Domain.insert_item_url + data;
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
