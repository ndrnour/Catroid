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
package org.catrobat.catroid.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Multimap;

import org.catrobat.catroid.BuildConfig;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.dialogs.TermsOfUseDialogFragment;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask;
import org.catrobat.catroid.ui.recyclerview.dialog.AboutDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.PrivacyPolicyDialogFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment;
import org.catrobat.catroid.ui.settingsfragments.Multilingual;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.catrobat.catroid.common.Constants.PREF_PROJECTNAME_KEY;
import static org.catrobat.catroid.common.SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY;

public class MainMenuActivity extends BaseCastActivity implements ProjectLoaderTask.ProjectLoaderListener {

	public static final String TAG = MainMenuActivity.class.getSimpleName();

	private static final int ACCESS_STORAGE = 0;

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({PROGRESS_BAR, FRAGMENT, ERROR})
	@interface Content {
	}

	protected static final int PROGRESS_BAR = 0;
	protected static final int FRAGMENT = 1;
	protected static final int ERROR = 2;
	private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
	public static FusedLocationProviderClient mFusedLocationClient;
	protected Location mLastLocation;

	private LocationManager locationManager;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	String re;

	@Override
	protected void onStart() {
		super.onStart();
		getLastLocation();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Multilingual.setToChosenLanguage(this);
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
		preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		editor = preferences.edit();

		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
		ScreenValueHandler.updateScreenWidthAndHeight(this);

		boolean hasUserAgreedToPrivacyPolicy = PreferenceManager.getDefaultSharedPreferences(this)
				.getBoolean(AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, false);

		if (hasUserAgreedToPrivacyPolicy) {
			loadContent();
		} else {
			setContentView(R.layout.privacy_policy_view);
		}
		setLangOnLocation();
	}

	@SuppressWarnings("MissingPermission")

	private void getLastLocation() {
		mFusedLocationClient.getLastLocation()
				.addOnCompleteListener(this, new OnCompleteListener<Location>() {
					@Override
					public void onComplete(@NonNull Task<Location> task) {
						if (task.isSuccessful() && task.getResult() != null) {
							mLastLocation = task.getResult();
						} else {
							Log.w(TAG, "getLastLocation:exception", task.getException());
						}
					}
				});
	}

	public void handleAgreedToPrivacyPolicyButton(View view) {
		PreferenceManager.getDefaultSharedPreferences(this)
				.edit()
				.putBoolean(AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, true)
				.commit();
		loadContent();
	}

	public void handleDeclinedPrivacyPolicyButton(View view) {
		View dialogView = View.inflate(this, R.layout.declined_privacy_agreement_alert_view, null);

		String linkString = getString(R.string.about_link_template,
				Constants.CATROBAT_ABOUT_URL,
				getString(R.string.share_website_text));

		((TextView) dialogView.findViewById(R.id.share_website_view)).setText(Html.fromHtml(linkString));

		new AlertDialog.Builder(this)
				.setView(dialogView)
				.setNeutralButton(R.string.ok, null)
				.create()
				.show();
	}

	private void loadContent() {
		setContentView(R.layout.activity_main_menu);
		showContentView(PROGRESS_BAR);

		if (!BuildConfig.FEATURE_APK_GENERATOR_ENABLED) {
			FacebookSdk.sdkInitialize(getApplicationContext());
			setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
			getSupportActionBar().setIcon(R.drawable.pc_toolbar_icon);
			getSupportActionBar().setTitle(R.string.app_name);
		}

		@PermissionChecker.PermissionResult
		int permissionResult = ContextCompat
				.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (permissionResult == PackageManager.PERMISSION_GRANTED) {
			onPermissionsGranted();
		} else {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_STORAGE);
		}
	}

	private void showContentView(@Content int content) {
		View progressBar = findViewById(R.id.progress_bar);
		View fragment = findViewById(R.id.fragment_container);
		View errorView = findViewById(R.id.runtime_permission_error_view);

		switch (content) {
			case PROGRESS_BAR:
				fragment.setVisibility(View.GONE);
				errorView.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				break;
			case FRAGMENT:
				fragment.setVisibility(View.VISIBLE);
				errorView.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
				break;
			case ERROR:
				fragment.setVisibility(View.GONE);
				errorView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				break;
		}
	}

	private void onPermissionsGranted() {
		if (BuildConfig.FEATURE_APK_GENERATOR_ENABLED) {
			setContentView(R.layout.activity_main_menu_splashscreen);
			prepareStandaloneProject();
			return;
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, new MainMenuFragment(), MainMenuFragment.TAG)
				.commit();
		showContentView(FRAGMENT);

		if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
			CastManager.getInstance().initializeCast(this);
		}
	}

	private void onPermissionDenied(int requestCode) {
		switch (requestCode) {
			case ACCESS_STORAGE:
				((TextView) findViewById(R.id.runtime_permission_error_view)).setText(R.string.error_no_write_access);
				showContentView(ERROR);
				break;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case ACCESS_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					onPermissionsGranted();
				} else {
					onPermissionDenied(requestCode);
				}
				break;
			case REQUEST_PERMISSIONS_REQUEST_CODE:
				getLastLocation();
				break;
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		Project currentProject = ProjectManager.getInstance().getCurrentProject();

		if (currentProject != null) {
			ProjectManager.getInstance().saveProject(getApplicationContext());
			PreferenceManager.getDefaultSharedPreferences(this)
					.edit()
					.putString(PREF_PROJECTNAME_KEY, currentProject.getName())
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main_menu, menu);

		String scratchConverter = getString(R.string.main_menu_scratch_converter);
		SpannableString scratchConverterBeta = new SpannableString(scratchConverter
				+ " "
				+ getString(R.string.beta));
		scratchConverterBeta.setSpan(
				new ForegroundColorSpan(getResources().getColor(R.color.beta_label_color)),
				scratchConverter.length(), scratchConverterBeta.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		menu.findItem(R.id.menu_scratch_converter).setTitle(scratchConverterBeta);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.menu_login).setVisible(!Utils.isUserLoggedIn(this));
		menu.findItem(R.id.menu_logout).setVisible(Utils.isUserLoggedIn(this));
		if (!BuildConfig.FEATURE_SCRATCH_CONVERTER_ENABLED) {
			menu.removeItem(R.id.menu_scratch_converter);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_rate_app:
				if (Utils.isNetworkAvailable(this)) {
					try {
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("market://details?id=" + getPackageName())));
					} catch (ActivityNotFoundException e) {
						ToastUtil.showError(this, R.string.main_menu_play_store_not_installed);
					}
				} else {
					ToastUtil.showError(this, R.string.error_internet_connection);
				}
				break;
			case R.id.menu_terms_of_use:
				new TermsOfUseDialogFragment().show(getFragmentManager(), TermsOfUseDialogFragment.TAG);
				break;
			case R.id.menu_privacy_policy:
				new PrivacyPolicyDialogFragment().show(getFragmentManager(), PrivacyPolicyDialogFragment.TAG);
				break;
			case R.id.menu_about:
				new AboutDialogFragment().show(getFragmentManager(), AboutDialogFragment.TAG);
				break;
			case R.id.menu_scratch_converter:
				if (Utils.isNetworkAvailable(this)) {
					startActivity(new Intent(this, ScratchConverterActivity.class));
				} else {
					ToastUtil.showError(this, R.string.error_internet_connection);
				}
				break;
			case R.id.settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			case R.id.menu_login:
				startActivity(new Intent(this, SignInActivity.class));
				break;
			case R.id.menu_logout:
				Utils.logoutUser(this);
				ToastUtil.showSuccess(this, R.string.logout_successful);
				break;
			case R.id.location:
				editor.remove("RE").apply();
				startActivity(new Intent(this, MainMenuActivity.class));
				finishAffinity();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void prepareStandaloneProject() {
		try {
			InputStream inputStream = getAssets().open(BuildConfig.START_PROJECT + ".zip");
			new ZipArchiver().unzip(inputStream, new File(PathBuilder.buildProjectPath(BuildConfig.PROJECT_NAME)));
			new ProjectLoaderTask(this, this).execute(BuildConfig.PROJECT_NAME);
		} catch (IOException e) {
			Log.e("STANDALONE", "Could not unpack Standalone Program: ", e);
		}
	}

	@Override
	public void onLoadFinished(boolean success, String message) {
		if (BuildConfig.FEATURE_APK_GENERATOR_ENABLED) {
			startActivityForResult(
					new Intent(this, PreStageActivity.class), PreStageActivity.REQUEST_RESOURCES_INIT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (BuildConfig.FEATURE_APK_GENERATOR_ENABLED) {
			if (requestCode == PreStageActivity.REQUEST_RESOURCES_INIT && resultCode == RESULT_OK) {
				SensorHandler.startSensorListener(this);
				Intent intent = new Intent(this, StageActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivityForResult(intent, StageActivity.STAGE_ACTIVITY_FINISH);
			}
			if (requestCode == StageActivity.STAGE_ACTIVITY_FINISH) {
				finish();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void setLangOnLocation () {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		assert locationManager != null;
		Location locations = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		List<String> providerList = locationManager.getAllProviders();

		re = preferences.getString("RE", "");
		if (null != locations && null != providerList && providerList.size() > 0) {

			double longitude = locations.getLongitude();
			double latitude = locations.getLatitude();

			Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
			try {
				List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
				Multimap<String, String> stringMap = Multilingual.localesHashMap();

				if (null != listAddresses && listAddresses.size() > 0) {
					final String countryCode = listAddresses.get(0).getCountryCode();
					String languageCode = "";

					ArrayList<String> localesWithSameCountryCode = new ArrayList<>();
					for (Map.Entry<String, String> entry : stringMap.entries()) {
						if (entry.getKey().equals(countryCode)) {
							localesWithSameCountryCode.add(entry.getValue());
							languageCode = entry.getValue();
							Log.i("value", languageCode);
						}
					}

					if (localesWithSameCountryCode.size() == 1) {
						Multilingual.setLanguageSharedPreference(getBaseContext(), languageCode, countryCode);
						Locale locationLocale = new Locale(languageCode, countryCode);
						Multilingual.updateLocale(this, locationLocale);

					} else if (localesWithSameCountryCode.size() > 1) {
						if (!re.equals("NO")) {
							// setup the alert builder
							AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setTitle(R.string.available_language_for_your_location);

							// add a list
							ArrayList<String> localeNames = new ArrayList<>();
							for (String langCode : localesWithSameCountryCode) {
								localeNames.add(new Locale(langCode, countryCode).getDisplayLanguage(new Locale(langCode, countryCode)));
							}
							final String[] stringName = new String[localeNames.size()];
							localeNames.toArray(stringName);

							final String[] stringA = new String[localesWithSameCountryCode.size()];
							localesWithSameCountryCode.toArray(stringA);

							builder.setItems(stringName, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Multilingual.setLanguageSharedPreference(getBaseContext(), stringA[which], countryCode);
									Locale locale = new Locale(stringA[which], countryCode);
									editor.putString("RE", "NO").apply();
									Multilingual.updateLocale(getBaseContext(), locale);
									startActivity(new Intent(getBaseContext(), MainMenuActivity.class));
									finishAffinity();
								}
							});
							// create and show the alert dialog
							AlertDialog dialog = builder.create();
							dialog.show();
						}
					}
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Log.e(TAG, String.valueOf(locations));
			Log.e(TAG, String.valueOf(providerList));
		}
	}
}
