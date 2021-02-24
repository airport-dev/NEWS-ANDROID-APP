package com.example.st11_original_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final String API_KEY = "YOUR APIKEY!!";
    final String BASE_URL="https://newsapi.org/v2/";
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //パーツ取得
        listView = (ListView)findViewById(R.id.sample_listview);

        //ローディング画面表示
        findViewById(R.id.loadingitem).setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        NeapService neapService = retrofit.create(NeapService.class);

        Call<Headlines> call = neapService.getHeadlines("jp","business", API_KEY);

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {

                if(!response.isSuccessful()){

                    //ローディング画面非表示
                    findViewById(R.id.loadingitem).setVisibility(View.GONE);
                    Log.d("TAG","Code: " + response.code());
                    //エラー画面に遷移する
                    Intent intent = new Intent(getApplication(),ErrorActivity.class);
                    intent.putExtra("msg","接続時にエラーが発生しました。 (エラ-コード"+response.code() + ")");
                    startActivity(intent);
                    return;
                }

                List<Articles> articles = response.body().getArticles();



                //リストビューに表示する要素を設定
                if(articles.size() > 0){
                    //件数がある時
                    findViewById(R.id.loadingitem).setVisibility(View.GONE);
                    setListView(articles);
                }else{
                    //ローディング画面非表示
                    findViewById(R.id.loadingitem).setVisibility(View.GONE);
                    Log.d("TAG","error1: " + articles.size());
                    //エラー画面に遷移する
                    Intent intent = new Intent(getApplication(),ErrorActivity.class);
                    intent.putExtra("msg","記事が取得できませんでした。もう一度読み込み直してください");
                    startActivity(intent);
                    return;
                }


            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                //ローディング画面非表示
                findViewById(R.id.loadingitem).setVisibility(View.GONE);
                Log.d("TAG","error2: " + t.getMessage());
                //エラー画面に遷移する
                Intent intent = new Intent(getApplication(),ErrorActivity.class);
                intent.putExtra("msg","接続時にエラーが発生しました。 (エラー内容:"+ t.getMessage() + ")");
                startActivity(intent);
                return;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = ((TextView)view.findViewById(R.id.url)).getText().toString();
                Intent intent = new Intent(getApplication(),WebNews.class);
                intent.putExtra("url",url);
                startActivity(intent);

            }
        });


    }

    public void setListView(List<Articles> articles){
        BaseAdapter adapter = new TestAdapter(this.getApplicationContext(),R.layout.list_item, articles);
        listView.setAdapter(adapter);
    }

    //トースト作成func
    private void toastMake(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        // 位置調整
        toast.setGravity(Gravity.CENTER, x, y);
        toast.show();
    }

    //リターン時の更新処理
    @Override
    protected void onRestart(){
        super.onRestart();
        reload();
    }

    //リロード
    public void reload(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }

}