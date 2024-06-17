/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;
import java.util.List;
import model.Khuyenmai;
/**
 *
 * @author User
 */
public interface KhuyenMaiService {
    List<Khuyenmai> getKhuyenMai();
    public boolean AddKM(Khuyenmai nv);
    public boolean UpdateKM(Khuyenmai nv);
    boolean updateTrangThaiKhuyenMai(Khuyenmai nv);
    public List<Khuyenmai> gettimKhuyenMai(String ma);
    public List<Khuyenmai> getListPT(long id);
     public List<String> getCboLoaiKhuyenMai();
}
