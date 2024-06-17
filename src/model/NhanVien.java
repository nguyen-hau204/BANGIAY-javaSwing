/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class NhanVien {
    private boolean gTinh, tt;
    private int idNV, idChucVu, luong; 
    private String maNV, tenNV, tenDN, matKhau, email, sdt;

    public boolean getgTinh() {
        return gTinh;
    }

    public void setgTinh(boolean gTinh) {
        this.gTinh = gTinh;
    }

    public boolean getTt() {
        return tt;
    }

    public void setTt(boolean tt) {
        this.tt = tt;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public int getIdChucVu() {
        return idChucVu;
    }

    public void setIdChucVu(int idChucVu) {
        this.idChucVu = idChucVu;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    
    public String checktrangThai(){
        if (tt  == true) {
            return "Hoạt động";
        } else {
            return "Không hoạt động";
        }
    }
    
    public String checkGioiTinhNV(){
        if (gTinh  == true) {
            return "Nam";
        } else {
            return "Nữ";
        }
    }
    public String checkChucVuNV(){
        if (idChucVu  == 1) {
            return "Quản lý";
        } else {
            return "Nhân viên";
        }
    }
    
    
}
