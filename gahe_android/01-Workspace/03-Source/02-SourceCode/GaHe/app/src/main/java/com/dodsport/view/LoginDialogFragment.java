package com.dodsport.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.dodsport.R;
import com.dodsport.weight.TimeUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class LoginDialogFragment extends DialogFragment
{
	private EditText mUsername;
	private EditText mPassword;
	private TimePickerView pvCustomLunar;

	public interface LoginInputListener
	{
		void onLoginInputComplete(String username, String password);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popupwindow_archives_edit, null);
		mUsername = (EditText) view.findViewById(R.id.evName);
		mPassword = (EditText) view.findViewById(R.id.evPhoneName);
		LinearLayout llBirthday = view.findViewById(R.id.llBirthday);
//		initLunarPicker();
		llBirthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pvCustomLunar.show();
			}
		});

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

//		builder.setView(view)
//				// Add action buttons
//				.setPositiveButton("Sign in",
//						new DialogInterface.OnClickListener()
//						{
//							@Override
//							public void onClick(DialogInterface dialog, int id)
//							{
//
//							}
//						}).setNegativeButton("Cancel", null);
		return builder.create();
	}



	private void initLunarPicker() {
		Calendar selectedDate = Calendar.getInstance();//系统当前时间
		Calendar startDate = Calendar.getInstance();
		startDate.set(1900, 1, 1);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2027, 12, 28);
		//时间选择器 ，自定义布局
		pvCustomLunar = new com.bigkoo.pickerview.TimePickerView.Builder(getActivity(), new com.bigkoo.pickerview.TimePickerView.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date, View v) {//选中事件回调
				try {
					String parseDate = TimeUtils.parseDate(date);
					//tvBirthday.setText(parseDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		})
				.setDate(selectedDate)
				.setRangDate(startDate, endDate)
				.setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

					@Override
					public void customLayout(final View v) {
						final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
						ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
						tvSubmit.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								pvCustomLunar.returnData();
								pvCustomLunar.dismiss();
							}
						});
						ivCancel.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								pvCustomLunar.dismiss();
							}
						});
						//公农历切换
						CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
						cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
								//自适应宽
								setTimePickerChildWeight(v, 0.8f, isChecked ? 1f : 1.1f);
							}
						});

					}

					/**
					 * 公农历切换后调整宽
					 * @param v
					 * @param yearWeight
					 * @param weight
					 */
					private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
						ViewGroup timepicker = (ViewGroup) v.findViewById(R.id.timepicker);
						View year = timepicker.getChildAt(0);
						LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
						lp.weight = yearWeight;
						year.setLayoutParams(lp);
						for (int i = 1; i < timepicker.getChildCount(); i++) {
							View childAt = timepicker.getChildAt(i);
							LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
							childLp.weight = weight;
							childAt.setLayoutParams(childLp);
						}
					}
				})
				.setType(new boolean[]{true, true, true, false, false, false})
				.isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
				.setDividerColor(Color.RED)
				.build();
	}
}
