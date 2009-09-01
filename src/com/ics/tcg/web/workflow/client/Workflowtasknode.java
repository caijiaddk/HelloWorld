package com.ics.tcg.web.workflow.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.MenuItem;

public abstract class Workflowtasknode extends Composite implements HasText{
	
	public String ID;
	public String taskType;
	private String text;
	protected AbsolutePanel panel1;
	protected MyPanel panel2, panel3, panel4;
	protected int pixel_x,pixel_y;
	protected boolean isstart,isfinished;
	public ContextMenuGwt cmg;
	public MenuItem setstart,setfinish;
	public boolean belongToSubWorkflow;
	public boolean hasFinishedConfigure;

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}
	
	public boolean isBelongToSubWorkflow() {
		return belongToSubWorkflow;
	}

	public void setBelongToSubWorkflow(boolean belongToSubWorkflow) {
		this.belongToSubWorkflow = belongToSubWorkflow;
	}

	public int getPixel_x() {
		return pixel_x;
	}

	public void setPixel_x(int pixel_x) {
		this.pixel_x = pixel_x;
	}

	public int getPixel_y() {
		return pixel_y;
	}

	public void setPixel_y(int pixel_y) {
		this.pixel_y = pixel_y;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	public void setText(String text) {
		// TODO Auto-generated method stub
		this.text=text;
	}
	public AbsolutePanel getPanel1() {
		return panel1;
	}

	public void setPanel1(MyPanel panel1) {
		this.panel1 = panel1;
	}

	public MyPanel getPanel2() {
		return panel2;
	}

	public void setPanel2(MyPanel panel2) {
		this.panel2 = panel2;
	}

	public MyPanel getPanel3() {
		return panel3;
	}

	public void setPanel3(MyPanel panel3) {
		this.panel3 = panel3;
	}

	public MyPanel getPanel4() {
		return panel4;
	}

	public void setPanel4(MyPanel panel4) {
		this.panel4 = panel4;
	}

	public boolean isIsstart() {
		return isstart;
	}

	public void setIsstart(boolean isstart) {
		this.isstart = isstart;
	}

	public boolean isIsfinished() {
		return isfinished;
	}

	public void setIsfinished(boolean isfinished) {
		this.isfinished = isfinished;
	}
	

	public boolean isHasFinishedConfigure() {
		return hasFinishedConfigure;
	}

	public void setHasFinishedConfigure(boolean hasFinishedConfigure) {
		this.hasFinishedConfigure = hasFinishedConfigure;
	}
}
