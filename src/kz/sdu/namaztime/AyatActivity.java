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

public class AyatActivity extends Activity {

	private TextView ayatName;
	private TextView ayatArab;
	private TextView ayatTrans;
	private TextView ayatKazakh;
	private String name;
	private String arab;
	private String trans;
	private String kazakh;
	private ProgressDialog progress;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ayat_activity);

		Typeface fontNormal = Typeface.createFromAsset(getAssets(),
				"fonts/arial.ttf");

		ayatName = (TextView) findViewById(R.id.ayat_activity_name);
		ayatName.setTypeface(fontNormal);
		ayatArab = (TextView) findViewById(R.id.ayat_activity_arab);
		ayatArab.setTypeface(fontNormal);
		ayatTrans = (TextView) findViewById(R.id.ayat_activity_trans);
		ayatTrans.setTypeface(fontNormal);
		ayatKazakh = (TextView) findViewById(R.id.ayat_activity_kazakh);
		ayatKazakh.setTypeface(fontNormal);

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		LinearLayout ll = (LinearLayout) findViewById(R.id.ayatBackground);
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
			new GetAyat().execute();
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

	private class GetAyat extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(AyatActivity.this);
			progress.setMessage("Күннің аятын алуда...");
			progress.setIndeterminate(false);
			progress.setCancelable(false);
			progress.show();
		}

		protected String doInBackground(String... args) {
			try {
				JSONParser jsonParser = new JSONParser();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				JSONObject json = jsonParser.makeHttpRequest(
						"http://meraliyev.kz/projectAndroid/get_ayat.php",
						"POST", params);
				Log.d("HI", json.toString());
				int success = json.getInt("success");
				if (success == 1) {
					name = json.getString("name");
					arab = json.getString("arab");
					trans = json.getString("tras");
					kazakh = json.getString("kazakh");
				}
			} catch (Exception e) {
			}
			return null;
		}

		protected void onPostExecute(String result) {
			progress.dismiss();
			ayatName.setText(name);
			ayatArab.setText(arab);
			ayatTrans.setText(trans);
			ayatKazakh.setText(kazakh);
		}
	}
}
