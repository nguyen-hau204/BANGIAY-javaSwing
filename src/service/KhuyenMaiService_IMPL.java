/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Khuyenmai;
import repository.DBConnect;

/**
 *
 * @author User
 */
public class KhuyenMaiService_IMPL implements KhuyenMaiService {

    Connection conn = DBConnect.getConnection();

    @Override
    public List<Khuyenmai> getKhuyenMai() {
        List<Khuyenmai> list = new ArrayList<>();
        try {

            Statement stm = conn.createStatement();
            String sql = "SELECT IDKHUYENMAI, MAGIAMGIA, TENKHUYENMAI, LOAIKHUYENMAI, SOLUONG, NGAYBATDAU, NGAYKETTHUC, TRANGTHAI FROM KHUYENMAI WHERE TRANGTHAI = 1";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Khuyenmai km = new Khuyenmai();
                km.setIdKM(rs.getInt(1));
                km.setMaGiamGiaKM(rs.getString(2));
                km.setTenKM(rs.getString(3));
                km.setLoaiKM(rs.getString(4));
                km.setSoLuongKM(rs.getInt(5));
                km.setNgayBatDauKM(rs.getDate(6));
                km.setNgayKTKM(rs.getDate(7));
                km.setTrangthai(rs.getBoolean(8));
                list.add(km);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean AddKM(Khuyenmai nv) {
        try {

            String sql = "INSERT INTO KHUYENMAI (MAGIAMGIA, TENKHUYENMAI, LOAIKHUYENMAI, SOLUONG, NGAYBATDAU, NGAYKETTHUC, TRANGTHAI) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, nv.getMaGiamGiaKM());
            ptm.setString(2, nv.getTenKM());
            ptm.setString(3, nv.getLoaiKM());
            ptm.setInt(4, nv.getSoLuongKM());
            ptm.setDate(5, new java.sql.Date(nv.getNgayBatDauKM().getTime()));
            System.out.println("ngày bắt đầu khuyến mãi là :"+nv.getNgayBatDauKM());
            ptm.setDate(6, new java.sql.Date(nv.getNgayKTKM().getTime()));
            ptm.setBoolean(7, nv.isTrangthai());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateKM(Khuyenmai nv) {
        try {

            String sql = "UPDATE KHUYENMAI SET TENKHUYENMAI = ?, LOAIKHUYENMAI = ?, SOLUONG = ?, NGAYBATDAU = ?, NGAYKETTHUC = ?, TRANGTHAI = ? WHERE MAGIAMGIA = ?";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, nv.getTenKM());
            ptm.setString(2, nv.getLoaiKM());
            ptm.setInt(3, nv.getSoLuongKM());
            ptm.setDate(4, new java.sql.Date(nv.getNgayBatDauKM().getTime()));
            ptm.setDate(5, new java.sql.Date(nv.getNgayKTKM().getTime()));
            ptm.setBoolean(6, nv.isTrangthai());
            ptm.setString(7, nv.getMaGiamGiaKM());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Khuyenmai> gettimKhuyenMai(String ma) {
        try {
            List<Khuyenmai> list = new ArrayList<>();
            Connection conn = DBConnect.getConnection();
           String sql = "SELECT IDKHUYENMAI, MAGIAMGIA, TENKHUYENMAI, LOAIKHUYENMAI, SOLUONG, NGAYBATDAU, NGAYKETTHUC,  TRANGTHAI FROM KHUYENMAI WHERE TRANGTHAI = 1 and  MAGIAMGIA =  ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, ma );
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Khuyenmai km = new Khuyenmai();
                km.setIdKM(rs.getInt(1));
                km.setMaGiamGiaKM(rs.getString(2));
                km.setTenKM(rs.getString(3));
                km.setLoaiKM(rs.getString(4));
                km.setSoLuongKM(rs.getInt(5));
                km.setNgayBatDauKM(rs.getDate(6));
                km.setNgayKTKM(rs.getDate(7));
                km.setTrangthai(rs.getBoolean(8));
   
                list.add(km);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateTrangThaiKhuyenMai(Khuyenmai nv) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE KHUYENMAI SET TRANGTHAI = ? WHERE IDKHUYENMAI = ?";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setBoolean(1, nv.isTrangthai());
            ptm.setInt(2, nv.getIdKM());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Khuyenmai> getListPT(long id) {
        try {
            List<Khuyenmai> list = new ArrayList<>();
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("SELECT *FROM KHUYENMAI\n"
                    + "ORDER BY IDKHUYENMAI\n"
                    + "OFFSET " + (id) + " ROWS\n"
                    + "FETCH NEXT 10 ROWS ONLY;");
            while (r.next()) {
                Khuyenmai s = new Khuyenmai();
                s.setIdKM(r.getInt(1));
                s.setMaGiamGiaKM(r.getString(2));
                s.setTenKM(r.getString(3));
                s.setLoaiKM(r.getString(4));
                s.setSoLuongKM(r.getInt(5));
                s.setNgayBatDauKM(r.getDate(6));
                s.setNgayKTKM(r.getDate(7));
                s.setTrangthai(r.getBoolean(8));
                list.add(s);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkTrungMa(String manv) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "Select MAGIAMGIA from KHUYENMAI where MAGIAMGIA = ? ";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, manv);

            ResultSet r = pr.executeQuery();
            while (r.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getCboLoaiKhuyenMai() {
        Connection con = DBConnect.getConnection();
        List<String> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("""
                                         SELECT DISTINCT LOAIKHUYENMAI, 
                                              CAST(REPLACE(LOAIKHUYENMAI, '%', '') AS DECIMAL(5,2)) AS PhanTram
                                          FROM KHUYENMAI
                                          ORDER BY PhanTram ;
                                          """);
            while (r.next()) {
                String loaiKM = r.getString(1);
                list.add(loaiKM);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

//    public float GTLoaiKM(String loaiKM) {
//        float giaTri = 0;
//        if (loaiKM.equalsIgnoreCase("Loại 0")) {
//            giaTri = 10000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 1")) {
//            giaTri = 20000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 2")) {
//            giaTri = 30000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 3")) {
//            giaTri = 40000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 4")) {
//            giaTri = 50000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 5")) {
//            giaTri = 60000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 6")) {
//            giaTri = 70000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 7")) {
//            giaTri = 80000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 8")) {
//            giaTri = 90000;
//        } else if (loaiKM.equalsIgnoreCase("Loại 9")) {
//            giaTri = 100000;
//        }
//        return giaTri;
//
//    }

    public String timLoaiKM(String tenKM) {
        String loaiKM = null;
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "SELECT LOAIKHUYENMAI FROM KHUYENMAI WHERE TENKHUYENMAI LIKE ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, tenKM);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Khuyenmai km = new Khuyenmai();
                
                km.setLoaiKM(rs.getString(1));
                
                loaiKM = km.getLoaiKM();
            }
            return loaiKM;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
