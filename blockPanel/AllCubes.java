package blockPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AllCubes extends JPanel{
	private String blockColor;
	private int stylenum;
	private Cube[][] allcubes = new Cube[20][28];
	
	private ArrayList<ArrayList<Rectangle>> xysquares = new ArrayList<ArrayList<Rectangle>>();
	public AllCubes() {
		for(int i=0;i<20;i++) {
			for(int j=0;j<28;j++) {
				allcubes[i][j]=new Cube(i*20, j*20,false,new GreyIcon().getIcon());
			}
		}
	}
	public void occupy(int x, int y) {
		// TODO Auto-generated method stub
		allcubes[x/20][y/20].occupy();
	}
	public Cube[][] getAllCubes() {
		// TODO Auto-generated method stub
		return allcubes;
	}
	public Cube[] constructBlock(String color) {
		Block block = new Block(color);
		return block.getBlockCubes();
	}
	public int getStyle() {
		return stylenum;
	}
	public class Block {
		private Cube[] blockCubes;
		private int style;
		private BlockGenerate gened;
		public Block(String color) {
			gened = new BlockGenerate();
			stylenum=gened.getNewBlockData();
			blockCubes=new Cube[gened.getCubeXs().length];
			for(int i=0; i<gened.getCubeXs().length;i++) {
				blockCubes[i]=allcubes[(gened.getCubeXs()[i]/20)][ (gened.getCubeYs()[i]/20)];
				blockCubes[i].setColor(color);
				blockCubes[i].occupy();
			}
		}
		public Cube[] getBlockCubes() {
			// TODO Auto-generated method stub
			return blockCubes;
		}
		public void descend() {
			for(int num=0; num<blockCubes.length;num++) {				
				int x = blockCubes[num].getXcor();
				int y = blockCubes[num].getYcor();
				if(blockCubes[num].touchDown(allcubes[x])) {
					break;
				}
				blockCubes[num].cancel();
				allcubes[x][y+20].occupy();
				blockCubes[num]=allcubes[x][y+20];
			}
		}
	}
}
