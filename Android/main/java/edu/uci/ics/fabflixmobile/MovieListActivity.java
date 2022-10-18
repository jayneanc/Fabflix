package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;
import android.graphics.Typeface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MovieListActivity extends ActionBarActivity {
    ArrayList<String> movieId1 = new ArrayList<String>();
    ArrayList<Movie> movieInfo = new ArrayList<Movie>();
    ArrayList<Movie> partMovie;

    public static int pageNum = 0;
    public static int result;

    private ListView listview;
    private TextView page;
    private Button btn_prev;
    private Button btn_next;
    private int pageCount ;
    ArrayAdapter<Movie> adapter;
    ListView listView;
    List<Movie> movieProperties;
    private Context content;


    /**
     * Using this increment value we can move the listview items
     */
    private int increment = 0;

    /**
     * Here set the values, how the ListView to be display
     *
     * Be sure that you must set like this
     *
     * TOTAL_LIST_ITEMS > NUM_ITEMS_PAGE
     */

    public int TOTAL_LIST_ITEMS;
    public int NUM_ITEMS_PAGE   = 10;

    public MovieListActivity(){}

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);

        // getting query results from MainActivity
        MainActivity m = new MainActivity();
        movieId1 = m.getMovieId();
        TOTAL_LIST_ITEMS = movieId1.size();

        btn_prev = (Button)findViewById(R.id.prev);
        btn_next = (Button)findViewById(R.id.next);
        btn_prev.setEnabled(false);
        page = (TextView)findViewById(R.id.page);


        /**
         * this block is for checking the number of pages
         * ====================================================
         */

        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        pageCount = TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
        /**
         * =====================================================
         */

        page.setText("Page "+(increment+1)+" of "+pageCount);
        if(increment == 0 && pageCount == 1){
            btn_next.setEnabled(false);
        }

        /**
         * The ArrayList data contains all the list items
         */
        for(int i=0;i<TOTAL_LIST_ITEMS;i++)
        {
            // parse necessary info out from each string gotton from jsonArray
            // then add to the data arrayList
            try {
                String data = movieId1.get(i);
                JSONObject dat = new JSONObject(data);
                String title = dat.getString("title");
                String year = dat.getString("year");
                String director = dat.getString("director");
                String genre = dat.getString("genre");
                String star = dat.getString("star");
                String rating = dat.getString("rating");
                movieInfo.add(new Movie(title,year,director,genre,star,rating));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        partMovie = new ArrayList<Movie>();
        if(movieInfo.size() < 10) {
            for(int j = increment * 10; j < movieInfo.size(); j++) {
                partMovie.add(movieInfo.get(j));
            }
        }
        else {
            int max = increment * 10 + 10;
            for(int j = increment * 10; j < max; j++) {
                partMovie.add(movieInfo.get(j));
            }
        }

        //create our new array adapter
        adapter = new propertyArrayAdapter(this, 0, partMovie);

        //Find list view and bind it with the custom adapter
        listView = (ListView) findViewById(R.id.customListView);
        listView.setAdapter(adapter);
        listView.setClickable(true);


        btn_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                increment++;
                partMovie = new ArrayList<Movie>();
                int max = increment * 10 + 10;
                if(movieInfo.size() >= max) {
                    for(int j = increment * 10; j < max; j++) {
                        partMovie.add(movieInfo.get(j));
                    }
                }
                else {
                    max = movieInfo.size();
                    for(int j = increment * 10; j < max; j++) {
                        partMovie.add(movieInfo.get(j));
                    }
                }
                adapter = new propertyArrayAdapter(content, 0, partMovie);
                listView.setAdapter(adapter);
                page.setText("Page "+(increment+1)+" of "+pageCount);
                CheckEnable();
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                increment--;
                partMovie = new ArrayList<Movie>();
                int max = increment * 10 + 10;
                if(movieInfo.size() >= max) {
                    for(int j = increment * 10; j < max; j++) {
                        partMovie.add(movieInfo.get(j));
                    }
                }
                else {
                    max = movieInfo.size();
                    for(int j = increment * 10; j < max; j++) {
                        partMovie.add(movieInfo.get(j));
                    }
                }
                adapter = new propertyArrayAdapter(content, 0, partMovie);
                listView.setAdapter(adapter);
                page.setText("Page "+(increment+1)+" of "+pageCount);
                CheckEnable();
            }
        });
    }

    /**
    * Method for enabling and disabling Buttons
    */
    private void CheckEnable()
    {
        if(increment+1 == pageCount)
        {
           btn_next.setEnabled(false);
           btn_prev.setEnabled(true);
        }
        else if(increment == 0)
        {
            btn_prev.setEnabled(false);
            btn_next.setEnabled(true);
        }
        else
        {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }


    //custom ArrayAdapater
    class propertyArrayAdapter extends ArrayAdapter<Movie>{

        //constructor, call on creation
        public propertyArrayAdapter(Context context, int resource, ArrayList<Movie> objects) {
            super(context, resource, objects);
            content = context;
            movieProperties = objects;
        }

        //called when rendering the list
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) content.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            //conditionally inflate either standard or special template
            View view;
            view = inflater.inflate(R.layout.property_layout_alt, null);

            //get the property we are displaying
            Movie movie = movieProperties.get(position);

            TextView title = (TextView) view.findViewById(R.id.title);
            TextView year = (TextView) view.findViewById(R.id.year1);
            TextView director = (TextView) view.findViewById(R.id.director1);
            TextView genre = (TextView) view.findViewById(R.id.genre1);
            TextView star = (TextView) view.findViewById(R.id.star1);
            TextView rating = (TextView) view.findViewById(R.id.rating1);

            title.setText(movie.getTitle());
            year.setText(movie.getYear());
            year.setTextColor(Color.rgb(153,217,234));
            year.setTypeface(null,Typeface.BOLD);
            director.setText(movie.getDirector());
            director.setTextColor(Color.rgb(153,217,234));
            director.setTypeface(null,Typeface.BOLD);
            genre.setText(movie.getGenre());
            genre.setTextColor(Color.rgb(153,217,234));
            genre.setTypeface(null,Typeface.BOLD);
            star.setText(movie.getStar());
            star.setTextColor(Color.rgb(153,217,234));
            star.setTypeface(null,Typeface.BOLD);
            rating.setText(movie.getRating());
            rating.setTextColor(Color.rgb(153,217,234));
            rating.setTypeface(null,Typeface.BOLD);

            return view;
        }
    }


}