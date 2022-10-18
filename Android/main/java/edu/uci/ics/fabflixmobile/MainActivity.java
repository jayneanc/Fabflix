package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends ActionBarActivity {
    // Create Object of EditText and TextWatcher
    EditText userInput;
    public static ArrayList<String> movieId;

    public MainActivity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
    }

    private void findViewsById() {
        userInput = (EditText) findViewById(R.id.input);
    }


    public void Search(View view) {
        System.out.println("Search function called");
        String s = userInput.getText().toString();
        System.out.println("user input: " + s);
        String ns = "";
        if (s.contains(" ")) {
            String[] ui = s.split(" ");
            for (int i = 0; i < ui.length; i++) {
                if (i == 0) {
                    ns = ui[i];
                }
                else {
                    ns += "%20" + ui[i];
                }
            }
        }
        else {
            ns = s;
        }
        System.out.println("put into url: " + ns);
        // no user is logged in, so we must connect to the server

        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;


        //String url = "https://ec2-52-14-179-229.us-east-2.compute.amazonaws.com:8443/a27/form-android" + "?query=" + ns;
        String url = "http://10.0.2.2:8080/a13/form-android" + "?query=" + ns;
        // 10.0.2.2 is the host machine when running the android emulator
        final StringRequest afterLoginRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // https://10.0.2.2:8443/project4-login-example/api/username => aws ipv4 address in 8443 port + /project4-login-example/api/username
                    @Override
                    public void onResponse(String response) {
                        System.out.println("getting from MainServlet");
                        Log.d("response2", response);
                        String res = response;
                        try {
                            movieId = new ArrayList<String>();
                            JSONArray jsonArray = new JSONArray(res);
                            System.out.println("jsonArray: " + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dat = jsonArray.getJSONObject(i);
                                /*if (i == 0) {
                                    data += dat.getString("data");
                                }
                                else {
                                    data += "," + dat.getString("data");
                                }*/
                                movieId.add(dat.getString("data"));
                            }
                            //System.out.println(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent goToIntent = new Intent(getApplicationContext(), MovieListActivity.class);
                        startActivity(goToIntent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ){
        };
        queue.add(afterLoginRequest);
    }
    public ArrayList<String> getMovieId() {
        return movieId;
    }
    /*
    public void Search(View view) {
        String sw = searchWord.getText().toString();
        System.out.println("searchWord: "+ sw);
        final Map<String, String> params = new HashMap<String, String>();
        params.put("query", sw);

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest search = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/b3/Main",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        ((TextView) findViewById(R.id.http_response)).setText(response);
                        ArrayList<String> ar = new ArrayList<String>();


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
        queue.add(search);
    }*/

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
    /*public void goToRed(View view) {
        String msg = ((EditText) findViewById(R.id.green_2_red_message)).getText().toString();

        Intent goToIntent = new Intent(this, RedActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }

    public void goToBlue(View view) {
        String msg = ((EditText) findViewById(R.id.green_2_blue_message)).getText().toString();

        Intent goToIntent = new Intent(this, BlueActivity.class);

        goToIntent.putExtra("last_activity", "green");
        goToIntent.putExtra("message", msg);

        startActivity(goToIntent);
    }*/

}
