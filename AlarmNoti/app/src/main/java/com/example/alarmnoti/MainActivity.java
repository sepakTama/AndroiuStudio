package com.example.alarmnoti;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity
                            implements View.OnClickListener {

    private InputMethodManager mInputMethodManager;
    private RelativeLayout mLayout;
    private int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimePicker tPicker = (TimePicker)findViewById(R.id.timePicker);
        tPicker.setIs24HourView(true);

        EditText edText = (EditText) findViewById(R.id.editText);
        mLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        // キーボード表示を制御するためのオブジェクト
        mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        Button btnSet = (Button)findViewById(R.id.set);
        btnSet.setOnClickListener(this);
        Button btnCancel = (Button)findViewById(R.id.cancel);
        btnCancel.setOnClickListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        // ソフトキーボードを非表示にする
        mInputMethodManager.hideSoftInputFromWindow(mLayout.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        // フォーカスを背景に
        mLayout.requestFocus();

        return false;
    }


    @Override
    public void onClick(View v){

        EditText edText = (EditText)findViewById(R.id.editText);
        // AlarmReceiverを呼び出すインテント
        // マニフェストファイルにreceiverが必要
        Intent bootIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        // 追加データとして、Notificationの識別子を渡す
        bootIntent.putExtra("notificationId", notificationId);
        // 追加データとして、やることを渡す
        bootIntent.putExtra("todo", edText.getText());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
        bootIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        TimePicker tPicker = (TimePicker)findViewById(R.id.timePicker);

        switch (v.getId()){
            case R.id.set:
                int hour = tPicker.getCurrentHour();
                int minute = tPicker.getCurrentMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                alarm.set(
                        AlarmManager.RTC_WAKEUP,
                        alarmStartTime,
                        alarmIntent
                );
                Toast.makeText(MainActivity.this, "通知をセットしました!", Toast.LENGTH_SHORT).show();
                notificationId++;

                break;
            case R.id.cancel:
                alarm.cancel(alarmIntent);
                Toast.makeText(MainActivity.this, "通知をキャンセルしました!", Toast.LENGTH_SHORT).show();
                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here, The action bar will
        // automatically handle clicks on the home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        if(id == R.id.action_setting){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
