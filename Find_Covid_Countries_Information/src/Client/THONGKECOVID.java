/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Client;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author hoalo
 */
public class THONGKECOVID {
    public static JFreeChart createChart(int c, int r, int d){
        JFreeChart barChart = ChartFactory.createBarChart(
                "BIỀU ĐỒ COVID", 
                "THÔNG SỐ", "SỐ NGƯỜI", createDataset(c, r, d), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }
    
    private static CategoryDataset createDataset(int c, int r, int d){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(c, "SỐ NGƯỜI", "Số ca nhiễm");
        dataset.addValue(r, "SỐ NGƯỜI", "Số ca hồi phục");
        dataset.addValue(d, "SỐ NGƯỜI", "Số ca tử vong");
        return dataset;
    }
}
