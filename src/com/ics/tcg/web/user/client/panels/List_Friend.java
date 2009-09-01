package com.ics.tcg.web.user.client.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.gwtlib.client.table.ColumnLayout;
import org.gwtlib.client.table.ContentProvider;
import org.gwtlib.client.table.Row;
import org.gwtlib.client.table.Rows;
import org.gwtlib.client.table.ui.Column;
import org.gwtlib.client.table.ui.SourcesTableEvents;
import org.gwtlib.client.table.ui.Table;
import org.gwtlib.client.table.ui.TableListenerAdapter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.db.Friends_Client;
import com.ics.tcg.web.user.client.db.User_Client;

@SuppressWarnings("deprecation")
public class List_Friend extends VerticalPanel {

	/** add select friend */
	String select_name = "";
	Integer select_id = -1;

	/** all the friends labels */
	ArrayList<MLabel> labels = new ArrayList<MLabel>();

	/** select label */
	MLabel selectedLabel;

	/** overview panel */
	Panel_Overview overview_panel;
	VerticalPanel content;
	PopupPanel contactPopup;
	HTML contactInfo;
	/** dialog box */
	DialogBox dialogBox;
	Grid grid;
	Table dia_table;

	List_Friend(Panel_Overview panel_Overview) {

		this.overview_panel = panel_Overview;

		// Create a pop up to show the contact info when a contact is clicked
		final VerticalPanel contactPopupContainer = new VerticalPanel();
		contactPopup = new PopupPanel(true, false);
		contactPopupContainer.setSpacing(5);
		contactInfo = new HTML();
		final Hyperlink delete = new Hyperlink("delete", "delete");
		contactPopupContainer.add(contactInfo);
		contactPopupContainer.add(delete);
		contactPopup.setWidget(contactPopupContainer);

		// container include content and tool bar
		content = new VerticalPanel();
		content.setSpacing(3);
		content.setWidth("100%");
		final HorizontalPanel toolbar = new HorizontalPanel();
		toolbar.setWidth("161");
		DOM.setStyleAttribute(toolbar.getElement(), "backgroundColor",
				"#d0e4f6");

		// add a friend
		Hyperlink add = new Hyperlink("add", "add");
		add.setWidth("25");
		DOM.setStyleAttribute(add.getElement(), "fontSize", "10pt");

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				// pop up the add friends dialog box
				dialogBox = new DialogBox();
				dialogBox.addStyleName("g-DialogBox");
				dialogBox.setText("Add A Friend");
				dialogBox.setSize("400", "300");
				DOM.setStyleAttribute(dialogBox.getElement(), "border", "0px");
				int height = (int) Window.getClientHeight() / 2 - 150;
				int width = (int) Window.getClientWidth() / 2 - 200;
				dialogBox.setPopupPosition(width, height);

				// hold the dialog content
				final AbsolutePanel dia_content = new AbsolutePanel();
				dia_content.setSize("400", "300");
				dialogBox.add(dia_content);
				{
					final Label label = new Label("    Search Mode");
					DOM.setStyleAttribute(label.getElement(),
							"backgroundColor", "#C3D9FF");
					label.setWidth("100%");

					// select normal or advanced
					final VerticalPanel selectPanel = new VerticalPanel();
					RadioButton simpleButton = new RadioButton("radio1");
					{
						simpleButton.setHeight("20");
						RadioButton advButton = new RadioButton("radio1");
						advButton.setHeight("20");
						simpleButton.setText("Search By Account");
						advButton.setText("Advanced Search");
						DOM.setStyleAttribute(simpleButton.getElement(),
								"fontSize", "10pt");
						DOM.setStyleAttribute(advButton.getElement(),
								"fontSize", "10pt");

						selectPanel.add(simpleButton);
						selectPanel.add(advButton);
						simpleButton.setValue(true);
					}

					// input query data
					final VerticalPanel inputPanel = new VerticalPanel();
					Label idLabel = new Label("Account:");
					final TextBox idTextBox = new TextBox();
					{
						idLabel = new Label("Account:");
						DOM.setStyleAttribute(idLabel.getElement(), "fontSize",
								"10pt");
						inputPanel.add(idLabel);
						idTextBox.setWidth("200");
						DOM.setStyleAttribute(idTextBox.getElement(),
								"fontSize", "10pt");
						inputPanel.add(idTextBox);
					}

					HTML space = new HTML("&nbsp;");
					space.setHeight("30px");
					selectPanel.add(space);

					// result grid
					final Grid gridPanel = createGrid();// means create table
					dia_table = (Table) gridPanel.getWidget(0, 0);

					gridPanel.setVisible(false);
					final VerticalPanel verticalPanel = new VerticalPanel();
					gridPanel.setSize("370", "100");
					verticalPanel.add(gridPanel);

					dia_content.add(label, 0, 10);
					dia_content.add(selectPanel, 30, 40);
					dia_content.add(inputPanel, 35, 110);
					dia_content.add(verticalPanel, 15, 40);

					final AbsolutePanel confirm = new AbsolutePanel();
					confirm.setSize("200", "24");

					final Button pre = new Button("Previous");
					final Button ok = new Button("Search");
					DOM.setStyleAttribute(pre.getElement(), "fontSize", "9pt");
					DOM.setStyleAttribute(ok.getElement(), "fontSize", "9pt");
					ok.removeStyleName("gwt-Button");
					pre.removeStyleName("gwt-Button");

					pre.setVisible(false);
					pre.setWidth("60");
					DOM.setStyleAttribute(pre.getElement(), "fontSize", "10pt");
					pre.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							label.setVisible(true);
							selectPanel.setVisible(true);
							inputPanel.setVisible(true);
							pre.setVisible(false);
							gridPanel.setVisible(false);
							ok.setText("Search");
						}
					});

					ok.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {

							if (ok.getText().equals("Search")) {

								select_id = -1;
								select_name = "";

								final String account = idTextBox.getText();
								// search the user
								overview_panel.friend_Service.getFriendInfo(
										account,
										new AsyncCallback<User_Client>() {
											@Override
											public void onFailure(Throwable arg0) {
												Window.alert("fail to get");
											}

											@Override
											public void onSuccess(
													User_Client result) {
												if (result == null) {
													label.setVisible(false);
													selectPanel
															.setVisible(false);
													inputPanel
															.setVisible(false);
													gridPanel.setVisible(true);
													pre.setVisible(true);

												} else {

													// row data
													final Row[] rows = new Row[1];
													for (int i = 0; i < rows.length; ++i) {

														String name = result
																.getAccount();
														Integer age = result
																.getAge();
														Boolean sex = result
																.getSex();
														String email = result
																.getEmail();
														Integer id = result
																.getUserid();
														rows[i] = new Row(i,
																new Object[] {
																		name,
																		age,
																		sex,
																		email,
																		id });
													}
													ContentProvider provider = new ContentProvider() {
														// Simulate retrieval of
														// sample data, in
														// requested sort order
														public void load(
																int begin,
																int end,
																final int sortId,
																boolean ascending) {
															final int sign = ascending ? 1
																	: -1;
															Row[] tmp = new Row[rows.length];
															for (int i = 0; i < rows.length; ++i)
																tmp[i] = rows[i];
															switch (sortId) {
															case 0:
																Arrays
																		.sort(
																				tmp,
																				new Comparator<Row>() {
																					public int compare(
																							Row o1,
																							Row o2) {
																						String v1 = (String) o1
																								.getValue(sortId);
																						String v2 = (String) o2
																								.getValue(sortId);
																						return sign
																								* (v1
																										.compareTo(v2));
																					}
																				});
																break;
															default:
																break;
															}
															Row[] srows = new Row[Math
																	.min(
																			end
																					- begin,
																			tmp.length
																					- begin)];
															for (int i = 0; i < srows.length; ++i)
																srows[i] = tmp[begin
																		+ i];
															dia_table
																	.onSuccess(new Rows(
																			srows,
																			begin,
																			sortId,
																			ascending));
														}
													};
													dia_table
															.setContentProvider(provider);
													dia_table
															.addTableListener(new TableListenerAdapter() {

																public void onRowClicked(
																		SourcesTableEvents sender,
																		Row row) {
																	for (int i = 0; i < rows.length; ++i)
																		rows[i]
																				.setState(Row.State.NONE);
																	row
																			.setState(Row.State.SELECT);
																	dia_table
																			.refreshRowState();
																	select_name = (String) row
																			.getValue(0);
																	select_id = (Integer) row
																			.getValue(4);
																}
															});
													dia_table.update();
													// select_name = (String)
													// rows[0]
													// .getValue(0);
													// select_id = (Integer)
													// rows[0]
													// .getValue(4);
												}

												label.setVisible(false);
												selectPanel.setVisible(false);
												inputPanel.setVisible(false);
												gridPanel.setVisible(true);
												pre.setVisible(true);
												ok.setText("Add");
											}
										});

							} else {
								if (select_id == -1) {
									Window.alert("You haven't select");
								} else if (overview_panel.userid == select_id) {
									Window.alert("You can't add yourself!");
								} else {
									overview_panel.friend_Service.check(
											overview_panel.userid, select_id,
											new AsyncCallback<Integer>() {
												@Override
												public void onFailure(
														Throwable caught) {
												}

												@Override
												public void onSuccess(
														Integer result) {

													if (result == 0) {
														saveFriend();
													}
													if (result == 1) {
														Window
																.alert("This person is already your friend!");
													}
												}
											});
								}

							}
						}

					});

					ok.setWidth("60");
					DOM.setStyleAttribute(ok.getElement(), "fontSize", "10pt");
					Button cancel = new Button("Cancel");
					cancel.removeStyleName("gwt-Button");
					cancel.setWidth("60");
					DOM.setStyleAttribute(cancel.getElement(), "fontSize",
							"10pt");
					cancel.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							dialogBox.hide();
						}
					});

					confirm.add(pre, 0, 0);
					confirm.add(ok, 65, 0);
					confirm.add(cancel, 130, 0);

					dia_content.add(confirm, 180, 260);

				}

				dialogBox.show();
			}

		});

		toolbar.add(add);

		// listBox = new ListBox(true);
		// advancedDisclosure.setWidth("140px");

		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				overview_panel.friend_Service.deleteFriend(
						overview_panel.userid, selectedLabel.friendsClient
								.getFriendid(), new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("failure");
							}

							@Override
							public void onSuccess(String result) {
								labels.remove(selectedLabel);
								content.remove(selectedLabel);
								contactPopup.hide();
							}
						});
			}
		});

		// get all friends
		overview_panel.friend_Service.getAllFriends(overview_panel.userid,
				new AsyncCallback<List<Friends_Client>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("failure to get friends");
					}

					@Override
					public void onSuccess(List<Friends_Client> result) {
						if (result == null) {

						} else {
							// ArrayList<Friends_Client> friends = new
							// ArrayList<Friends_Client>();
							// for (int i = 0; i < result.size(); i++) {
							// friends.add(result.get(i));
							// }
							for (int i = 0; i < result.size(); i++) {
								final MLabel label = new MLabel(result.get(i));
								labels.add(label);
								content.add(label);

								label.addMouseOverHandler(new LabelHandler(
										label, contactInfo, contactPopup));
								label.addMouseOutHandler(new MouseOutHandler() {
									@Override
									public void onMouseOut(MouseOutEvent event) {
										DOM.setStyleAttribute(label
												.getElement(), "filter",
												" Alpha(Opacity=100)");
										DOM
												.setStyleAttribute(label
														.getElement(), "color",
														"white");

									}
								});
							}
						}
					}
				});

		final ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("161", "165");
		scrollPanel.add(content);
		scrollPanel.scrollToRight();
		this.add(scrollPanel);
		this.add(toolbar);

	}

	// label
	class MLabel extends Label {

		Friends_Client friendsClient;

		public MLabel(Friends_Client friendsClient) {
			super(friendsClient.getFriendname());
			this.friendsClient = friendsClient;
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
	}

	// label handler
	class LabelHandler implements MouseOverHandler {
		MLabel label;
		String account;
		HTML contactInfo;
		PopupPanel contactPopup;
		User_Client user;

		LabelHandler(MLabel label, HTML contactInfo, PopupPanel contactPopup) {
			this.label = label;
			this.account = label.friendsClient.getFriendname();
			this.contactInfo = contactInfo;
			this.contactPopup = contactPopup;
			user = new User_Client();
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			DOM.setStyleAttribute(label.getElement(), "color", "black");
			DOM.setStyleAttribute(label.getElement(), "filter",
					" Alpha(Opacity=50)");
			overview_panel.friend_Service.getFriendInfo(account,
					new AsyncCallback<User_Client>() {
						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(User_Client result) {

							contactPopup.hide();
							// if (selectedLabel != null) {
							// selectedLabel
							// .removeStyleName("label-highlight");
							// }

							user = result;
							selectedLabel = (MLabel) label;
							// Set the info about the contact
							contactInfo.setHTML(user.account + "<br><i>"
									+ user.email + "</i><br>" + user.tel);
							// Show the popup of contact info
							int left = label.getAbsoluteLeft() + 165;
							int top = label.getAbsoluteTop();
							contactPopup.setPopupPosition(left, top);
							contactPopup.show();
						}
					});

		}

	}

	private Grid createGrid() {
		final Grid grid = new Grid(1, 1);
		final Table table = createTable();
		table.setSize("100%", "200");
		grid.setWidget(0, 0, table);
		grid.getRowFormatter().setVerticalAlign(0,
				HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setWidth(0, 0, "100%");
		grid.getCellFormatter().setHeight(0, 0, "100%");
		// HorizontalPanel hpanel = new HorizontalPanel();
		grid.setSize("100%", "100%");
		return grid;
	}

	private Table createTable() {
		// Set up the columns we want to be displayed
		final Column[] columns = { new Column(0, false, "Name", "20%"),
				new Column(1, false, "Age", "20%"),
				new Column(2, false, "Sex", "20%"),
				new Column(3, false, "Email", "40%"),
				new Column(4, false, "id", "0%") };
		// Generate some semi-random data for our example
		final Row[] rows = new Row[0];
		// Now configure the table
		ColumnLayout layout = new ColumnLayout(columns);
		final Table table = new Table(layout);
		table.show(4, false);
		ContentProvider provider = new ContentProvider() {
			// Simulate retrieval of sample data, in requested sort order
			public void load(int begin, int end, final int sortId,
					boolean ascending) {
				final int sign = ascending ? 1 : -1;
				Row[] tmp = new Row[rows.length];
				for (int i = 0; i < rows.length; ++i)
					tmp[i] = rows[i];
				switch (sortId) {
				case 0:
					Arrays.sort(tmp, new Comparator<Row>() {
						public int compare(Row o1, Row o2) {
							String v1 = (String) o1.getValue(sortId);
							String v2 = (String) o2.getValue(sortId);
							return sign * (v1.compareTo(v2));
						}
					});
					break;
				case 1:
					Arrays.sort(tmp, new Comparator<Row>() {
						public int compare(Row o1, Row o2) {
							Integer v1 = (Integer) o1.getValue(sortId);
							Integer v2 = (Integer) o2.getValue(sortId);
							return sign * (v1 < v2 ? -1 : (v1 == v2 ? 0 : 1));
						}
					});
					break;
				case 3:
					Arrays.sort(tmp, new Comparator<Row>() {
						public int compare(Row o1, Row o2) {
							String v1 = ((String) o1.getValue(sortId));
							String v2 = ((String) o2.getValue(sortId));
							return sign * (v1.compareTo(v2));
						}
					});
					break;
				default:
					break;
				}
				Row[] srows = new Row[Math.min(end - begin, tmp.length - begin)];
				for (int i = 0; i < srows.length; ++i)
					srows[i] = tmp[begin + i];
				table.onSuccess(new Rows(srows, begin, sortId, ascending));
			}
		};
		table.setContentProvider(provider);
		table.addTableListener(new TableListenerAdapter() {
			public void onCellClicked(SourcesTableEvents sender, Row row,
					Column column) {
			}

			public void onRowClicked(SourcesTableEvents sender, Row row) {
				for (int i = 0; i < rows.length; ++i)
					rows[i].setState(Row.State.NONE);
				row.setState(Row.State.SELECT);
				table.refreshRowState();
			}

			public void onClick(SourcesTableEvents sender, Row row,
					Column column, Widget widget) {
				if (widget instanceof CheckBox) {
					row.setValue(0,
							new Boolean(((CheckBox) widget).isChecked()));
				} else if (widget instanceof Button) {
					Window.alert(((Button) widget).getHTML());
				} else if (widget instanceof Hyperlink) {
					Window.alert(((Hyperlink) widget).getHTML());
				} else if (widget instanceof Image) {
					Window.alert(((Image) widget).getUrl());
				}
			}

			public void onChange(SourcesTableEvents sender, Row row,
					Column column, Widget widget) {
				if (widget instanceof ListBox) {
					ListBox listBox = (ListBox) widget;
					row.setValue(6, listBox
							.getValue(listBox.getSelectedIndex()));
				} else if (widget instanceof TextBox) {
					row.setValue(8, ((TextBox) widget).getText());
				}
			}
		});
		table.update();
		return table;
	}

	void saveFriend() {
		overview_panel.friend_Service.saveFriend(overview_panel.userid,
				select_name, new AsyncCallback<Friends_Client>() {
					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("fail to add friend");
					}

					@Override
					public void onSuccess(Friends_Client result) {

						final MLabel label = new MLabel(result);
						labels.add(label);
						content.add(label);
						label.addMouseOverHandler(new LabelHandler(label,
								contactInfo, contactPopup));
						label.addMouseOutHandler(new MouseOutHandler() {
							@Override
							public void onMouseOut(MouseOutEvent event) {
								DOM.setStyleAttribute(label.getElement(),
										"filter", " Alpha(Opacity=100)");
								DOM.setStyleAttribute(label.getElement(),
										"color", "white");
							}
						});
						dialogBox.hide();
					}

				});
	}

}
