package com.example.ariannaharadon.project4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TitleActivity extends Activity {
    //First screen the user sees. The content is established by activity_title.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    //Start MainActivity with OnClick listener on a button
    public void newMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}