/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.SanPham;
/**
 *
 * @author ADMIN
 */
public interface SanPhamService {
    public  List<SanPham> getSanPham(long id);
    public  List<SanPham> TimSanPham(String TenSp);
    public  boolean AddSanPham(SanPham s);
    public  boolean UpDateSanPham(SanPham s);
    
}
