package com.karcompany.networking;

import com.karcompany.logging.DefaultLogger;
import com.karcompany.models.RepositoryDetails;
import com.karcompany.models.UserDetails;
import com.karcompany.models.UserMetaData;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pvkarthik on 2016-12-04.
 *
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

	public Subscription getUserList(long lastPresentUserId, final UserListCallback callback) {
		return mRestService.getUserList(lastPresentUserId)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends UserMetaData[]>>() {
					@Override
					public Observable<? extends UserMetaData[]> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<UserMetaData[]>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						NetworkError ne = new NetworkError(e);
						callback.onError(ne);
						DefaultLogger.d(TAG, "onError "+ne.getAppErrorMessage());
					}

					@Override
					public void onNext(UserMetaData[] userList) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(userList);
					}
				});
	}

	public Subscription getUserDetails(String userName, final UserDataCallback callback) {
		return mRestService.getUserDetails(userName)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends UserDetails>>() {
					@Override
					public Observable<? extends UserDetails> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<UserDetails>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						DefaultLogger.d(TAG, "onError");
						callback.onError(new NetworkError(e));
					}

					@Override
					public void onNext(UserDetails userDetails) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(userDetails);
					}
				});
	}

	public Subscription getUserRepositories(String url, final UserReposCallback callback) {
		return mRestService.getUserRepositories(url)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends RepositoryDetails[]>>() {
					@Override
					public Observable<? extends RepositoryDetails[]> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<RepositoryDetails[]>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						DefaultLogger.d(TAG, "onError");
						callback.onError(new NetworkError(e));
					}

					@Override
					public void onNext(RepositoryDetails[] response) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(response);
					}
				});
	}

	public interface UserListCallback {
		void onSuccess(UserMetaData[] response);

		void onError(NetworkError networkError);
	}

	public interface UserDataCallback {
		void onSuccess(UserDetails response);

		void onError(NetworkError networkError);
	}

	public interface UserReposCallback {
		void onSuccess(RepositoryDetails[] response);

		void onError(NetworkError networkError);
	}

}
