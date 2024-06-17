/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.HoaDon;
import repository.DBConnect;

/**
 *
 * @author admin
 */
public class HoaDonService_IMPL implements HoaDonService {

    Connection conn = DBConnect.getConnection();

    @Override
    public List<HoaDon> getHoaDon(String tenkh, String ngaybatdau, String ngayketthuc, String sdt) {
        List<HoaDon> listHD = new ArrayList<>();
        ResultSet rs = null;
        try {
            if (tenkh.equals("") && ngaybatdau.equals("") && sdt.equals("") &&ngayketthuc.equals("")) {
                String sql = """
                SELECT 
                       hd.IDHOADON,
                       hd.MAHOADON,
                       kh.HOTEN,
                       kh.SODIENTHOAI ,
                       hd.NGAYDATDAT,
                       hd.GICHU,
                       km.TENKHUYENMAI,
                       nv.TENNV,
                       hd.TONGTIEN,
                       hd.TRANGTHAI
                       FROM HoaDon hd 
                        JOIN KhachHang kh ON hd.IDKHACHHANG = kh.IDKHACHHANG
                        JOIN KhuyenMai km ON hd.IDKHUYENMAI = km.IDKHUYENMAI
                        JOIN NhanVien nv ON hd.IDNHANVIEN = nv.IDNHANVIEN WHERE hd.TRANGTHAI = 1 ;
                """;

                Statement stm = conn.createStatement();
                rs = stm.executeQuery(sql);
            } else {
                String sql = """
                  SELECT 
                    hd.IDHOADON,
                    hd.MAHOADON,
                    kh.HOTEN,
                    kh.SODIENTHOAI ,        
                    hd.NGAYDATDAT,
                    hd.GICHU,
                    km.TENKHUYENMAI,
                    nv.TENNV,
                    hd.TONGTIEN,
                    hd.TRANGTHAI
                FROM HOADON hd
                JOIN KHUYENMAI km ON hd.IDKHUYENMAI = km.IDKHUYENMAI
                JOIN KHACHHANG kh ON hd.IDKHACHHANG = kh.IDKHACHHANG
                JOIN NHANVIEN nv ON hd.IDNHANVIEN = nv.IDNHANVIEN
                WHERE (kh.HOTEN = ? OR kh.SODIENTHOAI = ?)
                    OR hd.NGAYDATDAT BETWEEN ? AND ?
                    ORDER BY hd.NGAYDATDAT ASC ;;
                """;

                PreparedStatement ptm = conn.prepareStatement(sql);
                ptm.setString(1, tenkh);
                ptm.setString(2, sdt);
                ptm.setString(3, ngaybatdau);
                ptm.setString(4, ngayketthuc);
                rs = ptm.executeQuery();
            }

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setMaHoaDon(rs.getString(2));
                hd.setIdKhachHang(rs.getString(3));
                hd.setSoDienThoai(rs.getString(4));
                hd.setNgayDat(rs.getDate(5));
                hd.setGhiChu(rs.getString(6));
                hd.setIdKhuyenMai(rs.getString(7));
                hd.setIdNhanVien(rs.getString(8));
                hd.setTongTien(rs.getFloat(9));
                hd.setTrangThai(rs.getInt(10));
                listHD.add(hd);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDon> getHoaDonPhanTrang(long id) {
        List<HoaDon> listHD = new ArrayList<>();
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "   SELECT \n"
                    + "                            hd.IDHOADON,\n"
                    + "                            hd.MAHOADON,\n"
                    + "                            kh.HOTEN,\n"
                    + "                            kh.SODIENTHOAI,\n"
                    + "                            hd.NGAYDATDAT,\n"
                    + "                            hd.GICHU,\n"
                    + "                            km.TENKHUYENMAI,\n"
                    + "                            nv.TENNV,\n"
                    + "                            hd.TONGTIEN,\n"
                    + "                            hd.TRANGTHAI\n"
                    + "                        FROM HoaDon hd \n"
                    + "                         LEFT JOIN KhachHang kh ON hd.IDKHACHHANG = kh.IDKHACHHANG\n"
                    + "                         LEFT JOIN KhuyenMai km ON hd.IDKHUYENMAI = km.IDKHUYENMAI\n"
                    + "                         LEFT JOIN NhanVien nv ON hd.IDNHANVIEN = nv.IDNHANVIEN WHERE hd.TRANGTHAI = 1\n"
                    + "                         ORDER BY hd.IDHOADON\n"
                    + "                    OFFSET " + (id) + " ROWS\n"
                    + "            FETCH NEXT 10 ROWS ONLY";

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setMaHoaDon(rs.getString(2));
                hd.setIdKhachHang(rs.getString(3));
                hd.setSoDienThoai(rs.getString(4));
                hd.setNgayDat(rs.getDate(5));
                hd.setGhiChu(rs.getString(6));
                hd.setIdKhuyenMai(rs.getString(7));
                hd.setIdNhanVien(rs.getString(8));
                hd.setTongTien(rs.getFloat(9));
                hd.setTrangThai(rs.getInt(10));
                listHD.add(hd);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDon> getHoaDonBanHang() {
        List<HoaDon> listHD = new ArrayList<>();
        try {
            Connection conn = DBConnect.getConnection();
            String sql = """
                    SELECT 
                        hd.IDHOADON,
                        hd.MAHOADON,
                        kh.HOTEN,
                        hd.NGAYDATDAT,
                        hd.GICHU,
                        km.TENKHUYENMAI,
                        nv.TENNV,
                        hd.TONGTIEN,
                        hd.TRANGTHAI
                    FROM 
                        HoaDon hd 
                        LEFT JOIN KhachHang kh ON hd.IDKHACHHANG = kh.IDKHACHHANG
                        LEFT JOIN KhuyenMai km ON hd.IDKHUYENMAI = km.IDKHUYENMAI
                        LEFT JOIN NhanVien nv ON hd.IDNHANVIEN = nv.IDNHANVIEN 
                    WHERE 
                        hd.TRANGTHAI = 0
                    ORDER BY 
                        hd.IDHOADON DESC; 
                        """;

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setMaHoaDon(rs.getString(2));
                hd.setIdKhachHang(rs.getString(3));
                hd.setNgayDat(rs.getDate(4));
                hd.setGhiChu(rs.getString(5));
                hd.setIdKhuyenMai(rs.getString(6));
                hd.setIdNhanVien(rs.getString(7));
                hd.setTongTien(rs.getFloat(8));
                hd.setTrangThai(rs.getInt(9));
                listHD.add(hd);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addHoaDon(HoaDon hd) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = """
                        DECLARE @idkh INT, @idnv INT
                        SET @idkh = (SELECT IDKHACHHANG FROM KHACHHANG WHERE HOTEN = ?)
                        SET @idnv = (SELECT IDNHANVIEN FROM NHANVIEN WHERE [MANV] = ?)
                        INSERT INTO [dbo].[HOADON]
                        ([MAHOADON],[IDKHACHHANG],[NGAYDATDAT],[GICHU],[IDKHUYENMAI],[IDNHANVIEN],[TONGTIEN],[TRANGTHAI])
                        VALUES
                        (?,@idkh,?,null,null,@idnv,?,0)                        
                         """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(3, hd.getMaHoaDon());
            ptm.setString(1, hd.getIdKhachHang());
            ptm.setDate(4, new java.sql.Date(hd.getNgayDat().getTime()));
            ptm.setString(2, hd.getIdNhanVien());
            ptm.setFloat(5, hd.getTongTien());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteHoaDon(HoaDon hd) {
        Connection conn = DBConnect.getConnection();
        try {
            // Xóa tất cả các bản ghi trong bảng HOADONCT có tham chiếu đến bản ghi trong bảng HOADON
            String sqlDeleteHoaDonCT = """
                                  DECLARE @idhd int
                                   set @idhd = (select IDHOADON from hoadon where MAHOADON = ?)
                                   DELETE FROM [dbo].[HOADONCT] WHERE IDHOADON = @idhd
                                   """;
            PreparedStatement ptmDeleteHoaDonCT = conn.prepareStatement(sqlDeleteHoaDonCT);
            ptmDeleteHoaDonCT.setString(1, hd.getMaHoaDon() ); // Giả sử idHoaDon là khóa chính trong bảng HOADON
            ptmDeleteHoaDonCT.executeUpdate();

            // Sau đó, xóa bản ghi trong bảng HOADON
            String sqlDeleteHoaDon = "DELETE FROM [dbo].[HOADON] WHERE MAHOADON = ?";
            PreparedStatement ptmDeleteHoaDon = conn.prepareStatement(sqlDeleteHoaDon);
            ptmDeleteHoaDon.setString(1, hd.getMaHoaDon());
            ptmDeleteHoaDon.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateHoaDon(HoaDon hd) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = """
                        UPDATE [dbo].[HOADON]
                                           SET [MAHOADON] = ?
                                          ,[IDKHACHHANG] =?
                                          ,[NGAYDATDAT] = ?
                                          ,[GICHU] = ?
                                          ,[IDKHUYENMAI] = ?
                                          ,[IDNHANVIEN] =? 
                                           ,[TONGTIEN] = ? 
                                     WHERE IDHOADON =?
                        """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hd.getMaHoaDon());
            ptm.setString(2, hd.getIdKhachHang());
            ptm.setDate(3, new java.sql.Date(hd.getNgayDat().getTime()));
            ptm.setString(4, hd.getGhiChu());
            ptm.setString(5, hd.getIdKhuyenMai());
            ptm.setString(5, hd.getIdNhanVien());
            ptm.setFloat(6, hd.getTongTien());
            ptm.setInt(7, hd.getIdHoaDon());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<HoaDon> timTenKHHDON(String ten) {
        Connection conn = DBConnect.getConnection();
        try {
            List<HoaDon> list = new ArrayList();
            String sql = """
                         SELECT * FROM HOADON WHERE HOTEN LIKE ?
                         """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, "%" + ten + "%");
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setMaHoaDon(rs.getString(2));
                hd.setIdKhachHang(rs.getString(3));
                hd.setSoDienThoai(rs.getString(4));
                hd.setNgayDat(rs.getDate(5));
                hd.setGhiChu(rs.getString(6));
                hd.setIdKhuyenMai(rs.getString(7));
                hd.setIdNhanVien(rs.getString(8));
                hd.setTongTien(rs.getFloat(9));
                hd.setTrangThai(rs.getInt(10));
                list.add(hd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HoaDon> timSoDienThoaiKhHD(String sdt) {
        Connection conn = DBConnect.getConnection();
        try {
            List<HoaDon> list = new ArrayList();
            String sql = """
                         SELECT * FROM HOADON WHERE SODIENTHOAI LIKE ?
                         """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, "%" + sdt + "%");
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setMaHoaDon(rs.getString(2));
                hd.setIdKhachHang(rs.getString(3));
                hd.setSoDienThoai(rs.getString(4));
                hd.setNgayDat(rs.getDate(5));
                hd.setGhiChu(rs.getString(6));
                hd.setIdKhuyenMai(rs.getString(7));
                hd.setIdNhanVien(rs.getString(8));
                hd.setTongTien(rs.getFloat(9));
                hd.setTrangThai(rs.getInt(10));
                list.add(hd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean huyHoaDon(HoaDon hd) {
        Connection conn = DBConnect.getConnection();
        try {
            String sql = """
                        DELETE FROM [dbo].[HOADON]
                              WHERE MAHOADON = ? 
                        """;
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, hd.getMaHoaDon());
            ptm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

public boolean thanhtoan(HoaDon hd) {
    Connection conn = DBConnect.getConnection();
    try {
        String sql = "DECLARE @idkh INT, @idkm INT; "
                + "SET @idkh = (SELECT IDKHACHHANG FROM KHACHHANG WHERE HOTEN = ?); "
                + "SET @idkm = (SELECT IDKHUYENMAI FROM KHUYENMAI WHERE MAGIAMGIA = ?); "
                + "UPDATE HOADON "
                + "SET IDKHACHHANG = @idkh, GICHU = ?, TONGTIEN = ?, IDKHUYENMAI = @idkm, TRANGTHAI = 1 "
                + "WHERE MAHOADON = ?";
        PreparedStatement ptm = conn.prepareStatement(sql);
        
        ptm.setString(1, hd.getIdKhachHang());
        ptm.setString(2, hd.getIdKhuyenMai());
        ptm.setString(3, hd.getGhiChu());
        ptm.setFloat(4, hd.getTongTien());
        ptm.setString(5, hd.getMaHoaDon());
        System.out.println("makm la"+hd.getIdKhuyenMai());
        ptm.executeUpdate();
        JOptionPane.showMessageDialog(null, "Thanh toán thành công");
        return true;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Thanh toán thất bại");
        e.printStackTrace();
        return false;
    } 
}

}
