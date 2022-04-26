
/*
/
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hatsystem;

import data.SqlQuery;
import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.JOptionPane;

/**
 *
 * @author Blazl
 */
public class HatSystem {

    private static InfDB idb;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            idb = new InfDB("hatdb", "3306", "hatdb", "hatkey");
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        SqlQuery.setDatabase(idb);

        new Login().setVisible(true);
        //new LoginMenu("Ottoadmin").setVisible(true);
        
        

    }
}
