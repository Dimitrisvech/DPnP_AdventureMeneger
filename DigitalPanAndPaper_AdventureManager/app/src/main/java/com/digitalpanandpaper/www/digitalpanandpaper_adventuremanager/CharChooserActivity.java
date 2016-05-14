package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Interfaces.IDataAgent;

public class CharChooserActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private ArrayList<Character> _characterList;
    private LinearLayout _container;
    private final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_chooser);
        Init();
        DisplayAllChars();
    }

    public void removeChar(String name){
        _dataAgent.deleteCharFromUser(Domain.getUser(),name);
    }

    private void Init() {
        _container = (LinearLayout)findViewById(R.id.container);
        _dataAgent= DataAgentManager.getDataAgent();
        _characterList=_dataAgent.getAllCharsByUser(Domain.getUser());

        final Intent viewChange = new Intent(this,AddCharacterActivity.class);
        Button buttonAddChar = (Button)findViewById(R.id.addNewCharButton);
        buttonAddChar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(viewChange);
            }});
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
        final String characterName = charName;
        viewChange.putExtra("CharacterName",charName);
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View charView = layoutInflater.inflate(R.layout.character_chooser_element, null);
        TextView charNameRow = (TextView)charView.findViewById(R.id.charName);
        charNameRow.setText(cNameRow);
        charNameRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Button buttonDelete = (Button)charView.findViewById(R.id.delete);
                if (buttonDelete.getVisibility()==View.GONE)
                    buttonDelete.setVisibility(View.VISIBLE);
                else
                    buttonDelete.setVisibility(View.GONE);
                return true;
            }
        });
        TextView charDetailRow = (TextView)charView.findViewById(R.id.charDetail);
        charDetailRow.setText(cDetailRow);
        Button buttonChoose = (Button)charView.findViewById(R.id.choose);
        buttonChoose.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //start intent and change layout, pass the character id.
                startActivity(viewChange);
            }});
        Button buttonDelete = (Button)charView.findViewById(R.id.delete);
        buttonDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //*************************
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Do your Yes progress
                                removeChar(characterName);
                                ((LinearLayout)charView.getParent()).removeView(charView);
                                String deletedMsg = characterName+" has been deleted.";
                                Toast.makeText(CharChooserActivity.this, deletedMsg, Toast.LENGTH_SHORT).show();
                                
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do your No progress
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(context);
                ab.setMessage("Are you sure you want to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                //*************************

            }});
        _container.addView(charView);
    }
}
