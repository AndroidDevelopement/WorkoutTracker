package tracker.workout.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tracker.workout.workouttracker.database.table.Category;
import tracker.workout.workouttracker.database.table.Exercise;

import static tracker.workout.workouttracker.R.array.exercises;

/**
 * Created by Mantas on 29/10/2017.
 */

public class ExercisesForCategory extends AppCompatActivity {

    private ListView exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_for_category);
        exerciseList = (ListView) findViewById(R.id.listExercisesForCategory);
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Exercise[] exercises = (Exercise[]) extras.get("exercises");
            ArrayAdapter<Exercise> adapter = new ArrayAdapter(this, R.layout.list_item, exercises);
            exerciseList.setAdapter(adapter);
        }

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Code for adding exercises to workout
                System.out.println(adapterView.getItemAtPosition(i));
                ArrayList<Exercise> workoutExercises = (ArrayList<Exercise>) extras.get("workoutExercises");
                workoutExercises.add((Exercise) adapterView.getItemAtPosition(i));
                Intent intent = new Intent(ExercisesForCategory.this, CreateWorkout.class);
                intent.putExtra("workoutExercises", workoutExercises);
                startActivity(intent);
            }
        });
    }
}
