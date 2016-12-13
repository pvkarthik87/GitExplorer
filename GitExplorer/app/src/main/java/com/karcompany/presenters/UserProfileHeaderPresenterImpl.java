package com.karcompany.presenters;

import com.karcompany.models.UserDetails;
import com.karcompany.models.UserMetaData;
import com.karcompany.networking.ApiRepo;
import com.karcompany.networking.NetworkError;
import com.karcompany.views.UserProfileHeaderView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Presenter implementation which handles core features.
 */

public class UserProfileHeaderPresenterImpl implements UserProfileHeaderPresenter, ApiRepo.UserDataCallback {

	private UserProfileHeaderView mView;

	@Inject
	ApiRepo mApiRepo;

	private BrowseUsersPresenter mBrowseUsersPresenter;

	private boolean mIsLoading;
	private CompositeSubscription subscriptions;

	@Inject
	public UserProfileHeaderPresenterImpl(ApiRepo apiRepo, BrowseUsersPresenter browseUsersPresenter) {
		mApiRepo = apiRepo;
		mBrowseUsersPresenter = browseUsersPresenter;
		this.subscriptions = new CompositeSubscription();
	}

	@Override
	public void setView(UserProfileHeaderView headerView) {
		mView = headerView;
		subscriptions = new CompositeSubscription();
	}

	@Override
	public void onStart() {
		loadData();
	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
		if(subscriptions != null) {
			subscriptions.unsubscribe();
		}
	}

	@Override
	public void onSuccess(UserDetails response) {
		mIsLoading = false;
		if (mView != null) {
			mView.onDataReceived(response);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}

	private void loadData() {
		UserMetaData currentSelectedUser = mBrowseUsersPresenter.getCurrentUser();
		if(currentSelectedUser != null) {
			mIsLoading = true;
			Subscription subscription = mApiRepo.getUserDetails(currentSelectedUser.getLogin(), this);
			subscriptions.add(subscription);
		}
	}
}
