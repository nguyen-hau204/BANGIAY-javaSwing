/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HoaDon {

    private int idHoaDon;
    private String maHoaDon;
    private String idKhachHang;
    private String soDienThoai;
    private Date ngayDat;
    private String ghiChu;
    private String idKhuyenMai;
    private String idNhanVien;
    private float tongTien;
    private int trangThai;

    public String trangThaiPhanTrang() {
        if (trangThai == 0) {
            return "Chưa thanh toán";
        } else {
            return "Đã thanh toán";
        }
    }

    public String ktkhach() {
        if (idKhachHang == null) {
            return "";
        } else if (idKhachHang.equals(" ")) {
            return "khách vãng lai";
        } else {
            return idKhachHang;
        }
    }

    public String getTrangThai() {
        if (this.trangThai == 0) {
            return "Chưa thanh toán";
        }

        return "Đã thanh toán";
    }
}
