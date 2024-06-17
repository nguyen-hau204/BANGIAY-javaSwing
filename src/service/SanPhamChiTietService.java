/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;
import java.util.List;
import model.SanPhamChiTiet;
/**
 *
 * @author ADMIN
 */
public interface SanPhamChiTietService {
    public List<SanPhamChiTiet>  getSpCt(long id,String idsp);
    public boolean addSpct(SanPhamChiTiet s);
    public boolean update(SanPhamChiTiet s);
    public boolean doiTrangThaiSpct(SanPhamChiTiet s);
    public List<String> getcboLoaiGiay();
    public List<String> getcboThuongHieu();
    public List<String> getcboChatLieu();
    public List<String> getcboDeGiay();
    public List<String> getcboMauSac();
    public List<Integer> getcboIDsanPham();
    public List<String> getcboSize();
    public List<SanPhamChiTiet> timSanPhamChiTiet(String maSpct);
    public List<SanPhamChiTiet> locSanPhamChiTiet(String loaiGiay,String thuongHieu,String chatLieu,String deGiay,String mauSac);
    
}
