/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainClass {

  public static void main(String[] args) {
    JFrame aWindow = new JFrame();
    aWindow.setBounds(200, 200, 200, 200);
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    aWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    aWindow.getContentPane().setBackground(Color.PINK);
    aWindow.setVisible(true);
  }
}