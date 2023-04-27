package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class OrangeIcon {
	private ImageIcon icon;
	public OrangeIcon() {
		URL url = OrangeIcon.class.getResource("icons/orange.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
