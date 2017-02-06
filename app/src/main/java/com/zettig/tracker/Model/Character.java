package com.zettig.tracker.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Altair on 06.02.2017.
 */

@Table(name = "character")
public class Character extends Model{
    @Column(name = "name")
    private String name;
    @Column(name = "initiative")
    private int initiative;
    @Column(name = "turn")
    private boolean turn;

    public static List<Character> getAll(){
        return new Select().from(Character.class).execute();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public boolean isTurn() {return turn;}

    public void setTurn(boolean turn) {this.turn = turn;}
}
