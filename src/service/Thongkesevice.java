/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ThongKe;
import repository.DBConnect;

/**
 *
 * @author admin
 */
public class Thongkesevice implements thongkeInterface {

    Connection conn = DBConnect.getConnection();

    @Override
    public List<ThongKe> getall(String ngaybd, String ngaykt) {
        List<ThongKe> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            if (ngaybd.equals("") || ngaykt.equals("")) {
                String s1l = " SELECT SUM(TONGTIEN),TENSP, HOADON.NGAYDAT from hoadonchitiet \n"
                        + " JOIN hoaDon on hoadonchitiet.IDHOADON = HOADON.IDHOADON\n"
                        + " JOIN SANPHAM ON hoadonchitiet.IDSANPHAM = SANPHAM.IDSANPHAM\n"
                        + " GROUP BY SANPHAM.TENSP, HOADON.NGAYDAT;";

                Statement stm = conn.createStatement();
                rs = stm.executeQuery(s1l);
            } else {
                String sql = " SELECT SUM(TONGTIEN),TENSP,HOADON.NGAYDAT from hoadonchitiet \n"
                        + " JOIN hoaDon on hoadonchitiet.IDHOADON = HOADON.IDHOADON\n"
                        + " JOIN SANPHAM ON hoadonchitiet.IDSANPHAM = SANPHAM.IDSANPHAM\n"
                        + " WHERE  NGAYDAT BETWEEN ?  AND ? \n"
                        + " GROUP BY SANPHAM.TENSP, HOADON.NGAYDAT;";
                PreparedStatement ptm = conn.prepareStatement(sql);
                ptm.setString(1, ngaybd);
                ptm.setString(2, ngaykt);
                rs = ptm.executeQuery();
            }
            while (rs.next()) {
                double doanhthu = rs.getDouble(1);
                String tensp = rs.getString(2);
                 String ngays = rs.getString(3);
                ThongKe tk = new ThongKe();
                tk.setNgaykt(ngays);
                tk.setDoanhthu(doanhthu);
                tk.setTensp(tensp);
                list.add(tk);
            }
            return list;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

