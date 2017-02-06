package com.zettig.tracker.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private List<Character> list;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public AdapterTracker(AdapterView.OnItemClickListener onItemClickListener, AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    private void updateData(){
        Collections.sort(list,new CharacterComporator());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView name;
        TextView initiative;
        RelativeLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.character_name);
            initiative = (TextView)itemView.findViewById(R.id.character_initiative);
            container = (RelativeLayout) itemView.findViewById(R.id.character_container);
            container.setOnClickListener(this);
            container.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(null,v,getAdapterPosition(),v.getId());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClick(null,v,getAdapterPosition(),v.getId());
            Log.d("TAG","long click to: " + getAdapterPosition());
            return true;
        }
    }
}
