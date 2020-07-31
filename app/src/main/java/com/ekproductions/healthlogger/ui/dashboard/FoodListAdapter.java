package com.ekproductions.healthlogger.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekproductions.healthlogger.R;
import com.ekproductions.healthlogger.database.tables.FoodEntry;

import org.w3c.dom.Text;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    class FoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView foodNameText;
        private final  TextView caloriesText;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameText = itemView.findViewById(R.id.foodName);
            caloriesText = itemView.findViewById(R.id.calories);
        }
    }


    private final LayoutInflater inflater;
    List<FoodEntry> items;

    public FoodListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.food_item,parent,false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if(items!=null){
            FoodEntry current = items.get(position);
            holder.foodNameText.setText(current.getDescription());
            holder.caloriesText.setText(Integer.toString( current.getCalories()));


        }
        else{
            holder.foodNameText.setText("No Food Entries");
        }

    }

    void setFoodEntries (List<FoodEntry> entries){
        items = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.size();
        else
            return 0;
    }


}
