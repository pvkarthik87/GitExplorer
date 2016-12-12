package com.karcompany.di.modules;

import android.content.Context;

import com.karcompany.GitExplorerApplication;
import com.karcompany.networking.ApiRepo;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.BrowseUsersPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
	private final GitExplorerApplication application;

	public ApplicationModule(GitExplorerApplication application) {
		this.application = application;
	}

	@Provides @Singleton
	Context provideApplicationContext() {
		return this.application;
	}

	@Provides @Singleton
	BrowseUsersPresenter provideBrowseUsersPresenter(ApiRepo apiRepo) {
		return new BrowseUsersPresenterImpl(apiRepo);
	}
}
