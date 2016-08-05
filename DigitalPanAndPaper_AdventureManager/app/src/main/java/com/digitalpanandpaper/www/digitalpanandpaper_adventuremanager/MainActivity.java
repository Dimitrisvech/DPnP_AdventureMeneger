package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Data.InventoryItem;
import GameLogic.CharacterLogic;
import GameLogic.DiceLogic;
import Interfaces.IDataAgent;

public class MainActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private ScrollView _characterContainer;
    private ScrollView _inventoryContainer;
    private ScrollView _combatContainer;
    private Character _myCharacter;
    private ArrayList<InventoryItem> _inventory;
    private final Context _context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        tabInit();
        characterDetailTabInit();
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
        _combatContainer = (ScrollView)findViewById(R.id.Combat);
        Intent intent = getIntent();
        String name = intent.getStringExtra("CharacterName");

        _myCharacter = _dataAgent.getCharByName(name);
        _inventory = _dataAgent.getAllItemsByChar(_myCharacter.getCid());

    }

    private void characterDetailTabInit(){
        //Get element to show
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View charDetails = layoutInflater.inflate(R.layout.element_character_details, null);
        //Populate the textViews of the element
        LinearLayout nameLayout = (LinearLayout)charDetails.findViewById(R.id.characterDetailsLayoutName);
        nameLayout.setVisibility(View.VISIBLE);
        TextView nameView = (TextView)charDetails.findViewById(R.id.nameView);
        String text = " " + _myCharacter.getName() +" "+_myCharacter.getSurName();
        nameView.setText(text);

        TextView raceView = (TextView)charDetails.findViewById(R.id.raceView);
        raceView.setText(_myCharacter.getRace());

        TextView occupationView = (TextView)charDetails.findViewById(R.id.occupationView);
        occupationView.setText(_myCharacter.getOccupation());

        TextView hpView = (TextView)charDetails.findViewById(R.id.hpView);
        text=" " + _myCharacter.getMaxHealth();
        hpView.setText(text);

        TextView manaView = (TextView)charDetails.findViewById(R.id.manaView);
        text=" " + _myCharacter.getMaxMana();
        manaView.setText(text);

        TextView acView = (TextView)charDetails.findViewById(R.id.acView);
        text=" " + _myCharacter.getAC();
        acView.setText(text);

        TextView strView = (TextView)charDetails.findViewById(R.id.strView);
        text=" " + _myCharacter.getStat(Domain.Stat.STR);
        strView.setText(text);

        TextView dexView = (TextView)charDetails.findViewById(R.id.dexView);
        text=" " + _myCharacter.getStat(Domain.Stat.DEX);
        dexView.setText(text);

        TextView conView = (TextView)charDetails.findViewById(R.id.conView);
        text=" " + _myCharacter.getStat(Domain.Stat.CON);
        conView.setText(text);

        TextView intView = (TextView)charDetails.findViewById(R.id.intView);
        text=" " + _myCharacter.getStat(Domain.Stat.INT);
        intView.setText(text);

        TextView wisView = (TextView)charDetails.findViewById(R.id.wisView);
        text=" " + _myCharacter.getStat(Domain.Stat.WIS);
        wisView.setText(text);

        TextView chaView = (TextView)charDetails.findViewById(R.id.chaView);
        text=" " + _myCharacter.getStat(Domain.Stat.CHA);
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

    private void combatTabInit(){
        int strStat = _myCharacter.getStat(Domain.Stat.STR);
        final int strBonus = CharacterLogic.getBonusFromStat(strStat);
        //Get element to show
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View combat = layoutInflater.inflate(R.layout.element_combat_tab, null);
        //Init Edit text for button use
        final EditText modHpView = (EditText)combat.findViewById(R.id.modifyHp);
        final EditText modManaView = (EditText)combat.findViewById(R.id.modifyMana);
        final EditText rollDiceView = (EditText)combat.findViewById(R.id.rollDie);
        final TextView battleLogView = (TextView)combat.findViewById(R.id.battleLog);
        final TextView itemHitDieView = (TextView)combat.findViewById(R.id.itemHitDie);

        //Populate the textViews of the element
        final TextView currentHpView = (TextView)combat.findViewById(R.id.currentHp);
        String text =_myCharacter.getHealth()+"";
        currentHpView.setText(text);

        final TextView currentManaView = (TextView)combat.findViewById(R.id.currentMana);
        text = _myCharacter.getMana()+"";
        currentManaView.setText(text);

        TextView attackStrStatView = (TextView)combat.findViewById(R.id.attackStrStat);
        text = strStat+"";
        attackStrStatView.setText(text);

        ArrayAdapter<InventoryItem> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, _inventory);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner weaponList = (Spinner)combat.findViewById(R.id.weaponChooser);
        weaponList.setAdapter(dataAdapter);
        weaponList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Set combat buttons onClick behavior
        ImageButton addHpButton = (ImageButton)combat.findViewById(R.id.addHpButton);
        addHpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentHp=Integer.parseInt(currentHpView.getText().toString());
                int modHp=Integer.parseInt(modHpView.getText().toString());
                int newHp=currentHp+modHp;
                String hpText=newHp+"";
                currentHpView.setText(hpText);
                String logText = battleLogView.getText().toString();
                logText = "+ Healed by "+modHp+" points.\n" + logText;
                battleLogView.setText(logText);
            }});
        ImageButton subHpButton = (ImageButton)combat.findViewById(R.id.subHpButton);
        subHpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentHp=Integer.parseInt(currentHpView.getText().toString());
                int modHp=Integer.parseInt(modHpView.getText().toString());
                int newHp=currentHp-modHp;
                String hpText=newHp+"";
                currentHpView.setText(hpText);
                String logText = battleLogView.getText().toString();
                logText = "- Damaged by "+modHp+" points.\n" + logText;
                battleLogView.setText(logText);
            }});
        ImageButton addManaButton = (ImageButton)combat.findViewById(R.id.addManaButton);
        addManaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentMana=Integer.parseInt(currentManaView.getText().toString());
                int modMana=Integer.parseInt(modManaView.getText().toString());
                int newMana=currentMana+modMana;
                String manaText=newMana+"";
                currentManaView.setText(manaText);
                String logText = battleLogView.getText().toString();
                logText = "+ Restored "+modMana+" points of mana.\n" + logText;
                battleLogView.setText(logText);
            }});
        ImageButton subManaButton = (ImageButton)combat.findViewById(R.id.subManaButton);
        subManaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentMana=Integer.parseInt(currentManaView.getText().toString());
                int modMana=Integer.parseInt(modManaView.getText().toString());
                int newMana=currentMana-modMana;
                String manaText=newMana+"";
                currentManaView.setText(manaText);
                String logText = battleLogView.getText().toString();
                logText = "- Spent "+modMana+" points of mana.\n" + logText;
                battleLogView.setText(logText);
            }});

        ImageButton attackButton = (ImageButton)combat.findViewById(R.id.attackButton);
        attackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int itemHitDie=Integer.parseInt(itemHitDieView.getText().toString());
                int attack = DiceLogic.getHighRoll(itemHitDie,1,strBonus);
                String logText = battleLogView.getText().toString();
                logText = "@ Attacked for "+attack+" points of damage!\n" + logText;
                battleLogView.setText(logText);
            }});
        _combatContainer.addView(combat);
    }
}
