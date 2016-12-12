package com.karcompany.networking;

import com.karcompany.config.Constants;
import com.karcompany.models.UserDetails;
import com.karcompany.models.UserMetaData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pvkarthik on 2016-12-04.
 *
 * REST service interface which Retrofit uses to communicate to a rest end point.
 */

public interface RestService {

	@GET("/users?per_page="+ Constants.NUM_ITEMS_IN_PAGE)
	Observable<UserMetaData[]> getUserList(@Query("since") long lastPresentUserId);

	@GET("/users/{userName}")
	Observable<UserDetails> getUserDetails(@Path("userName") String userName);

}