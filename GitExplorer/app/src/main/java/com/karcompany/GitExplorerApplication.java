package com.karcompany;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Application which will initialize DI graph.
 */

import android.app.Application;

import com.karcompany.di.components.ApplicationComponent;
import com.karcompany.di.components.DaggerApplicationComponent;
import com.karcompany.di.modules.ApplicationModule;
import com.karcompany.networking.NetworkModule;

public class GitExplorerApplication extends Application {

	private static ApplicationComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeInjector();
	}

	private void initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.networkModule(new NetworkModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public static ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
}

