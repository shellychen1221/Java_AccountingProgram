package acc;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class Homepage {
   private JFrame frame;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Homepage window = new Homepage();
               window.frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }
    
   public Homepage() {
      initialize();
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize() {
      frame = new JFrame();
      frame.getContentPane().setBackground(new Color(188, 143, 143));
      frame.setBounds(100, 100, 405, 520);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(null);

      JButton btnNewButton = new JButton("list");
      btnNewButton.setBounds(128, 82, 109, 55);
      setting(frame, btnNewButton);
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Accounting s1 = new Accounting();
            s1.Screen2();
         }
      });
      JButton btnNewButton_1 = new JButton("reminder");
      btnNewButton_1.setBounds(110, 164, 144, 55);
      setting(frame, btnNewButton_1);
      btnNewButton_1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              String budgetInput = JOptionPane.showInputDialog(btnNewButton_1, "Please input a budget:");

              if (budgetInput == null || budgetInput.isEmpty()) {
                  JOptionPane.showMessageDialog(btnNewButton_1, "Invalid budget input");
                  return;
              }
              int enteredBudget = Integer.parseInt(budgetInput);
              int f = 0;  
              String filePath = "hello/data1.txt";
              File file = new File(filePath);
              try {
                  Scanner scanner = new Scanner(file);
                  
                  String line = scanner.nextLine();
                  String line2 = scanner.nextLine();
                  String line3 = scanner.nextLine();
                  String line4 = scanner.nextLine();
                  String line5 = scanner.nextLine();
                  
                  int a = Integer.parseInt(line);
                  int b = Integer.parseInt(line2);
                  int c = Integer.parseInt(line3);
                  f = Integer.parseInt(line5);
                  scanner.close();
              } catch (FileNotFoundException e1) {
                  e1.printStackTrace();
              }
               JOptionPane.showMessageDialog(btnNewButton_1, 
                  "Budget is sufficient. You are within the budget limit.");

              String filePath_budget = "hello/budget.txt";
              File file_budget = new File(filePath_budget);

              try {
                  File directory = new File("hello");
                  if (!directory.exists()) {
                      directory.mkdir();
                  }

                  if (file_budget.createNewFile()) {
                      System.out.println("budget.txt file created successfully.");
                  } else {
                      System.out.println("budget.txt file already exists.");
                  }

                  FileWriter fww = new FileWriter(file_budget);
                  BufferedWriter bww = new BufferedWriter(fww);
                  bww.write(budgetInput);
                  bww.close();
                  fww.close();

              } catch (IOException ex) {
                  ex.printStackTrace();
                  JOptionPane.showMessageDialog(btnNewButton_1, 
                      "Error while creating budget.txt: " + ex.getMessage());
              }
          }
      });
      JButton btnNewButton_2 = new JButton("graph analysis");
      btnNewButton_2.setBounds(85, 248, 191, 55);
      setting(frame, btnNewButton_2);
      btnNewButton_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GraphAnalysis s3 = new GraphAnalysis();
            s3.Screen3();
         }
      });

      JLabel lblNewLabel = new JLabel("Home page");
      lblNewLabel.setFont(new Font("Sitka Small", Font.ITALIC, 20));
      lblNewLabel.setForeground(Color.WHITE);
      lblNewLabel.setBounds(129, 42, 126, 49);
      frame.getContentPane().add(lblNewLabel);

      JLabel lblNewLabel_1 = new JLabel("");
      Image img = new ImageIcon(this.getClass().getResource("/Accounting.png")).getImage();
      lblNewLabel_1.setIcon(new ImageIcon(img));
      lblNewLabel_1.setBounds(-12, 10, 299, 60);
      frame.getContentPane().add(lblNewLabel_1);

      JLabel lblNewLabel_2 = new JLabel("");
      img = new ImageIcon(this.getClass().getResource("/pig.png")).getImage();
      lblNewLabel_2.setIcon(new ImageIcon(img));
      lblNewLabel_2.setBounds(70, 229, 261, 331);
      frame.getContentPane().add(lblNewLabel_2);

   }

   private static void setting(JFrame frame, JButton btnNewButton) {
      btnNewButton.setForeground(new Color(51, 51, 102));
      btnNewButton.setBackground(new Color(255, 250, 250));
      btnNewButton.setFont(new Font("Sitka Small", Font.BOLD, 15));
      btnNewButton.setBorder(new RoundedBorder(30));
      btnNewButton.setOpaque(false);
      btnNewButton.setFocusPainted(false);
      btnNewButton.setForeground(new Color(255, 250, 250));
      btnNewButton.setContentAreaFilled(true);
      frame.getContentPane().add(btnNewButton);
   }

   private static class RoundedBorder implements Border {

      private int radius;

      RoundedBorder(int radius) {
         this.radius = radius;
      }

      public Insets getBorderInsets(Component c) {
         return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
      }

      public boolean isBorderOpaque() {
         return true;
      }

      public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
         g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
      }
   }
}

