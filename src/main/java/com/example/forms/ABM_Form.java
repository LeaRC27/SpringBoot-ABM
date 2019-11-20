package com.example.forms;

import com.example.model.Persona;
import com.example.service.PersonaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static java.lang.System.exit;

@Component
public class ABM_Form extends JFrame{

    private static final Logger logger = LogManager.getLogger(ABM_Form.class);

    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton cancelarButton;
    private JScrollPane jScrollPane;
    private JPanel jPanelTable;
    private JButton modificarButton;
    private JTable table1 = new JTable();
    private Persona selectedPersona;

    @Autowired
    PersonaService personaService;

    public ABM_Form(){
        super("ABM_Form");

        setContentPane(panel1);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        agregarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // super.mouseClicked(e);
                try {
                    Persona p = new Persona(textField1.getText(), textField2.getText(), textField3.getText(), Long.decode(textField4.getText()));
                    personaService.guardarPersona(p);
                    populatePersonaTable(personaService.buscarPersonas(), table1, jScrollPane);
                }catch (DataIntegrityViolationException ex){
                    JOptionPane.showMessageDialog(panel1,"El DNI ingresado ya exite en nuestros registros","DNI duplicado",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (table1.getSelectedColumn() != -1 && table1.getSelectedRow() != -1) {

                    agregarButton.setVisible(false);
                    eliminarButton.setVisible(true);

                    selectedPersona = personaService.buscarPersona((int) table1.getValueAt(table1.getSelectedRow(), 0));
                    textField1.setText(selectedPersona.getNombre());
                    textField2.setText(selectedPersona.getApellido());
                    textField3.setText(selectedPersona.getTelefono());
                    textField4.setText(Long.toString(selectedPersona.getDni()));
                    modificarButton.setVisible(true);
                }
            }
        });
        eliminarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) throws IllegalArgumentException {
                super.mouseClicked(e);
                try {
                    if (selectedPersona == null) {
                        JOptionPane.showMessageDialog(panel1, "Debe seleccionar una persona de la lista", "Error al eliminar", JOptionPane.OK_OPTION);
                    } else if (JOptionPane.showConfirmDialog(panel1, "Desea eliminar a " + selectedPersona.getId() + " "
                            + selectedPersona.getNombre() + " " + selectedPersona.getApellido() + " ?", "Confirmar Eliminacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                        personaService.eliminarPersona(selectedPersona);

                        setFormWhite();
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(), "Error al eliminar persona", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        cancelarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int resp =JOptionPane.showConfirmDialog(panel1,"Desea cerrar la aplicacion?","Cerrar?",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(resp == 0){
                    exit(0);
                }else if(resp == 2){
                    setFormWhite();
                }

            }
        });
        modificarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(JOptionPane.showConfirmDialog(panel1,"Confirma que desea modificar?","Modificando Usuario",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == 0){
                    Persona p = selectedPersona;
                    p.setNombre(textField1.getText());
                    p.setApellido(textField2.getText());
                    p.setTelefono(textField3.getText());
                    p.setDni(Long.decode(textField4.getText()));

                    personaService.modificarPersona(p);

                    setFormWhite();
                }
            }
        });
    }
        private void formWindowOpened(java.awt.event.WindowEvent evt) {
            eliminarButton.setVisible(false);
            modificarButton.setVisible(false);
            List<Persona> personaList = personaService.buscarPersonas();
            if(personaList.size()>0){
                populatePersonaTable(personaList,table1,jScrollPane);
            }

        }

        private void populatePersonaTable(List<Persona> personas, JTable table, JScrollPane scrollPane) {
            String[] columnNames = {"Id", "Nombre", "Apellido", "Telefono", "DNI"};
            DefaultTableModel dtm = new DefaultTableModel(new Object[][]{}, columnNames);

            for (int i = 0; i < personas.size(); i++) {
                int id = personas.get(i).getId();
                String nombre = personas.get(i).getNombre();
                String apellido = personas.get(i).getApellido();
                String telefono = personas.get(i).getTelefono();
                String dni = String.valueOf(personas.get(i).getDni());

                Object[] data = {id, nombre, apellido, telefono, dni};
                dtm.addRow(data);
            }
            table.setModel(dtm);
            scrollPane.add(table);
            scrollPane.setViewportView(table);
        }

        private void setFormWhite(){
            selectedPersona = null;

            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");

            modificarButton.setVisible(false);
            agregarButton.setVisible(true);
            eliminarButton.setVisible(false);

            populatePersonaTable(personaService.buscarPersonas(), table1, jScrollPane);
        }

}
