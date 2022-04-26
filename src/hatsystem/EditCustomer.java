/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hatsystem;

import data.Address;
import data.Customer;
import data.Order;
import java.util.HashMap;
import javax.swing.JOptionPane;
import data.SqlQuery;
import data.Validation;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Friday
 */
public class EditCustomer extends javax.swing.JFrame {

    private String customerID;
    private String oldFirstName;
    private String oldLastName;
    private String oldEmail;
    private String oldTelephone;
    private String oldComment;
    private String oldStreetAddress;
    private String oldPostCode;
    private String oldCity;
    private String oldCountry;
    private String oldAddressID;
    private String customerNr;
    private LoginMenu loginMenu;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private Font defaultListFont;

    /**
     * Creates new form EditInformation
     */
    public EditCustomer(String customerNr) {
        this.customerNr = customerNr;
        customerNr();
        initComponents();
        listCustomerOrders.setModel(listModel);
        defaultListFont = listCustomerOrders.getFont();
        listCustomerOrders.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));
        fillOrderList();
        fillCustomerInfo();
        lblErrorMessage.setVisible(false);
        clearErrorMessages();
    }
    
    /**
     * For editing from Sök-lista
     * @param customerNr 
     * @param loginMenu 
     */
       public EditCustomer(String customerNr, LoginMenu loginMenu) {
        this.customerNr = customerNr;
        this.loginMenu = loginMenu;
        customerNr();
        initComponents();
        listCustomerOrders.setModel(listModel);
        defaultListFont = listCustomerOrders.getFont();
        listCustomerOrders.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));
        fillOrderList();
        fillCustomerInfo();
        lblErrorMessage.setVisible(false);
        clearErrorMessages();
    }
    
    private void customerNr(){
        customerID = Customer.getCustomerID(customerNr);        
    }
    
    private int orderID(){
        String stringOfOrders = listModel.getElementAt(listCustomerOrders.getSelectedIndex());
        int orderID = Integer.parseInt(stringOfOrders.substring(0, 5).trim());
        return orderID;
    }

    private void clearErrorMessages() {
        lblCheckFirstName.setText("");
        lblCheckLastName.setText("");
        lblCheckPhone.setText("");
        lblCheckEmail.setText("");
        lblCheckPostal.setText("");
        lblCheckCity.setText("");
        lblCheckCountry.setText("");
        lblCheckCountry1.setText("");
    }

    private void updateCustomerInfo() {
        lblErrorMessage.setVisible(false);

        String firstName = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String streetAddress = txt_streetAddress.getText();
        String postCode = txt_postCode.getText();
        String city = txt_city.getText();
        String telephone = txt_telephone.getText();
        String email = txt_email.getText();
        String comment = txt_comment.getText();
        String country = txt_country.getText();

        boolean infoChanged = false;

        if (firstName.isBlank() || lastName.isBlank()
                || telephone.isBlank() || email.isBlank()
                || streetAddress.isBlank() || postCode.isBlank()
                || city.isBlank() || country.isBlank()) {

            lblErrorMessage.setText("Vänligen fyll i alla fält");
            lblErrorMessage.setVisible(true);

        } else {
            String newFirstName = firstName.replaceAll("-", "");
            String newLastName = lastName.replaceAll("-", "");
            if (Validation.onlyLetters(newFirstName, lblCheckFirstName)
                    && Validation.onlyLetters(newLastName, lblCheckLastName)
                    && Validation.onlyDigits(telephone, lblCheckPhone)
                    && Validation.emailFormat(email, lblCheckEmail)
                    && Validation.noSpecialCharacters(streetAddress, lblCheckStreet)
                    && Validation.onlyDigits(postCode, lblCheckPostal)
                    && Validation.onlyLetters(city, lblCheckCity)
                    && Validation.onlyLetters(country, lblCheckCountry)) {

                HashMap<String, String> chosenCustomer = Customer.checkIfCustomerExists(firstName, lastName, email, telephone);

                boolean changeAddress = true;
                if (firstName.equalsIgnoreCase(oldFirstName) && lastName.equalsIgnoreCase(oldLastName) && telephone.equalsIgnoreCase(oldTelephone) && email.equalsIgnoreCase(oldEmail)) {
                    //do nothing
                } else if (chosenCustomer.isEmpty()) {
                    SqlQuery.update("UPDATE customer SET First_Name = '" + firstName + "' WHERE Customer_ID = " + customerID + ";");
                    SqlQuery.update("UPDATE customer SET Last_Name = '" + lastName + "' WHERE Customer_ID = " + customerID + ";");
                    SqlQuery.update("UPDATE customer SET Phone_Nr = '" + telephone + "' WHERE Customer_ID = " + customerID + ";");
                    SqlQuery.update("UPDATE customer SET Email = '" + email + "' WHERE Customer_ID = " + customerID + ";");
                    SqlQuery.update("UPDATE customer SET Comment = '" + comment + "' WHERE Customer_ID = " + customerID + ";");
                    infoChanged = true;
                } else {
                    changeAddress = false;
                    JOptionPane.showMessageDialog(null, "En kund med dessa kunduppgifter existerar redan");
                }
                HashMap<String, String> chosenAddress = Address.getAddress(streetAddress, postCode, city, country);

                if (streetAddress.equalsIgnoreCase(oldStreetAddress) && postCode.equalsIgnoreCase(oldPostCode) && city.equalsIgnoreCase(oldCity) && country.equalsIgnoreCase(oldCountry)) {
                    //do nothing
                } else {
                    if (changeAddress) {
                        if (chosenAddress.isEmpty()) {

                            String addressID = SqlQuery.getValue("SELECT Address FROM customer WHERE Customer_ID = " + customerID + ";");

                            SqlQuery.update("UPDATE address SET Street = '" + streetAddress + "' WHERE Address_ID = " + addressID + ";");
                            SqlQuery.update("UPDATE address SET Postal = '" + postCode + "' WHERE Address_ID = " + addressID + ";");
                            SqlQuery.update("UPDATE address SET City = '" + city + "' WHERE Address_ID = " + addressID + ";");
                            SqlQuery.update("UPDATE address SET Country = '" + country + "' WHERE Address_ID = " + addressID + ";");
                            infoChanged = true;

                        } else {
                            String chosenAddressID = chosenAddress.get("Address_ID");

                            SqlQuery.update("UPDATE customer SET Address = '" + chosenAddressID + "' WHERE Customer_ID = " + customerID + ";");
                            infoChanged = true;
                        }
                    }
                }
            }
        }
        if (infoChanged) {
            JOptionPane.showMessageDialog(null, "Ändringar sparade");
            loginMenu.fillCorrectCategory();
            dispose();
        }
    }

    public void fillOrderList() {
        listModel.clear();
        ArrayList<HashMap<String, String>> orders = SqlQuery.getMultipleRows("SELECT * FROM orders WHERE Customer = " + customerID + ";");
        int index = 0;
        
        while (index < orders.size()) {
            HashMap<String, String> currentOrder = orders.get(index);
            
            
            if(Order.checkIfActive(currentOrder.get("Orders_ID"))){
            listModel.addElement(String.format("%-7s %-15s %-15s %-20s"
                    + currentOrder.get("Total_Price"),
                    currentOrder.get("Orders_ID"),
                    currentOrder.get("Delivery_Date"),
                    currentOrder.get("Order_Date"),
                    currentOrder.get("Status")
            ));
            }

            index++;
        }
        
    }

    private void fillCustomerInfo() {
        HashMap<String, String> customer = SqlQuery.getRow("SELECT * FROM customer WHERE Customer_ID = " + customerID + ";");
        HashMap<String, String> address = Address.getAddressFromID(customer.get("Address"));

        oldFirstName = customer.get("First_Name");
        txt_firstName.setText(oldFirstName);
        oldLastName = customer.get("Last_Name");
        txt_lastName.setText(oldLastName);
        oldEmail = customer.get("Email");
        txt_email.setText(oldEmail);
        oldTelephone = customer.get("Phone_Nr");
        txt_telephone.setText(oldTelephone);
        oldComment = customer.get("Comment");
        txt_comment.setText(oldComment);

        oldStreetAddress = address.get("Street");
        txt_streetAddress.setText(oldStreetAddress);
        oldPostCode = address.get("Postal");
        txt_postCode.setText(oldPostCode);
        oldCity = address.get("City");
        txt_city.setText(oldCity);
        oldCountry = address.get("Country");
        txt_country.setText(oldCountry);

        oldAddressID = address.get("Address_ID");
    }
    
    
           

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCheckCountry1 = new javax.swing.JLabel();
        txt_firstName = new javax.swing.JTextField();
        lbl_firstName = new javax.swing.JLabel();
        lbl_lastName = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_telephone = new javax.swing.JLabel();
        lbl_streetAddress = new javax.swing.JLabel();
        lbl_postCode = new javax.swing.JLabel();
        lbl_city = new javax.swing.JLabel();
        lbl_comment = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_comment = new javax.swing.JTextArea();
        txt_lastName = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_telephone = new javax.swing.JTextField();
        txt_streetAddress = new javax.swing.JTextField();
        txt_postCode = new javax.swing.JTextField();
        txt_city = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        lbl_redigera_kunduppifter = new javax.swing.JLabel();
        btn_Delete = new javax.swing.JButton();
        txt_country = new javax.swing.JTextField();
        lbl_country = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listCustomerOrders = new javax.swing.JList<>();
        lbl_kundens_tidigare_ordrar = new javax.swing.JLabel();
        lblCheckFirstName = new javax.swing.JLabel();
        lblCheckLastName = new javax.swing.JLabel();
        lblCheckPhone = new javax.swing.JLabel();
        lblCheckStreet = new javax.swing.JLabel();
        lblCheckEmail = new javax.swing.JLabel();
        lblCheckPostal = new javax.swing.JLabel();
        lblCheckCity = new javax.swing.JLabel();
        lblCheckCountry = new javax.swing.JLabel();
        lblErrorMessage = new javax.swing.JLabel();
        btnShowOrder = new javax.swing.JButton();

        lblCheckCountry1.setForeground(new java.awt.Color(153, 0, 0));
        lblCheckCountry1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TopIT");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/TopITcute32px.png")).getImage());
        setResizable(false);

        lbl_firstName.setText("Förnamn");

        lbl_lastName.setText("Efternamn");

        lbl_email.setText("E-post");

        lbl_telephone.setText("Telefonnummer");

        lbl_streetAddress.setText("Gatuadress");

        lbl_postCode.setText("Postnummer");

        lbl_city.setText("Stad");

        lbl_comment.setText("Kommentar");

        txt_comment.setColumns(20);
        txt_comment.setRows(5);
        jScrollPane1.setViewportView(txt_comment);

        btn_save.setText("Spara ändringar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        lbl_redigera_kunduppifter.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_redigera_kunduppifter.setText("Redigera kunduppgifter");

        btn_Delete.setText("Radera kund");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        lbl_country.setText("Land");

        listCustomerOrders.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCustomerOrders.setToolTipText("");
        jScrollPane2.setViewportView(listCustomerOrders);

        lbl_kundens_tidigare_ordrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_kundens_tidigare_ordrar.setText("Kundens tidigare ordrar");

        lblCheckFirstName.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckLastName.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckPhone.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckStreet.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckEmail.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckPostal.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckCity.setForeground(new java.awt.Color(153, 0, 0));

        lblCheckCountry.setForeground(new java.awt.Color(153, 0, 0));

        lblErrorMessage.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErrorMessage.setText("Vänligen fyll i alla fält");

        btnShowOrder.setText("Visa order");
        btnShowOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl_lastName)
                                .addComponent(lbl_firstName)
                                .addComponent(lbl_telephone)
                                .addComponent(lbl_email)
                                .addComponent(lbl_streetAddress)
                                .addComponent(lbl_city)
                                .addComponent(lbl_postCode))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lbl_country))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(lbl_comment)))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblErrorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(txt_country)
                    .addComponent(txt_email)
                    .addComponent(txt_lastName)
                    .addComponent(txt_firstName)
                    .addComponent(txt_telephone)
                    .addComponent(txt_streetAddress)
                    .addComponent(txt_postCode)
                    .addComponent(txt_city, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_save)
                        .addGap(56, 56, 56)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Delete))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCheckPostal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckStreet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckCountry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckLastName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheckFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_kundens_tidigare_ordrar)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnShowOrder)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(lbl_redigera_kunduppifter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lbl_redigera_kunduppifter)
                .addGap(18, 18, 18)
                .addComponent(lbl_kundens_tidigare_ordrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblCheckLastName)
                        .addGap(35, 35, 35)
                        .addComponent(lblCheckPhone)
                        .addGap(18, 18, 18)
                        .addComponent(lblCheckEmail)
                        .addGap(52, 52, 52)
                        .addComponent(lblCheckStreet)
                        .addGap(18, 18, 18)
                        .addComponent(lblCheckPostal)
                        .addGap(18, 18, 18)
                        .addComponent(lblCheckCity)
                        .addGap(18, 18, 18)
                        .addComponent(lblCheckCountry))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowOrder)
                    .addComponent(lblCheckFirstName)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_firstName))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_lastName))
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_telephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_telephone))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_email))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_streetAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_streetAddress))
                                .addGap(18, 18, 18)
                                .addComponent(txt_postCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(201, 201, 201)
                                .addComponent(lbl_postCode)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_city))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_country, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_country))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_comment))
                        .addGap(18, 18, 18)
                        .addComponent(lblErrorMessage)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save)
                    .addComponent(btn_Delete))
                .addGap(32, 32, 32))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        boolean deActivated = false;
        String deActivateCustQuery = "update customer set Active = 0 where Customer_ID = " + customerID + ";";
        if (JOptionPane.showConfirmDialog(null, "Är du säker?", "VARNING! Detta kommer att radera vald kund!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            deActivated = SqlQuery.update(deActivateCustQuery);
        } else {
            // no option
        }
        if (deActivated) {
            JOptionPane.showMessageDialog(null, "Kunden är nu raderad!");
            Customer.listAllCustomers(LoginMenu.getSearchListModel());
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Ingen kund har raderats!");
        }
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        clearErrorMessages();
        updateCustomerInfo();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btnShowOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOrderActionPerformed
        new EditOrder(orderID(), this).setVisible(true); 
    }//GEN-LAST:event_btnShowOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShowOrder;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_save;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCheckCity;
    private javax.swing.JLabel lblCheckCountry;
    private javax.swing.JLabel lblCheckCountry1;
    private javax.swing.JLabel lblCheckEmail;
    private javax.swing.JLabel lblCheckFirstName;
    private javax.swing.JLabel lblCheckLastName;
    private javax.swing.JLabel lblCheckPhone;
    private javax.swing.JLabel lblCheckPostal;
    private javax.swing.JLabel lblCheckStreet;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JLabel lbl_city;
    private javax.swing.JLabel lbl_comment;
    private javax.swing.JLabel lbl_country;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_firstName;
    private javax.swing.JLabel lbl_kundens_tidigare_ordrar;
    private javax.swing.JLabel lbl_lastName;
    private javax.swing.JLabel lbl_postCode;
    private javax.swing.JLabel lbl_redigera_kunduppifter;
    private javax.swing.JLabel lbl_streetAddress;
    private javax.swing.JLabel lbl_telephone;
    private javax.swing.JList<String> listCustomerOrders;
    private javax.swing.JTextField txt_city;
    private javax.swing.JTextArea txt_comment;
    private javax.swing.JTextField txt_country;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_firstName;
    private javax.swing.JTextField txt_lastName;
    private javax.swing.JTextField txt_postCode;
    private javax.swing.JTextField txt_streetAddress;
    private javax.swing.JTextField txt_telephone;
    // End of variables declaration//GEN-END:variables
}
