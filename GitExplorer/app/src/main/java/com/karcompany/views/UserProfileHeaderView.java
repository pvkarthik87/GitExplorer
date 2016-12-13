package com.karcompany.views;

import com.karcompany.models.UserDetails;
import com.karcompany.models.UserMetaData;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface UserProfileHeaderView {

	void onDataReceived(UserDetails userDetails);

	void onFailure(String errorMsg);

}
