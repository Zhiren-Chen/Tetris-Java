package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class GreenIcon {
	private ImageIcon icon;
	public GreenIcon() {
		URL url = GreenIcon.class.getResource("icons/green.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
