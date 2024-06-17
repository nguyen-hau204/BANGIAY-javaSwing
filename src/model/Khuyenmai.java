/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
@Getter
@Setter
@AllArgsConstructor
public class Khuyenmai {
    private boolean trangthai;
    private String maGiamGiaKM, tenKM, loaiKM;
    private int soLuongKM, idKM;
    private Date ngayBatDauKM;
    private Date ngayKTKM;
    private int phantrangiam;
    public Khuyenmai() {
    }
        
        public String checktrangThai(){
        if (trangthai  == true) {
            return "Hoạt động";
        } else {
            return "Không hoạt động";
        }
    }
}
