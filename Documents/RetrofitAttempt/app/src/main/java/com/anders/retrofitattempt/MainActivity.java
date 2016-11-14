package com.anders.retrofitattempt;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {


    Gson mPhotosGson;
    private static final String TAG = "MainActivity";
    private static OkHttpClient httpClient;
    String BASE_URL = "https://api.flickr.com/";
    String STACK_OVERFLOW_URL = "https://api.stackexchange.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlickrPhotos();
                getStackInfo();
            }
        });
    }

    public void getFlickrPhotos() {
        PhotoInterface api;
        Map<String, String> photoMap = new HashMap<>();
        photoMap.put("method", "flickr.photos.search");
        photoMap.put("api_key", "b8debd39e5a9a66be95ddbf1eefb7abc");
        photoMap.put("format", "json");
        photoMap.put("tags", "Cork");
        photoMap.put("text", "Ireland");

        httpClient = new OkHttpClient();
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                SINCE THE RESULT WILL FIRST BE A STRING AND NOT A JSON OBJECT WE NEED THE SCALAR
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient).build();
        api = builder.create(PhotoInterface.class);

//        USE THIS FOR ONLY QUERIES NOT QUERYMAP
//        Call<String> call = api.getPhotosQuery(
//                "b8debd39e5a9a66be95ddbf1eefb7abc",
//                "Cork",
//                "Ireland",
//                "json");

//        USE THIS FOR QUERYMAP
        Call<String> call = api.getPhotosQueryMap(photoMap);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
/*              The result given has some extra string elements wrapping the JSON so it
                needs to be converted into a Json Object. */
                String result = response.body().substring(14, response.body().length() - 1);
                try {
                    JSONObject json = new JSONObject(result);

                    mPhotosGson = new Gson();
                    PhotosPojo photoList = mPhotosGson.fromJson(String.valueOf(json), PhotosPojo.class);
//                  Get the photos from the list
                    for (int j = 0; j < photoList.getPhotos().getPhoto().size() && j < 10; j++) {
                        String id = photoList.getPhotos().getPhoto().get(j).getId();
                        String secret = photoList.getPhotos().getPhoto().get(j).getSecret();
                        String farm = photoList.getPhotos().getPhoto().get(j).getFarm();
                        String server = photoList.getPhotos().getPhoto().get(j).getServer();
                        String photo = "https://farm" +
                                farm +
                                ".staticflickr.com/" +
                                server + "/" +
                                id + "_" +
                                secret + ".jpg";
                        Log.i(TAG, "onResponse: is there a photo here? " + photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getStackInfo() {
        PhotoInterface api;

        httpClient = new OkHttpClient();
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(STACK_OVERFLOW_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = builder.create(PhotoInterface.class);
        Call<StackOverflowQuestions> call = api.loadQuestions("desc", "creation", "stackoverflow", "android");

        call.enqueue(new Callback<StackOverflowQuestions>() {
            @Override
            public void onResponse(Call<StackOverflowQuestions> call, Response<StackOverflowQuestions> response) {
                if (response.body() != null) {
                    Log.i(TAG, "onResponse: body = " + response.body());
                    String fullArray = "";
                    if (response.body().items.get(0) != null) {
                        for (int i = 0; i < response.body().items.size(); i++) {
                            Log.i(TAG, "get(" + i + ").getOwner().getUser_id() = " + response.body().items.get(i).getOwner().getUser_id());
                            Log.i(TAG, "items.get(" + i + ").getTitle() = " + response.body().items.get(i).getTitle());
                            for (int j = 0; j < response.body().items.get(i).getTags().size(); j++) {
                                if (j == 0) {
                                    fullArray += (response.body().items.get(i).getTags().get(j));
                                } else {
                                    fullArray += (", " + response.body().items.get(i).getTags().get(j));
                                }
                            }
                            Log.i(TAG, "onResponse: full tag array -> " + fullArray);
                            Log.i(TAG, " -------------------------------------------------------------------- ");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StackOverflowQuestions> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    interface PhotoInterface {
//        FLICKR
        @GET("services/rest")
        Call<String> getPhotosQueryMap(
                @QueryMap Map<String, String> options
        );


        @GET("services/rest/?method=flickr.photos.search")
        Call<String> getPhotosQuery(
                @Query("api_key") String api_key,
                @Query("tags") String tags,
                @Query("text") String text,
                @Query("format") String format
        );

//        STACKOVERFLOW
        @GET("/2.2/questions?order=desc")
        Call<StackOverflowQuestions> loadQuestions(
                @Query("order") String order,
                @Query("sort") String sorted,
                @Query("site") String orderType,
                @Query("tagged") String tags);
    }

//       ===========================================================================

//          DISREGARD BELOW

//       ===========================================================================


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
