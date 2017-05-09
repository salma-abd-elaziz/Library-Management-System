package windows.manager;

import driver.Engine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReportUI extends JFrame {

    protected Container reportContent;
    private ReportController reportController;

    private JPanel reportPanel;



    public ReportUI(){
        super("STORE REPORT");
        this.setVisible(true);
        this.setSize(Engine.WINDOW_WIDTH , 650);
        reportContent = this.getContentPane();
        reportContent.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Controller */
        reportController= new ReportController();


        /*Panel */
        reportPanel = new JPanel();
        reportPanel.setBounds(10,10,Engine.WINDOW_WIDTH,650);
        reportPanel.setLayout(null);

        /* Table 1 */
        JTable booksSalesTable = new JTable();
        DefaultTableModel model1 =(DefaultTableModel) booksSalesTable.getModel();
        model1.setColumnIdentifiers(new Object[]{ "Book ISBN","Book Title","Number of Sold Copies"});
        ResultSet rs1=null;
        try {
            rs1=reportController.getSalesForBooks();
            while (rs1.next()){
                model1.insertRow(booksSalesTable.getRowCount(), new Object[]{ rs1.getString("ISBN"),rs1.getString("Title"),rs1.getString("S")});
            }
        } catch (SQLException e1) {
        }

        /* Table 2*/
        JTable topSellingBooksTable = new JTable();
        DefaultTableModel model =(DefaultTableModel) topSellingBooksTable.getModel();
        model.setColumnIdentifiers(new Object[]{ "Book ISBN","Book Title","Number of Sold Copies"});


        ResultSet rs=null;
        try {
            rs=reportController.getTopSellingBooks();
            while (rs.next()){
                model.insertRow(topSellingBooksTable.getRowCount(), new Object[]{ rs.getString("ISBN"),rs.getString("Title"),rs.getString("S")});
            }
        } catch (SQLException e1) {
            // msg box ;
            JOptionPane.showMessageDialog(null, "Book store has no books  ", "InfoBox: " + "No Books Message", JOptionPane.INFORMATION_MESSAGE);
          //  this.dispose();
        }


        /* Table 3 */
        JTable topPurchasingUsersTable= new JTable();
        DefaultTableModel model2 =(DefaultTableModel) topPurchasingUsersTable.getModel();
        model2.setColumnIdentifiers(new Object[]{ "User_name","money"});

        ResultSet rs3=null;
        try {
            rs3 = reportController.getTopPurchasingUsers();
            while (rs3.next()){
                model2.insertRow(topPurchasingUsersTable.getRowCount(), new Object[]{ rs3.getString("User_name"),rs3.getString("total")});
            }
        } catch (SQLException e1) {
            //JOptionPane.showMessageDialog(null, "Book store has no books  ", "InfoBox: " + "No Books Message", JOptionPane.INFORMATION_MESSAGE);
            //this.dispose();
        }



        /* LABELS */
        JLabel totalBooksSalesLabel = new JLabel("Total Books Sales");
        totalBooksSalesLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        totalBooksSalesLabel.setBounds(20,10 ,200,20);

        JLabel totalBooksSalesValueLabel = new JLabel("Total Books Sales");
        totalBooksSalesValueLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        totalBooksSalesValueLabel.setBounds(240,10 ,200,20);

        totalBooksSalesValueLabel.setText("0");
        try {
            totalBooksSalesValueLabel.setText(reportController.getTotalBooksSales());
        }catch(SQLException e){
        }


        JLabel salesForBooksLabel = new JLabel("Books Sales");
        salesForBooksLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        salesForBooksLabel.setBounds(20,35 ,200,20);


        JLabel topPurchasingUsersLabel = new JLabel("Top Purchasing Users");
        topPurchasingUsersLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        topPurchasingUsersLabel.setBounds(20,220 ,200,20);

        JLabel topSellingBooksLabel = new JLabel("Top Selling Books");
        topSellingBooksLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        topSellingBooksLabel.setBounds(20,420 ,200,20);


        /* TABLES' disable editing*/
        booksSalesTable.setEnabled(false);
        topSellingBooksTable.setEnabled(false);
        topPurchasingUsersTable.setEnabled(false);



        JScrollPane booksSalesSP = new JScrollPane();
        booksSalesSP.setViewportView(booksSalesTable);
        booksSalesSP.setBounds(10, 60, Engine.WINDOW_WIDTH-100, 150);

        JScrollPane topPurchasingUsersSP = new JScrollPane();
        topPurchasingUsersSP.setViewportView(topPurchasingUsersTable);
        topPurchasingUsersSP.setBounds(10,250 ,Engine.WINDOW_WIDTH-100, 150);

        JScrollPane topSellingBooks = new JScrollPane();
        topSellingBooks.setViewportView(topSellingBooksTable);
        topSellingBooks.setBounds(10, 445, Engine.WINDOW_WIDTH-100, 150);


        reportPanel.add(totalBooksSalesLabel);
        reportPanel.add(totalBooksSalesValueLabel);

        reportPanel.add(salesForBooksLabel);
        reportPanel.add(booksSalesSP);

        reportPanel.add(topPurchasingUsersLabel);
        reportPanel.add(topPurchasingUsersSP); //->

        reportPanel.add(topSellingBooksLabel);
        reportPanel.add(topSellingBooks); //->


        reportContent.add(reportPanel);

    }
}
