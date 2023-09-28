package chatting.app;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
    
    JTextField textarea;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server(){
        f.setLayout(null);
        
        JPanel pl= new JPanel();
        pl.setBackground(new Color(7,94,84));
        pl.setBounds(0,0,950,70);
        pl.setLayout(null);
        f.add(pl);
        
        ImageIcon im1 = new ImageIcon(ClassLoader.getSystemResource("Project/3.png"));
        Image im2 = im1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon im3= new ImageIcon(im2);
        JLabel back = new JLabel(im3);
        back.setBounds(5,20,25,25);
        pl.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        ImageIcon im4 = new ImageIcon(ClassLoader.getSystemResource("Project/11.jpg"));
        Image im5 = im4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon im6= new ImageIcon(im5);
        JLabel profile = new JLabel(im6);
        profile.setBounds(40,10,50,50);
        pl.add(profile);
        
        ImageIcon im7 = new ImageIcon(ClassLoader.getSystemResource("Project/video.png"));
        Image im8 = im7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon im9= new ImageIcon(im8);
        JLabel video = new JLabel(im9);
        video.setBounds(300,20,30,30);
        pl.add(video);
        
        ImageIcon im10 = new ImageIcon(ClassLoader.getSystemResource("Project/phone.png"));
        Image im11 = im10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon im12= new ImageIcon(im11);
        JLabel phone = new JLabel(im12);
        phone.setBounds(360,20,30,30);
        pl.add(phone);
        
        ImageIcon im13 = new ImageIcon(ClassLoader.getSystemResource("Project/3icon.png"));
        Image im14 = im13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon im15= new ImageIcon(im14);
        JLabel dot = new JLabel(im15);
        dot.setBounds(410,20,10,25);
        pl.add(dot);
        
        JLabel name = new JLabel("Pritom");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("San_Serif",Font.BOLD,18));
        pl.add(name);
        
        JLabel status = new JLabel("Online");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("San_Serif",Font.BOLD,14));
        pl.add(status);
        
        
        a1 = new JPanel();
        a1.setBounds(5,75,440,580);
        //a1.setBackground(new Color(236, 229, 221));
        f.add(a1);
        
        textarea = new JTextField();
        textarea.setBounds(5,655,310,40);
        textarea.setFont(new Font("San_Serif",Font.PLAIN,16));
        f.add(textarea);
        
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setFont(new Font("San_Serif",Font.PLAIN,16));
        send.addActionListener(this);
        send.setForeground(Color.WHITE);
        f.add(send);
        
        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        
        f.setVisible(true);
    }
   
    
     public void actionPerformed(ActionEvent ae){
         try {
         String output = textarea.getText();
         
         //JLabel out =new JLabel(output);
         
         JPanel p2 = formatLabel(output);
         //p2.add(out);
         
         a1.setLayout(new BorderLayout());
         
         JPanel right = new JPanel(new BorderLayout());
         right.add(p2,BorderLayout.LINE_END);
         vertical.add(right);
         vertical.add(Box.createVerticalStrut(15));
         
         a1.add(vertical,BorderLayout.PAGE_START);
         
         dout.writeUTF(output);
         
         textarea.setText("");
         
         f.repaint();
         f.invalidate();
         f.validate();
         }
         catch(Exception e){
             
             e.printStackTrace();
             
         }
     
    }
     
     public static JPanel formatLabel(String output){
         
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
         
         JLabel out = new JLabel("<html><p style=\"width: 150px\">" + output + "</p></html>");
         out.setFont(new Font("Tahoma",Font.PLAIN,16));
         out.setBackground(new Color(37,211,102));
         out.setOpaque(true);
         out.setBorder(new EmptyBorder(15,15,15,50));
         panel.add(out);
         
         Calendar cal = Calendar.getInstance();
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         
         JLabel time = new JLabel();
         time.setText(sdf.format(cal.getTime()));
         panel.add(time);
         return panel;
     }
     
     
     
    public static void main(String[] args){
        new Server();
        
        try{
            ServerSocket skt = new ServerSocket(8000);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}