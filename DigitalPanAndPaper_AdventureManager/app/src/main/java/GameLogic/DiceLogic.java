package GameLogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by BlackDragon on 31/03/2016.
 */
public class DiceLogic {

    public static ArrayList<Integer> rollDice(int diceType,int numberOfRolls){
        Random random=new Random();
        ArrayList<Integer> result=new ArrayList();
        for (int i=0;i<numberOfRolls;i++) {
            result.add(random.nextInt(diceType)+1);
        }
        return result;
    }

    public static int getHighRoll(int diceType,int numberOfRolls,int modifier){
        ArrayList<Integer> rolls;
        rolls=rollDice(diceType,numberOfRolls);
        int highRoll=rolls.get(0);
        for (int roll : rolls){
            if (roll>highRoll)
                highRoll=roll;
        }
        return highRoll+modifier;
    }



}
