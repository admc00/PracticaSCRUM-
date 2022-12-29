package Controlador;

import Vista.AñadirTarea;
import Vista.CargarPilaProductos;
import Vista.DefinirSprint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class ControladorDefinirSprint implements ActionListener {

    private CargarPilaProductos vistaPilaProductos;
    private AñadirTarea vistaañadirtarea;
    private DefinirSprint vistadefinirsprint;
    private BufferedWriter writer;

    public ControladorDefinirSprint() {
        vistaPilaProductos = new CargarPilaProductos();
        vistaañadirtarea = new AñadirTarea();
        vistadefinirsprint = new DefinirSprint();

        vistadefinirsprint.setSize(500, 400);
        vistadefinirsprint.setLocationRelativeTo(null);
        vistadefinirsprint.setVisible(true);

        vistaañadirtarea.setVisible(false);
        vistaPilaProductos.setVisible(false);
        
        addListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Aceptar": {

                try {
                    System.out.println("hola");
                    String sprint = "\n\nvs " + vistadefinirsprint.VelocidadSprintF.getText() + "\nd " + vistadefinirsprint.DuracionSprintF.getText();
                    escribirfichero(sprint);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorDefinirSprint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            default:
                throw new AssertionError();
        }
    }

    public void escribirfichero(String cadena) throws IOException {
        FileWriter fich = new FileWriter("D:\\Documentos\\UHU\\CMEPPS\\PracticaSCRUM\\datos.txt", true);
        writer = new BufferedWriter(fich);
        writer.write(cadena);
        writer.newLine();
        writer.close();
    }

    public void addListener() {
        vistadefinirsprint.AceptarB.addActionListener(this);
    }

}
