package com.karcompany.presenters;

import com.karcompany.models.UserMetaData;
import com.karcompany.mvputils.Presenter;
import com.karcompany.views.BrowseUsersView;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Presenter interface.
 *
 */

public interface BrowseUsersPresenter extends Presenter {

	void setView(BrowseUsersView browseUsersView);

	boolean isLoading();

	void loadUsers(long since);

	void onUserSelected(UserMetaData userMetaData);

	UserMetaData getCurrentUser();

}
