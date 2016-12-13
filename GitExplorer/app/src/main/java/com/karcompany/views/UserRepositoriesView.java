package com.karcompany.views;

import com.karcompany.models.RepositoryDetails;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface UserRepositoriesView {

	void onDataReceived(RepositoryDetails[] repos);

	void onFailure(String errorMsg);

}
