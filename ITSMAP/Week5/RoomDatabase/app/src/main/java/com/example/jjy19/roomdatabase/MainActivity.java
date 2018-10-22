package com.example.jjy19.roomdatabase;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button addButton;
    EditText editTaskName;
    EditText editTaskPlace;
    private List<Task> tasks;
    TaskDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addBtn);
        editTaskName = findViewById(R.id.editTask);
        editTaskPlace = findViewById(R.id.editPlace);

        //load tasks from database
        // tasks = loadTasks();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskPressed();
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class, "Task-database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        tasks = db.taskDao().getAll();


    }

    private void addTaskPressed(){
        String tempTask = editTaskName.getText().toString();
        String tempPlace = editTaskPlace.getText().toString();
        final Task t = new Task(tempTask, tempPlace);

//        t.setTaskName(tempTask);
//        t.setTaskPlace(tempPlace);

        tasks.add(t);
        db.taskDao().insertAll(t);
    }

    private void addTask(Task t){
        ((TaskApplication)getApplicationContext()).getTaskDatabase().taskDao().insertAll(t);
    }

    private List<Task> loadTasks(){
        return ((TaskApplication)getApplicationContext()).getTaskDatabase().taskDao().getAll();
    }
}
