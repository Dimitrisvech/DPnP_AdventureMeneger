package com.digitalpanandpaper.www.digitalpanandpaper_adventuremanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import GameLogic.CharacterLogic;

public class AddCharacterStatsActivity extends AppCompatActivity {

    private String[] _numPickerValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character_stats);
        initNumberPickers();
    }

    private void initNumberPickers(){
        NumberPicker[] numPickers = {
                (NumberPicker)findViewById(R.id.strNumPicker),
                (NumberPicker)findViewById(R.id.dexNumPicker),
                (NumberPicker)findViewById(R.id.conNumPicker),
                (NumberPicker)findViewById(R.id.intNumPicker),
                (NumberPicker)findViewById(R.id.wisNumPicker),
                (NumberPicker)findViewById(R.id.chaNumPicker)};

        TextView[] bonusDisplay = {
                (TextView)findViewById(R.id.strBonus),
                (TextView)findViewById(R.id.dexBonus),
                (TextView)findViewById(R.id.conBonus),
                (TextView)findViewById(R.id.intBonus),
                (TextView)findViewById(R.id.wisBonus),
                (TextView)findViewById(R.id.chaBonus)};

        _numPickerValues = getDisplayValues(CharacterLogic.MinStatValue,CharacterLogic.MaxStatValue,CharacterLogic.ZeroBonusValue);
        for (int i=0;i<6;i++) {
            numPickers[i].setDisplayedValues(_numPickerValues);
            numPickers[i].setMaxValue(_numPickerValues.length - 1);
            final TextView bonus = bonusDisplay[i];
            numPickers[i].setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    int realValue = Integer.parseInt(_numPickerValues[newVal]);
                    int result = CharacterLogic.getBonusFromStat(realValue);
                    if (result<0)
                        bonus.setText(" "+result);
                    else
                        bonus.setText(" +"+result);
                }
            });

        }
    }

    private String[] getDisplayValues(int minimumInclusive, int maximumInclusive,int initialValue) {

        ArrayList<String> result = new ArrayList<>();

        int value = initialValue+1;

        for(int i = minimumInclusive; i <= maximumInclusive; i++) {
            value-=1;
            if (value==-1) {
                value = maximumInclusive;
            }

            result.add(Integer.toString(value));
        }

        return result.toArray(new String[0]);
    }
}
