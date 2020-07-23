package com.ekproductions.healthlogger.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ekproductions.healthlogger.database.dao.DailyNoteDao;
import com.ekproductions.healthlogger.database.dao.ExerciseEntryDao;
import com.ekproductions.healthlogger.database.dao.FoodEntryDao;
import com.ekproductions.healthlogger.database.dao.PreviousExerciseEntryDao;
import com.ekproductions.healthlogger.database.dao.PreviousFoodEntryDao;
import com.ekproductions.healthlogger.database.tables.DailyNote;
import com.ekproductions.healthlogger.database.tables.ExerciseEntry;
import com.ekproductions.healthlogger.database.tables.FoodEntry;
import com.ekproductions.healthlogger.database.tables.PreviousExerciseEntry;
import com.ekproductions.healthlogger.database.tables.PreviousFoodEntry;
import com.ekproductions.healthlogger.database.tables.UserSetting;
import com.ekproductions.healthlogger.database.tables.WeightLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class HealthLoggerRepositoryTest {
    HealthLoggerRepository repository;
    HealthLoggerRoomDatabase db;

    @Before
    public void setUp() throws Exception {
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, HealthLoggerRoomDatabase.class).build();
        repository = new HealthLoggerRepository((Application) context);
        repository.deleteAllData();
    }

    @After
    public void tearDown() throws Exception {

        UserSetting settings = repository.getUserSettings();
        settings.setUserName("temp");
        repository.updateSettings(settings);
        repository.deleteAllData();
        db.close();
    }

    @Test
    public void getSettings() {
        UserSetting settings = repository.getUserSettings();
        assertEquals(settings.getUserName(), "temp");
    }

    @Test
    public void updateSettings() {
        UserSetting settings = repository.getUserSettings();
        settings.setUserName("Eric Santana");
        repository.updateSettings(settings);

        settings = repository.getUserSettings();
        assertEquals(settings.getUserName(), "Eric Santana");
    }

    @Test
    public void getWeightLogs() {
        List<WeightLog> logs = repository.getWeightLogs();
        //Should be empty at the beginning
        assertEquals(0,logs.size());

        //verify insert works
        WeightLog log = new WeightLog(150);
        repository.getWeightLogDao().insertLog(log);
        logs = repository.getWeightLogs();
        assertEquals(1, logs.size());


        //** Verify Weights are in order by date **/
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();
        WeightLog yesterdayLog = new WeightLog(200,yesterday);
        repository.getWeightLogDao().insertLog(yesterdayLog);
        logs = repository.getWeightLogs();
        WeightLog firstOnList = logs.get(0);
        assertEquals(200,(int)firstOnList.getWeight());

    }

    @Test
    public void getWeightLogsByDate() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();
        //Insert logs
        repository.getWeightLogDao().insertLog(new WeightLog(150,today));
        repository.getWeightLogDao().insertLog(new WeightLog(200,yesterday));

        //verify for today's entry
        List<WeightLog> logs = repository.getWeightLogsByDate(new Date());
        assertEquals(150,(int)logs.get(0).getWeight());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm a");

        //Log.d("Date Test today",DateFormat.getDateInstance().format(logs.get(0).getDate()));
        Log.d("Date Test today",formatter.format(logs.get(0).getDate()));



        //verify for yesterday's entry
        logs = repository.getWeightLogsByDate(yesterday);
        Log.d("Date Test yesterday",DateFormat.getDateInstance().format(logs.get(0).getDate()));
        assertEquals(200,(int)logs.get(0).getWeight());




    }

    @Test
    public void getFoodEntries() {
        FoodEntryDao dao = repository.getFoodEntryDao();

        //enter few food entries
        FoodEntry entry = new FoodEntry(new Date(),"Banana",100,"Snack",30,0,10);
        dao.insert(entry);

        entry.setDescription("Big Banana");
        entry.setCarbohydrates(60);
        entry.setCalories(200);

        dao.insert(entry);

        //test query

        List<FoodEntry> entries = dao.getEntries();
        assertEquals(2,entries.size());

    }

    @Test
    public void getFoodEntriesByDate() {
        FoodEntryDao dao = repository.getFoodEntryDao();

        //enter few food entries
        FoodEntry entry = new FoodEntry(new Date(),"Banana",100,"Snack",30,0,10);
        dao.insert(entry);

        //enter food entries from "Yesterday"
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();
        entry.setDate(yesterday);
          dao.insert(entry);

        entry.setDescription("Big Banana");
        Calendar timeQuery = Calendar.getInstance();
        timeQuery.setTime(yesterday);
        //add 3 hours for query
        //timeQuery.add(Calendar.HOUR,3);
        entry.setDate(timeQuery.getTime());
          dao.insert(entry);

        //timeQuery.add(Calendar.MINUTE, 30);
        entry.setDate(timeQuery.getTime());
         dao.insert(entry);

        //timeQuery.add(Calendar.HOUR, -1);


        //Validate Query from today
        List<FoodEntry> entries = dao.getEntryByDate(new Date()) ;
        assertEquals(1, entries.size());

        //validate Query from "Yesterday"
        entries = dao.getEntryByDate(timeQuery.getTime());
        assertEquals(3, entries.size());

    }

    @Test
    public void getFoodEntriesByDateAndType() {
        FoodEntryDao dao = repository.getFoodEntryDao();
        //enter few food entries for Today and "Snack" type (1)
        FoodEntry entry = new FoodEntry(new Date(),"Banana",100,"Snack",30,0,10);
        dao.insert(entry);

        //entries with Type "Lunch" and today  (2)
        entry.setEntryType("Lunch");
        dao.insert(entry);

        entry.setDescription("Juice");
        dao.insert(entry);

        //Entries with Type "Breakfast" and yesterday (3)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        entry.setEntryType("Breakfast");
        entry.setDate(calendar.getTime());
        dao.insert(entry);

        entry.setDescription("Bagel");
        dao.insert(entry);
        entry.setDescription("Cream Cheese");
        dao.insert(entry);


        //Validations
        //Today and Snack type
        List<FoodEntry> entries = dao.getEntryByDateAndType(new Date(), "Snack");
        assertEquals(1, entries.size());

        //Today and Lunch
        entries = dao.getEntryByDateAndType(new Date(), "Lunch");
        assertEquals(2, entries.size() );

        //Yesterday and Breakfast
        entries = dao.getEntryByDateAndType(calendar.getTime(), "Breakfast");
        assertEquals(3, entries.size() );

    }

    @Test
    public void insertPreviousFoodEntry() {
        PreviousFoodEntryDao dao = repository.getPreviousFoodEntryDao();
        //Create 11 Food Entries that are different.
        ArrayList<FoodEntry> entries = new ArrayList<FoodEntry>();
        FoodEntry entry;
        for(int i =0; i<11; i++){
            entry = new FoodEntry(new Date(),"Food #" +(i+1),100,"Test", 100,100,100);
            entries.add(entry);
        }

        //Test same entry. Only one of entry in table
        repository.insertPreviousFoodEntry( entries.get(0)); //Food #1
        repository.insertPreviousFoodEntry( entries.get(0)); //Food #1
        List<PreviousFoodEntry> previousFoodEntries = dao.getEntries();
        assertEquals(1,previousFoodEntries.size());

        //Insert 11 entries. The table should only have 10 records storing Food #11 and Food #2 through 10
        for(int i =0; i<11; i++){
            repository.insertPreviousFoodEntry(entries.get(i));
        }
        //validate table size
        previousFoodEntries = dao.getEntries();
        assertEquals(10, previousFoodEntries.size());

        //First record on list should be Food#11 and order 1
        assertEquals("Food #11", previousFoodEntries.get(0).getDescription());
        assertEquals(1, previousFoodEntries.get(0).getOrder());

        //Last entry on list should be Food#2 and order 10
        assertEquals("Food #2", previousFoodEntries.get(9).getDescription());
        assertEquals(10, previousFoodEntries.get(9).getOrder());

        //Validate all records on the list
        for (int i = 0, i2 = 11 ; i<previousFoodEntries.size(); i++, i2 --){
            assertEquals("Food #"+(i2), previousFoodEntries.get(i).getDescription());
            assertEquals((i+1), previousFoodEntries.get(i).getOrder());
        }

    }

    @Test
    public void getExerciseEntriesByDate() {
         ExerciseEntryDao dao = repository.getExerciseEntryDao();

        //enter few Exercise entries
        ExerciseEntry entry = new ExerciseEntry(new Date(),"Running",100,"Cardio",30);
        dao.insert(entry);

        //enter Exercise entries from "Yesterday"
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();
        entry.setDate(yesterday);
        dao.insert(entry);

        entry.setDescription("Walking");
        Calendar timeQuery = Calendar.getInstance();
        timeQuery.setTime(yesterday);
        //add 3 hours for query
        //timeQuery.add(Calendar.HOUR,3);
        entry.setDate(timeQuery.getTime());
        dao.insert(entry);

        //timeQuery.add(Calendar.MINUTE, 30);
        entry.setDate(timeQuery.getTime());
        dao.insert(entry);

        //timeQuery.add(Calendar.HOUR, -1);


        //Validate Query from today
        List<ExerciseEntry> entries = dao.getEntryByDate(new Date()) ;
        assertEquals(1, entries.size());

        //validate Query from "Yesterday"
        entries = dao.getEntryByDate(timeQuery.getTime());
        assertEquals(3, entries.size());
    }

    @Test
    public void getExerciseEntriesByDateAndType() {
        ExerciseEntryDao dao = repository.getExerciseEntryDao();
        //enter few Exercise entries for Today and "Snack" type (1)
        ExerciseEntry entry = new ExerciseEntry(new Date(),"Running",100,"Cardio",30);
        dao.insert(entry);

        //entries with Type "Muscular" and today  (2)
        entry.setEntryType("Muscular");
        dao.insert(entry);

        entry.setDescription("Weight Lifting");
        dao.insert(entry);

        //Entries with Type "Other" and yesterday (3)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        entry.setEntryType("Other");
        entry.setDate(calendar.getTime());
        dao.insert(entry);

        entry.setDescription("Yoga");
        dao.insert(entry);
        entry.setDescription("Stretching");
        dao.insert(entry);


        //Validations
        //Today and CArdio type
        List<ExerciseEntry> entries = dao.getEntryByDateAndType(new Date(), "Cardio");
        assertEquals(1, entries.size());

        //Today and muscular
        entries = dao.getEntryByDateAndType(new Date(), "Muscular");
        assertEquals(2, entries.size() );

        //Yesterday and Other
        entries = dao.getEntryByDateAndType(calendar.getTime(), "Other");
        assertEquals(3, entries.size() );
    }

    @Test
    public void insertPreviousExerciseEntry() {
        PreviousExerciseEntryDao dao = repository.getPreviousExerciseEntryDao();
        //Create 11 Food Entries that are different.
        ArrayList<ExerciseEntry> entries = new ArrayList<ExerciseEntry>();
        ExerciseEntry entry;
        for(int i =0; i<11; i++){
            entry = new ExerciseEntry(new Date(),"Exercise #" +(i+1),100,"Test", 100);
            entries.add(entry);
        }

        //Test same entry. Only one of entry in table
        repository.insertPreviousExerciseEntry( entries.get(0)); //Exercise #1
        repository.insertPreviousExerciseEntry( entries.get(0)); //Exercise #1
        List<PreviousExerciseEntry> previousExerciseEntries = dao.getEntries();
        assertEquals(1,previousExerciseEntries.size());

        //Insert 11 entries. The table should only have 10 records storing Exercise #11 and Exercise #2 through 10
        for(int i =0; i<11; i++){
            repository.insertPreviousExerciseEntry(entries.get(i));
        }
        //validate table size
        previousExerciseEntries = dao.getEntries();
        assertEquals(10, previousExerciseEntries.size());

        //First record on list should be Exercise#11 and order 1
        assertEquals("Exercise #11", previousExerciseEntries.get(0).getDescription());
        assertEquals(1, previousExerciseEntries.get(0).getOrder());

        //Last entry on list should be Exercise#2 and order 10
        assertEquals("Exercise #2", previousExerciseEntries.get(9).getDescription());
        assertEquals(10, previousExerciseEntries.get(9).getOrder());

        //Validate all records on the list
        for (int i = 0, i2 = 11 ; i<previousExerciseEntries.size(); i++, i2 --){
            assertEquals("Exercise #"+(i2), previousExerciseEntries.get(i).getDescription());
            assertEquals((i+1), previousExerciseEntries.get(i).getOrder());
        }

    }

    @Test
    public void getDailyNotesByDate() {
        DailyNoteDao dao = repository.getDailyNoteDao();

        //enter few DailyNote entries
        DailyNote entry = new DailyNote(new Date(), "Note Type Test", "notes");
        dao.insert(entry);

        //enter DailyNote from "Yesterday"
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();
        entry.setDate(yesterday);
        dao.insert(entry);

        entry.setNote("notes 2");
        Calendar timeQuery = Calendar.getInstance();
        timeQuery.setTime(yesterday);
        //add 3 hours for query
        //timeQuery.add(Calendar.HOUR,3);
        entry.setDate(timeQuery.getTime());
        dao.insert(entry);

        //timeQuery.add(Calendar.MINUTE, 30);
        entry.setDate(timeQuery.getTime());
        dao.insert(entry);

        //timeQuery.add(Calendar.HOUR, -1);


        //Validate Query from today
        List<DailyNote> entries = dao.getNotesByDate(new Date()) ;
        assertEquals(1, entries.size());

        //validate Query from "Yesterday"
        entries = dao.getNotesByDate(timeQuery.getTime());
        assertEquals(3, entries.size());
    }

}