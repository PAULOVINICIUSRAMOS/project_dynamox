package com.example.dynamox.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.dynamox.data.entity.UserEntity;

@Dao
public interface UserDAO {

    @Insert
    void insert(UserEntity user);
}
