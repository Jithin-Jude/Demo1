package com.inc.mountzoft.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar()/* or getSupportActionBar() */.setSubtitle(Html.fromHtml("<font color=\"red\">" + "Hello World !" + "</font>"));
        getSupportActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=\"red\">" + "J.A.R.V.I.S" + "</font>"));
    }

    public void proPicButtonFunction(View view) {
        Toast.makeText(Main2Activity.this, "Profile Picture !", Toast.LENGTH_LONG).show();
    }
}
