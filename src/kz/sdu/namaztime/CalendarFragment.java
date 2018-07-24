package kz.sdu.namaztime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

public class CalendarFragment extends Fragment {

	private CalendarView calendar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.calendar_fragment, null);

		calendar = (CalendarView) v
				.findViewById(R.id.calendar_fragment_calendar);
		calendar.setShowWeekNumber(false);
		calendar.setFirstDayOfWeek(2);
		calendar.setSelectedWeekBackgroundColor(getResources().getColor(
				R.color.green));
		calendar.setUnfocusedMonthDateColor(getResources().getColor(
				R.color.transparent));
		calendar.setWeekSeparatorLineColor(getResources().getColor(
				R.color.transparent));
		calendar.setSelectedDateVerticalBar(R.color.darkgreen);
		calendar.setFocusedMonthDateColor(getResources()
				.getColor(R.color.black));

		return v;
	}
}
