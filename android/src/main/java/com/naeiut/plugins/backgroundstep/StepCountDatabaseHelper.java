package com.naeiut.plugins.backgroundstep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepCountDatabaseHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "stepdata";
  private static final int DB_VERSION = 1;

  private static final String TB_NAME = "steps";
  private static final String TB_ID = "idx";
  private static final String TB_STEP = "step";
  private static final String TB_TIMESTAMP = "timestamp";

  StepCountDatabaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  public static void save(SQLiteDatabase db, Long step) {
    ContentValues values = new ContentValues();
    values.put(TB_STEP, step);
    values.put(TB_TIMESTAMP, System.currentTimeMillis());
    db.insertOrThrow(TB_NAME, null, values);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TB_NAME + " (" + TB_ID + " INTEGER PRIMARY KEY, " + TB_STEP + " INTEGER NOT NULL, " + TB_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    db.execSQL("CREATE INDEX stepindex ON " + TB_NAME + " (" + TB_STEP + ") ");
    db.execSQL("CREATE INDEX timeidex ON " + TB_NAME + " (" + TB_TIMESTAMP + ") ");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oVersion, int nVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    this.onCreate(db);
  }
}
