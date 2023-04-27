package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class GreyIcon {
	private ImageIcon icon;
	public GreyIcon() {
		URL url = BlueIcon.class.getResource("icons/grey.png");
		//icon = new ImageIcon(url);	
		icon = new ImageIcon();	
		//System.out.print("grey");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
