/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.SanPham;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import repository.DBConnect;
/**
 *
 * @author ADMIN
 */
public class SanPhamService_IMPL implements SanPhamService{
    
    Connection conn = DBConnect.getConnection();
    
    @Override
    public List<SanPham> getSanPham(long id) {
       List<SanPham> list = new ArrayList<>();
        try {           
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT *FROM SANPHAM\n"
                    + "where trangthai = 1 ORDER BY IDSANPHAM\n"
                    + "OFFSET "+ (id) +" ROWS\n"
                    + "FETCH NEXT 10 ROWS ONLY ");
            while(r.next()){
                SanPham s = new SanPham();
                s.setId(r.getInt(1));
                s.setMaSp(r.getString(2));
                s.setTenSp(r.getString(3));
                s.setMoTa(r.getString(4));
                s.setTrangThai(r.getBoolean(5));
                list.add(s);
            }
            return list;
            
        }
          
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SanPham> TimSanPham(String TenSp) {
         List<SanPham> list = new ArrayList<>();
        try {
            
            String sql = "Select * from SanPham where TenSp like ?";           
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1,"%" + TenSp + "%");
            ResultSet r = st.executeQuery();
            while(r.next()){
                SanPham s = new SanPham();
                s.setId(r.getInt(1));
                s.setMaSp(r.getString(2));
                s.setTenSp(r.getString(3));
                s.setMoTa(r.getString(4));
                s.setTrangThai(r.getBoolean(5));
                
                list.add(s);
            }
            return list;
            
        }
          
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean AddSanPham(SanPham s) {
        try {
            String sql = "insert into SanPham(MaSp,TenSP,Mota,trangthai) values(?,?,?,?)";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, s.getMaSp());
            pr.setString(2, s.getTenSp());
            pr.setString(3, s.getMoTa());
            pr.setBoolean(4, s.getTrangThai());
            
            pr.execute();
            JOptionPane.showMessageDialog(null, "Thêm thành công");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Thêm thất bại");
            return false;
        }
    }

    @Override
    public boolean UpDateSanPham(SanPham s) {
        try {
            String sql = "Update SanPham set TenSp =?,MoTa = ? where IdSanPham = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            
            pr.setString(1, s.getTenSp());
            pr.setString(2, s.getMoTa());
            pr.setInt(3, s.getId());
            
            JOptionPane.showMessageDialog(null, "Sửa Thành Công");
            pr.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sửa không thành công");
            return false;
        }
    }
    public boolean checkTrungMa(String maSp){
        try {
            String sql = "Select maSP from SanPham where MaSp = ? ";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, maSp);
           
            
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkTrungTen(String tenSp){
        try {
            String sql = "Select  TenSp from SanPham where TenSp = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            
            pr.setString(1, tenSp);
            
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean upDateTrangThai(SanPham s){
        try {
            String sql = "Update SanPham set TrangThai = ? where idSanPham = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setBoolean(1, s.getTrangThai());
            pr.setInt(2, s.getId());
            
            pr.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
