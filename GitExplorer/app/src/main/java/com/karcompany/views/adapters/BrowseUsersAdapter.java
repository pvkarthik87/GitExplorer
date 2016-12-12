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
import com.karcompany.config.Constants;
import com.karcompany.models.UserMetaData;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.utils.GlideUtils;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class BrowseUsersAdapter extends RecyclerView.Adapter<UserListItemViewHolder> {

	private static final int VIEW_TYPE_ODD = 100;
	private static final int VIEW_TYPE_EVEN = 101;

	private Fragment mFragment;
	private Context mContext;
	private Map<Long, UserMetaData> mDataMap;
	private List<Long> mDataList;
	private RecyclerView mRecyclerView;
	private Paginate mPaginate;

	@Inject
	BrowseUsersPresenter mBrowseUsersPresenter;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			//int itemPosition = mRecyclerView.getChildLayoutPosition(view);
			Long item = mDataList.get(itemPosition);
			//Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
			mBrowseUsersPresenter.onUserSelected(mDataMap.get(item));
		}
	};

	public BrowseUsersAdapter(Fragment fragment, RecyclerView recyclerView) {
		GitExplorerApplication.getApplicationComponent().inject(this);
		mFragment = fragment;
		mContext = fragment.getContext();
		mDataMap = new LinkedHashMap<>();
		mDataList = new ArrayList<>(4);
		mRecyclerView = recyclerView;
	}

	@Override
	public UserListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.view_user_row_item, parent, false);
		return new UserListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(UserListItemViewHolder holder, int position) {
		if(position < mDataList.size()) {
			UserMetaData userMetaData = mDataMap.get(mDataList.get(position));
			if(userMetaData != null) {
				holder.nameTxtView.setText(userMetaData.getLogin());
			} else {
				holder.nameTxtView.setText("");
			}
			GlideUtils.loadImageWithNoCache(mFragment, userMetaData.getAvatarUrl(), holder.profileImgView);
		}
		holder.itemView.setTag(position);
		holder.itemView.setOnClickListener(mOnClickListener);
		/*if(position > mLastPosition) {
			Animation animation = AnimationUtils.loadAnimation(mContext,
					R.anim.up_bottom);
			holder.itemView.startAnimation(animation);
			mLastPosition = position;
		}*/
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

	public void addData(UserMetaData[] userList) {
		if(userList != null) {
			for (UserMetaData userMetaData:userList) {
				mDataMap.put(userMetaData.getId(), userMetaData);
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

	public void enableLoadMore() {
		 mPaginate = Paginate.with(mRecyclerView, mPaginationCallback)
				 .setLoadingTriggerThreshold(10)
				.addLoadingListItem(true)
				.setLoadingListItemCreator(new CustomLoadingListItemCreator())
				.build();
	}

	private Paginate.Callbacks mPaginationCallback = new Paginate.Callbacks() {
		@Override
		public void onLoadMore() {
			// Load next page of data (e.g. network or database)
			loadData();
		}

		@Override
		public boolean isLoading() {
			// Indicate whether new page loading is in progress or not
			return mBrowseUsersPresenter.isLoading();
		}

		@Override
		public boolean hasLoadedAllItems() {
			// Indicate whether all data (pages) are loaded or not
			return isAllPagesLoaded();
		}
	};

	private class CustomLoadingListItemCreator implements LoadingListItemCreator {
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.view_loading, parent, false);
			return new LoadingViewHolder(view);
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			// Bind custom loading row if needed
		}
	}

	public void loadData() {
		if(!isAllPagesLoaded()) {
			mBrowseUsersPresenter.loadPage(getReqPageNumber());
		}
	}

	private long getReqPageNumber() {
		if(mDataList.size() > 0) {
			return mDataList.get(mDataList.size() - 1);
		}
		return 0;
	}

	public void clearData() {
		mDataList.clear();
		mDataMap.clear();
		if(mPaginate != null) {
			mPaginate.unbind();
		}
	}

	private boolean isAllPagesLoaded() {
		return false;
	}
}
