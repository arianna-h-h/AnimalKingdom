package com.example.ariannaharadon.project4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }


    public void newMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}