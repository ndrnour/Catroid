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
import org.catrobat.catroid.utils.CrashReporter;

import java.util.Arrays;
import java.util.Locale;

import static org.catrobat.catroid.ui.Multilingual.languageTagKey;

public class CatroidApplication extends MultiDexApplication {

	private static final String TAG = CatroidApplication.class.getSimpleName();

	private ApplicationSettings settings;
	private static Context context;

	public static final String OS_ARCH = System.getProperty("os.arch");

	public static boolean parrotLibrariesLoaded = false;
	public static String defaultSystemLanguage;
	public static SharedPreferences languageSharedPreferences;
	private static String[] shortLanguageCode = {"az", "bs", "ca", "cs", "da", "de", "en", "es", "fr", "gl", "hr", "in", "it",
			"hu", "mk", "ms", "nl", "no", "pl", "pt", "ru", "ro", "sq", "sl", "sv", "vi", "tr", "ml", "ta", "te", "th", "gu",
			"hi", "ja", "ko", "ar", "ur", "fa", "ps", "sd", "iw"};
	private static String[] longLanguageCode = {"sr-rCS", "sr-rSP", "en-rAU", "en-rCA", "en-rGB", "pt-rBR", "zh-rCN", "zh-rTW"};


	@Override
	public void onCreate() {
		super.onCreate();
		CrashReporter.initialize(this);
		Log.d(TAG, "CatroidApplication onCreate");
		settings = new ApplicationSettings(this);
		CatroidApplication.context = getApplicationContext();
		defaultSystemLanguage = Locale.getDefault().getLanguage();
		// open the App in the last chosen language
		languageSharedPreferences = getSharedPreferences("For_language", Context.MODE_PRIVATE);
		String languageTag = languageSharedPreferences.getString(languageTagKey, "");
		if (Arrays.asList(shortLanguageCode).contains(languageTag)) {
			Multilingual.setContextLocale(getApplicationContext(), languageTag);
		} else if (languageTag.equals(" ")) {
			Multilingual.setContextLocale(getApplicationContext(), defaultSystemLanguage);
		} else if (Arrays.asList(longLanguageCode).contains(languageTag)) {
			String language = languageTag.substring(0, 2);
			String country = languageTag.substring(4);
			Multilingual.setContextLocale(getApplicationContext(), language, country);
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
