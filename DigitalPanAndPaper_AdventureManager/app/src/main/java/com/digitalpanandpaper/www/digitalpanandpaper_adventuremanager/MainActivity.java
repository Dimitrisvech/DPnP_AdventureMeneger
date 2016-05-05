package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.Character);
        spec.setIndicator("Character");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.Inventory);
        spec.setIndicator("Inventory");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.Combat);
        spec.setIndicator("Combat");
        host.addTab(spec);

        Intent intent = getIntent();
        String name = intent.getStringExtra("CharacterName");
        TextView charName = (TextView) findViewById(R.id.tabCharName);
        charName.setText(name);
    }
}
