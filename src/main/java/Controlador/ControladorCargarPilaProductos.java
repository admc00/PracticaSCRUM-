package Controlador;

import Vista.AñadirTarea;
import Vista.CargarPilaProductos;
import Vista.DefinirSprint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCargarPilaProductos implements ActionListener {

    private CargarPilaProductos vistaPilaProductos;
    private AñadirTarea vistaañadirtarea;
    private DefinirSprint vistadefinirsprint;

    public ControladorCargarPilaProductos() {
        vistaPilaProductos = new CargarPilaProductos();
        vistaañadirtarea = new AñadirTarea();
        vistadefinirsprint = new DefinirSprint();

        vistadefinirsprint.setSize(500, 400);
        vistadefinirsprint.setLocationRelativeTo(null);
        vistadefinirsprint.setVisible(true);

        vistaañadirtarea.setVisible(false);
        vistaPilaProductos.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "AceptarB":

                break;
            default:
                throw new AssertionError();
        }
    }

    public void addListener() {
        vistadefinirsprint.AceptarB.addActionListener(this);
    }
}
