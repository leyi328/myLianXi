package com.hzyc.yy.demo_09;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button read;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        read = (Button) findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在08里面 把cp 设置成对外开放的
                //我需要给08定义一个权限 （自定义）
                //把这个权限给 CP


                //得到客户端
                ContentResolver cr = getContentResolver();
                String path = "content://com.hzyc.yy.demo_08.MyContentProvider/userinfo";
                Uri uri = Uri.parse(path);
                Cursor cursor = cr.query(uri,new String []{"username","password"},null,null,null);
                while(cursor.moveToNext()){
                    String username = cursor.getString(cursor.getColumnIndex("username"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    Log.i("数据库动态", "username=" + username + " password="+password);
                }


            }
        });
    }
}
