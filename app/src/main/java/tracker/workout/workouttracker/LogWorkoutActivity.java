package tracker.workout.workouttracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tracker.workout.workouttracker.database.Database;
import tracker.workout.workouttracker.database.DatabaseAlreadyClosedException;
import tracker.workout.workouttracker.database.DatabaseAlreadyOpenException;
import tracker.workout.workouttracker.database.table.Category;
import tracker.workout.workouttracker.database.table.Exercise;

public class LogWorkoutActivity extends AppCompatActivity {

    private Database database;
    private ListView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);
        gridView = (ListView) findViewById(R.id.workoutListView);

        database = new Database(this);

        try {
            database.open();
        } catch (DatabaseAlreadyOpenException e) {
            e.printStackTrace();
        }

        String[] workoutNames = database.getWorkouts();



        ArrayAdapter<Category> adapter = new ArrayAdapter(this, R.layout.list_item, workoutNames);
        gridView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            database.close();
        } catch (DatabaseAlreadyClosedException e) {
            e.printStackTrace();
        }
    }
}
