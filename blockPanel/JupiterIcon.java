package blockPanel;

import java.net.URL;

import javax.swing.ImageIcon;

public class JupiterIcon {
	private ImageIcon icon;
	public JupiterIcon() {
		URL url = JupiterIcon.class.getResource("icons/jupiter.jpg");
		icon = new ImageIcon(url);		
		//System.out.print("blue");
	}
	public ImageIcon getIcon() {
		return icon;
	}
}
