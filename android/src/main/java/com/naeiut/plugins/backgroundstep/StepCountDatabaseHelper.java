package com.naeiut.plugins.backgroundstep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;

import androidx.annotation.RequiresApi;


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

  public static int getTodayStep(SQLiteDatabase db) {

    int count = 0;

    String sql = "select (max(" + TB_STEP + ") - min(" + TB_STEP + ")) as step from " + TB_NAME;
    Cursor cursor = db.rawQuery(sql,null);

    while (cursor.moveToNext()) {
      count = cursor.getInt(0);
    }

    return count;

  }
//  @RequiresApi(api = Build.VERSION_CODES.O)
//  public static int getTodayStep(SQLiteDatabase db) {
//
//    int count = 0;
//    LocalDateTime s = LocalDate.now().atStartOfDay();
//    LocalDateTime e = LocalDate.now().plusDays(1).atStartOfDay();
//
//    String sql = "select (max(" + TB_STEP + ") - min(" + TB_STEP + ")) as step from " + TB_NAME + " where " + TB_TIMESTAMP + " > " + s + " and " + TB_TIMESTAMP + " < " + e;
//    Cursor cursor = db.rawQuery(sql,null);
//
//    while (cursor.moveToNext()) {      //다음 DB가 존재한다면 While문을 통해  CounterMember.Count 합을 저장한다
//      count = cursor.getInt(0);
//    }
//
//    return count;
//
//  }



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
