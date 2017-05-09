package windows.manager;

import driver.Engine;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 03/05/17.
 */
public class ModifyBookUI extends JFrame{

    ModifyBookController modifyBookController ;

    protected Container modifyingContent;

    JTable table;
    protected JPanel modifyBookPanel;
    protected  JTextField ISBN_num;


    public ModifyBookUI() {
        /* this */
        super("MODIFY BOOK IN STORE");
        this.setVisible(true);
        modifyingContent = this.getContentPane();
        this.setSize(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        /*Panel */
        modifyBookPanel = new JPanel();
        modifyBookPanel.setPreferredSize((Dimension) new Dimension(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT));
        modifyBookPanel.setLayout(null);

        /* Scroll Pane*/
        JScrollPane scrollPane = new JScrollPane();
        modifyBookPanel.add(scrollPane);



        /* Table*/
        table = new JTable();
        DefaultTableModel model =(DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new Object[]{ "ISBN","TITLE","Publisher","Prod_year","Price","Category","Copies_num","Min_copies"});//menna


        ResultSet rs=null;
        try {
            rs = Engine.STATEMENT.executeQuery("SELECT * FROM BOOK ");
            while (rs.next()){
                model.insertRow(table.getRowCount(), new Object[]{ rs.getString("ISBN"),rs.getString("Title"),
                        rs.getString("Publisher"),rs.getString("Prod_year"),rs.getString("Price"),
                        rs.getString("Category"),rs.getString("Copies_num"),rs.getString("Min_copies")});  //-------menna
            }
        } catch (SQLException e1) {
            // msg box ;
            JOptionPane.showMessageDialog(null, "Book store has no books to modify", "InfoBox: " + "No Books Message", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }


        table.setEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // add table to Scrollpane
        scrollPane.setViewportView(table);
        scrollPane.setBounds(5, 5,Engine.WINDOW_WIDTH-50, Engine.WINDOW_HEIGHT-100);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.WHITE);

        /**/
        JButton startModifyBtn = new JButton("Modify this BOOK");
        startModifyBtn.setBounds(Engine.WINDOW_WIDTH -300, Engine.WINDOW_HEIGHT-80, 186, 25);
        modifyBookPanel.add(startModifyBtn);

        /**/
        ISBN_num= new JTextField();
        ISBN_num.setBounds(200, Engine.WINDOW_HEIGHT-80, 186, 25);
        modifyBookPanel.add(ISBN_num);


        this.getContentPane().add(modifyBookPanel, BorderLayout.NORTH);

        modifyBookController = new ModifyBookController(this);
        startModifyBtn.addActionListener(modifyBookController.getStartModifyingBtnListener());

    }

    protected void modifying(String ISBN){ /// to be rename
        String selectQuery= "SELECT * FROM BOOK WHERE ISBN=book_ISBN";
        String getAuthorsQuery="SELECT Author_name from AUTHOR WHERE ISBN = book_ISBN ";

        selectQuery=selectQuery.replace("book_ISBN", ISBN);
        getAuthorsQuery=getAuthorsQuery.replace("book_ISBN", ISBN);

        ResultSet rs2= null;

        String authors="";
        try {
            rs2=Engine.STATEMENT.executeQuery(selectQuery);
            rs2.next();

            BookInfoForm bf = new BookInfoForm();
            modifyingContent.add(bf);
            bf.ISBN_v.setText(rs2.getString("ISBN"));
            bf.titleTF.setText(rs2.getString("Title"));
            bf.publisherTF.setText(rs2.getString("Publisher"));
            bf.prodYearTF.setText(rs2.getString("Prod_year"));
            bf.priceTF.setText(rs2.getString("Price"));
            bf.categoryCB.setSelectedItem(rs2.getString("Category"));
            bf.copiesNumTF.setText(rs2.getString("Copies_num"));
            bf.minCopiesTF.setText(rs2.getString("Min_copies"));


            rs2=Engine.STATEMENT.executeQuery(getAuthorsQuery);
            while (rs2.next()){

                authors +=rs2.getString("Author_name");
                authors+= ",";
            }

            if(authors.length()>0) { //-------------------------------------------------------------------------------------->
                authors = authors.substring(0, authors.length() - 1);
                bf.authorsTF.setText(authors);
            }

            ModifyBookController.updateBookBtnListener ac=(ModifyBookController.updateBookBtnListener)modifyBookController.getUpdateBookBtnListener();
            bf.okBtn.addActionListener(ac);
            ac.setISBN(ISBN,bf);


        } catch (SQLException e1) {
            if (e1.getErrorCode() == 1054) { //------------> wrong type + FK
                String l = e1.getMessage().replace("Unknown column ", "");
                l = l.replace(" in 'field list'", "");
            } else if (e1.getErrorCode() == 1452) {
            } else {
            }
        }


    }




}