package com.karcompany.views.fragments;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.R;
import com.karcompany.di.components.ApplicationComponent;
import com.karcompany.logging.DefaultLogger;
import com.karcompany.models.UserDetails;
import com.karcompany.presenters.UserProfileHeaderPresenter;
import com.karcompany.utils.GlideUtils;
import com.karcompany.views.UserProfileHeaderView;

import javax.inject.Inject;

import butterknife.Bind;

public class UserProfileHeaderFragment extends BaseFragment implements UserProfileHeaderView {

	private static final String TAG = DefaultLogger.makeLogTag(UserProfileHeaderFragment.class);

	@Inject
	UserProfileHeaderPresenter mUserProfileHeaderPresenter;

	@Bind(R.id.userProfileName)
	TextView mUserProfileName;

	@Bind(R.id.userProfileImage)
	ImageView mUserProfileImgView;

	@Bind(R.id.followersCount)
	TextView mFollowersCountView;

	@Bind(R.id.followingCount)
	TextView mFollowingCountView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user_profile_header, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
	}

	private void setUpPresenter() {
		mUserProfileHeaderPresenter.setView(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mUserProfileHeaderPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mUserProfileHeaderPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mUserProfileHeaderPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mUserProfileHeaderPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUserProfileHeaderPresenter.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(UserDetails userDetails) {
		mUserProfileName.setText(userDetails.getName());
		mFollowersCountView.setText(""+userDetails.getFollowers());
		mFollowingCountView.setText(""+userDetails.getFollowing());
		GlideUtils.loadImageWithNoCache(this, userDetails.getAvatarUrl(), mUserProfileImgView);
	}

	@Override
	public void onFailure(String errorMsg) {

	}
}
