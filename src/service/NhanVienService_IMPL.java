/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import repository.DBConnect;

/**
 *
 * @author User
 */
public class NhanVienService_IMPL implements NhanVienService {

    @Override
    public List<NhanVien> getNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        try {
            Connection conn = DBConnect.getConnection();
            Statement stm = conn.createStatement();
            String sql = "SELECT IDNHANVIEN, MANV, TENNV, TENDANGNHAP, MATKHAU, GIOITINH, EMAIL, SODIENTHOAI, IDCHUCVU, LUONG, TRANGTHAI FROM NhanVien where TRANGTHAI = 1";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setIdNV(rs.getInt(1));
                nv.setMaNV(rs.getString(2));
                nv.setTenNV(rs.getString(3));
                nv.setTenDN(rs.getString(4));
                nv.setMatKhau(rs.getString(5));
                nv.setgTinh(rs.getBoolean(6));
                nv.setEmail(rs.getString(7));
                nv.setSdt(rs.getString(8));
                nv.setIdChucVu(rs.getInt(9));
                nv.setLuong(rs.getInt(10));
                nv.setTt(rs.getBoolean(11));

                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NhanVien> gettimNhanVien(String ma) {
        try {
            List<NhanVien> list = new ArrayList<>();
            Connection conn = DBConnect.getConnection();
            String sql = "SELECT* FROM NHANVIEN WHERE MANV LIKE ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + ma + "%");
            ResultSet r = st.executeQuery();
            while (r.next()) {
                NhanVien nv = new NhanVien();

                nv.setIdNV(r.getInt(1));
                nv.setMaNV(r.getString(2));
                nv.setTenNV(r.getString(3));
                nv.setTenDN(r.getString(4));
                nv.setMatKhau(r.getString(5));
                nv.setgTinh(r.getBoolean(6));
                nv.setEmail(r.getString(7));
                nv.setSdt(r.getString(8));
                nv.setIdChucVu(r.getInt(9));
                nv.setLuong(r.getInt(10));
                nv.setTt(r.getBoolean(11));

                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean AddNhanVien(NhanVien nv) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO NHANVIEN (MANV, TENNV, TENDANGNHAP, MATKHAU, GIOITINH, EMAIL, SODIENTHOAI, IDCHUCVU, LUONG, TRANGTHAI) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, nv.getMaNV());
            ptm.setString(2, nv.getTenNV());
            ptm.setString(3, nv.getTenDN());
            ptm.setString(4, nv.getMatKhau());
            ptm.setBoolean(5, nv.getgTinh());
            ptm.setString(6, nv.getEmail());
            ptm.setString(7, nv.getSdt());
            ptm.setInt(8, nv.getIdChucVu());
            ptm.setInt(9, nv.getLuong());
            ptm.setBoolean(10, nv.getTt());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateNhanVien(NhanVien nv) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE NHANVIEN SET TENNV = ?, TENDANGNHAP = ?, MATKHAU = ?, GIOITINH = ?, EMAIL = ?, SODIENTHOAI = ?, IDCHUCVU = ?, LUONG = ?, TRANGTHAI = ? WHERE MANV = ?";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, nv.getTenNV());
            ptm.setString(2, nv.getTenDN());
            ptm.setString(3, nv.getMatKhau());
            ptm.setBoolean(4, nv.getgTinh());
            ptm.setString(5, nv.getEmail());
            ptm.setString(6, nv.getSdt());
            ptm.setInt(7, nv.getIdChucVu());
            ptm.setInt(8, nv.getLuong());
            ptm.setBoolean(9, nv.getTt());
            ptm.setString(10, nv.getMaNV());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteNhanVien(NhanVien nv) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "DELETE FROM NHANVIEN WHERE MANV = ?";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, nv.getMaNV());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTrangThaiNhanVien(NhanVien nv) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE NHANVIEN SET TRANGTHAI = ? WHERE IDNHANVIEN = ?";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setBoolean(1, nv.getTt());
            ptm.setInt(2, nv.getIdNV());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<NhanVien> getListPT(long id) {
        try {
            List<NhanVien> list = new ArrayList<>();
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT *FROM NHANVIEN\n"
                    + "ORDER BY IDNHANVIEN\n"
                    + "OFFSET " + (id) + " ROWS\n"
                    + "FETCH NEXT 10 ROWS ONLY;");
            while (r.next()) {
                NhanVien s = new NhanVien();
                s.setIdNV(r.getInt(1));
                s.setMaNV(r.getString(2));
                s.setTenNV(r.getString(3));
                s.setTenDN(r.getString(4));
                s.setMatKhau(r.getString(5));
                s.setgTinh(r.getBoolean(6));
                s.setEmail(r.getString(7));
                s.setSdt(r.getString(8));
                s.setIdChucVu(r.getInt(9));
                s.setLuong(r.getInt(10));
                s.setTt(r.getBoolean(11));
                list.add(s);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean checkTrungMa(String manv){
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "Select maNV from NhanVien where Manv = ? ";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, manv);
           
            
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
