package com.cqvip.mobilevers.utils;

import com.cqvip.mobilevers.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Utils {

	public static String checkUserid(Context context) {
		SharedPreferences localUsers = context.getSharedPreferences(
				"mobilevers", context.MODE_PRIVATE);
		String userid = localUsers.getString("userid", "0");
		if (!userid.equals("0")) {
			return userid;
		}
		Toast.makeText(context,context.getResources().getString(R.string.hellotome), 0).show();
		return null;
	}

}
