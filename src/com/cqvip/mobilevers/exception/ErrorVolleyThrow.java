package com.cqvip.mobilevers.exception;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

public class ErrorVolleyThrow implements ErrorListener{

	private Context context;
	private Dialog dialog;
	public ErrorVolleyThrow(Context context,Dialog dialog){
		this.context = context;
		this.dialog = dialog;
	}
	@Override
	public void onErrorResponse(VolleyError arg0) {
			
		 VolleyLog.e("Error: ", arg0.getMessage());
		
				if(dialog!=null&&dialog.isShowing())
					dialog.dismiss();
				
				String info = VolleyErrorHelper.getMessage(arg0, context);
				
					Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
		}
		
}
