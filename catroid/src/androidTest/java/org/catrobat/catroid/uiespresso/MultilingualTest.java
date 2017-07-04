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

package org.catrobat.catroid.uiespresso;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;

import org.catrobat.catroid.ui.MainMenuActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MultilingualTest {
	private int shortSleep = 1000;
	private static final Locale ARABICLOCALE = new Locale("ar");
	private static final Locale URDULOCALE = new Locale("ur");
	private static final Locale FARSILOCALE = new Locale("fa");
	private static final Locale DEUTSCHLOCALE = Locale.GERMAN;

	@Rule
	public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(MainMenuActivity.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testChangeLanguageToDeutsch() {
		gotoMultilingualActivity();
		onData(anything()).inAdapterView(withId(R.id.list_languages)).atPosition(8).perform(click());
		assertThat("current Locale is not Deutsch", Locale.getDefault().getDisplayLanguage(), is(DEUTSCHLOCALE
				.getDisplayLanguage()));
		SystemClock.sleep(shortSleep);
		String buttonProgramsName = getResourceString(R.string.main_menu_programs);
		String buttonContinueName = getResourceString(R.string.main_menu_continue);
		String buttonNewName = getResourceString(R.string.main_menu_new);
		onView(withId(R.id.main_menu_button_continue)).check(matches(withText(containsString(buttonContinueName))));
		onView(withId(R.id.main_menu_button_new)).check(matches(withText(buttonNewName)));
		onView(withId(R.id.main_menu_button_programs)).check(matches(withText(buttonProgramsName)));
	}

	@Test
	public void testChangeLanguageToArabic() {
		gotoMultilingualActivity();
		onData(anything()).inAdapterView(withId(R.id.list_languages)).atPosition(44).perform(click());
		assertThat("current Locale is not Arabic", Locale.getDefault().getDisplayLanguage(), is(ARABICLOCALE
				.getDisplayLanguage()));
		assertThat("not RTL", true, is(isRTL()));
		SystemClock.sleep(shortSleep);
		String buttonProgramsName = getResourceString(R.string.main_menu_programs);
		String buttonContinueName = getResourceString(R.string.main_menu_continue);
		String buttonNewName = getResourceString(R.string.main_menu_new);
		onView(withId(R.id.main_menu_button_continue)).check(matches(withText(containsString(buttonContinueName))));
		onView(withId(R.id.main_menu_button_new)).check(matches(withText(buttonNewName)));
		onView(withId(R.id.main_menu_button_programs)).check(matches(withText(buttonProgramsName)));
	}

	@Test
	public void testChangeLanguageToUrdu() {
		gotoMultilingualActivity();
		onData(anything()).inAdapterView(withId(R.id.list_languages)).atPosition(45).perform(click());
		assertThat("current Locale is not Urdu", Locale.getDefault().getDisplayLanguage(), is(URDULOCALE
				.getDisplayLanguage()));
		assertThat("not RTL", true, is(isRTL()));
		SystemClock.sleep(shortSleep);
		String buttonProgramsName = getResourceString(R.string.main_menu_programs);
		String buttonContinueName = getResourceString(R.string.main_menu_continue);
		String buttonNewName = getResourceString(R.string.main_menu_new);
		onView(withId(R.id.main_menu_button_continue)).check(matches(withText(containsString(buttonContinueName))));
		onView(withId(R.id.main_menu_button_new)).check(matches(withText(buttonNewName)));
		onView(withId(R.id.main_menu_button_programs)).check(matches(withText(buttonProgramsName)));
	}

	@Test
	public void testChangeLanguageToFarsi() {
		gotoMultilingualActivity();
		onData(anything()).inAdapterView(withId(R.id.list_languages)).atPosition(46).perform(click());
		assertThat("current Locale is not Farsi", Locale.getDefault().getDisplayLanguage(), is(FARSILOCALE
				.getDisplayLanguage()));
		assertThat("not RTL", true, is(isRTL()));
		SystemClock.sleep(shortSleep);
		String buttonProgramsName = getResourceString(R.string.main_menu_programs);
		String buttonContinueName = getResourceString(R.string.main_menu_continue);
		String buttonNewName = getResourceString(R.string.main_menu_new);
		onView(withId(R.id.main_menu_button_continue)).check(matches(withText(containsString(buttonContinueName))));
		onView(withId(R.id.main_menu_button_new)).check(matches(withText(buttonNewName)));
		onView(withId(R.id.main_menu_button_programs)).check(matches(withText(buttonProgramsName)));
	}

	private static boolean isRTL() {
		return isRTL(Locale.getDefault());
	}

	private static boolean isRTL(Locale locale) {
		final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
		return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
				directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
	}

	private String getResourceString(int id) {
		Context targetContext = InstrumentationRegistry.getTargetContext();
		return targetContext.getResources().getString(id);
	}

	private void gotoMultilingualActivity() {
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.settings)).perform(click());
		onView(withText(R.string.preference_description_language)).perform(click());
	}
}
