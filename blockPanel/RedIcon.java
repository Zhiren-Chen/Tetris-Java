package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class RedIcon {
	private ImageIcon icon;
	public RedIcon() {
		URL url = RedIcon.class.getResource("icons/red.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
