package com.example.sibal;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.Glide;


public class BodyLayout extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_layout);

        ImageView image =(ImageView) findViewById(R.id.iv);

        DrawableImageViewTarget gifImage = new DrawableImageViewTarget(image);
        Glide.with(this).load(R.drawable.imageex).into(gifImage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.body_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
       actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
               finish();
              return true;
           }
       }
        return super.onOptionsItemSelected(item);
    }
}
