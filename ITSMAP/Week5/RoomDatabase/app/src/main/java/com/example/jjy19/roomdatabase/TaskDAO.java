package com.example.jjy19.roomdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM TaskData")
    List<TaskData> getAll();

    @Query("select * from taskdata where taskID in (:taskIDs)")
    List<TaskData> loadAllByIds(int[] taskIDs);

    @Insert
    void insertAll(TaskData taskData);

    @Delete
    void delete(TaskData taskData);
}
