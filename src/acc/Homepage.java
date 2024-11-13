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

      JButton listButton = new JButton("list");
      listButton.setBounds(128, 82, 109, 55);
      setting(frame, listButton);
      listButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Accounting s1 = new Accounting();
            s1.Screen2();
         }
      });
      JButton reminderButton = new JButton("reminder");
      reminderButton.setBounds(110, 164, 144, 55);
      setting(frame, reminderButton);
      reminderButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              String budgetInput = JOptionPane.showInputDialog(reminderButton, "Please input a budget:");

              if (budgetInput == null || budgetInput.isEmpty()) {
                  JOptionPane.showMessageDialog(reminderButton, "Invalid budget input");
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
               JOptionPane.showMessageDialog(reminderButton, 
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
                  JOptionPane.showMessageDialog(reminderButton, 
                      "Error while creating budget.txt: " + ex.getMessage());
              }
          }
      });
      JButton GraphButton = new JButton("graph analysis");
      GraphButton.setBounds(85, 248, 191, 55);
      setting(frame, GraphButton);
      GraphButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            GraphAnalysis s3 = new GraphAnalysis();
            s3.Screen3();
         }
      });

      JLabel HomepageLabel = new JLabel("Home page");
      HomepageLabel.setFont(new Font("Sitka Small", Font.ITALIC, 20));
      HomepageLabel.setForeground(Color.WHITE);
      HomepageLabel.setBounds(129, 42, 126, 49);
      frame.getContentPane().add(HomepageLabel);

      JLabel Accountingimg = new JLabel("");
      Image img = new ImageIcon(this.getClass().getResource("/Accounting.png")).getImage();
      Accountingimg.setIcon(new ImageIcon(img));
      Accountingimg.setBounds(-12, 10, 299, 60);
      frame.getContentPane().add(Accountingimg);

      JLabel Pigimg = new JLabel("");
      img = new ImageIcon(this.getClass().getResource("/pig.png")).getImage();
      Pigimg.setIcon(new ImageIcon(img));
      Pigimg.setBounds(70, 229, 261, 331);
      frame.getContentPane().add(Pigimg);

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

