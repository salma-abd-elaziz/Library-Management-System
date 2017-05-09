package windows.manager;

import driver.Engine;

import javax.swing.*;

/**
 * Created by mirnafayez on 06/05/17.
 */
public class AddPublisherUi extends JFrame {

    protected JTextField publisherNameTF;
    protected JTextField publisherAddressTF;
    protected JTextField publisherPhoneTF;

    protected JButton addPublisherBtn;

    AddPublisherController publisherController;

    public AddPublisherUi() {
        super("ADD A NEW BOOK FOR STORE");
        this.setVisible(true);
        this.setSize(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // Controller
        publisherController = new AddPublisherController(this);

        JLabel publisherNameLabel = new JLabel("Publisher Name : ");
        JLabel publisherAddressLabel = new JLabel("Publisher Address : ");
        JLabel publisherPhoneLabel = new JLabel("Publisher Phone : ");

        publisherNameTF = new JTextField();
        publisherAddressTF = new JTextField();
        publisherPhoneTF = new JTextField();

        addPublisherBtn = new JButton(" Add Publisher");


        int start_x = 100;
        int x = 30;

        publisherNameLabel.setBounds(start_x, 62, 250, 19);
        publisherAddressLabel.setBounds(start_x, 62 + 1 * x, 250, 19);
        publisherPhoneLabel.setBounds(start_x, 62 + 2 * x, 250, 19);

        start_x = 429;

        publisherNameTF.setBounds(start_x, 62, 250, 19);
        publisherAddressTF.setBounds(start_x, 62 + 1 * x, 250, 19);
        publisherPhoneTF.setBounds(start_x, 62 + 2 * x, 250, 19);

        addPublisherBtn.setBounds(start_x, 62 + 3 * x, 250, 19);
        addPublisherBtn.addActionListener(publisherController.getAddPublisherBtnListener());


        this.getContentPane().add(publisherNameLabel);
        this.getContentPane().add(publisherAddressLabel);
        this.getContentPane().add(publisherPhoneLabel);

        this.getContentPane().add(publisherNameTF);
        this.getContentPane().add(publisherAddressTF);
        this.getContentPane().add(publisherPhoneTF);

        this.getContentPane().add(addPublisherBtn);
    }
}
