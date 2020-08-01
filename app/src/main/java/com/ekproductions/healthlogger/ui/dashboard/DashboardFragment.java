package com.ekproductions.healthlogger.ui.dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekproductions.healthlogger.R;
import com.ekproductions.healthlogger.database.HealthLoggerViewModel;
import com.ekproductions.healthlogger.database.tables.FoodEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

   // private DashboardViewModel dashboardViewModel;

    private HealthLoggerViewModelCopy dashboardViewModel;
    private int totalBreakFastCalories;
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
                updateTotal(root, adapter.getTotalBreakFastCalories());
            }
        });



        Button breakFastButton = root.findViewById(R.id.button11);
        breakFastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.insertFoodEntry(myCalendar);

            }
        });

        TextView dayTextView = root.findViewById(R.id.dayText);
        dayTextView.setText("Today");

        dayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                updateDataBaseQueryDate(getActivity(),adapter,dashboardViewModel,root);
                                String myFormat = "MM/dd/yy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                dayTextView.setText(sdf.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();



            }
        });


        //Next and Prevoius on click actions

        TextView next = root.findViewById(R.id.textView12);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.DATE, 1);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dayTextView.setText(sdf.format(myCalendar.getTime()));

                updateDataBaseQueryDate(getActivity(),adapter,dashboardViewModel,root);
            }
        });

        TextView prev = root.findViewById(R.id.textView11);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.DATE, -1);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dayTextView.setText(sdf.format(myCalendar.getTime()));

                updateDataBaseQueryDate(getActivity(),adapter,dashboardViewModel,root);
            }
        });

        return root;
    }

    void updateDataBaseQueryDate(Activity activity, FoodListAdapter adapter, HealthLoggerViewModelCopy model, View view){
        model.getBreakFastFoodEntries().removeObservers((LifecycleOwner) activity);
        model.updateByDate(myCalendar).observe((LifecycleOwner) activity, new Observer<List<FoodEntry>>() {
            @Override
            public void onChanged(List<FoodEntry> foodEntries) {
                adapter.setFoodEntries(foodEntries);
                updateTotal(view, adapter.getTotalBreakFastCalories());
            }
        });
    }

    final Calendar myCalendar = Calendar.getInstance();

    private void updateTotal(View root, int totalCalories) {
        TextView total = root.findViewById(R.id.textView9);
        total.setText("Total Calories: "+ totalCalories);
    }


    public void insertFood(View v){
        dashboardViewModel.insertFoodEntry(myCalendar);
    }
}