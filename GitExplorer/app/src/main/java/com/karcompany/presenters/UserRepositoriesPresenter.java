package com.karcompany.presenters;

import com.karcompany.mvputils.Presenter;
import com.karcompany.views.UserProfileHeaderView;
import com.karcompany.views.UserRepositoriesView;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * Presenter interface.
 *
 */

public interface UserRepositoriesPresenter extends Presenter {

	void setView(UserRepositoriesView view);

	void loadData();

}
