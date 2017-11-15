package tracker.workout.workouttracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tracker.workout.workouttracker.DatabaseHelper;
import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Workout;
import tracker.workout.workouttracker.dataContainers.Log;

import tracker.workout.workouttracker.dataContainers.WorkoutExercise;

public class WorkoutDiaryActivity extends AppCompatActivity {

    private ListView gridView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_diary);
        gridView = (ListView) findViewById(R.id.workoutDiaryListView);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        Log[] loggedWorkouts = databaseHelper.getLoggedWorkouts();
        Log loggedWorkout= databaseHelper.getLoggedWorkout(0);
        System.out.println("Logged Workouts Length:"+loggedWorkouts.length+" id:"+loggedWorkouts[0].getId());
        Workout workout = loggedWorkouts[0].getWorkout();
        WorkoutExercise[] workoutEx = workout.getExercises();
        System.out.println("Workouts Length:"+workoutEx.length);
        System.out.println("Workout Length:"+loggedWorkout.getWorkout().getExercises().length);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, loggedWorkouts);
        gridView.setAdapter(adapter);
    }
}
