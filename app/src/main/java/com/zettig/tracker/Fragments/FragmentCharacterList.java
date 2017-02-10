package com.zettig.tracker.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.activeandroid.util.Log;
import com.zettig.tracker.ActivityMain;
import com.zettig.tracker.Adapter.AdapterTracker;
import com.zettig.tracker.CallbackActivity;
import com.zettig.tracker.Model.Character;
import com.zettig.tracker.R;
import com.zettig.tracker.Utils.ApplicationManager;
import com.zettig.tracker.Utils.CharacterComporator;
import com.zettig.tracker.Utils.ManagerAlertDialog;
import com.zettig.tracker.Utils.SharedKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Altair on 06.02.2017.
 */

public class FragmentCharacterList extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "TAG";

    CallbackActivity callback;
    RecyclerView recyclerView;
    AdapterTracker adapter;
    FloatingActionButton fab;
    List<Character> list = new ArrayList<>();
    int round;

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
        adapter = new AdapterTracker(this);
        recyclerView.setAdapter(adapter );

        list = Character.getAll();
        Collections.sort(list, new CharacterComporator());
        adapter.setList(list);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        callback.hideKeyboard();
        round = ApplicationManager.getInstance().getSharedManager().getValueInteger(SharedKeys.ROUND);
        if (round != 0){
            callback.setTitle("Раунд: " + round);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ApplicationManager.getInstance().getSharedManager().putKeyInteger(SharedKeys.ROUND,round);
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
                restartGame();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupMenu(View view,int position) {
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.item_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        if (list.get(position).isSkip()){
            popup.getMenu().getItem(2).setChecked(true);
        }
        popup.show();
    }
    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_edit:
                    Fragment fragment = new FragmentCharacterEdit();
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",list.get(position).getId());
                    fragment.setArguments(bundle);
                    callback.replaceFragment(fragment,true);
                    return true;
                case R.id.menu_delete:
                    ManagerAlertDialog.showDialog(getActivity(),R.string.question_delete_character,
                            R.string.yes,R.string.no,onDelete,onDismisDelete);
                    adapter.updateList();
                    return true;
                case R.id.menu_skip:
                    list.get(position).setSkip(!menuItem.isChecked());
                    list.get(position).save();
                    menuItem.setChecked(!menuItem.isChecked());
                    adapter.updateList();
                    return true;
                default:
            }
            return true;
        }
        private DialogInterface.OnClickListener onDelete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!list.get(position).isTurn()) {
                    list.get(position).delete();
                    list.remove(position);
                    adapter.updateList();
                } else {
                    ApplicationManager.getInstance().showToast(R.string.error_delete);
                }
            }
        };

        private DialogInterface.OnClickListener onDismisDelete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
    }

    //////////////////
    //
    // CLICKLISTENERS
    //
    //////////////////

    private View.OnClickListener onFabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (list.size() > 1) {
                turnNext();
            } else {
                ApplicationManager.getInstance().showToast(R.string.error_few_character);
            }
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showPopupMenu(view,position);
    }


    //////////////////
    //
    // TRACKER METHOD
    //
    //////////////////

    private void turnNext(){
        Character c = Character.getTurned();
        if (c != null){
            int current = list.indexOf(c);
            int next = current + 1;
            c.setTurn(false);
            c.save();
            if (current != list.size() - 1) {
                list.get(next).setTurn(true);
                list.get(next).save();
                recyclerView.smoothScrollToPosition(next);
            } else {
                turnFirst();
            }
        } else {
            turnFirst();
        }
        if (list.get(list.indexOf(Character.getTurned())).isSkip()){
            turnNext();
        }
        adapter.updateList();
    }

    private void turnFirst(){
        Character c = list.get(0);
        c.setTurn(true);
        c.save();
        recyclerView.smoothScrollToPosition(0);
        round++;
        callback.setTitle("Раунд " + round);
        adapter.updateList();
    }

    private void restartGame(){
        Character.clearTurn();
        callback.setTitle("");
        round = 0;
        ApplicationManager.getInstance().getSharedManager().putKeyInteger(SharedKeys.ROUND,0);
        adapter.updateList();
    }
}
