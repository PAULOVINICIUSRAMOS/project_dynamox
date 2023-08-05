package com.example.dynamox.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dynamox.data.dao.UserDAO;
import com.example.dynamox.data.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 2, exportSchema = false)
public abstract class DatabaseConfig extends RoomDatabase {

    public abstract UserDAO userDAO();
}