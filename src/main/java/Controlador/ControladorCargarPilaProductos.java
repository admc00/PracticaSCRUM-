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

public class ControladorCargarPilaProductos implements ActionListener {

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

    public ControladorCargarPilaProductos() throws IOException {
        vistaPilaProductos = new CargarPilaProductos();
        vistaañadirtarea = new AñadirTarea();
        vistadefinirsprint = new DefinirSprint();

        vistaPilaProductos.setSize(500, 400);
        vistaPilaProductos.setLocationRelativeTo(null);
        vistaPilaProductos.setVisible(true);

        vistaañadirtarea.setVisible(false);
        vistadefinirsprint.setVisible(false);

        dibujartabla();
        addListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Añadir Historia": {
                try {
                    //Obtenemos el valor del text field y lo introducimos en el fichero con el prefijo H. para luego refrescar la tabla
                    String historia = vistaPilaProductos.HistoriasF.getText();
                    escribirfichero("H." + " " + historia);
                    dibujartabla();
                } catch (IOException ex) {
                    Logger.getLogger(ControladorCargarPilaProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "Añadir Tarea": {
                try {
                    //Desplegamos la vista de añadir tarea
                    ControladorAñadirTarea cañadirtarea = new ControladorAñadirTarea();
                } catch (IOException ex) {
                    Logger.getLogger(ControladorCargarPilaProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "Definir sprint": {
                //Desplegamos la ventana de definir sprint y ocultamos la vista cargar pila productos
                vistaPilaProductos.dispose();
                ControladorDefinirSprint cdefinisprint = new ControladorDefinirSprint();
            }
            break;

            default:
                throw new AssertionError();
        }
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

    //Dibuja la tabla 
    public void dibujartabla() throws IOException {
        vistaPilaProductos.HistoriasT.setModel(modelotabla);

        String[] columna = {"Nombre"};
        modelotabla.setColumnIdentifiers(columna);

        rellenartabla();
    }

    //Vacía la tabla previamente para volver a llenarla
    public void rellenartabla() throws IOException {

        int f = vistaPilaProductos.HistoriasT.getRowCount();

        for (int i = f - 1; i >= 0; i--) {
            modelotabla.removeRow(i);
        }

        leerfichero();
        Object[] fila = new Object[1];

        while (scan.hasNextLine()) {
            //fila[0] = scan.nextLine();
            if (scan.hasNext("H.")) {
                scan.next();
                fila[0] = scan.next();
                modelotabla.addRow(fila);
            } else {
                scan.nextLine();
            }
        }
        scan.close();
    }

    public void addListener() {
        vistaPilaProductos.AñadirHistoriaB.addActionListener(this);
        vistaPilaProductos.AñadirTareaB.addActionListener(this);
        vistaPilaProductos.DefinirSprintB.addActionListener(this);
    }
}
