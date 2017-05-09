package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mirnafayez on 04/05/17.
 */
public class BookInfoForm extends JPanel {

    protected Container content;
    private JLabel ISBN, Title, Publisher, Prod_year, Price, Category, Copies_num, Min_copies, authors;
    public JTextField ISBN_v, titleTF, publisherTF, prodYearTF, priceTF, copiesNumTF, minCopiesTF;
    public JTextArea  authorsTF;
    public JComboBox categoryCB;
    private JPanel JP;
    public JButton okBtn;
    String[] category_arr = {"Science", "Art", "Religion", "Geography", "History"};


    public BookInfoForm() {
        this.setVisible(true);
        this.setBounds(10, 10, Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setLayout(null);

        ISBN = new JLabel(" ISBN ");
        Title = new JLabel(" Title");
        Publisher = new JLabel("Publisher Name");
        Prod_year = new JLabel("Production Year");
        Price = new JLabel("Book Price");
        Category = new JLabel(" Category");
        Copies_num = new JLabel("Number of copies");
        Min_copies = new JLabel("Minimum Number of copies");
        authors = new JLabel("Authors");

        int x = 30;
        int start_x = 98;
        ISBN.setBounds(start_x, 62, 114, 19);
        Title.setBounds(start_x, 62 + 1 * x, 114, 19);
        Publisher.setBounds(start_x, 62 + 2 * x, 114, 19);
        Prod_year.setBounds(start_x, 62 + 3 * x, 114, 19);
        Price.setBounds(start_x, 62 + 4 * x, 114, 19);
        Category.setBounds(start_x, 62 + 5 * x, 114, 19);
        Copies_num.setBounds(start_x, 62 + 6 * x, 114, 19);
        Min_copies.setBounds(start_x, 62 + 7 * x, 114, 19);
        authors.setBounds(start_x, 62 + 8 * x, 114, 19);
        /* */

        categoryCB = new JComboBox(category_arr);
        authorsTF = new JTextArea();

        ISBN_v = new JTextField();
        titleTF = new JTextField();
        publisherTF = new JTextField();
        priceTF = new JTextField();
        prodYearTF = new JTextField();
        copiesNumTF = new JTextField();
        minCopiesTF = new JTextField();


        start_x = 429;

        ISBN_v.setBounds(start_x, 62, 114, 19);
        titleTF.setBounds(start_x, 62 + 1 * x, 114, 19);
        publisherTF.setBounds(start_x, 62 + 2 * x, 114, 19);
        prodYearTF.setBounds(start_x, 62 + 3 * x, 114, 19);
        priceTF.setBounds(start_x, 62 + 4 * x, 114, 19);
        categoryCB.setBounds(start_x, 62 + 5 * x, 114, 19);
        copiesNumTF.setBounds(start_x, 62 + 6 * x, 114, 19);
        minCopiesTF.setBounds(start_x, 62 + 7 * x, 114, 19);
        authorsTF.setBounds(start_x, 62 + 8 * x, 160, 19*3);


        okBtn = new JButton("OK ");
        okBtn.setBounds(start_x, 62 + 11 * x, 114, 19);


        this.add(ISBN);
        this.add(ISBN_v);
        this.add(Title);
        this.add(titleTF);
        this.add(Publisher);
        this.add(publisherTF);
        this.add(Prod_year);
        this.add(prodYearTF);
        this.add(Price);
        this.add(priceTF);
        this.add(Category);
        this.add(categoryCB);
        this.add(Copies_num);
        this.add(copiesNumTF);
        this.add(Min_copies);
        this.add(minCopiesTF);
        this.add(authors);
        this.add(authorsTF);


        this.add(okBtn);


    }
}
