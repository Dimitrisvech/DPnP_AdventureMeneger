package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Data.InventoryItem;

/**
 * Created by Arik on 06-Aug-16.
 * Custom adapter for the inventory
 */
public class InventoryItemAdapter extends ArrayAdapter<InventoryItem> {

    public InventoryItemAdapter(Context context, int resource, ArrayList<InventoryItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InventoryItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.element_item_list, parent, false);
        }
        // Lookup view for data population
        TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
        TextView itemType = (TextView) convertView.findViewById(R.id.item_type_val);
        TextView itemHitdie = (TextView) convertView.findViewById(R.id.item_hitdie_val);
        TextView itemAC = (TextView) convertView.findViewById(R.id.item_ac_val);
        // Populate the data into the template view using the data object
        itemName.setText(item.getName());
        itemType.setText(item.getType());
        itemHitdie.setText(item.getHitDie());
        itemAC.setText(item.getArmorClass());
        // Return rthe completed view to render on screen
        return convertView;
    }
}
