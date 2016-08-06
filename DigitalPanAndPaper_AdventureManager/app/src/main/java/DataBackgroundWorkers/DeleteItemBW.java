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
public class DeleteItemBW extends AsyncTask<String,Void,String> {


    Context context;
    ProgressDialog pDialog;

    String iid;

    public DeleteItemBW(Context ctx){this.context = ctx;}

    @Override
    protected String doInBackground(String... params) {
        iid = params[0]; // Or 4? I'm not sure how this works. Assuming it gets execute(input).

        String link;
        String data;
        String line;
        BufferedReader bufferedReader;
        String result="";

        try {
            data = "?iid=" + URLEncoder.encode(iid, "UTF-8");

            link = Domain.delete_item_url + data;
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
