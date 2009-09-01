package com.ics.tcg.web.workflow.client.command;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ics.tcg.web.user.client.panels.Panel_Overview;
import com.ics.tcg.web.workflow.client.LoopTask;
import com.ics.tcg.web.workflow.client.examples.DiagramBuilderExample;
import com.ics.tcg.web.workflow.client.examples.SubDiagram;

public class EditLoopCommand implements Command{

	LoopTask editwidget;
	DiagramBuilderExample diagrambuilderexample;
	TabPanel tabs;
	Panel_Overview overview;
	public SubDiagram subDiagram;
	
	public EditLoopCommand(Widget w,DiagramBuilderExample d,Panel_Overview overview){
		this.overview=overview;
		editwidget = (LoopTask)w;
		diagrambuilderexample = d;
		
	}
	public void execute() {
		// TODO Auto-generated method stub
		tabs = (TabPanel)diagrambuilderexample.getParent().getParent().getParent();
		Edit_loop_task();
	}

	public void Edit_loop_task(){
		editwidget.cmg.popupPanel.hide();
		subDiagram = new SubDiagram((LoopTask)editwidget,diagrambuilderexample.gde,overview);
		tabs.add(subDiagram, editwidget.getTitle());				//tab的标题显示为被编辑容器的titile					
		tabs.selectTab(tabs.getWidgetCount()-1);					//初始化subdiagram显示，起初为空
		subDiagram.display_workflow(editwidget.sub_workflow);		//载入子工作流
//		tabs.selectTab(1);
	}
}
