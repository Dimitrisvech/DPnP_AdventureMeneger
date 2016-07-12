package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Interfaces.IDataAgent;

public class MainActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private ScrollView _characterContainer;
    private ScrollView _inventoryContainer;
    private Character _myCharacter;
    private final Context _context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        tabInit();
        characterDetailTabInit(_myCharacter);
    }
    private void tabInit(){
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
    }
    private void init(){
        _dataAgent = DataAgentManager.getDataAgent(_context);
        _characterContainer = (ScrollView)findViewById(R.id.Character);
        _inventoryContainer = (ScrollView)findViewById(R.id.Inventory);

        Intent intent = getIntent();
        String name = intent.getStringExtra("CharacterName");

        _myCharacter = _dataAgent.getCharByName(name);

    }

    private void characterDetailTabInit(Character c){
        //Get element to show
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View charDetails = layoutInflater.inflate(R.layout.element_character_details, null);
        //Populate the textViews of the element
        LinearLayout nameLayout = (LinearLayout)charDetails.findViewById(R.id.characterDetailsLayoutName);
        nameLayout.setVisibility(View.VISIBLE);
        TextView nameView = (TextView)charDetails.findViewById(R.id.nameView);
        String text = " " + c.getName() +" "+c.getSurName();
        nameView.setText(text);

        TextView raceView = (TextView)charDetails.findViewById(R.id.raceView);
        raceView.setText(c.getRace());

        TextView occupationView = (TextView)charDetails.findViewById(R.id.occupationView);
        occupationView.setText(c.getOccupation());

        TextView hpView = (TextView)charDetails.findViewById(R.id.hpView);
        text=" " + c.getMaxHealth();
        hpView.setText(text);

        TextView manaView = (TextView)charDetails.findViewById(R.id.manaView);
        text=" " + c.getMaxMana();
        manaView.setText(text);

        TextView acView = (TextView)charDetails.findViewById(R.id.acView);
        text=" " + c.getAC();
        acView.setText(text);

        TextView strView = (TextView)charDetails.findViewById(R.id.strView);
        text=" " + c.getStat(Domain.Stat.STR);
        strView.setText(text);

        TextView dexView = (TextView)charDetails.findViewById(R.id.dexView);
        text=" " + c.getStat(Domain.Stat.DEX);
        dexView.setText(text);

        TextView conView = (TextView)charDetails.findViewById(R.id.conView);
        text=" " + c.getStat(Domain.Stat.CON);
        conView.setText(text);

        TextView intView = (TextView)charDetails.findViewById(R.id.intView);
        text=" " + c.getStat(Domain.Stat.INT);
        intView.setText(text);

        TextView wisView = (TextView)charDetails.findViewById(R.id.wisView);
        text=" " + c.getStat(Domain.Stat.WIS);
        wisView.setText(text);

        TextView chaView = (TextView)charDetails.findViewById(R.id.chaView);
        text=" " + c.getStat(Domain.Stat.CHA);
        chaView.setText(text);

        //TODO set text according to real counts (after inv and equ implementation)
        TextView invCount = (TextView)charDetails.findViewById(R.id.invCount);
        invCount.setText("0");

        TextView equipmentCount = (TextView)charDetails.findViewById(R.id.equipmentCount);
        equipmentCount.setText("0");

        //Set charDetails buttons onClick behavior
        LinearLayout invLayout = (LinearLayout)charDetails.findViewById(R.id.characterDetailsLayoutInv);
        invLayout.setVisibility(View.GONE);
        /*Button invButton = (Button)charDetails.findViewById(R.id.invButton);
        invButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Inventory: Not implemented yet...", Toast.LENGTH_SHORT).show();
            }});*/

        Button equButton = (Button)charDetails.findViewById(R.id.equButton);
        equButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Equipment: Not implemented yet...", Toast.LENGTH_SHORT).show();
            }});
        _characterContainer.addView(charDetails);
    }
}
