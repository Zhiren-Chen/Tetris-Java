package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class BlueIcon{
	private ImageIcon icon;
	public BlueIcon() {
		URL url = BlueIcon.class.getResource("icons/blue.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
