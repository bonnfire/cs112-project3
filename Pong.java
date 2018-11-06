
    import javax.swing.JPanel;
    import javax.swing.JFrame;
    import java.awt.Color;
    import java.awt.Graphics;
    import java.awt.Dimension;
    import java.util.Random;

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
    }

    class Sphere{
        Pair position;
        Pair velocity;
        Pair acceleration;
        double radius;
        int score1;
        int score2;
        // double dampening;

        Color color;

        public Sphere()
        {
            Random rand = new Random();
            position = new Pair(500.0, 500.0);
            velocity = new Pair((double)(rand.nextInt(1000) - 500), (double)(rand.nextInt(1000) - 500));
            acceleration = new Pair(0.0, 200.0);
            radius = 25;
            //dampening = 1.1;
            color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        }

        public void update(World w, double time){
            position = position.add(velocity.times(time));
            velocity = velocity.add(acceleration.times(time));
            score(w);
            bounce(w);
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
            g.drawOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));
            g.setColor(c);
        }

        public void score(World w){
          Boolean bounced = false;
          if (position.x - radius < 0){
              velocity.flipX();
              position.x = radius;
              score1 = score1 + 1;
              System.out.println("Player 1's score is " + score1);
              bounced = true;
          }
          else if (position.x + radius > w.width){
              velocity.flipX();
              position.x = w.width - radius;
              score2 = score2 + 1;
              System.out.println("Player 2's score is " + score2);
              bounced = true;
          }
      }


        private void bounce(World w){
          //Include the Rectangle class
          // BAD CODE BELOW
            Boolean bounced = false;

            if (position.x - 50 - radius < 0 && position.y - 100 - radius < 0){
                velocity.flipX();
                position.x = radius - 50;
                position.y = radius - 100;
                bounced = true;
            }
            else if (position.x + 50 + radius > w.width && position.y + 100 + radius > w.width){
                velocity.flipX();
                position.x = w.width - radius - 50;
                position.y = w.width - radius - 100;
                bounced = true;
            }

            if (position.y - radius < 0){
                velocity.flipY();
                position.y = radius;
                bounced = true;
            }
            else if(position.y + radius >  w.height){
                velocity.flipY();
                position.y = w.height - radius;
                bounced = true;
          }

            //if (bounced){
            //    velocity = velocity.divide(dampening);
            //}
        }
    } // class Sphere

    class World{
        int height;
        int width;
        int numSpheres;
        Sphere spheres[];

        public World(int initWidth, int initHeight, int initNumSpheres){
            width = initWidth;
            height = initHeight;

            numSpheres = initNumSpheres;
            spheres  = new Sphere[numSpheres];

            for (int i = 0; i < numSpheres; i ++)
                {
                    spheres[i] = new Sphere();
                }

        }

        public void drawSpheres(Graphics g){
            for (int i = 0; i < numSpheres; i++){
                spheres[i].draw(g);
            }
        }

        public void updateSpheres(double time){
            for (int i = 0; i < numSpheres; i ++)
                spheres[i].update(this, time);
        }

    }

    public class Pong extends JPanel{
        public static final int WIDTH = 1024;
        public static final int HEIGHT = 768;
        public static final int PLATWIDTH = 50;
        public static final int PLATHEIGHT = 100;
        public static final int FPS = 60;
        World world;

        class Runner implements Runnable{
            public void run(){
                while(true){
                    world.updateSpheres(1.0 / (double)FPS);
                    repaint();
                    try{
                        Thread.sleep(1000/FPS);
                    }
                    catch(InterruptedException e){}
                }
            }
        }

        public Pong(){
            world = new World(WIDTH, HEIGHT, 1);
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            Thread mainThread = new Thread(new Runner());
            mainThread.start();
        }

        public static void main(String[] args){
            JFrame frame = new JFrame("Physics!!!");
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
            g.fillRect(0, HEIGHT/2, PLATWIDTH, PLATHEIGHT);
            g.fillRect(WIDTH-PLATWIDTH, HEIGHT/2, PLATWIDTH, PLATHEIGHT);
            world.drawSpheres(g);
        }
    }
