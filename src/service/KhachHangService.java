/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.KhachHang;

/**
 *
 * @author admin
 */
public interface KhachHangService {
    List<KhachHang> getAllKhachHang();
    boolean addKhachHang(KhachHang kh);
//    boolean deleteKhachHang(KhachHang kh);
    boolean updateKhachHang(KhachHang kh);
    boolean updateTrangThaiKhachHang(KhachHang kh);
    List<KhachHang>timTenKhachHang(String tenKH);
}
