package acc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GraphAnalysis {

    private JFrame frame2;

    /**
     * Launch the application.
     */
    public void Screen3() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    GraphAnalysis window = new GraphAnalysis();
                    window.frame2.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GraphAnalysis() {
        initialize();
        piechart();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame2 = new JFrame();
        frame2.getContentPane().setBackground(new Color(188, 143, 143));
        frame2.setBounds(100, 100, 649, 564);
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame2.getContentPane().setLayout(null);
    }

    public void piechart() {
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 67, 633, 419);
        frame2.getContentPane().add(panel_1);

        final DefaultPieDataset pieDataset = new DefaultPieDataset();
        int breakfastTotal = 0;
        int lunchTotal = 0;
        int dinnerTotal = 0;
        int dailySupplyTotal = 0;
        int settlementTotal = 0;

        String filePath = "hello/data.csv";
        System.out.println("Attempting to read file: " + filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File does not exist at: " + file.getAbsolutePath());
            return;
        }

        Scanner scanner;
        try {
            scanner = new Scanner(file);
            
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                int breakfast = parseInteger(values[0]);
                int lunch = parseInteger(values[1]);
                int dinner = parseInteger(values[2]);
                int dailySupply = parseInteger(values[3]);
                int settlement = parseInteger(values[4]);

                breakfastTotal += breakfast;
                lunchTotal += lunch;
                dinnerTotal += dinner;
                dailySupplyTotal += dailySupply;
                settlementTotal += settlement;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("文件内容格式有误。");
            e.printStackTrace();
        }

        pieDataset.setValue("breakfast", breakfastTotal);
        pieDataset.setValue("lunch", lunchTotal);
        pieDataset.setValue("dinner", dinnerTotal);
        pieDataset.setValue("daily supply", dailySupplyTotal);

        JFreeChart chart = ChartFactory.createPieChart3D("Expenditure", pieDataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", 
                new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);

        panel_1.setLayout(null);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(0, 0, 633, 419);
        panel_1.add(chartPanel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(0, 0, 61, 38);
        frame2.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame2.setVisible(false);
            }
        });
        backButton.setFont(new Font("Dialog", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 250, 250));

        frame2.setVisible(true);
    }

    private int parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + value);
            return 0;
        }
    }
}

