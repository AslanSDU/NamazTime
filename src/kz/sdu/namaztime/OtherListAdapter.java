package kz.sdu.namaztime;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OtherListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Integer> images;
	private ArrayList<String> texts;

	public OtherListAdapter(Context context) {
		this.context = context;
		images = new ArrayList<Integer>();
		texts = new ArrayList<String>();

		images.add(R.drawable.hadis);
		images.add(R.drawable.ayat);
		images.add(R.drawable.settings);
		texts.add("Хадистер");
		texts.add("Аяттар");
		texts.add("Қондырғылар");

		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return images.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		View v = view;
		if (v == null) {
			v = inflater.inflate(R.layout.other_list_item, parent, false);
		}

		Typeface font = Typeface.createFromAsset(context.getAssets(),
				"fonts/arial.ttf");

		ImageView image = (ImageView) v.findViewById(R.id.otherListItemImage);
		image.setImageResource(images.get(position));
		TextView text = (TextView) v.findViewById(R.id.otherListItemText);
		text.setTypeface(font);
		text.setText(texts.get(position));

		return v;
	}
}
