package blockPanel;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OverDialog extends JDialog{
	private JButton clear;
	public OverDialog(BlockFrame f) {
		super(f, "Tetris Alert", true);
		JPanel c = new JPanel();
		this.add(c);
		setLayout(new FlowLayout(1, 10, 10));
		c.add(new JLabel("Game Over."));
		clear = new JButton(("Clear"));
		this.setBounds(500,500,200,200);
		this.setVisible(true);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disappear();				
				f.reset();
				
			}
		});
		clear.setVisible(true);
	}
	private void disappear() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}
}
