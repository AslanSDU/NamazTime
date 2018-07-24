package kz.sdu.namaztime;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OtherFragment extends Fragment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.other_fragment, null);

		ListView list = (ListView) v.findViewById(R.id.otherList);
		OtherListAdapter adapter = new OtherListAdapter(getActivity()
				.getApplicationContext());
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = null;
				switch (position) {
				case 0:
					intent = new Intent(getActivity(), HadithActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(getActivity(), AyatActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(getActivity(), SettingsActivity.class);
					startActivity(intent);
				default:
					break;
				}
			}
		});
		return v;
	}
}
