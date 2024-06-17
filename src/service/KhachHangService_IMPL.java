/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import repository.DBConnect;
import java.sql.*;

public class KhachHangService_IMPL implements KhachHangService {

    @Override
    public List<KhachHang> getAllKhachHang() {
        try {
            Connection conn = DBConnect.getConnection();
            List<KhachHang> listKH = new ArrayList<>();
            Statement stm = conn.createStatement();
            String sql = """
                         SELECT [IDKHACHHANG]
                               ,[MAKH]
                               ,[HOTEN]
                               ,[SODIENTHOAI]
                               ,[EMAIL]
                               ,[DIACHI]
                               ,[GIOITINH]
                               ,[TRANGTHAI]
                           FROM [dbo].[KHACHHANG]
                         """;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setSoDienThoai(rs.getString(4));
                kh.setEmail(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setGioiTinh(rs.getBoolean(7));
                kh.setTrangThai(rs.getBoolean(8));
                listKH.add(kh);
            }
            return listKH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addKhachHang(KhachHang kh) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = """
                         INSERT INTO [dbo].[KHACHHANG]
                                      ([MAKH]
                                      ,[HOTEN]
                                      ,[SODIENTHOAI]
                                      ,[EMAIL]
                                      ,[DIACHI]
                                      ,[GIOITINH]
                                      ,[TRANGTHAI])
                                VALUES
                                      (?,?,?,?,?,?,?)
                         """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, kh.getMaKhachHang());
            ptm.setString(2, kh.getTenKhachHang());
            ptm.setString(3, kh.getSoDienThoai());
            ptm.setString(4, kh.getEmail());
            ptm.setString(5, kh.getDiaChi());
            ptm.setBoolean(6, kh.getGioiTinh());
            ptm.setBoolean(7, kh.getTrangThai());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateKhachHang(KhachHang kh) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = """
                         UPDATE [dbo].[KHACHHANG]
                                         SET [MAKH] = ?
                                            ,[HOTEN] = ?
                                            ,[SODIENTHOAI] = ?
                                            ,[EMAIL] = ?
                                            ,[DIACHI] = ?
                                            ,[GIOITINH] = ?
                                            ,[TRANGTHAI] = ?
                                       WHERE IDKHACHHANG = ?
                         """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, kh.getMaKhachHang());
            ptm.setString(2, kh.getTenKhachHang());
            ptm.setString(3, kh.getSoDienThoai());
            ptm.setString(4, kh.getEmail());
            ptm.setString(5, kh.getDiaChi());
            ptm.setBoolean(6, kh.getGioiTinh());
            ptm.setBoolean(7, kh.getTrangThai());
            ptm.setInt(8, kh.getIdKhachHang());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTrangThaiKhachHang(KhachHang kh) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = """
                 UPDATE [dbo].[KHACHHANG]
                      SET [TRANGTHAI] = ?
                       WHERE IDKHACHHANG = ?
                   """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setBoolean(1, kh.getTrangThai());
            ptm.setInt(2, kh.getIdKhachHang());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<KhachHang> timTenKhachHang(String tenKH) {
        Connection conn = DBConnect.getConnection();
        try {
            List<KhachHang> list = new ArrayList();
            String sql = """
                         SELECT * FROM KHACHHANG WHERE HOTEN LIKE ?
                         """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, "%" + tenKH + "%");
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setSoDienThoai(rs.getString(4));
                kh.setEmail(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setGioiTinh(rs.getBoolean(7));
                kh.setTrangThai(rs.getBoolean(8));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<KhachHang> getList2(long id) {
        Connection conn = DBConnect.getConnection();

        try {
            List<KhachHang> list = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT *FROM KHACHHANG\n"
                    + "ORDER BY IDKHACHHANG\n"
                    + "OFFSET " + (id) + " ROWS\n"
                    + "FETCH NEXT 10 ROWS ONLY;");
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setSoDienThoai(rs.getString(4));
                kh.setEmail(rs.getString(5));
                kh.setDiaChi(rs.getString(6));
                kh.setGioiTinh(rs.getBoolean(7));
                kh.setTrangThai(rs.getBoolean(8));
                list.add(kh);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
