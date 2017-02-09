package com.zettig.tracker.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.zettig.tracker.Model.Character;
import com.zettig.tracker.R;
import com.zettig.tracker.Utils.CharacterComporator;

import java.util.Collections;
import java.util.List;

/**
 * Created by Altair on 06.02.2017.
 */

public class AdapterTracker extends RecyclerView.Adapter<AdapterTracker.ViewHolder> {

    private final int TURN = 0;
    private final int SKIP = 1;
    private final int BASE = 2;

    private List<Character> list;
    private AdapterView.OnItemClickListener onItemClickListener;

    public AdapterTracker(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isSkip()){
            return SKIP;
        } else if(list.get(position).isTurn()){
            return TURN;
        } else {
            return BASE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item,parent,false);
        switch (viewType){
            case BASE:
                view.setBackgroundColor(Color.WHITE);
                break;
            case TURN:
                view.setBackgroundColor(Color.RED);
                break;
            case SKIP:
                view.setBackgroundColor(Color.LTGRAY);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.initiative.setText(String.valueOf(list.get(position).getInitiative()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Character> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void updateList(){
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView initiative;
        ImageView menu;
        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.character_name);
            initiative = (TextView)itemView.findViewById(R.id.character_initiative);
            menu =(ImageView) itemView.findViewById(R.id.item_menu);
            menu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(null,v,getAdapterPosition(),v.getId());
        }
    }
}
