package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Interfaces.IDataAgent;

public class AddCharacterActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private Character newCharacter;
    private final Context _context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);
        Init();
    }

    private void Init() {
        _dataAgent= DataAgentManager.getDataAgent(_context);

        Button buttonBack = (Button)findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }});

        Button buttonNext = (Button)findViewById(R.id.nextButton);
        final Intent viewChange = new Intent(this,AddCharacterStatsActivity.class);
        buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(checkCharacterDetails()) {
                    _dataAgent.setLocalTemporaryCharacter(newCharacter);
                    startActivity(viewChange);
                }

            }});
    }

    private boolean checkCharacterDetails(){
        TextView error = (TextView) findViewById(R.id.errorMessage);
        EditText nameField = (EditText) findViewById(R.id.newCharName);
        String name = nameField.getText().toString();
        if (name.isEmpty()){
            error.setText("Error: Name cannot be empty.");
            error.setVisibility(View.VISIBLE);
            return false;
        }
        Character temp = _dataAgent.getCharByName(name);
        if (temp!=null){
            error.setText("Error: Name already exists.");
            error.setVisibility(View.VISIBLE);
            return false;
        }
        EditText surnameField = (EditText) findViewById(R.id.newCharSurname);
        String surname = surnameField.getText().toString();
        EditText raceField = (EditText) findViewById(R.id.newCharRace);
        String race = raceField.getText().toString();
        EditText occupationField = (EditText) findViewById(R.id.newCharOccupation);
        String occupation = occupationField.getText().toString();
        newCharacter = new Character(0,Domain.getUser().getUid(),name,surname,race,occupation,0,0,0);
        return true;
    }
}
