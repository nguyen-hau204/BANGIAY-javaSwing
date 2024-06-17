/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.ChatLieu;
import model.LoaiGiay;
import model.DeGiay;
import model.MauSac;
import model.ThuongHieu;
import model.Size;
import repository.DBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class ThuocTinhService_IMPL {
    Connection con = DBConnect.getConnection();
   public List<ChatLieu> getChatLieu(){
       try {
           List<ChatLieu> list = new ArrayList<>();
           Statement st = con.createStatement();
           ResultSet r = st.executeQuery("Select IdChatLieu,TenChatLieu from ChatLieu");
           while(r.next()){
               ChatLieu cl = new ChatLieu();
               cl.setId(r.getInt(1));
               cl.setTenCl(r.getString(2));
               
               list.add(cl);
           }
           return list;
       } catch (Exception e) {
           return null;
       }
   }
   public List<LoaiGiay> getloaiGiay(){
       try {
           List<LoaiGiay> list = new ArrayList<>();
           Statement st = con.createStatement();
           ResultSet r = st.executeQuery("Select IdLoaiGiay,TenLoai from loaigiay");
           while(r.next()){
               LoaiGiay cl = new LoaiGiay();
               cl.setId(r.getInt(1));
               cl.setTenLg(r.getString(2));
               
               list.add(cl);
           }
           return list;
       } catch (Exception e) {
           return null;
       }
   }
   public List<ThuongHieu> getThuongHieu(){
       try {
           List<ThuongHieu> list = new ArrayList<>();
           Statement st = con.createStatement();
           ResultSet r = st.executeQuery("Select IDThuongHieu,TENTHUONGHIEU from THUONGHIEU");
           while(r.next()){
               ThuongHieu cl = new ThuongHieu();
               cl.setId(r.getInt(1));
               cl.setTenTh(r.getString(2));
               
               list.add(cl);
           }
           return list;
       } catch (Exception e) {
           return null;
       }
   }
   public List<MauSac> getMauSac(){
       try {
           List<MauSac> list = new ArrayList<>();
           Statement st = con.createStatement();
           ResultSet r = st.executeQuery("Select IDMAUSac,TENMAU from MAUSAC");
           while(r.next()){
               MauSac cl = new MauSac();
               cl.setId(r.getInt(1));
               cl.setTenMau(r.getString(2));
               
               list.add(cl);
           }
           return list;
       } catch (Exception e) {
           return null;
       }
   }
   public List<Size> getSize(){
       try {
           List<Size> list = new ArrayList<>();
           Statement st = con.createStatement();
           ResultSet r = st.executeQuery("Select IDSIZE,SOSIZE from SIZE");
           while(r.next()){
               Size cl = new Size();
               cl.setId(r.getInt(1));
               cl.setSoSize(r.getInt(2));
               
               list.add(cl);
           }
           return list;
       } catch (Exception e) {
           return null;
       }
   }
   public List<DeGiay> getDeGiay(){
       try {
           List<DeGiay> list = new ArrayList<>();
           Statement st = con.createStatement();
           ResultSet r = st.executeQuery("Select IDDEGIAY,LoaiDEGIAY from DEGIAY");
           while(r.next()){
               DeGiay cl = new DeGiay();
               cl.setId(r.getInt(1));
               cl.setTenDeGiay(r.getString(2));
               
               list.add(cl);
           }
           return list;
       } catch (Exception e) {
           return null;
       }
   }
   
   public boolean addChatLieu(ChatLieu s){
       try{
           String sql = "Insert into ChatLieu(TENCHATLIEU) values(?)";
           PreparedStatement pr = con.prepareStatement(sql);
           pr.setString(1, s.getTenCl());
           
           pr.execute();
           return true;
           
       }catch (Exception e) {
              return false;
      }
       
   }
   public boolean addLoaiGiay(LoaiGiay s){
       try{
           String sql = "Insert into LoaiGiay(TenLoai) values(?)";
           PreparedStatement pr = con.prepareStatement(sql);
           pr.setString(1, s.getTenLg());
           
           pr.execute();
           return true;
           
       }catch (Exception e) {
              return false;
      }
       
   }
   public boolean addThuongHieu(ThuongHieu s){
       try{
           String sql = "Insert into ThuongHieu(TenThuongHieu) values(?)";
           PreparedStatement pr = con.prepareStatement(sql);
           pr.setString(1, s.getTenTh());
           
           pr.execute();
           return true;
           
       }catch (Exception e) {
              return false;
      }
       
   }
   public boolean addMauSac(MauSac s){
       try{
           String sql = "Insert into MauSac(TenMau) values(?)";
           PreparedStatement pr = con.prepareStatement(sql);
           pr.setString(1, s.getTenMau());
           
           pr.execute();
           return true;
           
       }catch (Exception e) {
              return false;
      }
       
   }
   public boolean addSize(Size s){
       try{
           String sql = "Insert into Size(soSize) values(?)";
           PreparedStatement pr = con.prepareStatement(sql);
           pr.setInt(1, s.getSoSize());
           
           pr.execute();
           return true;
           
       }catch (Exception e) {
              return false;
      }
       
   }
   public boolean addDeGiay(DeGiay s){
       try{
           String sql = "Insert into DEGIAY(LOAIDEGIAY) values(?)";
           PreparedStatement pr = con.prepareStatement(sql);
           pr.setString(1, s.getTenDeGiay());
           
           pr.execute();
           return true;
           
       }catch (Exception e) {
              return false;
      }
       
   }
}
