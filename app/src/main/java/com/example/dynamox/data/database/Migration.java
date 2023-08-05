package com.example.dynamox.data.database;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration extends androidx.room.migration.Migration {
    /**
     * Creates a new migration between {@code startVersion} and {@code endVersion}.
     *
     * @param startVersion The start version of the database.
     * @param endVersion   The end version of the database after this migration is applied.
     */
    public Migration(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

    }
}
