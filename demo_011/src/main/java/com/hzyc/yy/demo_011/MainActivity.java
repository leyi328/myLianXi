package com.hzyc.yy.demo_011;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button getData;
    private ProgressDialog progressDialog;
    private final String PATH = "http://192.168.1.127:8080/tf34_002/TestServlet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData = (Button) findViewById(R.id.getData);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("数据加载中");
        progressDialog.setMessage("请等待。。。。。。。");

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(PATH);
            }
        });

    }

    class MyTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        //异步线程函数中获取web端的数据
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.i("请求web数据",params[0]);
                Thread.sleep(3000);
                //连接呢 socket
                //get传值
                //params[0] = params[0] + "?name=张三";

                Log.i("请求web数据",params[0]);
                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                //connection.setRequestMethod("GET"); //get 传输2kb以下  传值直接跟在URL后面会泄漏数据  不适合传中文
                connection.setRequestMethod("POST");
                connection.setReadTimeout(6000);
                //POST 传值
                String value = "name=张三&psd=123456";
                connection.getOutputStream().write(value.getBytes());


                //获取连接所得到的数据
                //直接读成字符串
                BufferedReader  reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str = null;
                StringBuffer stringBuffer = new StringBuffer();
                while((str = reader.readLine()) != null){
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
