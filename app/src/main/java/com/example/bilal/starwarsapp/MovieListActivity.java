package com.example.bilal.starwarsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bilal.starwarsapp.Database.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity {

    private static final String END_POINT = "https://api.themoviedb.org/3/discover/movie?api_key=7256d5365386f9e30fcc95f314282f9a&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    private static final String TAG = "MovieListActivity";
    private List<FilmInfo> filmInfoList = new ArrayList<>();

    private RequestQueue mRequestQue;
    private AppDatabase db;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private JSONObject jsonObject;
    private FilmInfo films;
    private ViewPager mViewPager;
    private boolean favoScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), filmInfoList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        db = AppDatabase.getInstance(getApplicationContext());

        switch (getIntent().getAction()) {
            case Intent.ACTION_MAIN:
                favoScreen = false;
                mSectionsPagerAdapter.setFavoScreen(favoScreen);
                apiOut();
                break;
            case Intent.ACTION_OPEN_DOCUMENT:
                favoScreen = true;
                mSectionsPagerAdapter.setFavoScreen(favoScreen);
                getFavo();
                break;
        }
    }

    public void getFavo() {
        filmInfoList.addAll(db.filmInfo().getAllFilm());
        Log.e(TAG, "getFavo: " + filmInfoList.size());
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    private void apiOut() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, END_POINT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonObject = new JSONObject(response);
                    addToList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQue = Volley.newRequestQueue(this);
        mRequestQue.add(stringRequest);
    }

    private void addToList() {
        try {

            JSONArray array = jsonObject.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                films = new FilmInfo(object.getString("vote_average"),
                        object.getString("title"),
                        "http://image.tmdb.org/t/p/w185/" + object.getString("poster_path"),
                        object.getString("overview"));
                filmInfoList.add(films);
            }
            mSectionsPagerAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static class PlaceholderFragment extends Fragment {

        @BindView(R.id.db_film_naam)
        TextView fileName;

        @BindView(R.id.db_rating)
        TextView rating;

        @BindView(R.id.filmPicture)
        ImageView pic;

        @BindView(R.id.db_overview)
        TextView overview;

        @BindView(R.id.btn_addFavo)
        FloatingActionButton btnFavo;

        private AppDatabase db;

        public PlaceholderFragment() {

        }

        public static MovieListActivity.PlaceholderFragment newInstance(String title, String rating, String imgUrl, String overview, boolean favoScreen) {
            MovieListActivity.PlaceholderFragment fragment = new MovieListActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("rating", rating);
            args.putString("img", imgUrl);
            args.putString("overview", overview);
            args.putBoolean("favoScreen", favoScreen);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
            ButterKnife.bind(this, rootView);
            db = AppDatabase.getInstance(getContext());

            final String ratingString = getArguments().getString("rating");
            final String filmNameString = getArguments().getString("title");
            final String filmPictureString = getArguments().getString("img");
            final String overviewString = getArguments().getString("overview");
            final boolean favoScreen = getArguments().getBoolean("favoScreen");

            if (favoScreen) {
                btnFavo.setVisibility(View.INVISIBLE);
            }

            fileName.setText(filmNameString);
            rating.setText(ratingString);
            //textRecipe.setText(getArguments().getString("title"));
            Glide.with(this).load(filmPictureString).into(pic);
            overview.setText(overviewString);

            btnFavo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: " + db.filmInfo().getAllFilm().size());
                    FilmInfo filmInfo = new FilmInfo(ratingString, filmNameString, filmPictureString, overviewString);
                    db.filmInfo().insert(filmInfo);
                    Toast.makeText(getContext(), "Opgeslagen!", Toast.LENGTH_SHORT).show();

                }
            });

            return rootView;
        }
    }
}

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private FilmInfo f;
    private List<FilmInfo> filmInfoList;
    private boolean favoScreen;

    public SectionsPagerAdapter(FragmentManager fm, List<FilmInfo> filmInfoList) {
        super(fm);
        this.filmInfoList = filmInfoList;
    }

    @Override
    public Fragment getItem(int position) {
        f = filmInfoList.get(position);
        return MovieListActivity.PlaceholderFragment.newInstance(f.getFilmNaam(), f.getRatingNumber(), f.getFilmPicture(), f.getOverview(), favoScreen);
    }

    public void setFavoScreen(boolean favoScreen) {
        this.favoScreen = favoScreen;
    }

    @Override
    public int getCount() {
        // Show total pages.
        return filmInfoList.size();
    }
}



