package com.example.sibal;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class TimerSideMenu extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS=0;

    private TextView textcountdown;
    private Button mButtonStart;
    private Button mButtonReset;
    private EditText mEditTextInput;
    private Button mButtonSet;
    private Button mtestButton;//팝업팡
    
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mstartTimeInMillis;
    private long mTimeLeftInMillis=START_TIME_IN_MILLIS;
    private long mEndTime;
    //handle 써야함

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_timer);

        mEditTextInput=findViewById(R.id.edittext);
        mButtonSet=findViewById(R.id.button_set);
        textcountdown =findViewById(R.id.textview_timer);


        mButtonStart=findViewById(R.id.bt_start);
        mButtonReset=findViewById(R.id.bt_reset);
        mtestButton=findViewById(R.id.testbutton);//팝업창

        Toolbar toolbar = (Toolbar) findViewById(R.id.body_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들

        //팝업창 만드는 코드
        mtestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad =new AlertDialog.Builder(TimerSideMenu.this);

                ad.setTitle("제목");
                ad.setMessage("세트를 입력하시오");

                final EditText edit=new EditText(TimerSideMenu.this);
                ad.setView(edit);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result=edit.getText().toString(); //edittext에 그 값을 가지고 와라
                        mEditTextInput.setText(result); //mEditTextInput=edittext
                        dialog.dismiss();
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

              /*  String input=textcountdown.getText().toString();
                if(input.length()==0){
                    Toast.makeText(TimerSideMenu.this,"empty",Toast.LENGTH_LONG).show();
                    return;
                }

                long millisInput=Long.parseLong(input)*1000;
                if(millisInput==0){
                    Toast.makeText(TimerSideMenu.this,"empty",Toast.LENGTH_LONG).show();
                    return;
                }
                setTime(millisInput);
                textcountdown.setText("");*/
            }


        });
        //팝업창 만드는 코드 이 위 까지

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input= mEditTextInput.getText().toString();
                if(input.length()==0){
                    Toast.makeText(TimerSideMenu.this,"empty",Toast.LENGTH_LONG).show();
                    return;
                }

                long millisInput=Long.parseLong(input)*1000;
                if(millisInput==0){
                    Toast.makeText(TimerSideMenu.this,"empty",Toast.LENGTH_LONG).show();
                    return;
                }
                setTime(millisInput);
                mEditTextInput.setText("");

            }
        });

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updaateCountDownText();
    }
    private void  setTime(long milliseconds){
        mstartTimeInMillis=milliseconds;
        resetTimer();
        closekeyboard();
    }

    private void startTimer(){
        mEndTime=System.currentTimeMillis()+mTimeLeftInMillis;

        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis=millisUntilFinished;
                updaateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning=false;
                mButtonStart.setText("start");
                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
               // updateWatchInterface();
            }
        }.start();

        mTimerRunning=true;
         mButtonStart.setText("pause");
         mButtonReset.setVisibility(View.INVISIBLE);
        //updateWatchInterface();
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning=false;
        //updateWatchInterface();
        mButtonStart.setText("start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis=mstartTimeInMillis;
        updaateCountDownText();
        updateWatchInterface();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStart.setVisibility(View.VISIBLE);
    }

    private void updaateCountDownText(){
        int minutes=(int) (mTimeLeftInMillis/1000) / 60;
        int seconds=(int) (mTimeLeftInMillis/1000) % 60;

        String timeLeftFormatted= String.format(Locale.getDefault(),
                    "%02d:%02d",minutes,seconds);

        textcountdown.setText(timeLeftFormatted);
    }

    private void  updateWatchInterface(){ //updateButtons
        if(mTimerRunning){
            mEditTextInput.setVisibility(View.VISIBLE); //edit버튼
            //mButtonSet.setVisibility(View.VISIBLE);

            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStart.setText("Pause");
        }else{
            mEditTextInput.setVisibility(View.VISIBLE);
            //mButtonSet.setVisibility(View.VISIBLE);
            mButtonStart.setText("start");

            if(mTimeLeftInMillis<1000){
                mButtonStart.setVisibility(View.INVISIBLE);
            }else{
                mButtonStart.setVisibility((View.VISIBLE));
            }

            if (mTimeLeftInMillis<mstartTimeInMillis){
                mButtonStart.setVisibility(View.VISIBLE);
            }else{
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void  closekeyboard(){
        View view=this.getCurrentFocus();
        if (view !=null){
            InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    @Override

    protected  void onStop(){
        super.onStop();

        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
         SharedPreferences.Editor editor=prefs.edit();

         editor.putLong("startTimeInMillis",mstartTimeInMillis);
          editor.putLong("millisLeft",mTimeLeftInMillis);
         editor.putBoolean("timerRunning",mTimerRunning);
         editor.putLong("endTime",mEndTime);

        editor.apply();

         if(mCountDownTimer !=null){
            mCountDownTimer.cancel();
    }
}

@Override
protected void onStart(){
        super.onStart();

        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);

        mstartTimeInMillis=prefs.getLong("startTimeMillis",100000);
        mTimeLeftInMillis=prefs.getLong("millisLeft",mstartTimeInMillis);
        mTimerRunning=prefs.getBoolean("timerRunning",false);

        updaateCountDownText();
        updateWatchInterface();

        if(mTimerRunning){
            mEndTime=prefs.getLong("endTime",0);
            mTimeLeftInMillis=mEndTime-System.currentTimeMillis();

            if (mTimeLeftInMillis<0){
                mTimeLeftInMillis=0;
                mTimerRunning=false;
                updaateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }

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
