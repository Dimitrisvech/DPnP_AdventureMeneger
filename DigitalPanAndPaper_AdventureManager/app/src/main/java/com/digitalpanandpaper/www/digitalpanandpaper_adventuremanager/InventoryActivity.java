package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Data.Character;
import Data.DataAgentManager;
import Data.InventoryItem;
import Interfaces.IDataAgent;

public class InventoryActivity extends AppCompatActivity {

    private IDataAgent _dataAgent;
    private ArrayList<InventoryItem> _itemList;
    private ListView itemList;
    private Character _char;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_inventory);
        Init();
    }

    private void Init() {
        itemList = (ListView)findViewById(R.id.itemList);
        _dataAgent = DataAgentManager.getDataAgent(context);
        _char = new Character(0,9, "a", "a", "a", "a", 12,12,12);
        _itemList=_dataAgent.getAllItemsByChar(_char.getCid());
        // Create the adapter to convert the array to views
        InventoryItemAdapter adapter = new InventoryItemAdapter(this, _itemList);
//         Attach the adapter to a ListView
        itemList.setAdapter(adapter);

//        itemList.
//
//        final Intent viewChange = new Intent(this,AddInventoryItemActivity.class);
//        Button buttonAddItem = (Button)findViewById(R.id.addNewItemButton);
//        buttonAddItem.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startActivity(viewChange);
//            }});
    }
}
