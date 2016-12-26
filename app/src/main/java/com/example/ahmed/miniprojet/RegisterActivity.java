package com.example.ahmed.miniprojet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    EditText ednom,edprenom,edmail,edlogin,edphone,edpass,edpassagain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ednom=(EditText) findViewById(R.id.ednom);
        edprenom=(EditText) findViewById(R.id.edprenom);
        edmail=(EditText) findViewById(R.id.edmail);
        edlogin=(EditText) findViewById(R.id.edlogin1);
        edphone=(EditText) findViewById(R.id.edphone);
        edpass=(EditText) findViewById(R.id.edpass);
        edpassagain=(EditText) findViewById(R.id.edpassagain);
    }
    public void inscrit(View view) {
        String url="";
        String strPass1 = edpass.getText().toString();
        String strPass2 = edpassagain.getText().toString();
        if (strPass1.equals(strPass2)) {
            url ="http://anproip.esy.es/inscript.php?nom="+ednom.getText().toString()+"&prenom="+edprenom.getText().toString()+
                    "&login="+edlogin.getText().toString()+"&phone="+edphone.getText().toString()+"&password="+edpass.getText().toString();
        //"http://anproip.esy.es/inscript.php?nom=tt&prenom=yy&login=t&phone=44&password=dd

          /* Intent i= new Intent(this,ProposerTrajet.class);
            startActivity(i);*/
        } else {
           Toast.makeText(getApplicationContext(),"passwords not equals",Toast.LENGTH_SHORT).show();
        }

        new MyAsyncTaskgetNews().execute(url);

    }

    public void annulerInsc(View view) {
        Intent i= new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {
            String ch ="user is added";
            try {
                JSONObject json=new JSONObject(progress[0]);
                String msg =json.getString("msg");
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                if(ch.equals(ch)){
                    Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                    ednom.getText().clear();
                            edprenom.getText().clear();
                    edmail.getText().clear();
                    edlogin.getText().clear();

                    edphone.getText().clear();edpass.getText().clear();
                    edpassagain.getText().clear();
                    startActivity(i);
                }

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }




    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }
}


