package com.karcompany.di.components;

import android.content.Context;

import com.karcompany.GitExplorerApplication;
import com.karcompany.di.modules.ApplicationModule;
import com.karcompany.networking.NetworkModule;
import com.karcompany.views.activities.BaseActivity;
import com.karcompany.views.activities.BrowseUsersActivity;
import com.karcompany.views.adapters.BrowseUsersAdapter;
import com.karcompany.views.fragments.BrowseUsersFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

	void inject(GitExplorerApplication gitExplorerApplication);

	void inject(BaseActivity baseActivity);

	void inject(BrowseUsersActivity browseUsersActivity);

	void inject(BrowseUsersFragment browseUsersFragment);

	void inject(BrowseUsersAdapter browseUsersAdapter);
	
	//Exposed to sub-graphs.
	Context context();
}
