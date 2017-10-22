package com.hzyc.yy.demo_010;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //1.android 中默认存在一个 主线程 == main == UI线程
    //1.不要在UI线程中做繁重的操作  （阻塞了UI线程）  只有UI线程卡了
    //只要主线程5秒之内处理不完当前任务 提示无响应
    //2.不要在UI线程之外更新控件
    private Button button1, button2;
    private ImageView imageView;
    private final String PATH = "http://192.168.1.127:8080/tf34_002/TestServlet";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.imageView);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("请等待");
        progressDialog.setMessage("加载中。。。。。。。");
     /*   button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("message","在干活");
            }
        });
*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(R.drawable.camera);
                    }
                }).start();*/
               /* imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(R.drawable.camera);
                    }
                });*/
                //执行任务
                new myTask().execute(PATH);
            }
        });
    }

    //内部类形式
    //Bitmap 位图  （android  图片）
    //<传入到异步任务内部的参数，监控异步执行的进度 Void没有进度，任务获取的数据类型>
    //String... == String []
    class myTask extends AsyncTask<String, Void, String> {

        //1---》2---》3---》4
        //1
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        //2更新进度  但是圆形进度条不需要的
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //3
        //重要的方法  这个方法其实就是一个异步线程
        //这个方法的返回值就是异步任务最终获取到的数据   数据是由 泛型中第三个泛型定义的
        @Override
        protected String doInBackground(String... params) {
            Log.i("参数", params[0]);
            try {
                Thread.sleep(3000);
                //建立连接
                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(6000);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str = null;
                StringBuffer stringBuffer = new StringBuffer();
                while((str = bufferedReader.readLine()) != null){
                    stringBuffer.append(str);
                }
                return stringBuffer.toString();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //4 代表异步任务执行完毕  异步线程重新回到UI线程
        //可以更新控件
        //bitmap参数就是  异步任务方法的返回值
        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
