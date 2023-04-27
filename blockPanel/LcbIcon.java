package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class LcbIcon {
	private ImageIcon icon;
	public LcbIcon() {
		URL url = LcbIcon.class.getResource("icons/lcb.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
