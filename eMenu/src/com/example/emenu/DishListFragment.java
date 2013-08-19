package com.example.emenu;

import java.util.HashMap;
import java.util.List;
import android.app.Activity;
//import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class DishListFragment extends ListFragment {
	OnDishListSelectedListener mCallback;

	BinderData adapter = null;
	List<HashMap<String, String>> dishDataCollection;

	static final String KEY_TAG = "dish"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_RATE = "rate";
	static final String KEY_RATING = "rating";
	static final String KEY_SPICY = "spicy";
	static final String KEY_DESCRIPTION = "discription";
	static final String KEY_ICON = "icon";
	static final String KEY_SHORT = "shortdescription";

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnDishListSelectedListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onDishListSelected(int position, HashMap<String,String> dishDetails);
	}

	public DishListFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new BinderData(getActivity(), R.id.article);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnDishListSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDishListSelectedListener");
		}
	}

	public void showItem(List<HashMap<String, String>> map) {
		dishDataCollection = map;		
		if (dishDataCollection != null) {
			adapter.init(dishDataCollection);
		}
		
		setListAdapter(adapter);
		mCallback.onDishListSelected(0, dishDataCollection.get(0));
	}	
	
	public void setItemSelected(int position){
		adapter.setSelectItem(position);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
//		adapter.setSelectItem(position);
//		adapter.notifyDataSetChanged();
		setItemSelected(position);
		HashMap<String, String> dishDetails = dishDataCollection.get(position);
		mCallback.onDishListSelected(position, dishDetails);
	}
}
