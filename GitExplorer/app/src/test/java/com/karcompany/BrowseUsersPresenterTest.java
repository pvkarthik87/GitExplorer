package com.karcompany;

import com.karcompany.models.RepositoryDetails;
import com.karcompany.models.UserMetaData;
import com.karcompany.networking.ApiRepo;
import com.karcompany.presenters.BrowseUsersPresenter;
import com.karcompany.presenters.BrowseUsersPresenterImpl;
import com.karcompany.views.BrowseUsersView;

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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class BrowseUsersPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private BrowseUsersView view;

	private BrowseUsersPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new BrowseUsersPresenterImpl(model);
		presenter.setView(view);
		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				UserMetaData userMetaData = new UserMetaData();
				UserMetaData[] users = new UserMetaData[1];
				users[0] = userMetaData;
				((ApiRepo.UserListCallback) presenter).onSuccess(users);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getUserList(0, (ApiRepo.UserListCallback) presenter);
	}

	/**
	 Verify if model.getUserList was called once.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchUsers() {
		presenter.loadUsers(0);
		// verify can be called only on mock objects
		verify(model, times(1)).getUserList(0, (ApiRepo.UserListCallback) presenter);
		verify(view, times(1)).onDataReceived(any(UserMetaData[].class));
	}

}
