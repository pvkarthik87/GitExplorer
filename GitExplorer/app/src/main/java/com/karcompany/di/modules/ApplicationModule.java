package com.karcompany.di.modules;

import android.content.Context;

import com.karcompany.GitExplorerApplication;
import com.karcompany.networking.ApiRepo;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.BrowseUsersPresenterImpl;
import com.karcompany.presenters.UserProfileHeaderPresenter;
import com.karcompany.presenters.UserProfileHeaderPresenterImpl;
import com.karcompany.presenters.UserRepositoriesPresenter;
import com.karcompany.presenters.UserRepositoriesPresenterImpl;

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

	@Provides @Singleton
	UserProfileHeaderPresenter provideUserProfileHeaderPresenter(ApiRepo apiRepo, BrowseUsersPresenter browseUsersPresenter) {
		return new UserProfileHeaderPresenterImpl(apiRepo, browseUsersPresenter);
	}

	@Provides @Singleton
	UserRepositoriesPresenter provideUserRepositoriesPresenter(ApiRepo apiRepo, BrowseUsersPresenter browseUsersPresenter) {
		return new UserRepositoriesPresenterImpl(apiRepo, browseUsersPresenter);
	}
}
