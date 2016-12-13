package com.karcompany.presenters;

import com.karcompany.models.UserMetaData;
import com.karcompany.networking.ApiRepo;
import com.karcompany.networking.NetworkError;
import com.karcompany.views.BrowseUsersView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Presenter implementation which handles core features.
 */

public class BrowseUsersPresenterImpl implements BrowseUsersPresenter, ApiRepo.UserListCallback {

	private BrowseUsersView mView;

	@Inject
	ApiRepo mApiRepo;

	private boolean mIsLoading;
	private CompositeSubscription subscriptions;
	private UserMetaData mSelectedUser;

	@Inject
	public BrowseUsersPresenterImpl(ApiRepo apiRepo) {
		mApiRepo = apiRepo;
		this.subscriptions = new CompositeSubscription();
	}

	@Override
	public void setView(BrowseUsersView browseUsersView) {
		mView = browseUsersView;
		subscriptions = new CompositeSubscription();
		mSelectedUser = null;
	}

	@Override
	public void onStart() {

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
		mSelectedUser = null;
		if(subscriptions != null) {
			subscriptions.unsubscribe();
		}
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	@Override
	public void loadUsers(long since) {
		mIsLoading = true;
		Subscription subscription = mApiRepo.getUserList(since, this);
		subscriptions.add(subscription);
	}

	@Override
	public void onUserSelected(UserMetaData userMetaData) {
		mSelectedUser = userMetaData;
		if(mView != null) {
			mView.onUserSelected(userMetaData);
		}
	}

	@Override
	public void onSuccess(UserMetaData[] response) {
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

	@Override
	public UserMetaData getCurrentUser() {
		return mSelectedUser;
	}
}
