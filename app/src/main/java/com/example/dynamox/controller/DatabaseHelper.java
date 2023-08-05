package com.example.dynamox.controller;

import android.content.Context;

import androidx.room.Room;

import com.example.dynamox.data.database.DatabaseConfig;
import com.example.dynamox.data.entity.UserEntity;

public class DatabaseHelper {

    private DatabaseConfig db;

    public DatabaseHelper(Context context) {
        db = Room.databaseBuilder(context, DatabaseConfig.class, "users_db").fallbackToDestructiveMigration().build();
    }

    public void saveUser(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserEntity user = new UserEntity(name, 0);
                try {
                    db.userDAO().insert(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
