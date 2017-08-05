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

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.common.FileChecksumContainer;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ComeToFrontBrick;
import org.catrobat.catroid.content.bricks.GoNStepsBackBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.io.StorageHandler;
import org.catrobat.catroid.test.utils.Reflection;
import org.catrobat.catroid.ui.MainMenuActivity;

import org.catrobat.catroid.uiespresso.util.assertions.LayoutDirectionAssertions;
import org.catrobat.catroid.uiespresso.util.assertions.TextDirectionAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class RTL_BricksTest {
	private static final String DEFAULT_TEST_PROJECT_NAME = "testProject";
	private static final Locale ARABICLOCALE = new Locale("ar");

	@Rule
	public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(MainMenuActivity.class);

	@Before
	public void setUp() throws Exception {
		createTestProject(DEFAULT_TEST_PROJECT_NAME);
	}

	@Test
	public void assertBricksLayoutAndTextDirectionAreRTL() {
		assertThat("LayoutDirection is not RTL", true, is(isRTL()));
		assertThat("current Locale is not Arabic", Locale.getDefault().getDisplayLanguage(), is(ARABICLOCALE.getDisplayLanguage()));
		onView(withText(R.string.main_menu_programs)).perform(click());
		onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());
		onView(withId(R.id.spritelist_item_background)).perform(click());
		onView(withId(R.id.program_menu_button_scripts)).perform(click());

		onView(withId(R.id.brick_when_started_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_when_started)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withId(R.id.brick_hide_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_hide)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withId(R.id.brick_show_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_show)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withId(R.id.brick_set_size_to_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_set_size_to)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withId(R.id.brick_go_back_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_go_back)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withId(R.id.brick_go_to_front_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_come_to_front)).check(TextDirectionAssertions.isTextDirectionRTL());

		onView(withId(R.id.brick_place_at_layout)).check(LayoutDirectionAssertions.isLayoutDirectionRTL());
		onView(withText(R.string.brick_place_at)).check(TextDirectionAssertions.isTextDirectionRTL());
	}

	public static List<Brick> createTestProject(String projectName) {
		int xPosition = 457;
		int yPosition = 598;
		double size = 0.8;

		Project project = new Project(null, projectName);
		Sprite firstSprite = new SingleSprite("cat");

		Script testScript = new StartScript();

		ArrayList<Brick> brickList = new ArrayList<>();
		brickList.add(new HideBrick());
		brickList.add(new ShowBrick());
		brickList.add(new SetSizeToBrick(size));
		brickList.add(new GoNStepsBackBrick(1));
		brickList.add(new ComeToFrontBrick());
		brickList.add(new PlaceAtBrick(xPosition, yPosition));

		for (Brick brick : brickList) {
			testScript.addBrick(brick);
		}

		firstSprite.addScript(testScript);

		project.getDefaultScene().addSprite(firstSprite);
		ProjectManager projectManager = ProjectManager.getInstance();
		projectManager.setFileChecksumContainer(new FileChecksumContainer());
		projectManager.setProject(project);
		projectManager.setCurrentSprite(firstSprite);
		projectManager.setCurrentScript(testScript);
		projectManager.setCurrentScene(project.getDefaultScene());
		StorageHandler.getInstance().saveProject(project);

		// the application version is needed when the project will be uploaded
		// 0.7.3beta is the lowest possible version currently accepted by the web
		Reflection.setPrivateField(project.getXmlHeader(), "applicationVersion", "0.7.3beta");

		return brickList;
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
