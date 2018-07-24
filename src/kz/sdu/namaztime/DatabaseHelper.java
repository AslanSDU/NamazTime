package kz.sdu.namaztime;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "namaztime";
	private SharedPreferences sp;
	private String city = "";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		city = sp.getString("city", "Алматы");
	}

	public void onCreate(SQLiteDatabase db) {
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void createTimesTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + city);
		db.execSQL("CREATE TABLE " + city
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "title TEXT, date TEXT, time TEXT)");
		db.close();
	}

	public void addTime(String title, String date, String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("date", date);
		values.put("time", time);

		db.insert(city, null, values);
		db.close();
	}

	public String[] getTimes(String date) {
		SQLiteDatabase db = this.getWritableDatabase();
		String[] times = new String[6];
		Cursor c = db.rawQuery("SELECT * FROM " + city + " WHERE date='" + date
				+ "'", null);
		int i = 0;
		if (c.moveToFirst()) {
			do {
				times[i] = c.getString(3);
				i++;
			} while (c.moveToNext());
		}
		db.close();
		return times;
	}

	public void createMosquesTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS mosques");
		db.execSQL("CREATE TABLE mosques"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT,address TEXT,latitude TEXT,longitude TEXT)");
		db.close();
	}

	public void addMosque(ForMap fm) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", fm.getName());
		values.put("address", fm.getAddress());
		values.put("latitude", fm.getLatitude());
		values.put("longitude", fm.getLongitude());

		db.insert("mosques", null, values);
		db.close();
	}

	public ArrayList<ForMap> getMosques() {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<ForMap> mosques = new ArrayList<ForMap>();
		Cursor c = db.rawQuery("SELECT * FROM mosques", null);
		if (c.moveToFirst()) {
			do {
				mosques.add(new ForMap(c.getString(1), c.getString(2), c
						.getString(3), c.getString(4)));
			} while (c.moveToNext());
		}
		return mosques;
	}

	public int getMosqueCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM mosques", null);
		return c.getCount();
	}

	public void createHalalPlacesTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS halal");
		db.execSQL("CREATE TABLE halal"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT,address TEXT,latitude TEXT,longitude TEXT)");
		db.close();
	}

	public void addHalalPlace(ForMap fm) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", fm.getName());
		values.put("address", fm.getAddress());
		values.put("latitude", fm.getLatitude());
		values.put("longitude", fm.getLongitude());

		db.insert("halal", null, values);
		db.close();
	}

	public ArrayList<ForMap> getHalalPlaces() {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<ForMap> halals = new ArrayList<ForMap>();
		Cursor c = db.rawQuery("SELECT * FROM halal", null);
		if (c.moveToFirst()) {
			do {
				halals.add(new ForMap(c.getString(1), c.getString(2), c
						.getString(3), c.getString(4)));
			} while (c.moveToNext());
		}
		return halals;
	}

	public int getHalalPlaceCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM halal", null);
		return c.getCount();
	}
}
