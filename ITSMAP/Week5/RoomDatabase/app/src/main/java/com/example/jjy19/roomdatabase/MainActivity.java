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
    List<TaskData> tasks;
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

        db = Room.databaseBuilder(getApplicationContext(),
                TaskDatabase.class, "Task-database").build();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

    }

    private void addTask(){
        String tempTask = editTaskName.getText().toString();
        String tempPlace = editTaskPlace.getText().toString();
        final Task t = new Task(tempTask, tempPlace);

        t.setTaskName(tempTask);
        t.setTaskPlace(tempPlace);

        tasks.add(t);
        addTask();


        db.taskDao().insertAll(newTask);
    }
}
