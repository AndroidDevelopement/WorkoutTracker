package tracker.workout.workouttracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import tracker.workout.workouttracker.database.Database;
import tracker.workout.workouttracker.database.DatabaseAlreadyClosedException;
import tracker.workout.workouttracker.database.DatabaseAlreadyOpenException;
import tracker.workout.workouttracker.database.table.Category;

public class CreateWorkoutActivity extends AppCompatActivity {

	private Database database;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_workout);
        gridView = (GridView) findViewById(R.id.categoryGridView);

		database = new Database(this);

		try {
			database.open();
		} catch (DatabaseAlreadyOpenException e) {
			e.printStackTrace();
		}

        ArrayAdapter<Category> adapter = new ArrayAdapter(this, R.layout.list_item, database.getCategories());
        gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category category = (Category) parent.getItemAtPosition(position);
				// OPEN CATEGORY EXERCISES LIST
				// database.getExercises(category);
				// Mantas
			}
		});
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