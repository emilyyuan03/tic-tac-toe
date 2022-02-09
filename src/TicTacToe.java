import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Tic Tac Toe: gui that simulates the game Tic Tac Toe, with two players playing on the same computer
 */
public class TicTacToe extends JFrame{
	
	private JLabel turnLabel;
	private String xPlayer;
	private String oPlayer;
	private boolean turn; //true means xPlayer's turn, false means oPlayer's turn
	private PicPanel[][] picGrid;
	private boolean gameEnded;
	
	public TicTacToe(){
		
		setSize(550, 700);
		setTitle("Tic Tac Toe");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ask for player names
		String name1 = JOptionPane.showInputDialog(null, "Please enter P1's name: ");
		String name2 = JOptionPane.showInputDialog(null, "Please enter P2's name: ");
		
		//determine who goes first randomly
		int randNum = (int)(Math.random()*2);
		if(randNum == 0){
			xPlayer = name1;
			oPlayer = name2;
		}
		else{
			xPlayer = name2;
			oPlayer = name1;
		}
		
		turn = true;
		
		//create the mainpanel where all other panels are added
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0,0,550,700);
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(110, 170, 240));
		
		//create the grid panel
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(3,3,5,5));
		grid.setBounds(0,0, 550, 550);
		grid.setBackground(Color.BLACK);
		
		picGrid = new PicPanel[3][3];
		for(int row=0; row<picGrid.length; row++) {
			for(int col=0; col<picGrid[0].length; col++) {
				picGrid[row][col] = new PicPanel();
				grid.add(picGrid[row][col]);
			}
		}
		
		mainPanel.add(grid);
		
		//create JLabels for player names and whose turn it is
		JLabel xLabel = new JLabel(xPlayer + " (X)");
		xLabel.setBounds(25, 560, 150, 20);
		mainPanel.add(xLabel);
		
		JLabel oLabel = new JLabel(oPlayer + " (O)");
		oLabel.setBounds(420, 560, 150, 20);
		mainPanel.add(oLabel);
		
		turnLabel = new JLabel(xPlayer + "'s Turn");
		turnLabel.setBounds(220, 640, 150, 20);
		mainPanel.add(turnLabel);
		
		add(mainPanel);
		setVisible(true);
	}
	
	//inner PicPanel class
	class PicPanel extends JPanel implements MouseListener{
		
		private BufferedImage image;
		private String imageName;
		
		public PicPanel(){
			setBackground(Color.white);
			this.addMouseListener(this);
		}
		
		//draws the image
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(image != null){
				int x = (this.getWidth() - image.getWidth(null)) / 2;
				int y = (this.getHeight() - image.getHeight(null))/ 2;
				g.drawImage(image, x, y, null);
			}
		}
		
		//when mouse is clicked
		public void mouseClicked(MouseEvent e) {
			
			//if no pic in panel currently & game is not over, draws pic
			if(image == null && !gameEnded){
				
				//if it is x player's turn
				if(turn){
					
					try{
						image = ImageIO.read(new File("x.png"));
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, "Could not read in the pic");
						System.exit(0);
					}
					
					turnLabel.setText(oPlayer + "'s turn");
					imageName = "x";
					determineWinner(xPlayer);
				}
				
				//if it is o player's turn
				else{
					
					try{
						image = ImageIO.read(new File("o.png"));
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, "Could not read in the pic");
						System.exit(0);
					}
					
					turnLabel.setText(xPlayer + "'s turn");
					imageName = "o";
					determineWinner(oPlayer);
				}
				
				turn = !turn;
			}
			
			this.repaint();
		}
		
		//checks row/col of most recent move to check if there is 3 in a row or if there is a tie, updates jlabel
		private void determineWinner(String curPlayer) {
			
			//find the current row + col of the picpanel that was just clicked
			int curRow = 0;
			int curCol = 0;
			
			for(int row=0; row<picGrid.length; row++) {
				for(int col=0; col<picGrid[0].length; col++) {
					if(picGrid[row][col] == this) {
						curRow = row;
						curCol = col;
					}
				}
			}
			
			String pic = picGrid[curRow][curCol].imageName;
			
			//check horizontals
			if(picGrid[curRow][0].imageName!=null && picGrid[curRow][1].imageName!=null && picGrid[curRow][2].imageName!=null) {
				if(picGrid[curRow][0].imageName.equals(pic) && picGrid[curRow][1].imageName.equals(pic) && picGrid[curRow][2].imageName.equals(pic)) {
					turnLabel.setText(curPlayer + " won!");
					gameEnded = true;
				}
			}
			
			//check verticals
			if(picGrid[0][curCol].imageName!=null && picGrid[1][curCol].imageName!=null && picGrid[2][curCol].imageName!=null) {
				if(picGrid[0][curCol].imageName.equals(pic) && picGrid[1][curCol].imageName.equals(pic) && picGrid[2][curCol].imageName.equals(pic)) {
					turnLabel.setText(curPlayer + " won!");
					gameEnded = true;
				}
			}
			
			//check the 2 diagonals
			if(picGrid[0][0].imageName!=null && picGrid[1][1].imageName!=null && picGrid[2][2].imageName!=null) {
				if(picGrid[0][0].imageName.equals(picGrid[1][1].imageName) && picGrid[2][2].imageName.equals(picGrid[1][1].imageName)) {
					turnLabel.setText(curPlayer + " won!");
					gameEnded = true;
				}
			}
			
			if(picGrid[0][2].imageName!=null && picGrid[1][1].imageName!=null && picGrid[2][0].imageName!=null) {
				if(picGrid[0][2].imageName.equals(picGrid[1][1].imageName) && picGrid[2][0].imageName.equals(picGrid[1][1].imageName)) {
					turnLabel.setText(curPlayer + " won!");
					gameEnded = true;
				}
			}
			
			//check for tie- if all spots are filled
			int count = 0;
			for(int row=0; row<picGrid.length; row++)
				for(int col=0; col<picGrid[0].length; col++)
					if(picGrid[row][col].imageName != null)
						count++;
			
			if(count == 9) {
				turnLabel.setText("Tie!");
				gameEnded = true;
			}
			
		}
		
		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}
		
	}
	
	public static void main(String[] args){
		new TicTacToe();
	}

}
