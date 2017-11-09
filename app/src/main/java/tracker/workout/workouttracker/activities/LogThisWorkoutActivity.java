package tracker.workout.workouttracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tracker.workout.workouttracker.R;
import tracker.workout.workouttracker.dataContainers.Workout;
import tracker.workout.workouttracker.dataContainers.WorkoutExercise;

public class LogThisWorkoutActivity extends AppCompatActivity {

    private ListView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_this_workout);
        gridView = (ListView) findViewById(R.id.logThisWorkoutListView);
        final Bundle extras = getIntent().getExtras();

        Workout workout = null;
        WorkoutExercise[] workoutExercises = {};

        if (extras != null) {
			workout = (Workout) extras.getSerializable("workout");
        	workoutExercises = workout.getExercises();
		}

        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, workoutExercises);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Need to add logged workout to Database here
            }
        });
    }

}
