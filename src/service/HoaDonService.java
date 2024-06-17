/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.HoaDon;

/**
 *
 * @author admin
 */
public interface HoaDonService {
    List<HoaDon> getHoaDon(String tenkh, String ngaybatdau, String ngayketthuc , String sdt);
    boolean addHoaDon (HoaDon hd);
    boolean deleteHoaDon (HoaDon hd);
    boolean updateHoaDon (HoaDon hd);
    List<HoaDon> timTenKHHDON (String ten);
    List<HoaDon> timSoDienThoaiKhHD (String sdt);
}
