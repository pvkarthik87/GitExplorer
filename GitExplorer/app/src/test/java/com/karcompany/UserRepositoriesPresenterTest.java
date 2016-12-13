package com.karcompany;

import com.karcompany.models.RepositoryDetails;
import com.karcompany.models.UserDetails;
import com.karcompany.models.UserMetaData;
import com.karcompany.networking.ApiRepo;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.BrowseUsersPresenterImpl;
import com.karcompany.presenters.UserRepositoriesPresenter;
import com.karcompany.presenters.UserRepositoriesPresenterImpl;
import com.karcompany.views.UserRepositoriesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class UserRepositoriesPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private UserRepositoriesView view;

	private BrowseUsersPresenter browseUsersPresenter;

	private UserRepositoriesPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		browseUsersPresenter = new BrowseUsersPresenterImpl(model);
		presenter = new UserRepositoriesPresenterImpl(model, browseUsersPresenter);
		presenter.setView(view);
		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				RepositoryDetails[] repositoryDetails = new RepositoryDetails[0];
				((ApiRepo.UserReposCallback) presenter).onSuccess(repositoryDetails);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getUserRepositories("http://kick.com", (ApiRepo.UserReposCallback) presenter);
	}

	/**
	 Verify if model.getUserDetails was not called.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchUserReposWhenUserNotSelected() {
		presenter.loadData();
		// verify can be called only on mock objects
		verify(model, times(0)).getUserRepositories("http://kick.com", (ApiRepo.UserReposCallback) presenter);
		//verify(view, times(0)).onDataReceived(any(UserDetails.class));
	}

	/**
	 Verify if model.getUserDetails was not called.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchUserReposWhenUserSelected() {
		UserMetaData user = new UserMetaData();
		user.setReposUrl("http://kick.com");
		browseUsersPresenter.onUserSelected(user);
		presenter.loadData();
		// verify can be called only on mock objects
		verify(model, times(1)).getUserRepositories("http://kick.com", (ApiRepo.UserReposCallback) presenter);
		verify(view, times(1)).onDataReceived(any(RepositoryDetails[].class));
	}

}
