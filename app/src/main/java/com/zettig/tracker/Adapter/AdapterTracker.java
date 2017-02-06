package com.zettig.tracker.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zettig.tracker.Model.Character;
import com.zettig.tracker.R;

import java.util.List;

/**
 * Created by Altair on 06.02.2017.
 */

public class AdapterTracker extends RecyclerView.Adapter<AdapterTracker.ViewHolder> {

    List<Character> list;

    public AdapterTracker(List<Character> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item,parent,false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView initiative;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.character_name);
            initiative = (TextView)itemView.findViewById(R.id.character_initiative);
        }
    }
}
