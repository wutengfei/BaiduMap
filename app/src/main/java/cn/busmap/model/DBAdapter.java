package cn.busmap.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    private static final String DB_NAME = "smartbook.db";
    private static final int DB_version = 1;
    //用户表user
    private static final String DB_TABLE_USER = "user";
    private static final String KEY_userId = "userId";
    private static final String KEY_USERNAME = "loginName";
    private static final String KEY_PASSWORD = "password";


    private SQLiteDatabase db;
    private final Context context;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //用户表user
        private static final String DB_CREATE_USER = "create table " + DB_TABLE_USER + "(" + KEY_userId +
                " integer primary key autoincrement," + KEY_USERNAME + " varchar(20)," + KEY_PASSWORD + " varchar(20))";


        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE_USER);

        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USER);
            onCreate(_db);
        }

    }

    public DBAdapter(Context _context) {
        context = _context;
    }

    public void open() throws SQLiteException {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_version);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /////////////////////////////////////用户模块
    //增加用户
    public long insert(User user) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_USERNAME, user.getUsername());
        newValues.put(KEY_PASSWORD, user.getPassword());
        return db.insert(DB_TABLE_USER, null, newValues);
    }

    //删除所有用户
    public long deleteAllData() {
        return db.delete(DB_TABLE_USER, null, null);
    }

    //删出一个用户
    public long deleteOneDataByName(String username) {
        return db.delete(DB_TABLE_USER, KEY_USERNAME + " like ? ", new String[]{username});
    }

    //更新用户信息
    public long updateOneDataByName(String username, User user) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_USERNAME, user.getUsername());
        updateValues.put(KEY_PASSWORD, user.getPassword());
        return db.update(DB_TABLE_USER, updateValues, KEY_USERNAME + " like ? ", new String[]{username});

    }

    public User[] getOneByName(String username) {
        Cursor cursor = db.query(DB_TABLE_USER, new String[]{KEY_USERNAME, KEY_PASSWORD},
                KEY_USERNAME + " like ? ", new String[]{username}, null, null, null, null);
        return ConvertToUser(cursor);
    }

    public User[] getAllUser() {
        Cursor cursor = db.query(DB_TABLE_USER, new String[]{KEY_USERNAME, KEY_PASSWORD},
                null, null, null, null, KEY_USERNAME + " asc");//asc是排序规则，升序
        return ConvertToUser(cursor);
    }

    private User[] ConvertToUser(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) return null;
        User[] peoples = new User[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            peoples[i] = new User();
            peoples[i].setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            peoples[i].setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            cursor.moveToNext();
        }
        return peoples;
    }


}