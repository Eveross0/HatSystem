/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hatsystem;

import data.Employee;
import data.Fabric;
import data.SpecialHat;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import data.SqlQuery;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import data.StandardHat;
import data.Validation;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.function.ToDoubleFunction;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

/**
 * @author luna
 */
public class AddHatType extends javax.swing.JFrame {

    private int customSelectedHatID;
    private int orderedHatID;
    private static int hatIdentifier = 1;

    //konstr
    public AddHatType() {
        initComponents();
        fillFabricComboBox(cmbSpecialFabrics);
        fillFabricComboBox(cmb_customFabric);
        fillEmployeeComboBox();
        lblSpecialError.setVisible(false);
        lblStandardErrorMessage.setVisible(false);
        lblCustomErrorMessage.setVisible(false);
        lblCustomErrorPrice.setVisible(false);
        txtfSpecialDescription.setLineWrap(true);
        txtfSpecialDescription.setWrapStyleWord(true);

        fillList();
        // Enables enter press.
        this.getRootPane().setDefaultButton(btn_addHat);

    }

    //public String getHatIdentifier;
    /**
     * Fills the fabric combo box with fabric options.
     */
    private void fillFabricComboBox(JComboBox cmbBox) {

        HashSet<String> fabrics = Fabric.getAllFabricNames();

        for (String s : fabrics) {
            cmbBox.addItem(s);
        }
    }

    private void fillEmployeeComboBox() {
        ArrayList<String> employees = Employee.getEmployee();

        for (String e : employees) {
            cmbSpecialResponsible.addItem(e);
            cmb_customEmployee.addItem(e);
        }
    }

    private void fillColorsComboBox(String fabricName) {

        cmbSpecialColors.removeAllItems();

        ArrayList<String> colors = Fabric.getFabricColors(fabricName);

        for (String s : colors) {
            cmbSpecialColors.addItem(s);
        }
    }

    /**
     * WARNING: Do NOT modify this code.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_standardHat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListStandardHat = new javax.swing.JList<>();
        btn_addHat = new javax.swing.JButton();
        lbl_size = new javax.swing.JLabel();
        cmbStandardChosenSize = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        lblStandardErrorMessage = new javax.swing.JLabel();
        panel_customHat = new javax.swing.JPanel();
        scrollPane_CustomHat = new javax.swing.JScrollPane();
        listCustHat = new javax.swing.JList<>();
        lbl_customFabric = new javax.swing.JLabel();
        lbl_customColor = new javax.swing.JLabel();
        lbl_customSize = new javax.swing.JLabel();
        lbl_customDescription = new javax.swing.JLabel();
        lbl_customPrice = new javax.swing.JLabel();
        textField_customPrice = new javax.swing.JTextField();
        cmb_customFabric = new javax.swing.JComboBox<>();
        cmb_customColor = new javax.swing.JComboBox<>();
        cmb_customSize = new javax.swing.JComboBox<>();
        lbl_customTitle = new javax.swing.JLabel();
        btn_customAddToOrder = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cmb_customEmployee = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaCustomDescription = new javax.swing.JTextArea();
        lblCheckPriceCustomHat = new javax.swing.JLabel();
        lblCustomErrorMessage = new javax.swing.JLabel();
        lblCustomErrorPrice = new javax.swing.JLabel();
        panel_specialHat = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbSpecialFabrics = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cmbSpecialColors = new javax.swing.JComboBox<>();
        txtfSpecialName = new javax.swing.JTextField();
        btnSpecialSave = new javax.swing.JButton();
        txtfSpecialPrice = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblSpecialError = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        lblImageTxt = new javax.swing.JLabel();
        btbAddImage = new javax.swing.JButton();
        lblSpecialSize = new javax.swing.JLabel();
        cmbSpecialSize = new javax.swing.JComboBox<>();
        lblSpecialResponsible = new javax.swing.JLabel();
        cmbSpecialResponsible = new javax.swing.JComboBox<>();
        lblCheckNameSpecial = new javax.swing.JLabel();
        lblCheckPriceSpecial = new javax.swing.JLabel();
        checkBoxSaveAsStandard = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtfSpecialDescription = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TopIT");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/TopITcute32px.png")).getImage());
        setResizable(false);

        jListStandardHat.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListStandardHatValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListStandardHat);

        btn_addHat.setText("Lägg till hatt i order");
        btn_addHat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addHatActionPerformed(evt);
            }
        });

        lbl_size.setText("Storlek");

        cmbStandardChosenSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "XXS", "XS", "S", "M", "L", "XL", "XXL" }));
        cmbStandardChosenSize.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Lägg till standardhatt");

        lblStandardErrorMessage.setForeground(new java.awt.Color(153, 0, 0));
        lblStandardErrorMessage.setText("Vänligen välj en hatt i listan först");

        javax.swing.GroupLayout panel_standardHatLayout = new javax.swing.GroupLayout(panel_standardHat);
        panel_standardHat.setLayout(panel_standardHatLayout);
        panel_standardHatLayout.setHorizontalGroup(
            panel_standardHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_standardHatLayout.createSequentialGroup()
                .addGroup(panel_standardHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_standardHatLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(panel_standardHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 919, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_standardHatLayout.createSequentialGroup()
                        .addGap(385, 385, 385)
                        .addComponent(lbl_size)
                        .addGap(18, 18, 18)
                        .addComponent(cmbStandardChosenSize, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_standardHatLayout.createSequentialGroup()
                        .addGap(398, 398, 398)
                        .addComponent(lblStandardErrorMessage))
                    .addGroup(panel_standardHatLayout.createSequentialGroup()
                        .addGap(421, 421, 421)
                        .addComponent(btn_addHat)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        panel_standardHatLayout.setVerticalGroup(
            panel_standardHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_standardHatLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(panel_standardHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_size)
                    .addComponent(cmbStandardChosenSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblStandardErrorMessage)
                .addGap(18, 18, 18)
                .addComponent(btn_addHat)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Standardhatt", panel_standardHat);

        listCustHat.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listCustHatValueChanged(evt);
            }
        });
        scrollPane_CustomHat.setViewportView(listCustHat);

        lbl_customFabric.setText("Tyg");

        lbl_customColor.setText("Färg");

        lbl_customSize.setText("Storlek");

        lbl_customDescription.setText("Beskrivning");

        lbl_customPrice.setText("Pris");

        cmb_customFabric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_customFabricActionPerformed(evt);
            }
        });

        cmb_customSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "XXS", "XS", "S", "M", "L", "XL", "XXL" }));

        lbl_customTitle.setFont(new java.awt.Font("Apple Braille", 1, 24)); // NOI18N
        lbl_customTitle.setText("Lägg till anpassad standardhatt");

        btn_customAddToOrder.setText("Lägg till hatt i order");
        btn_customAddToOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_customAddToOrderActionPerformed(evt);
            }
        });

        jLabel13.setText("Ansvarig");

        cmb_customEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_customEmployeeActionPerformed(evt);
            }
        });

        txtAreaCustomDescription.setColumns(20);
        txtAreaCustomDescription.setRows(5);
        jScrollPane2.setViewportView(txtAreaCustomDescription);

        lblCheckPriceCustomHat.setForeground(new java.awt.Color(153, 0, 0));

        lblCustomErrorMessage.setForeground(new java.awt.Color(153, 0, 0));
        lblCustomErrorMessage.setText("Vänligen välj en hatt i listan först");

        lblCustomErrorPrice.setForeground(new java.awt.Color(153, 0, 0));
        lblCustomErrorPrice.setText("Endast siffror och punkt");

        javax.swing.GroupLayout panel_customHatLayout = new javax.swing.GroupLayout(panel_customHat);
        panel_customHat.setLayout(panel_customHatLayout);
        panel_customHatLayout.setHorizontalGroup(
            panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customHatLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_customHatLayout.createSequentialGroup()
                        .addComponent(lbl_customTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_customHatLayout.createSequentialGroup()
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollPane_CustomHat, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                            .addGroup(panel_customHatLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_customAddToOrder)
                                    .addComponent(lblCustomErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_customHatLayout.createSequentialGroup()
                                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_customFabric)
                                    .addComponent(lbl_customColor)
                                    .addComponent(lbl_customSize))
                                .addGap(49, 49, 49)
                                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmb_customFabric, 0, 203, Short.MAX_VALUE)
                                    .addComponent(cmb_customColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmb_customSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panel_customHatLayout.createSequentialGroup()
                                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_customDescription)
                                    .addComponent(lbl_customPrice)
                                    .addComponent(jLabel13))
                                .addGap(24, 24, 24)
                                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textField_customPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                    .addComponent(cmb_customEmployee, 0, 209, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(panel_customHatLayout.createSequentialGroup()
                                        .addComponent(lblCustomErrorPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCheckPriceCustomHat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(68, 68, 68))))
        );
        panel_customHatLayout.setVerticalGroup(
            panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customHatLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lbl_customTitle)
                .addGap(33, 33, 33)
                .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane_CustomHat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_customHatLayout.createSequentialGroup()
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_customFabric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_customFabric))
                        .addGap(27, 27, 27)
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_customColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_customColor))
                        .addGap(26, 26, 26)
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_customSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_customSize))
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_customHatLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbl_customDescription)
                                .addGap(74, 74, 74)
                                .addComponent(lbl_customPrice))
                            .addGroup(panel_customHatLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textField_customPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCheckPriceCustomHat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCustomErrorPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_customHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_customEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addGap(54, 54, 54)
                .addComponent(lblCustomErrorMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_customAddToOrder)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Anpassad hatt", panel_customHat);

        jLabel4.setText("Beskrivning");

        jLabel8.setText("Tyg");

        cmbSpecialFabrics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSpecialFabricsActionPerformed(evt);
            }
        });

        jLabel9.setText("Färg");

        btnSpecialSave.setText("Lägg till hatt i order");
        btnSpecialSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpecialSaveActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("Lägg till specialsydd hatt");

        lblSpecialError.setForeground(new java.awt.Color(153, 0, 0));
        lblSpecialError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSpecialError.setText("Vänligen fyll i alla fält");

        jLabel11.setText("Namn");

        jLabel12.setText("Pris");

        btbAddImage.setText("Lägg Till Bild");
        btbAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbAddImageActionPerformed(evt);
            }
        });

        lblSpecialSize.setText("Storlek");

        cmbSpecialSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "XXS", "XS", "S", "M", "L", "XL", "XXL" }));
        cmbSpecialSize.setToolTipText("");

        lblSpecialResponsible.setText("Ansvarig");

        lblCheckNameSpecial.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckPriceSpecial.setForeground(new java.awt.Color(153, 0, 0));

        checkBoxSaveAsStandard.setText("Spara som standardmodell");

        txtfSpecialDescription.setColumns(20);
        txtfSpecialDescription.setRows(5);
        jScrollPane3.setViewportView(txtfSpecialDescription);

        javax.swing.GroupLayout panel_specialHatLayout = new javax.swing.GroupLayout(panel_specialHat);
        panel_specialHat.setLayout(panel_specialHatLayout);
        panel_specialHatLayout.setHorizontalGroup(
            panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_specialHatLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_specialHatLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_specialHatLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblSpecialError, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_specialHatLayout.createSequentialGroup()
                                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8)
                                            .addComponent(lblSpecialSize)
                                            .addComponent(lblSpecialResponsible))
                                        .addGap(38, 38, 38)
                                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(checkBoxSaveAsStandard, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                            .addComponent(cmbSpecialResponsible, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbSpecialSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbSpecialColors, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbSpecialFabrics, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(panel_specialHatLayout.createSequentialGroup()
                                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addGroup(panel_specialHatLayout.createSequentialGroup()
                                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panel_specialHatLayout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)))
                                            .addComponent(jLabel4))
                                        .addGap(38, 38, 38)
                                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtfSpecialPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                            .addComponent(txtfSpecialName))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblCheckNameSpecial, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(lblCheckPriceSpecial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panel_specialHatLayout.createSequentialGroup()
                                .addComponent(btbAddImage)
                                .addGap(18, 18, 18)
                                .addComponent(lblImageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 225, Short.MAX_VALUE))
                    .addGroup(panel_specialHatLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(btnSpecialSave)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panel_specialHatLayout.setVerticalGroup(
            panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_specialHatLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_specialHatLayout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panel_specialHatLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel10)
                        .addGap(28, 28, 28)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_specialHatLayout.createSequentialGroup()
                                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCheckNameSpecial, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtfSpecialName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11)))
                                .addGap(18, 18, 18)
                                .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtfSpecialPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)))
                            .addComponent(lblCheckPriceSpecial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_specialHatLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSpecialFabrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSpecialColors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSpecialSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSpecialSize))
                        .addGap(18, 18, 18)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSpecialResponsible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSpecialResponsible))
                        .addGap(18, 18, 18)
                        .addComponent(checkBoxSaveAsStandard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_specialHatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btbAddImage)
                            .addComponent(lblImageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(lblSpecialError)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSpecialSave)
                        .addGap(35, 35, 35))))
        );

        jTabbedPane1.addTab("Specialsydd hatt", panel_specialHat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void cmbSpecialFabricsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSpecialFabricsActionPerformed
        String chosenFabric = cmbSpecialFabrics.getSelectedItem().toString();
        fillColorsComboBox(chosenFabric);
    }//GEN-LAST:event_cmbSpecialFabricsActionPerformed

    private void btnSpecialSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpecialSaveActionPerformed
        String name = txtfSpecialName.getText();
        String price = txtfSpecialPrice.getText();
        String description = txtfSpecialDescription.getText();
        String fabric = cmbSpecialFabrics.getSelectedItem().toString();
        String colour = cmbSpecialColors.getSelectedItem().toString();
        String image = lblImageTxt.getText().replaceAll("\\\\", "\\\\\\\\");
        String size = cmbSpecialSize.getSelectedItem().toString();
        String worker = cmbSpecialResponsible.getSelectedItem().toString();

        String nameWithoutWitespace = name.replaceAll(" ", "");

        lblCheckNameSpecial.setVisible(false);
        lblCheckPriceSpecial.setVisible(false);

        if (name.isBlank() || price.isBlank()) {
            lblSpecialError.setText("Vänligen fyll i alla fält");
            lblSpecialError.setVisible(true);
        } else {
            if (Validation.isPrice(price, lblCheckPriceSpecial)) {

                int fabricComboID = Fabric.getFabricID(fabric, colour);
                int employeeComboID = Employee.getEmployeeIDAdmin(worker);
                boolean isUniqueHatAndFabricCombo = SpecialHat.isUniqueCombination(name, fabricComboID);
                if (isUniqueHatAndFabricCombo) {
                    SpecialHat.addHat(name.trim(), price, size, description, fabricComboID, employeeComboID);
                    if (image.isEmpty()) {
                        SpecialHat.addSpecialHatNoImage(name, price, fabricComboID);
                    } else {
                        SpecialHat.addSpecialHat(name, price, fabricComboID, image);
                    }

                    String newHatID = SqlQuery.getValue("SELECT MAX(Hat_ID) FROM hat;");
                    orderedHatID = Integer.parseInt(newHatID);
                    LoginMenu.addToListOtherHat(orderedHatID);
                    LoginMenu.listOrderItems();

                    if (checkBoxSaveAsStandard.isSelected()) {
                        if (StandardHat.isUniqueCombination(name, fabricComboID)) {
                            StandardHat.addStandardHat(name, price, description, fabricComboID);
                        } else {
                            JOptionPane.showMessageDialog(null, "Denna modell finns redan som standardmodell");
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Hatten är registrerad!");
                    this.dispose();

                } else {
                    lblSpecialError.setText("Denna kombinationen är redan registrerad som en hatt");
                    lblSpecialError.setVisible(true);
                }
            }
        }

    }//GEN-LAST:event_btnSpecialSaveActionPerformed

    private void btbAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbAddImageActionPerformed

        SpecialHat.chooseFile(lblImage, lblImageTxt);

    }//GEN-LAST:event_btbAddImageActionPerformed

    private void listCustHatValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listCustHatValueChanged

        String stringID = listCustHat.getSelectedValue().substring(0, 8);
        String newString = stringID.replaceAll("\\s", "");
        customSelectedHatID = Integer.parseInt(newString);
        setCustomValues(customSelectedHatID);
        lblCustomErrorMessage.setVisible(false);

    }//GEN-LAST:event_listCustHatValueChanged

    private void cmb_customFabricActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_customFabricActionPerformed
        String chosenFabric = cmb_customFabric.getSelectedItem().toString();
        fillColorComboBox(chosenFabric);
    }//GEN-LAST:event_cmb_customFabricActionPerformed

    private void btn_customAddToOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_customAddToOrderActionPerformed

        lblCustomErrorMessage.setVisible(false);
        lblCustomErrorPrice.setVisible(false);

        if (listCustHat.isSelectionEmpty()) {

            lblCustomErrorMessage.setVisible(true);

        } else {
            if (Validation.isPrice(textField_customPrice.getText(), lblCustomErrorPrice)) {

                saveCustomHatToDB();

                LoginMenu.addToListOtherHat(orderedHatID);
                LoginMenu.listOrderItems();
                JOptionPane.showMessageDialog(null, "Hatten är tillagd i ordern!");
                //TODO: Om alla fönster stängs ner, ändra här
                this.dispose();

            }
        }


    }//GEN-LAST:event_btn_customAddToOrderActionPerformed

    private void cmb_customEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_customEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_customEmployeeActionPerformed

    private void btn_addHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addHatActionPerformed

        String separator = ".";

        lblStandardErrorMessage.setVisible(false);

        try {
            String stringID = jListStandardHat.getSelectedValue().substring(0, 8);
            String newString = stringID.replaceAll("\\s", "");
            String completeHatIdentifier = newString + separator + hatIdentifier;
            int selectedHatID = Integer.parseInt(newString);

            String chosenSize = cmbStandardChosenSize.getSelectedItem().toString();

            LoginMenu.addToListStandardHat(completeHatIdentifier, chosenSize);
            LoginMenu.listOrderItems();
            JOptionPane.showMessageDialog(null, "Hatten är tillagd i ordern!");
            hatIdentifier++;
            this.dispose();
        } catch (Exception e) {
            lblStandardErrorMessage.setVisible(true);
        }

//        String newHatID = SqlQuery.getValue("SELECT MAX(Hat_ID) FROM hat;");
//        orderedHatID = Integer.parseInt(newHatID);
//        LoginMenu.addToListOtherHat(orderedHatID);
    }//GEN-LAST:event_btn_addHatActionPerformed

    private void jListStandardHatValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListStandardHatValueChanged
        lblStandardErrorMessage.setVisible(false);
    }//GEN-LAST:event_jListStandardHatValueChanged

    private void fillList() {
        //custom hat
        DefaultListModel<String> listModelCustom = new DefaultListModel<>();
        listCustHat.setModel(listModelCustom);
        //standard hat
        DefaultListModel<String> listModelStandard = new DefaultListModel<>();
        jListStandardHat.setModel(listModelStandard);

        ArrayList<HashMap<String, String>> allHats = StandardHat.getAllStandardHats();
        int index = 0;
        while (index < allHats.size()) {
            HashMap<String, String> currentHat = allHats.get(index);
            String fabricID = currentHat.get("Hat_Fabric");
            HashMap<String, String> currentFabric = Fabric.getFabricFromID(fabricID);
            //custom hat
            listModelCustom.addElement(String.format("%-10s %-20s %-20s %-20s" + currentHat.get("Price"), currentHat.get("Standard_Hat_ID"), currentHat.get("Name"), currentFabric.get("Name"), currentFabric.get("Color")));
            //standard hat
            listModelStandard.addElement(String.format("%-10s %-20s %-20s %-20s" + currentHat.get("Price"), currentHat.get("Standard_Hat_ID"), currentHat.get("Name"), currentFabric.get("Name"), currentFabric.get("Color")));

            index++;
        }

        //custom hat
        Font defaultListFontCustom = listCustHat.getFont();
        listCustHat.setFont(new Font("monospaced", defaultListFontCustom.getStyle(), defaultListFontCustom.getSize()));
        //standard hat
        Font defaultListFontStandard = jListStandardHat.getFont();
        jListStandardHat.setFont(new Font("monospaced", defaultListFontStandard.getStyle(), defaultListFontStandard.getSize()));
    }

    private void setCustomValues(int hatID) {
        // get fabric for chosen hat
        HashMap<String, String> chosenHat = StandardHat.getHat(hatID);
        String fabricID = chosenHat.get("Hat_Fabric");
        Double price = Double.parseDouble(chosenHat.get("Price"));

        Double priceExtraCost = price*1.2;
        
        String formatPrice = Validation.setTwoDecimals(priceExtraCost);

        String priceDot = formatPrice.replaceAll(",", ".");
        textField_customPrice.setText(priceDot);
       
        HashMap<String, String> defaultFabric = Fabric.getFabricFromID(fabricID);
        String fabricName = defaultFabric.get("Name");

        cmb_customFabric.setSelectedItem(fabricName);
        fillColorComboBox(fabricName);
        cmb_customColor.setSelectedItem(defaultFabric.get("Color"));

    }

    private void fillColorComboBox(String fabricName) {

        cmb_customColor.removeAllItems();

        ArrayList<String> colors = Fabric.getFabricColors(fabricName);

        for (String s : colors) {
            cmb_customColor.addItem(s);
        }
    }

    private void saveCustomHatToDB() {
        HashMap<String, String> chosenHat = StandardHat.getHat(customSelectedHatID);
        String chosenHatName = chosenHat.get("Name");
        String fabricName = cmb_customFabric.getSelectedItem().toString();
        String fabricColor = cmb_customColor.getSelectedItem().toString();

        int fabricID = Fabric.getFabricID(fabricName, fabricColor);
        String size = cmb_customSize.getSelectedItem().toString();
        String description = txtAreaCustomDescription.getText();
        String price = textField_customPrice.getText();
        String employee = cmb_customEmployee.getSelectedItem().toString();
        int employeeID = Employee.getEmployeeIDAdmin(employee);

        String query = "INSERT INTO hat (Name, Price, Size, Description, Hat_Fabric, Worker) VALUES ('" + chosenHatName + "', " + price + ", '" + size + "', '" + description + "', " + fabricID + ", " + employeeID + ");";
        SqlQuery.add(query);
        String newHatID = SqlQuery.getValue("SELECT MAX(Hat_ID) FROM hat;");
        SqlQuery.add("INSERT INTO custom_hat (Hat_ID) VALUES (" + newHatID + ");");
        orderedHatID = Integer.parseInt(newHatID);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbAddImage;
    private javax.swing.JButton btnSpecialSave;
    private javax.swing.JButton btn_addHat;
    private javax.swing.JButton btn_customAddToOrder;
    private javax.swing.JCheckBox checkBoxSaveAsStandard;
    private javax.swing.JComboBox<String> cmbSpecialColors;
    private javax.swing.JComboBox<String> cmbSpecialFabrics;
    private javax.swing.JComboBox<String> cmbSpecialResponsible;
    private javax.swing.JComboBox<String> cmbSpecialSize;
    private javax.swing.JComboBox<String> cmbStandardChosenSize;
    private javax.swing.JComboBox<String> cmb_customColor;
    private javax.swing.JComboBox<String> cmb_customEmployee;
    private javax.swing.JComboBox<String> cmb_customFabric;
    private javax.swing.JComboBox<String> cmb_customSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListStandardHat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCheckNameSpecial;
    private javax.swing.JLabel lblCheckPriceCustomHat;
    private javax.swing.JLabel lblCheckPriceSpecial;
    private javax.swing.JLabel lblCustomErrorMessage;
    private javax.swing.JLabel lblCustomErrorPrice;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblImageTxt;
    private javax.swing.JLabel lblSpecialError;
    private javax.swing.JLabel lblSpecialResponsible;
    private javax.swing.JLabel lblSpecialSize;
    private javax.swing.JLabel lblStandardErrorMessage;
    private javax.swing.JLabel lbl_customColor;
    private javax.swing.JLabel lbl_customDescription;
    private javax.swing.JLabel lbl_customFabric;
    private javax.swing.JLabel lbl_customPrice;
    private javax.swing.JLabel lbl_customSize;
    private javax.swing.JLabel lbl_customTitle;
    private javax.swing.JLabel lbl_size;
    private javax.swing.JList<String> listCustHat;
    private javax.swing.JPanel panel_customHat;
    private javax.swing.JPanel panel_specialHat;
    private javax.swing.JPanel panel_standardHat;
    private javax.swing.JScrollPane scrollPane_CustomHat;
    private javax.swing.JTextField textField_customPrice;
    private javax.swing.JTextArea txtAreaCustomDescription;
    private javax.swing.JTextArea txtfSpecialDescription;
    private javax.swing.JTextField txtfSpecialName;
    private javax.swing.JTextField txtfSpecialPrice;
    // End of variables declaration//GEN-END:variables
}
