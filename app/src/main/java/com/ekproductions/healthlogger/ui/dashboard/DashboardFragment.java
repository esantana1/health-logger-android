package com.ekproductions.healthlogger.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekproductions.healthlogger.R;
import com.ekproductions.healthlogger.database.HealthLoggerViewModel;
import com.ekproductions.healthlogger.database.tables.FoodEntry;

import java.util.List;

public class DashboardFragment extends Fragment {

   // private DashboardViewModel dashboardViewModel;

    private HealthLoggerViewModelCopy dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Eric's Try & Catch
        //TODO: Bug - for some reason, the ViewModelProvider doesnt work on the Viewmodel
//        try {
//            dashboardViewModel =
//                    new ViewModelProvider(this).get(HealthLoggerViewModelCopy.class);
//        }
//        catch (Exception e){
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage(e.getMessage());
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
        dashboardViewModel =
                    new ViewModelProvider(this).get(HealthLoggerViewModelCopy.class);

        // Orignal Generated code that works
        //dashboardViewModel =
          //      new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //Eric's added code
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText("This is the Dashboard -(Eric)");
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        final FoodListAdapter adapter = new FoodListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dashboardViewModel.getBreakFastFoodEntries().observe(getActivity(), new Observer<List<FoodEntry>>() {
            @Override
            public void onChanged(List<FoodEntry> foodEntries) {
                adapter.setFoodEntries(foodEntries);
            }
        });

        Button breakFastButton = root.findViewById(R.id.button11);
        breakFastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.insertFoodEntry();
            }
        });


        return root;
    }




    public void insertFood(View v){
        dashboardViewModel.insertFoodEntry();
    }
}