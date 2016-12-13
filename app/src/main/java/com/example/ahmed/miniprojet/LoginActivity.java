package com.example.ahmed.miniprojet;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    TextView tx; String text,text1="";
    EditText ed1,ed2;
    Button btn;
    LoginButton login;
    private String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id;

    CallbackManager callbackManager;

    ImageView imgProfilePic;
    TextView tv_username,txtEmail;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        tx=(TextView) findViewById(R.id.tvregistre);
        ed1=( EditText) findViewById(R.id.edlogin1);
        ed2=(EditText) findViewById(R.id.edpassword1);
        login = (LoginButton)findViewById(R.id.login_button);
        login.setReadPermissions("public_profile email");
        //google//////////////////////////////////////////
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        ///////////////////////////////////////////
        getKeyHash();
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                facebook_id=f_name= m_name= l_name= gender= profile_image= full_name= email_id="";

                if(AccessToken.getCurrentAccessToken() != null){

                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    RequestData();
                    Toast.makeText(getApplicationContext(),f_name,Toast.LENGTH_SHORT).show();
                    System.out.println(f_name);
                    startActivity(i);
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        facebook_id=profile.getId();
                        Log.e("facebook_id", facebook_id);
                        f_name=profile.getFirstName();
                        Log.e("f_name", f_name);
                        m_name=profile.getMiddleName();
                        Log.e("m_name", m_name);
                        l_name=profile.getLastName();
                        Log.e("l_name", l_name);
                        full_name=profile.getName();
                        Log.e("full_name", full_name);
                        profile_image=profile.getProfilePictureUri(400, 400).toString();
                        Log.e("profile_image", profile_image);

                    }

                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void bulogin(View view) {
        String url="http://172.16.27.69/coiv/login.php/?login="+ed1.getText().toString()+"&password="+ed2.getText().toString();
        new MyAsyncTaskgetNews().execute(url);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:

                signIn();

                break;
        /*    case R.id.btn_logout:

                signOut();

                break;*/
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
        /*    tv_username.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            txtEmail.setText(getString(R.string.signed_in_fmt,email));
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);
*/
              System.out.println("email"+acct.getEmail());
            System.out.println(acct.getDisplayName());
            Intent i =new Intent(LoginActivity.this,MainActivity.class);
            Toast.makeText(getApplicationContext(),acct.getDisplayName(),Toast.LENGTH_SHORT).show();
            startActivity(i);


        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
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

            try {
                JSONObject json=new JSONObject(progress[0]);
                if (json.getString("msg").equals("true")){
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();}

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
    private void getKeyHash() {

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("navigationloginsample.android.com.loginsample", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();

                try {
                    if(json != null){
                         text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                     text1=json.getString("name");
                        System.out.println("name :"+text1);

                        f_name=text1;
                        // details_txt.setText(Html.fromHtml(text));
                      //  profile.setProfileId(json.getString("id"  System.out.println("Json data :"+json);));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == RC_SIGN_IN) {
          GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
          handleSignInResult(result);
      }else
          callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
