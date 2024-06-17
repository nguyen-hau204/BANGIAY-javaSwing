/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.HoaDonChiTiet;
import java.sql.Connection;
import repository.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class HoaDonChiTietService_IMPL implements HoaDonChiTietService1 {

    private Connection conn = DBConnect.getConnection();
    String select = "SELECT IDHOADONCT,HOADON.MAHOADON,SANPHAMCHITIET.IDSANPHAMCHITIET,TENSP,HOADONCT.SOLUONG , SOSIZE, MAUSAC.TENMAU, DEGIAY.LOAIDEGIAY,CHATLIEU.TENCHATLIEU,DONGIA,THANHTIEN,HOADONCT.TRANGTHAI  FROM HOADONCT\n"
            + "            JOIN HOADON ON HOADON.IDHOADON = HOADONCT.IDHOADON\n"
            + "            JOIN SANPHAMCHITIET ON HOADONCT.IDSANPHAMCHITIET = SANPHAMCHITIET.IDSANPHAMCHITIET\n"
            + "            JOIN SANPHAM ON SANPHAMCHITIET.IDSANPHAM = SANPHAM.IDSANPHAM\n"
            + "            JOIN [SIZE] on SANPHAMCHITIET.IDSIZE = [SIZE].IDSIZE\n"
            + "            JOIN MAUSAC ON SANPHAMCHITIET.IDMAUSAC = MAUSAC.IDMAUSAC\n"
            + "            JOIN THUONGHIEU ON SANPHAMCHITIET.IDTHUONGHIEU = THUONGHIEU.IDTHUONGHIEU\n"
            + "            JOIN DEGIAY ON SANPHAMCHITIET.IDDEGIAY = DEGIAY.IDDEGIAY\n"
            + "            JOIN CHATLIEU ON SANPHAMCHITIET.IDCHATLIEU = CHATLIEU.IDCHATLIEU"
            + "            WHERE HOADONCT.IDHOADON = ?";

    public List<HoaDonChiTiet> getall(int idhd) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            PreparedStatement ptm = conn.prepareCall(select);
            ptm.setInt(1, idhd);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                int idhdct = rs.getInt(1);
                String mahd = rs.getString(2);
                int idspct = rs.getInt(3);
                String tensp = rs.getString(4);
                int soluong = rs.getInt(5);
                int sosize = rs.getInt(6);
                String tenmau = rs.getString(7);
                String degiay = rs.getString(8);
                String chatlieu = rs.getString(9);
                float dongia = rs.getFloat(10);
                Double thanhtien = rs.getDouble(11);
                int trangthai = rs.getInt(12);
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setIdHoaDonCT(idhdct);
                hdct.setMaHoaDon(mahd);
                hdct.setIdSpct(idspct);
                hdct.setTenSP(tensp);
                hdct.setSoLuong(soluong);
                hdct.setSize(sosize);
                hdct.setMauSac(tenmau);
                hdct.setDeGiay(degiay);
                hdct.setChatLieu(chatlieu);
                hdct.setGia(dongia);
                hdct.setThanhTien(thanhtien);
                hdct.setTrangThai(trangthai);
                list.add(hdct);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Boolean add(HoaDonChiTiet hdct,int sltrcsua) {
        try {

            String sql = "DECLARE @idsanpham INT , @dongia FLOAT , @soluong INT , @thanhtien FLOAT, @IDHD VARCHAR(10);\n"
                    + "SET @idsanpham = (SELECT IDSANPHAMCHITIET FROM SANPHAMCHITIET WHERE MASPCT = ?);\n"
                    + "SET @dongia = (SELECT GIA FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham);\n"
                    + "SET @soluong = ?;\n"
                    + "SET @thanhtien = @dongia * @soluong;\n"
                    + "SET @IDHD = (SELECT IDHOADON FROM HOADON WHERE MAHOADON = ?);\n"
                    + "IF EXISTS (SELECT 1 FROM HOADONCT WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham)\n"
                    + "BEGIN\n"
                    + "UPDATE HOADONCT\n"
                    + "SET SOLUONG = @soluong,\n"
                    + "THANHTIEN = THANHTIEN + @thanhtien\n"
                    + "WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham;\n"
                    + "UPDATE SANPHAMCHITIET\n"
                    + "SET SOLUONG = [SANPHAMCHITIET].[SOLUONG] +(  "+(sltrcsua)+" - @soluong ) where IDSANPHAMCHITIET = @idsanpham\n"
                    + "END \n"
                    + "ELSE\n"
                    + "BEGIN\n"
                    + "INSERT INTO HOADONCT (IDHOADON, IDSANPHAMCHITIET, SOLUONG, DONGIA, THANHTIEN, TRANGTHAI)\n"
                    + "VALUES (@IDHD, @idsanpham, @soluong, @dongia, @thanhtien, 1);\n"
                    + "UPDATE SANPHAMCHITIET set soluong = [SANPHAMCHITIET].[SOLUONG] +(  "+(sltrcsua)+" - @soluong ) where IDSANPHAMCHITIET = @idsanpham\n"
                    + "END        \n"
                    + "SELECT * FROM HOADONCT WHERE IDHOADON = @IDHD;\n"
                    + "SELECT * FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham";
//      ?1 mã spct ; ?2 số lượng , ?3 MAHD
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hdct.getMaSanPhamChiTiet());
            ptm.setInt(2, hdct.getSoLuong());
            ptm.setString(3, hdct.getMaHoaDon());
            ptm.executeQuery();
            JOptionPane.showMessageDialog(null, "Thêm thành công");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm thất bại");
            return false;
        }

    }
 public Boolean chon(HoaDonChiTiet hdct,int idhoadon) {
        try {

            String sql = """
                           DECLARE @idsanpham INT , @dongia FLOAT , @soluong INT ,@soluongbandau INT, @thanhtien FLOAT, @IDHD VARCHAR(10);
                         SET @idsanpham = (SELECT IDSANPHAMCHITIET FROM SANPHAMCHITIET WHERE MASPCT = ?);
                         SET @soluong = ?;
                         SET @IDHD = (SELECT IDHOADON FROM HOADON WHERE MAHOADON = ?);
                         SET @dongia = (SELECT GIA FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham);
                         SET @soluongbandau = (SELECT HOADONCT.SOLUONG FROM HOADONCT WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham);
                         SET @thanhtien = @dongia * @soluong;
                         IF EXISTS (SELECT 1 FROM HOADONCT WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham)
                         BEGIN
                             UPDATE HOADONCT
                             SET SOLUONG =  @soluong,
                             THANHTIEN = THANHTIEN + @thanhtien
                             WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham;
                             UPDATE SANPHAMCHITIET
                             SET SOLUONG = [SANPHAMCHITIET].[SOLUONG] +(  @soluongbandau - @soluong )  where IDSANPHAMCHITIET = @idsanpham
                         END 
                         ELSE
                         BEGIN
                             INSERT INTO HOADONCT (IDHOADON, IDSANPHAMCHITIET, SOLUONG, DONGIA, THANHTIEN, TRANGTHAI)
                             VALUES (@IDHD, @idsanpham, @soluong, @dongia, @thanhtien, 1);
                             UPDATE SANPHAMCHITIET set soluong = [SANPHAMCHITIET].[SOLUONG] - @soluong where IDSANPHAMCHITIET = @idsanpham
                         END        
                         SELECT * FROM HOADONCT WHERE IDHOADON = @IDHD;
                         SELECT SOLUONG FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham;
                         """;
//      ?1 mã spct ; ?2 số lượng , ?3 MAHD
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hdct.getMaSanPhamChiTiet());
            ptm.setInt(2, hdct.getSoLuong());
            ptm.setString(3, hdct.getMaHoaDon());
            ptm.executeQuery();
            JOptionPane.showMessageDialog(null, "Thêm thành công");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm thất bại");
            return false;
        }

    }

    public Boolean tru(HoaDonChiTiet hdct,int sltrcsua) {
        try {

            String sql = "DECLARE @idsanpham INT , @dongia FLOAT , @soluong INT , @thanhtien FLOAT, @IDHD VARCHAR(10);\n"
                    + "SET @idsanpham = (SELECT IDSANPHAMCHITIET FROM SANPHAMCHITIET WHERE MASPCT = ?);\n"
                    + "SET @dongia = (SELECT GIA FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham);\n"
                    + "SET @soluong = ?;\n"
                    + "SET @thanhtien = @dongia * @soluong;\n"
                    + "SET @IDHD = (SELECT IDHOADON FROM HOADON WHERE MAHOADON = ?);\n"
                    + "BEGIN\n"
                    + "UPDATE HOADONCT\n"
                    + "SET SOLUONG = @soluong,\n"
                    + "THANHTIEN = THANHTIEN - @thanhtien\n"
                    + "WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham;\n"
                    + "UPDATE SANPHAMCHITIET set soluong = [SANPHAMCHITIET].[SOLUONG] +(  "+(sltrcsua)+" - @soluong ) where IDSANPHAMCHITIET = @idsanpham\n"
                    + "END \n"
                    + "SELECT * FROM HOADONCT WHERE IDHOADON = @IDHD;\n"
                    + "SELECT * FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham";
//      ?1 mã spct ; ?2 số lượng , ?3 MAHD
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hdct.getMaSanPhamChiTiet());
            ptm.setInt(2, hdct.getSoLuong());
            ptm.setString(3, hdct.getMaHoaDon());
            ptm.executeQuery();
            JOptionPane.showMessageDialog(null, "Giảm thành công");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Giảm thất bại");
            return false;
        }

    }
    public Boolean sua(HoaDonChiTiet hdct) {
        try {

            String sql = "DECLARE @idsanpham INT , @dongia FLOAT , @soluong INT , @thanhtien FLOAT, @IDHD VARCHAR(10);\n"
                    + "SET @idsanpham = (SELECT IDSANPHAMCHITIET FROM SANPHAMCHITIET WHERE MASPCT = ?);\n"
                    + "SET @dongia = (SELECT GIA FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham);\n"
                    + "SET @soluong = ?;\n"
                    + "SET @thanhtien = @dongia * @soluong;\n"
                    + "SET @IDHD = (SELECT IDHOADON FROM HOADON WHERE MAHOADON = ?);\n"
                    + "BEGIN\n"
                    + "UPDATE HOADONCT\n"
                    + "SET SOLUONG = @soluong,\n"
                    + "THANHTIEN = THANHTIEN - @thanhtien\n"
                    + "WHERE IDHOADON = @IDHD AND IDSANPHAMCHITIET = @idsanpham;\n"
                    + "UPDATE SANPHAMCHITIET set soluong = soluong - (@soluong - soluong) where IDSANPHAMCHITIET = @idsanpham\n"
                    + "END \n"
                    + "SELECT * FROM HOADONCT WHERE IDHOADON = @IDHD;\n"
                    + "SELECT * FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = @idsanpham";
//      ?1 mã spct ; ?2 số lượng , ?3 MAHD
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hdct.getMaSanPhamChiTiet());
            ptm.setInt(2, hdct.getSoLuong());
            ptm.setString(3, hdct.getMaHoaDon());
            ptm.executeQuery();
            JOptionPane.showMessageDialog(null, "Sửa thành công");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sửa thất bại");
            return false;
        }

    }

    public Boolean xoa(HoaDonChiTiet hdct) {
        try {
            String sql = """
                    DECLARE @idhoadon INT, @idspct INT , @soluong int ;
                     SET @idhoadon = (SELECT IDHOADON FROM HOADON WHERE MAHOADON = ?);
                     SET @idspct = (SELECT IDSANPHAMCHITIET FROM SANPHAMCHITIET WHERE IDSANPHAMCHITIET = ?);
                     set @soluong = ?;
                     DELETE FROM HOADONCT WHERE IDHOADON = @idhoadon AND IDSANPHAMCHITIET = @idspct;
                     UPDATE SANPHAMCHITIET SET SOLUONG = SOLUONG + @soluong WHERE IDSANPHAMCHITIET = @idspct;
                     """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hdct.getMaHoaDon()); 
            ptm.setInt(2, hdct.getIdSpct());        
            System.out.println("Số lượng là " + hdct.getSoLuong());
            ptm.setInt(3, hdct.getSoLuong());
            ptm.executeUpdate();  
            return true;
        } catch (Exception e) {
            e.printStackTrace();  
            return false;
        }
    }

}
