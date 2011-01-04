/**
 * 15.11.2010
 * created by kurt
 */
package de.kurka.mobile.showcase.client.activities;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.kurka.gwt.mobile.dom.client.event.touch.simple.SimpleTouchHandler;
import de.kurka.gwt.mobile.ui.client.widget.celllist.CellSelectedEvent;
import de.kurka.gwt.mobile.ui.client.widget.celllist.CellSelectedHandler;
import de.kurka.mobile.showcase.client.ClientFactory;
import de.kurka.mobile.showcase.client.Topic;
import de.kurka.mobile.showcase.client.places.AboutPlace;
import de.kurka.mobile.showcase.client.places.AnimationPlace;
import de.kurka.mobile.showcase.client.places.UIPlace;

/**
 * @author kurt
 *
 */
public class HomeActivity extends AbstractActivity {

	private final ClientFactory clientFactory;
	private HandlerRegistration addCellSelectedHandler;
	private HandlerRegistration addSimpleTouchHandler;

	public HomeActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		HomeView view = clientFactory.getHomeView();

		view.setTitle("mobile gwt");
		view.setRightButtonText("about");

		view.getFirstHeader().setText("Showcase");

		view.setTopics(createTopicsList());

		panel.setWidget(view);

		addCellSelectedHandler = view.getCellSelectedHandler().addCellSelectedHandler(new CellSelectedHandler() {

			@Override
			public void onCellSelected(CellSelectedEvent event) {
				int index = event.getIndex();
				if (index == 0) {
					clientFactory.getPlaceController().goTo(new UIPlace());
					return;
				}
				if (index == 1) {
					clientFactory.getPlaceController().goTo(new AnimationPlace());
					return;
				}

			}
		});

		addSimpleTouchHandler = view.getAboutButton().addSimpleTouchHandler(new SimpleTouchHandler() {

			@Override
			public void onTouch() {
				clientFactory.getPlaceController().goTo(new AboutPlace());

			}
		});
	}

	@Override
	public void onStop() {
		super.onStop();
		if (addCellSelectedHandler != null) {
			addCellSelectedHandler.removeHandler();
			addCellSelectedHandler = null;
		}

		if (addSimpleTouchHandler != null) {
			addSimpleTouchHandler.removeHandler();
			addSimpleTouchHandler = null;
		}

	}

	private List<Topic> createTopicsList() {
		ArrayList<Topic> list = new ArrayList<Topic>();

		list.add(new Topic("UI", 5));
		list.add(new Topic("Animations", 5));
		list.add(new Topic("Credits", 5));

		return list;
	}

}