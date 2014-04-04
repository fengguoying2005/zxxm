package test;

import java.util.ArrayList;
import java.util.List;

public class Menu1 {
	private String text;
	private boolean isexpand;
	private List<Menu2> children = new ArrayList<Menu2>();
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isIsexpand() {
		return isexpand;
	}
	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}
	public List<Menu2> getChildren() {
		return children;
	}
	public void setChildren(List<Menu2> children) {
		this.children = children;
	}
	
	
}
