package com.example.emenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DishTypeFragment extends ListFragment {
	OnDishTypeSelectedListener mCallback;

	List<HashMap<String, String>> cusineDataCollection;
	List<String> mDishType = new ArrayList<String>();
	ArrayAdapter<String> mDishTypeAdapter;

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnDishTypeSelectedListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onDishTypeSelected(int position);
	}

	public DishTypeFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDishTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.cusine_item,
				mDishType);
		//loadDishType();
		setListAdapter(mDishTypeAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();

		if (getFragmentManager().findFragmentById(R.id.dishList_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	public void setCusineCollection(
			List<HashMap<String, String>> cusineDataCollection) {
		for (int i = 0; i < cusineDataCollection.size(); i++) {
			mDishType.add(cusineDataCollection.get(i).get("name"));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (OnDishTypeSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Notify the parent activity of selected item
		mCallback.onDishTypeSelected(position);
		// Set the item as checked to be highlighted when in two-pane layout
		getListView().setItemChecked(position, true);
	}

}
