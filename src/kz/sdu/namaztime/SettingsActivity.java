package kz.sdu.namaztime;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	private String[] cities = { "Астана", "Алматы", "Актау", "Актобе",
			"Аркалык", "Атырау", "Екибастуз", "Жезказган", "Жетисай",
			"Караганда", "Кокшетау", "Костанай", "Кызыл Орда", "Орал",
			"Оскемен", "Павлодар", "Петропавловск", "Семей", "Талдыкорган",
			"Тараз", "Туркестан", "Шымкент" };
	private Spinner city;
	private SharedPreferences sp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		Typeface fontNormal = Typeface.createFromAsset(getAssets(),
				"fonts/arial.ttf");

		TextView ayat = (TextView) findViewById(R.id.settings_activity_ayat_reminder_text);
		ayat.setTypeface(fontNormal);
		TextView hadith = (TextView) findViewById(R.id.settings_activity_hadith_reminder_text);
		hadith.setTypeface(fontNormal);
		TextView namaz = (TextView) findViewById(R.id.settings_activity_namaz_reminder_text);
		namaz.setTypeface(fontNormal);

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		LinearLayout ll = (LinearLayout) findViewById(R.id.settingsBackground);
		switch (sp.getInt("fon", 1)) {
		case 1:
			ll.setBackgroundResource(R.drawable.fon1);
			break;
		case 2:
			ll.setBackgroundResource(R.drawable.fon2);
			break;
		case 3:
			ll.setBackgroundResource(R.drawable.fon3);
			break;
		case 4:
			ll.setBackgroundResource(R.drawable.fon4);
			break;
		case 5:
			ll.setBackgroundResource(R.drawable.fon5);
			break;
		case 6:
			ll.setBackgroundResource(R.drawable.fon6);
			break;
		default:
			break;
		}

		String currentCity = sp.getString("city", "Алматы");
		int currentPosition = 0;
		for (int i = 0; i < cities.length; i++) {
			if (currentCity.equals(cities[i])) {
				currentPosition = i;
				break;
			}
		}

		city = (Spinner) findViewById(R.id.settings_activity_language);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, cities);
		city.setAdapter(adapter);
		city.setSelection(currentPosition);
		city.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("city", (String) city.getSelectedItem());
				editor.commit();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}
