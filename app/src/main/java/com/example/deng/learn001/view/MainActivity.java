package com.example.deng.learn001.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.deng.learn001.R;
import com.example.deng.learn001.Utils.HttpUtils;
import com.example.deng.learn001.bean.Data;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mViewListView;
    private List<Data.DataBean> data1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data1=new ArrayList<>();
        mViewListView = (ListView) findViewById(R.id.list_view);
        mViewListView.setAdapter(new MyAadpter());
        initData();


    }

    private void initData() {

        HttpUtils.doGetAsyn("http://120.27.41.245:3000/budejie", new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {

                if (result!=null){
                    System.out.println(result);
                    Gson gson = new Gson();
                    Data data = gson.fromJson(result, Data.class);
                    data1 = data.getData();
                }
            }
        });


    }

    private class MyAadpter extends BaseAdapter {

        @Override
        public int getCount() {
            return data1.size();
        }

        @Override
        public Object getItem(int position) {
            return data1.get(position).getTitle();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv=null;

            if(convertView != null) {       // 判断缓存对象是否为null,  不为null时已经缓存了对象

                tv = (TextView) convertView;
            } else {    // 等于null, 说明第一次显示, 新创建

                tv = new TextView(MainActivity.this);
            }
            tv.setText(data1.get(position).getTitle());
           return tv;
        }
    }
}
