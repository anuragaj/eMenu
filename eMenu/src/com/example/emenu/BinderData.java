package com.example.emenu;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BinderData extends ArrayAdapter<String> {

	private int mItemIndex = -1;
	// XML node keys
	static final String KEY_TAG = "dish"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_RATE = "rate";
	static final String KEY_RATING = "rating";
	static final String KEY_SPICY = "spicy";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_ICON = "icon";
	static final String KEY_SHORT = "shortdescription";

	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String, String>> dishDataCollection;
	ViewHolder holder;

	public BinderData(Context context, int list_row) {
		// super(context , list_row , map);
		super(context, list_row);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void init(List<HashMap<String, String>> map) {
		this.dishDataCollection = map;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return idlist.size();
		return dishDataCollection.size();
	}


	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView != null)
			if (position == mItemIndex) {
				convertView.setSelected(true);
				convertView.setPressed(true);
				convertView.setBackgroundResource(R.drawable.gradient_bg_hover);
			} else {
				convertView.setBackgroundResource(R.drawable.gradient_bg);
			}

		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_row, parent, false);
			holder = new ViewHolder();

			holder.name = (TextView) vi.findViewById(R.id.dish); // city name
			holder.rate = (TextView) vi.findViewById(R.id.rate); // city weather
																	// overview
			holder.shortdescription = (TextView) vi
					.findViewById(R.id.shortdescription); // city temperature
			holder.dishImage = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image
																				// vi.setActivated(true);
			vi.setTag(holder);
		} else {

			holder = (ViewHolder) vi.getTag();
		}

		// Setting all values in listview

		holder.name.setText(dishDataCollection.get(position).get(KEY_NAME));
		holder.rate.setText(dishDataCollection.get(position).get(KEY_RATE));
		holder.shortdescription.setText(dishDataCollection.get(position).get(
				KEY_SHORT));

		// Setting an image
		String uri = "drawable/"
				+ dishDataCollection.get(position).get(KEY_ICON);
		int imageResource = vi
				.getContext()
				.getApplicationContext()
				.getResources()
				.getIdentifier(
						uri,
						null,
						vi.getContext().getApplicationContext()
								.getPackageName());
		Drawable image = vi.getContext().getResources()
				.getDrawable(imageResource);
		holder.dishImage.setImageDrawable(image);

		return vi;
	}

	/*
	 * 
	 * */
	static class ViewHolder {

		TextView name;
		TextView rate;
		TextView shortdescription;
		ImageView dishImage;
	}

	public void setSelectItem(int index) {
		mItemIndex = index;
	}

	public int getSelectItem() {
		return mItemIndex;
	}

}
