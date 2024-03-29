/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hatsystem;

import data.Fabric;
import data.StandardHat;
import java.util.ArrayList;
import java.util.HashSet;
import data.SqlQuery;
import data.Validation;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author luna
 */
public class EditStandardHat extends javax.swing.JFrame {

    public static String hatId = "4";
    private String oldName;
    private String oldPrice;
    private String oldDescription;
    private String oldFabricName;
    private String oldFabricColor;
    private LoginMenu loginMenu;

    /**
     * Creates new form EditStandardHat
     */
    public EditStandardHat(String hatId) {
        initComponents();
        this.hatId = hatId;
        fillFabricComboBox();
        fillValue();
        lblErrorMessage.setVisible(false);
    }
    
    /** For editing from sök-lista i LoginMenu
     * 
     * @param hatId 
     */
     public EditStandardHat(String hatId, LoginMenu loginMenu) {
        initComponents();
        this.hatId = hatId;
        this.loginMenu = loginMenu;
        fillFabricComboBox();
        fillValue();
        lblErrorMessage.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbFabrics = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbColors = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        lblCheckName = new javax.swing.JLabel();
        lblCheckPrice = new javax.swing.JLabel();
        lblErrorMessage = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TopIT");

        txtName.setText("jTextField1");
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtPrice.setText("jTextField2");

        jLabel1.setText("Namn");

        jLabel2.setText("Pris");

        jLabel3.setText("Beskrivning");

        jLabel4.setText("Tyg");

        cbFabrics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFabricsActionPerformed(evt);
            }
        });

        jLabel5.setText("Färg");

        btnSave.setText("Spara");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Visa/redigera standardhatt");

        btnDelete.setText("Radera");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblCheckName.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckPrice.setForeground(new java.awt.Color(153, 0, 0));

        lblErrorMessage.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtDescription.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCheckPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(lblCheckName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(108, 108, 108))
            .addGroup(layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel5))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFabrics, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbColors, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(230, 230, 230))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCheckName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(lblCheckPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbFabrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(cbColors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnSave))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void fillFabricComboBox() {

        HashSet<String> fabrics = Fabric.getAllFabricNames();

        for (String s : fabrics) {
            cbFabrics.addItem(s);
        }
    }

    private void fillColorComboBox(String fabricName) {

        cbColors.removeAllItems();

        ArrayList<String> colors = Fabric.getFabricColors(fabricName);

        for (String s : colors) {
            cbColors.addItem(s);
        }
    }

    private void fillValue() {
        HashMap<String, String> currentHat = StandardHat.getHat(Integer.parseInt(hatId));
        HashMap<String, String> currentFabric = Fabric.getFabricFromID(currentHat.get("Hat_Fabric"));

        oldName = currentHat.get("Name");
        oldPrice = currentHat.get("Price");
        oldDescription = currentHat.get("Description");
        oldFabricName = currentFabric.get("Name");
        oldFabricColor = currentFabric.get("Color");

        txtName.setText(oldName);
        txtPrice.setText(oldPrice);
        txtDescription.setText(oldDescription);

        cbFabrics.setSelectedItem(oldFabricName);
        fillColorComboBox(oldFabricName);
        cbColors.setSelectedItem(oldFabricColor);
    }

    private void updateHat() {
        clearErrorMessages();
        lblErrorMessage.setVisible(false);

        String hatName = txtName.getText();
        String hatPrice = txtPrice.getText();
        String hatDescription = txtDescription.getText();
        String fabricName = cbFabrics.getSelectedItem().toString();
        String fabricColor = cbColors.getSelectedItem().toString();

        if (hatName.isBlank() || hatPrice.isBlank()) {
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Vänligen fyll i alla fält");
        } else {
            if (Validation.onlyLetters(hatName, lblCheckName) && Validation.isPrice(hatPrice, lblCheckPrice)) {

                int fabricID = Fabric.getFabricID(fabricName, fabricColor);
                if (StandardHat.isUniqueCombination(hatName, fabricID)) {

                    SqlQuery.update("UPDATE Standard_Hat SET Name = '" + hatName + "' WHERE Standard_Hat_ID = " + hatId + ";");
                    SqlQuery.update("UPDATE Standard_Hat SET Price = '" + hatPrice + "' WHERE Standard_Hat_ID = " + hatId + ";");
                    SqlQuery.update("UPDATE Standard_Hat SET Description = '" + hatDescription + "' WHERE Standard_Hat_ID = " + hatId + ";");
                    SqlQuery.update("UPDATE Standard_Hat SET Hat_Fabric = '" + fabricID + "' WHERE Standard_Hat_ID = " + hatId + ";");
                    JOptionPane.showMessageDialog(null, "Ändringar sparade!");
                    loginMenu.fillCorrectCategory();
                    this.dispose();
                } else {
                    lblErrorMessage.setVisible(true);
                    lblErrorMessage.setText("Denna standardhatt existerar redan");
                }
            }
        }

    }

    private void clearErrorMessages() {
        lblCheckName.setText("");
        lblCheckPrice.setText("");

    }


    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void cbFabricsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFabricsActionPerformed
        String chosenFabric = cbFabrics.getSelectedItem().toString();
        fillColorComboBox(chosenFabric);

    }//GEN-LAST:event_cbFabricsActionPerformed

    // Metoden skapades av Mohannad o Ahmad
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String deActivateHatQuery = "update standard_hat set Active = 0 where Standard_Hat_ID = " + hatId + ";";

        boolean deleted = false;
        if (JOptionPane.showConfirmDialog(null, "Är du säker?", "VARNING! Detta kommer att radera vald hatt!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // yes option
            deleted = SqlQuery.update(deActivateHatQuery);
        } else {
            // no option
        }
        if (deleted) {
            JOptionPane.showMessageDialog(null, "Hatten är nu raderad!");
        } else {
            JOptionPane.showMessageDialog(null, "Ingen hatt har raderats!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        updateHat();
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */

    /* Create and display the form */
 /*  java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditStandardHat(hatId).setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbColors;
    private javax.swing.JComboBox<String> cbFabrics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblCheckName;
    private javax.swing.JLabel lblCheckPrice;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
