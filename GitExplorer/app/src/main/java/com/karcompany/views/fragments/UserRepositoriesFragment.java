package com.karcompany.views.fragments;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.R;
import com.karcompany.di.components.ApplicationComponent;
import com.karcompany.logging.DefaultLogger;
import com.karcompany.models.RepositoryDetails;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.UserRepositoriesPresenter;
import com.karcompany.presenters.ViewType;
import com.karcompany.views.UserRepositoriesView;
import com.karcompany.views.adapters.BrowseUserReposAdapter;
import com.karcompany.views.adapters.BrowseUsersAdapter;

import javax.inject.Inject;

import butterknife.Bind;

public class UserRepositoriesFragment extends BaseFragment implements UserRepositoriesView {

	private static final String TAG = DefaultLogger.makeLogTag(UserRepositoriesFragment.class);

	@Bind(R.id.repoList)
	RecyclerView mReposRecyclerView;

	@Inject
	UserRepositoriesPresenter mUserRepositoriesPresenter;

	private BrowseUserReposAdapter mAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user_repositories, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
		mAdapter = new BrowseUserReposAdapter(this);
		setUpRecyclerView();
	}

	private void setUpPresenter() {
		mUserRepositoriesPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mReposRecyclerView.setLayoutManager(layoutManager);
		mReposRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mUserRepositoriesPresenter.onStart();
		mAdapter.loadData();
	}

	@Override
	public void onResume() {
		super.onResume();
		mUserRepositoriesPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mUserRepositoriesPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mUserRepositoriesPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUserRepositoriesPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mReposRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(RepositoryDetails[] repos) {
		mAdapter.addData(repos);
	}

	@Override
	public void onFailure(String errorMsg) {

	}
}
