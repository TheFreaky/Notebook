package notebook.graphics;

import notebook.db.DataSerializator;
import notebook.db.DataStorage;
import notebook.db.DataStorageDB;
import notebook.entity.EventDataSet;
import notebook.exceptions.AudioCaptureException;
import notebook.exceptions.IllegalDateFormatException;
import notebook.exceptions.IllegalDatesSequenceException;
import notebook.logic.Event;
import notebook.logic.EventList;
import notebook.logic.EventManager;
import notebook.voice.Controller;
import notebook.voice.Recognizer;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Максим on 30.01.2017.
 */
public class Gui {
    private JPanel mainPanel;

    private JPanel listPanel;
    private JList<String> eventsList;

    private JPanel actionPanel;
    private JTextField titleField;
    private JTextField dateField;
    private JTextField descriptionField;
    private JButton addEventButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton editButton;
    private JButton sortButton;
    private JButton recordButton;
    private JButton recognizeButton;

    private DataStorage dataStorage;

    public Gui() {
        dataStorage = new DataStorageDB();
        dataStorage.load();
        refreshList();
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        recognizeButton.setEnabled(false);

        eventsList.addListSelectionListener(getListSelectionListener());
        addEventButton.addActionListener(getAddListener());
        saveButton.addActionListener(getSaveListener());
        editButton.addActionListener(getEditListener());
        deleteButton.addActionListener(getDeleteListener());
        sortButton.addActionListener(getSortListener());
        recordButton.addActionListener(getRecordListener());
        recognizeButton.addActionListener(getRecognizeListener());
    }

    //Listeners
    private ListSelectionListener getListSelectionListener() {
        return e -> {
            int id = eventsList.getSelectedIndex() + 1;

            if (eventsList.getSelectedIndex() >= 0) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);

                Event event = EventList.getInstance().getEvent(id);
                titleField.setText(event.getTitle());
                descriptionField.setText(event.getDescription());
                dateField.setText(event.getDate());
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        };
    }

    private ActionListener getAddListener() {
        return e -> addEvent();
    }

    private ActionListener getEditListener() {
        return e -> {

            int id = eventsList.getSelectedIndex() + 1;

            try {
                EventManager.editEvent(id, titleField.getText(), descriptionField.getText(), dateField.getText());

                refreshList();
            } catch (IllegalDatesSequenceException e1) {
                JOptionPane.showMessageDialog(null, "The end date can't be earlier than " +
                        "the event start date.\n" +
                        "Please enter right dates!");
            } catch (IllegalDateFormatException e1) {
                JOptionPane.showMessageDialog(null, "Please enter right dates!");
            }
        };
    }

    private ActionListener getDeleteListener() {
        return e -> {
            int id = eventsList.getSelectedIndex() + 1;

            EventList.getInstance().deleteEvent(id);
            refreshList();
        };
    }

    private ActionListener getSortListener() {
        final boolean[] isPressed = {false};

        return e -> {
            if (isPressed[0]) {
                EventList.getInstance().sortByDate();
                sortButton.setText("Sort by Title");
                isPressed[0] = false;
            } else {
                EventList.getInstance().sortByName();
                sortButton.setText("Sort by Date");
                isPressed[0] = true;
            }

            refreshList();
        };
    }

    private ActionListener getSaveListener() {
        return e -> dataStorage.save();
    }

    private ActionListener getRecordListener() {
        return e -> {
            try {
                recognizeButton.setEnabled(true);

                Controller.record();
            } catch (AudioCaptureException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
        };
    }

    private ActionListener getRecognizeListener() {
        return e -> {
            Controller.stopCapture();

            try {
                int option = Recognizer.recognize();

                if (option == Recognizer.READ_ALL) {
                    Recognizer.readAll();
                }

                if (option == Recognizer.SAVE) {
                    dataStorage.save();
                }

                recognizeButton.setEnabled(false);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
        };
    }


    private void refreshList() {
        final DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (String event : EventManager.showEvents()) {
            defaultListModel.addElement(event);
        }

        eventsList.setModel(defaultListModel);
    }

    private void addEvent() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String date = dateField.getText();
        try {
            EventManager.addEvent(title, description, date);

            refreshList();
            titleField.setText("");
            descriptionField.setText("");
            dateField.setText("");
        } catch (IllegalDatesSequenceException ex) {
            JOptionPane.showMessageDialog(null, "The end date can't be earlier than " +
                    "the event start date.\n" +
                    "Please enter right dates!");
        } catch (IllegalDateFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter right dates!");
        }
    }

    public void start() {
        JFrame frame = new JFrame("Diary");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMaximumSize(new Dimension(1280, 720));
        mainPanel.setMinimumSize(new Dimension(1280, 720));
        mainPanel.setPreferredSize(new Dimension(1280, 720));
        listPanel = new JPanel();
        listPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(listPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        listPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder("                                   Title                                                                      Description                                                         Date"));
        eventsList = new JList();
        eventsList.setEnabled(true);
        eventsList.setFocusable(false);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        eventsList.setModel(defaultListModel1);
        eventsList.setSelectionMode(2);
        eventsList.setVisibleRowCount(1);
        eventsList.putClientProperty("List.isFileList", Boolean.FALSE);
        scrollPane1.setViewportView(eventsList);
        actionPanel = new JPanel();
        actionPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(actionPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addEventButton = new JButton();
        addEventButton.setText("Add event");
        panel2.add(addEventButton, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        panel3.add(saveButton, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel1.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit");
        panel4.add(editButton, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel1.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(110, -1), new Dimension(110, -1), new Dimension(110, -1), 0, false));
        sortButton = new JButton();
        sortButton.setContentAreaFilled(true);
        sortButton.setFocusable(true);
        sortButton.setHideActionText(false);
        sortButton.setHorizontalTextPosition(0);
        sortButton.setText("Sort by Title");
        panel5.add(sortButton, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel1.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        panel6.add(deleteButton, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder("Voice Control"));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel7.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        recordButton = new JButton();
        recordButton.setText("Record");
        panel8.add(recordButton, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel7.add(panel9, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        recognizeButton = new JButton();
        recognizeButton.setText("Recognize");
        panel9.add(recognizeButton, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel10, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleField = new JTextField();
        panel10.add(titleField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dateField = new JTextField();
        panel10.add(dateField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        descriptionField = new JTextField();
        panel10.add(descriptionField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Title");
        panel10.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Description");
        panel10.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Date");
        panel10.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        label1.setLabelFor(scrollPane1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}