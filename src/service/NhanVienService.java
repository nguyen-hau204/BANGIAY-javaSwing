/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;
import model.NhanVien;
import java.sql.*;
import java.util.List;
/**
 *
 * @author User
 */
public interface NhanVienService {
    public List<NhanVien> getNhanVien();
    public List<NhanVien> gettimNhanVien(String ma);
    public boolean AddNhanVien(NhanVien nv);
    public boolean UpdateNhanVien(NhanVien nv);
    public boolean DeleteNhanVien(NhanVien nv);
    boolean updateTrangThaiNhanVien(NhanVien nv);
    public List<NhanVien> getListPT(long id);
}
