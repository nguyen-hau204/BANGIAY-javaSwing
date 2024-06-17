/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class SanPhamChiTiet {
      private int idSpct;
    private String maSpct;
    private int idsp;
    private String loaiGiay;
    private String size;
    private String thuongHieu;
    private String deGiay;
    private String mauSac;
    private String chatLieu;
    private double gia;
    private int soLuongSpct;
    private boolean trangThaiSpct;

    public SanPhamChiTiet() {
    }

    public int getIdSpct() {
        return idSpct;
    }

    public void setIdSpct(int idSpct) {
        this.idSpct = idSpct;
    }

    public String getMaSpct() {
        return maSpct;
    }

    public void setMaSpct(String maSpct) {
        this.maSpct = maSpct;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getLoaiGiay() {
        return loaiGiay;
    }

    public void setLoaiGiay(String loaiGiay) {
        this.loaiGiay = loaiGiay;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public String getDeGiay() {
        return deGiay;
    }

    public void setDeGiay(String deGiay) {
        this.deGiay = deGiay;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuongSpct() {
        return soLuongSpct;
    }

    public void setSoLuongSpct(int soLuongSpct) {
        this.soLuongSpct = soLuongSpct;
    }

    public boolean getTrangThaiSpct() {
        return trangThaiSpct;
    }

    public void setTrangThaiSpct(boolean trangThaiSpct) {
        this.trangThaiSpct = trangThaiSpct;
    }
    
    
    public String checkTrangThai(){
        if(trangThaiSpct == true){
            return "Đang bán";
        }else{
            return "Ngừng bán";
        }
    }
}
