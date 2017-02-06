package com.zettig.tracker.Fragments;

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
import com.zettig.tracker.Utils.CharacterComporator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Altair on 06.02.2017.
 */

public class FragmentCharacterList extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    CallbackActivity callback;
    RecyclerView recyclerView;
    AdapterTracker adapter;
    FloatingActionButton fab;
    List<Character> list = new ArrayList<>();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.character_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add){
            callback.replaceFragment(new FragmentCharacterEdit(),true);
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
        callback.replaceFragment(new FragmentCharacterEdit(),true);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        callback.replaceFragment(new FragmentCharacterEdit(),true);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TAG","long click to: " + position);
        return true;
    }
}
