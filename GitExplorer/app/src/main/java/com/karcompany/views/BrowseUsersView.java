package com.karcompany.views;

import com.karcompany.models.UserMetaData;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface BrowseUsersView {

	void onDataReceived(UserMetaData[] userList);

	void onFailure(String errorMsg);

	void onUserSelected(UserMetaData userMetaData);

}
