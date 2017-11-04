package tracker.workout.workouttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Arrays;


public class LogWorkoutActivity extends AppCompatActivity {

    private ListView gridView;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);
        gridView = (ListView) findViewById(R.id.workoutListView);
        myDb = new DatabaseHelper(this);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, myDb.getWorkoutNames());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                System.out.println("\n\n\n\n\n\n");
                System.out.println(category);
                String[] exercises = myDb.getWorkoutExercises(category);
                System.out.println(Arrays.toString(exercises));
                Intent intent = new Intent(LogWorkoutActivity.this, LogThisWorkout.class);
                intent.putExtra("exercises", exercises);
                startActivity(intent);


//                final Bundle extras = getIntent().getExtras();
//                ArrayList<Exercise> workoutExercises = (ArrayList<Exercise>) extras.get("workoutExercises");
//                intent.putExtra("workoutExercises", workoutExercises);
//                startActivity(intent);
            }
        });
    }
}
