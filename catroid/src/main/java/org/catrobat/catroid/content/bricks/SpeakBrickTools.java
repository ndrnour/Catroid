/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.catrobat.catroid.common.Constants.availableLocalesTTS;

public class SpeakBrickTools {
	protected static Locale locale;
	private static transient Locale preLocale;



	public void setLocale(Locale mlocale) {
		locale = mlocale;
	}

	public static Locale getLocale() {
		return locale;
	}

	static void setSpeakSpinner(Context context, View view, boolean isPrototypeView) {
		final Spinner speakBrickSpinner = (Spinner) view.findViewById(R.id.brick_speak_spinner);
		final ArrayAdapter<Locale> spinnerAdapter = createSpeakBrickAdapter(context);
		SpinnerAdapterWrapper spinnerAdapterWrapper = new SpinnerAdapterWrapper(context, spinnerAdapter);
		speakBrickSpinner.setAdapter(spinnerAdapterWrapper);
		if (isPrototypeView) {
			speakBrickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if (position == 0) {
						locale = new Locale(CatroidApplication.defaultSystemLanguage);
					} else {
						locale = (Locale) parent.getItemAtPosition(position);
					}
					Log.e("Languages", locale.getDisplayName());
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}
		setSpinnerSelection(speakBrickSpinner);
	}

	private static ArrayAdapter<Locale> createSpeakBrickAdapter(Context context) {
		ArrayAdapter<Locale> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (Locale locale : availableLocalesTTS) {
			arrayAdapter.add(locale);
		}
		return arrayAdapter;
	}

	private static void setSpinnerSelection(Spinner spinner) {
		if (Locales().contains(locale)) {
			spinner.setSelection(Locales().indexOf(locale), true);
		} else {
			spinner.setSelection(0,false);
		}
	}

	private static class SpinnerAdapterWrapper implements SpinnerAdapter {

		protected Context context;
		protected ArrayAdapter<Locale> spinnerAdapter;
		private boolean isTouchInDropDownView;

		SpinnerAdapterWrapper(Context context, ArrayAdapter<Locale> spinnerAdapter) {
			this.context = context;
			this.spinnerAdapter = spinnerAdapter;

			this.isTouchInDropDownView = false;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			View dropDownView = spinnerAdapter.getDropDownView(position, convertView, parent);
			TextView textView = (TextView) dropDownView;
			textView.setText(availableLocalesTTS[position].getDisplayName());
			textView.setTextColor(Color.BLUE);
			dropDownView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					isTouchInDropDownView = true;
					return false;
				}
			});
			return textView;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			spinnerAdapter.registerDataSetObserver(observer);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			spinnerAdapter.unregisterDataSetObserver(observer);
		}

		@Override
		public int getCount() {
			return spinnerAdapter.getCount();
		}

		@Override
		public Object getItem(int position) {
			return spinnerAdapter.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return spinnerAdapter.getItemId(position);
		}

		@Override
		public boolean hasStableIds() {
			return spinnerAdapter.hasStableIds();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = (TextView) spinnerAdapter.getView(position, convertView, parent);
			textView.setText(availableLocalesTTS[position].getDisplayName());
			textView.setTextColor(Color.BLUE);
			if (isTouchInDropDownView) {
				isTouchInDropDownView = false;
			}
			return textView;
		}

		@Override
		public int getItemViewType(int position) {
			return spinnerAdapter.getItemViewType(position);
		}

		@Override
		public int getViewTypeCount() {
			return spinnerAdapter.getViewTypeCount();
		}

		@Override
		public boolean isEmpty() {
			return spinnerAdapter.isEmpty();
		}
	}

	private static List<Locale> Locales() {
		List<Locale> mLocales = new ArrayList<>();
		Collections.addAll(mLocales, availableLocalesTTS);
		return mLocales;
	}
}
