package Controlador;

import Vista.AñadirTarea;
import Vista.CargarPilaProductos;
import Vista.DefinirSprint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ControladorAñadirTarea implements ActionListener {

    private CargarPilaProductos vistaPilaProductos;
    private AñadirTarea vistaañadirtarea;
    private DefinirSprint vistadefinirsprint;
    private Scanner scan;
    private BufferedWriter writer;

    public DefaultTableModel modelotabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorAñadirTarea() throws IOException {
        vistaPilaProductos = new CargarPilaProductos();
        vistaañadirtarea = new AñadirTarea();
        vistadefinirsprint = new DefinirSprint();

        vistaPilaProductos.dispose();
        vistaPilaProductos.setVisible(false);
        vistadefinirsprint.setVisible(false);

        vistaañadirtarea.setSize(500,400);
        vistaañadirtarea.setLocationRelativeTo(null);
        vistaañadirtarea.setVisible(true);

        dibujartabla();
        addListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Confirmar": {

                try {
                    String tarea ="T. " + vistaañadirtarea.TareaF.getText() + " " + vistaañadirtarea.PrioridadF.getText() + " " + vistaañadirtarea.ValorF.getText() + " "
                            + vistaañadirtarea.EsfuerzoF.getText();
                    escribirfichero(tarea);
                    dibujartabla();
                } catch (IOException ex) {
                    Logger.getLogger(ControladorAñadirTarea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            default:
                throw new AssertionError();
        }
    }

    //Dibuja la tabla
    private void dibujartabla() throws IOException {
        vistaañadirtarea.TareasT.setModel(modelotabla);

        String[] columna = {"Tarea", "Prioridad", "Valor", "Esfuerzo"};
        modelotabla.setColumnIdentifiers(columna);

        vistaañadirtarea.TareasT.getColumnModel().getColumn(0).setPreferredWidth(100);
        vistaañadirtarea.TareasT.getColumnModel().getColumn(1).setPreferredWidth(50);
        vistaañadirtarea.TareasT.getColumnModel().getColumn(2).setPreferredWidth(50);
        vistaañadirtarea.TareasT.getColumnModel().getColumn(3).setPreferredWidth(50);

        rellenartabla();
    }

    //Vacía previamente el contenido de la tabla para rellenarlo despues
    private void rellenartabla() throws IOException {
        int f = vistaañadirtarea.TareasT.getRowCount();

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

    //Abre el fichero en modo lectura OJO: AQUI ABRIMOS EL FICHERO PERO HAY QUE INDICAR CUANDO QUEREMOS CERRARLO
    public void leerfichero() throws IOException {
        File fichero = new File("C:\\Users\\antonio diego\\OneDrive - UNIVERSIDAD DE HUELVA\\Documentos\\UNI\\CMEPPS\\PracticaSCRUM-master\\datos.txt");
        fichero.createNewFile();
        scan = new Scanner(fichero);
    }

    //Abre el fichero en modo escritura, escribe la cadena al final del fichero y cierra el fichero
    public void escribirfichero(String cadena) throws IOException {
        FileWriter fich = new FileWriter("C:\\Users\\antonio diego\\OneDrive - UNIVERSIDAD DE HUELVA\\Documentos\\UNI\\CMEPPS\\PracticaSCRUM-master\\datos.txt", true);
        writer = new BufferedWriter(fich);
        writer.write(cadena);
        writer.newLine();
        writer.close();
    }
    
    private void addListener() {
        vistaañadirtarea.ConfirmarB.addActionListener(this);
    }
}