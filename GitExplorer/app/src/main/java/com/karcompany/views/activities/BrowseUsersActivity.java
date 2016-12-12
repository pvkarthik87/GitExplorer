package com.karcompany.views.activities;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Initial launch activity which will get displayed on first launch of application.
 */

import android.os.Bundle;

import com.karcompany.R;
import com.karcompany.di.HasComponent;
import com.karcompany.di.components.ApplicationComponent;

public class BrowseUsersActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_users);
		setTitle(getString(R.string.git_users));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
