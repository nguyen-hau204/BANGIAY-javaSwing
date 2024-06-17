/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.ThongKe;

/**
 *
 * @author admin
 */
public interface thongkeInterface {

    List<ThongKe> getall(String ngaybd, String ngaykt);

}
