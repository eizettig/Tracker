package com.zettig.tracker.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zettig.tracker.Model.Character;
import com.zettig.tracker.R;
import com.zettig.tracker.Utils.ApplicationManager;
import com.zettig.tracker.Utils.ManagerAlertDialog;

/**
 * Created by Altair on 06.02.2017.
 */

public class FragmentCharacterEdit extends Fragment {

    TextInputEditText name;
    TextInputEditText initiative;
    Character character = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_edit,container,false);
        name = (TextInputEditText) view.findViewById(R.id.edit_name);
        initiative = (TextInputEditText) view.findViewById(R.id.edit_initiative);

        if (getArguments() != null){
            long id = getArguments().getLong("id");
            character = Character.findById(id);
            if (character != null) {
                name.setText(character.getName());
                initiative.setText(String.valueOf(character.getInitiative()));
            }
        }

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.character_edit,menu);
        if (character == null){
            menu.findItem(R.id.delete).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                ManagerAlertDialog.showDialog(getActivity(),R.string.question_delete_character,R.string.yes,R.string.no,onDelete,onDismisDelete);
                break;
            case R.id.done:
                if (character == null) {
                    addNewCharacter();
                } else {
                    editCharacter();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editCharacter() {
        if (checkData()){
            character.setName(name.getText().toString().trim());
            character.setInitiative(Integer.parseInt(initiative.getText().toString().trim()));
            if (character.save() > 0){
                getActivity().onBackPressed();
            } else {
                ApplicationManager.getInstance().showToast(R.string.error_insert_character);
            }
        }
    }

    private void addNewCharacter() {
        if (checkData()){
            Character c = new Character();
            c.setName(name.getText().toString().trim());
            c.setInitiative(Integer.parseInt(initiative.getText().toString().trim()));
            if (c.save() > 0){
                getActivity().onBackPressed();
            } else {
                ApplicationManager.getInstance().showToast(R.string.error_insert_character);
            }
        }
    }

    private boolean checkData(){
        String n = name.getText().toString().trim();
        String i = initiative.getText().toString().trim();
        if (TextUtils.isEmpty(n)){
            ApplicationManager.getInstance().showToast(R.string.error_no_name);
            return false;
        }
        if (TextUtils.isEmpty(i)){
            ApplicationManager.getInstance().showToast(R.string.error_no_initiative);
            return false;
        }  else {
            return true;
        }
    }

    private DialogInterface.OnClickListener onDelete = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            character.delete();
            getActivity().onBackPressed();
        }
    };

    private DialogInterface.OnClickListener onDismisDelete = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

}
