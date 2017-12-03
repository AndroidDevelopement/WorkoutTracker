/*
 *  CustomAdapter - A custom adapter made to display edit and delete
 *  buttons on particular lists.
 */

package tracker.workout.workouttracker;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import tracker.workout.workouttracker.dataContainers.Log;
import tracker.workout.workouttracker.dataContainers.Workout;

/**
 * Created by Mantas on 24/11/2017.
 */

public class CustomAdapter<T> extends BaseAdapter implements ListAdapter {
    private ArrayList<T> list;
    private Context context;
    private int layoutItem;

    public CustomAdapter(Context context, int layoutItem, T[] list) {
        this.list = new ArrayList<>(Arrays.asList(list));
        this.context = context;
        this.layoutItem = layoutItem;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutItem, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.row_text_view);
        listItemText.setText(list.get(position).toString());

        Button deleteBtn = (Button)view.findViewById(R.id.row_delete);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                // Delete data from database
                Object object = list.get(position);
                list.remove(position);
                notifyDataSetChanged();

                if (object instanceof Log) {
                    final Log log = (Log) object;
                    new AsyncTask() {
                        @Override
                        protected Void doInBackground(Object... objects) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(v.getContext());
                            databaseHelper.deleteLoggedWorkout(log.getId());
                            return null;
                        }
                    }.execute();
                } else if (object instanceof Workout) {
                    final Workout workout = (Workout) object;
                    new AsyncTask() {
                        @Override
                        protected Void doInBackground(Object... objects) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(v.getContext());
                            databaseHelper.deleteWorkout(workout.getId());
                            return null;
                        }
                    }.execute();
                }
            }
        });

        return view;
    }
}
