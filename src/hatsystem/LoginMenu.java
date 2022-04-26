/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hatsystem;

import data.Fabric;
import data.StandardHat;
import data.Order;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import data.SqlQuery;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import data.Customer;
import data.Address;
import data.Employee;
import data.GeneratePDF;
import data.Validation;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author luna
 */
public class LoginMenu extends javax.swing.JFrame {

    /**
     * Creates new form LoginMenu
     */
    private static String username;
    // A HashMap that stores id and size from class AddHatType.
    private static String employeeFirstName;
    private static HashMap<String, String> hashMapStandardHat = new HashMap<>();
    // An Array that stores id from class AddHatType.
    private static ArrayList<Integer> arrayOtherHat = new ArrayList<Integer>();
    private static DefaultListModel<String> orderListModel = new DefaultListModel<>();
    private Font defaultListFontOther;
    private static DefaultListModel<String> listModel = new DefaultListModel<>();

    private static HashMap<String, String> hashMapListPrice = new HashMap<>();
    //Had to create a static label variable that references to the non-static lable
    //to be able to use it with static methods.
    private static JLabel totalPriceLabel;

    // refers to current LoginMenu object
    private static LoginMenu mainLoginMenu;
    private static DefaultListModel listModelStatic = listModel;

    public LoginMenu(String username) {
        this.username = username;
        
        employeeFirstName = SqlQuery.getValue("SELECT Name FROM employee WHERE Username = '"+ username +"';");
        initComponents();

        lblError.setVisible(false);
        lblErrorEmpty.setVisible(false);
        lblErrorDeliveryAddress.setText("");
        lblErrorPostAddress.setText("");
        lblErrorCountry.setText("");
        lblErrorDate.setText("");

        jListAllOrders.setModel(orderListModel);
        defaultListFontOther = jListAllOrders.getFont();
        jListAllOrders.setFont(new Font("monospaced", defaultListFontOther.getStyle(), defaultListFontOther.getSize()));
        jPanelOrderAddress.setVisible(false);
        listFoundResults.setModel(listModel);
        Font defaultListFont = jListAllOrders.getFont();
        jListAllOrders.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));
        listFoundResults.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));

        this.mainLoginMenu = mainLoginMenu;
        lblErrorMessageCategory.setVisible(false);
        totalPriceLabel = lblTotalPrice;
        // Belongs to "Sök" tab
        cbCategory.setSelectedItem("Kunder");
        fillCorrectCategory();
//        Customer.listAllCustomers(listModel);
        cmbSearchStandardHats.setEnabled(false);
        cmbSearchStandardHats.setVisible(false);

        setYearsTotalSales();

    }

    public static DefaultListModel getSearchListModel() {
        return listModelStatic;
    }

    /**
     * Returns the logged in username
     *
     * @return
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Retrieves one standard hats compeleteID and size, and adds them to the jList "listFoundResults". Used in the "Sök" tab.
     */
    public static void addToListStandardHat(String completeHatIdentifier, String size) {
        hashMapStandardHat.put(completeHatIdentifier, size);

    }

    /**
     * Retrives one custum or special hats id and adds it to the ArrayList. Is then used for retrieving the hat from db.
     *
     * @param hatID
     */
    public static void addToListOtherHat(int hatID) {

        arrayOtherHat.add(hatID);

    }

    private static double getTotalPrice() {
        double totalPrice = 0;
        for (String i : hashMapListPrice.keySet()) {
            Double price = Double.parseDouble(hashMapListPrice.get(i));

            totalPrice += price;

        }
        return totalPrice;
    }

    private double getTotalPriceNonStatic() {
        double totalPrice = 0;
        for (String i : hashMapListPrice.keySet()) {
            Double price = Double.parseDouble(hashMapListPrice.get(i));

            totalPrice += price;
        }
        return totalPrice;
    }

    /**
     * Fills the jList with information about all hats for the ongoing order.
     */
    public static void listOrderItems() {
        ArrayList<HashMap<String, String>> addedStandardHats = new ArrayList<>();
        ArrayList<HashMap<String, String>> addedOtherHats = new ArrayList<>();

        ArrayList<String> arrayOfCompleteID = new ArrayList<>();

        orderListModel.clear();

        if (!hashMapStandardHat.isEmpty()) {
            hashMapStandardHat.forEach((key, value) -> {
                String id = key.substring(0, key.indexOf("."));
                HashMap<String, String> fetchedHat = SqlQuery.getRow("SELECT * FROM Standard_Hat WHERE Standard_Hat_ID = " + id + ";");
                addedStandardHats.add(fetchedHat);
                arrayOfCompleteID.add(key);

            });

            int index = 0;

            while (index < hashMapStandardHat.size()) {
                HashMap<String, String> currentHat = addedStandardHats.get(index);
                String completeID = arrayOfCompleteID.get(index);
                String size = hashMapStandardHat.get(completeID);

                String fabricID = currentHat.get("Hat_Fabric");
                HashMap<String, String> currentFabric = Fabric.getFabricFromID(fabricID);

                orderListModel.addElement(String.format("%-7s %-12s %-10s %-10s %-10s" + currentHat.get("Price"),
                        completeID, currentHat.get("Name"), currentFabric.get("Name"),
                        currentFabric.get("Color"), size));

                hashMapListPrice.put(completeID, currentHat.get("Price"));
                index++;

            }

        }

        if (!arrayOtherHat.isEmpty()) {
            for (int id : arrayOtherHat) {
                HashMap<String, String> fetchedHat = SqlQuery.getRow("SELECT * FROM Hat WHERE Hat_ID = " + id + ";");
                addedOtherHats.add(fetchedHat);
            }

            int index2 = 0;
            while (index2 < arrayOtherHat.size()) {
                HashMap<String, String> currentHat = addedOtherHats.get(index2);

                String fabricID = currentHat.get("Hat_Fabric");
                HashMap<String, String> currentFabric = Fabric.getFabricFromID(fabricID);

                orderListModel.addElement(String.format("%-7s %-12s %-10s %-10s %-10s" + currentHat.get("Price"), "C"
                        + currentHat.get("Hat_ID"), currentHat.get("Name"), currentFabric.get("Name"),
                        currentFabric.get("Color"), currentHat.get("Size")));
                hashMapListPrice.put(currentHat.get("Hat_ID"), currentHat.get("Price"));
                index2++;

            }

        }
        String totalPrice = String.valueOf(getTotalPrice());
        totalPriceLabel.setText(totalPrice);
    }

    /**
     * Deletes all hats in jList from db and static lists.
     */
    private void deleteNonOrderedHats() {

        for (int id : arrayOtherHat) {
            SqlQuery.delete("DELETE FROM special_hat WHERE Hat_ID = " + id + ";");
            SqlQuery.delete("DELETE FROM custom_hat WHERE Hat_ID = " + id + ";");
            SqlQuery.delete("DELETE FROM hat WHERE Hat_ID = " + id + ";");
        }
        hashMapStandardHat.clear();
        arrayOtherHat.clear();
        orderListModel.clear();
        hashMapListPrice.clear();
        lblTotalPrice.setText(String.valueOf(getTotalPriceNonStatic()));
    }

    public void addCustomerInfoToOrder(HashMap<String, String> customerInfo, String customerNr) {

        lblCustomerName.setText(customerInfo.get("First_Name") + " " + customerInfo.get("Last_Name"));

        HashMap<String, String> address = Address.getAddressFromID(customerInfo.get("Address"));

        txtDeliveryAdress.setText(address.get("Street"));
        txtPostCode.setText(address.get("Postal"));
        txtCity.setText(address.get("City"));
        txtCountry.setText(address.get("Country"));
        jPanelOrderAddress.setVisible(true);
        lblCustomerNumber.setText(customerNr);

    }

    private void setYearsTotalSales() {

        ArrayList<String> years = SqlQuery.getColumn("SELECT DISTINCT year(Order_Date) FROM orders;");

        for (String element : years) {
            cmbChosenYear.addItem(element);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_start = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        btn_changePassword = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        panel_createOrder = new javax.swing.JPanel();
        btnSaveOrder = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAllOrders = new javax.swing.JList<>();
        btnAddNewHatType = new javax.swing.JButton();
        btnClearHats = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCreateNewCustomerFromOrder = new javax.swing.JButton();
        txtExpectedDate = new javax.swing.JTextField();
        btbDeleteChosenHat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCustomerName = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jPanelOrderAddress = new javax.swing.JPanel();
        txtDeliveryAdress = new javax.swing.JTextField();
        txtPostCode = new javax.swing.JTextField();
        txtCity = new javax.swing.JTextField();
        txtCountry = new javax.swing.JTextField();
        lblCustomerNumber = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        lblSum = new javax.swing.JLabel();
        lblErrorEmpty = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();
        lblErrorDate = new javax.swing.JLabel();
        lblErrorDeliveryAddress = new javax.swing.JLabel();
        lblErrorPostAddress = new javax.swing.JLabel();
        lblErrorCountry = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panel_register = new javax.swing.JPanel();
        btnRegisterCustomer = new javax.swing.JButton();
        btnRegisterStandardHat = new javax.swing.JButton();
        btnRegisterFabric = new javax.swing.JButton();
        panel_search = new javax.swing.JPanel();
        lblChooseCategory = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFoundResults = new javax.swing.JList<>();
        btnEditCatergory = new javax.swing.JButton();
        lblErrorMessageCategory = new javax.swing.JLabel();
        txtSearchWord = new javax.swing.JTextField();
        cmbSearchSpecific = new javax.swing.JComboBox<>();
        btnSearchSpecific = new javax.swing.JButton();
        lblErrorValidateSearchWord = new javax.swing.JLabel();
        cmbSearchStandardHats = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbChosenYear = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnGeneratePDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TopIT");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Images/TopITcute32px.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFocusable(false);

        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("Välkommen "+ employeeFirstName + "!");

        btn_logout.setText("Logga ut");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        btn_changePassword.setText("Ändra lösenord");
        btn_changePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changePasswordActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/topIT.png"))); // NOI18N

        javax.swing.GroupLayout panel_startLayout = new javax.swing.GroupLayout(panel_start);
        panel_start.setLayout(panel_startLayout);
        panel_startLayout.setHorizontalGroup(
            panel_startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_startLayout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addGroup(panel_startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_startLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel9))
                    .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(349, Short.MAX_VALUE))
            .addGroup(panel_startLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panel_startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_changePassword, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(btn_logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_startLayout.setVerticalGroup(
            panel_startLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_startLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addComponent(btn_changePassword)
                .addGap(33, 33, 33)
                .addComponent(btn_logout)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        lbl_title.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Start", panel_start);

        btnSaveOrder.setText("Spara order");
        btnSaveOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveOrderActionPerformed(evt);
            }
        });

        jScrollPane2.setColumnHeaderView(null);
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jListAllOrders.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListAllOrders.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAllOrdersValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListAllOrders);

        btnAddNewHatType.setText("Lägg till ny hatt i order");
        btnAddNewHatType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewHatTypeActionPerformed(evt);
            }
        });

        btnClearHats.setText("Rensa alla hattar");
        btnClearHats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearHatsActionPerformed(evt);
            }
        });

        jLabel2.setText("Kund");

        jLabel3.setText("Leveransadress");

        jLabel4.setText("Beräknat leveransdatum");

        jLabel5.setText("Orderstatus");

        btnCreateNewCustomerFromOrder.setText("Lägg till kund");
        btnCreateNewCustomerFromOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewCustomerFromOrderActionPerformed(evt);
            }
        });

        btbDeleteChosenHat.setText("Ta bort vald hatt");
        btbDeleteChosenHat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbDeleteChosenHatActionPerformed(evt);
            }
        });

        jLabel1.setText("Postadress");

        jLabel6.setText("Land");

        lblCustomerName.setText(" ");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Under utvärdering", "Pågående", "Redo att skickas" }));

        javax.swing.GroupLayout jPanelOrderAddressLayout = new javax.swing.GroupLayout(jPanelOrderAddress);
        jPanelOrderAddress.setLayout(jPanelOrderAddressLayout);
        jPanelOrderAddressLayout.setHorizontalGroup(
            jPanelOrderAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrderAddressLayout.createSequentialGroup()
                .addGroup(jPanelOrderAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOrderAddressLayout.createSequentialGroup()
                        .addComponent(txtPostCode, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCity, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                    .addComponent(txtDeliveryAdress)
                    .addComponent(txtCountry))
                .addContainerGap())
        );
        jPanelOrderAddressLayout.setVerticalGroup(
            jPanelOrderAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrderAddressLayout.createSequentialGroup()
                .addComponent(txtDeliveryAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelOrderAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPostCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Kundnummer");

        lblTotalPrice.setText("0.0");

        lblSum.setText("Summa");

        lblErrorEmpty.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorEmpty.setText("Vänligen lägg till ett objekt");

        lblError.setForeground(new java.awt.Color(153, 0, 0));
        lblError.setText("Vänligen välj en hatt");

        lblErrorDate.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorDate.setText("Date");

        lblErrorDeliveryAddress.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorDeliveryAddress.setText("DeliberyAddress");

        lblErrorPostAddress.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorPostAddress.setText("PostAddress");

        lblErrorCountry.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorCountry.setText("Country");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Skapa order");

        javax.swing.GroupLayout panel_createOrderLayout = new javax.swing.GroupLayout(panel_createOrder);
        panel_createOrder.setLayout(panel_createOrderLayout);
        panel_createOrderLayout.setHorizontalGroup(
            panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_createOrderLayout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_createOrderLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveOrder)
                        .addGap(53, 53, 53)
                        .addComponent(lblSum, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112))
                    .addGroup(panel_createOrderLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_createOrderLayout.createSequentialGroup()
                                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btbDeleteChosenHat, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(btnClearHats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(75, 75, 75))
                            .addGroup(panel_createOrderLayout.createSequentialGroup()
                                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCreateNewCustomerFromOrder)
                                    .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddNewHatType)
                                    .addComponent(lblErrorEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panel_createOrderLayout.createSequentialGroup()
                        .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_createOrderLayout.createSequentialGroup()
                                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCustomerNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_createOrderLayout.createSequentialGroup()
                                        .addComponent(jPanelOrderAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblErrorDeliveryAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblErrorPostAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblErrorCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addGroup(panel_createOrderLayout.createSequentialGroup()
                                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtExpectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblErrorDate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        panel_createOrderLayout.setVerticalGroup(
            panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_createOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreateNewCustomerFromOrder, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_createOrderLayout.createSequentialGroup()
                        .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_createOrderLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel6))
                    .addGroup(panel_createOrderLayout.createSequentialGroup()
                        .addComponent(lblErrorDeliveryAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorPostAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorCountry))
                    .addComponent(jPanelOrderAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtExpectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_createOrderLayout.createSequentialGroup()
                        .addComponent(btnAddNewHatType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblErrorEmpty)
                        .addGap(117, 117, 117)
                        .addComponent(lblError)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btbDeleteChosenHat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearHats))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_createOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveOrder)
                    .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSum))
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("Skapa order", panel_createOrder);

        btnRegisterCustomer.setText("Registrera ny kund");
        btnRegisterCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterCustomerActionPerformed(evt);
            }
        });

        btnRegisterStandardHat.setText("Registrera ny standardhatt");
        btnRegisterStandardHat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterStandardHatActionPerformed(evt);
            }
        });

        btnRegisterFabric.setText("Registrera nytt tyg");
        btnRegisterFabric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterFabricActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_registerLayout = new javax.swing.GroupLayout(panel_register);
        panel_register.setLayout(panel_registerLayout);
        panel_registerLayout.setHorizontalGroup(
            panel_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_registerLayout.createSequentialGroup()
                .addGap(363, 363, 363)
                .addGroup(panel_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegisterCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegisterStandardHat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegisterFabric, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(412, Short.MAX_VALUE))
        );
        panel_registerLayout.setVerticalGroup(
            panel_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_registerLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(btnRegisterCustomer)
                .addGap(50, 50, 50)
                .addComponent(btnRegisterStandardHat)
                .addGap(45, 45, 45)
                .addComponent(btnRegisterFabric)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registrera", panel_register);

        lblChooseCategory.setText("Ange sökkategori:");

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kunder", "Ordrar", "Standardhattar" }));
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(listFoundResults);

        btnEditCatergory.setText("Visa/redigera");
        btnEditCatergory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCatergoryActionPerformed(evt);
            }
        });

        lblErrorMessageCategory.setForeground(new java.awt.Color(153, 0, 0));
        lblErrorMessageCategory.setText("Vänligen välj något objekt att redigera först");

        txtSearchWord.setToolTipText("");

        cmbSearchSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Förnamn", "Efternamn" }));
        cmbSearchSpecific.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbSearchSpecificFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbSearchSpecificFocusLost(evt);
            }
        });
        cmbSearchSpecific.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSearchSpecificActionPerformed(evt);
            }
        });

        btnSearchSpecific.setText("Sök kund");
        btnSearchSpecific.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSpecificActionPerformed(evt);
            }
        });

        lblErrorValidateSearchWord.setForeground(new java.awt.Color(153, 0, 0));

        javax.swing.GroupLayout panel_searchLayout = new javax.swing.GroupLayout(panel_search);
        panel_search.setLayout(panel_searchLayout);
        panel_searchLayout.setHorizontalGroup(
            panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_searchLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(lblChooseCategory)
                .addGap(18, 18, 18)
                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_searchLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSearchSpecific, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchWord, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_searchLayout.createSequentialGroup()
                        .addComponent(btnSearchSpecific, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblErrorValidateSearchWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditCatergory))
                    .addGroup(panel_searchLayout.createSequentialGroup()
                        .addComponent(cmbSearchStandardHats, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblErrorMessageCategory)))
                .addGap(77, 77, 77))
            .addGroup(panel_searchLayout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        panel_searchLayout.setVerticalGroup(
            panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_searchLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChooseCategory))
                .addGap(53, 53, 53)
                .addGroup(panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_searchLayout.createSequentialGroup()
                        .addComponent(lblErrorMessageCategory)
                        .addGroup(panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_searchLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnSearchSpecific))
                            .addGroup(panel_searchLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditCatergory))
                            .addGroup(panel_searchLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblErrorValidateSearchWord, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel_searchLayout.createSequentialGroup()
                        .addGroup(panel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSearchSpecific, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSearchStandardHats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sök", panel_search);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Skapa årssammanställning av totalförsäljning");

        cmbChosenYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChosenYearActionPerformed(evt);
            }
        });

        jLabel10.setText("Välj år");

        btnGeneratePDF.setText("Generera PDF");
        btnGeneratePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneratePDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGeneratePDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbChosenYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(221, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(209, 209, 209))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel8)
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbChosenYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(26, 26, 26)
                .addComponent(btnGeneratePDF)
                .addContainerGap(281, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Årsredovisning", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_changePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_changePasswordActionPerformed
        new ChangePsw(username).setVisible(true);
    }//GEN-LAST:event_btn_changePasswordActionPerformed

    private void btnAddNewHatTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewHatTypeActionPerformed
        new AddHatType().setVisible(true);
        lblErrorEmpty.setVisible(false);
    }//GEN-LAST:event_btnAddNewHatTypeActionPerformed

    private void btnClearHatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearHatsActionPerformed
        int x = JOptionPane.showConfirmDialog(null, "Är du säker på att du vill radera alla hattar?", "Varning!", JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.YES_OPTION) {

            deleteNonOrderedHats();
        }
    }//GEN-LAST:event_btnClearHatsActionPerformed

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        fillCorrectCategory();
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void btnRegisterCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterCustomerActionPerformed
        new AddCustomer().setVisible(true);
    }//GEN-LAST:event_btnRegisterCustomerActionPerformed

    private void btnRegisterStandardHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterStandardHatActionPerformed
        new AddStandardHat().setVisible(true);
    }//GEN-LAST:event_btnRegisterStandardHatActionPerformed

    private void btnCreateNewCustomerFromOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewCustomerFromOrderActionPerformed
        new FindCustomerFromOrder(this).setVisible(true);
        lblErrorDeliveryAddress.setText("");
        lblErrorPostAddress.setText("");
        lblErrorCountry.setText("");
        lblErrorDate.setText("");
    }//GEN-LAST:event_btnCreateNewCustomerFromOrderActionPerformed

    private void btnSaveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOrderActionPerformed
        //TODO error knapparna för kunden i orderfönstret flyttar på sig själva.

        lblErrorDeliveryAddress.setText("");
        lblErrorPostAddress.setText("");
        lblErrorCountry.setText("");
        lblErrorDate.setText("");

        String totalPrice = lblTotalPrice.getText();
        String deliveryDate = txtExpectedDate.getText();
        String orderDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String status = cmbStatus.getSelectedItem().toString();

        String streetAddress = txtDeliveryAdress.getText();
        String postCode = txtPostCode.getText();
        String city = txtCity.getText();
        String country = txtCountry.getText();
        HashMap<String, String> chosenAddress = Address.getAddress(streetAddress, postCode, city, country);
        String addressID = chosenAddress.get("Address_ID");

        // TODO validering för om kund/adress/datum är valt
        String customerNr = lblCustomerNumber.getText();
        String customerID = Customer.getCustomerID(customerNr);

        int employeeID = Employee.getEmployeeID(username);

        if (hashMapStandardHat.isEmpty() && arrayOtherHat.isEmpty()) {

            lblErrorEmpty.setVisible(true);

        } else {
            if (!Validation.isBlank(txtDeliveryAdress, lblErrorDeliveryAddress)
                    && !Validation.isBlank(txtPostCode, lblErrorPostAddress)
                    && !Validation.isBlank(txtCity, lblErrorPostAddress)
                    && !Validation.isBlank(txtCountry, lblErrorCountry)
                    && !Validation.isBlank(txtExpectedDate, lblErrorDate)) {
                if (Validation.onlyDigits(txtPostCode.getText(), lblErrorPostAddress)
                        && Validation.onlyLetters(txtCity.getText(), lblErrorPostAddress)
                        && Validation.onlyLetters(txtCountry.getText(), lblErrorCountry)
                        && Validation.isDate(txtExpectedDate.getText(), lblErrorDate)
                        && Validation.isValidMonthAndDayWithLabel(txtExpectedDate.getText().substring(5, 7), txtExpectedDate.getText().substring(8, 10), lblErrorDate)) {

                    boolean exists = Address.doesAddressExist(streetAddress, postCode, city, country);
                    if (!exists) {
                        Address.addAddress(streetAddress, postCode, city, country);
                        addressID = Address.getAddressID();

                    }

                    boolean success = Order.addToOrder(totalPrice, deliveryDate, orderDate, status, addressID, customerID, employeeID);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Ordern är skapad!");
                    }
                    int orderID = Order.getOrderID();

                    if (!hashMapStandardHat.isEmpty()) {
                        for (String i : hashMapStandardHat.keySet()) {
                            String size = hashMapStandardHat.get(i);
                            String standardHatID = i.substring(0, i.indexOf("."));
                            int hatID = Integer.parseInt(standardHatID);

                            Order.addToOrderedStandardHat(size, hatID, orderID);
                        }

                    }
                    if (!arrayOtherHat.isEmpty()) {
                        for (int id : arrayOtherHat) {
                            Order.addToOrderedHat(id, orderID);
                        }
                    }
                    hashMapStandardHat.clear();
                    arrayOtherHat.clear();
                    hashMapListPrice.clear();
                    lblCustomerName.setText("");
                    lblCustomerNumber.setText("");
                    txtDeliveryAdress.setText("");
                    txtCity.setText("");
                    txtCountry.setText("");
                    txtPostCode.setText("");
                    txtExpectedDate.setText("");
                    listOrderItems();

                    //fraktsedel
                    if (cmbStatus.getSelectedIndex() == 2) {
                        int x = JOptionPane.showConfirmDialog(null, "Vill du generera en fraktsedel för ordern?", "Fraktsedel", JOptionPane.YES_NO_OPTION);

                        if (x == JOptionPane.YES_OPTION) {
                            String addedOrder = SqlQuery.getValue("SELECT MAX(Orders_ID) FROM orders;");

                            new GenerateInvoicePDF(addedOrder).setVisible(true);
                        }
                    }
                }
            }

            cmbStatus.setSelectedIndex(0);
    }//GEN-LAST:event_btnSaveOrderActionPerformed
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        deleteNonOrderedHats();
    }//GEN-LAST:event_formWindowClosing

    /**
     * Deletes choosen hat from jList, db and static lists.
     *
     * @param evt
     */
    private void btbDeleteChosenHatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbDeleteChosenHatActionPerformed

        int test = jListAllOrders.getSelectedIndex();

        String hatIdentifier;
        if (jListAllOrders.getSelectedIndex() > -1) {
            int x = JOptionPane.showConfirmDialog(null, "Är du säker på att du vill radera vald hatt?", "Varning!", JOptionPane.YES_NO_OPTION);

            if (x == JOptionPane.YES_OPTION) {
                String itemToDelete = orderListModel.getElementAt(jListAllOrders.getSelectedIndex());
                String typeOfHat = itemToDelete.substring(0, 1);
                String customHatID = itemToDelete.substring(1, 7).replaceAll("\\s", "");
                String standardHatID = itemToDelete.substring(0, 7).replaceAll("\\s", "");
                if (typeOfHat.equals("C")) {

                    //Lambda uttryck som tar bort ALLA element i listan som har värdet av hatID.
                    arrayOtherHat.removeIf(s -> s == Integer.parseInt(customHatID));

                    SqlQuery.delete("DELETE FROM special_hat WHERE Hat_ID = " + customHatID + ";");
                    SqlQuery.delete("DELETE FROM custom_hat WHERE Hat_ID = " + customHatID + ";");
                    SqlQuery.delete("DELETE FROM hat WHERE Hat_ID = " + customHatID + ";");
                    hashMapListPrice.remove(customHatID);

                } else {
                    hashMapStandardHat.remove(standardHatID);
                    hashMapListPrice.remove(standardHatID);
                }
                listOrderItems();
                lblTotalPrice.setText(String.valueOf(getTotalPriceNonStatic()));
            }
        } else {
            lblError.setVisible(true);
        }

    }//GEN-LAST:event_btbDeleteChosenHatActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btnEditCatergoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCatergoryActionPerformed
        lblErrorMessageCategory.setVisible(false);

        String category = cbCategory.getSelectedItem().toString();
        String objectToEdit = listFoundResults.getSelectedValue();

        if (objectToEdit == null) {
            lblErrorMessageCategory.setVisible(true);
            lblErrorMessageCategory.setText("Vänligen välj något objekt att redigera först");

        } else {
            switch (category) {
                case "Kunder":
                    String customerNr = objectToEdit.substring(0, 9);
                    new EditCustomer(customerNr, this).setVisible(true);
                    break;
                case "Ordrar":
                    String orderToEdit = objectToEdit.substring(0, 10).trim();
                    new EditOrder(Integer.parseInt(orderToEdit), this).setVisible(true);
                    break;
                case "Standardhattar":
                    String hatID = objectToEdit.substring(0, 10).trim();
                    new EditStandardHat(hatID, this).setVisible(true);
                    break;
                default:
                    lblErrorMessageCategory.setVisible(true);
                    lblErrorMessageCategory.setText("Vänligen välj något objekt att redigera först");
            }
        }

    }//GEN-LAST:event_btnEditCatergoryActionPerformed

    private void btnRegisterFabricActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterFabricActionPerformed
        new AddFabric().setVisible(true);
    }//GEN-LAST:event_btnRegisterFabricActionPerformed

    private void btnSearchSpecificActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSpecificActionPerformed

        //hämta in vad användaren vill söka efter
        String category = cmbSearchSpecific.getSelectedItem().toString();
        //hämta in sökord
        String searchWord = txtSearchWord.getText().trim();
        lblErrorValidateSearchWord.setText("");

        switch (category) {
            case "Förnamn":
                Customer.listCustomersFromFirstName(searchWord, listModel);
                break;
            case "Efternamn":
                Customer.listCustomerFromLastName(searchWord, listModel);
                break;
            case "Under utvärdering":
                Order.listOrdersByStatus(category, listModel);
                break;
            case "Pågående":
                Order.listOrdersByStatus(category, listModel);
                break;
            case "Redo att skickas":
                Order.listOrdersByStatus(category, listModel);
                break;
            case "Skickad":
                Order.listOrdersByStatus(category, listModel);
                break;
            case "Hattnamn":
                StandardHat.listAllHatsByHatName(cmbSearchStandardHats.getSelectedItem().toString(), listModel);
                break;
            case "Tyg":
                StandardHat.listAllHatsByFabric(cmbSearchStandardHats.getSelectedItem().toString(), listModel);
                break;
            case "Lista alla kunder":
                Customer.listAllCustomers(listModel);
                break;
            case "Lista alla ordrar":
                Order.listAllOrders(listModel);
                break;
            case "Lista alla standardhattar":
                StandardHat.listAllStandardHats(listModel);
                break;
            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_btnSearchSpecificActionPerformed

    private void btnGeneratePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneratePDFActionPerformed

        JFileChooser fc = new JFileChooser();
        //så användaren endast kan välja mappar
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();
            //hämtar den valda mappens sökväg
            String searchPath = file.getAbsolutePath();

            String year = cmbChosenYear.getSelectedItem().toString();
            searchPath += ("\\Oversikt_Moms_" + year);

            //skapar fraktsedeln
            GeneratePDF.generateMomsPDF(year, searchPath);
            JOptionPane.showMessageDialog(null, "Sammanställning har skapats");

        }
    }//GEN-LAST:event_btnGeneratePDFActionPerformed

    private void cmbSearchSpecificActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSearchSpecificActionPerformed
//        String searchingFor = cmbSearchSpecific.getSelectedItem().toString();
//        if(searchingFor.equals("Visa alla")){
//            cmbSearchStandardHats.setEnabled(false);
//            txtSearchWord.setEditable(false);
//        }
    }//GEN-LAST:event_cmbSearchSpecificActionPerformed

    private void cmbSearchSpecificFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbSearchSpecificFocusGained

    }//GEN-LAST:event_cmbSearchSpecificFocusGained

    private void cmbSearchSpecificFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbSearchSpecificFocusLost
        String headCategory = cbCategory.getSelectedItem().toString();
        if (headCategory.equals("Standardhattar"));
        String category = cmbSearchSpecific.getSelectedItem().toString();

        switch (category) {
            case "Hattnamn": {
                cmbSearchStandardHats.removeAllItems();
                StandardHat.fillCmbWithAllHatNames(cmbSearchStandardHats);
                break;
            }
            case "Tyg": {
                cmbSearchStandardHats.removeAllItems();
                Fabric.fillCmbWithAllFabrics(cmbSearchStandardHats);
                break;
            }
        }
    }//GEN-LAST:event_cmbSearchSpecificFocusLost

    private void jListAllOrdersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAllOrdersValueChanged
        lblError.setVisible(false);
    }//GEN-LAST:event_jListAllOrdersValueChanged

    private void cmbChosenYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbChosenYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbChosenYearActionPerformed
    /**
     * Fill listmodel with data depending on user's choice in combobox
     */
    public void fillCorrectCategory() {

        String category = cbCategory.getSelectedItem().toString();
        // cmbSearchSpecific.removeAllItems();

        switch (category) {
            case "Kunder": {
                Customer.listAllCustomers(listModel);
                lblErrorMessageCategory.setText("");
                txtSearchWord.setEnabled(true);
                cmbSearchStandardHats.setEnabled(false);
                cmbSearchStandardHats.removeAllItems();
                cmbSearchStandardHats.setVisible(false);
                btnSearchSpecific.setText("Sök kund");
                cmbSearchSpecific.removeAllItems();
                cmbSearchSpecific.addItem("Förnamn");
                cmbSearchSpecific.addItem("Efternamn");
                cmbSearchSpecific.addItem("Lista alla kunder");
                break;
            }
            case "Ordrar": {
                Order.listAllOrders(listModel);
                lblErrorMessageCategory.setText("");
                txtSearchWord.setEnabled(false);
                txtSearchWord.setText("");
                cmbSearchStandardHats.setEnabled(false);
                cmbSearchStandardHats.removeAllItems();
                cmbSearchStandardHats.setVisible(false);
                btnSearchSpecific.setText("Sök order");
                cmbSearchSpecific.removeAllItems();
                cmbSearchSpecific.addItem("Under utvärdering");
                cmbSearchSpecific.addItem("Pågående");
                cmbSearchSpecific.addItem("Redo att skickas");
                cmbSearchSpecific.addItem("Skickad");
                cmbSearchSpecific.addItem("Lista alla ordrar");
                break;
            }
            case "Standardhattar": {
                StandardHat.listAllStandardHats(listModel);
                cmbSearchStandardHats.removeAllItems();
                StandardHat.fillCmbWithAllHatNames(cmbSearchStandardHats);
                lblErrorMessageCategory.setText("");
                txtSearchWord.setEnabled(false);
                txtSearchWord.setText("");
                cmbSearchStandardHats.setVisible(true);
                cmbSearchStandardHats.setEnabled(true);
                btnSearchSpecific.setText("Sök hatt");
                cmbSearchSpecific.removeAllItems();
                cmbSearchSpecific.addItem("Hattnamn");
                cmbSearchSpecific.addItem("Tyg");
                cmbSearchSpecific.addItem("Lista alla standardhattar");
                break;
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbDeleteChosenHat;
    private javax.swing.JButton btnAddNewHatType;
    private javax.swing.JButton btnClearHats;
    private javax.swing.JButton btnCreateNewCustomerFromOrder;
    private javax.swing.JButton btnEditCatergory;
    private javax.swing.JButton btnGeneratePDF;
    private javax.swing.JButton btnRegisterCustomer;
    private javax.swing.JButton btnRegisterFabric;
    private javax.swing.JButton btnRegisterStandardHat;
    private javax.swing.JButton btnSaveOrder;
    private javax.swing.JButton btnSearchSpecific;
    private javax.swing.JButton btn_changePassword;
    private javax.swing.JButton btn_logout;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JComboBox<String> cmbChosenYear;
    private javax.swing.JComboBox<String> cmbSearchSpecific;
    private javax.swing.JComboBox<String> cmbSearchStandardHats;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListAllOrders;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelOrderAddress;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblChooseCategory;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblCustomerNumber;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblErrorCountry;
    private javax.swing.JLabel lblErrorDate;
    private javax.swing.JLabel lblErrorDeliveryAddress;
    private javax.swing.JLabel lblErrorEmpty;
    private javax.swing.JLabel lblErrorMessageCategory;
    private javax.swing.JLabel lblErrorPostAddress;
    private javax.swing.JLabel lblErrorValidateSearchWord;
    private javax.swing.JLabel lblSum;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JList<String> listFoundResults;
    private javax.swing.JPanel panel_createOrder;
    private javax.swing.JPanel panel_register;
    private javax.swing.JPanel panel_search;
    private javax.swing.JPanel panel_start;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtDeliveryAdress;
    private javax.swing.JTextField txtExpectedDate;
    private javax.swing.JTextField txtPostCode;
    private javax.swing.JTextField txtSearchWord;
    // End of variables declaration//GEN-END:variables
}
