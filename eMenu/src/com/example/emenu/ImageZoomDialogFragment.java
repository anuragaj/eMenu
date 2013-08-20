package com.example.emenu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class ImageZoomDialogFragment extends DialogFragment {
	public View v;
	String imagePath;
	ImageView imageView;
	String KEY_ICON = "icon";

	public ImageZoomDialogFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
			System.out.println("icon file name is: "+savedInstanceState.getString(KEY_ICON));
		}
		
		String path = getArguments().getString(KEY_ICON);
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		v = inflater.inflate(R.layout.imagezoom_dialog_layout, null);
		v.setBackgroundColor(Color.TRANSPARENT);
		imageView = (ImageView) v.findViewById(R.id.image);
		String PACKAGE_NAME = getActivity().getApplicationContext()
				.getPackageName();
		int imgId = getResources().getIdentifier(
				PACKAGE_NAME + ":drawable/" + path, null, null);
		imageView.setImageResource(imgId);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		//v.setBackgroundResource(0);
		
		builder.setView(v)
		// Add action buttons
		// .setPositiveButton(R.string.signin,
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int id) {
				// // sign in the user ...
				// }
				// })
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								ImageZoomDialogFragment.this.getDialog()
										.cancel();
							}
						});
		return builder.create();
	}
}
