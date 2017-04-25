import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by samagbeh on 4/25/17.
 */
public class GUI extends JFrame {

    private JPanel mainPanel;
    private JTextField enterName;
    private JTextField enterRecordTime;
    private JLabel nameLabel;
    private JLabel recordTimeLabel;
    private JButton submitButton;
    private JButton updateButton;
    private JMenuBar menuBar;
    private JPanel menuPanel;

    private JButton deleteButton;

    private JList<CubesRecord> allRecordsList;
    private JScrollPane allRecordsListScrollPane;
    private DefaultListModel<CubesRecord> allRecordsModel;

    private Controller controller;


    GUI(Controller controller) {

        this.controller = controller;  //Store a reference to the controller object.
        // Need this to make requests to the database.

        //Add GUI components
        configureMenus();

        //Configure the list model
        allRecordsModel = new DefaultListModel<CubesRecord>();
        allRecordsList.setModel(allRecordsModel);


        //and mainPanel listeners
        addMainPanelListeners();

        //Regular setup stuff for the window / JFrame
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //Make components except JList invisible
        enterName.setVisible(false);
        enterRecordTime.setVisible(false);
        nameLabel.setVisible(false);
        recordTimeLabel.setVisible(false);
        submitButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);

    }

    private void addMainPanelListeners() {

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Identify what is selected
                CubesRecord cubesRecord = allRecordsList.getSelectedValue();
                if (cubesRecord == null) {
                    JOptionPane.showMessageDialog(GUI.this, "Please select an record to delete");
                } else {
                    controller.delete(cubesRecord);
                    ArrayList<CubesRecord> cubesRecords = controller.getAllData();
                    setListData(cubesRecords);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Read data, send message to database via controller

                String name = enterName.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(GUI.this, "Enter a name");
                    return;
                }
                double solveTime;

                try {
                    solveTime = Double.parseDouble(enterRecordTime.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(GUI.this, "Enter a number for solve time");
                    return;
                }

                CubesRecord cubesRecord = new CubesRecord(name, solveTime);
                controller.addRecordToDatabase(cubesRecord);

                //Clear input JTextFields
                enterName.setText("");
                enterRecordTime.setText("");

                //and request all data from DB to update list
                ArrayList<CubesRecord> allData = controller.getAllData();
                setListData(allData);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Read data, send message to database via controller

                String name = enterName.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(GUI.this, "Enter a name");
                    return;
                }
                double solveTime;

                try {
                    solveTime = Double.parseDouble(enterRecordTime.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(GUI.this, "Enter a number for solve time");
                    return;
                }

                CubesRecord cubesRecord = new CubesRecord(name, solveTime);
                controller.updateRecordInDatabase(cubesRecord);

                //Clear input JTextFields
                enterName.setText("");
                enterRecordTime.setText("");

                //and request all data from DB to update list
                ArrayList<CubesRecord> allData = controller.getAllData();
                setListData(allData);
            }
        });

    }

            //This does the same as the IntelliJ GUI designer.
            private void configureMenus() {

                menuBar = new JMenuBar();
                JMenu menu = new JMenu("Options");
                JMenuItem addARecord = new JMenuItem("Add a Record");
                JMenuItem updateARecord = new JMenuItem("Update a Record");
                JMenuItem deleteARecord = new JMenuItem("Delete a Record");
                JMenuItem quit = new JMenuItem("Quit");

                menuPanel = new JPanel();


                //Initialize the components
                submitButton = new JButton("Add Record");
                enterName = new JTextField();
                enterRecordTime = new JTextField();
                nameLabel = new JLabel("Enter the rubix cube solver's name: ");
                recordTimeLabel = new JLabel("Enter the solve time in seconds: ");

                //and the JList, add it to a JScrollPane
                allRecordsList = new JList<CubesRecord>();
                allRecordsListScrollPane = new JScrollPane(allRecordsList);

                deleteButton = new JButton("Delete");
                updateButton = new JButton("Update Record");

                //Create a JPanel to hold all of the above
                mainPanel = new JPanel();

                //A LayoutManager is in charge of organizing components within a JPanel.
                //BoxLayout can display items in a vertical or horizontal list, (and also other configurations, see JavaDoc)
                BoxLayout layoutMgr = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
                mainPanel.setLayout(layoutMgr);

                //Add listeners for menu items

                addARecord.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        enterName.setVisible(true);
                        enterRecordTime.setVisible(true);
                        nameLabel.setVisible(true);
                        recordTimeLabel.setVisible(true);
                        submitButton.setVisible(true);
                        updateButton.setVisible(false);
                        deleteButton.setVisible(false);


                    }
                });

                deleteARecord.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        enterName.setVisible(false);
                        enterRecordTime.setVisible(false);
                        nameLabel.setVisible(false);
                        recordTimeLabel.setVisible(false);
                        submitButton.setVisible(false);
                        updateButton.setVisible(false);
                        deleteButton.setVisible(true);


                    }
                });

                updateARecord.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        enterName.setVisible(true);
                        enterRecordTime.setVisible(true);
                        nameLabel.setVisible(true);
                        recordTimeLabel.setVisible(true);
                        updateButton.setVisible(true);
                        submitButton.setVisible(false);
                        deleteButton.setVisible(false);


                    }

                });

                quit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });


                //And add the components to the JPanel mainPanel and JPanel menuPanel
                mainPanel.add(nameLabel);
                mainPanel.add(enterName);
                mainPanel.add(recordTimeLabel);
                mainPanel.add(enterRecordTime);
                mainPanel.add(submitButton);
                mainPanel.add(allRecordsListScrollPane);
                mainPanel.add(deleteButton);
                mainPanel.add(updateButton);
                mainPanel.add(menuPanel, BorderLayout.WEST);
                menuPanel.add(menuBar, BorderLayout.WEST);
                menu.add(addARecord);
                menu.add(updateARecord);
                menu.add(deleteARecord);
                menu.add(quit);
                menuBar.add(menu);


            }


            void setListData(ArrayList<CubesRecord> data) {

                //Display data in allDataTextArea
                allRecordsModel.clear();

                for (CubesRecord record : data) {
                    allRecordsModel.addElement(record);
                }

            }

    }
