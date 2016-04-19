package backup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class CustomTitlebar extends JFrame
{
JPanel p;
JMenuBar mb;
JButton close,min,max;
Font f=new Font("Arial",Font.BOLD,10);
int pX,pY;

    public CustomTitlebar()
    {
        createAndShowGUI();
    }
    
    private void createAndShowGUI()
    {
        // Custom look and feel
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){}
        
        setTitle("Custom Titlebar");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);        
        
        // Create JMenuBar
        mb=new JMenuBar();
        mb.setLayout(new BorderLayout());
        mb.setForeground(java.awt.Color.black);
        mb.setBackground(java.awt.Color.black);
        
        
        // Create panel
        p=new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(1,3));
        
        // Create buttons
        min=new JButton("-");
        max=new JButton("+");
        close=new JButton("x");
        
        min.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                // minimize
                setState(ICONIFIED);
            }
        });
        
        max.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                maximize();
            }
        });
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                // terminate program
                System.exit(0);
            }
        });
        
        // set focus painted false
        // i don't like it, so i removed it
        // if you like, you can remove these steps
        min.setFocusPainted(false);
        max.setFocusPainted(false);
        close.setFocusPainted(false);
        
        // font, again if you don't like you can
        // remove these steps, also remove the Font object
        min.setFont(f);
        max.setFont(f);
        close.setFont(f);
        
        // Add buttons
        p.add(close);
        p.add(max);
        p.add(min);
        
        // To west, mac style!
        mb.add(p,BorderLayout.WEST);
        
        // Add mouse listener for JMenuBar mb
        mb.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me)
            {
                // Get x,y and store them
                pX=me.getX();
                pY=me.getY();
            }
        });
        
        // Add MouseMotionListener for detecting drag
        mb.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me)
            {
                // Set the location
                // get the current location x-co-ordinate and then get
                // the current drag x co-ordinate, add them and subtract most recent
                // mouse pressed x co-ordinate
                // do same for y co-ordinate
                setLocation(getLocation().x+me.getX()-pX,getLocation().y+me.getY()-pY);
            }
        });
        
        // Set the menu bar
        //setJMenuBar(mb);
        this.setJMenuBar(mb);
        
        // Set size, visibility,shape and center it
        setSize(400,400);
        setVisible(true);
        setShape(new java.awt.geom.RoundRectangle2D.Double(0,0,getWidth(),getHeight(),5,5));
        setLocationRelativeTo(null);
    }
    
    private void maximize()
    {
    // Get GraphicsEnvironment object for getting GraphicsDevice object
    GraphicsEnvironment env=GraphicsEnvironment.getLocalGraphicsEnvironment();
    
    // Get the screen devices
    GraphicsDevice[] g=env.getScreenDevices();
    
    // I only have one, the first one
    // If current window is full screen, set fullscreen window to null
    // else set the current screen
    g[0].setFullScreenWindow(g[0].getFullScreenWindow()==this?null:this);
    }
    
    public static void main(String args[])
    {
        new CustomTitlebar();
    }
}