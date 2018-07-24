package kz.sdu.namaztime;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HadithActivity extends Activity {

	private TextView hadithName;
	private TextView hadithText;
	private TextView hadithFaith;
	private String name;
	private String text;
	private String faith;
	private ProgressDialog progress;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hadith_activity);

		Typeface fontNormal = Typeface.createFromAsset(getAssets(),
				"fonts/arial.ttf");

		hadithName = (TextView) findViewById(R.id.hadith_activity_name);
		hadithName.setTypeface(fontNormal);
		hadithText = (TextView) findViewById(R.id.hadith_activity_text);
		hadithText.setTypeface(fontNormal);
		hadithFaith = (TextView) findViewById(R.id.hadith_activity_faith);
		hadithFaith.setTypeface(fontNormal);

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		LinearLayout ll = (LinearLayout) findViewById(R.id.hadithBackground);
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

		if (isNetworkConnected()) {
			new GetHadith().execute();
		} else {
			Toast.makeText(this, "Check internet connection",
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		}
		return true;
	}

	private class GetHadith extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(HadithActivity.this);
			progress.setMessage("Күннің хадисін алуда...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		protected String doInBackground(String... args) {
			try {
				JSONParser jsonParser = new JSONParser();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				JSONObject json = jsonParser.makeHttpRequest(
						"http://meraliyev.kz/projectAndroid/get_hadis.php",
						"POST", params);
				Log.d("HELLO", json.toString());
				int success = json.getInt("success");
				if (success == 1) {
					name = json.getString("name");
					text = json.getString("text");
					faith = json.getString("faith");

				}
			} catch (Exception e) {
			}
			return null;
		}

		protected void onPostExecute(String result) {
			progress.dismiss();
			hadithName.setText(name);
			hadithText.setText(text);
			hadithFaith.setText(faith);
		}
	}
}
