package com.ics.tcg.web.workflow.client.command;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.workflow.client.SimpleTask;
import com.ics.tcg.web.workflow.client.TimerNode;
import com.ics.tcg.web.workflow.client.examples.DiagramBuilderExample;

public class AddTimerCommand implements Command {

	SimpleTask addwidget;
	DiagramBuilderExample diagrambuilderexample;
	
	public AddTimerCommand(Widget w,DiagramBuilderExample d){
		addwidget = (SimpleTask)w;
		diagrambuilderexample = d;
	}
	
	public void execute() {
		// TODO Auto-generated method stub
		addTimer();
		addwidget.cmg.popupPanel.hide();

	}

	public void addTimer(){
		TimerNode timer = new TimerNode(addwidget.getText(),addwidget); 
//		diagrambuilderexample.timernodeList.add(timer);					//将新添加的timernode的引用保存起来
		addwidget.setTimerNode(timer);
//		timer.addStyleName("timer-node");					//使得时钟图标在任何图层的下面
		AbsolutePanel backPanel = diagrambuilderexample.getPanel();
		backPanel.add(timer, addwidget.getAbsoluteLeft() - backPanel.getAbsoluteLeft()-32, 
				addwidget.getAbsoluteTop()- backPanel.getAbsoluteTop()-24);
//		RootPanel.get().add(timer, addwidget.getPanel1().getAbsoluteLeft() - 47, addwidget.getPanel1()
//					.getAbsoluteTop()- 24);
		addwidget.hasTimer=true;
		addwidget.DisableAddTimer();
	}
	
}
