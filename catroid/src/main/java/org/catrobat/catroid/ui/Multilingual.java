/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
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

package org.catrobat.catroid.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.catrobat.catroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.catrobat.catroid.CatroidApplication.defaultSystemLanguage;
import static org.catrobat.catroid.CatroidApplication.languageSharedPreferences;

public class Multilingual extends Activity {
	private static String[] languageCode = {"az", "bs", "ca", "cs", "sr-rCS", "sr-rSP", "da", "de", "en-rAU", "en-rCA",
			"en-rGB", "en", "es", "fr", "gl", "hr", "in", "it", "hu", "mk", "ms", "nl", "no", "pl", "pt-rBR", "pt", "ru",
			"ro", "sq", "sl", "sv", "vi", "tr", "ml", "ta", "te", "th", "gu", "hi", "ja", "ko", "zh-rCN", "zh-rTW", "ar",
			"ur", "fa", "ps", "sd", "iw"};
	public static String languageTagKey = "applicationLanguage";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multilingual);
		setTitle(R.string.preference_title_language);
		ListView listview = (ListView) findViewById(R.id.list_languages);
		List<String> languagesNames = new ArrayList<>();
		languagesNames.add(getResources().getString(R.string.device_language));
		for (String aLanguageCode : languageCode) {
			if (aLanguageCode.length() == 2) {
				languagesNames.add(new Locale(aLanguageCode).getDisplayName(new Locale(aLanguageCode)));
			} else {
				String language = aLanguageCode.substring(0, 2);
				String country = aLanguageCode.substring(4);
				languagesNames.add(new Locale(language, country).getDisplayName(new Locale(language, country)));
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				R.layout.multilingual_name_text, R.id.lang_text, languagesNames);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					setLocale(defaultSystemLanguage);
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
				}
				if (position == 1) {
					setLocale("az");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "az");
					editor.commit();
				}
				if (position == 2) {
					setLocale("bs");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "bs");
					editor.commit();
				}
				if (position == 3) {
					setLocale("ca");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ca");
					editor.commit();
				}
				if (position == 4) {
					setLocale("cs");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "cs");
					editor.commit();
				}
				if (position == 5) {
					setLocale("sr", "CS");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "sr-rCS");
					editor.commit();
				}
				if (position == 6) {
					setLocale("sr", "SP");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "sr-rSP");
					editor.commit();
				}
				if (position == 7) {
					setLocale("da");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "da");
					editor.commit();
				}
				if (position == 8) {
					setLocale("de");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "de");
					editor.commit();
				}
				if (position == 9) {
					setLocale("en", "AU");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "en-rAU");
					editor.commit();
				}
				if (position == 10) {
					setLocale("en", "CA");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "en-rCA");
					editor.commit();
				}
				if (position == 11) {
					setLocale("en", "GB");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "en-rGB");
					editor.commit();
				}
				if (position == 12) {
					setLocale("en");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "en");
					editor.commit();
				}
				if (position == 13) {
					setLocale("es");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "es");
					editor.commit();
				}
				if (position == 14) {
					setLocale("fr");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "fr");
					editor.commit();
				}
				if (position == 15) {
					setLocale("gl");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "gl");
					editor.commit();
				}
				if (position == 16) {
					setLocale("hr");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "hr");
					editor.commit();
				}
				if (position == 17) {
					setLocale("in");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "in");
					editor.commit();
				}
				if (position == 18) {
					setLocale("it");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "it");
					editor.commit();
				}
				if (position == 19) {
					setLocale("hu");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "hu");
					editor.commit();
				}
				if (position == 20) {
					setLocale("mk");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "mk");
					editor.commit();
				}
				if (position == 21) {
					setLocale("ms");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ms");
					editor.commit();
				}
				if (position == 22) {
					setLocale("nl");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "nl");
					editor.commit();
				}
				if (position == 23) {
					setLocale("no");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "no");
					editor.commit();
				}
				if (position == 24) {
					setLocale("pl");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "pl");
					editor.commit();
				}
				if (position == 25) {
					setLocale("pt", "BR");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "pt-rBR");
					editor.commit();
				}
				if (position == 26) {
					setLocale("pt");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "pt");
					editor.commit();
				}
				if (position == 27) {
					setLocale("ru");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ru");
					editor.commit();
				}
				if (position == 28) {
					setLocale("ro");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ro");
					editor.commit();
				}
				if (position == 29) {
					setLocale("sq");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "sq");
					editor.commit();
				}
				if (position == 30) {
					setLocale("sl");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "sl");
					editor.commit();
				}
				if (position == 31) {
					setLocale("sv");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "sv");
					editor.commit();
				}
				if (position == 32) {
					setLocale("vi");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "vi");
					editor.commit();
				}
				if (position == 33) {
					setLocale("tr");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "tr");
					editor.commit();
				}
				if (position == 34) {
					setLocale("ml");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ml");
					editor.commit();
				}
				if (position == 35) {
					setLocale("ta");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ta");
					editor.commit();
				}
				if (position == 36) {
					setLocale("te");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "te");
					editor.commit();
				}
				if (position == 37) {
					setLocale("th");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "th");
					editor.commit();
				}
				if (position == 38) {
					setLocale("gu");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "gu");
					editor.commit();
				}
				if (position == 39) {
					setLocale("hi");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "hi");
					editor.commit();
				}
				if (position == 40) {
					setLocale("ja");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ja");
					editor.commit();
				}
				if (position == 41) {
					setLocale("ko");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ko");
					editor.commit();
				}
				if (position == 42) {
					setLocale("zh", "CN");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "zh-rCN");
					editor.commit();
				}
				if (position == 43) {
					setLocale("zh", "TW");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "zh-rTW");
					editor.commit();
				}
				if (position == 44) {
					setLocale("ar");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ar");
					editor.commit();
				}
				if (position == 45) {
					setLocale("ur");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ur");
					editor.commit();
				}
				if (position == 46) {
					setLocale("fa");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "fa");
					editor.commit();
				}
				if (position == 47) {
					setLocale("ps");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "ps");
					editor.commit();
				}
				if (position == 48) {
					setLocale("sd");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "sd");
					editor.commit();
				}
				if (position == 49) {
					setLocale("iw");
					SharedPreferences.Editor editor = languageSharedPreferences.edit();
					editor.clear().apply();
					editor.putString(languageTagKey, "iw");
					editor.commit();
				}
			}
		});
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static void setContextLocale(Context context, String languageTag) {
		Locale language = new Locale(languageTag);
		Resources resources = context.getResources();
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		Configuration conf = resources.getConfiguration();
		conf.locale = language;
		Locale.setDefault(language);
		conf.setLayoutDirection(language);
		resources.updateConfiguration(conf, displayMetrics);
	}

	public void setLocale(String languageTag) {
		setContextLocale(this, languageTag);
		Intent intent = new Intent(Multilingual.this, MainMenuActivity.class);
		startActivity(intent);
		finishAffinity();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static void setContextLocale(Context context, String languageTag, String countryTag) {
		Locale language = new Locale(languageTag, countryTag);
		Resources resources = context.getResources();
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		Configuration conf = resources.getConfiguration();
		conf.locale = language;
		Locale.setDefault(language);
		conf.setLayoutDirection(language);
		resources.updateConfiguration(conf, displayMetrics);
	}

	public void setLocale(String languageTag, String countryTag) {
		setContextLocale(this, languageTag, countryTag);
		Intent intent = new Intent(Multilingual.this, MainMenuActivity.class);
		startActivity(intent);
		finishAffinity();
	}
}