package tracker.workout.workouttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class LogWorkoutActivity extends AppCompatActivity {

    private ListView gridView;
    private Button emptyButton;
    private TextView emptyText;
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
                String[] exercises = myDb.getWorkoutExercises(category);
                Intent intent = new Intent(LogWorkoutActivity.this, LogThisWorkout.class);
                intent.putExtra("exercises", exercises);
                startActivity(intent);
            }
        });

        emptyButton=(Button)findViewById(R.id.emptyCreateWorkoutButton);
        gridView.setEmptyView(emptyButton);
        emptyText=(TextView)findViewById(R.id.emptyText);
        gridView.setEmptyView(emptyText);

        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivity = new Intent(LogWorkoutActivity.this, CreateWorkoutActivity.class);
                startActivity(addActivity);
            }
        });


    }
}
