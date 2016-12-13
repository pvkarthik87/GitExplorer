package com.karcompany.views.fragments;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.R;
import com.karcompany.di.components.ApplicationComponent;
import com.karcompany.logging.DefaultLogger;
import com.karcompany.models.UserMetaData;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.ViewType;
import com.karcompany.views.BrowseUsersView;
import com.karcompany.views.activities.UserProfileActivity;
import com.karcompany.views.adapters.BrowseUsersAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class BrowseUsersFragment extends BaseFragment implements BrowseUsersView {

	private static final String TAG = DefaultLogger.makeLogTag(BrowseUsersFragment.class);

	private static final String CURRENT_VIEW_TYPE = "CURRENT_VIEW_TYPE";

	@Bind(R.id.user_list)
	RecyclerView mUserRecyclerView;

	@Inject
	BrowseUsersPresenter mBrowseUsersPresenter;

	@Bind(R.id.fabbutton)
	FloatingActionButton mFabBtn;

	private BrowseUsersAdapter mAdapter;
	private ViewType mCurrentViewType = ViewType.LIST;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_browse_users, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_VIEW_TYPE)) {
			mCurrentViewType = ViewType.get(savedInstanceState.getString(CURRENT_VIEW_TYPE, ViewType.LIST.toString()));
		}
		setUpPresenter();
		setUpFabBtn();
		mAdapter = new BrowseUsersAdapter(this, mUserRecyclerView);
		setUpRecyclerView();
		mAdapter.enableLoadMore();
	}

	private void setUpPresenter() {
		mBrowseUsersPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		if(mCurrentViewType == ViewType.LIST) {
			LinearLayoutManager layoutManager = new LinearLayoutManager(
					getActivity(), LinearLayoutManager.VERTICAL, false);
			mUserRecyclerView.setLayoutManager(layoutManager);
		} else {
			GridLayoutManager layoutManager = new GridLayoutManager(
					getActivity(), 2);
			mUserRecyclerView.setLayoutManager(layoutManager);
		}
		mUserRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mBrowseUsersPresenter.onStart();
		mAdapter.loadData();
	}

	@Override
	public void onResume() {
		super.onResume();
		mBrowseUsersPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mBrowseUsersPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mBrowseUsersPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBrowseUsersPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mUserRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(UserMetaData[] userList) {
		mAdapter.addData(userList);
	}

	@Override
	public void onFailure(String errorMsg) {

	}

	@OnClick(R.id.fabbutton)
	public void onToogleViewClicked() {
		if(mCurrentViewType == ViewType.LIST) {
			mCurrentViewType = ViewType.GRID;
		} else {
			mCurrentViewType = ViewType.LIST;
		}
		setUpRecyclerView();
		setUpFabBtn();
	}

	private void setUpFabBtn() {
		if(mCurrentViewType == ViewType.LIST) {
			mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_view_list_white_36dp));
		} else {
			mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_grid_on_white_36dp));
		}
	}

	@Override
	public void onUserSelected(UserMetaData userMetaData) {
		Intent intent = new Intent(getActivity(), UserProfileActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CURRENT_VIEW_TYPE, mCurrentViewType.getCode());
		outState.putString(CURRENT_VIEW_TYPE, mCurrentViewType.getCode());
	}
}
