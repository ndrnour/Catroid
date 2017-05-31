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
package org.catrobat.catroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.parrot.freeflight.settings.ApplicationSettings;

import org.catrobat.catroid.ui.Multilingual;

import java.util.Locale;

public class CatroidApplication extends MultiDexApplication {

	private static final String TAG = CatroidApplication.class.getSimpleName();

	private ApplicationSettings settings;
	private static Context context;

	public static final String OS_ARCH = System.getProperty("os.arch");

	public static boolean parrotLibrariesLoaded = false;
	public static String defaultSystemLanguage;
	public static SharedPreferences languageSharedPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "CatroidApplication onCreate");
		settings = new ApplicationSettings(this);
		CatroidApplication.context = getApplicationContext();
		defaultSystemLanguage = Locale.getDefault().getLanguage();
		// open the App in the last chosen language
		languageSharedPreferences = getSharedPreferences("For_language", getApplicationContext().MODE_PRIVATE);
		String langTag = languageSharedPreferences.getString("Nur", "");
		switch (langTag) {
			case "":
				Multilingual.setContextLocale(getApplicationContext(), defaultSystemLanguage);
				break;
			case "ar":
				Multilingual.setContextLocale(getApplicationContext(), "ar");
				break;
			case "az":
				Multilingual.setContextLocale(getApplicationContext(), "az");
				break;
			case "bs":
				Multilingual.setContextLocale(getApplicationContext(), "bs");
				break;
			case "ca":
				Multilingual.setContextLocale(getApplicationContext(), "ca");
				break;
			case "cs":
				Multilingual.setContextLocale(getApplicationContext(), "cs");
				break;
			case "da":
				Multilingual.setContextLocale(getApplicationContext(), "da");
				break;
			case "de":
				Multilingual.setContextLocale(getApplicationContext(), "de");
				break;
			case "en-rAU":
				Multilingual.setContextLocale(getApplicationContext(), "en-rAU");
				break;
			case "en-rCA":
				Multilingual.setContextLocale(getApplicationContext(), "en-rCA");
				break;
			case "en-rGB":
				Multilingual.setContextLocale(getApplicationContext(), "en-rGB");
				break;
			case "en":
				Multilingual.setContextLocale(getApplicationContext(), "en");
				break;
			case "es":
				Multilingual.setContextLocale(getApplicationContext(), "es");
				break;
			case "fa":
				Multilingual.setContextLocale(getApplicationContext(), "fa");
				break;
			case "fr":
				Multilingual.setContextLocale(getApplicationContext(), "fr");
				break;
			case "gl":
				Multilingual.setContextLocale(getApplicationContext(), "gl");
				break;
			case "gu":
				Multilingual.setContextLocale(getApplicationContext(), "gu");
				break;
			case "iw":
				Multilingual.setContextLocale(getApplicationContext(), "iw");
				break;
			case "hi":
				Multilingual.setContextLocale(getApplicationContext(), "hi");
				break;
			case "hr":
				Multilingual.setContextLocale(getApplicationContext(), "hr");
				break;
			case "hu":
				Multilingual.setContextLocale(getApplicationContext(), "hu");
				break;
			case "in":
				Multilingual.setContextLocale(getApplicationContext(), "in");
				break;
			case "it":
				Multilingual.setContextLocale(getApplicationContext(), "it");
				break;
			case "ja":
				Multilingual.setContextLocale(getApplicationContext(), "ja");
				break;
			case "ko":
				Multilingual.setContextLocale(getApplicationContext(), "ko");
				break;
			case "mk":
				Multilingual.setContextLocale(getApplicationContext(), "mk");
				break;
			case "ml":
				Multilingual.setContextLocale(getApplicationContext(), "ml");
				break;
			case "ms":
				Multilingual.setContextLocale(getApplicationContext(), "ms");
				break;
			case "nl":
				Multilingual.setContextLocale(getApplicationContext(), "nl");
				break;
			case "no":
				Multilingual.setContextLocale(getApplicationContext(), "no");
				break;
			case "pl":
				Multilingual.setContextLocale(getApplicationContext(), "pl");
				break;
			case "ps":
				Multilingual.setContextLocale(getApplicationContext(), "ps");
				break;
			case "pt":
				Multilingual.setContextLocale(getApplicationContext(), "pt");
				break;
			case "pt-rBR":
				Multilingual.setContextLocale(getApplicationContext(), "pt-rBR");
				break;
			case "ro":
				Multilingual.setContextLocale(getApplicationContext(), "ro");
				break;
			case "ru":
				Multilingual.setContextLocale(getApplicationContext(), "ru");
				break;
			case "sd":
				Multilingual.setContextLocale(getApplicationContext(), "sd");
				break;
			case "sl":
				Multilingual.setContextLocale(getApplicationContext(), "sl");
				break;
			case "sq":
				Multilingual.setContextLocale(getApplicationContext(), "sq");
				break;
			case "sr-rCS":
				Multilingual.setContextLocale(getApplicationContext(), "sr-rCS");
				break;
			case "sr-rSP":
				Multilingual.setContextLocale(getApplicationContext(), "sr-rSP");
				break;
			case "sv":
				Multilingual.setContextLocale(getApplicationContext(), "sv");
				break;
			case "ta":
				Multilingual.setContextLocale(getApplicationContext(), "ta");
				break;
			case "te":
				Multilingual.setContextLocale(getApplicationContext(), "te");
				break;
			case "th":
				Multilingual.setContextLocale(getApplicationContext(), "th");
				break;
			case "tr":
				Multilingual.setContextLocale(getApplicationContext(), "tr");
				break;
			case "ur":
				Multilingual.setContextLocale(getApplicationContext(), "ur");
				break;
			case "vi":
				Multilingual.setContextLocale(getApplicationContext(), "vi");
				break;
			case "zh-rCN":
				Multilingual.setContextLocale(getApplicationContext(), "zh-rCN");
				break;
			case "zh-rTW":
				Multilingual.setContextLocale(getApplicationContext(), "zh-rTW");
				break;
		}
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public ApplicationSettings getParrotApplicationSettings() {
		return settings;
	}

	public static synchronized boolean loadNativeLibs() {
		if (parrotLibrariesLoaded) {
			return true;
		}

		try {
			System.loadLibrary("avutil");
			System.loadLibrary("swscale");
			System.loadLibrary("avcodec");
			System.loadLibrary("avfilter");
			System.loadLibrary("avformat");
			System.loadLibrary("avdevice");
			System.loadLibrary("adfreeflight");
			parrotLibrariesLoaded = true;
		} catch (UnsatisfiedLinkError e) {
			Log.e(TAG, Log.getStackTraceString(e));
			parrotLibrariesLoaded = false;
		}
		return parrotLibrariesLoaded;
	}

	public static Context getAppContext() {
		return CatroidApplication.context;
	}
}
