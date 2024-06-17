/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bangiay.view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import repository.DBConnect;
/**
 *
 * @author paine
 */
public class test1 {
      public static void main(String[] args) {
        // Kết nối đến cơ sở dữ liệu
        try (Connection connection = DBConnect.getConnection();) {
            // Tạo câu truy vấn SQL
            String sql = "SELECT * FROM HOADON WHERE TRANGTHAI = 0 ";
            
            // Tạo một PreparedStatement để thực thi câu truy vấn
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Thực thi câu truy vấn và nhận kết quả
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Tạo một DefaultTableModel để lưu trữ dữ liệu cho JTable
                    DefaultTableModel model = new DefaultTableModel();
                    
                    // Lấy metadata của kết quả truy vấn để thiết lập cột cho model
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        model.addColumn(metaData.getColumnName(columnIndex));
                    }
                    
                    // Thêm hàng dữ liệu từ ResultSet vào model
                    while (resultSet.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            rowData[i] = resultSet.getObject(i + 1);
                        }
                        model.addRow(rowData);
                    }
                    
                    // Tạo và hiển thị JTable với dữ liệu từ model
                    JTable table = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame();
                    frame.add(scrollPane);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
