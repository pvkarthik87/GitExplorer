package com.karcompany.views.activities;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Initial launch activity which will get displayed on first launch of application.
 */

import android.os.Bundle;

import com.karcompany.R;
import com.karcompany.di.HasComponent;
import com.karcompany.di.components.ApplicationComponent;

public class UserProfileActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		setTitle(getString(R.string.user_profile));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
