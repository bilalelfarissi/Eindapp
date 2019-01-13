package com.example.bilal.starwarsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bilal.starwarsapp.Database.AppDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements View.OnClickListener {
    private AppDatabase db;
    @BindView(R.id.button_start)
    Button start_button;
    @BindView(R.id.btn_getFavo)
    Button favo_button;

    private List<FilmInfo> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        start_button.setOnClickListener(this);

        db = AppDatabase.getInstance(getApplicationContext());
    }

    @OnClick({R.id.btn_getFavo})
    public void openFavo(){
        Intent naarMovieList = new Intent(this, MovieListActivity.class);
        naarMovieList.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivity(naarMovieList);
        Toast.makeText(this, "Op naar de favorieten!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                Intent naarMovieList = new Intent(this, MovieListActivity.class);
                naarMovieList.setAction(Intent.ACTION_MAIN);
                startActivity(naarMovieList);
                Toast.makeText(this, "Op naar de filmlijst!", Toast.LENGTH_SHORT).show();
        }
    }


}
