package com.gwinsoft.components.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WdTreeNode implements Comparable<WdTreeNode> {

    private String id;
    private String text;
    private String value;
    private int checkstate;
    private boolean showcheck = true;
    private boolean complete;
    private boolean isexpand;
    private boolean hasChildren;
    private String pId;
    private List<WdTreeNode> ChildNodes = new ArrayList<WdTreeNode>();
    private boolean isFolder = false;
    private boolean deleteflag;
    
    public WdTreeNode() {
    	
    }
    public WdTreeNode(String id,String text,String value,int checkstate,boolean complete, boolean isexpand, boolean hasChildren) {
    	this.id = id;
    	this.text = text;
    	this.value = value;
    	this.checkstate = checkstate;
    	this.complete = complete;
    	this.isexpand = isexpand;
    	this.hasChildren = hasChildren;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCheckstate() {
		return checkstate;
	}
	public void setCheckstate(int checkstate) {
		this.checkstate = checkstate;
	}
	public boolean isShowcheck() {
		return showcheck;
	}
	public void setShowcheck(boolean showcheck) {
		this.showcheck = showcheck;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public boolean isIsexpand() {
		return isexpand;
	}
	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public List<WdTreeNode> getChildNodes() {
		return ChildNodes;
	}
	public void setChildNodes(List<WdTreeNode> childNodes) {
		ChildNodes = childNodes;
	}
	
	public void addChild(WdTreeNode node) {
		addChild(this, node);
	}
	private boolean addChild(WdTreeNode root, WdTreeNode node) {
		boolean result = false;
		if(node.getpId()==null) {
			return true;
		}
		if(node.getpId().equals(root.getId())) {
			root.ChildNodes.add(node);
			root.hasChildren = true;
			//Collections.sort(root.ChildNodes);
			return true;
		} else {
			for(WdTreeNode n : root.ChildNodes) {
				result = addChild(n, node);
				if(result) {
					break;
				}
			}
		}
		return result;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public int compareTo(WdTreeNode o) {
		String value = o.getValue();
		if(this.value!=null && value!=null && !"".equals(this.value) && !"".equals(value)) {
			if(this.value.indexOf("~") != -1) {
				String[] values1 = this.value.split("~");
				String[] values2 = value.split("~");
				int a = 0;
				int b = 0;
				//System.out.println(this.value + " VS " + value +","+values1.length);
				if(values1.length>3 && values2.length>3) {
					if(values1.length<10 || values1[9]==null || "".equals(values1[9])) {
						a = 999999999;
					} else {
						a = Integer.parseInt(values1[9]);
					}
					if(values2.length<10 || values2[9]==null || "".equals(values2[9])) {
						b = 999999999;
					} else {
						b = Integer.parseInt(values2[9]);
					}
					//System.out.println("A:"+this.text+",B:"+o.getText()+"="+(a-b));
					return a - b;
				}
			} else {
				if(this.value.length()>value.length()) {
					return 1;
				} else if(this.value.equals(value)) {
					return this.id.compareTo(o.getId());
				} else {
					return this.value.compareTo(value);
				}
			}
			
		}
		if(this.id!=null && o.getId()!=null) {
			return this.id.compareTo(o.getId());
		}
		return 0;
	}
	public boolean isFolder() {
		return isFolder;
	}
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public boolean isDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}
    
}
