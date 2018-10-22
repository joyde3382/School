package com.example.jjy19.roomdatabase;

import android.app.Application;
import android.arch.persistence.room.Room;

public class TaskApplication extends Application {

    private TaskDatabase db;

    //singleton pattern used, for lazy loading + single instance since db object is expensive
    public TaskDatabase getTaskDatabase(){
        if(db == null){
            //this builder is for simplicity of the example and not good practise
            //- dangerous to allow queries on the main thread as it could block
            //- destructive migrations is dangerous as you might loose data with change in schema
            db = Room.databaseBuilder(this, TaskDatabase.class, "my_tasks").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }

        return db;
    }


}
