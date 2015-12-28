package typing;
import java.applet.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class applet extends Applet implements KeyListener {
        int charx;
        int chary;
        String wordtext;
        String keytext;
        String timetext;

        public void init(){
                setSize(500,700);
                setBackground(Color.orange);
                addKeyListener(this);
                charx=30;
                chary=100;
                keytext="";
        }
        public void paint(Graphics g){
                g.setColor(Color.red);
            Font font1=new Font("",Font.PLAIN,32);
                g.setFont(font1);
                g.drawString(keytext,charx, chary);

                g.setColor(Color.white);
                Font font2=new Font("",Font.BOLD,38);
                g.setFont(font2);
                g.drawString("type10Words!", 30, 50);

                g.setColor(Color.cyan);
                Font font3=new Font("",Font.PLAIN,36);
                g.setFont(font3);
                g.drawString("TIME:", 30,600 );


        }

    public void keyPressed(KeyEvent e){
        System.out.print(e.getKeyChar());
        keytext+=e.getKeyChar();
        repaint();
        }

        public void keyReleased(KeyEvent e){

        }

        public void keyTyped(KeyEvent e){

        }

}
