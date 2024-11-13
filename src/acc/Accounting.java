package acc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.JPanel;

import java.util.Scanner;
import java.util.Vector;
import javax.swing.JScrollBar;
import java.awt.SystemColor;

public class Accounting {

private JFrame frame;
final Object[] columnNames = { "breakfast", "lunch", "dinner", "daily supply", "settlement" };
final Object[][] rowData = {};
private JTextField textField_1;
private JTextField textField_2;

/**
 * Launch the application.
   */
public void Screen2() {
   EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
         try {
            Accounting window = new Accounting();
            window.frame.setVisible(true);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   });
}

/**
 * Create the application.
   */
public Accounting() {
   initialize();
}

/**
 * Initialize the contents of the frame.
   */
private void initialize() {

   frame = new JFrame();
   frame.getContentPane().setBackground(new Color(188, 143, 143));
   frame.setBounds(100, 100, 463, 630);
   frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   frame.getContentPane().setLayout(null);

   JButton btnNewButton = new JButton("Back");
   btnNewButton.setBackground(new Color(255, 250, 250));
   btnNewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         frame.setVisible(false);
      }
   });
   btnNewButton.setFont(new Font("Dialog", Font.BOLD, 14));
   btnNewButton.setBounds(10, 10, 61, 38);
   frame.getContentPane().add(btnNewButton);

   JPanel panel = new JPanel();
   panel.setBounds(10, 58, 423, 385);
   frame.getContentPane().add(panel);

   TableModel tableModel = new DefaultTableModel(rowData, columnNames);
   JTable table = new JTable(tableModel);
   RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
   final TableModel tableModel1 = table.getModel();
   tableModel1.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
         int firstRow = e.getFirstRow();
         int lastRow = e.getLastRow();
         int column = e.getColumn();
         int type = e.getType();

         if (type == TableModelEvent.UPDATE) {
            if (column < 0 || column > 3) {
               return;
            }

            for (int row = firstRow; row <= lastRow; row++) {
               try {
                  Object breakfestObj = tableModel1.getValueAt(row, 0);
                  Object lunchObj = tableModel1.getValueAt(row, 1);
                  Object dinnerObj = tableModel1.getValueAt(row, 2);
                  Object dailysupplyObj = tableModel1.getValueAt(row, 3);

                  int breakfest = parseInteger(breakfestObj);
                  int lunch = parseInteger(lunchObj);
                  int dinner = parseInteger(dinnerObj);
                  int dailysupply = parseInteger(dailysupplyObj);

                  int totalScore = breakfest + lunch + dinner + dailysupply;
                  tableModel1.setValueAt(totalScore, row, 4);

                  int sum = 0;
                  
                  for (int i = 0; i < table.getRowCount(); i++) {
                     sum += parseInteger(table.getValueAt(i, 4));
                  }
                  
                  textField_1.setText(Integer.toString(sum));

               int budget = 0;
                  String filePath_budget = "hello/budget.txt";
                  File file_budget = new File(filePath_budget);
                  try {
                     Scanner scanner = new Scanner(file_budget);
                     budget = Integer.parseInt(scanner.nextLine());
                     scanner.close();
                  } catch (FileNotFoundException e1) {
                     e1.printStackTrace();
                  }

                  if (budget < sum) {
                     JOptionPane.showMessageDialog(btnNewButton, 
                        "YOU ARE OVER BUDGET.",
                        "Budget Exceeded", 
                        JOptionPane.WARNING_MESSAGE);
                  }
                  float average = sum / (float) table.getRowCount();
                  textField_2.setText(Float.toString(average));

               } catch (NumberFormatException ex) {
                  System.err.println("Error parsing number: " + ex.getMessage());
               }
            }
         }
      }
   });

   MyCellEditor cellEditor = new MyCellEditor(new JTextField());

   for (int i = 1; i < columnNames.length; i++) {
      TableColumn tableColumn = table.getColumn(columnNames[i]);
      tableColumn.setCellEditor(cellEditor);
   }
   table.setRowSorter(rowSorter);
   table.setRowSorter(rowSorter);
   table.setModel(tableModel1);
   panel.add(table.getTableHeader(), BorderLayout.NORTH);
   JLabel lblNewLabel = new JLabel("None record yet...");
   panel.add(lblNewLabel);
   lblNewLabel.setFont(new Font("Lucida Bright", Font.PLAIN, 18));
   lblNewLabel.setForeground(Color.BLACK);
   panel.add(table, BorderLayout.CENTER);
   JButton btnNewButton_1 = new JButton("+");
   btnNewButton_1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         ((DefaultTableModel) tableModel1).addRow(new Object[]{""});
         lblNewLabel.setVisible(false);
      }
   });
   btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 24));
   btnNewButton_1.setBackground(new Color(255, 250, 250));
   btnNewButton_1.setBounds(372, 13, 61, 38);
   frame.getContentPane().add(btnNewButton_1);

   textField_1 = new JTextField();
   textField_1.setBounds(137, 21, 86, 21);
   frame.getContentPane().add(textField_1);
   textField_1.setColumns(10);

   JLabel lblNewLabel_1 = new JLabel("Settlement");
   lblNewLabel_1.setForeground(Color.WHITE);
   lblNewLabel_1.setFont(new Font("Consolas", Font.BOLD, 11));
   lblNewLabel_1.setBounds(75, 10, 66, 38);
   frame.getContentPane().add(lblNewLabel_1);

   JLabel lblNewLabel_2 = new JLabel("Average");
   lblNewLabel_2.setForeground(Color.WHITE);
   lblNewLabel_2.setFont(new Font("Consolas", Font.BOLD, 11));
   lblNewLabel_2.setBounds(233, 14, 52, 32);
   frame.getContentPane().add(lblNewLabel_2);

   textField_2 = new JTextField();
   textField_2.setBounds(284, 19, 86, 21);
   frame.getContentPane().add(textField_2);
   textField_2.setColumns(10);

   JButton btnNewButton_2 = new JButton("export");
   btnNewButton_2.setBackground(SystemColor.control);
   btnNewButton_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
   btnNewButton_2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         String filePath = "hello/data.csv";
         File file = new File(filePath);
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (int i = 0; i < table.getColumnCount(); i++) {
               bw.write(table.getColumnName(i));
               if (i < table.getColumnCount() - 1) {
                  bw.write(",");
               }
            }
            bw.newLine();

            for (int i = 0; i < table.getRowCount(); i++) {
               for (int j = 0; j < table.getColumnCount(); j++) {
                  Object value = table.getValueAt(i, j);
                  bw.write(value != null ? value.toString() : "");
                  if (j < table.getColumnCount() - 1) {
                     bw.write(",");
                  }
               }
               bw.newLine();
            }

            JOptionPane.showMessageDialog(frame, "Data exported successfully!");

         } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error exporting data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
      }
   });
   btnNewButton_2.setBounds(10, 467, 124, 54);
   frame.getContentPane().add(btnNewButton_2);

   JButton btnNewButton_2_1 = new JButton("import");
   btnNewButton_2_1.setBackground(SystemColor.control);
   btnNewButton_2_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
   btnNewButton_2_1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         String filePath = "hello/data.csv";
         File file = new File(filePath);

         try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            line = br.readLine();
            while ((line = br.readLine()) != null) {
               String[] row = line.split(",");
               model.addRow(row);
            }
            
            String filePathq = "hello/data.csv";
            File file1 = new File(filePathq);

            try (BufferedReader br1 = new BufferedReader(new FileReader(file1))) {
               String line1;
               DefaultTableModel model1 = (DefaultTableModel) table.getModel();
               model1.setRowCount(0);
               line1 = br1.readLine();

               String lastLine = null;

               while ((line1 = br1.readLine()) != null) {
                  String[] row = line1.split(",");
                  model1.addRow(row);
                  lastLine = line1;
                  }
                  int sum = 0;
                  for (int i = 0; i < table.getRowCount(); i++) {
                      int breakfest = parseInteger(table.getValueAt(i, 0));
                      int lunch = parseInteger(table.getValueAt(i, 1));
                      int dinner = parseInteger(table.getValueAt(i, 2));
                      int dailysupply = parseInteger(table.getValueAt(i, 3));
      
                      int total = breakfest + lunch + dinner + dailysupply;
                      table.setValueAt(total, i, 4);
                      sum += total; 
                  }
      
                  textField_1.setText(Integer.toString(sum));
                  float average = sum / (float) table.getRowCount();
                  textField_2.setText(Float.toString(average));
            
               if (lastLine != null) {
                  String[] lastRow = lastLine.split(",");
                  String lastValue = lastRow[lastRow.length - 1];
                  try {
                     int lastNumber = Integer.parseInt(lastValue);
                     int budget = 0;
                     String filePath_budget = "hello/budget.txt";
                     File file_budget = new File(filePath_budget);
                     try {
                        Scanner scanner = new Scanner(file_budget);
                        budget = Integer.parseInt(scanner.nextLine());
                        scanner.close();
                     } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                     }

                     if (budget < lastNumber) {
                        JOptionPane.showMessageDialog(btnNewButton, 
                           "YOU ARE OVER BUDGET.",
                           "Budget Exceeded", 
                           JOptionPane.WARNING_MESSAGE);
                     }
                  } catch (NumberFormatException ex) {
                     JOptionPane.showMessageDialog(null, 
                        "The last value in the CSV is not a valid number: " + lastValue,
                        "Invalid Number", 
                        JOptionPane.ERROR_MESSAGE);
                  }
               } else {
                  JOptionPane.showMessageDialog(null, 
                     "The file is empty!",
                     "Error", 
                     JOptionPane.ERROR_MESSAGE);
               }
            } catch (IOException ex) {
               ex.printStackTrace();
               JOptionPane.showMessageDialog(null, 
                  "Error reading the file: " + ex.getMessage(),
                  "File Error", 
                  JOptionPane.ERROR_MESSAGE);
            }

   

            JOptionPane.showMessageDialog(frame, "Data imported successfully!");

         } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
         } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
      }
   });
   btnNewButton_2_1.setBounds(161, 467, 124, 54);
   frame.getContentPane().add(btnNewButton_2_1);

   JButton btnNewButton_2_1_1 = new JButton("delete");
   btnNewButton_2_1_1.setBackground(SystemColor.control);
   btnNewButton_2_1_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
   btnNewButton_2_1_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         DefaultTableModel dm = (DefaultTableModel) table.getModel();
         int rowCount = dm.getRowCount();
         for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
         }
         textField_1.setText("0");
         textField_2.setText("0.0");
      }
   });
   btnNewButton_2_1_1.setBounds(308, 467, 124, 54);
   frame.getContentPane().add(btnNewButton_2_1_1);

   JScrollBar scrollBar = new JScrollBar();
   scrollBar.setBackground(SystemColor.controlDkShadow);
   scrollBar.setForeground(Color.PINK);
   scrollBar.setBounds(430, 58, 17, 385);
   frame.getContentPane().add(scrollBar);
}

public int parseInteger(Object value) { 
   if (value == null || value.toString().trim().isEmpty()) {
      return 0;
   }
   return Integer.parseInt(value.toString());
}

public static class MyCellEditor extends DefaultCellEditor {
   public MyCellEditor(JTextField textField) {
      super(textField);
   }

   public MyCellEditor(JCheckBox checkBox) {
      super(checkBox);
   }

   public MyCellEditor(JComboBox comboBox) {
      super(comboBox);
   }

   @Override
   public boolean stopCellEditing() {
      Component comp = getComponent();
      Object obj = getCellEditorValue();
      if (obj == null || !obj.toString().matches("[0-9]*")) {
         comp.setForeground(Color.RED);
         return false;
      }
      comp.setForeground(Color.BLACK);
      return super.stopCellEditing();
   }
}
}

