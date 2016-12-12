package com.karcompany.presenters;

import com.karcompany.models.UserMetaData;
import com.karcompany.mvputils.Presenter;
import com.karcompany.views.BrowseUsersView;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Presenter interface.
 *
 */

public interface BrowseUsersPresenter extends Presenter {

	void setView(BrowseUsersView browseUsersView);

	boolean isLoading();

	void loadPage(long pageNo);

	void onUserSelected(UserMetaData userMetaData);

}
