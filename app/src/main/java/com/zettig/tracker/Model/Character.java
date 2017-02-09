package com.zettig.tracker.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

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
    @Column(name = "skip")
    private boolean skip;

    public static Character findById(long id){
        return new Select().from(Character.class).where("id = ?",id).executeSingle();
    }
    public static List<Character> getAll(){
        return new Select().from(Character.class).execute();
    }

    public static Character getTurned(){
        return new Select().from(Character.class).where("turn = ?",true).executeSingle();
    }

    public static void clearTurn(){
        new Update(Character.class).set("turn = ?" ,false).execute();
        new Update(Character.class).set("skip = ?" ,false).execute();
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

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
