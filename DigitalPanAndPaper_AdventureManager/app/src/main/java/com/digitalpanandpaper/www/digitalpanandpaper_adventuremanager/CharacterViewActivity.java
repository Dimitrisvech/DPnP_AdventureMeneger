package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Interfaces.IDataAgent;

public class CharacterViewActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private ScrollView _container;
    private Character _char;
    private final Context _context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_view);
        init();
        showCharacterDetails(_char);
        initButtons();
    }

    private void init(){
        _dataAgent = DataAgentManager.getDataAgent(_context);
        _container = (ScrollView)findViewById(R.id.containerCharView);
        _char = _dataAgent.getLocalTemporaryCharacter();
    }

    private void showCharacterDetails(Character c){
        TextView charName = (TextView) findViewById(R.id.charNameView);
        String text = c.getName()+" "+c.getSurName();
        charName.setText(text);

        //Get element to show
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View charDetails = layoutInflater.inflate(R.layout.element_character_details, null);
        //Populate the textViews of the element
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
        Button invButton = (Button)charDetails.findViewById(R.id.invButton);
        invButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(CharacterViewActivity.this, "Inventory: Not implemented yet...", Toast.LENGTH_SHORT).show();
            }});

        Button equButton = (Button)charDetails.findViewById(R.id.equButton);
        equButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(CharacterViewActivity.this, "Equipment: Not implemented yet...", Toast.LENGTH_SHORT).show();
            }});
        _container.addView(charDetails);
    }

    private void initButtons(){
        Button buttonBack = (Button)findViewById(R.id.backButtonCharView);
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }});

        Button buttonSave = (Button)findViewById(R.id.saveButtonCharView);
        buttonSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(CharacterViewActivity.this, "Character saved!", Toast.LENGTH_SHORT).show();
                _dataAgent.updateOrInsertCharOfUser(Domain.getUser().getUsername(),_char);
                Intent intent = new Intent(getApplicationContext(), CharChooserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }});
    }
}
