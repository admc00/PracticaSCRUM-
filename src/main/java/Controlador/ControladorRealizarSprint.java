/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.AñadirTarea;
import Vista.CargarPilaProductos;
import Vista.DefinirSprint;
import Vista.GenerarGrafica;
import Vista.RealizarSprint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author antonio diego
 */
public class ControladorRealizarSprint implements ActionListener {

    private CargarPilaProductos vistaPilaProductos;
    private AñadirTarea vistaañadirtarea;
    private DefinirSprint vistadefinirsprint;
    private RealizarSprint vistarealizarsprint;
    private GenerarGrafica vistaGenerarGrafica;
    private Scanner scan;
    private BufferedWriter writer;
    private String duracionsprint, valorsprint;
    private int Springs[];
    private int nSprings = 0;
    public DefaultTableModel modelotabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public DefaultTableModel modelotablaSprints = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorRealizarSprint(String duracionsprint, String valorsprint) throws IOException {
        vistaPilaProductos = new CargarPilaProductos();
        vistaañadirtarea = new AñadirTarea();
        vistadefinirsprint = new DefinirSprint();
        vistarealizarsprint = new RealizarSprint();

        vistaPilaProductos.dispose();
        vistaPilaProductos.setVisible(false);
        vistadefinirsprint.setVisible(false);
        vistaañadirtarea.setVisible(false);

        vistarealizarsprint.setSize(640, 400);
        vistarealizarsprint.setLocationRelativeTo(null);
        vistarealizarsprint.setVisible(true);

        this.duracionsprint = duracionsprint;
        this.valorsprint = valorsprint;
        Springs = new int[20];

        dibujartabla();
        dibujartablaSprints();
        definirSPrint();
        addListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Restar Tarea": {
                if ((Integer.parseInt(vistarealizarsprint.jTextFieldValorSprint.getText()) - Integer.parseInt(vistarealizarsprint.jTextFieldValorTarea.getText())) >= 0) {
                    int valorN = Integer.parseInt(vistarealizarsprint.jTextFieldValorSprint.getText()) - Integer.parseInt(vistarealizarsprint.jTextFieldValorTarea.getText());
                    vistarealizarsprint.jTextFieldValorSprint.setText("" + valorN);
                    DefaultTableModel modelo = (DefaultTableModel) vistarealizarsprint.jTableTareasSprint.getModel();
                    modelo.removeRow(vistarealizarsprint.jTableTareasSprint.getSelectedRow());
                    vistarealizarsprint.jTextFieldValorTarea.setText("");
                }
            }
            break;
            case "Finalizar Sprint": {
                try {
                    System.out.println("holaaaaa");
                    Springs[nSprings] = Integer.parseInt(vistarealizarsprint.jTextFieldValorSprint.getText());
                    modelotablaSprints.addRow(new Object[]{"" + (nSprings + 1), "" + Springs[nSprings]});
                    nSprings++;
                    definirSPrint();
                } catch (IOException ex) {
                    Logger.getLogger(ControladorRealizarSprint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "Generar Grafica": {
                vistaGenerarGrafica = new GenerarGrafica(Springs, Integer.parseInt(duracionsprint), nSprings);
                vistarealizarsprint.dispose();
                vistaGenerarGrafica.setSize(800, 600);
                vistaGenerarGrafica.setLocationRelativeTo(null);
                vistaGenerarGrafica.setDefaultCloseOperation(EXIT_ON_CLOSE);
                vistaGenerarGrafica.setVisible(true);
            }
            break;

            default:
                throw new AssertionError();
        }
    }

    private void dibujartabla() throws IOException {
        vistarealizarsprint.jTableTareasSprint.setModel(modelotabla);

        String[] columna = {"Tarea", "Prioridad", "Valor", "Esfuerzo"};
        modelotabla.setColumnIdentifiers(columna);

        vistarealizarsprint.jTableTareasSprint.getColumnModel().getColumn(0).setPreferredWidth(100);
        vistarealizarsprint.jTableTareasSprint.getColumnModel().getColumn(1).setPreferredWidth(50);
        vistarealizarsprint.jTableTareasSprint.getColumnModel().getColumn(2).setPreferredWidth(50);
        vistarealizarsprint.jTableTareasSprint.getColumnModel().getColumn(3).setPreferredWidth(50);

        rellenartabla();
    }

    private void rellenartabla() throws IOException {
        int f = vistarealizarsprint.jTableTareasSprint.getRowCount();

        for (int i = f - 1; i >= 0; i--) {
            modelotabla.removeRow(i);
        }

        leerfichero();
        Object[] fila = new Object[5];

        while (scan.hasNext()) {
            if (scan.hasNext("T.")) {
                scan.next();
                fila[0] = scan.next();
                fila[1] = scan.next();
                fila[2] = scan.next();
                fila[3] = scan.next();
                modelotabla.addRow(fila);
            } else {
                scan.nextLine();
            }
        }
        scan.close();
    }

    public void leerfichero() throws IOException {
        File fichero = new File("C:\\Users\\antonio diego\\OneDrive - UNIVERSIDAD DE HUELVA\\Documentos\\UNI\\CMEPPS\\PracticaSCRUM-master\\datos.txt");
        fichero.createNewFile();
        scan = new Scanner(fichero);
    }

    public void definirSPrint() throws IOException {

        vistarealizarsprint.jTextFieldValorSprint.setText(valorsprint);
    }

    public void addListener() {
        vistarealizarsprint.jButtonRestarTarea.addActionListener(this);
        vistarealizarsprint.jButtonFinalizarSprint.addActionListener(this);
        vistarealizarsprint.jButtonGenerarGrafica.addActionListener(this);
        vistarealizarsprint.jTableTareasSprint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                vistarealizarSprintjTableMouseClicked(evt);
            }
        });
    }

    private void vistarealizarSprintjTableMouseClicked(MouseEvent evt) {
        int fila = vistarealizarsprint.jTableTareasSprint.getSelectedRow();
        vistarealizarsprint.jTextFieldValorTarea.setText(vistarealizarsprint.jTableTareasSprint.getValueAt(fila, 2).toString());
    }

    private void dibujartablaSprints() throws IOException {
        vistarealizarsprint.jTableSprint.setModel(modelotablaSprints);

        String[] columna = {"Sprint", "Valor"};
        modelotablaSprints.setColumnIdentifiers(columna);

        vistarealizarsprint.jTableSprint.getColumnModel().getColumn(0).setPreferredWidth(100);
        vistarealizarsprint.jTableSprint.getColumnModel().getColumn(1).setPreferredWidth(50);

    }
}
