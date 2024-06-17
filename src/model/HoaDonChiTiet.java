/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HoaDonChiTiet {

    private int idHoaDonCT;
    private String maHoaDon;
    private String maSanPhamChiTiet;
    private int idSpct;
    private String tenSP;
    private int soLuong;
    private int size;
    private String mauSac;
    private String deGiay;
    private String chatLieu;
    private float gia;
    private Double thanhTien;
    private int trangThai;

    public HoaDonChiTiet() {
    }

}
