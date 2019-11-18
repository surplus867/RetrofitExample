package com.example.android.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "caa7bbb8acf08fcdc2b3f26cb3219b89";
    public static String LANGUAGE = "en-us";
    public static String CATEGORY = "popular";

    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = (TextView) findViewById(R.id.my_tv);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface myInterface = retrofit.create (ApiInterface.class);

       Call<MovieResults> call = myInterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);

       call.enqueue(new Callback<MovieResults>() {
           @Override
           public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
              MovieResults results =  response.body();
               List<MovieResults.Result> listOfMovies = results.getResults();
               MovieResults.Result firstMovie = listOfMovies.get(0);

               myTextView.setText(firstMovie.getTitle());
           }

           @Override
           public void onFailure(Call<MovieResults> call, Throwable t) {

           }
       });


    }
}
