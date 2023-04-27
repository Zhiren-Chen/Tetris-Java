package blockPanel;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class StyleIcons {
	private Icon[] icons = new Icon[37];
	private URL url;
	public StyleIcons() {
		for(int i=1; i<icons.length; i++) {
			try {
				icons[i]=new ImageIcon(StyleIcons.class.getResource("pics/style"+i+".jpg"));
			}catch(Exception e) {
				System.out.print("Caught");
				e.printStackTrace();
			}
		}
	}
	public Icon get(int i) {
		return icons[i];
	}
}
