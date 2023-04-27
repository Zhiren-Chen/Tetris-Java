package blockPanel;

import java.util.Random;

import javax.swing.JLabel;

public class BlockGenerate {
	public static int count=0;
	private int startY = 20;
	private int startX;
	private int[] cubeX;
	private int[] cubeY;
	private JLabel preview;
	public BlockGenerate() {
		count++;
	}
	public int getNewBlockData() {
		if(BlockFrame.turnnum==0) {
			BlockFrame.nextselector = 3;			
		}
		int selector=BlockFrame.nextselector;
		BlockFrame.nextselector = (int) ((int)41*Math.random())+1;
		if (BlockFrame.nextselector ==37) {
			BlockFrame.nextselector =2;
		}
		if (BlockFrame.nextselector ==38) {
			BlockFrame.nextselector =4;
		}
		if (BlockFrame.nextselector ==39) {
			BlockFrame.nextselector =5;
		}
		if (BlockFrame.nextselector ==40) {
			BlockFrame.nextselector =22;
		}
		if (BlockFrame.nextselector ==41) {
			BlockFrame.nextselector =22;
		}
		System.out.println("selector="+selector);
		switch(selector) {
		case 1:
			return genStyle1Data();
		case 2:
			return genStyle2Data();
		case 3:
			return genStyle3Data();
		case 4:
			return genStyle4Data();
		case 5:
			return genStyle5Data();
		case 6:
			return genStyle6Data();
		case 7:
			return genStyle7Data();
		case 8:
			return genStyle8Data();
		case 9:
			return genStyle9Data();
		case 10:
			return genStyle33Data();
		case 11:
			return genStyle11Data();
		case 12:
			return genStyle12Data();
		case 13:
			return genStyle13Data();
		case 14:
			return genStyle14Data();
		case 15:
			return genStyle15Data();
		case 16:
			return genStyle16Data();
		case 17:
			return genStyle17Data();
		case 18: 
			return genStyle18Data();
		case 19: 
			return genStyle19Data();
		case 20: 
			return genStyle20Data();
		case 21: 
			return genStyle21Data();
		case 22: 
			return genStyle22Data();
		case 23: 
			return genStyle1Data();
		case 24: 
			return genStyle4Data();
		case 25: 
			return genStyle25Data();
		case 26: 
			return genStyle26Data();
		case 27: 
			return genStyle27Data();
		case 28: 
			return genStyle28Data();
		case 29: 
			return genStyle29Data();
		case 30: 
			return genStyle30Data();
		case 31: 
			return genStyle31Data();
		case 32: 
			return genStyle32Data();
		case 33: 
			return genStyle33Data();
		case 34: 
			return genStyle34Data();
		case 35:
			return genStyle1Data();
		case 36:
			return genStyle2Data();
		}
		return genStyle1Data();
	}
	public int genStyle1Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[3];
		cubeY = new int[3];
		for(int i=0; i<3; i++) {
			cubeX[i]=startX+20*i;
			cubeY[i]=20;
		}	
		return 1;
	}
	public int genStyle2Data() {
		startX = ((int) (Math.random()*20))*20;
		cubeX = new int[3];
		cubeY = new int[3];
		for(int i=0; i<3; i++) {
			cubeX[i]=startX;
			cubeY[i]=20*i;
		}	
		return 2;
	}
	public int genStyle3Data() {
		startX = ((int) (Math.random()*19))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[3]=startX;
		cubeX[2]=startX+20;
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=40;
		return 3;
	}
	public int genStyle4Data() {
		startX = ((int) (Math.random()*17))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+40;cubeX[3]=startX+60;		
		cubeY[0]=40;
		cubeY[1]=40;
		cubeY[2]=40;
		cubeY[3]=40;
		return 4;
	}
	public int genStyle5Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[3]=startX+40;
		cubeX[2]=startX+20;
		cubeY[0]=20;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=20;
		return 5;
	}
	public int genStyle6Data() {
		startX = ((int) (Math.random()*19))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX+20; cubeX[1]=startX; cubeX[3]=startX+20;
		cubeX[2]=startX+20;
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=40;
		return 6;
	}
	public int genStyle7Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+20; cubeX[3]=startX+20;		
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=40;
		return 7;
	}
	public int genStyle8Data() {
		startX = ((int) (Math.random()*19))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX;cubeX[3]=startX;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=60;
		return 8;
	}
	public int genStyle9Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+40; cubeX[2]=startX+20;
		cubeX[3]=startX+20;
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=0;
		cubeY[3]=20;
		return 9;
	}
	public int genStyle10Data() {
		startX = ((int) (Math.random()*18))*18;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX+20; cubeX[1]=startX; cubeX[2]=startX+20; cubeX[3]=startX+40; cubeX[4]=startX+20;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=20;
		cubeY[4]=40;
		return 10;
	}
	public int genStyle11Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX; cubeX[3]=startX+20;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=40;
		return 11;
	}
	public int genStyle12Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX+40; cubeX[1]=startX; cubeX[2]=startX+20; cubeX[3]=startX+40;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=20;
		return 12;
	}
	public int genStyle13Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+40; cubeX[3]=startX;		
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=0;
		cubeY[3]=20;
		return 13;
	}
	public int genStyle14Data() {
		startX = ((int) (Math.random()*19))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX+20; cubeX[1]=startX; cubeX[2]=startX; cubeX[3]=startX;		
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=40;
		return 14;
	}
	public int genStyle15Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+40; cubeX[3]=startX+40;		
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=0;
		cubeY[3]=20;
		return 15;
	}
	public int genStyle16Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX+20; cubeX[1]=startX+20; cubeX[2]=startX+20; cubeX[3]=startX;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=40;
		return 16;
	}
	public int genStyle17Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX+20; cubeX[3]=startX+40;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=20;
		return 17;
	}
	public int genStyle18Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+40; cubeX[3]=startX+20; cubeX[4]=startX+20;		
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=0;
		cubeY[3]=20;
		cubeY[4]=40;
		return 18;
	}
	public int genStyle19Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX+40; cubeX[1]=startX; cubeX[2]=startX+20; cubeX[3]=startX+40; cubeX[4]=startX+40;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=20;
		cubeY[4]=40;
		return 19;
	}
	public int genStyle20Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX+20; cubeX[1]=startX+20; cubeX[2]=startX; cubeX[3]=startX+20; cubeX[4]=startX+40;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=40;
		cubeY[4]=40;
		return 20;
	}
	public int genStyle21Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX; cubeX[3]=startX+20; cubeX[4]=startX+40;		
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=20;
		cubeY[4]=20;
		return 21;
	}
	public int genStyle22Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX; cubeX[3]=startX+20;	
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=20;
		return 22;
	}
	public int genStyle23Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[6];
		cubeY = new int[6];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+40; cubeX[3]=startX; cubeX[4]=startX+20; cubeX[5]=startX+40;
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=0;
		cubeY[3]=20;
		cubeY[4]=20;
		cubeY[5]=20;
		return 23;
	}
	public int genStyle24Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[6];
		cubeY = new int[6];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX; cubeX[3]=startX+20; cubeX[4]=startX; cubeX[5]=startX+20;
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=20;
		cubeY[4]=40;
		cubeY[5]=40;
		return 24;
	}
	public int genStyle25Data() {
		startX = ((int) (Math.random()*19))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX+20; cubeX[3]=startX+20; 
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=20;
		cubeY[3]=40;
		return 25;
	}
	public int genStyle26Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX+20; cubeX[1]=startX+40; cubeX[2]=startX; cubeX[3]=startX+20; 
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=20;
		return 26;
	}
	public int genStyle27Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX+20; cubeX[3]=startX+20; 
		cubeY[0]=20;
		cubeY[1]=40;
		cubeY[2]=0;
		cubeY[3]=20;
		return 27;
	}
	public int genStyle28Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[4];
		cubeY = new int[4];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+20; cubeX[3]=startX+40; 
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=20;
		return 28;
	}
	public int genStyle29Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX; cubeX[1]=startX; cubeX[2]=startX; cubeX[3]=startX+20; cubeX[4]=startX+20;
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=0;
		cubeY[4]=40;
		return 29;
	}
	public int genStyle30Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX; cubeX[1]=startX+20; cubeX[2]=startX+40; cubeX[3]=startX; cubeX[4]=startX+40;
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=0;
		cubeY[3]=20;
		cubeY[4]=20;
		return 30;
	}
	public int genStyle31Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX+20; cubeX[1]=startX+20; cubeX[2]=startX+20; cubeX[3]=startX; cubeX[4]=startX;
		cubeY[0]=0;
		cubeY[1]=20;
		cubeY[2]=40;
		cubeY[3]=0;
		cubeY[4]=40;
		return 31;
	}
	public int genStyle32Data() {
		startX = ((int) (Math.random()*18))*20;
		cubeX = new int[5];
		cubeY = new int[5];
		cubeX[0]=startX; cubeX[1]=startX+40; cubeX[2]=startX; cubeX[3]=startX+20; cubeX[4]=startX+40;
		cubeY[0]=0;
		cubeY[1]=0;
		cubeY[2]=20;
		cubeY[3]=20;
		cubeY[4]=20;
		return 32;
	}	
	public int genStyle33Data() {
		startX = ((int) (Math.random()*19))*20;
		cubeX = new int[2];
		cubeY = new int[2];
		cubeX[0]=startX; cubeX[1]=startX+20;
		cubeY[0]=0;
		cubeY[1]=0;
		return 33;
	}	
	public int genStyle34Data() {
		startX = ((int) (Math.random()*20))*20;
		cubeX = new int[2];
		cubeY = new int[2];
		cubeX[0]=startX; cubeX[1]=startX;
		cubeY[0]=0;
		cubeY[1]=20;
		return 34;
	}	
	public int[] getCubeXs() {
		return cubeX;
	}
	public int[] getCubeYs() {
		return cubeY;
	}
}
