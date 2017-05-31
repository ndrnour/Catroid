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

package org.catrobat.catroid.test.ui;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.Multilingual;
import org.catrobat.catroid.ui.SettingsActivity;

import java.util.Locale;

public class MultilingualTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

	public MultilingualTest() {
		super(MainMenuActivity.class);
	}

	private static Solo solo;

	private int mediumSleep = 1000;
	private static final Locale ARABICLOCALE = new Locale("ar");
	private static final Locale URDULOCALE = new Locale("ur");
	private static final Locale FARSILOCALE = new Locale("fa");
	private static final Locale DEUTSCHLOCALE = Locale.GERMAN;

	private static final String DEUTSCH = "Deutsch";
	private static final String ARABIC = "العربية";
	private static final String URDU = "اردو";
	private static final String FARSI = "فارسی";
	private static final String SERBIAN_LA = "(Latin)";

	// English Strings
	private static final String BUTTON_CONTINUE_NAME = "Continue";
	private static final String BUTTON_NEW_NAME = "New";
	// Arabic Strings
	private static final String APP_NAME_ARABIC = "بوكت كوود";
	private static final String BUTTON_CONTINUE_NAME_ARABIC = "متابعة";
	private static final String BUTTON_NEW_NAME_ARABIC = "جديد";
	private static final String BUTTON_PROGRAMS_NAME_ARABIC = "البرامج";
	// Urdu Strings
	private static final String APP_NAME_URDU = "پاکٹ کوڈ";
	private static final String BUTTON_CONTINUE_NAME_URDU = "جاری رکھیں";
	private static final String BUTTON_NEW_NAME_URDU = "نیا";
	private static final String BUTTON_PROGRAMS_NAME_URDU = "پروگرامات";
	// Farsi Strings
	private static final String APP_NAME_FARSI = "پاکت کد";
	private static final String BUTTON_CONTINUE_NAME_FARSI = "ادامه";
	private static final String BUTTON_NEW_NAME_FARSI = "جدید";
	private static final String BUTTON_PROGRAMS_NAME_FARSI = "برنامه ها";
	// GERMAN Strings
	private static final String BUTTON_CONTINUE_NAME_GERMAN = "Fortsetzen";
	private static final String BUTTON_NEW_NAME_GERMAN = "Neu";
	private static final String BUTTON_PROGRAMS_NAME_GERMAN = "Programme";

	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testChangeLanguageToArabic() throws Exception {
		gotoMultilingualActivity();
		solo.searchText(ARABIC);
		solo.clickOnText(ARABIC);
		solo.sleep(mediumSleep);
		assertTrue("current LayoutDirection is not RTL", isRTL());
		Locale CurrentLocale = Locale.getDefault();
		assertEquals("Current Locale is not Arabic", CurrentLocale, ARABICLOCALE);
		String App_Name = solo.getString(R.string.app_name);
		String Button_Programs_Name = solo.getString(R.string.main_menu_programs);
		String Button_Continue_Name = solo.getString(R.string.main_menu_continue);
		String BUTTON_NEW_NAME = solo.getString(R.string.main_menu_new);
		assertEquals("Hey *_* there is a mistake", App_Name, APP_NAME_ARABIC);
		assertEquals("Hey *_* there is a mistake", BUTTON_CONTINUE_NAME_ARABIC, Button_Continue_Name);
		assertEquals("Hey *_* there is a mistake", BUTTON_NEW_NAME_ARABIC, BUTTON_NEW_NAME);
		assertEquals("Hey *_* there is a mistake", BUTTON_PROGRAMS_NAME_ARABIC, Button_Programs_Name);
	}

	public void testChangeLanguageToUrdu() throws Exception {
		gotoMultilingualActivity();
		solo.searchText(URDU);
		solo.clickOnText(URDU);
		solo.sleep(mediumSleep);
		assertTrue("current LayoutDirection is not RTL", isRTL());
		Locale CurrentLocale = Locale.getDefault();
		assertEquals("Current Locale is not Urdu", CurrentLocale, URDULOCALE);
		String App_Name = solo.getString(R.string.app_name);
		String Button_Programs_Name = solo.getString(R.string.main_menu_programs);
		String Button_Continue_Name = solo.getString(R.string.main_menu_continue);
		String Button_New_Name = solo.getString(R.string.main_menu_new);
		assertEquals(" Hey *_* there is a mistake", App_Name, APP_NAME_URDU);
		assertEquals("Hey *_* there is a mistake", BUTTON_CONTINUE_NAME_URDU, Button_Continue_Name);
		assertEquals("Hey *_* there is a mistake", BUTTON_NEW_NAME_URDU, Button_New_Name);
		assertEquals("Hey *_* there is a mistake", BUTTON_PROGRAMS_NAME_URDU, Button_Programs_Name);
	}

	public void testChangeLanguageToFarsi() throws Exception {
		gotoMultilingualActivity();
		solo.searchText(FARSI);
		solo.clickOnText(FARSI);
		solo.sleep(mediumSleep);
		assertTrue("current LayoutDirection is not RTL", isRTL());
		Locale currentLocale = Locale.getDefault();
		assertEquals("Current Locale is not Farsi", currentLocale, FARSILOCALE);
		String App_Name = solo.getString(R.string.app_name);
		String Button_Programs_Name = solo.getString(R.string.main_menu_programs);
		String Button_Continue_Name = solo.getString(R.string.main_menu_continue);
		String Button_New_Name = solo.getString(R.string.main_menu_new);
		assertEquals("Hey *_* there is a mistake", App_Name, APP_NAME_FARSI);
		assertEquals("Hey *_* there is a mistake", BUTTON_CONTINUE_NAME_FARSI, Button_Continue_Name);
		assertEquals("Hey *_* there is a mistake", BUTTON_NEW_NAME_FARSI, Button_New_Name);
		assertEquals("Hey *_* there is a mistake", BUTTON_PROGRAMS_NAME_FARSI, Button_Programs_Name);
	}

	public void testChangeLanguageToDeutsch() throws Exception {
		gotoMultilingualActivity();
		solo.searchText(DEUTSCH);
		solo.clickOnText(DEUTSCH);
		solo.sleep(mediumSleep);
		assertFalse("current LayoutDirection is not LTR", isRTL());
		Locale currentLocale = Locale.getDefault();
		assertEquals("Current Locale is not Deutsch", currentLocale, DEUTSCHLOCALE);
		String Button_Programs_Name = solo.getString(R.string.main_menu_programs);
		String Button_Continue_Name = solo.getString(R.string.main_menu_continue);
		String Button_New_Name = solo.getString(R.string.main_menu_new);
		assertEquals("Hey *_* there is a mistake", Button_Programs_Name, BUTTON_PROGRAMS_NAME_GERMAN);
		assertEquals("Hey *_* there is a mistake", Button_Continue_Name, BUTTON_CONTINUE_NAME_GERMAN);
		assertEquals("Hey *_* there is a mistake", Button_New_Name, BUTTON_NEW_NAME_GERMAN);
	}

	public void testFullBackLanguage() throws Exception {
		gotoMultilingualActivity();
		solo.searchText(SERBIAN_LA);
		solo.clickOnText(SERBIAN_LA);
		solo.sleep(mediumSleep);
		Locale currentLocale = Locale.getDefault();
		Log.e("current Locale is ", currentLocale.toString());
		String continue_Btn = solo.getString(R.string.main_menu_continue);
		String new_Btn = solo.getString(R.string.main_menu_new);
		assertEquals(continue_Btn, BUTTON_CONTINUE_NAME);
		assertEquals(new_Btn, BUTTON_NEW_NAME);
	}

	private void gotoMultilingualActivity() {
		solo.assertCurrentActivity("Current Activity is not MainMenuActivity ", MainMenuActivity.class);
		solo.sendKey(Solo.MENU);
		solo.clickOnMenuItem(solo.getString(R.string.settings));
		solo.assertCurrentActivity("Current Activity is not SettingsActivity", SettingsActivity.class);
		solo.clickOnText(solo.getString(R.string.preference_title_language));
		solo.assertCurrentActivity("Current Activity is not Multilingual", Multilingual.class);
	}

	private static boolean isRTL() {
		return isRTL(Locale.getDefault());
	}

	private static boolean isRTL(Locale locale) {
		final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
		return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
				directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
	}
}
