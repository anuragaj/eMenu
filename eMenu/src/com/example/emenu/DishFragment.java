/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.emenu;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

//import android.app.Fragment;

public class DishFragment extends Fragment {
	static final String KEY_TAG = "dish"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_RATE = "rate";
	static final String KEY_RATING = "rating";
	static final String KEY_SPICY = "spicy";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_ICON = "icon";
	static final String KEY_SHORT = "shortdescription";

	final static String ARG_POSITION = "position";
	final static String ARG_ID = "nil";
	int mCurrentPosition = -1;
	HashMap<String, String> dishDetails = null;
	String dishId = null;
	TextView dishName, dishDescription, spicylabel, orderlabel;
	ImageButton dishImage;
	NumberPicker np;
	SeekBar spicyBar;
	Button addOrder;
	View view;
	View v;
	int currentShowingDishITemPosition = -1;

	public class orderedItems {
		private String dishName;
		private int quantity;
		private int spicyIndex;

		public void setDishName(String dishName) {
			this.dishName = dishName;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public void setSpicyIndex(int index) {
			this.spicyIndex = index;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
			dishId = savedInstanceState.getString(ARG_ID);
		}
		v = inflater.inflate(R.layout.dishdetails_layout, container, false);
		v.setBackgroundResource(R.drawable.gradient_bg);
		dishName = (TextView) v.findViewById(R.id.dishName);
		dishDescription = (TextView) v.findViewById(R.id.dishDescription);
		dishImage = (ImageButton) v.findViewById(R.id.dishImage);

		dishImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle bundle = new Bundle();

				bundle.putString(KEY_ICON, dishDetails.get(KEY_ICON));
				ImageZoomDialogFragment mImageZoomDialogFragment = new ImageZoomDialogFragment();
				mImageZoomDialogFragment.setArguments(bundle);
				mImageZoomDialogFragment
						.show(getActivity().getSupportFragmentManager(),
								"ImageZoomDialogFragment");
			}
		});

		spicyBar = (SeekBar) v.findViewById(R.id.spicybar);

		spicylabel = (TextView) v.findViewById(R.id.spicyLabel);
		orderlabel = (TextView) v.findViewById(R.id.orderLabel);

		np = (NumberPicker) v.findViewById(R.id.orderQuantity);
		np.setMaxValue(20);
		np.setMinValue(0);
		np.setWrapSelectorWheel(false);
		np.setFocusable(true);
		np.setFocusableInTouchMode(true);

		return v;
	}

	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			updateDishView(args.getInt(ARG_POSITION), dishDetails);
		} else if (mCurrentPosition != -1) {
			// Set article based on saved instance state defined during
			// onCreateView
			updateDishView(mCurrentPosition, dishDetails);
		}
	}

	public void updateDishView(int position, HashMap<String, String> dishDetails) {
		currentShowingDishITemPosition = position;
		
		if (dishDetails != null) {
			dishName.setText(dishDetails.get(KEY_NAME));
			dishDescription.setText(dishDetails.get(KEY_DESCRIPTION));

			String fnm = dishDetails.get(KEY_ICON); // this is image file name
			String PACKAGE_NAME = getActivity().getApplicationContext()
					.getPackageName();
			int imgId = getResources().getIdentifier(
					PACKAGE_NAME + ":drawable/" + fnm, null, null);

			dishImage.setImageResource(imgId);

		} else {
			System.out.println("Details Empty");
		}
		mCurrentPosition = position;
		this.dishDetails = dishDetails;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt(ARG_POSITION, mCurrentPosition);
		outState.putString(ARG_ID, dishId);
	}
}