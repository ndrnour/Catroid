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
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.SpannableString;
import android.view.View;

import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.uiespresso.util.assertions.LayoutDirectionAssertions;
import org.catrobat.catroid.uiespresso.util.assertions.TextDirectionAssertions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Locale;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class RtlLayoutsTest {
	private static final Locale ARABICLOCALE = new Locale("ar");
	private final String title = getResourceString(R.string.main_menu_scratch_converter);
	private final String beta = getResourceString(R.string.beta).toUpperCase(Locale.getDefault());
	private final String mainMenuScratchConverterBeta = title + " " + beta;
	@Rule
	public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(MainMenuActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		mActivityRule.launchActivity(null);
	}

	@Test
	public void assertLayoutsAndTextDirectionAreRTL() {
		//delete all SharedPreferences to avoid "ShowDetails/HideDetails" problem
		//you will face the described Problem when you run this test more than one time respectively
		File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
		String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
		for (String fileName : sharedPreferencesFileNames) {
			InstrumentationRegistry.getTargetContext().getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
		}

		assertThat("not RTL", true, is(isRTL()));
		assertThat("current Locale is not Arabic", Locale.getDefault().getDisplayLanguage(), is(ARABICLOCALE.getDisplayLanguage
				()));
		onView(withId(R.id.main_menu_button_continue)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withId(R.id.main_menu_button_new)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withId(R.id.main_menu_button_programs)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withId(R.id.main_menu_button_help)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withId(R.id.main_menu_button_web)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withId(R.id.main_menu_button_upload)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());

		onView(withText(R.string.main_menu_programs)).perform(click());
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.delete)).perform(click());
		onView(withText(R.string.select_all)).perform(click());
		onView(withId(Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android"))).perform(click());
		onView(withId(android.R.id.button1)).perform(click());
		SystemClock.sleep(3000);

		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.show_details)).perform(click());
		onView(withText(R.string.last_used)).check(matches(isCompletelyDisplayed()));
		onView(withText(R.string.last_used)).check(TextDirectionAssertions.isTextDirectionRTL());
		onView(withText(R.string.size)).check(matches(isCompletelyDisplayed()));
		onView(withText(R.string.size)).check(TextDirectionAssertions.isTextDirectionRTL());
		onView(withId(R.id.details_right_top)).check(matches(isDisplayed()));
		onView(withId(R.id.details_right_bottom)).check(matches(isDisplayed()));

		onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.show_details)).perform(click());

		onView(withIndex(withId(R.id.project_activity_sprite_title), 0)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.spritelist_item_background), 0)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.textView_number_of_scripts), 0)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.textView_number_of_looks), 0)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.textView_number_of_bricks), 0)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.textView_number_of_sounds), 0)).check(matches(isCompletelyDisplayed()));

		onView(withIndex(withId(R.id.project_activity_sprite_title), 0)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withIndex(withId(R.id.spritelist_item_background), 0)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withIndex(withId(R.id.textView_number_of_scripts), 0)).check(TextDirectionAssertions.isTextDirectionRTL());
		onView(withIndex(withId(R.id.textView_number_of_looks), 0)).check(TextDirectionAssertions.isTextDirectionRTL());
		onView(withIndex(withId(R.id.textView_number_of_bricks), 0)).check(TextDirectionAssertions.isTextDirectionRTL());
		onView(withIndex(withId(R.id.textView_number_of_sounds), 0)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withIndex(withId(R.id.spritelist_item_background), 0)).perform(click());
		onView(withText(R.string.scripts)).check(matches(isCompletelyDisplayed()));
		onView(withText(R.string.backgrounds)).check(matches(isCompletelyDisplayed()));
		onView(withText(R.string.sounds)).check(matches(isCompletelyDisplayed()));
		onView(withText(R.string.backgrounds)).perform(click());

		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.show_details)).perform(click());

		onView(withIndex(withId(R.id.fragment_look_item_name_text_view), 0)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withIndex(withId(R.id.fragment_look_item_name_text_view), 0)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.fragment_look_item_measure_prefix_text_view), 0)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withIndex(withId(R.id.fragment_look_item_measure_prefix_text_view), 0)).check(matches
				(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.fragment_look_item_size_prefix_text_view), 0)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withIndex(withId(R.id.fragment_look_item_size_prefix_text_view), 0)).check(matches
				(isCompletelyDisplayed()));
		pressBack();
		pressBack();
		pressBack();
		pressBack();
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(mainMenuScratchConverterBeta)).perform(click());
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText(R.string.show_details)).perform(click());

		onView(withIndex(withId(R.id.scratch_projects_list_item_title), 1)).check(matches(isCompletelyDisplayed()));
		onView(withIndex(withId(R.id.scratch_projects_list_item_details_text), 1)).check(matches(isCompletelyDisplayed()));
	}

	private static boolean isRTL() {
		return isRTL(Locale.getDefault());
	}

	private static boolean isRTL(Locale locale) {
		final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
		return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
				directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
	}

	public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
		return new TypeSafeMatcher<View>() {
			int currentIndex = 0;

			@Override
			public void describeTo(Description description) {
				description.appendText("with index: ");
				description.appendValue(index);
				matcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				return matcher.matches(view) && currentIndex++ == index;
			}
		};
	}

	private String getResourceString(int id) {
		Context targetContext = InstrumentationRegistry.getTargetContext();
		return targetContext.getResources().getString(id);
	}
}
