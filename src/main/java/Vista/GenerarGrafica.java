/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Frame.java to edit this template
 */
package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author antonio diego
 */
public class GenerarGrafica extends JFrame {

    JPanel panel;
    private int Sprints[] ;
    private int duracionSprint,nSprints;
    int pa[]= new int[3];
    int n = 2;
    

    private void init() {
        panel = new JPanel();
        getContentPane().add(panel);
        pa[0]=1;
        pa[1]=0;
        pa[2]=7;
        // Fuente de Datos
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        
        for (int i = 0; i < nSprints; i++) {
            line_chart_dataset.addValue(Sprints[i],"Velocidad/sprint",""+duracionSprint);
            duracionSprint+=2;
        }
        
            
            //duracionSprint +=duracionSprintInicial;
        
        // Creando el Grafico
        JFreeChart chart = ChartFactory.createLineChart("Grafico Burn-up",
                "Semanas", "Velocidad/sprint", line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
    }

    /**
     * Creates new form GenerarGrafica
     */
    public GenerarGrafica(int s[],int ss,int ns) {
        this.Sprints = s;
        this.duracionSprint = ss;
        this.nSprints = ns;
        initComponents();
        this.init();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
