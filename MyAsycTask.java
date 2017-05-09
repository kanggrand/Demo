package com.a1000phone.android31myapp.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.a1000phone.android31myapp.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAsycTask extends AsyncTask<String, Void, List<String>> {
	private List<String> dataList = new ArrayList<String>();
	private Context context;
	private OnSuccessListener onSuccessListener;

	public MyAsycTask(Context context, OnSuccessListener onSuccessListener) {
		this.context = context;
		this.onSuccessListener = onSuccessListener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<String> doInBackground(String... params) {
		String url = params[0];
		String json = HttpUtils.getHttpResult(url);
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				String image = jsonObject2.getString("image");
				dataList.add(image);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		super.onPostExecute(result);
		if (result == null) {
			Toast.makeText(context, "网络有误", Toast.LENGTH_SHORT).show();
		} else {
			onSuccessListener.onSuccess(result);
		}
	}

}
