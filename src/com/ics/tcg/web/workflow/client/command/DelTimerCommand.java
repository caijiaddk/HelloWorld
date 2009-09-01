package com.ics.tcg.web.workflow.client.command;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.SimpleTask;
import com.ics.tcg.web.workflow.client.TimerNode;
import com.ics.tcg.web.workflow.client.examples.DiagramBuilderExample;
public class DelTimerCommand implements Command {

	SimpleTask deletewidget;
	DiagramBuilderExample diagrambuilderexample;

	public DelTimerCommand(Widget w, DiagramBuilderExample d) {
		deletewidget = (SimpleTask) w;
		diagrambuilderexample = d;
	}

	public void execute() {
		// TODO Auto-generated method stub
		deleteTimer();
		deletewidget.EnableAddTimer();
		deletewidget.cmg.popupPanel.hide();
	}

	public void deleteTimer() {
		TimerNode timer;
		if (deletewidget.getTimerNode() != null) {
			timer = deletewidget.getTimerNode();
			timer.removeFromParent();
			deletewidget.setTimerNode(null);
			deletewidget.hasTimer=false;
//			diagrambuilderexample.timernodeList.remove(timer);
		}
	}
}
