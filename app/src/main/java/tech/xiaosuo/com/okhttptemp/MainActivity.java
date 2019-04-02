package tech.xiaosuo.com.okhttptemp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postFormDataAsync();
        //getMethodAsync();
    //    new Thread(getSyncRunnable).start();
    }

     private void postMultiData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType fileType = MediaType.parse("File/*");
        File file = new File("path/name");
        RequestBody fileBody = RequestBody.create(fileType,file);
         MultipartBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("USERNAME","NICHOLAS").addFormDataPart("PASSWORD","123456")
                 .addFormDataPart("file","filename",fileBody).build();
         Request request = new Request.Builder().url("http://www.baidu.com").post(multipartBody).build();
         okHttpClient.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {

             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {

             }
         });
     }

    private void postFileData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType fileType = MediaType.parse("File/*");
        File file = new File("path/icon.jpg");
        RequestBody requestBody = RequestBody.create(fileType,file);
        Request request = new Request.Builder().url("http://www.baidu.com").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void postJsonData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType jsonType = MediaType.parse("application/json; charset = utf-8");
        String jsonStr = "{\"username\" : \"nicholas\", \"passwor\":\"123456\"}";
        RequestBody requestBody = RequestBody.create(jsonType,jsonStr);
        Request request = new Request.Builder().url("https://www.baidu.com").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
            //post form data
    private void postFormDataAsync(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("username","Nicholas");
        Request request = new Request.Builder().url("http://www.baidu.com").post(formBodyBuilder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG," post response fail: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG," post response onResponse: "  +  response.code() + " response body: " + response.body().string() + " response msg: " + response.message());
                if(response.isSuccessful()){
                    Log.d(TAG," post response code: " +  response.code() + " response body: " + response.body().string() + " response msg: " + response.message());
                }
            }
        });

    }

    private void getMethodAsync(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
         okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
             if(response.isSuccessful()){
                 Log.d(TAG," response code: " +  response.code() + " response body: " + response.body().string() + " response msg: " + response.message());
             }
            }
        });
    };
   //ok http get method sync
    private void getMethodSync(){
        //get method syncronous
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                Log.d(TAG," response code: " +  response.code() + " response body: " + response.body().string() + " response msg: " + response.message());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Runnable getSyncRunnable = new Runnable() {
        @Override
        public void run() {
            getMethodSync();
        }
    };
}
