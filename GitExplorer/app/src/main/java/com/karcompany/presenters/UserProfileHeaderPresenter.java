package com.karcompany.presenters;

import com.karcompany.models.UserMetaData;
import com.karcompany.mvputils.Presenter;
import com.karcompany.views.BrowseUsersView;
import com.karcompany.views.UserProfileHeaderView;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Presenter interface.
 *
 */

public interface UserProfileHeaderPresenter extends Presenter {

	void setView(UserProfileHeaderView headerView);

}
