package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity {
    EditText uname, pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
    }

    private void findViewsById() {
        uname = (EditText) findViewById(R.id.txtUser);
        pword = (EditText) findViewById(R.id.txtPass);
    }
    public void connectToTomcat(View view) {
        String username = uname.getText().toString();
        String password = pword.getText().toString();

        // Post request form data
        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        // no user is logged in, so we must connect to the server

        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        /*
        // 10.0.2.2 is the host machine when running the android emulator
        final StringRequest afterLoginRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/7/AndroidLoginServlet",
                new Response.Listener<String>() {
                   // https://10.0.2.2:8443/project4-login-example/api/username => aws ipv4 address in 8443 port + /project4-login-example/api/username
                    @Override
                    public void onResponse(String response) {
                        System.out.println("afterLoginRequest onResponse function");
                        Log.d("response2", response);
                        ((TextView) findViewById(R.id.http_response)).setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        );*/

        //final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://ec2-52-14-179-229.us-east-2.compute.amazonaws.com:8443/a27/api/android-login",
        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:8080/a13/api/android-login",
                new Response.Listener<String>() {
                    String s = null;
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        String message = null;

                        s = response;
                        if (s.contains("success")) {
                            message = "Login Success! Redirecting...";
                            ((TextView) findViewById(R.id.http_response)).setText(message);
                            Intent goToIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(goToIntent);
                        }
                        else {
                            message = "Login Fail! Refreshing...";
                            ((TextView) findViewById(R.id.http_response)).setText(message);
                            finish();
                            startActivity(getIntent());
                        }
                        // Add the request to the RequestQueue.
                        //queue.add(afterLoginRequest);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }  // HTTP POST Form Data
        };
        queue.add(loginRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_red, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    public void goToGreen(View view) {
        String msg = ((EditText) findViewById(R.id.red_2_green_message)).getText().toString();

        Intent goToIntent = new Intent(this, GreenActivity.class);

        goToIntent.putExtra("last_activity", "red");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }*/
}
