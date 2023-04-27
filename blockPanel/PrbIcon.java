package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class PrbIcon {
	private ImageIcon icon;
	public PrbIcon() {
		URL url = PrbIcon.class.getResource("icons/prb.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
