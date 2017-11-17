package tracker.workout.workouttracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Workout;
import tracker.workout.workouttracker.dataContainers.WorkoutExercise;

public class ThisWorkoutDiaryActivity extends AppCompatActivity {

    private ListView gridView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_this_workout_diary);
        gridView = (ListView) findViewById(R.id.thisWorkoutDiaryListView);
        textView = (TextView) findViewById(R.id.workoutNameTextView);

        final Bundle extras = getIntent().getExtras();
        final Workout workout;

        if (extras != null) {
            workout = (Workout) extras.getSerializable("workout");
            textView.setText(workout.toString());

            WorkoutExercise[] workoutExercises = workout.getExercises();
            String[] exerciseDescriptions = new String[workoutExercises.length];

            for(int i=0; i<workoutExercises.length; i++) {
                exerciseDescriptions[i] = workoutExercises[i].toSetsRepsString();
            }

            ArrayAdapter<WorkoutExercise> adapter = new ArrayAdapter(this, R.layout.list_item, exerciseDescriptions);
            gridView.setAdapter(adapter);

        }
    }
}
