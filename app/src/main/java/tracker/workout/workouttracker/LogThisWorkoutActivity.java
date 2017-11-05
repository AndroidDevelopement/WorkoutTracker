package tracker.workout.workouttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogThisWorkoutActivity extends AppCompatActivity {

    private ListView gridView;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_this_workout);
        gridView = (ListView) findViewById(R.id.logThisWorkoutListView);
        final Bundle extras = getIntent().getExtras();
        String[] exercises = {};

        if (extras != null) {
            exercises = (String[]) extras.get("exercises");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, exercises);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Need to add logged workout to Database here
            }
        });
    }
}
