package windows.customer;

import driver.Engine;
import windows.manager.BookInfoForm;
import windows.manager.ModifyBookController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by LENOVO on 5/1/2017.
 */
public class AddUI extends JFrame {

    private JTable table;
    protected windows.manager.BookInfoForm modifyingBookPanel;


    private final String ICON_PATH = "";
    AddController addController;
    ButtonGroup bG;
    JTextPane textPane;
    DefaultTableModel model;

    public AddUI(){
        this.setBounds(100, 100, 759, 578);
        this.getContentPane().setLayout(null);

        addController = new AddController(this);

        bG = new ButtonGroup();

        JRadioButton rdbtnIsbn = new JRadioButton("ISBN");
        rdbtnIsbn.setBounds(24, 50, 149, 23);
        this.getContentPane().add(rdbtnIsbn);

        JRadioButton rdbtnTitle = new JRadioButton("Title");
        rdbtnTitle.setBounds(24, 77, 149, 23);
        this.getContentPane().add(rdbtnTitle);

        JRadioButton rdbtnAuthor = new JRadioButton("Author");
        rdbtnAuthor.setBounds(24, 104, 149, 23);
        this.getContentPane().add(rdbtnAuthor);

        JRadioButton rdbtnPublisher = new JRadioButton("Publisher");
        rdbtnPublisher.setBounds(24, 131, 149, 23);
        this.getContentPane().add(rdbtnPublisher);

        JRadioButton rdbtnCategory = new JRadioButton("Category");
        rdbtnCategory.setBounds(24, 158, 149, 23);
        this.getContentPane().add(rdbtnCategory);

        textPane = new JTextPane();
        textPane.setBounds(92, 12, 316, 21);
        this.getContentPane().add(textPane);

        JLabel lblNewLabel = new JLabel("resources/pics/sicon");
        lblNewLabel.setBounds(50, 0, 70, 42);
        this.getContentPane().add(lblNewLabel);


        bG.add(rdbtnIsbn);
        bG.add(rdbtnTitle);
        bG.add(rdbtnAuthor);
        bG.add(rdbtnPublisher);
        bG.add(rdbtnCategory);

        model = new DefaultTableModel();
        table = new JTable(model);

        JScrollPane js = new JScrollPane();
        js.setViewportView(table);
        js.setBounds(204, 108, 446, 375);


        model.addColumn("Book");
        model.addColumn("Price");
        model.addColumn("No. Of Copies");
        model.addColumn("ISBN");

        table.setBounds(204, 108, 446, 375);
        this.getContentPane().add(js);
        js.setVisible(true);

        JLabel lblSearchResults = new JLabel("Search Results");
        lblSearchResults.setBounds(380, 81, 106, 15);
        this.getContentPane().add(lblSearchResults);

        JButton btnAdd = new JButton("Add to cart");
        btnAdd.setBounds(390, 495, 117, 25);
        this.getContentPane().add(btnAdd);

        setBtnIcon(lblNewLabel);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(465, 12, 117, 25);
        this.getContentPane().add(btnSearch);


        JButton modifyBtn = new JButton("Modify Book");
        modifyBtn.setBounds(200, 495, 200, 25);

        if(Engine.IS_MANAGER == 1)
            this.getContentPane().add(modifyBtn);

        modifyBtn.addActionListener(addController.getStartModifyingBtnListener());
        btnSearch.addActionListener(addController.getSearchBtnListener());
        btnAdd.addActionListener(addController.getAddBtnListener());

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private boolean setBtnIcon(JLabel lbl) {
        return setBtnIcon(lbl, lbl.getText());
    }

    private boolean setBtnIcon(JLabel btn, String iconName) {
        try {
            BufferedImage master = ImageIO.read(new File(ICON_PATH + iconName.toLowerCase() + ".png"));
            Image scaled = master.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
            btn.setText("");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getChoice(){
        String selectedBtn = (getSelectedButtonText(bG));
        if(selectedBtn == null) {
            JOptionPane.showMessageDialog(null, "Choose search criteria");
        }
        return selectedBtn;
    }


    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public String getSearchValue(){
        String searchValue = textPane.getText();
        if(searchValue.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter search value");
        }
        return searchValue;
    }

    public DefaultTableModel getModel(){
        return model;
    }

    public JTable getTable(){
        return table;
    }

    public String getNumberOfCopies(){
        return JOptionPane.showInputDialog("Enter number of copies");
    }


    protected void modifying(String ISBN) { /// to be rename
        String selectQuery = "SELECT * FROM BOOK WHERE ISBN=book_ISBN";
        String getAuthorsQuery="SELECT Author_name from AUTHOR WHERE ISBN = book_ISBN ";

        selectQuery = selectQuery.replace("book_ISBN", ISBN);
        getAuthorsQuery=getAuthorsQuery.replace("book_ISBN", ISBN);

        ResultSet rs2= null;

        String authors="";

        try {
            rs2 = Engine.STATEMENT.executeQuery(selectQuery);
            rs2.next();


            windows.manager.BookInfoForm bf = new BookInfoForm();
            modifyingBookPanel=bf;
            this.getContentPane().add(bf);
            bf.ISBN_v.setText(rs2.getString("ISBN"));
            bf.titleTF.setText(rs2.getString("Title"));
            bf.publisherTF.setText(rs2.getString("Publisher"));
            //bf.titleTF.setText(rs2.getString(4));
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

            AddController.updateBookBtnListener ac = (AddController.updateBookBtnListener) addController.getUpdateBookBtnListener();
            bf.okBtn.addActionListener(ac);


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

