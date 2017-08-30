package com.hzyc.yy.demo_06;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //io  线程 thread  调度 timer

    private Button open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open = (Button) findViewById(R.id.open);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setIcon(R.drawable.image1);
                progressDialog.setTitle("正在加载");
                progressDialog.setMessage("请等待.......");


                //设置样式
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //设置一个长度
                progressDialog.setMax(100);
                progressDialog.show();


                //进度自己增加  线程（一个独立的干活的主方法）
                //UI线程 主线程  （其他的线程子线程  需要通过主线程启动）

                //run方法 每个线程执行的方法 （一旦启动只调用run方法  调用一遍）
                class myThread extends Thread{
                    @Override
                    public void run() {
                        int i = 0;
                        while(true){
                            try {
                                sleep(1000);
                                progressDialog.setProgress(i+=10);
                                if(i == 100){
                                    break;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                new myThread().start();

            }
        });


    }
}
