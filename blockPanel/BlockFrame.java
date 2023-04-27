package blockPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;


public class BlockFrame extends JFrame{
	public Thread gameThread;
	private int turnum=0;
	private Thread descendThread;
	private AllCubes cubes = new AllCubes();
	private JButton left = new JButton("LEFT");
	private JButton right = new JButton("RIGHT");
	private JButton reset = new JButton("RESET");
	private JButton pause = new JButton("PAUSE");
	private JButton conti = new JButton("CONTINUE");
	private ArrayList<Cube> restcubes = new ArrayList<Cube>();
	private ArrayList<Cube> sinkcubes = new ArrayList<Cube>();
	private ArrayList<Cube> aircubes = new ArrayList<Cube>();
	private Cube[] alivecubes;
	private String color;
	private boolean isDropping=false, isKeying=false;
	private boolean touchdown=false, touch1down=false, touchnewdown=false;
	private static boolean isBearing, isCancelling;
	private static boolean isPaused = false;
	private static boolean isAuto=true;
	private static int blocktype;
	private static int sleepTurn;
	private static int mark=0;
	private int sleepCoef;
	private double time, tcoef;
	private static int desnum=0;
	private static int buggyOvers=0;
	public static int turnnum=0;
	public static int nextselector;
	private JTextArea ta = new JTextArea(80,80);
	StyleIcons preicons;
	JLabel prelabel;
	private static boolean desRunning=false;
	JButton begin = new JButton();
	JLabel bl = new JLabel("BEGIN");
	JPanel panel = new JPanel();
	JPanel subpanel = new JPanel();
	JPanel prewindow = new JPanel();
	JPanel hider = new JPanel();
	JPanel levelPanel = new JPanel();
	JRadioButton auto = new JRadioButton("  Auto     ");
	JTextField tf = new JTextField(12);
	private Cube[][] allcubes;
	private final Object obj = new Object();
	private static Lock lock = new ReentrantLock();
	private static boolean isOver=false;
	Condition c1;
	Condition c2;
	private String[] colors = new String[] {"blue","orange","red","prb","green","jupiter","lcb"};
	public String randColor() {
		int colnum;
		String color;		
		colnum=(int) (Math.random()*7);
		color=colors[colnum];
		return color;
	}
	
	public BlockFrame() {
		super();
		setSize(600,600);
		add(panel);
		begin.add(bl);
		setVisible(true);
		panel.setLayout(null);
		panel.setBackground(Color.DARK_GRAY);
		panel.add(subpanel);
		subpanel.setBounds(401, 180, 199, 390);
		subpanel.setBackground(Color.GRAY);
		subpanel.setBorder(BorderFactory.createRaisedBevelBorder());
		subpanel.setLayout(new FlowLayout(1,10,20));
		subpanel.add(new JLabel("Difficulty:"));
		JComboBox<String> levelbox = new JComboBox<>(new LevelBox());
		subpanel.add(levelbox);
		levelbox.setSelectedIndex(0);
		subpanel.add(auto);
		auto.setSelected(true);
		auto.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(isAuto==false) {
					isAuto=true;
				}
				else {
					isAuto=false;
					tcoef=1;
				}
				panel.requestFocus();
			}				
		});
		subpanel.add(tf);
		tf.setText("Mark: "); 
		panel.add(prewindow);
		prewindow.setBounds(401, 60, 199, 120);
		prewindow.setBackground(Color.WHITE);
		prewindow.setBorder(BorderFactory.createRaisedBevelBorder());
		prewindow.add(new JLabel("NEXT SHAPE"));
		preicons = new StyleIcons();
		panel.add(hider);
		hider.setBackground(Color.gray);
		hider.setBorder(BorderFactory.createRaisedBevelBorder());
		hider.setBounds(0, 0, 600, 60);
		hider.setLayout(new FlowLayout(1,20,20));
		hider.add(begin);
		hider.add(pause);
		hider.add(conti);
		begin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				begin.setVisible(false);
				pause.setVisible(true); conti.setVisible(true);
				if(levelbox.getSelectedIndex()==0) {
					time=50;
				}else if(levelbox.getSelectedIndex()==1) {
					time=25;
				}else if(levelbox.getSelectedIndex()==2) {
					time=15;
				}
				tcoef=1;
				tf.setText("Mark: "); 
				isOver=false;				
				turnum=0;
				restcubes = new ArrayList<Cube>();
				sinkcubes = new ArrayList<Cube>();
				aircubes = new ArrayList<Cube>();
				isDropping=false; isKeying=false;
				touchdown=false; touch1down=false; touchnewdown=false;
				desnum=0;
				turnnum=0;
				desRunning=false;
				isBearing=false; isCancelling=false;				
				allcubes=cubes.getAllCubes();
				for(int i=0; i<allcubes.length; i++) {
					for(int j=0; j<allcubes[0].length;j++) {
						panel.add(allcubes[i][j].getLabel());
						allcubes[i][j].getLabel().setBounds(i*20, j*20, 16, 16);
						
					}
				}
				c1 = lock.newCondition();
				c2 = lock.newCondition();
				gameThread = new Thread(new Game());
				gameThread.start();
				
				panel.setVisible(true);
				panel.requestFocus();
				try {
					panel.removeKeyListener(keyListener);
				}catch(Exception ek) {
					System.out.println("ERROR in begin.addActionListener() panel.removeKeyListener()");
					ek.printStackTrace();
				}
				panel.addKeyListener(keyListener);
			}	
		});
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isPaused=true;	
				
			}
		});
		conti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isPaused=false;	
				panel.requestFocus();
				if(levelbox.getSelectedIndex()==0) {
					time=50;
				}else if(levelbox.getSelectedIndex()==1) {
					time=25;
				}else if(levelbox.getSelectedIndex()==2) {
					time=12;
				}
				if(isAuto) {
				}
			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buggyOvers=0;
				isOver=true;
				isPaused=false;
				mark=0;
				pause.setVisible(false); conti.setVisible(false);
				boolean isCompleted = false;
				while(!isCompleted) {
					while(isBearing||isDropping||isCancelling) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							System.out.println("ERROR in reset.addActionListener() sleep");
							e1.printStackTrace();
						}
					}
					for(int i=19; i>=0; i--) {
						for(int j=27; j>=0; j--) {
							setColor(allcubes[i][j],"grey");
						}
					}
					isCompleted=true;
					for(int i=19; i>=0; i--) {
						for(int j=0; j<27; j++) {
							if(allcubes[i][j].getOccupied()) {
								isCompleted=false;
							}
						}
					}
				}
				prelabel.setVisible(false);
				prewindow.remove(prelabel);			
				begin.setVisible(true);				
			}
		});
		hider.add(reset);
		
	}
	
	class Game implements Runnable{
		
		private int count=0;
		@Override
		public synchronized void run() {
			Descend des = new Descend();
			
			while(!isOver) {
				count++;
				synchronized("") {
					try {
						while(desRunning) {
							if(isOver) break;
							Thread.sleep(100);
						}
						while(isPaused) {
							Thread.sleep(3000);
						}
						int d=0;
						descendThread=new Thread(des);
						if(!isOver) {
							descendThread.start();
							desnum++;
							System.out.println(desnum+"th DESCEND THREAD, isOver="+isOver);
							d++;
						}else {
							break;
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("ERROR in class Game");
						e.printStackTrace();
					} finally {							
					}
				}				
			}			
		}		
	}
	
	class Descend implements Runnable{
		private int xcor, ycor;
		private Cube[] nextalivecubes;
		private String nextcolor;
		private int nextblocktype=0;
		private Cube tempcube;
		private boolean touchleft=false, touchright=false;
		private boolean isCanceling=false;
		private boolean markGain=false;
		
		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			synchronized(obj) {
				if(!isOver) {
					isBearing=true;
					desRunning=true;
					turnnum++;	
					markGain=false;
					turnum++;			
					System.out.print("Another Descend Thread ");
					color=randColor();
					alivecubes = cubes.constructBlock(color); 
					blocktype=cubes.getStyle();		
					System.out.println("block style num = "+blocktype);
					int startX; int startY;	
					
					for(int i=0; i<alivecubes.length; i++) {
						startX=alivecubes[i].getXcor(); startY=alivecubes[i].getYcor();
						int indexX=startX/20; int indexY=1; int x=indexX,y=indexY;
						
						Cube topcube = allcubes[startX/20][startY/20];
						setColor(topcube,color);
						System.out.println("created one topcube");
					}
					try {
						prewindow.remove(prelabel);
					}catch(Exception e) {
						System.out.println("ERROR in prewindow.remove(prelabel)");
						//e.printStackTrace();
					}	
					prelabel=new JLabel(preicons.get(nextselector));
					prewindow.add(prelabel);
					prelabel.setBounds(50, 20,80, 80);
					isBearing=false;
				}
				while(!isOver) {
					touchdown=false;							
					try {
						for (sleepTurn=0; sleepTurn<10; sleepTurn++) {
							if(isOver) break;
							Thread.sleep((long) (time/tcoef));
						}	
						while(isPaused) {
							Thread.sleep(2000);
						}
						for(int i=0; i<alivecubes.length; i++) {							
							xcor=alivecubes[i].getXcor();
							ycor=alivecubes[i].getYcor();
							if(ycor==540) {
								touchdown=true;
							}else if((allcubes[xcor/20][ycor/20+1].getOccupied())&&(restcubes.contains(allcubes[xcor/20][ycor/20+1]))) {
								touchdown=true;
							}						
						}
						isDropping=true;
						while(isKeying) {
							Thread.sleep(4);//wait for key input to be processed yet not cancel this dropping
						}
						if(!touchdown) {																		
							//Thread.sleep(10);
							if(!isKeying&&!touchdown) {
								for(int i=alivecubes.length-1;i>=0;i--) {//MUST MOVE FROM BELOW TO UP!
									alivecubes[i]=moving(alivecubes[i],"down");		
								}
							}							
							isDropping=false;
						}else {
							for(int i=0; i<alivecubes.length; i++) {
								touchdown=true;
								restcubes.add(allcubes[alivecubes[i].getXcor()/20][alivecubes[i].getYcor()/20]);
							}
							isDropping=false;
							break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("ERROR in dropping");
						e.printStackTrace();
					} finally {
						isDropping=false;
					}
				}
				int count;
				boolean linecanceled=true, crashed=false;
				isCancelling=true;
				while((linecanceled)) {
					linecanceled=false;
					for(int j=0; j<28; j++) {
						count=0;
						for(int i=0; i<20; i++) {
							if (allcubes[i][j].getOccupied()) {
								count++;
							}							
						}					
						if(count==20) {
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								System.out.println("ERROR in while((linecanceled))");
								e.printStackTrace();
							}
							for(int i=0; i<20; i++) {
								setColor(allcubes[i][j], "grey");
								restcubes.remove(allcubes[i][j]);								
							}
							mark++;
							markGain=true;
							tf.setText("Mark: "+mark); 
							if(mark%2==0) {
								
							}	
							linecanceled=true;
							crashed=true;
							for(int j2=j-1; j2>=0; j2--) {
								for(int i2=0; i2<20; i2++) {
									if((allcubes[i2][j2].getOccupied())&&(restcubes.contains(allcubes[i2][j2]))) {
									}								
								}
								//System.out.println("restcubes added some cubes from allcubes");
							}
						}
					}		
					if(linecanceled) {
						if(isAuto) {
							if(mark<8) {
								tcoef=tcoef*1.1;

							}else if(mark<20) {
								tcoef=tcoef*1.06;
							}else {
								tcoef=tcoef*1.03;
							}
						}				
					}	
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("ERROR after if(linecanceled)");
						e.printStackTrace();
					}
					int sinknum=0;
					int crashturn=0;
					int bottnum=0; int prebottnum=0;
					sinkcubes=new ArrayList<Cube>();
					aircubes=new ArrayList<Cube>();
					bottnum=0;
					for(int i=0; i<20; i++) {
						if(allcubes[i][27].getOccupied()) {
							sinkcubes.add(allcubes[i][27]);
							bottnum++;
						}						
					}
					int toplevel=27;
					int occunum;
					for(int j=27; j>=0; j--) {
						occunum=0;
						for(int i=0; i<20; i++) {
							if(allcubes[i][j].getOccupied()) {
								occunum++;
							}
						}
						if(occunum==0) {
							toplevel=j;
							break;
						}else {
							continue;
						}
					}
					
					boolean added=true;
					boolean addedY=false;
					while(added) {	
						added=false;
						addedY=false;
						for(int i=0; i<sinkcubes.size(); i++) {
							int lx = sinkcubes.get(i).getXcor()/20-1;
							int rx = sinkcubes.get(i).getXcor()/20+1;
							int ux = sinkcubes.get(i).getXcor()/20;
							int bx = sinkcubes.get(i).getXcor()/20;
							int ly = sinkcubes.get(i).getYcor()/20;
							int ry = sinkcubes.get(i).getYcor()/20;
							int uy = sinkcubes.get(i).getYcor()/20-1;
							int by = sinkcubes.get(i).getYcor()/20+1;
							if(uy>=0) {
								if(allcubes[ux][uy].getOccupied()) {
									if(!sinkcubes.contains(allcubes[ux][uy])) {
										sinkcubes.add(allcubes[ux][uy]);
										added=true;
										addedY=true;
									}
								}
							}
							if(by<28) {
								if(allcubes[bx][by].getOccupied()) {
									if(!sinkcubes.contains(allcubes[bx][by])) {
										sinkcubes.add(allcubes[bx][by]);
										added=true;
									}
								}
							}
							if(lx>=0) {
								if(allcubes[lx][ly].getOccupied()) {
									if(!sinkcubes.contains(allcubes[lx][ly])) {
										sinkcubes.add(allcubes[lx][ly]);
										added=true;
									}
								}
							}
							if(rx<20) {
								if(allcubes[rx][ry].getOccupied()) {
									if(!sinkcubes.contains(allcubes[rx][ry])) {
										sinkcubes.add(allcubes[rx][ry]);
										added=true;
									}
								}
							}
						}
					}
					sinknum=sinkcubes.size();
					System.out.println("restcubes with size "+restcubes.size()+": ");
					for(int i=0; i<restcubes.size(); i++) {
						System.out.print("("+restcubes.get(i).getXcor()+","+restcubes.get(i).getYcor()+") ");
					}
					System.out.println("aircubes before removing: ");
					for(int i=0; i<20; i++) {
						for(int j=0; j<28; j++) {
							if(restcubes.contains(allcubes[i][j])) {
								aircubes.add(allcubes[i][j]);
								System.out.print("("+i*20+","+j*20+")");
							}
						}
					}
					System.out.println();
					System.out.println("aircubes before removing has size "+aircubes.size());
					System.out.println("sinkcubes is empty: "+sinkcubes.isEmpty());
					System.out.println("(to be removed)sinkcubes with size "+sinkcubes.size()+": ");
					for(int i=0; i<sinkcubes.size(); i++) {
						System.out.print("("+sinkcubes.get(i).getXcor()+","+sinkcubes.get(i).getYcor()+")");
					}
					System.out.println();
					
					ArrayList<Integer> toremoves = new ArrayList<Integer>(0);
					int toremove;
					for(int i=0; i<aircubes.size(); i++) {
						for(int n=0; n<sinkcubes.size(); n++) {
							if(((aircubes.get(i).getXcor()==sinkcubes.get(n).getXcor())&&(aircubes.get(i).getYcor()==sinkcubes.get(n).getYcor()))) {
								toremoves.add(i);
								break;
							}
						}			
						
					}
					Cube[] cuberemoves = new Cube[toremoves.size()];
					for(int i=0; i<toremoves.size(); i++) {
						toremove=toremoves.get(i);
						cuberemoves[i]=aircubes.get(toremove);
					}
					for(int i=0; i<cuberemoves.length; i++) {
						aircubes.remove(cuberemoves[i]);
					}
					System.out.println();
					System.out.println("aircubes after removing with size "+aircubes.size()+": ");
					for(int i=0; i<aircubes.size(); i++) {					
						System.out.print("("+aircubes.get(i).getXcor()+","+aircubes.get(i).getYcor()+"),"+aircubes.get(i).getOccupied()+" ");
					}
					System.out.println();
					System.out.println("restcubes.size()BEFORE sinking="+restcubes.size());
					System.out.println("sinkedcubes.size()BEFORE sinking="+sinkcubes.size());
					System.out.println("aircubes.size()afBEFORE sinking="+aircubes.size());	
					while(crashed) {
						//System.out.println("Crashturn at the beginning="+crashturn);
						crashed=false;
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("ERROR in while(crashed)");
							e.printStackTrace();
						}
						int numUp=0;
						int stillUp;
						if(aircubes.size()>0) {											
							for(int i=0; i<aircubes.size(); i++) {
								if(aircubes.get(i).getYcor()!=540) {
									if(allcubes[aircubes.get(i).getXcor()/20][aircubes.get(i).getYcor()/20+1].getOccupied()==false) {
										numUp++;
									}
								}
							}						
							for(int i=0; i<aircubes.size(); i++) {
								if(aircubes.get(i).getYcor()!=540) {
									if(allcubes[aircubes.get(i).getXcor()/20][aircubes.get(i).getYcor()/20+1].getOccupied()==false) {
										numUp++;
									}
								}
							}
							ArrayList<Cube> movedaircubes = new ArrayList<Cube>();
							for(int h=520; h>=0;h-=20) {
								for(int k=0; k<restcubes.size(); k++) {
									if(aircubes.contains(restcubes.get(k))) {									
										if(restcubes.get(k).getYcor()==h) {											
											restcubes.set(k,moving(restcubes.get(k), "down"));
											movedaircubes.add(restcubes.get(k));
										}
									}								
								}
							}	
							aircubes=movedaircubes;
							crashed=true;							
							ArrayList<Cube> botcubes = new ArrayList<Cube>();
							for(int i=0; i<aircubes.size(); i++) {
								System.out.println("before adding to botcubes: the aircube has Y coor of "+aircubes.get(i).getYcor());
								if(aircubes.get(i).getYcor()==540) {
									botcubes.add(aircubes.get(i));
									continue;
								}
								if(sinkcubes.contains(allcubes[aircubes.get(i).getXcor()/20][aircubes.get(i).getYcor()/20+1])) {
									botcubes.add(aircubes.get(i));
								}
							}
							System.out.println("botcubes have size of "+botcubes.size());
							for(int n=0; n<botcubes.size(); n++) {
								ArrayList<Cube> downcubes = new ArrayList<Cube>();
								downcubes.add(botcubes.get(n));
								boolean myadded=true;
								while(myadded) {	
									myadded=false;
									for(int i=0; i<downcubes.size(); i++) {
										int lx = downcubes.get(i).getXcor()/20-1;
										int rx = downcubes.get(i).getXcor()/20+1;
										int ux = downcubes.get(i).getXcor()/20;
										int bx = downcubes.get(i).getXcor()/20;
										int ly = downcubes.get(i).getYcor()/20;
										int ry = downcubes.get(i).getYcor()/20;
										int uy = downcubes.get(i).getYcor()/20-1;
										int by = downcubes.get(i).getYcor()/20+1;
										if(uy>=0) {
											if(aircubes.contains(allcubes[ux][uy])) {
												if(!downcubes.contains(allcubes[ux][uy])) {
													downcubes.add(allcubes[ux][uy]);
													myadded=true;
												}
											}
										}
										if(by<28) {
											if(aircubes.contains(allcubes[bx][by])) {
												if(!downcubes.contains(allcubes[bx][by])) {
													downcubes.add(allcubes[bx][by]);
													myadded=true;
												}
											}
										}
										if(lx>=0) {
											if(aircubes.contains(allcubes[lx][ly])) {
												if(!downcubes.contains(allcubes[lx][ly])) {
													downcubes.add(allcubes[lx][ly]);
													myadded=true;
												}
											}
										}
										if(rx<20) {
											if(aircubes.contains(allcubes[rx][ry])) {
												if(!downcubes.contains(allcubes[rx][ry])) {
													downcubes.add(allcubes[rx][ry]);
													myadded=true;
												}
											}
										}									
									}
								}
								for(int i=0; i<downcubes.size(); i++) {
									if(aircubes.contains(allcubes[downcubes.get(i).getXcor()/20][downcubes.get(i).getYcor()/20])){
										aircubes.remove(allcubes[downcubes.get(i).getXcor()/20][downcubes.get(i).getYcor()/20]);
										System.out.println("aircubes removed one cube");
									}
									if(!sinkcubes.contains(allcubes[downcubes.get(i).getXcor()/20][downcubes.get(i).getYcor()/20])) {
										sinkcubes.add(allcubes[downcubes.get(i).getXcor()/20][downcubes.get(i).getYcor()/20]);
									}
								}
								System.out.println("downcubes's size is: "+downcubes.size());
							}
							crashturn++;
						}
					}
					//2023 NEW 
					for(int i=0; i<20; i++) {
						for(int j=0; j<3; j++) {
							if(!allcubes[i][j].getOccupied()) {
								setColor(allcubes[i][j],"grey");
							}
						}
					}
					
				}
				
				isCancelling=false;
				for(int i=0; i<20; i++) {
					for(int j=0; j<3; j++) {
						if(allcubes[i][j].getOccupied()) {
							isOver=true;
							break;
						}
					}
				}
				if(isOver&&buggyOvers==0) {
					gameOver();	
				}
				desRunning=false;																												
			}
		}
	}
	public void setColor(Cube cube, String color) {
		int xcor = cube.getXcor();
		int ycor = cube.getYcor();
		panel.remove(cube.getLabel());
		if(!color.equals("grey")) {
			cube.setColor(color);
			cube.occupy();
		}else{
			cube.setColor("grey");
			cube.cancel();
		}
		panel.add(cube.getLabel());
		cube.getLabel().setBounds(xcor, ycor, 20, 20);
	}
	public void move(Cube cube, String direction) {
		Cube tempcube;
		int xcor = cube.getXcor();
		int ycor = cube.getYcor();
		setColor(cube,"grey");
		if(direction.equals("left")) {
			tempcube=allcubes[xcor/20-1][ycor/20];
			setColor(allcubes[xcor/20-1][ycor/20],this.color);			
		}else if(direction.equals("right")) {
			tempcube=allcubes[xcor/20+1][ycor/20];
			setColor(allcubes[xcor/20+1][ycor/20],this.color);
		}else if(direction.equals("down")) {
			tempcube=allcubes[xcor/20][ycor/20+1];
			setColor(allcubes[xcor/20][ycor/20+1],this.color);
		} else {
			tempcube=cube;
		}
	}
	public Cube moving(Cube cube, String direction) {
		Cube tempcube;
		String myColor=cube.getColor();
		int xcor = cube.getXcor();
		int ycor = cube.getYcor();
		setColor(cube,"grey");
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR in moving()");
			e.printStackTrace();
		}
		if(direction.equals("left")) {
			tempcube=allcubes[xcor/20-1][ycor/20];
			setColor(tempcube,myColor);			
		}else if(direction.equals("right")) {
			tempcube=allcubes[xcor/20+1][ycor/20];
			setColor(tempcube,myColor);
		}else if(direction.equals("down")) {
			tempcube=allcubes[xcor/20][ycor/20+1];
			setColor(tempcube,myColor);
		} else {
			tempcube=cube;
		}
		return tempcube;
	}
	public int sink(ArrayList<Cube> opcubes) {
		ArrayList<Cube> botcubes = new ArrayList<Cube> ();
		int gridnum=0;
		int xcor, ycor;
		int minH, botnum = 0;
		boolean isUp=true;
		for(int i=0; i<opcubes.size(); i++) {
			xcor=opcubes.get(i).getXcor(); ycor=opcubes.get(i).getYcor();
			if(ycor!=540) {
				if((!allcubes[xcor/20][ycor/20+1].getOccupied())) {
					botnum++;				
				}	
			}else{
				isUp=false;
			}
		}	
		System.out.println("botnum="+botnum);
		while(true) {
			
			boolean tempisUp = true;
			int tempbot = 0;
			for(int i=0; i<opcubes.size(); i++) {			
				xcor=opcubes.get(i).getXcor(); ycor=opcubes.get(i).getYcor();
				if(ycor!=540) {
					if((!allcubes[xcor/20][ycor/20+1].getOccupied())) {
						tempbot++;
					}	
				}else {
					tempisUp=false;
				}
				if(tempbot==0) {
					tempisUp=false;
				}
			}
			if((tempbot==botnum)&&(tempbot!=0)&&(tempisUp)) {
				System.out.println("tempbot==botnum");
				gridnum++;
			}else {
				break;
			}
			
		}
		return gridnum;
	}
	KeyListener keyListener = new KeyListener() {
		int xval, yval;
		boolean touchleft=false, touchright=false, touch2down=false;
		Cube tempcube;
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("KeyEvent");
			System.out.println("Pattern locations before moving: ");																	
			touchleft=false; touchright=false; touch1down=false; touch2down=false;
			int keyCode = e.getKeyCode();
			for(int i=0; i<alivecubes.length; i++) {							
				xval=alivecubes[i].getXcor();
				yval=alivecubes[i].getYcor();
				System.out.print("("+xval+","+yval+")");
				if((xval==0)) {
					touchleft=true;								
				}else if((xval>0)&&(xval<380)) {
					if((allcubes[xval/20-1][yval/20].getOccupied())&&(restcubes.contains(allcubes[xval/20-1][yval/20]))) {
						touchleft=true;
					}
					if((allcubes[xval/20+1][yval/20].getOccupied())&&(restcubes.contains(allcubes[xval/20+1][yval/20]))){
						touchright=true;
					}								
				}else {
					touchright=true;
				}
				if(yval>=540) {
					touch1down=true;
				} else if((allcubes[xval/20][yval/20+1].getOccupied())&&(restcubes.contains(allcubes[xval/20][yval/20+1]))){
					touch1down=true;									
				}	
				if(yval==20) {
					touch2down=true;
				}
				if(yval>=520) {
					touch2down=true;
				}else {
					if((allcubes[xval/20][yval/20+2].getOccupied())&&(restcubes.contains(allcubes[xval/20][yval/20+2]))){
						touch2down=true;
					}
				}
			}
			System.out.println();
			System.out.println("touch1down="+touch1down);
			if(!touchdown) {
				while(isDropping) {//if Descend thread have not finished, wait until it is finished, and key input will not be canceled
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						System.out.println("ERROR in KeyListener() if(!touchdown) sleep");
						e1.printStackTrace();
					}
				}
				int minx=380, maxx=0;
				for(int i=0; i<alivecubes.length; i++) {
					if(minx>alivecubes[i].getXcor()) {
						minx=alivecubes[i].getXcor();
					}
					if(maxx<alivecubes[i].getXcor()) {
						maxx=alivecubes[i].getXcor();
					}
				}
				
				if(keyCode==KeyEvent.VK_LEFT) {
					System.out.println("LEFT KEY");								
					if(!touchleft&&!isDropping) {									
						isKeying=true;
						for(int xloc = minx; xloc<=maxx;xloc+=20) {
							for(int i=0;i<alivecubes.length;i++) {//chained loop starting at the leftest cube.
								if(alivecubes[i].getXcor()==xloc) {
									alivecubes[i]=moving(alivecubes[i],"left");
								}										
							}
						}
					}
					System.out.println("alivecubes length = "+alivecubes.length);
				}
				if(keyCode==KeyEvent.VK_RIGHT) {
					System.out.println("RIGHT KEY");
					
					if(!touchright&&!isDropping) {
						isKeying=true;
						for(int xloc = maxx; xloc>=minx;xloc-=20) {
							for(int i=alivecubes.length-1;i>=0;i--) {
								if(alivecubes[i].getXcor()==xloc) {
									alivecubes[i]=moving(alivecubes[i],"right");
								}											
							}
						}
					}
				}
				if(keyCode==KeyEvent.VK_DOWN) {
					System.out.println("DOWN KEY");
					
					if(!touch2down&&!isDropping&&!touch1down) {
						isKeying=true;
						for(int i=alivecubes.length-1;i>=0;i--) {
							alivecubes[i]=moving(alivecubes[i],"down");
						}
					}
				}
				if(keyCode==KeyEvent.VK_W&&(!isDropping)) {									
					System.out.print("W (CCW rotate) ");
					int[] xvals = new int[alivecubes.length];
					int[] yvals = new int[alivecubes.length];
					isKeying=true;
					for(int i=0; i<alivecubes.length;i++) {
						xvals[i]=alivecubes[i].getXcor();
						yvals[i]=alivecubes[i].getYcor();
					}
					switch(blocktype) {
					case 1:			
						System.out.println("Case 1, after rotating: ");								
						xvals[0]+=20;
						xvals[2]-=20;
						yvals[0]-=20;
						yvals[2]+=20;
						if(yvals[2]<=540&&!(allcubes[xvals[2]/20][yvals[2]/20].getOccupied())) {
							if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())) {
								System.out.println("rotating pattern 1");
								setColor(alivecubes[0],"grey");
								setColor(alivecubes[2],"grey");
								alivecubes[0]=allcubes[xvals[0]/20][yvals[0]/20];
								alivecubes[2]=allcubes[xvals[2]/20][yvals[2]/20];
								setColor(alivecubes[0],color);
								setColor(alivecubes[2],color);
								blocktype=2;
								for(int i=0; i<alivecubes.length; i++) {
									System.out.println("("+alivecubes[i].getXcor()+","+alivecubes[i].getYcor()+")");
								}
								System.out.println();
							}			
						}			
						break;
					case 2:
						xvals[0]-=20;
						xvals[2]+=20;
						yvals[0]+=20;
						yvals[2]-=20;
						if(xvals[0]>=0&&xvals[2]<=380) {
							if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
									&&(xvals[0]>=0)) {
								setColor(alivecubes[0],"grey");
								setColor(alivecubes[2],"grey");
								alivecubes[0]=allcubes[xvals[0]/20][yvals[0]/20];
								alivecubes[2]=allcubes[xvals[2]/20][yvals[2]/20];
								setColor(alivecubes[0],color);
								setColor(alivecubes[2],color);
								blocktype=1;
							}
						}	
						break;
					case 3:
						xvals[0]-=20;									
						xvals[2]-=20;
						xvals[3]+=20;
						yvals[0]+=20;
						yvals[1]-=20;
						yvals[3]-=20;
						if((xvals[0]>=0)&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=5;
						}
						break;
					case 4:
						xvals[0]+=40;
						xvals[1]+=20;
						xvals[3]-=20;
						yvals[0]-=40;
						yvals[1]-=20;
						yvals[3]+=20;	
						if((xvals[0]>=0)&&(yvals[3]<=540)&&(yvals[0]>=0)) {
							if((!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())&&
									(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())
								) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=8;
							}
						}		
						break;
					case 5:
						xvals[0]+=20;
						xvals[1]-=20;
						xvals[3]-=20;
						yvals[0]-=20;
						yvals[1]+=20;
						yvals[3]+=20;
						if((yvals[3]!=540)&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=6;
						}
						break;
					case 6:
						xvals[0]-=20;
						xvals[1]+=40;
						yvals[0]+=20;
						if((xvals[1]<=380)&&(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=9;
						}
						break;
					case 7:
						xvals[0]+=40;
						xvals[1]-=20;
						xvals[3]+=20;
						yvals[0]+=20;
						yvals[1]+=40;
						yvals[2]+=20;
						if(xvals[0]<=380) {
							if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())
									&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=12;
							}
						}										
					    break;
				    case 8: 
				    	xvals[0]-=40;
						xvals[1]-=20;
						xvals[3]+=20;
						yvals[0]+=40;
						yvals[1]+=20;
						yvals[3]-=20;	
						if((xvals[0]>=0)&&(xvals[3]<=380)) {
							if((!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())&&
									(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())
								) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=4;
							}
						}		
						break;
				    case 9:
				    	xvals[0]+=20;
						xvals[1]-=20;									
						xvals[2]+=20;
						yvals[0]-=20;
						try {
							if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=3;
							}
						}catch(Exception e9) {
							System.out.println("ERROR in shape generation case9");
							e9.printStackTrace();
						}
						break;
				    case 11:
				    	xvals[0]-=20;
						xvals[2]+=20;	
						xvals[3]-=40;
						yvals[1]-=20;
						yvals[2]-=40;
						yvals[3]-=20;
						if(xvals[0]>=0) {
							if((!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())
									&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=13;
							}
						}	
						break;
				    case 12:
				    	xvals[0]-=40;
						xvals[2]-=20;
						xvals[3]-=20;
						yvals[2]+=20;
						yvals[3]+=20;
						if(yvals[3]<=540) {
							if((!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())
									&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=11;
							}
						}
						break;
				    case 13:
				    	xvals[0]+=20;
						xvals[1]+=20;	
						xvals[3]+=40;
						yvals[0]-=20;
						yvals[1]-=20;
						if(yvals[0]>=0) {
							if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())
									&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=7;
							}
						}
						break;
				    case 14:
				    	xvals[0]-=20;
				    	yvals[0]+=20;
				    	xvals[1]+=20;
				    	yvals[1]+=20;
				    	xvals[2]+=40;
				    	xvals[3]+=40;
				    	if(xvals[3]<=380) {
							if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
									&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=15;
							}
						}
						break;
				    case 15:
				    	xvals[0]+=20;
				    	yvals[1]+=20;
				    	xvals[2]-=20;
				    	yvals[2]+=40;
				    	xvals[3]-=40;
				    	yvals[3]+=20;	
				    	if(yvals[3]<=540) {
							if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
									&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=16;
							}
						}
						break;
				    case 16:
				    	xvals[0]-=40;
				    	xvals[1]-=40;
				    	xvals[2]-=20; yvals[2]-=20;
				    	xvals[3]+=20; yvals[3]-=20;
				    	if(xvals[0]>=0) {
							if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
									&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=17;
							}
						}
						break;
				    case 17:
				    	xvals[0]+=40; yvals[0]-=20;
				    	xvals[1]+=20; yvals[1]-=40;
				    	yvals[2]-=20;
				    	xvals[3]-=20;
				    	if(yvals[0]>=0) {
							if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
									&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=14;
							}
						}
						break;
				    case 18:
				    	xvals[0]+=40;
				    	xvals[1]-=20; yvals[1]+=20;
				    	xvals[2]-=20; yvals[2]+=20;
				    	xvals[3]+=20;
				    	xvals[4]+=20;
				    	if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())
								&&(!allcubes[xvals[4]/20][yvals[4]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=19;
						}
						break;
				    case 19:
				    	xvals[0]-=20;
				    	xvals[1]+=20;
				    	xvals[2]-=20; yvals[2]+=20;
				    	xvals[3]-=20; yvals[3]+=20;
				    	if((!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())
								&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=20;
						}
						break;
				    case 20:
				    	xvals[0]-=20;
				    	xvals[1]-=20;
				    	yvals[3]-=20;
				    	yvals[4]-=20;
				    	if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[4]/20][yvals[4]/20].getOccupied())
								&&(!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=21;
						}
						break;
				    case 21:
				    	xvals[1]+=20; yvals[1]-=20;
				    	xvals[2]+=40; yvals[2]-=40;
				    	xvals[4]-=20; yvals[4]+=20;
				    	if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[4]/20][yvals[4]/20].getOccupied())
								&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())) {
							resetBlock(alivecubes,xvals,yvals);
							blocktype=18;
						}
						break;
				    case 23:
				    	yvals[0]-=20;
				    	yvals[1]-=20;
				    	xvals[2]-=40; 
				    	xvals[3]+=20; yvals[3]-=20;
				    	xvals[4]-=20;
				    	xvals[5]-=20;
				    	if(yvals[0]>=0) {
				    		if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=24;
							}
				    	}							    	
						break;
				    case 24:
				    	yvals[0]+=20;
				    	yvals[1]+=20;
				    	xvals[2]+=40; 
				    	xvals[3]-=20; yvals[3]+=20;
				    	xvals[4]+=20;
				    	xvals[5]+=20;
				    	if(xvals[2]<=380) {
				    		if((!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())&&(!allcubes[xvals[5]/20][yvals[5]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=23;
							}
				    	}			    	
						break;
				    case 25:
				    	xvals[0]+=20; yvals[0]+=20;
				    	xvals[1]+=40; 
				    	xvals[2]-=20; yvals[2]+=20;
				    	if(xvals[1]<=380) {
				    		if((!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())&&(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=26;
							}
				    	}			    	
						break;
				    case 26:
				    	xvals[0]-=20; yvals[0]-=20;
				    	xvals[1]-=40; 
				    	xvals[2]+=20; yvals[2]-=20;
				    	if(yvals[3]<=540) {
				    		if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=25;
							}
				    	}			    	
						break;
				    case 27:
				    	xvals[1]+=20; yvals[1]-=20;
				    	yvals[2]+=40; 
				    	xvals[3]+=20; yvals[3]+=20;
				    	if(xvals[3]<=380) {
				    		if((!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=28;
							}
				    	}			    	
						break;
				    case 28:
				    	xvals[1]-=20; yvals[1]+=20;
				    	yvals[2]-=40; 
				    	xvals[3]-=20; yvals[3]-=20;
				    	if(yvals[3]<=540) {
				    		if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=27;
							}
				    	}			    	
						break;
				    case 29:
				    	xvals[0]-=20; yvals[0]+=20;
				    	xvals[2]+=20; yvals[2]-=20;
				    	xvals[3]-=40; yvals[3]+=40;				    	
				    	if(xvals[0]>=0) {
				    		if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
				    				&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=30;
							}
				    	}			    	
						break;
				    case 30:
				    	xvals[0]+=20; yvals[0]-=20;
				    	xvals[2]-=20; yvals[2]+=20;
				    	yvals[3]-=40;
				    	xvals[4]-=40; 
				    	if(xvals[0]>=0) {
				    		if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
				    				&&(!allcubes[xvals[3]/20][yvals[3]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=31;
							}
				    	}			    	
						break;
				    case 31:
				    	xvals[0]-=20; 
				    	xvals[1]+=20; yvals[1]-=20;
				    	xvals[2]-=20; yvals[2]-=20;
				    	xvals[3]+=20; yvals[3]+=20;
				    	xvals[4]+=40; yvals[4]-=20;
				    	if(xvals[1]<=380) {
				    		if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
				    				&&(!allcubes[xvals[4]/20][yvals[4]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=32;
							}
				    	}			    	
						break;
				    case 32:
				    	xvals[0]+=20; 
				    	xvals[1]-=20; yvals[1]+=20;
				    	xvals[2]+=20; yvals[2]+=20;
				    	xvals[3]+=20; yvals[3]-=20;
				    	yvals[4]+=20;
				    	if(yvals[4]<=540) {
				    		if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())&&(!allcubes[xvals[2]/20][yvals[2]/20].getOccupied())
				    				&&(!allcubes[xvals[4]/20][yvals[4]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=29;
							}
				    	}			    	
						break;
				    case 33:
				    	yvals[0]-=20;
				    	xvals[1]-=20;
				    	if(yvals[0]>=0) {
				    		if((!allcubes[xvals[0]/20][yvals[0]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=34;
							}
				    	}			    	
						break;
				    case 34:
				    	yvals[0]+=20;
				    	xvals[1]+=20;
				    	if(xvals[1]<=380) {
				    		if((!allcubes[xvals[1]/20][yvals[1]/20].getOccupied())) {
								resetBlock(alivecubes,xvals,yvals);
								blocktype=33;
							}
				    	}			    	
						break;
					}	
						
				}
				checkDown();
				isKeying=false;
			}
			
			System.out.println("New location: ");
			for(int i=0; i<alivecubes.length; i++) {
				System.out.print("("+alivecubes[i].getXcor()+","+alivecubes[i].getYcor()+")");
			}
			System.out.println();
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			checkDown();
		}
		public void resetBlock(Cube[] blockcubes, int[] xvals, int[] yvals) {
			for(int i=0; i<blockcubes.length; i++) {
				setColor(blockcubes[i],"grey");
			}
			for(int i=0; i<blockcubes.length; i++) {
				blockcubes[i]=allcubes[xvals[i]/20][yvals[i]/20];
			}
			for(int i=0; i<blockcubes.length; i++) {
				setColor(blockcubes[i],color);
			}
		}
		public void checkDown() {
			for(int i=0; i<alivecubes.length; i++) {
				if(alivecubes[i].getYcor()>=540) {
					if(sleepTurn==9) {touchdown=true;}
											
				}else if((allcubes[alivecubes[i].getXcor()/20][alivecubes[i].getYcor()/20+1].getOccupied())&&(sleepTurn==9)&&
						restcubes.contains(allcubes[alivecubes[i].getXcor()/20][alivecubes[i].getYcor()/20+1])) {
					touchdown=true;//only update touchdown to let descend thread know when at the end of sleeping, thus allowing final movements
				}
			}
		}
	};
	public void gameOver() {
		OverDialog over = new OverDialog(this);
		buggyOvers++;
	}
	public void reset() {
		for(int i=0; i<20; i++) {
			for(int j=0; j<28; j++) {
				setColor(allcubes[i][j],"grey");
			}
		}
		isOver=false;
	}
}
