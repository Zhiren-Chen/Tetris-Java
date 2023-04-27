package blockPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cube {
	private int xcor;
	private int ycor;
	private boolean occupied;
	private JLabel jl;
	private String color;
	public Cube(int x, int y, boolean o, Icon icon) {
		xcor=x;
		ycor=y;
		occupied=o;
		jl=new JLabel(icon);		
	}
	public JLabel getLabel() {
		return jl;
	}
	public int getXcor() {
		return xcor;
	}
	public int getYcor() {
		return ycor;
	}
	public void setXcor(int x) {
		xcor=x;
	}
	public void setYcor(int y) {
		ycor=y;
	}
	public boolean getOccupied() {
		return occupied;
	}
	public void setOccupied(boolean o) {
		occupied=o;
	}
	public void setColor(Icon icon) {
		jl=new JLabel(icon);
	}
	public String getColor() {
		return color;
	}
	public void setColor(String mycolor) {
		color=mycolor;
	}
	public void setCors(int x, int y) {
		xcor=x;
		ycor=y;
	}
	public void cancel() {
		occupied=false;
		jl=new JLabel(new GreyIcon().getIcon());
	}
	public void occupy() {
		// TODO Auto-generated method stub
		occupied=true;
		if(color.contentEquals("orange")) {
			jl=new JLabel(new OrangeIcon().getIcon());
		}else if(color.contentEquals("blue")) {
			jl=new JLabel(new BlueIcon().getIcon());
		}else if(color.contentEquals("red")){
			jl=new JLabel(new RedIcon().getIcon());
		}else if(color.contentEquals("green")){
			jl=new JLabel(new GreenIcon().getIcon());
		}else if(color.contentEquals("prb")){
			jl=new JLabel(new PrbIcon().getIcon());
		}else if(color.contentEquals("jupiter")){
			jl=new JLabel(new JupiterIcon().getIcon());
		}else if(color.contentEquals("lcb")){
			jl=new JLabel(new LcbIcon().getIcon());
		}	
	}
	public boolean touch(Cube c) {
		// TODO Auto-generated method stub
		if(this.ycor==(c.getYcor()-20)) {
			return true;
		}
		return false;
	}
	public boolean touchDown(Cube[] cubes) {
		// TODO Auto-generated method stub
		for(int i=0;i<cubes.length;i++) {
			if(cubes[i].getOccupied()) {
				if(this.touch(cubes[i])) {
					return true;
				}
			}
		}
		return false;
	}
}
