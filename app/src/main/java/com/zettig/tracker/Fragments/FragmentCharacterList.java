package com.zettig.tracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.activeandroid.util.Log;
import com.zettig.tracker.ActivityMain;
import com.zettig.tracker.Adapter.AdapterTracker;
import com.zettig.tracker.CallbackActivity;
import com.zettig.tracker.Model.Character;
import com.zettig.tracker.R;
import com.zettig.tracker.Utils.ApplicationManager;
import com.zettig.tracker.Utils.CharacterComporator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Altair on 06.02.2017.
 */

public class FragmentCharacterList extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private static final String TAG = "TAG";
    private static final String TURN = "TURN";
    CallbackActivity callback;
    RecyclerView recyclerView;
    AdapterTracker adapter;
    FloatingActionButton fab;
    List<Character> list = new ArrayList<>();
    private int round = 0;
    private int turn = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        callback = (ActivityMain)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_list,container,false);
        setHasOptionsMenu(true);

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(onFabClick);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterTracker(this,this);
        recyclerView.setAdapter(adapter );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list = Character.getAll();
        Collections.sort(list, new CharacterComporator());
        adapter.setList(list);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //////////////////
    //
    // MENU
    //
    //////////////////

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.character_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                callback.replaceFragment(new FragmentCharacterEdit(),true);
                break;
            case R.id.restart_game:
                clearTurned();
                turn = -1;
                round = 0;
                callback.setTitle("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //////////////////
    //
    // CLICKLISTENERS
    //
    //////////////////

    private View.OnClickListener onFabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (list.size()<2){
                ApplicationManager.getInstance().showToast(R.string.error_few_character);
            } else {
                android.util.Log.d(TAG, "onClick: " + turn);
                if (turn == -1){
                    turnFirst();
                } else {
                    turnNext();
                }
            }
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = new FragmentCharacterEdit();
        Bundle bundle = new Bundle();
        bundle.putLong("id",list.get(position).getId());
        fragment.setArguments(bundle);
        callback.replaceFragment(fragment,true);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TAG","long click to: " + position);
        return true;
    }

    //////////////////
    //
    // TRACKER METHOD
    //
    //////////////////


    private void turnFirst(){
        round++;
        callback.setTitle("Раунд: " + round);
        turn = 0;
        setTurned(turn);
    }

    private void turnNext(){
        if (turn != recyclerView.getChildCount()-1) {
            turn++;
            setTurned(turn);
        } else {
            turnFirst();
        }
    }
    private void setTurned(int position){
        clearTurned();
        recyclerView.getChildAt(position).setBackgroundColor(Color.RED);
    }

    private void clearTurned(){
        for (int i=0;i<recyclerView.getChildCount();i++){
            recyclerView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
    }


}
