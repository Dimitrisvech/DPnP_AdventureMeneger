package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import Data.Character;
import Data.DataAgentManager;
import Data.Domain;
import Data.InventoryItem;
import GameLogic.CharacterLogic;
import GameLogic.DiceLogic;
import Interfaces.IDataAgent;

public class MainActivity extends AppCompatActivity{

    private IDataAgent _dataAgent;
    private ScrollView _characterContainer;
    private LinearLayout _inventoryContainer;
    private ScrollView _combatContainer;
    private Character _myCharacter;
    private ArrayList<InventoryItem> _inventory;
    private final Context _context=this;
    private float lastX=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        tabInit();
        characterDetailTabInit();
        combatTabInit();
        inventoryListTabInit();
        hideKeyboard(_characterContainer.getRootView());
//        hideKeyboard(_characterContainer);
//        hideKeyboard(_inventoryContainer);
//        hideKeyboard(_combatContainer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {

        switch (touchEvent.getAction()) {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN:
                lastX = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchEvent.getX();

                // if left to right swipe on screen
                if (lastX > currentX) {

                    switchTabs(false);
                }
                // if right to left swipe on screen
                if (lastX < currentX) {
                    switchTabs(true);
                }
                break;
        }
        return false;
    }

    public void switchTabs(boolean direction) {
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        if (direction) // true = move left
        {
            if (tabHost.getCurrentTab() == 0)
                tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
            else
                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
        } else
        // move right
        {
            if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
                    .getTabCount() - 1))
                tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
            else
                tabHost.setCurrentTab(0);
        }
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
        _inventoryContainer = (LinearLayout)findViewById(R.id.Inventory);
        _combatContainer = (ScrollView)findViewById(R.id.Combat);
        Intent intent = getIntent();
        String name = intent.getStringExtra("CharacterName");

        _myCharacter = _dataAgent.getCharByName(name);
        _inventory = _dataAgent.getAllItemsByChar(_myCharacter.getCid());

    }

    private void inventoryListTabInit() {
        //Get element to show
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inventoryItems =  layoutInflater.inflate(R.layout.element_inventory,null);
        LinearLayout invLayout = (LinearLayout)inventoryItems.findViewById(R.id.inventoryLayout);
        ListView itemlist = (ListView)inventoryItems.findViewById(R.id.itemList);
        invLayout.setVisibility(View.VISIBLE);
        itemlist.setVisibility(View.VISIBLE);
        ArrayList<InventoryItem> arrayOfItems = new ArrayList<InventoryItem>();
        // Create the adapter to convert the array to views
        InventoryItemAdapter adapter = new InventoryItemAdapter(this,_inventory );
        // Attach the adapter to a ListView
        itemlist.setAdapter(adapter);

        final LinearLayout llAddNewItem = (LinearLayout) invLayout.findViewById(R.id.addItemDropdown);
        Button bAddNewItem = (Button) invLayout.findViewById(R.id.addNewItemButton);
        bAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText iname = (EditText) llAddNewItem.findViewById(R.id.edit_iname);
                EditText itype = (EditText) llAddNewItem.findViewById(R.id.edit_itype);
                EditText ihitdie = (EditText) llAddNewItem.findViewById(R.id.edit_ihitdie);
                EditText iarmorclass = (EditText) llAddNewItem.findViewById(R.id.edit_iac);

                Button bApply = (Button) llAddNewItem.findViewById(R.id.bAddItem_apply);
                bApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText iname = (EditText) llAddNewItem.findViewById(R.id.edit_iname);
                        EditText itype = (EditText) llAddNewItem.findViewById(R.id.edit_itype);
                        EditText ihitdie = (EditText) llAddNewItem.findViewById(R.id.edit_ihitdie);
                        EditText iarmorclass = (EditText) llAddNewItem.findViewById(R.id.edit_iac);
                        if (iname.getText().toString().matches("")
                                || itype.getText().toString().matches("")
                                || itype.getText().toString().matches("")
                                || itype.getText().toString().matches("")) {
                            Toast.makeText(MainActivity.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            _dataAgent.insertItem(new InventoryItem(_myCharacter.getCid(),
                                    Integer.valueOf(iarmorclass.getText().toString()),
                                    itype.getText().toString(),
                                    Integer.valueOf(ihitdie.getText().toString()),
                                    0,
                                    iname.getText().toString(),
                                    itype.getText().toString()
                            ));

                            _inventory = _dataAgent.getAllItemsByChar(_myCharacter.getCid());
                            ListView view = (ListView)_inventoryContainer.findViewById(R.id.itemList);
                            InventoryItemAdapter invadapter = (InventoryItemAdapter)view.getAdapter();
                            invadapter.clear();
                            invadapter.addAll(_inventory);
                            Spinner spview = (Spinner)_combatContainer.findViewById(R.id.weaponChooser);
                            ArrayAdapter<InventoryItem> dataAdapter = (ArrayAdapter<InventoryItem>) spview.getAdapter();
                            dataAdapter.clear();
                            for (InventoryItem iweapon:_inventory
                                    ) {
                                if(iweapon.getType().contains(Domain.weaponCode)){
                                    dataAdapter.add(iweapon);
                                }
                            }

                            Toast.makeText(MainActivity.this, "Item Created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                iname.setText("");
                itype.setText("");
                ihitdie.setText("");
                iarmorclass.setText("");
                itype.clearComposingText();
                ihitdie.clearComposingText();
                iarmorclass.clearComposingText();
                if (llAddNewItem.getVisibility() == View.GONE)
                    llAddNewItem.setVisibility(View.VISIBLE);
                else
                    llAddNewItem.setVisibility(View.GONE);
            }
        });

        Button bCancel = (Button) llAddNewItem.findViewById(R.id.bAddItem_cancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText iname = (EditText) llAddNewItem.findViewById(R.id.edit_iname);
                EditText itype = (EditText) llAddNewItem.findViewById(R.id.edit_itype);
                EditText ihitdie = (EditText) llAddNewItem.findViewById(R.id.edit_ihitdie);
                EditText iarmorclass = (EditText) llAddNewItem.findViewById(R.id.edit_iac);
                iname.setText("");
                itype.setText("");
                ihitdie.setText("");
                iarmorclass.setText("");
                llAddNewItem.setVisibility(View.GONE);
            }
        });

        _inventoryContainer.addView(inventoryItems);
        registerForContextMenu(itemlist);
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

        ArrayList<InventoryItem> weapons = new ArrayList<>();
        for (InventoryItem item:_inventory
             ) {
            if(item.getType().contains(Domain.weaponCode)){
                weapons.add(item);
            }
        }
        ArrayAdapter<InventoryItem> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, weapons);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner weaponList = (Spinner)combat.findViewById(R.id.weaponChooser);
        weaponList.setAdapter(dataAdapter);
        weaponList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hitDieText=((InventoryItem)parent.getSelectedItem()).getHitDie()+"";
                itemHitDieView.setText(hitDieText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set combat buttons onClick behavior
        ImageView addHpButton = (ImageView)combat.findViewById(R.id.addHpButton);
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
        ImageView subHpButton = (ImageView)combat.findViewById(R.id.subHpButton);
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
        ImageView addManaButton = (ImageView)combat.findViewById(R.id.addManaButton);
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
        ImageView subManaButton = (ImageView)combat.findViewById(R.id.subManaButton);
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

        ImageView attackButton = (ImageView)combat.findViewById(R.id.attackButton);
        attackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    int itemHitDie = Integer.parseInt(itemHitDieView.getText().toString());
                    int attack = DiceLogic.getHighRoll(itemHitDie, 1, strBonus);
                    String logText = battleLogView.getText().toString();
                    logText = "@ Attacked for " + attack + " points of damage!\n" + logText;
                    battleLogView.setText(logText);
                }
                catch (Exception e){
                    Toast.makeText(_context, "Cant attack without a weapon.", Toast.LENGTH_SHORT).show();
                }
            }});

        ImageView rollButton = (ImageView)combat.findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int dice = Integer.parseInt(rollDiceView.getText().toString());
                if (dice>0) {
                    int roll = DiceLogic.getHighRoll(dice, 1, 0);
                    String logText = battleLogView.getText().toString();
                    logText = "# Rolled (d" + dice + ") for " + roll + "!\n" + logText;
                    battleLogView.setText(logText);
                }
                else{
                    Toast.makeText(_context, "Dice must be greater the zero.", Toast.LENGTH_SHORT).show();
                }
            }});
        _combatContainer.addView(combat);
    }

   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.contextmenu_inventory_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final InventoryItem invItem = _inventory.get(info.position);
        switch (item.getItemId()) {
            case R.id.remove_item:
                _dataAgent.deleteItemFromChar(invItem.getId());
                _inventory = _dataAgent.getAllItemsByChar(_myCharacter.getCid());
                ListView view = (ListView)_inventoryContainer.findViewById(R.id.itemList);
                InventoryItemAdapter invadapter = (InventoryItemAdapter)view.getAdapter();
                invadapter.clear();
                invadapter.addAll(_inventory);
                Spinner spview = (Spinner)_combatContainer.findViewById(R.id.weaponChooser);
                ArrayAdapter<InventoryItem> dataAdapter = (ArrayAdapter<InventoryItem>) spview.getAdapter();
                dataAdapter.clear();
                for (InventoryItem iweapon:_inventory
                        ) {
                    if(iweapon.getType().contains(Domain.weaponCode)){
                        dataAdapter.add(iweapon);
                    }
                }
                dataAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Item removed from inventory", Toast.LENGTH_SHORT).show();
//                View view2 = (View) _combatContainer.findViewById(R.id.)
//                adapter.clear();
//                adapter.addAll();
                return true;
            case R.id.edit_item:
                LinearLayout llAddNewItem = (LinearLayout) _inventoryContainer.findViewById(R.id.addItemDropdown);
                llAddNewItem.setVisibility(View.VISIBLE);
                EditText iname = (EditText) llAddNewItem.findViewById(R.id.edit_iname);
                EditText itype = (EditText) llAddNewItem.findViewById(R.id.edit_itype);
                EditText ihitdie = (EditText) llAddNewItem.findViewById(R.id.edit_ihitdie);
                EditText iarmorclass = (EditText) llAddNewItem.findViewById(R.id.edit_iac);
                iname.setText(invItem.getName());
                itype.setText(invItem.getType());
                ihitdie.setText(String.valueOf(invItem.getHitDie()));
                iarmorclass.setText(String.valueOf(invItem.getArmorClass()));

                Button bApply = (Button) llAddNewItem.findViewById(R.id.bAddItem_apply);
                bApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout llAddNewItem = (LinearLayout) _inventoryContainer.findViewById(R.id.addItemDropdown);
                        EditText iname = (EditText) llAddNewItem.findViewById(R.id.edit_iname);
                        EditText itype = (EditText) llAddNewItem.findViewById(R.id.edit_itype);
                        EditText ihitdie = (EditText) llAddNewItem.findViewById(R.id.edit_ihitdie);
                        EditText iarmorclass = (EditText) llAddNewItem.findViewById(R.id.edit_iac);
                        if (iname.getText().toString().matches("")
                                || itype.getText().toString().matches("")
                                || itype.getText().toString().matches("")
                                || itype.getText().toString().matches("")) {
                            Toast.makeText(MainActivity.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            _dataAgent.deleteItemFromChar(invItem.getId());
                            _dataAgent.insertItem(new InventoryItem(invItem.getCid(),
                                    Integer.valueOf(iarmorclass.getText().toString()),
                                    invItem.getEquippedSpot(),
                                    Integer.valueOf(ihitdie.getText().toString()),
                                    0,
                                    iname.getText().toString(),
                                    itype.getText().toString()
                            ));
                            _inventory = _dataAgent.getAllItemsByChar(_myCharacter.getCid());
                            ListView view = (ListView)_inventoryContainer.findViewById(R.id.itemList);
                            InventoryItemAdapter invadapter = (InventoryItemAdapter)view.getAdapter();
                            invadapter.clear();
                            invadapter.addAll(_inventory);
                            Spinner spview = (Spinner)_combatContainer.findViewById(R.id.weaponChooser);
                            ArrayAdapter<InventoryItem> dataAdapter = (ArrayAdapter<InventoryItem>) spview.getAdapter();
                            dataAdapter.clear();
                            for (InventoryItem iweapon:_inventory
                                    ) {
                                if(iweapon.getType().contains(Domain.weaponCode)){
                                    dataAdapter.add(iweapon);
                                }
                            }
                            Toast.makeText(MainActivity.this, "Item Modified", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return true;
        }
        return false;
    }

    /**
     * Hide keyboard on touch of UI
     */
    public void hideKeyboard(View view) {

        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                hideKeyboard(innerView);
            }
        }
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(v);
                    return false;
                }

            });
        }

    }

    /**
     * Hide keyboard while focus is moved
     */
    public void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                if (android.os.Build.VERSION.SDK_INT < 11) {
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    if (this.getCurrentFocus() != null) {
                        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    view.clearFocus();
                }
                view.clearFocus();
            }
        }
    }

}
