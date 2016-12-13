package com.karcompany.views;

import com.karcompany.models.RepositoryDetails;
import com.karcompany.models.UserDetails;
import com.karcompany.models.UserMetaData;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface UserRepositoriesView {

	void onDataReceived(RepositoryDetails[] repos);

	void onFailure(String errorMsg);

}
