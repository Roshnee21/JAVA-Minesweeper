


//Main Class:








public class Main implements Runnable{

	GUI window= new GUI();
	
	public static void main(String[] args) {
		new Thread(new Main()).start();
	}

	@Override
	public void run() {
        while(true) {
        	if(window.resetter==false) {
        		window.VictoryStatus();
        		
        		
        	window.repaint();
        		
        }
	}

}


























//GUI CLASS:


import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class GUI extends JFrame{
	
	Date initDate=new Date();
	int sec;
	
	int spacing=1;
	int px;
	int py;
	int neigh_mines;
	
	boolean resetter=false;
	
	boolean setFlag=false;
	
	boolean happiness = true;
	boolean victory=false;
	boolean defeat=false;
	
	int reveal_count=0;
	int mine_ctr=0;
	
	int mines[][] = new int[9][9];
	int neighbour[][]= new int [9][9];
	boolean revealed[][]=new boolean[9][9];
	boolean flagged[][]=new boolean[9][9];
	
	Random rand= new Random();
          public GUI() {
    	  this.setTitle("MineSweeper");
    	  this.setSize(460, 590);
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setResizable(false);
    	  this.setVisible(true);
   



 	  
    	  for(int i=0;i<9;i++) {
			  for(int j=0;j<9;j++) {
				  if(rand.nextInt(81)<10) {
					  mines[i][j]=1;
					  
				  }
				  revealed[i][j]=false;
			  }
    	  }

    	  
    	  for(int i=0;i<9;i++) {
			  for(int j=0;j<9;j++) { 
				  neigh_mines=0;
				  for(int m=0;m<9;m++) {
				    for(int n=0;n<9;n++) {
					 if(MineCheck(i,j,m,n) == true && !(m==i && n==j))
							  neigh_mines++;
					  }
				  }
				  neighbour[i][j]=neigh_mines;
			  }
    	  }
    	  
					  
    	  
    	    	  Board b=new Board();
    	  this.setContentPane(b);
    	  
    	  Move move = new Move();
    	  this.addMouseMotionListener(move);
    	  
    	  Click click=new Click();
    	  this.addMouseListener(click);
    	  
    	 }
      
  public class Board extends JPanel{
	  public void paintComponent(Graphics g) {
		  
		  
		  //background
		  g.setColor(Color.black);
		  g.fillRect(0, 0, 1000, 1000);
		  
		  
		  //grid
		  for(int i=0;i<9;i++)
			  for(int j=0;j<9;j++) {
				  g.setColor(Color.gray);
				   
				  //mines 	this if-statement is to be deleted
				 //	if(mines[i][j]==1)
				 	//	g.setColor(Color.green);
			
				 	
				 	
//mouse cursor
if(px>=i*50+spacing+8 && px<=i*50+spacing+40+8 &&
				 			py>=j*50+100+spacing+31 && py<=j*50+100+spacing+40+31)   
				 		g.setColor(Color.blue);
				 
				 	
				 	//onclick event
                    if(revealed[i][j]==true) {
                    	g.setColor(Color.white);
                    }
                    
                    //neighbours
				   g.fillRect(i*50+spacing, j*50+100+spacing, 40, 40);
				  if(revealed[i][j]==true)
				  {
                  
					  if(mines[i][j]==0 && neighbour[i][j]!=0) {
						  if(neighbour[i][j]==1)
				   g.setColor(Color.black);
						  if(neighbour[i][j]==2)
							   g.setColor(Color.blue);
						  if(neighbour[i][j]==3)
							   g.setColor(Color.pink);
						  if(neighbour[i][j]==4)
							   g.setColor(Color.green);
						  if(neighbour[i][j]==5)
							   g.setColor(Color.yellow);
						  if(neighbour[i][j]==6)
							   g.setColor(Color.darkGray);
						  if(neighbour[i][j]==7)
							   g.setColor(Color.red);
							   
				   g.setFont(new Font("Stardos Stencil",Font.BOLD,30));
                   g.drawString(Integer.toString(neighbour[i][j]), i*50+spacing+8+3, j*50+100+spacing+31 );
					  }
					  
					  
					  
					  
					  //mines
					  else if(mines[i][j]==1){
						  g.setColor(Color.red);
						  g.fillRect(i*50+spacing, j*50+100+spacing, 40, 40);
						  g.setColor(Color.black);
                          g.fillOval(i*50+spacing+7,j*50+100+spacing+ 7,25, 25);
								  
					  }
					  
				  }
				  }
		     //reset
		    g.setColor(Color.yellow);
		    g.fillRect(195, 30, 50, 50);
		    g.setColor(Color.black);
            g.setFont(new Font("Stardos Stencil",Font.BOLD,14));
            g.drawString("RESET",197, 56);
	       
		    //flags
		    for(int i=0;i<9;i++)
				  for(int j=0;j<9;j++) {
					  if(flagged[i][j]==true && revealed[i][j]!=true) {
						  g.setColor(Color.black);
				            g.fillRect(i*50+6+18, j*50+50+38+22, 3, 20);
				            g.setColor(Color.red);
				            g.fillRect(i*50+6+4, j*50+50+38+22, 15, 10);
				            g.fillRect(i*50+6+12, j*50+50+38+40, 15,3);

					  }
				  }
		    
		     //timer
		    g.setColor(Color.white);
		    
		    if(!(victory==true || defeat==true))
		    sec = (int) ((new Date().getTime()-initDate.getTime())/1000);
		    g.setFont(new Font("Stardos Stencil",Font.BOLD,30));
            g.drawString(Integer.toString(sec), 380, 60);
		    
		       
            //winMsg
            if(victory==true) {
            	
            g.setColor(Color.green);
            g.setFont(new Font("Stardos Stencil",Font.BOLD,30));
            g.drawString("You Won", 20, 40);
            
	    }else if(defeat==true) {
	    	g.setColor(Color.red);
            g.setFont(new Font("Stardos Stencil",Font.BOLD,30));
            g.drawString("You lost", 20, 40);
             for(int p=0;p<9;p++)
            	 for(int q=0;q<9;q++)
            		 if(mines[p][q]==1) {
            			 g.setColor(Color.red);
						  g.fillRect(p*50+spacing, q*50+100+spacing, 40, 40);
						  g.setColor(Color.black);
                         g.fillOval(p*50+spacing+7,q*50+100+spacing+ 7,25, 25);
						
            		 }
	    }
            
            //flag
            if(setFlag==false)
            g.setColor(Color.gray);
            else if(setFlag==true)
            g.setColor(Color.red);

            g.drawRect(280, 30, 40, 40);
            g.setColor(Color.gray);
            g.fillRect(305	, 40, 3, 20);
            g.setColor(Color.red);
            g.fillRect(290, 40, 15, 10);
            g.fillRect(298, 60, 15,3);



            }
	  }
  public class Move implements MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		px=e.getX()	;
		py=e.getY();
		/*System.out.println(px+","+py);
*/
}
	  
  }
  public class Click implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<9;i++)
			  for(int j=0;j<9;j++) {
				 	if(px>=i*50+spacing+8 && px<=i*50+spacing+40+8 &&
				 			py>=j*50+100+spacing+31 && py<=j*50+100+spacing+40+31) {
				 		System.out.println((j+1)+","+(i+1));
				 		if(setFlag==false && flagged[i][j]==false ) {
				 		revealed[i][j]=true;
				 		autoReveal(i,j);
				 		reveal_count++;
				 		System.out.println(reveal_count);
				 		}
				 		else if(setFlag==true && revealed[i][j]!=true) {
				 			flagged[i][j]=true;
				 		}
				 	}
	}
		
		if(px>=280+6 && px<=320+6 && py>=30+28 && py<=30+40+28) {
			if(setFlag==false)
				setFlag=true;
			else if(setFlag==true)
				setFlag=false;
			System.out.println(setFlag);
		}
		
		if(inSmiley() == true) {
			System.out.println("true");
			ResetAll();
		}
}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	  
  }
    public boolean inSmiley() {
    	if(px>=195+6 && px<=195+50+6 && py>=40+28 && py<=40+50+28 )
    		return true;
    	
    	return false;
    }
    
   public void ResetAll() {
    	
    	resetter = true;
    	
    	 happiness = true;
    	 victory=false;
    	 defeat=false;
    	 reveal_count=0;
    	 
    	 initDate = new Date();
    	
    	 for(int i=0;i<9;i++)
			  for(int j=0;j<9;j++) 
				  mines[i][j]=0;
    	 
    	  for(int i=0;i<9;i++)
			  for(int j=0;j<9;j++) {
				  if(rand.nextInt(81)<10) {
					  mines[i][j]=1;
				  }
				  revealed[i][j]=false;
				  flagged[i][j]=false;
			  }
    	  
    	  
    	  for(int i=0;i<9;i++) {
			  for(int j=0;j<9;j++) { 
				  neigh_mines=0;
				  for(int m=0;m<9;m++) {
				   for(int n=0;n<9;n++) {
					 if(MineCheck(i,j,m,n) == true && !(m==i && n==j))
							  neigh_mines++;
					  }
				  }
				  neighbour[i][j]=neigh_mines;
			  }
    	  }
    	
    	resetter = false;
    }
    public void VictoryStatus() {
    	for(int i=0;i<9;i++) {
			  for(int j=0;j<9;j++) {
				 if(mines[i][j]==1 && revealed[i][j]==true) {
					 happiness=false;
					 defeat=true;
				 }
					 
			  }
		}
    	if(reveal_count>=81-mineCount())
    		victory=true;
    }
    
    public int mineCount() {
    	int total=0;
    	for(int i=0;i<9;i++) {
			  for(int j=0;j<9;j++) {
				  if(mines[i][j]==1)
					  total++;
			  }
    	}
    	return total;
    }
    
    
	public boolean MineCheck(int mx, int my, int cx, int cy) {
		if(mx-cx<2 && mx-cx>-2 && my-cy<2 && my-cy>-2 && mines[cx][cy]==1)
			return true;
		else
			return false;
	}
	public boolean blankCheck(int mx, int my, int cx, int cy) {
		if(mx-cx<2 && mx-cx>-2 && my-cy<2 && my-cy>-2 && neighbour[cx][cy]==0)
			return true ;
		else
			return false;
	}
 
 
   public void autoReveal(int x,int y) {
	   int count=0;
	   if(neighbour[x][y]==0 && mines[x][y]!=1) {
		  count+= revealNeighbour(x-1,y-1);
		  count+= revealNeighbour(x-1,y);
		  count+=revealNeighbour(x-1,y+1);
		  count+=revealNeighbour(x,y-1);
		  count+= revealNeighbour(x,y+1);
		  count+= revealNeighbour(x+1,y-1);
		  count+=revealNeighbour(x+1,y);
		  count+=revealNeighbour(x+1,y+1);
		  
		  if(count==8) {
			  autoReveal(x-1,y-1);
			  autoReveal(x-1,y);
			  autoReveal(x-1,y+1);
			  autoReveal(x-1,y-1);
			  autoReveal(x-1,y+1);
			  autoReveal(x-1,y-1);
			  autoReveal(x-1,y);
			  autoReveal(x-1,y+1);

		  }
         		  }
		}
   int revealNeighbour(int x,int y) {
	   if(x>=0 && x<=8 && y>=0 && y<=8) {
	         if( revealed[x][y]!=true && flagged[x][y]==false) {
	        revealed[x][y]=true;
	        reveal_count++;
	        return 1;
	         }
	   }
	         return 0;
	   
	   
   }
   }
   

				 
