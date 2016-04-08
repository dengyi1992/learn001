package com.example.deng.learn001.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.deng.learn001.R;
import com.example.deng.learn001.Utils.HttpUtils;
import com.example.deng.learn001.bean.Data;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private static final int GETIT = 1;
    private RecyclerView mRvRecyclerView;
    private List<Data.DataBean> data1;
    private MyAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETIT:
                    adapter.notifyDataSetChanged();
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        data1 = new ArrayList<>();
        mRvRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRvRecyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new MyAdapter();
        mRvRecyclerView.setAdapter(adapter);
        initData();

    }

    private void initData() {

        HttpUtils.doGetAsyn("http://120.27.41.245:3000/budejie", new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {

                if (result != null) {
                    System.out.println(result);
                    Gson gson = new Gson();
                    Data data = gson.fromJson(result, Data.class);
                    data1 = data.getData();

                    handler.sendEmptyMessage(GETIT);
                    /**
                     * 子线程不能更新ui
                     */
                }
            }
        });


    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVH> {
        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Main2Activity.this)
                    .inflate(R.layout.list_item, parent, false);


            return new MyVH(view);
        }

        @Override
        public void onBindViewHolder(MyVH holder, int position) {
//            holder.imageViewImageView
            Glide.with(Main2Activity.this).load(data1.get(position).getImgbig()).into(holder.imageViewImageView);
            holder.textViewTextView.setText(data1.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return data1.size();
        }

        public class MyVH extends RecyclerView.ViewHolder {
            public ImageView imageViewImageView ;
            public TextView textViewTextView;
            public MyVH(View itemView) {
                super(itemView);
                imageViewImageView = (ImageView) itemView.findViewById(R.id.imageView);
                textViewTextView = (TextView) itemView.findViewById(R.id.textView);
            }
        }
    }
}
