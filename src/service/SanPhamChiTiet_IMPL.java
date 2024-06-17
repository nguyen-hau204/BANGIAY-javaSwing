/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.SanPham;
import model.SanPhamChiTiet;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import repository.DBConnect;
/**
 *
 * @author ADMIN
 */
public class SanPhamChiTiet_IMPL implements SanPhamChiTietService{
    Connection con = DBConnect.getConnection();
    
    @Override
    public List<SanPhamChiTiet> getSpCt(long id, String idsp) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        ResultSet r;
        try {
            if(String.valueOf(idsp).equalsIgnoreCase("")){
                String sql = "select SANPHAMCHITIET.IDSANPHAMCHITIET,SANPHAMCHITIET.MASPCT,SANPHAMCHITIET.IDSANPHAM,LoaiGiay.TenLoai,Size.Sosize,ThuongHieu.TenThuongHieu,DeGiay.LoaiDeGiay,MauSac.TenMau,ChatLieu.TenChatLieu,SANPHAMCHITIET.GIA,SANPHAMCHITIET.SOLUONG,SANPHAMCHITIET.TRANGTHAI from SANPHAMCHITIET "
                                                       + " join MAUSAC on MAUSAC.IDMAUSac = SANPHAMCHITIET.IDMAUSac \n" +
"							   join SIZE on SIZE.IDSIZE = SANPHAMCHITIET.IDSIZE \n" +
"							   join LOAIGIAY on LOAIGIAY.IDLOAIGIAY = SANPHAMCHITIET.IDLOAIGIAY \n" +
"							   join THUONGHIEU on THUONGHIEU.IDTHUONGHIEU = SANPHAMCHITIET.IDTHUONGHIEU \n" +
"							   join CHATLIEU on CHATLIEU.IDCHATLIEU = SANPHAMCHITIET.IDCHATLIEU \n" +
"							   join DEGIAY on DEGIAY.IDDEGIAY = SANPHAMCHITIET.IDDEGIAY Where SANPHAMCHITIET.TrangThai = 1 ORDER BY SANPHAMCHITIET.IDSANPHAMCHITIET OFFSET  " + (id) + "ROWS FETCH NEXT 10 ROWS ONLY ";
            Statement st = con.createStatement();
             r = st.executeQuery(sql);
            }else{       
            String sql = "select SANPHAMCHITIET.IDSANPHAMCHITIET,SANPHAMCHITIET.MASPCT,SANPHAMCHITIET.IDSANPHAM,LoaiGiay.TenLoai,Size.Sosize,ThuongHieu.TenThuongHieu,DeGiay.LoaiDeGiay,MauSac.TenMau,ChatLieu.TenChatLieu,SANPHAMCHITIET.GIA,SANPHAMCHITIET.SOLUONG,SANPHAMCHITIET.TRANGTHAI from SANPHAMCHITIET "
                                                       + " join MAUSAC on MAUSAC.IDMAUSac = SANPHAMCHITIET.IDMAUSac \n" +
"							   join SIZE on SIZE.IDSIZE = SANPHAMCHITIET.IDSIZE \n" +
"							   join LOAIGIAY on LOAIGIAY.IDLOAIGIAY = SANPHAMCHITIET.IDLOAIGIAY \n" +
"							   join THUONGHIEU on THUONGHIEU.IDTHUONGHIEU = SANPHAMCHITIET.IDTHUONGHIEU \n" +
"							   join CHATLIEU on CHATLIEU.IDCHATLIEU = SANPHAMCHITIET.IDCHATLIEU \n" +
"							   join DEGIAY on DEGIAY.IDDEGIAY = SANPHAMCHITIET.IDDEGIAY where SanPhamChiTiet.IDSanPham = ? AND SanPhamChiTiet.TrangThai = 1";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idsp);
             r = st.executeQuery();
            }
            while(r.next()){
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setIdSpct(r.getInt(1));
                spct.setMaSpct(r.getString(2));
                spct.setIdsp(r.getInt(3));
                spct.setLoaiGiay(r.getString(4));
                spct.setSize(r.getString(5));
                spct.setThuongHieu(r.getString(6));
                spct.setDeGiay(r.getString(7));
                spct.setMauSac(r.getString(8));
                spct.setChatLieu(r.getString(9));
                spct.setGia(r.getDouble(10));
                spct.setSoLuongSpct(r.getInt(11));
                spct.setTrangThaiSpct(r.getBoolean(12));
                
                list.add(spct);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<SanPhamChiTiet> getSPCTBH(long id){
        List<SanPhamChiTiet> list = new ArrayList<>();
          try {
            String sql = "select SANPHAMCHITIET.IDSANPHAMCHITIET,SANPHAMCHITIET.MASPCT,SANPHAMCHITIET.IDSANPHAM,LoaiGiay.TenLoai,Size.Sosize,ThuongHieu.TenThuongHieu,DeGiay.LoaiDeGiay,MauSac.TenMau,ChatLieu.TenChatLieu,SANPHAMCHITIET.GIA,SANPHAMCHITIET.SOLUONG,SANPHAMCHITIET.TRANGTHAI from SANPHAMCHITIET "
                                                       + " join MAUSAC on MAUSAC.IDMAUSac = SANPHAMCHITIET.IDMAUSac \n" +
"							   join SIZE on SIZE.IDSIZE = SANPHAMCHITIET.IDSIZE \n" +
"							   join LOAIGIAY on LOAIGIAY.IDLOAIGIAY = SANPHAMCHITIET.IDLOAIGIAY \n" +
"							   join THUONGHIEU on THUONGHIEU.IDTHUONGHIEU = SANPHAMCHITIET.IDTHUONGHIEU \n" +
"							   join CHATLIEU on CHATLIEU.IDCHATLIEU = SANPHAMCHITIET.IDCHATLIEU \n" +
"							   join DEGIAY on DEGIAY.IDDEGIAY = SANPHAMCHITIET.IDDEGIAY Where SANPHAMCHITIET.TrangThai = 1 and soluong > 0 ORDER BY SANPHAMCHITIET.IDSANPHAMCHITIET OFFSET  " + (id) + "ROWS FETCH NEXT 10 ROWS ONLY ";
            Statement st = con.createStatement();          
            ResultSet r = st.executeQuery(sql);
            while(r.next()){
                SanPhamChiTiet spct = new SanPhamChiTiet();
                
                spct.setIdSpct(r.getInt(1));
                spct.setMaSpct(r.getString(2));
                spct.setIdsp(r.getInt(3));
                spct.setLoaiGiay(r.getString(4));
                spct.setSize(r.getString(5));
                spct.setThuongHieu(r.getString(6));
                spct.setDeGiay(r.getString(7));
                spct.setMauSac(r.getString(8));
                spct.setChatLieu(r.getString(9));
                spct.setGia(r.getDouble(10));
                spct.setSoLuongSpct(r.getInt(11));
                spct.setTrangThaiSpct(r.getBoolean(12));
                
                list.add(spct);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    
    }
    
    public String getmau(String mau){
        try {
            String sql = "select IDMAUSAC FROM MAUSAC WHERE TENMAU = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, mau);
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return r.getString("IDMAUSAC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getLoaiGiay(String lg){
        try {
            String sql = "  SELECT TOP (1000) [IDLOAIGIAY]    \n" +
                            "FROM [PRO1041S].[dbo].[LOAIGIAY]\n" +
                            "WHERE [TENLOAI] = ?;";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, lg);
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return r.getString("IDLOAIGIAY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getSize(String size){
        try {
            String sql = "select IDsize FROM SIZE WHERE soSize = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, size);
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return r.getString("IDsize");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getThuongHieu(String th){
        try {
            String sql = "select IDThuongHieu FROM ThuongHieu WHERE TENThuongHieu = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, th);
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return r.getString("IDThuongHieu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getDeGiay(String deGiay){
        try {
            String sql = "select IDdeGiay FROM DEGIAY WHERE LoaiDeGiay = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, deGiay);
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return r.getString("idDeGiay");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getChatLieu(String chatLieu){
        try {
            String sql = "select IDCHATLIEU FROM CHATLIEU WHERE TenChatLieu = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, chatLieu);
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return r.getString("IDCHATLIEU");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean addSpct(SanPhamChiTiet s) {
        try {
            String sql = "Insert into SanPhamChiTiet(MAspct,IDSANPHAM,IDLOAIGIAY,IDSIZE,IDTHUONGHIEU,IDDEGIAY,IDMAUSAC,IDCHATLIEU,GIA,SOLuong,TRANgThai) values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pr = con.prepareStatement(sql);  
            pr.setString(1, s.getMaSpct());
            pr.setInt(2, s.getIdsp());
            String loaiGiay = getLoaiGiay(s.getLoaiGiay());
              pr.setString(3, loaiGiay);
            String size = getSize(s.getSize());
               pr.setString(4, size);
            String thuongHieu = getThuongHieu(s.getThuongHieu());
               pr.setString(5, thuongHieu);
            String deGiay = getDeGiay(s.getDeGiay());
               pr.setString(6, deGiay);
            String mau = getmau(s.getMauSac());
               pr.setString(7, mau);
            String chatLieu = getChatLieu(s.getChatLieu());
               pr.setString(8, chatLieu);
               pr.setDouble(9, s.getGia());
               pr.setInt(10, s.getSoLuongSpct());
               pr.setBoolean(11, s.getTrangThaiSpct());
                      
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
    public boolean update(SanPhamChiTiet s) {
        try {
            String sql = "UPdate SanPhamChiTiet set idLoaiGiay = ?, idsize = ?,idThuongHieu = ?,iddeGiay = ?,idmausac = ?,idChatLieu = ?,Gia = ?,soLuong = ? where IDSanPhamChiTiet = ?";
            PreparedStatement pr = con.prepareStatement(sql);  
            
            
            
           String mau = getmau(s.getMauSac());
            pr.setString(5, mau);
           String size = getSize(s.getSize());
            pr.setString(2, size);
           String LoaiGiay = getLoaiGiay(s.getLoaiGiay());
            pr.setString(1, LoaiGiay);
           String Th = getThuongHieu(s.getThuongHieu());
            pr.setString(3, Th); 
           String deGiay = getDeGiay(s.getDeGiay());
            pr.setString(4, deGiay);
           String chatLieu = getChatLieu(s.getChatLieu());
            pr.setString(6, chatLieu);
            pr.setDouble(7, s.getGia());
            pr.setInt(8, s.getSoLuongSpct());   
            pr.setInt(9, s.getIdSpct());
            
            
            pr.execute();
            JOptionPane.showMessageDialog(null, "Sua thành công");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sua thất bại");
            return false;
        }
    }

    @Override
    public boolean doiTrangThaiSpct(SanPhamChiTiet s) {
         try {
            String sql = "Update SanPhamChiTiet set TrangThai = ? where idSanPhamChiTiet = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setBoolean(1, s.getTrangThaiSpct());
            pr.setInt(2, s.getIdSpct());
            
            pr.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getcboLoaiGiay() {
        List<String> list = new ArrayList<>();      
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select TenLoai from LoaiGiay");
            while(r.next()){
                String mau = r.getString(1);
                list.add(mau);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getcboThuongHieu() {
       List<String> list = new ArrayList<>();      
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select TenThuongHieu from ThuongHieu");
            while(r.next()){
                String mau = r.getString(1);
                list.add(mau);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getcboChatLieu() {
        List<String> list = new ArrayList<>();      
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select TenChatLieu from ChatLieu");
            while(r.next()){
                String mau = r.getString(1);
                list.add(mau);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getcboDeGiay() {
        List<String> list = new ArrayList<>();      
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select LoaiDeGiay from degiay");
            while(r.next()){
                String mau = r.getString(1);
                list.add(mau);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getcboMauSac() {
        List<String> list = new ArrayList<>();      
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select TenMau from MauSac");
            while(r.next()){
                String mau = r.getString(1);
                list.add(mau);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getcboSize() {
       List<String> list = new ArrayList<>();      
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select Sosize from size");
            while(r.next()){
                String mau = r.getString(1);
                list.add(mau);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SanPhamChiTiet> timSanPhamChiTiet(String maSpct) {
        List<SanPhamChiTiet> list = new ArrayList<>();
        try {
            String sql = "select SANPHAMCHITIET.IDSANPHAMCHITIET,SANPHAMCHITIET.MASPCT,SANPHAMCHITIET.IDSANPHAM,LoaiGiay.TenLoai,Size.Sosize,ThuongHieu.TenThuongHieu,DeGiay.LoaiDeGiay,MauSac.TenMau,ChatLieu.TenChatLieu,SANPHAMCHITIET.GIA,SANPHAMCHITIET.SOLUONG,SANPHAMCHITIET.TRANGTHAI from SANPHAMCHITIET "
                                                       + " join MAUSAC on MAUSAC.IDMAUSac = SANPHAMCHITIET.IDMAUSac \n" +
"							   join SIZE on SIZE.IDSIZE = SANPHAMCHITIET.IDSIZE \n" +
"							   join LOAIGIAY on LOAIGIAY.IDLOAIGIAY = SANPHAMCHITIET.IDLOAIGIAY \n" +
"							   join THUONGHIEU on THUONGHIEU.IDTHUONGHIEU = SANPHAMCHITIET.IDTHUONGHIEU \n" +
"							   join CHATLIEU on CHATLIEU.IDCHATLIEU = SANPHAMCHITIET.IDCHATLIEU \n" +
"							   join DEGIAY on DEGIAY.IDDEGIAY = SANPHAMCHITIET.IDDEGIAY where SANPHAMCHITIET.MASPCT like ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,"%" + maSpct + "%");
            ResultSet r = st.executeQuery();
            while(r.next()){
                SanPhamChiTiet spct = new SanPhamChiTiet();
                
                spct.setIdSpct(r.getInt(1));
                spct.setMaSpct(r.getString(2));
                spct.setIdsp(r.getInt(3));
                spct.setLoaiGiay(r.getString(4));
                spct.setSize(r.getString(5));
                spct.setThuongHieu(r.getString(6));
                spct.setDeGiay(r.getString(7));
                spct.setMauSac(r.getString(8));
                spct.setChatLieu(r.getString(9));
                spct.setGia(r.getDouble(10));
                spct.setSoLuongSpct(r.getInt(11));
                spct.setTrangThaiSpct(r.getBoolean(12));
                
                list.add(spct);
            }
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SanPhamChiTiet> locSanPhamChiTiet(String loaiGiay, String thuongHieu, String chatLieu, String deGiay, String mauSac) {
         try {
            List<SanPhamChiTiet> list = new ArrayList<>();
            String sql = "select SANPHAMCHITIET.IDSANPHAMCHITIET,SANPHAMCHITIET.MASPCT,SANPHAMCHITIET.IDSANPHAM,LoaiGiay.TenLoai,Size.Sosize,ThuongHieu.TenThuongHieu,DeGiay.LoaiDeGiay,MauSac.TenMau,ChatLieu.TenChatLieu,SANPHAMCHITIET.GIA,SANPHAMCHITIET.SOLUONG,SANPHAMCHITIET.TRANGTHAI from SANPHAMCHITIET  "
                                                       + " join MAUSAC on MAUSAC.IDMAUSAC = SANPHAMCHITIET.IDMAUSAC \n" +
"							   join SIZE on SIZE.IDSIZE = SANPHAMCHITIET.IDSIZE \n" +
"							   join LOAIGIAY on LOAIGIAY.IDLOAIGIAY = SANPHAMCHITIET.IDLOAIGIAY \n" +
"							   join THUONGHIEU on THUONGHIEU.IDTHUONGHIEU = SANPHAMCHITIET.IDTHUONGHIEU \n" +
"							   join CHATLIEU on CHATLIEU.IDCHATLIEU = SANPHAMCHITIET.IDCHATLIEU \n" +
"							   join DEGIAY on DEGIAY.IDDEGIAY = SANPHAMCHITIET.IDDEGIAY \n" +
"							   where MAUSAC.TENMAU like ?  and LoaiGiay.TenLoai like ? \n" +
"							   and THUONGHIEU.TENTHUONGHIEU like ? and DEGIAY.LOAIDEGIAY like ? and CHATLIEU.TENCHATLIEU like ?";         
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(2,"%" + loaiGiay + "%");         
            st.setString(3,"%" + thuongHieu + "%");         
            st.setString(5,"%" + chatLieu +"%");         
            st.setString(4,"%" + deGiay + "%");         
            st.setString(1,"%" + mauSac + "%" );         
             
            ResultSet r = st.executeQuery();

            
            while(r.next()){
               SanPhamChiTiet spct = new SanPhamChiTiet();
                
               
                spct.setIdSpct(r.getInt(1));
                spct.setMaSpct(r.getString(2));
                spct.setIdsp(r.getInt(3));
                spct.setLoaiGiay(r.getString(4));
                spct.setSize(r.getString(5));
                spct.setThuongHieu(r.getString(6));
                spct.setDeGiay(r.getString(7));
                spct.setMauSac(r.getString(8));
                spct.setChatLieu(r.getString(9));
                spct.setGia(r.getDouble(10));
                spct.setSoLuongSpct(r.getInt(11));
                spct.setTrangThaiSpct(r.getBoolean(12));
                
                
                list.add(spct);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean checkTrungMaSpct(String maSpct){
        try {
            String sql = "Select MaSPCT from SanPhamChiTiet where MaSPCt = ? ";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, maSpct);
                       
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkTrungidSp(int id ){
        try {
            String sql = "Select IDsanPham from SanPhamchitiet where IDsanPham = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            
            pr.setInt(1, id);
            
            ResultSet r = pr.executeQuery();
            while(r.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkTrungTTSPCT(int loaigiay,int size,int thuonghieu,int degiay,int mausac, int chatlieu) {
        try {
            String query = "SELECT COUNT(*) FROM SANPHAMCHITIET WHERE idLoaiGiay = ? and idsize = ? and idThuongHieu = ? and iddeGiay = ? and idmausac = ? and idChatLieu = ?";
            PreparedStatement stm = con.prepareStatement(query);
          
            stm.setInt(1, loaigiay);           
            stm.setInt(2, size);          
            stm.setInt(3, thuonghieu);
            stm.setInt(4, degiay);
            stm.setInt(5, mausac);
            stm.setInt(6, chatlieu);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }     

    @Override
    public List<Integer> getcboIDsanPham() {
        try {
            List<Integer> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("Select IDSANPHAM from SanPham");
            while(r.next()){                                
                int id = r.getInt(1);               
                list.add(id);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
