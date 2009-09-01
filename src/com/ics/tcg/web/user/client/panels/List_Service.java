package com.ics.tcg.web.user.client.panels;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ics.tcg.web.user.client.db.AbstractService_Client;
import com.ics.tcg.web.user.client.db.User_Service_Client;
import com.ics.tcg.web.user.client.qos.QosDialog;

public class List_Service extends VerticalPanel {

	String namepre="";
	Image imagepre = new Image();
	/** for add to list */
	Integer select_service_id = -1;
	String select_service_name = "";

	// User_Service_Client selected_service = new User_Service_Client();
	MLabel selectedLabel;

	List_Service(final Panel_Overview overview_panel, final Integer userid) {

		// Create a popup to show the contact info when a contact is clicked
		VerticalPanel contactPopupContainer = new VerticalPanel();
		contactPopupContainer.setSpacing(5);
		final HTML contactInfo = new HTML();
		contactPopupContainer.add(contactInfo);
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(15);
		final Hyperlink edit = new Hyperlink("edit", "edit");
		final Hyperlink delete = new Hyperlink("delete", "delete");
		horizontalPanel.add(edit);
		horizontalPanel.add(delete);
		contactPopupContainer.add(horizontalPanel);
		final PopupPanel contactPopup = new PopupPanel(true, false);
		contactPopup.setWidget(contactPopupContainer);

		// container.setHeight("200");
		final VerticalPanel content = new VerticalPanel();
		content.setSpacing(3);
		content.setWidth("100%");
		HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setWidth("161");
		Hyperlink add = new Hyperlink("add", "add");
		add.setWidth("25");
		DOM.setStyleAttribute(add.getElement(), "fontFamily", "sans-serif");
		DOM.setStyleAttribute(add.getElement(), "fontSize", "10pt");
		toolbar.add(add);
		DOM.setStyleAttribute(toolbar.getElement(), "backgroundColor",
				"#d0e4f6");
		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("161", "165");
		scrollPanel.add(content);
		scrollPanel.scrollToBottom();

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DialogBox dialogBox = new DialogBox();
				dialogBox.addStyleName("g-DialogBox");
				AbsolutePanel absolutePanel = new AbsolutePanel();
				absolutePanel.setPixelSize(400, 300);
				dialogBox.setWidget(absolutePanel);
				dialogBox.setText("Add Service");
				DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");

				int height = (int) Window.getClientHeight() / 2 - 200;
				int width = (int) Window.getClientWidth() / 2 - 250;
				dialogBox.setPopupPosition(width, height);

				final ScrollPanel scrollPanel = new ScrollPanel();
				scrollPanel.setSize("350", "240");
				final FlexTable flexTable = new FlexTable();
				scrollPanel.add(flexTable);
				scrollPanel.scrollToRight();
				HorizontalPanel confirm = new HorizontalPanel();
				confirm.setSpacing(10);
				final Button ok = new Button("Save");
				DOM.setStyleAttribute(ok.getElement(), "fontSiz3", "10pt");
				ok.removeStyleName("gwt-Button");
				final Button cancel = new Button("Cancel");
				DOM.setStyleAttribute(cancel.getElement(), "fontSiz3", "10pt");
				cancel.removeStyleName("gwt-Button");

				confirm.add(ok);
				confirm.add(cancel);
				absolutePanel.add(scrollPanel, 25, 25);
				absolutePanel.add(confirm, 25, 240);

				overview_panel.service_Service
						.getAllAbservices(new AsyncCallback<List<AbstractService_Client>>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("fail");
							}

							@Override
							public void onSuccess(
									List<AbstractService_Client> result) {

								for (int i = 0; i < result.size(); i++) {
									final AbstractService_Client abstractService_Client = result
											.get(i);
									final Image image = new Image();
									image.setUrl("xml/"
											+ abstractService_Client.asname
											+ ".jpg");
									image.setSize("80", "40");
									flexTable.setCellSpacing(20);
									flexTable.setWidget(i / 3, i % 3, image);
									image.addClickHandler(new ClickHandler() {
										@Override
										public void onClick(ClickEvent event) {
											imagepre.setUrl(namepre);
											image
													.setUrl("img/select_service.png");
											namepre = "xml/"
													+ abstractService_Client.asname
													+ ".jpg";
											imagepre = image;
											select_service_id = abstractService_Client.asid;
											select_service_name = abstractService_Client.asname;
										}
									});
									image
											.setTitle(abstractService_Client.asname);
								}

							}

						});

				ok.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						overview_panel.service_Service.check(
								overview_panel.userid, select_service_id,
								new AsyncCallback<Integer>() {
									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(Integer result) {
										if (result == 1) {
											Window
													.alert("It's already in the list!");
										} else {
											overview_panel.service_Service
													.saveUserAbservice(
															userid,
															select_service_id,
															select_service_name,
															new AsyncCallback<User_Service_Client>() {

																@Override
																public void onFailure(
																		Throwable caught) {
																	Window
																			.alert("fail to add service");
																}

																@Override
																public void onSuccess(
																		User_Service_Client result) {

																	overview_panel.user_Service_Clients
																			.add(result);
																	MLabel label = new MLabel(
																			result);
																	content
																			.add(label);
																	label
																			.addMouseOverHandler(new ServiceLabelHandler(
																					label,
																					contactInfo,
																					contactPopup));

																	dialogBox
																			.hide();
																}
															});
										}
									}

								});

					}

				});

				cancel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						dialogBox.hide();
					}
				});

				dialogBox.show();
			}

		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				QosDialog imageViewer = new QosDialog(
						selectedLabel.userServiceClient);
				// imageViewer.setSize("500", "400");
				imageViewer.setText("Qos Config");
				int height = (int) Window.getClientHeight() / 2 - 200;
				int width = (int) Window.getClientWidth() / 2 - 300;
				imageViewer.setPopupPosition(width, height);
				contactPopup.hide();
				imageViewer.show();

			}
		});

		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overview_panel.service_Service.deleteUserAbservice(userid,
						selectedLabel.userServiceClient.abserviceid,
						new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("failure");
							}

							@Override
							public void onSuccess(String result) {
								overview_panel.user_Service_Clients
										.remove(selectedLabel.userServiceClient);
								content.remove(selectedLabel);
								contactPopup.hide();
								Window.alert(result);
							}
						});
			}
		});

		overview_panel.service_Service.getUserAbservices(userid,
				new AsyncCallback<List<User_Service_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<User_Service_Client> result) {
						if (result == null) {

						} else {
							for (int i = 0; i < result.size(); i++) {
								// add to the list
								overview_panel.user_Service_Clients.add(result
										.get(i));

								final MLabel label = new MLabel(result.get(i));
								content.add(label);

								label
										.addMouseOverHandler(new ServiceLabelHandler(
												label, contactInfo,
												contactPopup));
							}
						}
					}

				});

		this.add(scrollPanel);
		this.add(toolbar);

	}

	// label handler
	class ServiceLabelHandler implements MouseOverHandler {
		MLabel label;
		String account;
		HTML contactInfo;
		PopupPanel contactPopup;
		User_Service_Client user_Service_client;

		ServiceLabelHandler(MLabel label, HTML contactInfo,
				PopupPanel contactPopup) {
			this.label = label;
			this.contactInfo = contactInfo;
			this.contactPopup = contactPopup;
			this.user_Service_client = label.userServiceClient;

		}

		@Override
		public void onMouseOver(MouseOverEvent event) {

			// contactPopup.hide();
			DOM.setStyleAttribute(label.getElement(), "color", "black");
			DOM.setStyleAttribute(label.getElement(), "filter",
					" Alpha(Opacity=50)");
			selectedLabel = label;
			// Set the info about the contact
			contactInfo.setHTML(user_Service_client.abservicename + "<br><i>"
					+ "fuction:" + "</i><br>");
			// Show the popup of contact info
			int left = label.getAbsoluteLeft() + 165;
			int top = label.getAbsoluteTop();
			contactPopup.setPopupPosition(left, top);
			contactPopup.show();

		}

	}

	// label
	class MLabel extends Label {

		User_Service_Client userServiceClient;

		public MLabel(User_Service_Client userServiceClient) {
			super(userServiceClient.abservicename);
			this.userServiceClient = userServiceClient;
			sinkEvents(Event.ONMOUSEOUT);
			setWidth("100%");
			DOM.setStyleAttribute(this.getElement(), "cursor", "hand");
			DOM.setStyleAttribute(this.getElement(), "fontSize", "10pt");
			DOM.setStyleAttribute(this.getElement(), "color", "white");
			DOM.setStyleAttribute(this.getElement(), "padding", "2px");
			// add color
			DOM.setStyleAttribute(this.getElement(), "background", "#"
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf))
					+ Integer.toHexString(Random.nextInt(0xf)));
		}

		public void onBrowserEvent(Event event) {

			if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
				DOM.setStyleAttribute(this.getElement(), "filter",
						" Alpha(Opacity=100)");
				DOM.setStyleAttribute(this.getElement(), "color", "white");
			}
			super.onBrowserEvent(event);
		}
	}

}
