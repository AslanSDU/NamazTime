package kz.sdu.namaztime;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NamazTimeFragment extends Fragment {

	private LinearLayout namazTimeBackground;
	private SharedPreferences sp;
	private TextView namazTimeFajrTime;
	private TextView namazTimeSunriseTime;
	private TextView namazTimeZuhrTime;
	private TextView namazTimeAsrTime;
	private TextView namazTimeMaghribTime;
	private TextView namazTimeIshaTime;
	private String[] months = { "Қаңтар", "Ақпан", "Наурыз", "Сәуір", "Мамыр",
			"Маусым", "Шілде", "Тамыз", "Қыркүйек", "Қазан", "Қараша",
			"Желтоқсан" };
	private String[] week_names = { "Дүйсенбі", "Сейсенбі", "Сәрсенбі",
			"Бейсенбі", "Жұма", "Сенбі", "Жексенбі" };
	private int currentMonth = 0;
	private String city = "";
	private String currentTime = "";
	private String currentDateForDB = "";
	private int year = 0;
	private Calendar calendar;
	private ProgressDialog progress;
	private DatabaseHelper db;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.namaz_time_fragment, null);

		namazTimeBackground = (LinearLayout) getActivity().findViewById(
				R.id.mainBackground);
		namazTimeBackground.setBackgroundResource(R.drawable.fon1);

		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		db = new DatabaseHelper(getActivity());

		calendar = Calendar.getInstance();

		Typeface fontNormal = Typeface.createFromAsset(getActivity()
				.getAssets(), "fonts/arial.ttf");
		Typeface fontBold = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/arialb.ttf");

		TextView namazTimeCity = (TextView) v.findViewById(R.id.namazTimeCity);
		namazTimeCity.setTypeface(fontBold);
		city = sp.getString("city", "Алматы");
		namazTimeCity.setText(city);

		TextView namazTimeCurrentTime = (TextView) v
				.findViewById(R.id.namazTimeCurrentTime);
		namazTimeCurrentTime.setTypeface(fontBold);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		currentTime = addZero(hour) + ":" + addZero(minute);
		namazTimeCurrentTime.setText(currentTime);

		TextView namazTimeCurrentDate = (TextView) v
				.findViewById(R.id.namazTimeCurrentDate);
		namazTimeCurrentDate.setTypeface(fontNormal);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		currentMonth = calendar.get(Calendar.MONTH);
		String month = convertMonth(currentMonth);
		year = calendar.get(Calendar.YEAR);
		String currentDate = addZero(day) + " " + month + " " + year;
		namazTimeCurrentDate.setText(currentDate);
		currentDateForDB = year + "." + addZero(currentMonth + 1) + "."
				+ addZero(day);

		TextView namazTimeDayOfWeek = (TextView) v
				.findViewById(R.id.namazTimeDayOfWeek);
		namazTimeDayOfWeek.setTypeface(fontNormal);
		int week_day = calendar.get(Calendar.WEEK_OF_MONTH);
		namazTimeDayOfWeek.setText(convertWeek(week_day - 2));

		TextView namazTimeFajr = (TextView) v.findViewById(R.id.namazTimeFajr);
		namazTimeFajr.setTypeface(fontNormal);
		TextView namazTimeSunrise = (TextView) v
				.findViewById(R.id.namazTimeSunrise);
		namazTimeSunrise.setTypeface(fontNormal);
		TextView namazTimeZuhr = (TextView) v.findViewById(R.id.namazTimeZuhr);
		namazTimeZuhr.setTypeface(fontNormal);
		TextView namazTimeAsr = (TextView) v.findViewById(R.id.namazTimeAsr);
		namazTimeAsr.setTypeface(fontNormal);
		TextView namazTimeMaghrib = (TextView) v
				.findViewById(R.id.namazTimeMaghrib);
		namazTimeMaghrib.setTypeface(fontNormal);
		TextView namazTimeIsha = (TextView) v.findViewById(R.id.namazTimeIsha);
		namazTimeIsha.setTypeface(fontNormal);

		namazTimeFajrTime = (TextView) v.findViewById(R.id.namazTimeFajrTime);
		namazTimeFajrTime.setTypeface(fontNormal);
		namazTimeSunriseTime = (TextView) v
				.findViewById(R.id.namazTimeSunriseTime);
		namazTimeSunriseTime.setTypeface(fontNormal);
		namazTimeZuhrTime = (TextView) v.findViewById(R.id.namazTimeZuhrTime);
		namazTimeZuhrTime.setTypeface(fontNormal);
		namazTimeAsrTime = (TextView) v.findViewById(R.id.namazTimeAsrTime);
		namazTimeAsrTime.setTypeface(fontNormal);
		namazTimeMaghribTime = (TextView) v
				.findViewById(R.id.namazTimeMaghribTime);
		namazTimeMaghribTime.setTypeface(fontNormal);
		namazTimeIshaTime = (TextView) v.findViewById(R.id.namazTimeIshaTime);
		namazTimeIshaTime.setTypeface(fontNormal);

		boolean cityInDB = sp.getBoolean(city, false);
		int yearInDB = sp.getInt(city + "_year", 0);
		if (!cityInDB || (cityInDB && yearInDB != year)) {
			if (isNetworkConnected()) {
				db.createTimesTable();
				new GetNamazTimes().execute();
			} else {
				Toast.makeText(getActivity(),
						"Check your connection to the network!",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			setTimes();
			changeBackgroud();
		}
		return v;
	}

	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		}
		return true;
	}

	private String addZero(int value) {
		String s = "";
		if (value < 9) {
			s += "0";
		}
		s += value;
		return s;
	}

	private String convertMonth(int value) {
		return months[value];
	}

	private String convertWeek(int value) {
		return week_names[value];
	}

	private void changeBackgroud() {
		SharedPreferences.Editor editor = sp.edit();
		if (currentTime.compareTo(namazTimeFajrTime.getText().toString()) > 0
				&& currentTime.compareTo(namazTimeSunriseTime.getText()
						.toString()) < 0) {
			namazTimeBackground.setBackgroundResource(R.drawable.fon1);
			editor.putInt("fon", 1);
		} else if (currentTime.compareTo(namazTimeSunriseTime.getText()
				.toString()) > 0
				&& currentTime
						.compareTo(namazTimeZuhrTime.getText().toString()) < 0) {
			namazTimeBackground.setBackgroundResource(R.drawable.fon2);
			editor.putInt("fon", 2);
		} else if (currentTime
				.compareTo(namazTimeZuhrTime.getText().toString()) > 0
				&& currentTime.compareTo(namazTimeAsrTime.getText().toString()) < 0) {
			namazTimeBackground.setBackgroundResource(R.drawable.fon3);
			editor.putInt("fon", 3);
		} else if (currentTime.compareTo(namazTimeAsrTime.getText().toString()) > 0
				&& currentTime.compareTo(namazTimeMaghribTime.getText()
						.toString()) < 0) {
			namazTimeBackground.setBackgroundResource(R.drawable.fon4);
			editor.putInt("fon", 4);
		} else if (currentTime.compareTo(namazTimeMaghribTime.getText()
				.toString()) > 0
				&& currentTime
						.compareTo(namazTimeIshaTime.getText().toString()) < 0) {
			namazTimeBackground.setBackgroundResource(R.drawable.fon5);
			editor.putInt("fon", 5);
		} else if (currentTime
				.compareTo(namazTimeIshaTime.getText().toString()) > 0
				|| currentTime
						.compareTo(namazTimeFajrTime.getText().toString()) < 0) {
			namazTimeBackground.setBackgroundResource(R.drawable.fon6);
			editor.putInt("fon", 6);
		}
		editor.commit();
	}

	private void setTimes() {
		String[] times = db.getTimes(currentDateForDB);
		if (times[0].length() < 5) {
			times[0] = "0" + times[0];
		}
		namazTimeFajrTime.setText(times[0]);
		if (times[1].length() < 5) {
			times[1] = "0" + times[1];
		}
		namazTimeSunriseTime.setText(times[1]);
		if (times[2].length() < 5) {
			times[2] = "0" + times[2];
		}
		namazTimeZuhrTime.setText(times[2]);
		if (times[3].length() < 5) {
			times[3] = "0" + times[3];
		}
		namazTimeAsrTime.setText(times[3]);
		if (times[4].length() < 5) {
			times[4] = "0" + times[4];
		}
		namazTimeMaghribTime.setText(times[4]);
		if (times[5].length() < 5) {
			times[5] = "0" + times[5];
		}
		namazTimeIshaTime.setText(times[5]);
	}

	private class GetNamazTimes extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(getActivity());
			progress.setMessage("Намаз уақытын алуда...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		protected String doInBackground(String... args) {
			String url = "http://azan.kz/ws/getNamazTimes?region=" + city
					+ "&month=" + (currentMonth + 1);
			try {
				JSONParserAzanKZ json = new JSONParserAzanKZ(url);
				JSONObject jsonObject = json.parse();
				if (jsonObject != null) {
					JSONArray namazTimes = jsonObject.getJSONArray("result");
					for (int i = 0; i < namazTimes.length(); i++) {
						JSONObject object = namazTimes.getJSONObject(i);
						String title = object.getString("title");
						String date = object.getString("date");
						String time = object.getString("time");
						db.addTime(title, date, time);
					}
				}
			} catch (Exception e) {
			}
			return null;
		}

		protected void onPostExecute(String result) {
			progress.dismiss();
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean(city, true);
			editor.putInt(city + "_year", year);
			editor.commit();
			setTimes();
			changeBackgroud();
		}
	}
}
