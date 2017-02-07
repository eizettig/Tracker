package com.zettig.tracker.Utils;

import com.zettig.tracker.Model.Character;

import java.util.Comparator;

/**
 * Created by Altair on 06.02.2017.
 */

public class CharacterComporator implements Comparator<Character> {
    @Override
    public int compare(Character o1, Character o2) {
        if (o1.getInitiative() < o2.getInitiative()){
            return 1;
        } else if (o1.getInitiative() > o2.getInitiative()){
            return -1;
        } else {
            return 0;
        }
    }
}
