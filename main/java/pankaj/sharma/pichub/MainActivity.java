package pankaj.sharma.pichub;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Spinner mSpinner;
    EditText mEdittext;
    Button mButton;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter recyleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String urls, jsonData;

    ArrayList<DataModels> dataModelsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = findViewById(R.id.spinner);
        mEdittext = findViewById(R.id.editText);
        mButton = findViewById(R.id.button);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.data, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mRecyclerView = findViewById(R.id.recycle);
        mRecyclerView.setHasFixedSize(true);
        int numberOfColumns = 2;
        mLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dataModelsArrayList = new ArrayList<>();
        urls =  "https://pixabay.com/api/?key=<Enter your pixabay.com api key>";
        new AsynkTaskImage().execute(urls);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
               // mRecyclerView.getRecycledViewPool().clear();
                String type = mSpinner.getSelectedItem().toString().trim();
                String searchValue = mEdittext.getText().toString().trim();

                String finalVal = searchValue.trim().replaceAll(" ","\\s+");
                urls =  "https://pixabay.com/api/?key=<Enter your pixabay.com api key>&q="+finalVal+"&image_type="+type;
                new AsynkTaskImage().execute(urls);
            }
        });
    }

    //Check Internet Is Connected Or Not
    protected boolean anIConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null &&activeNetworkInfo.isConnected();
    }

    protected class AsynkTaskImage extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urls).build();
                //System.out.println("pankaj sharma "+urls);
                Response responses = null;
                try {
                    responses = client.newCall(request).execute();
                } catch (IOException e) {
                    Log.d("tag","Ex 1 : 12345678 : "+e);
                }
                if(responses!=null)
                    jsonData = responses.body().string();
            }
            catch(IOException e)
            {
                Log.d("tag","Ex 2 : 12345678 : "+e);
            }

            return jsonData;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            loadImages(s);
            //System.out.println("pankaj sharma "+jsonData);
        }

    }
    private void loadImages(String imgUrl)
    {
        try
        {

            JSONObject jsonObject = new JSONObject(imgUrl);
            System.out.println("pankaj sharma  1111"+jsonObject);

            JSONArray hints = jsonObject.getJSONArray("hits");

            for (int i = 0; i < hints.length(); i++)
            {
                JSONObject data = hints.getJSONObject(i);

                String largeImageURL = data.getString("largeImageURL");
                int likes = data.getInt("likes");
                long id = data.getLong("id");
                long views = data.getLong("views");
                String webformatURL = data.getString("webformatURL");
                String tags = data.getString("tags");
                long downloads = data.getLong("downloads");
                String previewURL = data.getString("previewURL");
                System.out.println(i+"dzzzzzzzzzz"+largeImageURL+""+likes+""+id+""+views+""+webformatURL+""+tags+""+downloads+""+previewURL);

                dataModelsArrayList.add(new DataModels(largeImageURL, likes, id, views, webformatURL, tags, downloads, previewURL));
            }
            recyleAdapter = new MyAdapter(MainActivity.this, dataModelsArrayList);
            recyleAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(recyleAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            //mRecyclerView.setItemViewCacheSize();
        }
        catch (Exception e)
        {
            Log.d("tag","Ex 3 : 12345678 : "+e);
        }

    }
}
