import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;

////////////////////////////////////////////////////////////////////////////////////////
////////
////////  PROJECT 3 Pong
//////// Margaret Medina-Pena and Bonnie Lin!!!!!!!!!
////////
////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////
////////
//////// PAIR CLASS
////////
////////////////////////////////////////////////////////////////////////////////////////

class Pair{
    double x;
    double y;

    public Pair(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Pair add(Pair p){
    	Pair pair = new Pair(0, 0);
    	pair.x = this.x + p.x;
    	pair.y = this.y + p.y;
    	return pair;
    }

    public Pair times(double t){
        Pair pair = new Pair(0, 0);
        pair.x = this.x * t;
        pair.y = this.y * t;
        return pair;
    }

    public void flipX(){
        this.x = - this.x;
        this.y = this.y;
    }

    public void flipY(){
        this.x = this.x;
        this.y = - this.y;
    }

    public Pair divide(double e){
        Pair pair = new Pair(0, 0);
        pair.x = this.x / e;
        pair.y = this.y / e;
        return pair;
    }
} // Pair Class

////////////////////////////////////////////////////////////////////////////////////////
////////
//////// PADDLE CLASS
////////
////////////////////////////////////////////////////////////////////////////////////////

class Paddle{
    Pair position;
    Pair velocity;
    static double direction;
    final int WIDTH = 1024;
    final int HEIGHT = 768;
    final int PLATWIDTH = 50;
    int PLATHEIGHT = 100;
    Color color;

    public Paddle() {
    	Random rand = new Random();
    	position = new Pair(0,500);
    	velocity = new Pair (0,direction);
    	color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public void setHeight(Sphere sphere){
      if (sphere.score1 == 2 || sphere.score2 == 2 || sphere.score1 == 3 || sphere.score2 == 3){
         PLATHEIGHT = 90;
      }
      if (sphere.score1 == 4 || sphere.score2 == 4 || sphere.score1 == 5 || sphere.score2 == 5){
          PLATHEIGHT = 80;
      }
      if (sphere.score1 == 6 || sphere.score2 == 6 || sphere.score1 == 7 || sphere.score2 == 7){
         PLATHEIGHT = 70;
      }
      if (sphere.score1 == 8 || sphere.score2 == 8 || sphere.score1 == 9 || sphere.score2 == 9){
          PLATHEIGHT = 60;
      }
    }

    public void drawPaddle1(Graphics g){
    	 Color c = g.getColor();
       g.setColor(color);
       if (position.y + velocity.y > 0 && position.y + velocity.y < HEIGHT-PLATHEIGHT){
    	    position.y = velocity.y + position.y;
    	}
      g.fillRect((int)position.x, (int)position.y, PLATWIDTH, PLATHEIGHT);
      g.setColor(c);
      }

     public void drawPaddle2(Graphics g){
    	Color c = g.getColor();
    	g.setColor(color);
    	position.x = WIDTH-PLATWIDTH;
    	if (position.y + velocity.y > 0 && position.y + velocity.y < HEIGHT-PLATHEIGHT){
    	    position.y = velocity.y + position.y;
    	}
    	g.fillRect((int)position.x, (int)position.y, PLATWIDTH, PLATHEIGHT);
    	g.setColor(c);
     }
}//class Paddle

////////////////////////////////////////////////////////////////////////////////////////
////////
//////// SPHERE CLASS
////////
///////////////////////////////////////////////////////////////////////////////////////

class Sphere{
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    public static int score1;
    public static int score2;
    final int PLATWIDTH = 50;
    int PLATHEIGHT;
    Color color;
    Boolean score = false;

    public Sphere(){
    	Random rand = new Random();
    	position = new Pair(500.0, 500.0);
    	velocity = new Pair(150.0, 0.0);
    	radius = 25;
    	color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public void update(World w, double time, Paddle paddle1, Paddle paddle2){
    	position = position.add(velocity.times(time));
    	score(w, paddle1, paddle2);
    }

    public void setPosition(Pair p){
	position = p;
    }

    public void setVelocity(Pair v){
	velocity = v;
    }

    public void setAcceleration(Pair a){
	acceleration = a;
    }

    public void draw(Graphics g){
	Color c = g.getColor();

	g.setColor(color);
	g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));
	g.setColor(c);
    }

    public void score(World w, Paddle paddle1, Paddle paddle2){
	if (position.x - radius < 0){
	    velocity.flipX();
	    position.x = w.width/2;
	    position.y = w.height/2;
      score = true;
	    score2 = score2 + 1;
      paddle1.setHeight(this);
      paddle2.setHeight(this);
}
	else if (position.x + radius > w.width){
	    velocity.flipX();
	    position.x = w.width/2;
	    position.y = w.height/2;
      score = true;
      score1 = score1 + 1;
      paddle1.setHeight(this);
      paddle2.setHeight(this);
	}
	if (position.y - radius < 0){
	    velocity.flipY();
              position.y = radius;
              score = false;
	}
	else if(position.y + radius >  w.height){
	    velocity.flipY();
	    position.y = w.height - radius;
      score = false;
	}
		//check paddle1
	if (position.x - radius <= paddle1.position.x + PLATWIDTH){
	    if(position.y + radius >= paddle1.position.y && position.y -radius <= paddle1.position.y + paddle1.PLATHEIGHT){
		double pointOnPaddle1 = position.y - paddle1.position.y;
		double angleY = 0;
    if (paddle1.PLATHEIGHT == 100){
        angleY = 1.5*pointOnPaddle1 - 75;
    }
    else if (paddle1.PLATHEIGHT == 90){
        angleY = (150/90)*pointOnPaddle1 - 75;
    }
    else if (paddle1.PLATHEIGHT == 80){
        angleY = (150/80)*pointOnPaddle1 - 75;
    }
    else if (paddle1.PLATHEIGHT == 70){
        angleY = (150/70)*pointOnPaddle1 - 75;
    }
    else if (paddle1.PLATHEIGHT == 60){
        angleY = (150/60)*pointOnPaddle1 - 75;
    }
		velocity.y = angleY;
		velocity.flipX();
  }
      score = false;
	}
  else if (position.x + radius >= paddle2.position.x){
	    if(position.y + radius >= paddle2.position.y && position.y - radius <= paddle2.position.y + paddle2.PLATHEIGHT){
		      double pointOnPaddle2 = position.y - paddle2.position.y;
		      double angleY = 0;
  		if (paddle2.PLATHEIGHT == 100){
  		    angleY = 1.5*pointOnPaddle2 - 75;
  		}
  	  else if (paddle2.PLATHEIGHT == 90){
  		    angleY = (150/90)*pointOnPaddle2 - 75;
  		}
  		else if (paddle2.PLATHEIGHT == 80){
  		    angleY = (150/80)*pointOnPaddle2 - 75;
  		}
  		else if (paddle2.PLATHEIGHT == 70){
  		    angleY = (150/70)*pointOnPaddle2 - 75;
  		}
      else if (paddle2.PLATHEIGHT == 60){
  		    angleY = (150/60)*pointOnPaddle2 - 75;
  		}
  		velocity.y = angleY;
  		velocity.x += 75;
  		velocity.flipX();
	}
  score = false;
}

    }	//check scores
} // class Sphere


////////////////////////////////////////////////////////////////////////////////////////
////////
//////// WORLD CLASS
////////
///////////////////////////////////////////////////////////////////////////////////////

class World{
    int height;
    int width;
    public static final int FPS = 60;
    final int WIDTH = 1024;
    final int HEIGHT = 768;
    final int PLATWIDTH = 50;
    int PLATHEIGHT;
    Sphere sphere;
    Paddle paddle1;
    Paddle paddle2;

    public World(int initWidth, int initHeight){
    	width = initWidth;
    	height = initHeight;
    	sphere = new Sphere();
    	paddle1 = new Paddle();
    	paddle2 = new Paddle();
    }

    public void drawSphere(Graphics g){
	     sphere.draw(g);
    }

    public void drawPaddles(Graphics r){
    	paddle1.drawPaddle1(r);
    	paddle2.drawPaddle2(r);
    }

    public void updateSpheres(double time, Paddle paddle1, Paddle paddle2){
	     sphere.update(this, time, paddle1, paddle2);
    }

    public void drawScore(Graphics g){
          String a = "Player 1's score is " + sphere.score1;
          int x1 = 50;
          int y1 = 50;

          String b = "Player 2's score is " + sphere.score2;
          int x2 = width - 300;
          int y2 = height - 50;

          g.drawString(a, x1, y1);
          g.drawString(b, x2, y2);

          if(sphere.score == true){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            sphere.score = false;
          }
    }

    public void drawQuitScreen(Graphics g){
	     g.setColor(Color.BLACK);
       g.fillRect(0, 0, WIDTH, HEIGHT);
       g.setColor(Color.WHITE);
       String a = "Thanks for playing!";
      g.drawString(a, width/2, height/2);
    }

    public void victoryToScreen(Graphics g){
	String winner = "";
	String b = " Play again to get your revenge!!!";

	if (sphere.score1 == 10){
	    winner = "Player 1";
	    sphere.color = Color.BLACK;
	}
	else if (sphere.score2 == 10){
	    winner = "Player 2";
	    sphere.color = Color.BLACK;
	}
	if (winner != ""){
	    String a = winner + " has won the match!";
	    sphere.velocity.x = 0;
	    sphere.velocity.y = 0;
	    g.drawString(a, width/3, height/2);
	    g.drawString(b, width/3-30, (height/2)+40);
	}
    }
}

////////////////////////////////////////////////////////////////////////////////////////
////////
//////// PONG CLASS
////////
///////////////////////////////////////////////////////////////////////////////////////

public class Pong extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    World world;
    Sphere sphere;
    Boolean quit = false;

    class Runner implements Runnable{
	public void run(){
	    while(true){
		world.updateSpheres(1.0 / (double)FPS, world.paddle1, world.paddle2);
		repaint();
    try{
		    Thread.sleep(1000/FPS);
		}
		catch(InterruptedException e){}
	    }
	}
    }

    public void keyPressed(KeyEvent e) {
	char c=e.getKeyChar();
       	if(c == 'r'||c == 'R') {
	      world.paddle1.velocity.y = -5;
	}

	else if (c == 'f'|| c == 'F') {
	      world.paddle1.velocity.y = 0;
	}

	else if (c == 'v' || c == 'V') {
	    world.paddle1.velocity.y = 5;
	}
	else if (c == 'u' || c == 'U') {
	    world.paddle2.velocity.y = -5;
	}
	else if (c == 'j' || c == 'J') {
	    world.paddle2.velocity.y = 0;
	}
	else if (c == 'n' || c == 'N') {
	    world.paddle2.velocity.y = 5;
	}
  else if (c == 'q' || c == 'Q') {
	    quit = true;
	}
    }

    public void keyReleased(KeyEvent e) {
	char c=e.getKeyChar();
    }

    public void keyTyped(KeyEvent e) {
	char c = e.getKeyChar();
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Pong(){
	world = new World(WIDTH, HEIGHT);
       	addKeyListener(this);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	Thread mainThread = new Thread(new Runner());
	mainThread.start();
    }


    public static void main(String[] args){
	JFrame frame = new JFrame("Pong Game!!!");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Pong mainInstance = new Pong();
	frame.setContentPane(mainInstance);
	frame.pack();
	frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, WIDTH, HEIGHT);
      g.setColor(Color.WHITE);
	    for(int i = 0; i < HEIGHT; i = i + 50){
		      g.fillRect(WIDTH/2, i, 5, 10);
      }
	    g.setFont(new Font("Dialog", Font.BOLD, 30));
      world.drawScore(g);
	    world.drawSphere(g);
	    world.drawPaddles(g);
	    world.victoryToScreen(g);
	    if (quit == true){
        world.drawQuitScreen(g);
	    }
    }
} // Pong class
