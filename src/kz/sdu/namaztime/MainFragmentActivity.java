package kz.sdu.namaztime;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainFragmentActivity extends FragmentActivity implements
		OnClickListener {

	private ImageButton findKiblaButton;
	private ImageButton calendarButton;
	private ImageButton namazTimeButton;
	private ImageButton otherButton;
	private FragmentTransaction fragmentTransaction;
	private NamazTimeFragment namazTimesFragment;
	private FindKiblaFragment findKiblaFragment;
	private CalendarFragment calendarFragment;
	private OtherFragment otherFragment;
	public static FragmentManager fragmentManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment_activity);

		fragmentManager = getSupportFragmentManager();

		namazTimesFragment = new NamazTimeFragment();
		findKiblaFragment = new FindKiblaFragment();
		calendarFragment = new CalendarFragment();
		otherFragment = new OtherFragment();

		fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.mainContainer, namazTimesFragment);
		fragmentTransaction.commit();

		findKiblaButton = (ImageButton) findViewById(R.id.findKiblaButton);
		findKiblaButton.setOnClickListener(this);

		calendarButton = (ImageButton) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(this);

		namazTimeButton = (ImageButton) findViewById(R.id.namazTimeButton);
		namazTimeButton.setSelected(true);
		namazTimeButton.setOnClickListener(this);

		otherButton = (ImageButton) findViewById(R.id.otherButton);
		otherButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		fragmentTransaction = getFragmentManager().beginTransaction();
		if (v == findKiblaButton) {
			findKiblaButton.setSelected(true);
			calendarButton.setSelected(false);
			namazTimeButton.setSelected(false);
			otherButton.setSelected(false);
			fragmentTransaction.replace(R.id.mainContainer, findKiblaFragment);
		} else if (v == calendarButton) {
			findKiblaButton.setSelected(false);
			calendarButton.setSelected(true);
			namazTimeButton.setSelected(false);
			otherButton.setSelected(false);
			fragmentTransaction.replace(R.id.mainContainer, calendarFragment);
		} else if (v == namazTimeButton) {
			findKiblaButton.setSelected(false);
			calendarButton.setSelected(false);
			namazTimeButton.setSelected(true);
			otherButton.setSelected(false);
			fragmentTransaction.replace(R.id.mainContainer, namazTimesFragment);
		} else if (v == otherButton) {
			findKiblaButton.setSelected(false);
			calendarButton.setSelected(false);
			namazTimeButton.setSelected(false);
			otherButton.setSelected(true);
			fragmentTransaction.replace(R.id.mainContainer, otherFragment);
		}
		fragmentTransaction.commit();
	}
}
