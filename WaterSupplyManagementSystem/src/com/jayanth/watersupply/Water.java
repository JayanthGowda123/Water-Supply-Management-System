package com.jayanth.watersupply;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class Water {

    private JTextField nameData;
    private JTextField locationData;
    private JTable table1;
    private JButton ADDRECORDButton;
    private JButton UPDATERECORDButton;
    private JPanel waterPanel;
    private JComboBox<String> quantity;
    private JComboBox<String> litres;

    JFrame waterF;
    public Water() {

        // Frame
        waterF = new JFrame("Water Supply Management System");
        waterF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waterF.setSize(700, 500);
        waterF.setLocationRelativeTo(null);

        // Panel
        waterPanel = new JPanel();
        waterPanel.setLayout(null);

        // Labels
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 30, 80, 25);
        waterPanel.add(nameLabel);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(320, 30, 80, 25);
        waterPanel.add(locationLabel);

        JLabel litresLabel = new JLabel("Litres:");
        litresLabel.setBounds(50, 70, 80, 25);
        waterPanel.add(litresLabel);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(320, 70, 80, 25);
        waterPanel.add(quantityLabel);

        // TextFields
        nameData = new JTextField();
        nameData.setBounds(130, 30, 150, 25);
        waterPanel.add(nameData);

        locationData = new JTextField();
        locationData.setBounds(400, 30, 150, 25);
        waterPanel.add(locationData);

        // ComboBoxes
        litres = new JComboBox<>(new String[]{"10", "20", "30", "40"});
        litres.setBounds(130, 70, 150, 25);
        waterPanel.add(litres);

        quantity = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        quantity.setBounds(400, 70, 150, 25);
        waterPanel.add(quantity);

        // Buttons
        ADDRECORDButton = new JButton("Add Record");
        ADDRECORDButton.setBounds(180, 110, 140, 30);
        waterPanel.add(ADDRECORDButton);

        UPDATERECORDButton = new JButton("Update Record");
        UPDATERECORDButton.setBounds(350, 110, 160, 30);
        waterPanel.add(UPDATERECORDButton);

        // Table
        table1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(50, 170, 600, 250);
        waterPanel.add(scrollPane);

        // Add panel to frame
        waterF.setContentPane(waterPanel);
        waterF.setVisible(true);

        // Load data & listeners
        tableData();
        addListeners();
    }

    // ---------------- LISTENERS ----------------
    private void addListeners() {

        ADDRECORDButton.addActionListener(e -> {
            if (nameData.getText().isEmpty() || locationData.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }

            try {
                String sql = "INSERT INTO water (NAME, LOCATION, LITRES, QUANTITY, PRICE) VALUES (?,?,?,?,?)";
                Connection connection = DriverManager.getConnection(
                		"jdbc:mysql://localhost:3306/intern?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","wateruser","water123");

                int price = Integer.parseInt(litres.getSelectedItem().toString())
                        * Integer.parseInt(quantity.getSelectedItem().toString()) * 10;

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nameData.getText());
                ps.setString(2, locationData.getText());
                ps.setString(3, litres.getSelectedItem().toString());
                ps.setString(4, quantity.getSelectedItem().toString());
                ps.setInt(5, price);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Added Successfully");

                nameData.setText("");
                locationData.setText("");
                tableData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        UPDATERECORDButton.addActionListener(e -> {
            try {
                int price = Integer.parseInt(litres.getSelectedItem().toString())
                        * Integer.parseInt(quantity.getSelectedItem().toString()) * 10;

                String sql = "UPDATE water SET LOCATION=?, LITRES=?, QUANTITY=?, PRICE=? WHERE NAME=?";
                Connection connection = DriverManager.getConnection(
                		"jdbc:mysql://localhost:3306/intern?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","wateruser","water123");

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, locationData.getText());
                ps.setString(2, litres.getSelectedItem().toString());
                ps.setString(3, quantity.getSelectedItem().toString());
                ps.setInt(4, price);
                ps.setString(5, nameData.getText());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Updated");

                tableData();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                nameData.setText(table1.getValueAt(row, 0).toString());
                locationData.setText(table1.getValueAt(row, 1).toString());
            }
        });
    }

    // ---------------- TABLE DATA ----------------
    public void tableData() {
        try {
            String sql = "SELECT * FROM water";
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/intern?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","wateruser","water123");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            table1.setModel(buildTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        Vector<Vector<Object>> data = new Vector<>();

        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
	
