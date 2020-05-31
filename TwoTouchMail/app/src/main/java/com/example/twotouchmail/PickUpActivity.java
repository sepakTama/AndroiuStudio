package com.example.twotouchmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PickUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);

        Button btnSend = (Button) this.findViewById(R.id.button);
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioGroup rgPlace = (RadioGroup) findViewById(R.id.rg_place);
                int checkedID = rgPlace.getCheckedRadioButtonId();
                String strPlace = ((RadioButton)findViewById(checkedID)).getText().toString();
                EditText edit01 = (EditText)findViewById(R.id.editText);
                String title = edit01.getText().toString();

                Resources res = getResources();
                Uri uri = Uri.parse("mailto:" + res.getString(R.string.mail_to).toString());

                Intent intent=new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_TEXT, strPlace+"に迎えにきて");
                startActivity(intent);

            }
        });
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
