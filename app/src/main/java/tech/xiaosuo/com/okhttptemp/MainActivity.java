package tech.xiaosuo.com.okhttptemp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    Button downLoadButton;
    ProgressBar progressBar;
    ImageView imageView;
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554272776682&di=70291487c5a10b49618a860b4535cf6a&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fimage%2F92246689%2F31698-ElPlsj_960x540.jpg";
    Context mContext;
    private static final int MSG_ERROR = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downLoadButton = findViewById(R.id.download_button);
        downLoadButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
        imageView = findViewById(R.id.image_view);
        mContext = this;
      //  postFormDataAsync();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.download_button:
                downLoadFile(url);
                break;
                default:
                    break;
        }
    }

    private void downLoadFile(String url){
/*           boolean checkPermission = requestWritePerm();
           if(!checkPermission){
                Log.d(TAG," we can't get the wirte external permission");
                return;
           }*/
          OkHttpClient okHttpClient = new OkHttpClient();
          Request request = new Request.Builder().url(url).build();
         okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG," onFailure e " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG," onResponse e " + response.code() + " msg: " + response.message() );//+ " body: " + response.body().string()
                byte[] imgBytes = response.body().bytes();
               // Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes,0,imgBytes.length);
                String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                Log.d(TAG," sdpath: " + sdPath);
                File file = new File(sdPath + File.separator + "meinv");
                File pic = null;
                try {
                    if(!file.exists()){
                        file.mkdirs();

                    }
                    pic = new File(file.getAbsolutePath() + "mei.jpg");
                    boolean result = pic.createNewFile();
                    if(result){
                         Log.d(TAG," create file success");
                    }else{
                        Log.d(TAG," create file fail");
                        Toast.makeText(getApplicationContext(),"create file fail",Toast.LENGTH_SHORT).show();
                    }
                    OutputStream outputStream = new FileOutputStream(pic);
                    outputStream.write(imgBytes);
                }catch (Exception e){
                   // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG," exception: " + e.toString());
                    Message msg = new Message();
                    msg.what = MSG_ERROR;
                    Bundle bundle = new Bundle();
                    bundle.putString("exception",e.toString());
                    msg.setData(bundle);
                    mainHandler.sendMessage(msg);
                    return;
                }

                Message msg = new Message();
                msg.what = 0;
              //  msg.obj = bitmap;
                Bundle bundle = new Bundle();
                bundle.putByteArray("bitmap_array",imgBytes);
                msg.setData(bundle);
                mainHandler.sendMessage(msg);

            }
        });
    }

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            Bundle bundle = msg.getData();
            switch (what){
                case 0:
                    if(bundle == null){
                        return;
                    }
                    byte[] bytes = bundle.getByteArray("bitmap_array");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                    imageView.setImageBitmap(bitmap);
                    break;
                case MSG_ERROR:
                    if(bundle == null){
                        return;
                    }
                    String exception = bundle.getString("exception");
                    Log.d(TAG," msg exception: " + exception);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setMessage(exception).setTitle("Warning").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
                    default:
                        break;
            }
        }
    };



       @TargetApi(Build.VERSION_CODES.M)
       private boolean requestWritePerm() {

           if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
               Log.d(TAG," sdk int less than m");
               return true;
           }

           if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
               Log.d(TAG," app have get the write external permission");
               return true;
           }
           boolean showPermRat = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
           if(showPermRat){
               Log.d(TAG," should show permission Rationale");
               requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23);
           }else{
               Log.d(TAG," should not show permission Rationale");
               requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23);
           }

           return false;
/*        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
          Snackbar.make(mPhoneNumberView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }*/

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 23) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //populateAutoComplete();
                Log.d(TAG," we have get the write external permission");
            }
        }
    }
}
