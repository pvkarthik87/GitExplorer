package com.karcompany.views.adapters;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Recycler view adapter which displays data.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.GitExplorerApplication;
import com.karcompany.R;
import com.karcompany.models.RepositoryDetails;
import com.karcompany.models.UserMetaData;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.UserRepositoriesPresenter;
import com.karcompany.utils.GlideUtils;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class BrowseUserReposAdapter extends RecyclerView.Adapter<UserListItemViewHolder> {

	private static final int VIEW_TYPE_ODD = 100;
	private static final int VIEW_TYPE_EVEN = 101;

	private Fragment mFragment;
	private Context mContext;
	private Map<Long, RepositoryDetails> mDataMap;
	private List<Long> mDataList;

	@Inject
	UserRepositoriesPresenter mUserRepositoriesPresenter;

	public BrowseUserReposAdapter(Fragment fragment) {
		GitExplorerApplication.getApplicationComponent().inject(this);
		mFragment = fragment;
		mContext = fragment.getContext();
		mDataMap = new LinkedHashMap<>();
		mDataList = new ArrayList<>(4);
	}

	@Override
	public UserListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.view_user_repo_row_item, parent, false);
		return new UserListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(UserListItemViewHolder holder, int position) {
		if(position < mDataList.size()) {
			RepositoryDetails repositoryDetails = mDataMap.get(mDataList.get(position));
			if(repositoryDetails != null) {
				holder.nameTxtView.setText(repositoryDetails.getName());
			} else {
				holder.nameTxtView.setText("");
			}
		}
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

	public void addData(RepositoryDetails[] repos) {
		if(repos != null) {
			for (RepositoryDetails repositoryDetails:repos) {
				mDataMap.put(repositoryDetails.getId(), repositoryDetails);
			}
			int oldSize = mDataList.size();
			mDataList.clear();
			mDataList.addAll(mDataMap.keySet());
			int newSize = mDataList.size();
			if(oldSize > 0) {
				notifyItemRangeInserted(oldSize, newSize - oldSize);
			} else {
				notifyDataSetChanged();
			}
		}
	}

	public void loadData() {
		mUserRepositoriesPresenter.loadData();
	}

	public void clearData() {
		mDataList.clear();
		mDataMap.clear();
	}
}
