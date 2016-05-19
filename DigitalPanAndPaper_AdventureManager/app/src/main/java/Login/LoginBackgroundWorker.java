package Login;

import android.app.AlertDialog;
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

import Data.Domain;
import Data.User;

/**
 * Created by Dimas on 18-May-16.
 */
public class LoginBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String userName;
    String password;
    ProgressDialog pDialog;



    public LoginBackgroundWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {

        final String local_login_url = "http://192.168.1.12/android_connect/get_user_by_name.php";
        final String global_login_url = "http://37.142.201.27/android_connect/get_user_by_name.php";
        userName = params[0];
        password = params[1];
        //String phoneNumber = arg0[3];
        //String emailAddress = arg0[4];

        String link;
        String data;
        String line;
        BufferedReader bufferedReader;
        String result="";

        try {
            data = "?uUserName=" + URLEncoder.encode(userName, "UTF-8");
            //data += "&username=" + URLEncoder.encode(userName, "UTF-8");
            //data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
            //data += "&phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            //data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");

            link = global_login_url + data;
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

        if (result != null) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                int success = jsonObj.getInt("success");
                if (success==1) {
                    //TODO success
                    JSONArray jsonUserArray = jsonObj.getJSONArray("user");
                    JSONObject jsonUser = jsonUserArray.getJSONObject(0);
                    User user = new User(jsonUser.getInt("uid"),jsonUser.getString("uUserName"),jsonUser.getString("uPassword"),jsonUser.getString("uMail"));

                    if(password.equals(user.getPassword())) {
                        Domain.setUser(user);
                        Intent viewChange = new Intent(context, CharChooserActivity.class);
                        context.startActivity(viewChange);
                    }
                    else
                        Toast.makeText(context, "Wrong password.", Toast.LENGTH_SHORT).show();

                } else if (success==0) {
                    //TODO Failed
                    Toast.makeText(context, "No such user found...", Toast.LENGTH_SHORT).show();

                } else {
                    //TODO Error
                    Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data\\ \nConnection Error. ", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();

        }

        pDialog.dismiss();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}