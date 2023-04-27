package blockPanel;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class LevelBox extends AbstractListModel<String> implements ComboBoxModel<String>{
	String selected = null;
	String[] items = {"Standard","Medium","High"};
	public String getElementAt(int index) {
		return (String) items[index];
	}
	public int getSize() {
		return items.length;
	}
	public void setSelectedItem(Object item) {
		selected = (String)item;
	}
	public Object getSelectedItem() {
		return selected;
		
	}
}
