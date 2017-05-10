package driver;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import windows.manager.AddManagerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

/**
 * Created by LENOVO on 4/30/2017.
 */
public class Connector{
    CustomerUI ui;

    public Connector(CustomerUI ui){
        this.ui = ui;
    }

    public ActionListener getEditBtnListener() {
        return new EditBtnListener();
    }
    public ActionListener getAddBtnListener() {
        return new AddBtnListener();
    }
    public ActionListener getManageBtnListener() {
        return new ManageBtnListener();
    }
    public ActionListener getCheckoutBtnListener() {
        return new CheckOutBtnListener();
    }
    public ActionListener getLogoutBtnListener() {
        return new LogOutBtnListener();
    }
    public ActionListener getAddPublisherBtnListener() {
        return new AddPublisherBtnListener();
    }






    private class EditBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            windows.customer.EditUI editUI = new windows.customer.EditUI();
        }
    }

    private class AddBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            windows.customer.AddUI addUI = new windows.customer.AddUI();
        }
    }


    private class ManageBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                 windows.customer.ManageCartUI manageUI = new windows.customer.ManageCartUI();
            } catch (SQLException e1) {
            }
        }
    }

    private class CheckOutBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            windows.customer.CheckOutUI checkOutUI = new windows.customer.CheckOutUI();
        }
    }

    private class LogOutBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Engine.LOGGED_IN_USER = null;
            ui.setVisible(false);
            ui.dispose();
            driver.LogInUI LogIn = new driver.LogInUI();
        }
    }

    public ActionListener getaddBookBtnListener() {
        return new addBookBtnListener();
    }
    public ActionListener getmodifyBtnListener() {
        return new modifyBtnListener();
    }
    public ActionListener getorderBtnListener() {
        return new orderBtnListener();
    }
    public ActionListener getconfOrderBtnListener() {
        return new confOrderBtnListener();
    }

    public ActionListener getviewReportBtnListener() {
        return new viewReportBtnListener();
    }

    public ActionListener getAddMangerBtrListener() {
        return new addMangerBtrListener();
    }


    private class addBookBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            windows.manager.AddBookToStoreUI AddBookToStoreUI = new windows.manager.AddBookToStoreUI();
        }
    }

    private class modifyBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            windows.manager.ModifyBookUI modifyBookUI = new windows.manager.ModifyBookUI();
        }
    }

    private class orderBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            windows.manager.NewOrderUI newOrderUI= new  windows.manager.NewOrderUI();
        }
    }


    private class confOrderBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            windows.manager.OrdersUI ordersUI = new windows.manager.OrdersUI();
        }
    }


    private class viewReportBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JasperReportBuilder report = DynamicReports.report();//a new report
            report
                    .columns(
                            Columns.column("ISBN", "ISBN", DataTypes.stringType()),
                            Columns.column("Title", "title", DataTypes.stringType()),
                            Columns.column("Sum", "S", DataTypes.stringType())
                    )
                    .title(//title of the report
                            Components.text("Total sales for books in the previous month")
                                    .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .pageFooter(Components.pageXofY())//show page number on the page footer
                    .setDataSource("SELECT B.ISBN ,Title ,SUM(Num_books) AS S FROM SHOPPING_CART AS SC , BOOK AS B WHERE B.ISBN = SC.ISBN AND Confirmed = 1 AND Con_date >= date_sub(NOW(), INTERVAL 3 MONTH)  GROUP BY ISBN ORDER BY S DESC",
                            Engine.CONNECTION);


            JasperReportBuilder report2 = DynamicReports.report();
            report2
                    .columns(
                            Columns.column("User", "User_name", DataTypes.stringType()),
                            Columns.column("Total", "total", DataTypes.stringType())
                    )
                    .title(Components.text("Top 5 customers who purchase the most purchase amount in descending order for the last three\n" + "months")
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .pageFooter(Components.pageXofY())//show page number on the page footer
                    .setDataSource("SELECT User_name, SUM(Book_price * Num_books) AS total FROM SHOPPING_CART WHERE Confirmed=1 AND  Con_date >= date_sub(NOW(), INTERVAL 3 MONTH) GROUP BY User_name ORDER BY total DESC limit 5", Engine.CONNECTION);


            JasperReportBuilder report3 = DynamicReports.report();
            report3
                    .columns(
                            Columns.column("ISBN", "ISBN", DataTypes.stringType()),
                            Columns.column("Title", "Title", DataTypes.stringType()),
                            Columns.column("SUM", "S", DataTypes.stringType())

                    )
                    .title(
                            Components.text("Top 10 selling books for the last three months")
                                    .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .pageFooter(Components.pageXofY())//show page number on the page footer
                    .setDataSource("SELECT B.ISBN ,Title ,SUM(Num_books) AS S FROM SHOPPING_CART AS SC , BOOK AS B WHERE B.ISBN= SC.ISBN AND Confirmed=1 AND  Con_date >= date_sub(NOW(), INTERVAL 3 MONTH)  GROUP BY ISBN ORDER  BY S DESC limit 10",
                            Engine.CONNECTION);


            try {
                report.show();
                report.toPdf(new FileOutputStream("reports/report1.pdf"));

                report2.show();
                report2.toPdf(new FileOutputStream("reports/report2.pdf"));

                report3.show();
                report3.toPdf(new FileOutputStream("reports/report3.pdf"));

            } catch (DRException e1) {
            } catch (FileNotFoundException e2) {
            }

        }
    }

    private class AddPublisherBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            windows.manager.AddPublisherUi addPublisherUi = new windows.manager.AddPublisherUi();
        }
    }

    private class addMangerBtrListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            AddManagerUI addMANGERUI = new AddManagerUI();
        }
    }

}
