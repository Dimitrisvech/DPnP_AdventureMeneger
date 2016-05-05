package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Interfaces.IDataAgent;

public class CharChooserActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private ArrayList<Character> _characterList;
    private LinearLayout _container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_chooser);
        Init();
        DisplayAllChars();
    }

    private void Init() {
        _container = (LinearLayout)findViewById(R.id.container);
        _dataAgent= DataAgentManager.getDataAgent();
        _characterList=_dataAgent.getAllCharsByUser(Domain.getUser());
    }

    private void DisplayAllChars(){
        TextView noCharMsg = (TextView)findViewById(R.id.noCharMsg);
        if (_characterList.isEmpty())
            noCharMsg.setVisibility(View.VISIBLE);
        else {
            noCharMsg.setVisibility(View.GONE);
            for (Character character:_characterList) {
                String nameRow = character.getName() + " " + character.getSurName()
                                + " The " + character.getOccupation() + "("+character.getRace() + ")";
                String DetailRow = "Game World: " + character.getWorld().getWorldName() + "; Exp: " + character.getExp();
                DisplayChar(nameRow,DetailRow,character.getName());
            }
        }

    }
    private void DisplayChar(String cNameRow,String cDetailRow,String charName){
        final Intent viewChange = new Intent(this,MainActivity.class);
        viewChange.putExtra("CharacterName",charName);
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View charView = layoutInflater.inflate(R.layout.character_chooser_element, null);
        TextView charNameRow = (TextView)charView.findViewById(R.id.charName);
        charNameRow.setText(cNameRow);
        TextView charDetailRow = (TextView)charView.findViewById(R.id.charDetail);
        charDetailRow.setText(cDetailRow);
        Button buttonChoose = (Button)charView.findViewById(R.id.choose);
        buttonChoose.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //start intent and change layout, pass the character id.
                startActivity(viewChange);
            }});
        _container.addView(charView);
    }
}
