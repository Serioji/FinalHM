package SnakeGame;
import javax.swing.*;
import java.awt.*;
 public class Label extends JFrame {
    public Label(String s){
        Frame f= new Frame("Label Example");
        Label l1;
        l1=new Label("Points.");
        l1.setBounds(700,250, 100,30);
        f.add(l1);
        f.setSize(200,200);
        f.setLayout(null);
        f.setVisible(true);
    }
}