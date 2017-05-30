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

package org.catrobat.catroid.uiespresso.util;

import android.content.res.Resources;
import android.os.SystemClock;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MainMenuActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsString;

import static org.hamcrest.Matchers.is;

import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class HindiNumbers_AtShowDetails {
	private String expectedHindiNumber = "٨٢٤٫٨";
	private Locale arabicLocale = new Locale("ar");

	@Rule
	public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(MainMenuActivity.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHindiNumbers_ShowDetails_AtMyProjectsActivity() {
		assertThat("not RTL", true, is(isRTL()));
		assertThat("current Locale is not Arabic", Locale.getDefault().getDisplayLanguage(), is(arabicLocale.getDisplayLanguage()));
		onView(withText(R.string.main_menu_programs)).perform(click());
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.delete)).perform(click());
		onView(withText(R.string.select_all)).perform(click());
		onView(withId(Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android"))).perform(click());
		onView(withId(android.R.id.button1)).perform(click());
		SystemClock.sleep(5000);
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.show_details)).perform(click());
		onView(withId(R.id.details_right_bottom)).check(matches(withText(containsString(expectedHindiNumber))));
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
