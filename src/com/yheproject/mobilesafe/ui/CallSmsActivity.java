package com.yheproject.mobilesafe.ui;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yheproject.mobilesafe.R;
import com.yheproject.mobilesafe.db.dao.BlackNumberDao;

public class CallSmsActivity extends Activity {

	private static final String TAG = "CallSmsActivity";
	private BlackNumberDao dao;
	private ListView lv_call_sms_safe;
	private Button bt_add_black_number;
	private List<String> numbers;
	private CallSmsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_sms_safe);
		dao = new BlackNumberDao(this);
		bt_add_black_number = (Button) findViewById(R.id.bt_add_black_number);
		lv_call_sms_safe = (ListView) findViewById(R.id.lv_call_sms_safe);
		// 注册上下文菜单
		registerForContextMenu(lv_call_sms_safe);

		bt_add_black_number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showInputDialog("");
			}
		});
		numbers = dao.getAllNumbers();
		adapter = new CallSmsAdapter();
		// adapter = new ArrayAdapter<String>(this, R.layout.blacknumber_item,
		// R.id.tv_blacknumber_item, numbers);
		lv_call_sms_safe.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = getIntent();
		String number = intent.getStringExtra("number");
		if (number != null) {
			Log.i(TAG, "提示用户添加 黑名单号码");
			showInputDialog(number);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int id = (int) info.id;
		String number = numbers.get(id);
		switch (item.getItemId()) {
		case R.id.update_number:

			updataNumber(number);
			break;
		case R.id.delete_number:
			// 当前条目的id

			dao.delete(number);
			// 重新获取黑名单号码
			numbers = dao.getAllNumbers();
			// 通知listview更新界面
			adapter.notifyDataSetChanged();
			break;

		}
		return false;
	}

	private void showInputDialog(String number) {
		AlertDialog.Builder builder = new Builder(CallSmsActivity.this);
		builder.setTitle("Add BlackList Number");
		final EditText et = new EditText(CallSmsActivity.this);
		et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setText(number);
		builder.setView(et);
		builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String number = et.getText().toString().trim();
				if (TextUtils.isEmpty(number)) {
					Toast.makeText(getApplicationContext(), "BlackList number cannot be empty", 1)
							.show();
					return;
				} else {
					dao.add(number);
					// todo: 通知listview更新数据
					// 缺点: 重新刷新整个listview
					// numbers = dao.getAllNumbers();
					// lv_call_sms_safe.setAdapter(new
					// ArrayAdapter<String>(CallSmsActivity.this,
					// R.layout.blacknumber_item, R.id.tv_blacknumber_item,
					// numbers));
					numbers = dao.getAllNumbers();

					// 让数据适配器通知listview更新数据
					adapter.notifyDataSetChanged();

				}

			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}

	/**
	 * 更新黑名单号码
	 * 
	 * @param number
	 */
	private void updataNumber(final String oldnumber) {
		AlertDialog.Builder builder = new Builder(CallSmsActivity.this);
		builder.setTitle("Edit BlackList Number");
		final EditText et = new EditText(CallSmsActivity.this);
		et.setInputType(InputType.TYPE_CLASS_PHONE);
		builder.setView(et);
		builder.setPositiveButton("Edit改", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String newNumber = et.getText().toString().trim();
				if (TextUtils.isEmpty(newNumber)) {
					Toast.makeText(getApplicationContext(), "BlackList number cannot be empty", 1)
							.show();
					return;
				} else {
					dao.update(oldnumber, newNumber);
					// todo: 通知listview更新数据
					// 缺点: 重新刷新整个listview
					// numbers = dao.getAllNumbers();
					// lv_call_sms_safe.setAdapter(new
					// ArrayAdapter<String>(CallSmsActivity.this,
					// R.layout.blacknumber_item, R.id.tv_blacknumber_item,
					// numbers));
					numbers = dao.getAllNumbers();

					// 让数据适配器通知listview更新数据
					adapter.notifyDataSetChanged();

				}

			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();

	}

	private class CallSmsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return numbers.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return numbers.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = View.inflate(CallSmsActivity.this,
					R.layout.blacknumber_item, null);
			TextView tv_blacknumber_item = (TextView) view
					.findViewById(R.id.tv_blacknumber_item);
			tv_blacknumber_item.setText(numbers.get(position));
			return view;
		}

	}
}
