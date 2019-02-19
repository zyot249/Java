package lecture.no1;

import javax.swing.*;

public class SayHello {
    public static void main(String args[]){
        String yourName = JOptionPane.showInputDialog(null,"Enter your name","Your Name");
        JOptionPane.showMessageDialog(null,"Welcome to the " + yourName +"'s world!!\n And happy new year!","Say Hello",JOptionPane.INFORMATION_MESSAGE);
    }
}
