/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bangiay.view;

import java.time.LocalDate;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.encoder.QRCode;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JDialog;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;
import repository.DBConnect;
import service.KhachHangService_IMPL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.xml.transform.Result;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.Khuyenmai;
import model.NhanVien;
import model.SanPham;
import model.SanPhamChiTiet;
import model.ThongKe;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import service.HoaDonService_IMPL;
import service.KhuyenMaiService_IMPL;
import service.NhanVienService_IMPL;
import service.SanPhamService_IMPL;
import service.SanPhamChiTietService;
import service.SanPhamChiTiet_IMPL;
import service.HoaDonChiTietService_IMPL;
import service.Thongkesevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.SheetVisibility;

/**
 *
 * @author nguye
 */
public class MAIN_VIEW31t1 extends javax.swing.JFrame implements Runnable, ThreadFactory {

    DefaultTableModel mol = new DefaultTableModel();
    KhachHangService_IMPL khachHangService_IMPL = new KhachHangService_IMPL();
    HoaDonService_IMPL hoaDonService_IMPL = new HoaDonService_IMPL();
    HoaDonChiTietService_IMPL chiTietService_IMPL = new HoaDonChiTietService_IMPL();
    NhanVienService_IMPL nhanVienService_IMPL = new NhanVienService_IMPL();
    SanPhamService_IMPL spd = new SanPhamService_IMPL();
    SanPhamChiTiet_IMPL spdChiTiet_IMPL = new SanPhamChiTiet_IMPL();
    KhuyenMaiService_IMPL KhuyenMaiService_IMPL = new KhuyenMaiService_IMPL();
    Thongkesevice sevice = new Thongkesevice();

    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private Executor exe = Executors.newSingleThreadScheduledExecutor(this);

    String tenKH;
    String tennv, tencv;
    long idNV, idKH, idSP, idHD, idKM = 0, idSPCT = 0;
    long countNV, countKH, countHD, countSP, countKM, countSpct;
    int trangKH, trangHD, trangSP, trangNV, trangKM = 1, trangSPCT = 1;
    String ten = "";
    String ngay = "";
    String ngayketthuc = "";
    String sdt = "";
    public static int idhd = 0;

    String ngaybatdau;
    String ngayketthuc1;
    String ngaybd = "";
    String ngaykt = "";
    Connection conn = DBConnect.getConnection();

    public MAIN_VIEW31t1(String hoten, String chucvu) {
        initComponents();
        cbo_LoaiKM.setEditable(true);
        setLocationRelativeTo(null);
        countALL();
        this.setResizable(true); // true là được bấm phóng to màn hinh , còn false là không được phongs to màn hình 
        fillALL();
        lbl_tennv.setText(hoten);
        lbl_chucvu.setText(chucvu);
        layngay();
      initWebcam();
        showTenLoaiKhuyenMai();
        ((AbstractDocument) txt_khachdua.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) { // Chỉ cho phép nhập số
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        txt_tongtien.setEnabled(false);
    }

    void layngay() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Ngày và giờ hiện tại: " + currentDateTime);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        System.out.println("Ngày hiện tại định dạng: " + formattedDate);
        lbl_ngay.setText(formattedDate);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jaddkh = new javax.swing.JFrame();
        txt_maKh1 = new javax.swing.JTextField();
        txt_tenKH1 = new javax.swing.JTextField();
        btnNewKh1 = new javax.swing.JButton();
        txt_dienthoaiKH1 = new javax.swing.JTextField();
        txt_emailKH1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblIDKhachHang1 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        rdoDangHoatDongKH1 = new javax.swing.JRadioButton();
        jLabel66 = new javax.swing.JLabel();
        txt_diachiKH1 = new javax.swing.JTextField();
        btnThemKh1 = new javax.swing.JButton();
        rdo_namKH1 = new javax.swing.JRadioButton();
        rdo_nuKH1 = new javax.swing.JRadioButton();
        jLabel67 = new javax.swing.JLabel();
        rdoKhongHoatDongKH2 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbl_tennv = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_ngay = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_chucvu = new javax.swing.JLabel();
        btn_BANHANG = new javax.swing.JButton();
        btn_QLHD = new javax.swing.JButton();
        btn_QLNV = new javax.swing.JButton();
        btn_QLSP = new javax.swing.JButton();
        btn_THONGKE = new javax.swing.JButton();
        btn_QLKH = new javax.swing.JButton();
        btn_QLKM = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lbl_nameud = new javax.swing.JLabel();
        jtab = new javax.swing.JTabbedPane();
        viewbanhang = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        Home = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        btn_truoc = new javax.swing.JButton();
        btn_sau = new javax.swing.JButton();
        lbl_sotrangspct = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSanPhamBanHang = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        txtTimMaSPBanHang = new javax.swing.JTextField();
        cboLoaiGiay2 = new javax.swing.JComboBox<>();
        cboMau2 = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        cboDeGiay2 = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        cboThuongHieu2 = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        cboChatLieu2 = new javax.swing.JComboBox<>();
        btn_themGioHang = new javax.swing.JButton();
        btnTkiemMaSPBanHang = new javax.swing.JButton();
        panelgiohang = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_giohang = new javax.swing.JTable();
        btn_xoa1 = new javax.swing.JButton();
        btn_congGioHang = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        btn_truochoadon = new javax.swing.JButton();
        btn_nexthoadon = new javax.swing.JButton();
        lbl_tranghoadon = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tbl_HoaDonBanHang = new javax.swing.JTable();
        panelThanhToan = new javax.swing.JPanel();
        btn_themkhachhang = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        txt_tenkhachtt = new javax.swing.JTextField();
        txt_khachdua = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        lbl_tienthua = new javax.swing.JLabel();
        btn_thanhtoan = new javax.swing.JButton();
        btn_taohoadon = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lbl_thanhtoan = new javax.swing.JLabel();
        lbl_thieu = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        btnHuyHoaDonn = new javax.swing.JButton();
        btn_vangLai = new javax.swing.JButton();
        btn_timKhachTheoSDT = new javax.swing.JButton();
        txt_khuyenmai = new javax.swing.JTextField();
        btnApMaKhuyenMai = new javax.swing.JButton();
        txt_tongtien = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        lbl_loaikm = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jcam = new javax.swing.JPanel();
        lbl_maqr = new javax.swing.JLabel();
        viewkhachhang = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        rdo_namKH = new javax.swing.JRadioButton();
        rdo_nuKH = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txt_maKh = new javax.swing.JTextField();
        txt_tenKH = new javax.swing.JTextField();
        txt_dienthoaiKH = new javax.swing.JTextField();
        txt_emailKH = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_diachiKH = new javax.swing.JTextField();
        btnThemKh = new javax.swing.JButton();
        btnSuaKh = new javax.swing.JButton();
        btnNewKh = new javax.swing.JButton();
        btnDoiTrangThaiKH = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblIDKhachHang = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        rdoKhongHoatDongKH = new javax.swing.JRadioButton();
        rdoDangHoatDongKH = new javax.swing.JRadioButton();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangKhachHang = new javax.swing.JTable();
        txtTkiemKH = new javax.swing.JTextField();
        btn_sauKH = new javax.swing.JButton();
        lbl_trangKH = new javax.swing.JLabel();
        btn_truocKH = new javax.swing.JButton();
        btnTimKiemKH = new javax.swing.JButton();
        viewhoadon = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBangHoaDonHD = new javax.swing.JTable();
        btn_truocHD = new javax.swing.JButton();
        lbl_trangHD = new javax.swing.JLabel();
        btn_sauHD = new javax.swing.JButton();
        btn_maxHD = new javax.swing.JButton();
        btn_minHD = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBangHoaDonCTHD = new javax.swing.JTable();
        txtTimKiemTheoTenKhachHangHDON = new javax.swing.JTextField();
        txtTimKiemTheoNgayHDon = new javax.swing.JTextField();
        btnTimKiemSoDienThoaiHD = new javax.swing.JButton();
        txtTimKiemTheoNgayKTHDon = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        viewnhanvien = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        rdo_namNV = new javax.swing.JRadioButton();
        rdo_nuNV = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txt_maNV = new javax.swing.JTextField();
        txt_tenDNNV = new javax.swing.JTextField();
        txt_mkNV = new javax.swing.JTextField();
        txt_emailNV = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_sdtNV = new javax.swing.JTextField();
        btn_ThemNV = new javax.swing.JButton();
        btn_SuaNV = new javax.swing.JButton();
        btn_LamMoiNV = new javax.swing.JButton();
        btn_DoiTTNV = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txt_idchucvuNV = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txt_luongNV = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        rdo_hdNV = new javax.swing.JRadioButton();
        rdo_khdNV = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        lblIDNhanVien = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_tenNhanVien = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_bangNhanVien = new javax.swing.JTable();
        txt_tkNV = new javax.swing.JTextField();
        btn_TruocNv = new javax.swing.JButton();
        btn_SauNV = new javax.swing.JButton();
        btn_TKNV = new javax.swing.JButton();
        lbl_trangNV = new javax.swing.JLabel();
        viewsp = new javax.swing.JPanel();
        panelSP = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        txtMaSp = new javax.swing.JTextField();
        txtTensp = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        btn_themSP = new javax.swing.JButton();
        btn_suaSP = new javax.swing.JButton();
        btn_newSP = new javax.swing.JButton();
        btn_xemctSP = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();
        btndoiTT = new javax.swing.JButton();
        lblIDSanPham = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblBangSanPham = new javax.swing.JTable();
        txtTimSp = new javax.swing.JTextField();
        btn_truocSP = new javax.swing.JButton();
        lbl_trangSP = new javax.swing.JLabel();
        btn_sauSP = new javax.swing.JButton();
        btn_timKiem = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        viewkhuyenmai = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMaKhuyenMai = new javax.swing.JTextField();
        txtTenKM = new javax.swing.JTextField();
        txtSoluongKM = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtNgayKTKM = new javax.swing.JTextField();
        btn_themKM = new javax.swing.JButton();
        btn_suaKM = new javax.swing.JButton();
        btnNewKhuyenMai = new javax.swing.JButton();
        btnDoiTrangThaiKM = new javax.swing.JButton();
        txtNgayBDKM = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        rdo_hdKM = new javax.swing.JRadioButton();
        rdo_khdKM = new javax.swing.JRadioButton();
        jLabel54 = new javax.swing.JLabel();
        lblIdKhuyenMai = new javax.swing.JLabel();
        cbo_LoaiKM = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblBangKhuyenMai = new javax.swing.JTable();
        txt_TKKM = new javax.swing.JTextField();
        btnTruocKM = new javax.swing.JButton();
        lblTrangKM = new javax.swing.JLabel();
        btnSauKM = new javax.swing.JButton();
        btnTimKiemKM = new javax.swing.JButton();
        viewthongke = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txt_ngaybdtk = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txt_ngaykttk = new javax.swing.JTextField();
        btn_xem = new javax.swing.JButton();
        btnBieuDo = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_bangthongke = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        lbltongtien = new javax.swing.JLabel();

        jaddkh.getContentPane().setLayout(new java.awt.GridBagLayout());

        txt_maKh1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_maKh1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_maKh1KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 123;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 12, 0, 0);
        jaddkh.getContentPane().add(txt_maKh1, gridBagConstraints);

        txt_tenKH1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 123;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
        jaddkh.getContentPane().add(txt_tenKH1, gridBagConstraints);

        btnNewKh1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNewKh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Clipboard.png"))); // NOI18N
        btnNewKh1.setText("New");
        btnNewKh1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNewKh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewKh1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(37, 63, 42, 0);
        jaddkh.getContentPane().add(btnNewKh1, gridBagConstraints);

        txt_dienthoaiKH1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 123;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
        jaddkh.getContentPane().add(txt_dienthoaiKH1, gridBagConstraints);

        txt_emailKH1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 158;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 10, 0, 0);
        jaddkh.getContentPane().add(txt_emailKH1, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Id Khách Hàng");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 24, 0, 0);
        jaddkh.getContentPane().add(jLabel24, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Tên khách hàng ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 20, 0, 0);
        jaddkh.getContentPane().add(jLabel25, gridBagConstraints);

        lblIDKhachHang1.setText("jLabel8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 131;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        jaddkh.getContentPane().add(lblIDKhachHang1, gridBagConstraints);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setText("Số điện thoại");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 20, 0, 0);
        jaddkh.getContentPane().add(jLabel35, gridBagConstraints);

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel64.setText("Trạng thái ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 27, 0, 0);
        jaddkh.getContentPane().add(jLabel64, gridBagConstraints);

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setText("Giới tính ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 27, 0, 0);
        jaddkh.getContentPane().add(jLabel58, gridBagConstraints);

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 27, 0, 0);
        jaddkh.getContentPane().add(jLabel65, gridBagConstraints);

        buttonGroup5.add(rdoDangHoatDongKH1);
        rdoDangHoatDongKH1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoDangHoatDongKH1.setText("Đang hoạt động ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 18, 0, 0);
        jaddkh.getContentPane().add(rdoDangHoatDongKH1, gridBagConstraints);

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setText("Địa chỉ ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 27, 0, 0);
        jaddkh.getContentPane().add(jLabel66, gridBagConstraints);

        txt_diachiKH1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 158;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 10, 0, 0);
        jaddkh.getContentPane().add(txt_diachiKH1, gridBagConstraints);

        btnThemKh1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemKh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add.png"))); // NOI18N
        btnThemKh1.setText("Thêm");
        btnThemKh1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnThemKh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKh1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(37, 67, 42, 0);
        jaddkh.getContentPane().add(btnThemKh1, gridBagConstraints);

        buttonGroup4.add(rdo_namKH1);
        rdo_namKH1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo_namKH1.setSelected(true);
        rdo_namKH1.setText("Nam");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 18, 0, 0);
        jaddkh.getContentPane().add(rdo_namKH1, gridBagConstraints);

        buttonGroup4.add(rdo_nuKH1);
        rdo_nuKH1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo_nuKH1.setText("Nữ");
        rdo_nuKH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_nuKH1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 41, 0, 0);
        jaddkh.getContentPane().add(rdo_nuKH1, gridBagConstraints);

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel67.setText("Mã khách hàng");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 20, 0, 0);
        jaddkh.getContentPane().add(jLabel67, gridBagConstraints);

        buttonGroup5.add(rdoKhongHoatDongKH2);
        rdoKhongHoatDongKH2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoKhongHoatDongKH2.setSelected(true);
        rdoKhongHoatDongKH2.setText("Không hoạt động ");
        rdoKhongHoatDongKH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhongHoatDongKH2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 63, 0, 6);
        jaddkh.getContentPane().add(rdoKhongHoatDongKH2, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setForeground(new java.awt.Color(153, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 700));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 750));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 10, 10)));

        jPanel18.setBackground(new java.awt.Color(204, 204, 204));
        jPanel18.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Họ tên :");

        lbl_tennv.setText("jLabel16");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Ngày :");

        lbl_ngay.setText("jLabel17");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Chức vụ :");

        lbl_chucvu.setText("jLabel17");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_chucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_tennv, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(lbl_ngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lbl_tennv, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_chucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbl_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btn_BANHANG.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_BANHANG.setForeground(new java.awt.Color(0, 51, 51));
        btn_BANHANG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Full basket.png"))); // NOI18N
        btn_BANHANG.setText("Bán hàng");
        btn_BANHANG.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 2, 2), 1, true));
        btn_BANHANG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BANHANGActionPerformed(evt);
            }
        });

        btn_QLHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_QLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/List.png"))); // NOI18N
        btn_QLHD.setText("Quản lý hoá đơn");
        btn_QLHD.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 2, 2), 1, true));
        btn_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLHDActionPerformed(evt);
            }
        });

        btn_QLNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Boy.png"))); // NOI18N
        btn_QLNV.setText("Quản lý nhân viên");
        btn_QLNV.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 2, 2), 1, true));
        btn_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLNVActionPerformed(evt);
            }
        });

        btn_QLSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_QLSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/giay.png"))); // NOI18N
        btn_QLSP.setText("Quản lý sản phẩm ");
        btn_QLSP.setToolTipText("");
        btn_QLSP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 2, 2), 1, true));
        btn_QLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLSPActionPerformed(evt);
            }
        });

        btn_THONGKE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_THONGKE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Best.png"))); // NOI18N
        btn_THONGKE.setText("Thống kê");
        btn_THONGKE.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 2, 2)));
        btn_THONGKE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_THONGKEActionPerformed(evt);
            }
        });

        btn_QLKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_QLKH.setForeground(new java.awt.Color(51, 0, 51));
        btn_QLKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Conference.png"))); // NOI18N
        btn_QLKH.setText("Quản lý khách hàng");
        btn_QLKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 2, 2), 1, true));
        btn_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLKHActionPerformed(evt);
            }
        });

        btn_QLKM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_QLKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Certificate.png"))); // NOI18N
        btn_QLKM.setText("Quản lý khuyến mãi ");
        btn_QLKM.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(2, 2, 2), 1, true));
        btn_QLKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLKMActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Exit.png"))); // NOI18N
        jButton9.setText("Log out");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_BANHANG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_THONGKE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_BANHANG, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btn_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btn_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_QLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btn_QLKM, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_THONGKE, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 790));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(new javax.swing.border.MatteBorder(null));

        lbl_nameud.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_nameud.setText("Ứng dụng quản lý bán hàng giày sneaker Five_Shoes");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(401, 401, 401)
                .addComponent(lbl_nameud, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(498, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lbl_nameud, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1320, 40));

        jtab.setBackground(new java.awt.Color(0, 153, 153));
        jtab.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Home.setBackground(new java.awt.Color(255, 255, 255));
        Home.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Home.setForeground(new java.awt.Color(204, 204, 204));
        Home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(204, 204, 204));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_truoc.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_truoc.setText("<");
        btn_truoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_truoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truocActionPerformed(evt);
            }
        });
        jPanel19.add(btn_truoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 69, 20));

        btn_sau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_sau.setText(">");
        btn_sau.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_sau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sauActionPerformed(evt);
            }
        });
        jPanel19.add(btn_sau, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 65, 20));

        lbl_sotrangspct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_sotrangspct.setText("1");
        jPanel19.add(lbl_sotrangspct, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 50, 20));

        tblSanPhamBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_SPCT", "Mã SPCT", "ID SP", "Loại giày ", "Size", "Thương hiệu", "Đế giày ", "Màu sắc", "Chất liệu", "Giá", "Số lượng ", "Trạng thái "
            }
        ));
        tblSanPhamBanHang.setRowHeight(25);
        tblSanPhamBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamBanHangMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblSanPhamBanHang);

        jPanel19.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 800, 160));

        jPanel29.setBackground(new java.awt.Color(204, 204, 204));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setText("Mã Sản Phẩm");

        txtTimMaSPBanHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTimMaSPBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimMaSPBanHangActionPerformed(evt);
            }
        });
        txtTimMaSPBanHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimMaSPBanHangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimMaSPBanHangKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimMaSPBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtTimMaSPBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 530, 50));

        cboLoaiGiay2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiGiay2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboLoaiGiay2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLoaiGiay2MouseClicked(evt);
            }
        });
        cboLoaiGiay2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiGiay2ActionPerformed(evt);
            }
        });
        jPanel19.add(cboLoaiGiay2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 90, -1));

        cboMau2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMau2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboMau2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMau2ActionPerformed(evt);
            }
        });
        jPanel19.add(cboMau2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 90, -1));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setText("Loại Giày");
        jPanel19.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, -1, 20));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText("Đế giày");
        jPanel19.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 60, 20));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("Màu Sắc");
        jPanel19.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 60, 20));

        cboDeGiay2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDeGiay2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboDeGiay2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboDeGiay2MouseClicked(evt);
            }
        });
        cboDeGiay2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDeGiay2ActionPerformed(evt);
            }
        });
        jPanel19.add(cboDeGiay2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 90, -1));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setText("Thương Hiệu");
        jPanel19.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 90, 20));

        cboThuongHieu2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThuongHieu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboThuongHieu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboThuongHieu2MouseClicked(evt);
            }
        });
        cboThuongHieu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThuongHieu2ActionPerformed(evt);
            }
        });
        jPanel19.add(cboThuongHieu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 90, -1));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("Chất Liệu ");
        jPanel19.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, -1, 20));

        cboChatLieu2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChatLieu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cboChatLieu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboChatLieu2MouseClicked(evt);
            }
        });
        cboChatLieu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieu2ActionPerformed(evt);
            }
        });
        jPanel19.add(cboChatLieu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 90, -1));

        btn_themGioHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_themGioHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Accept.png"))); // NOI18N
        btn_themGioHang.setText("Chọn sản phẩm");
        btn_themGioHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_themGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themGioHangActionPerformed(evt);
            }
        });
        jPanel19.add(btn_themGioHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 130, 30));

        btnTkiemMaSPBanHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTkiemMaSPBanHang.setText("Lọc");
        btnTkiemMaSPBanHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTkiemMaSPBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTkiemMaSPBanHangActionPerformed(evt);
            }
        });
        jPanel19.add(btnTkiemMaSPBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 80, 30));

        Home.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 820, 360));

        panelgiohang.setBackground(new java.awt.Color(204, 204, 204));
        panelgiohang.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(3, 3, 3))); // NOI18N
        panelgiohang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_giohang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID spct", "Tên sản phẩm", "Số lượng", "Size", "Màu", "Loại đế ", "Chất liệu", "Đơn giá", "Tổng tiền"
            }
        ));
        tbl_giohang.setRowHeight(25);
        tbl_giohang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_giohangMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbl_giohang);

        panelgiohang.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 800, 150));

        btn_xoa1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_xoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Delete.png"))); // NOI18N
        btn_xoa1.setText("Xóa ");
        btn_xoa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_xoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoa1ActionPerformed(evt);
            }
        });
        panelgiohang.add(btn_xoa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 180, 70, 30));

        btn_congGioHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_congGioHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add.png"))); // NOI18N
        btn_congGioHang.setText("Sửa");
        btn_congGioHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_congGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_congGioHangActionPerformed(evt);
            }
        });
        panelgiohang.add(btn_congGioHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, 70, 30));

        Home.add(panelgiohang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 820, 220));

        jPanel31.setBackground(new java.awt.Color(204, 204, 204));
        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa đơn chờ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_truochoadon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_truochoadon.setText("<");
        btn_truochoadon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_truochoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truochoadonActionPerformed(evt);
            }
        });
        jPanel31.add(btn_truochoadon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 70, 20));

        btn_nexthoadon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_nexthoadon.setText(">");
        btn_nexthoadon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_nexthoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nexthoadonActionPerformed(evt);
            }
        });
        jPanel31.add(btn_nexthoadon, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 70, 20));

        lbl_tranghoadon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_tranghoadon.setText("1");
        jPanel31.add(lbl_tranghoadon, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 60, 20));

        tbl_HoaDonBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Hóa Đơn ", "Mã Hóa Đơn ", "Tên Khách Hàng", "Ngày Đặt ", "Ghi Chú ", "Tên Khuyến Mãi", "Tên Nhân Viên", "Tổng Tiền ", "Trạng Thái ", "TKM"
            }
        ));
        tbl_HoaDonBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HoaDonBanHangMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tbl_HoaDonBanHang);
        if (tbl_HoaDonBanHang.getColumnModel().getColumnCount() > 0) {
            tbl_HoaDonBanHang.getColumnModel().getColumn(9).setMinWidth(0);
            tbl_HoaDonBanHang.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbl_HoaDonBanHang.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jPanel31.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 800, 110));

        Home.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 820, 170));

        panelThanhToan.setBackground(new java.awt.Color(204, 204, 204));
        panelThanhToan.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btn_themkhachhang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_themkhachhang.setText("+");
        btn_themkhachhang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_themkhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themkhachhangActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setText("Khách hàng");

        txt_tenkhachtt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_khachdua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_khachdua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_khachduaActionPerformed(evt);
            }
        });
        txt_khachdua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_khachduaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_khachduaKeyReleased(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel46.setText("Tổng tiền");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setText("Tiền khách đưa");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setText("Trả lại");

        lbl_tienthua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btn_thanhtoan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_thanhtoan.setText("THANH TOÁN");
        btn_thanhtoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_thanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thanhtoanActionPerformed(evt);
            }
        });

        btn_taohoadon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_taohoadon.setText("TẠO HÓA ĐƠN CHỜ");
        btn_taohoadon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_taohoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taohoadonActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setText("Mã hóa đơn");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel50.setText("Còn thiếu");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel51.setText("Khuyến Mại ");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel52.setText("Thanh toán");

        lbl_thanhtoan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnHuyHoaDonn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHuyHoaDonn.setText("HỦY HÓA ĐƠN");
        btnHuyHoaDonn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnHuyHoaDonn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonnActionPerformed(evt);
            }
        });

        btn_vangLai.setText("Khách vãng lai");
        btn_vangLai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_vangLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vangLaiActionPerformed(evt);
            }
        });

        btn_timKhachTheoSDT.setText("Tìm theo SDT");
        btn_timKhachTheoSDT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_timKhachTheoSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timKhachTheoSDTActionPerformed(evt);
            }
        });

        txt_khuyenmai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_khuyenmai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_khuyenmaiActionPerformed(evt);
            }
        });

        btnApMaKhuyenMai.setText("Áp mã");
        btnApMaKhuyenMai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnApMaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApMaKhuyenMaiActionPerformed(evt);
            }
        });

        txt_tongtien.setFont(new java.awt.Font("Noto Sans CJK HK", 1, 12)); // NOI18N

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setText("Loại Khuyến Mại ");

        javax.swing.GroupLayout panelThanhToanLayout = new javax.swing.GroupLayout(panelThanhToan);
        panelThanhToan.setLayout(panelThanhToanLayout);
        panelThanhToanLayout.setHorizontalGroup(
            panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThanhToanLayout.createSequentialGroup()
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel49)
                        .addGap(34, 34, 34)
                        .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txt_tenkhachtt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_themkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btn_timKhachTheoSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_vangLai, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btn_taohoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnHuyHoaDonn, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelThanhToanLayout.createSequentialGroup()
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelThanhToanLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                                        .addComponent(jLabel50)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_thieu, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(lbl_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelThanhToanLayout.createSequentialGroup()
                                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel70)
                                    .addGroup(panelThanhToanLayout.createSequentialGroup()
                                        .addComponent(btnApMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel51)))
                                .addGap(24, 24, 24)
                                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_khuyenmai, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_loaikm, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_khachdua, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(53, 53, 53))
        );
        panelThanhToanLayout.setVerticalGroup(
            panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThanhToanLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(txt_tenkhachtt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_timKhachTheoSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_vangLai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnApMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_khuyenmai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(lbl_loaikm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_khachdua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel50)
                    .addComponent(lbl_thieu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_taohoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuyHoaDonn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));
        jPanel15.setForeground(new java.awt.Color(204, 204, 204));

        jcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel15.add(jcam);
        jPanel15.add(lbl_maqr);

        javax.swing.GroupLayout viewbanhangLayout = new javax.swing.GroupLayout(viewbanhang);
        viewbanhang.setLayout(viewbanhangLayout);
        viewbanhangLayout.setHorizontalGroup(
            viewbanhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewbanhangLayout.createSequentialGroup()
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewbanhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        viewbanhangLayout.setVerticalGroup(
            viewbanhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewbanhangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(viewbanhangLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(viewbanhangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(panelThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jtab.addTab("tab5", viewbanhang);

        jPanel22.setBackground(new java.awt.Color(204, 204, 204));
        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 3, 3), 1, true));

        jPanel23.setBackground(new java.awt.Color(204, 204, 204));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khách hàng ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        buttonGroup4.add(rdo_namKH);
        rdo_namKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo_namKH.setText("Nam");

        buttonGroup4.add(rdo_nuKH);
        rdo_nuKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo_nuKH.setText("Nữ");
        rdo_nuKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_nuKHActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Mã khách hàng");

        txt_maKh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_maKh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_maKhKeyPressed(evt);
            }
        });

        txt_tenKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_dienthoaiKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_emailKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Tên khách hàng ");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Số điện thoại");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Giới tính ");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Email");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Địa chỉ ");

        txt_diachiKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnThemKh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemKh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add.png"))); // NOI18N
        btnThemKh.setText("Thêm");
        btnThemKh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnThemKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhActionPerformed(evt);
            }
        });

        btnSuaKh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaKh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Refresh.png"))); // NOI18N
        btnSuaKh.setText("Sửa");
        btnSuaKh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSuaKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhActionPerformed(evt);
            }
        });

        btnNewKh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNewKh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Clipboard.png"))); // NOI18N
        btnNewKh.setText("New");
        btnNewKh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNewKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewKhActionPerformed(evt);
            }
        });

        btnDoiTrangThaiKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDoiTrangThaiKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Gear.png"))); // NOI18N
        btnDoiTrangThaiKH.setText("Đổi trạng thái ");
        btnDoiTrangThaiKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDoiTrangThaiKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiTrangThaiKHActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Id Khách Hàng");

        lblIDKhachHang.setText("jLabel8");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel60.setText("Trạng thái ");

        buttonGroup5.add(rdoKhongHoatDongKH);
        rdoKhongHoatDongKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoKhongHoatDongKH.setText("Không hoạt động ");

        buttonGroup5.add(rdoDangHoatDongKH);
        rdoDangHoatDongKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoDangHoatDongKH.setText("Đang hoạt động ");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_maKh, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIDKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dienthoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(130, 130, 130)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoDangHoatDongKH)
                        .addGap(24, 24, 24)
                        .addComponent(rdoKhongHoatDongKH)
                        .addGap(51, 51, 51)
                        .addComponent(btnDoiTrangThaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel21))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_diachiKH, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                                    .addComponent(txt_emailKH)))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_namKH, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(rdo_nuKH, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnThemKh, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewKh, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_emailKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(lblIDKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_diachiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_maKh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnThemKh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdo_namKH)
                                    .addComponent(rdo_nuKH))))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewKh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dienthoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoKhongHoatDongKH)
                    .addComponent(rdoDangHoatDongKH)
                    .addComponent(btnDoiTrangThaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jPanel24.setBackground(new java.awt.Color(204, 204, 204));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách khách hàng ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblBangKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Khách Hàng", "Mã khách hàng ", "Tên khách hàng ", "Số điện thoại", "Email", "Địa chỉ ", "Giới tính ", "Trạng Thái"
            }
        ));
        tblBangKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBangKhachHang);

        txtTkiemKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_sauKH.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btn_sauKH.setText(">");
        btn_sauKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_sauKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sauKHActionPerformed(evt);
            }
        });

        lbl_trangKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_trangKH.setText("1");

        btn_truocKH.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btn_truocKH.setText("<");
        btn_truocKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_truocKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truocKHActionPerformed(evt);
            }
        });

        btnTimKiemKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiemKH.setText("Tìm kiếm tên");
        btnTimKiemKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTimKiemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(425, 425, 425)
                        .addComponent(btn_truocKH, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147)
                        .addComponent(lbl_trangKH, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(btn_sauKH, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(txtTkiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTkiemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnTimKiemKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sauKH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_trangKH, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_truocKH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout viewkhachhangLayout = new javax.swing.GroupLayout(viewkhachhang);
        viewkhachhang.setLayout(viewkhachhangLayout);
        viewkhachhangLayout.setHorizontalGroup(
            viewkhachhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewkhachhangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        viewkhachhangLayout.setVerticalGroup(
            viewkhachhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewkhachhangLayout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtab.addTab("tab6", viewkhachhang);

        viewhoadon.setBackground(new java.awt.Color(204, 204, 204));

        jPanel26.setBackground(new java.awt.Color(204, 204, 204));
        jPanel26.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 3, 3), 1, true));
        jPanel26.setPreferredSize(new java.awt.Dimension(1197, 1871));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách hóa đơn ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblBangHoaDonHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Hóa Đơn ", "Mã Hóa Đơn ", "Tên Khách Hàng", "Số điện thoại", "Ngày Đặt ", "Ghi Chú ", "Tên Khuyến Mãi", "Tên Nhân Viên", "Tổng Tiền ", "Trạng thái "
            }
        ));
        tblBangHoaDonHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangHoaDonHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblBangHoaDonHD);

        btn_truocHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_truocHD.setText("<");
        btn_truocHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_truocHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truocHDActionPerformed(evt);
            }
        });

        lbl_trangHD.setText("1");

        btn_sauHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_sauHD.setText(">");
        btn_sauHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_sauHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sauHDActionPerformed(evt);
            }
        });

        btn_maxHD.setText(">>");
        btn_maxHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_maxHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_maxHDActionPerformed(evt);
            }
        });

        btn_minHD.setText("<<");
        btn_minHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_minHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_minHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(358, 358, 358)
                        .addComponent(btn_minHD, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btn_truocHD, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(lbl_trangHD)
                        .addGap(111, 111, 111)
                        .addComponent(btn_sauHD, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btn_maxHD, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_trangHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_sauHD, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addComponent(btn_maxHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_minHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_truocHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa đơn chi tiết ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblBangHoaDonCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id Hóa Đơn Chi Tiết", "Id Hóa Đơn ", "Id Sản Phẩm Chi Tiết ", "Số Lượng ", "Đơn Giá", "Thành Tiền "
            }
        ));
        jScrollPane3.setViewportView(tblBangHoaDonCTHD);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        txtTimKiemTheoTenKhachHangHDON.setToolTipText("");
        txtTimKiemTheoTenKhachHangHDON.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTimKiemTheoTenKhachHangHDON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemTheoTenKhachHangHDONActionPerformed(evt);
            }
        });

        txtTimKiemTheoNgayHDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnTimKiemSoDienThoaiHD.setText("Tìm kiếm ");
        btnTimKiemSoDienThoaiHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTimKiemSoDienThoaiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSoDienThoaiHDActionPerformed(evt);
            }
        });

        txtTimKiemTheoNgayKTHDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Đến ");

        jLabel17.setText("Từ Ngày ");

        jLabel23.setText("Tên hoặc số điện thoại");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiemTheoTenKhachHangHDON, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemTheoNgayHDon, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemTheoNgayKTHDon, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnTimKiemSoDienThoaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(333, 333, 333))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimKiemSoDienThoaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtTimKiemTheoTenKhachHangHDON, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)
                        .addComponent(txtTimKiemTheoNgayHDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimKiemTheoNgayKTHDon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 1252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout viewhoadonLayout = new javax.swing.GroupLayout(viewhoadon);
        viewhoadon.setLayout(viewhoadonLayout);
        viewhoadonLayout.setHorizontalGroup(
            viewhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewhoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        viewhoadonLayout.setVerticalGroup(
            viewhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewhoadonLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );

        jtab.addTab("tab7", viewhoadon);

        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 3, 3), 1, true));

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        buttonGroup2.add(rdo_namNV);
        rdo_namNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo_namNV.setSelected(true);
        rdo_namNV.setText("Nam");

        buttonGroup2.add(rdo_nuNV);
        rdo_nuNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo_nuNV.setText("Nữ");
        rdo_nuNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_nuNVActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mã nhân viên");

        txt_maNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_tenDNNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_mkNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_emailNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tên đăng nhập");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Mật khẩu");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Giới tính ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Email");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Số điện thoại");

        txt_sdtNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_ThemNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_ThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add.png"))); // NOI18N
        btn_ThemNV.setText("Thêm");
        btn_ThemNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_ThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNVActionPerformed(evt);
            }
        });

        btn_SuaNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_SuaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Refresh.png"))); // NOI18N
        btn_SuaNV.setText("Sửa");
        btn_SuaNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_SuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaNVActionPerformed(evt);
            }
        });

        btn_LamMoiNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_LamMoiNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Clipboard.png"))); // NOI18N
        btn_LamMoiNV.setText("New");
        btn_LamMoiNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_LamMoiNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiNVActionPerformed(evt);
            }
        });

        btn_DoiTTNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_DoiTTNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Gear.png"))); // NOI18N
        btn_DoiTTNV.setText("Đổi trạng thái ");
        btn_DoiTTNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_DoiTTNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DoiTTNVActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("ID chức vụ");

        txt_idchucvuNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("Lương");

        txt_luongNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("Trạng thái");

        buttonGroup3.add(rdo_hdNV);
        rdo_hdNV.setSelected(true);
        rdo_hdNV.setText("Hoạt động");

        buttonGroup3.add(rdo_khdNV);
        rdo_khdNV.setText("Không hoạt động");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Id Nhân Viên");

        lblIDNhanVien.setText("jLabel53");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Tên nhân viên");

        txt_tenNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(rdo_namNV, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(rdo_nuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_mkNV, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_tenDNNV, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_maNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                            .addComponent(lblIDNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tenNhanVien))
                        .addGap(96, 96, 96)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel14)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))))
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_luongNV, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_idchucvuNV, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_sdtNV, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_emailNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(rdo_hdNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdo_khdNV)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btn_SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_LamMoiNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_DoiTTNV, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_ThemNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(121, 121, 121))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_emailNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblIDNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_sdtNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_maNV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_tenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_idchucvuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_tenDNNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_luongNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btn_LamMoiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_mkNV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_DoiTTNV, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdo_namNV)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdo_nuNV)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdo_hdNV)
                            .addComponent(rdo_khdNV)
                            .addComponent(jLabel29))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tbl_bangNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id nhân viên", "Mã nhân viên", "Tên nhân viên", "Tên đăng nhập", "Mật khẩu ", "Giới tính ", "Email", "Số điện thoại", "ID chức vụ", "Lương", "Trạng thái"
            }
        ));
        tbl_bangNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangNhanVienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_bangNhanVien);

        txt_tkNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_TruocNv.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btn_TruocNv.setText("<");
        btn_TruocNv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_TruocNv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TruocNvActionPerformed(evt);
            }
        });

        btn_SauNV.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btn_SauNV.setText(">");
        btn_SauNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_SauNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SauNVActionPerformed(evt);
            }
        });

        btn_TKNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Zoom.png"))); // NOI18N
        btn_TKNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_TKNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TKNVActionPerformed(evt);
            }
        });

        lbl_trangNV.setText("1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(txt_tkNV, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btn_TKNV, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addComponent(btn_TruocNv, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(lbl_trangNV, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138)
                        .addComponent(btn_SauNV, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_tkNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TKNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_TruocNv, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_SauNV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lbl_trangNV, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewnhanvienLayout = new javax.swing.GroupLayout(viewnhanvien);
        viewnhanvien.setLayout(viewnhanvienLayout);
        viewnhanvienLayout.setHorizontalGroup(
            viewnhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewnhanvienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        viewnhanvienLayout.setVerticalGroup(
            viewnhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewnhanvienLayout.createSequentialGroup()
                .addContainerGap(168, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtab.addTab("tab8", viewnhanvien);

        viewsp.setBackground(new java.awt.Color(204, 204, 204));

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel33.setBackground(new java.awt.Color(204, 204, 204));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel33.setAutoscrolls(true);

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setText("ID Sản Phẩm");

        txtMaSp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaSpKeyPressed(evt);
            }
        });

        txtTensp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("Mã Sản Phẩm ");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("Tên Sản Phẩm");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setText("Mô tả");

        btn_themSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_themSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add.png"))); // NOI18N
        btn_themSP.setText("Thêm");
        btn_themSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_themSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themSPActionPerformed(evt);
            }
        });

        btn_suaSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_suaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Refresh.png"))); // NOI18N
        btn_suaSP.setText("Sửa");
        btn_suaSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_suaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaSPActionPerformed(evt);
            }
        });

        btn_newSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_newSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Clipboard.png"))); // NOI18N
        btn_newSP.setText("New");
        btn_newSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_newSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newSPActionPerformed(evt);
            }
        });

        btn_xemctSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_xemctSP.setText("Xem chi tiết");
        btn_xemctSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_xemctSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xemctSPActionPerformed(evt);
            }
        });

        txtMota.setColumns(20);
        txtMota.setRows(5);
        txtMota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane10.setViewportView(txtMota);

        btndoiTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btndoiTT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Gear.png"))); // NOI18N
        btndoiTT.setText("Đổi trạng thái ");
        btndoiTT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btndoiTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndoiTTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(jLabel53))
                .addGap(26, 26, 26)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTensp, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                    .addComponent(txtMaSp)
                    .addComponent(lblIDSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(jLabel57)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(btn_xemctSP, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_suaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_themSP, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_newSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndoiTT, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(70, 70, 70))))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel33Layout.createSequentialGroup()
                            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_suaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_newSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_themSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btndoiTT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_xemctSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel34.setBackground(new java.awt.Color(204, 204, 204));
        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblBangSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Sản Phẩm", "Mã Sản Phẩm", "Tên Sản Phẩm", "Mô Tả", "Trạng Thái "
            }
        ));
        tblBangSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangSanPhamMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblBangSanPham);

        txtTimSp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_truocSP.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btn_truocSP.setText("<");
        btn_truocSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_truocSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truocSPActionPerformed(evt);
            }
        });

        lbl_trangSP.setText("1");

        btn_sauSP.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btn_sauSP.setText(">");
        btn_sauSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_sauSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sauSPActionPerformed(evt);
            }
        });

        btn_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_timKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Search.png"))); // NOI18N
        btn_timKiem.setText("Tìm kiếm");
        btn_timKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timKiemActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel59.setText("Tên Sản Phẩm ");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(476, 476, 476)
                        .addComponent(btn_truocSP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(lbl_trangSP, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(btn_sauSP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel59)
                        .addGap(44, 44, 44)
                        .addComponent(txtTimSp, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(btn_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(408, Short.MAX_VALUE))
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimSp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_truocSP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_trangSP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sauSP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 118, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSPLayout = new javax.swing.GroupLayout(panelSP);
        panelSP.setLayout(panelSPLayout);
        panelSPLayout.setHorizontalGroup(
            panelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSPLayout.setVerticalGroup(
            panelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSPLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout viewspLayout = new javax.swing.GroupLayout(viewsp);
        viewsp.setLayout(viewspLayout);
        viewspLayout.setHorizontalGroup(
            viewspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewspLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        viewspLayout.setVerticalGroup(
            viewspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtab.addTab("tab9", viewsp);

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 3, 3), 1, true));

        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 3, 3), 1, true));

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Mã giảm giá");

        txtMaKhuyenMai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaKhuyenMai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaKhuyenMaiKeyPressed(evt);
            }
        });

        txtTenKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtSoluongKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("Tên khuyến mãi");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Loại khuyến mãi");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("Ngày bắt đầu");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("Số lượng");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setText("Ngày kết thúc");

        txtNgayKTKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_themKM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_themKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Add.png"))); // NOI18N
        btn_themKM.setText("Thêm");
        btn_themKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_themKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themKMActionPerformed(evt);
            }
        });

        btn_suaKM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_suaKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Refresh.png"))); // NOI18N
        btn_suaKM.setText("Sửa");
        btn_suaKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_suaKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaKMActionPerformed(evt);
            }
        });

        btnNewKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNewKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Clipboard.png"))); // NOI18N
        btnNewKhuyenMai.setText("New");
        btnNewKhuyenMai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNewKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewKhuyenMaiActionPerformed(evt);
            }
        });

        btnDoiTrangThaiKM.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDoiTrangThaiKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Gear.png"))); // NOI18N
        btnDoiTrangThaiKM.setText("Đổi trạng thái ");
        btnDoiTrangThaiKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDoiTrangThaiKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiTrangThaiKMActionPerformed(evt);
            }
        });

        txtNgayBDKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("Trạng thái");

        buttonGroup1.add(rdo_hdKM);
        rdo_hdKM.setSelected(true);
        rdo_hdKM.setText("Hoạt động");

        buttonGroup1.add(rdo_khdKM);
        rdo_khdKM.setText("Không hoạt động");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setText("Id khuyến mãi ");

        cbo_LoaiKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_LoaiKMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_themKM, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel30)
                            .addComponent(jLabel7)
                            .addComponent(jLabel54))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenKM, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                            .addComponent(txtMaKhuyenMai)
                            .addComponent(lblIdKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbo_LoaiKM, 0, 275, Short.MAX_VALUE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(48, 48, 48)
                                .addComponent(rdo_hdKM, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(85, 85, 85)
                                .addComponent(rdo_khdKM, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNgayKTKM, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel32))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNgayBDKM, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                    .addComponent(txtSoluongKM))))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(btn_suaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNewKhuyenMai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDoiTrangThaiKM, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoluongKM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(lblIdKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_suaKM, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayBDKM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themKM, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayKTKM, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdo_hdKM)
                            .addComponent(rdo_khdKM)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDoiTrangThaiKM, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(cbo_LoaiKM, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addGap(26, 26, 26))))
        );

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách Khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblBangKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID khuyến mãi", "Mã giảm giá", "Tên Khuyến mãi", "Loại khuyến mãi", "Số lượng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ));
        tblBangKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblBangKhuyenMai);

        txt_TKKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnTruocKM.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTruocKM.setText("<");
        btnTruocKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTruocKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruocKMActionPerformed(evt);
            }
        });

        lblTrangKM.setText("1");

        btnSauKM.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSauKM.setText(">");
        btnSauKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSauKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSauKMActionPerformed(evt);
            }
        });

        btnTimKiemKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Zoom.png"))); // NOI18N
        btnTimKiemKM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTimKiemKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txt_TKKM, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnTimKiemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(774, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(btnTruocKM, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215)
                .addComponent(lblTrangKM, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187)
                .addComponent(btnSauKM, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(332, 332, 332))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_TKKM)
                    .addComponent(btnTimKiemKM, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTruocKM, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTrangKM, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSauKM, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1268, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 733, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout viewkhuyenmaiLayout = new javax.swing.GroupLayout(viewkhuyenmai);
        viewkhuyenmai.setLayout(viewkhuyenmaiLayout);
        viewkhuyenmaiLayout.setHorizontalGroup(
            viewkhuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1308, Short.MAX_VALUE)
            .addGroup(viewkhuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(viewkhuyenmaiLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(32, Short.MAX_VALUE)))
        );
        viewkhuyenmaiLayout.setVerticalGroup(
            viewkhuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 887, Short.MAX_VALUE)
            .addGroup(viewkhuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewkhuyenmaiLayout.createSequentialGroup()
                    .addContainerGap(138, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)))
        );

        jtab.addTab("tab10", viewkhuyenmai);

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(3, 3, 3), 1, true));

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm dữ liệu theo ngày , tháng , năm ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(3, 3, 3))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Từ Ngày ");

        txt_ngaybdtk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ngaybdtk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ngaybdtkActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setText("Đến Ngày");

        txt_ngaykttk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ngaykttk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ngaykttkActionPerformed(evt);
            }
        });

        btn_xem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_xem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Search.png"))); // NOI18N
        btn_xem.setText("Tra Cứu");
        btn_xem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_xem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xemActionPerformed(evt);
            }
        });

        btnBieuDo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBieuDo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Bar chart.png"))); // NOI18N
        btnBieuDo.setText("Biểu đồ thống kê ");
        btnBieuDo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBieuDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBieuDoActionPerformed(evt);
            }
        });

        btnXuatFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Open file.png"))); // NOI18N
        btnXuatFile.setText("Xuất File Excel");
        btnXuatFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(txt_ngaybdtk, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(434, 434, 434)
                        .addComponent(btnBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(txt_ngaykttk, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(424, 424, 424)
                        .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(141, 141, 141))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(322, 322, 322)
                .addComponent(btn_xem, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_ngaybdtk, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txt_ngaykttk, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btn_xem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dữ liệu được thống kê", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(3, 3, 3))); // NOI18N

        tbl_bangthongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Ngày Đặt", "Tổng Tiền", "Sản Phẩm"
            }
        ));
        tbl_bangthongke.setRowHeight(25);
        jScrollPane6.setViewportView(tbl_bangthongke);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1203, Short.MAX_VALUE)
                .addGap(56, 56, 56))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setText("Tổng Doanh Thu :");

        lbltongtien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel41)
                        .addGap(55, 55, 55)
                        .addComponent(lbltongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(lbltongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewthongkeLayout = new javax.swing.GroupLayout(viewthongke);
        viewthongke.setLayout(viewthongkeLayout);
        viewthongkeLayout.setHorizontalGroup(
            viewthongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewthongkeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        viewthongkeLayout.setVerticalGroup(
            viewthongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewthongkeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jtab.addTab("tab11", viewthongke);

        jPanel1.add(jtab, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, -30, 1310, 820));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BANHANGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BANHANGActionPerformed
        jtab.setSelectedIndex(0);
    }//GEN-LAST:event_btn_BANHANGActionPerformed

    private void btn_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLKHActionPerformed
        jtab.setSelectedIndex(1);
    }//GEN-LAST:event_btn_QLKHActionPerformed

    private void btn_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLHDActionPerformed
        jtab.setSelectedIndex(2);
    }//GEN-LAST:event_btn_QLHDActionPerformed

    private void btn_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLNVActionPerformed
        if (lbl_chucvu.getText().equals("Nhân viên")) {
            JOptionPane.showMessageDialog(this, "Chức năng cho quản lý");
        } else {
            jtab.setSelectedIndex(3);
        }
    }//GEN-LAST:event_btn_QLNVActionPerformed

    private void btn_QLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLSPActionPerformed
        if (lbl_chucvu.getText().equals("Nhân viên")) {
            JOptionPane.showMessageDialog(this, "Chức năng cho quản lý");
        } else {
            jtab.setSelectedIndex(4);
        }
    }//GEN-LAST:event_btn_QLSPActionPerformed

    private void btn_QLKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLKMActionPerformed
        if (lbl_chucvu.getText().equals("Nhân viên")) {
            JOptionPane.showMessageDialog(this, "Chức năng cho quản lý");
        } else {
            jtab.setSelectedIndex(5);
        }
    }//GEN-LAST:event_btn_QLKMActionPerformed

    private void btn_THONGKEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_THONGKEActionPerformed
        if (lbl_chucvu.getText().equals("Nhân viên")) {
            JOptionPane.showMessageDialog(this, "Chức năng cho quản lý");
        } else {
            jtab.setSelectedIndex(6);
        }
    }//GEN-LAST:event_btn_THONGKEActionPerformed

    private void rdo_nuKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_nuKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_nuKHActionPerformed

    private void btnSuaKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhActionPerformed
        KhachHang khh = new KhachHang();
        khh.setIdKhachHang(Integer.parseInt(lblIDKhachHang.getText()));
        khh.setMaKhachHang(txt_maKh.getText());
        khh.setTenKhachHang(txt_tenKH.getText());
        khh.setSoDienThoai(txt_dienthoaiKH.getText());
        khh.setEmail(txt_emailKH.getText());
        khh.setDiaChi(txt_diachiKH.getText());
        boolean gioiTinh;
        if (rdo_namKH.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        khh.setGioiTinh(gioiTinh);
        boolean trangThai;
        if (rdoDangHoatDongKH.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        khh.setTrangThai(trangThai);

        if (checkTrongKhachHang()) {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa khách hàng không");
            if (chon == JOptionPane.YES_OPTION) {
                khachHangService_IMPL.updateKhachHang(khh);
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                filltableKhachHang(idKH);
            } else if (chon == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Sửa không thành công");
        }

    }//GEN-LAST:event_btnSuaKhActionPerformed

    private void rdo_nuNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_nuNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_nuNVActionPerformed

    private void btn_SuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaNVActionPerformed
        NhanVien nv = new NhanVien();
        nv.setMaNV(txt_maNV.getText());
        nv.setTenNV(txt_tenNhanVien.getText());
        nv.setTenDN(txt_tenDNNV.getText());
        nv.setMatKhau(txt_mkNV.getText());
        boolean gioiTinh;
        if (rdo_namNV.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        nv.setgTinh(gioiTinh);

        nv.setEmail(txt_emailNV.getText());
        nv.setSdt(txt_sdtNV.getText());
        nv.setIdChucVu(Integer.parseInt(txt_idchucvuNV.getText()));
        nv.setLuong(Integer.parseInt(txt_luongNV.getText()));
        boolean trangThai;
        if (rdo_hdNV.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        nv.setTt(trangThai);
        if (checkTrongKhachHang()) {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm nhân viên không");
            if (chon == JOptionPane.YES_OPTION) {
                nhanVienService_IMPL.UpdateNhanVien(nv);
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                this.filltableNhanVien(idNV);
            } else if (chon == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công");
        }
    }//GEN-LAST:event_btn_SuaNVActionPerformed

    private void btn_suaKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaKMActionPerformed
        // TODO add your handling code here:
        Khuyenmai km = new Khuyenmai();

        km.setMaGiamGiaKM(txtMaKhuyenMai.getText());
        km.setTenKM(txtTenKM.getText());
        km.setLoaiKM(cbo_LoaiKM.getSelectedItem().toString());
        km.setSoLuongKM(Integer.parseInt(txtSoluongKM.getText()));

        String ngaybdkm1 = txtNgayBDKM.getText();
        SimpleDateFormat ngaybdkm = new SimpleDateFormat("yyyy-MM-dd");
        Date ngaybdkm2 = null;
        try {
            ngaybdkm2 = ngaybdkm.parse(ngaybdkm1);
        } catch (Exception e) {
        }
        km.setNgayBatDauKM(ngaybdkm2);
        String ngayktkm1 = txtNgayKTKM.getText();
        SimpleDateFormat ngayktkm = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayktkm2 = null;
        try {
            ngayktkm2 = ngayktkm.parse(ngayktkm1);
        } catch (Exception e) {
        }
        km.setNgayKTKM(ngayktkm2);
        boolean trangThai;
        if (rdo_hdKM.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }

        if (checkTrongKhuyenMai()) {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm nhân viên không");
            if (chon == JOptionPane.YES_OPTION) {
                if (KhuyenMaiService_IMPL.UpdateKM(km)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    this.fillTableKhuyenMai();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
            }

        }
    }//GEN-LAST:event_btn_suaKMActionPerformed

    private void txt_ngaybdtkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ngaybdtkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ngaybdtkActionPerformed

    private void txt_ngaykttkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ngaykttkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ngaykttkActionPerformed

    private void btn_xemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xemActionPerformed
        // Kiểm tra xem các trường txt có trống không
        // Kiểm tra xem các trường txt có trống không
        String startDateString = txt_ngaybdtk.getText();
        String endDateString = txt_ngaykttk.getText();
        DefaultTableModel mols = (DefaultTableModel) tbl_bangthongke.getModel();
        int gia = 0;
        mol.setRowCount(0);
        if (startDateString.isEmpty() || endDateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ ngày");
            return; // Dừng xử lý nếu có trường trống
        } else {
            try {
                String sql = """
        SELECT 
            HOADON.NGAYDATDAT AS [Ngày Đặt],
            (SANPHAMCHITIET.GIA * HOADONCT.SOLUONG) AS [Thành Tiền],
            SANPHAM.TENSP AS [sp]
        FROM 
            HOADON
            JOIN HOADONCT ON HOADON.IDHOADON = HOADONCT.IDHOADON
            JOIN SANPHAMCHITIET ON HOADONCT.IDSANPHAMCHITIET = SANPHAMCHITIET.IDSANPHAMCHITIET
            JOIN SANPHAM ON SANPHAMCHITIET.IDSANPHAM = SANPHAM.IDSANPHAM
        WHERE 
            HOADON.NGAYDATDAT BETWEEN ? AND ?;
        """;

                Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // Chuyển đổi ngày từ chuỗi sang kiểu java.sql.Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);

                Date startDate;
                Date endDate;
                try {
                    startDate = new Date(dateFormat.parse(startDateString).getTime());
                    endDate = new Date(dateFormat.parse(endDateString).getTime());

                    // Kiểm tra ngày đặt đầu không lớn hơn ngày kết thúc
                    if (startDate.after(endDate)) {
                        JOptionPane.showMessageDialog(this, "Ngày đặt đầu không được lớn hơn ngày kết thúc");
                        return; // Dừng xử lý nếu ngày đặt đầu lớn hơn ngày kết thúc
                    }

                    // Gán giá trị cho các tham số dấu hỏi và thực hiện truy vấn SQL
                    pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                    pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

                    ResultSet rs = pstmt.executeQuery();

                    DefaultCategoryDataset barchartData = new DefaultCategoryDataset();
                    mols.setRowCount(0);
                    while (rs.next()) {
                        Date ngay = rs.getDate("Ngày Đặt");
                        int tongTien = rs.getInt("Thành Tiền");
                        String sp = rs.getString("sp");

                        // Kiểm tra nếu có dòng nào có giá trị ngày, sp và thành tiền hợp lệ thì thêm vào bảng
                        if (ngay != null && !sp.isEmpty() && tongTien != 0) {
                            Object[] todata = new Object[]{ngay, tongTien, sp};
                            mols.addRow(todata);
                            gia += tongTien; // Cập nhật tổng tiền
                        }
                    }

                    lbltongtien.setText(String.valueOf(gia));

                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
                    System.out.println("Định dạng ngày không hợp lệ!");
                    e.printStackTrace();
                    return;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_btn_xemActionPerformed

    private void btnBieuDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBieuDoActionPerformed
        // Kiểm tra xem các trường txt có trống không
        String startDateString = txt_ngaybdtk.getText();
        String endDateString = txt_ngaykttk.getText();

        if (startDateString.isEmpty() || endDateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui lòng nhập đủ ngày");
            return; // Dừng xử lý nếu có trường trống
        } else {
            try {
                String sql = """
         SELECT 
             HOADON.NGAYDATDAT AS [Ngày Đặt],
             (SANPHAMCHITIET.GIA * HOADONCT.SOLUONG) AS [Thành Tiền]
         FROM 
             HOADON
         JOIN 
             HOADONCT ON HOADON.IDHOADON = HOADONCT.IDHOADON
         JOIN 
             SANPHAMCHITIET ON HOADONCT.IDSANPHAMCHITIET = SANPHAMCHITIET.IDSANPHAMCHITIET
         JOIN 
             SANPHAM ON SANPHAMCHITIET.IDSANPHAM = SANPHAM.IDSANPHAM
         WHERE 
             HOADON.NGAYDATDAT BETWEEN ? AND ?;
         """;

                Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // Chuyển đổi ngày từ chuỗi sang kiểu java.sql.Date
                try {
                    // Lấy giá trị từ JTextField

                    // Kiểm tra định dạng ngày
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setLenient(false);

                    Date startDate = null;
                    Date endDate = null;

                    try {
                        startDate = new Date(dateFormat.parse(startDateString).getTime());
                        endDate = new Date(dateFormat.parse(endDateString).getTime());

                        // Gán giá trị cho các tham số dấu hỏi và thực hiện truy vấn SQL
                        pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                        pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

                        ResultSet rs = pstmt.executeQuery();

                        DefaultCategoryDataset barchartData = new DefaultCategoryDataset();
                        while (rs.next()) {
                            Date ngay = rs.getDate("Ngày Đặt");
                            System.out.println("Ngày: " + ngay); // In ra ngày để kiểm tra
                            int tongTien = rs.getInt("Thành Tiền");
                            String ngayString = new SimpleDateFormat("yyyy-MM-dd").format(ngay);
                            barchartData.addValue(tongTien, "Tổng Tiền", ngayString);
                        }

                        JFreeChart jChart = ChartFactory.createBarChart(
                                "Thống Kê",
                                "Ngày",
                                "Tổng Tiền",
                                barchartData,
                                PlotOrientation.VERTICAL,
                                false,
                                true,
                                false
                        );

                        CategoryPlot plot = jChart.getCategoryPlot();
                        BarRenderer renderer = (BarRenderer) plot.getRenderer();
                        renderer.setSeriesPaint(0, Color.BLUE);

                        ChartPanel chartPanel = new ChartPanel(jChart);
                        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 1700));

                        JFrame frame = new JFrame("Biểu Đồ");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setContentPane(chartPanel);
                        frame.pack();
                        frame.setVisible(true);

                    } catch (ParseException e) {
                        // Xử lý lỗi khi ngày không hợp lệ
                        System.out.println("Định dạng ngày không hợp lệ!");
                        e.printStackTrace();
                        return;
                    }

                    // Tiếp tục xử lý dữ liệu nếu ngày hợp lệ
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_btnBieuDoActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
//        try {
//
//            // Chuẩn bị câu truy vấn SQL
//            String sql = """
//                     SELECT 
//                         HOADON.NGAYDATDAT AS [Ngày Đặt],
//                         (SANPHAMCHITIET.GIA * HOADONCT.SOLUONG) AS [Thành Tiền]
//                     FROM 
//                         HOADON
//                     JOIN 
//                         HOADONCT ON HOADON.IDHOADON = HOADONCT.IDHOADON
//                     JOIN 
//                         SANPHAMCHITIET ON HOADONCT.IDSANPHAMCHITIET = SANPHAMCHITIET.IDSANPHAMCHITIET
//                     JOIN 
//                         SANPHAM ON SANPHAMCHITIET.IDSANPHAM = SANPHAM.IDSANPHAM
//                     WHERE 
//                         HOADON.NGAYDATDAT BETWEEN ? AND ?;
//                     """;
//
//            // Tạo PreparedStatement
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            // Thiết lập tham số cho câu truy vấn
//            pstmt.setString(1, "startDateValue");
//            pstmt.setString(2, "endDateValue");
//
//            // Thực thi câu truy vấn
//            ResultSet rs = pstmt.executeQuery();
//
//            // Tạo Workbook và Sheet
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = (Sheet) workbook.createSheet("Data");
//
//            // Tạo hàng tiêu đề
//            Row headerRow = sheet.createRow(0);
//            Row heaRow = sheet.createRow(0); // Đây là dòng mới được thêm vào
//
//            headerRow.createCell(0).setCellValue("Ngày Đặt");
//            headerRow.createCell(1).setCellValue("Thành Tiền");
//
//            int rowNum = 1;
//            // Ghi dữ liệu từ ResultSet vào tệp Excel
//            while (rs.next()) {
//                Row row = sheet.createRow(rowNum++);
//                String ngayDat = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("Ngày Đặt"));
//                row.createCell(0).setCellValue(ngayDat);
//                row.createCell(1).setCellValue(rs.getDouble("Thành Tiền"));
//            }
//
//            // Lưu Workbook vào một tệp Excel
//            String excelFilePath = "data.xlsx";
//            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
//                workbook.write(outputStream);
//            }
//
//            JOptionPane.showMessageDialog(this, "Dữ liệu đã được xuất thành công vào file Excel.");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xuất dữ liệu vào file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btn_truocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocActionPerformed
        // TODO add your handling code here:
        if (idSPCT >= 10) {
            idSPCT = idSPCT - 10;
            trangSPCT = trangSPCT - 1;
            fillTableSpct(idSPCT);
            lbl_sotrangspct.setText("" + trangSPCT);
        }
    }//GEN-LAST:event_btn_truocActionPerformed

    private void btn_sauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauActionPerformed
        // TODO add your handling code here:
        if (idSPCT >= 0 && idSPCT <= countSpct - 10) {
            idSPCT = idSPCT + 10;
            trangSPCT = trangSPCT + 1;
            fillTableSpct(idSPCT);
            lbl_sotrangspct.setText("" + trangSPCT);
        }
    }//GEN-LAST:event_btn_sauActionPerformed

    private void tblSanPhamBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamBanHangMouseClicked
        // TODO add your handling code here:
        int index = tblSanPhamBanHang.getSelectedRow();
        lblIDSanPham.setText(tblSanPhamBanHang.getValueAt(index, 0).toString());
        txtMaSp.setText(tblSanPhamBanHang.getValueAt(index, 1).toString());
        txtTensp.setText(tblSanPhamBanHang.getValueAt(index, 2).toString());
        txtMota.setText(tblSanPhamBanHang.getValueAt(index, 3).toString());
    }//GEN-LAST:event_tblSanPhamBanHangMouseClicked

    private void txtTimMaSPBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimMaSPBanHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimMaSPBanHangActionPerformed

    private void txtTimMaSPBanHangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimMaSPBanHangKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimMaSPBanHangKeyPressed

    private void txtTimMaSPBanHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimMaSPBanHangKeyReleased
        // TODO add your handling code here:
        String ma = txtTimMaSPBanHang.getText();
        mol = (DefaultTableModel) tblSanPhamBanHang.getModel();
        mol.setRowCount(0);
        for (SanPhamChiTiet s : spdChiTiet_IMPL.timSanPhamChiTiet(ma)) {
            Object[] todata = new Object[]{
                s.getIdSpct(), s.getMaSpct(), s.getIdsp(), s.getLoaiGiay(), s.getSize(), s.getThuongHieu(), s.getDeGiay(), s.getMauSac(), s.getChatLieu(), s.getGia(), s.getSoLuongSpct(), s.checkTrangThai()
            };
            mol.addRow(todata);
        }
    }//GEN-LAST:event_txtTimMaSPBanHangKeyReleased

    private void cboLoaiGiay2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLoaiGiay2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiGiay2MouseClicked

    private void cboLoaiGiay2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiGiay2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiGiay2ActionPerformed

    private void cboMau2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMau2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMau2ActionPerformed

    private void btn_xoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoa1ActionPerformed
        int index = tbl_giohang.getSelectedRow();
        int index1 = tbl_HoaDonBanHang.getSelectedRow();
        if (index1 != -1) {
            if (index != -1) {
                try {

                    index = tbl_giohang.getSelectedRow();
                    idhd = Integer.parseInt(String.valueOf(tbl_HoaDonBanHang.getValueAt(index1, 0)));
                    String mahoadon = tbl_HoaDonBanHang.getValueAt(index1, 1).toString();
                    HoaDonChiTiet gh = new HoaDonChiTiet();
                    gh.setIdSpct(Integer.parseInt(tbl_giohang.getValueAt(index, 0).toString()));
                    gh.setMaHoaDon(mahoadon);
                    gh.setSoLuong(Integer.parseInt(tbl_giohang.getValueAt(index, 2).toString()));
                    if (chiTietService_IMPL.xoa(gh)) {
                        fillGioHang(idhd);
                    }
                    fillGioHang(idhd);
                    fillTableSpct(idSPCT);
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm trong giỏ hàng");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn hoá đơn");
        }


    }//GEN-LAST:event_btn_xoa1ActionPerformed

    private void btnHuyHoaDonnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonnActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy hóa đơn hay không ?");
        if (chon == JOptionPane.YES_OPTION) {
            int index = tbl_giohang.getSelectedRow();
            int index1 = tbl_HoaDonBanHang.getSelectedRow();
            if (index1 != -1) {
                try {
                    // Lấy thông tin hoá đơn đã chọn
                    int idhd = Integer.parseInt(String.valueOf(tbl_HoaDonBanHang.getValueAt(index1, 0)));
                    String mahoadon = tbl_HoaDonBanHang.getValueAt(index1, 1).toString();

                    // Lặp qua tất cả các sản phẩm trong giỏ hàng
                    DefaultTableModel model = (DefaultTableModel) tbl_giohang.getModel();
                    for (int i = model.getRowCount() - 1; i >= 0; i--) {
                        // Lấy thông tin sản phẩm
                        int idSpct = Integer.parseInt(model.getValueAt(i, 0).toString());
                        int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());

                        // Tạo đối tượng HoaDonChiTiet để thực hiện xóa
                        HoaDonChiTiet gh = new HoaDonChiTiet();
                        gh.setIdSpct(idSpct);
                        gh.setMaHoaDon(mahoadon);
                        gh.setSoLuong(soLuong);

                        // Thực hiện xóa sản phẩm ra khỏi giỏ hàng
                        if (chiTietService_IMPL.xoa(gh)) {
                            // Nếu xóa thành công, cập nhật lại giao diện
                            model.removeRow(i); // Xóa hàng trong bảng giỏ hàng
                        }
                    }

                    // Hiển thị thông báo xóa thành công
                    System.out.println("xóa thành công sản phẩm khỏi giỏ hàng");
                    // Cập nhật lại bảng HOADONCT và bảng sản phẩm chi tiết (nếu cần)
                    fillGioHang(idhd);
                    fillTableSpct(idSPCT);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hiển thị thông báo xóa thất bại (nếu cần)
                    System.out.println(" Xóa sản phẩm khỏi giỏ hàng thất bại");
                }
            } else {
                // Hiển thị thông báo nếu chưa chọn hoá đơn
                JOptionPane.showMessageDialog(this, "Chưa chọn hoá đơn");
            }
            HoaDon huyHd = new HoaDon();
            huyHd.setMaHoaDon(tbl_HoaDonBanHang.getValueAt(index1, 1).toString());

            hoaDonService_IMPL.deleteHoaDon(huyHd);
            filltableHoaDonBanHang();
            JOptionPane.showMessageDialog(this, "Hủy thành công ");
            resret();
        } else if (chon == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Bạn đã hủy");
        }

    }//GEN-LAST:event_btnHuyHoaDonnActionPerformed

    private void tbl_giohangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_giohangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_giohangMouseClicked

    private void btn_truochoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truochoadonActionPerformed
        // TODO add your handling code here:
        if (idHD >= 10) {
            idHD = idHD - 10;
            trangHD = trangHD - 1;
            filltableHoaDonBanHang();
            lbl_tranghoadon.setText("" + trangHD);
        }
    }//GEN-LAST:event_btn_truochoadonActionPerformed

    private void btn_nexthoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nexthoadonActionPerformed
        // TODO add your handling code here:
        if (idHD >= 0 && idHD <= countHD - 10) {
            idHD = idHD + 10;
            trangHD = trangHD + 1;
            filltableHoaDonPhanTrang(idHD);
            lbl_tranghoadon.setText("" + trangHD);
        }
    }//GEN-LAST:event_btn_nexthoadonActionPerformed

    private void btn_themkhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themkhachhangActionPerformed
        jaddkh.setVisible(true);
        jaddkh.setSize(750, 370);
        jaddkh.setLocationRelativeTo(null);
    }//GEN-LAST:event_btn_themkhachhangActionPerformed

    private void txt_khachduaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_khachduaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_khachduaActionPerformed

    private void txt_khachduaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_khachduaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_khachduaKeyPressed

    void chekvoucher() {
        String sql = "";

    }

    private void btn_thanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thanhtoanActionPerformed
        String khach = txt_tenkhachtt.getText();
        String km = txt_khuyenmai.getText();
        int index = tbl_HoaDonBanHang.getSelectedRow();

        // Kiểm tra giá trị tổng tiền
        if (Float.valueOf(txt_tongtien.getText()) > 0) {
            // Kiểm tra xem có nhập tiền khách thanh toán không
            if (!txt_khachdua.getText().equals("")) {
                // Kiểm tra xem có thiếu tiền không
                if (lbl_thieu.getText().equals("")) {
                    // Hiển thị hộp thoại để nhập ghi chú hóa đơn
                    String ghichu = JOptionPane.showInputDialog(this, "Ghi chú hóa đơn :");

                    // Tạo đối tượng HoaDon và thiết lập các thuộc tính
                    HoaDon hd = new HoaDon();
                    hd.setMaHoaDon(lblMaHoaDon.getText());

                    hd.setIdKhachHang(khach);
                    hd.setGhiChu(ghichu);
                    hd.setIdKhuyenMai(km);
                    hd.setTongTien(Float.parseFloat(lbl_thanhtoan.getText()));

                    System.out.println("mã hóa đơn là: " + lblMaHoaDon.getText());
                    System.out.println("gichu là : " + ghichu);
                    System.out.println("khuyến mãi là: " + km);
                    System.out.println("khách là :" + khach);
                    // Thực hiện thanh toán và hiển thị thông báo tương ứng
                    if (hoaDonService_IMPL.thanhtoan(hd)) {
                        filltableHoaDonBanHang();
                        fillGioHang(idhd);
                        resret();
                    }
                } else {
                    // Hiển thị thông báo nếu có thiếu tiền
                    JOptionPane.showMessageDialog(this, "Vui lòng thanh toán " + (0 - ((Float.parseFloat(txt_khachdua.getText())) - (Float.parseFloat(lbl_thanhtoan.getText())))));
                }
            } else {
                // Hiển thị thông báo yêu cầu nhập tiền khách thanh toán
                JOptionPane.showMessageDialog(this, "Nhập tiền khách thanh toán");
            }
        } else {
            // Hiển thị thông báo nếu tổng tiền phải lớn hơn 0
            JOptionPane.showMessageDialog(this, "Tổng tiền phải lớn hơn 0");
        }
    }//GEN-LAST:event_btn_thanhtoanActionPerformed

    void resret() {
        lblMaHoaDon.setText("");
        txt_tenkhachtt.setText("");
        txt_tongtien.setText("");
        txt_khuyenmai.setText("");
        lbl_thanhtoan.setText("");
        txt_khachdua.setText("");
        lbl_thieu.setText("");
        lbl_tienthua.setText("");
        DefaultTableModel modelGioHang = (DefaultTableModel) tbl_giohang.getModel();
        while (modelGioHang.getRowCount() > 0) {
            modelGioHang.removeRow(0);
        }
    }


    private void btn_taohoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taohoadonActionPerformed

        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn tạo hoá đơn không");
        if (chon == 0) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = currentDate.format(formatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String maHD = genMaHoaDon();
            lblMaHoaDon.setText(maHD);
            String mahd = lblMaHoaDon.getText();
            String khachhang = null;
            Date ngaydat = null;
            try {
                ngaydat = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int tongtien = 0;
            String nhanvien = ViewDangNhap.ma;
            String km = null;
            HoaDon hd = new HoaDon();
            hd.setMaHoaDon(mahd);
            hd.setIdKhachHang(khachhang);
            hd.setNgayDat(ngaydat);
            hd.setTongTien(tongtien);
            hd.setIdNhanVien(nhanvien);
            hd.setIdKhuyenMai(km);

            if (hoaDonService_IMPL.addHoaDon(hd)) {
                filltableHoaDonBanHang();
            }
        } else if (chon == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không được tạo");
        }


    }//GEN-LAST:event_btn_taohoadonActionPerformed

    private void btn_suaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaSPActionPerformed
        // TODO add your handling code here:
        SanPham s = new SanPham();
        s.setId(Integer.parseInt(lblIDSanPham.getText()));

        s.setTenSp(txtTensp.getText());
        s.setMoTa(txtMota.getText());

        if (CheckTrongSp()) {
            if (spd.checkTrungTen(txtTensp.getText())) {
                JOptionPane.showMessageDialog(this, "Đã có Tên sản phẩm này, Sửa thất bại");
            } else {
                int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa hay không ");
                if (chon == JOptionPane.YES_OPTION) {
                    spd.AddSanPham(s);
                    fillTableSanPham(idSP);
                } else if (chon == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "Bạn đã hủy sửa");
                } else if (chon == JOptionPane.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(this, "Hủy sửa thành công");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thất bại");
        }
    }//GEN-LAST:event_btn_suaSPActionPerformed

    private void btn_xemctSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xemctSPActionPerformed
        // TODO add your handling code here:
        ViewQuanLySPCT viewQuanLySPCT = new ViewQuanLySPCT(lblIDSanPham.getText());
        viewQuanLySPCT.setVisible(true);
    }//GEN-LAST:event_btn_xemctSPActionPerformed

    private void cboDeGiay2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboDeGiay2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDeGiay2MouseClicked

    private void cboDeGiay2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDeGiay2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDeGiay2ActionPerformed

    private void cboThuongHieu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboThuongHieu2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThuongHieu2MouseClicked

    private void cboThuongHieu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThuongHieu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThuongHieu2ActionPerformed

    private void cboChatLieu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboChatLieu2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieu2MouseClicked

    private void cboChatLieu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieu2ActionPerformed

    private void btn_themGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themGioHangActionPerformed
        int rowIndex = tbl_HoaDonBanHang.getSelectedRow();
        int Index = tblSanPhamBanHang.getSelectedRow();
        if (rowIndex != -1) {
            if (Index != -1) {
                // Nếu có hàng được chọn
                String mahd = tbl_HoaDonBanHang.getValueAt(rowIndex, 1).toString();
                int idspct = Integer.parseInt(tblSanPhamBanHang.getValueAt(Index, 0).toString());
                String maspct = checkMaSpctTheoIDspct(idspct);
                System.out.println("Đã chọn vào bảng. ID hóa đơn là: " + mahd);

                String soluong = JOptionPane.showInputDialog(this, "Nhập số lượng sản phẩm");
                try {
                    int soLuongSPTang = Integer.parseInt(soluong);
                    if (soLuongSPTang > checkslsp(maspct) || soLuongSPTang <= 0) {
                        JOptionPane.showMessageDialog(this, "vui lòng kiểm tra số lượng");
                    } else {
                        System.out.println(maspct);
                        HoaDonChiTiet ct = new HoaDonChiTiet();
                        ct.setMaSanPhamChiTiet(maspct);
                        ct.setSoLuong(Integer.parseInt(soluong));
                        ct.setMaHoaDon(mahd);
                        int idhoadon = Integer.parseInt(tbl_HoaDonBanHang.getValueAt(rowIndex, 0).toString());
                        chiTietService_IMPL.chon(ct, idhoadon);
                        fillGioHang(idhoadon);
                        fillTableSpct(idSPCT);
                        tong();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "xin vui lòng nhập số");
                }
            } else {
                JOptionPane.showMessageDialog(this, "chưa chọn sản phẩm");
            }

        } else {
            JOptionPane.showMessageDialog(this, "vui lòng chọn hoá đơn");
        }
    }//GEN-LAST:event_btn_themGioHangActionPerformed

    private void btn_DoiTTNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DoiTTNVActionPerformed
        // TODO add your handling code here:
        NhanVien nv = new NhanVien();
        nv.setIdNV(Integer.parseInt(lblIDNhanVien.getText()));
        boolean trangThai;
        if (rdo_hdNV.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        nv.setTt(trangThai);
        nhanVienService_IMPL.updateTrangThaiNhanVien(nv);
        JOptionPane.showMessageDialog(this, "Đổi trạng thái thành công ");
        filltableNhanVien(idKH);
    }//GEN-LAST:event_btn_DoiTTNVActionPerformed

    private void tblBangKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangKhachHangMouseClicked
        int index = tblBangKhachHang.getSelectedRow();
        lblIDKhachHang.setText(tblBangKhachHang.getValueAt(index, 0).toString());
        txt_maKh.setText(tblBangKhachHang.getValueAt(index, 1).toString());
        txt_tenKH.setText(tblBangKhachHang.getValueAt(index, 2).toString());
        txt_dienthoaiKH.setText(tblBangKhachHang.getValueAt(index, 3).toString());
        txt_emailKH.setText(tblBangKhachHang.getValueAt(index, 4).toString());
        txt_diachiKH.setText(tblBangKhachHang.getValueAt(index, 5).toString());
        String gioiTinh = tblBangKhachHang.getValueAt(index, 6).toString();
        if (gioiTinh.equals("Nam")) {
            rdo_namKH.setSelected(true);
        } else {
            rdo_nuKH.setSelected(true);
        }
        String trangThai = tblBangKhachHang.getValueAt(index, 7).toString();
        if (trangThai.equals("Đang hoạt động")) {
            rdoDangHoatDongKH.setSelected(true);
        } else {
            rdoKhongHoatDongKH.setSelected(true);
        }
    }//GEN-LAST:event_tblBangKhachHangMouseClicked

    private void btnDoiTrangThaiKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiTrangThaiKHActionPerformed
        // TODO add your handling code here:
        KhachHang khh = new KhachHang();
        khh.setIdKhachHang(Integer.parseInt(lblIDKhachHang.getText()));
        boolean trangThai;
        if (rdoDangHoatDongKH.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        khh.setTrangThai(trangThai);
        khachHangService_IMPL.updateTrangThaiKhachHang(khh);
        JOptionPane.showMessageDialog(this, "Đổi trạng thái thành công ");
        coutKhachHang();
        idKH = countKH - 10;
        filltableKhachHang(idKH);

    }//GEN-LAST:event_btnDoiTrangThaiKHActionPerformed

    private void btnNewKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewKhActionPerformed
        txt_maKh.setText("");
        txt_tenKH.setText("");
        txt_dienthoaiKH.setText("");
        txt_emailKH.setText("");
        txt_diachiKH.setText("");
    }//GEN-LAST:event_btnNewKhActionPerformed

    private void btnThemKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhActionPerformed
        KhachHang khh = new KhachHang();
        khh.setMaKhachHang(txt_maKh.getText());
        khh.setTenKhachHang(txt_tenKH.getText());
        khh.setSoDienThoai(txt_dienthoaiKH.getText());
        khh.setEmail(txt_emailKH.getText());
        khh.setDiaChi(txt_diachiKH.getText());
        boolean gioiTinh;
        if (rdo_namKH.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        khh.setGioiTinh(gioiTinh);
        boolean trangThai;
        if (rdoDangHoatDongKH.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        khh.setTrangThai(trangThai);

        if (checkTrongKhachHang()) {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khách hàng không");
            if (chon == JOptionPane.YES_OPTION) {
                khachHangService_IMPL.addKhachHang(khh);
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                coutKhachHang();
                idKH = countKH - 10;
                filltableKhachHang(idKH);
            } else if (chon == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công");
        }
    }//GEN-LAST:event_btnThemKhActionPerformed

    private void btnTimKiemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKHActionPerformed
        // TODO add your handling code here:
        String ten = txtTkiemKH.getText();
        mol = (DefaultTableModel) tblBangKhachHang.getModel();
        mol.setRowCount(0);
        for (KhachHang k : khachHangService_IMPL.timTenKhachHang(ten)) {
            Object[] toData = new Object[]{
                k.getIdKhachHang(), k.getMaKhachHang(), k.getTenKhachHang(), k.getSoDienThoai(), k.getEmail(), k.getDiaChi(), k.checkGioiTinh(), k.getTrangThai() ? "Đang hoạt động" : "Không hoạt động "
            };
            mol.addRow(toData);
        }
    }//GEN-LAST:event_btnTimKiemKHActionPerformed

    private void btn_sauKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauKHActionPerformed

        if (idKH >= 0 && idKH <= countKH - 10) {
            idKH = idKH + 10;
            trangKH = trangKH + 1;
            filltableKhachHang(idKH);
            lbl_trangKH.setText("" + trangKH);

        }
    }//GEN-LAST:event_btn_sauKHActionPerformed

    private void btn_truocKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocKHActionPerformed
        // TODO add your handling code here:
        if (idKH >= 10) {
            idKH = idKH - 10;
            trangKH = trangKH - 1;
            filltableKhachHang(idKH);
            lbl_trangKH.setText("" + trangKH);

        }
    }//GEN-LAST:event_btn_truocKHActionPerformed

    private void tblBangHoaDonHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangHoaDonHDMouseClicked
//        fillTableHoaDon();
        int rowIndex = tblBangHoaDonHD.getSelectedRow();

        if (rowIndex >= 0) {
            int id = Integer.parseInt(tblBangHoaDonHD.getValueAt(rowIndex, 0).toString());
            fillhdct(id);

            // Hiển thị thông tin của hàng đã chọn
            String message = "Đã chọn hàng có ID: " + id;
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } else {
            // Không có hàng nào được chọn, có thể thông báo hoặc xử lý theo cách khác.
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblBangHoaDonHDMouseClicked

    private void btn_TruocNvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TruocNvActionPerformed
        // TODO add your handling code here:
        if (idNV >= 10) {
            idNV = idNV - 10;
            trangNV = trangNV - 1;
            filltableNhanVien(idNV);
            lbl_trangNV.setText("" + trangNV);
        }
    }//GEN-LAST:event_btn_TruocNvActionPerformed

    private void btn_SauNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SauNVActionPerformed
        // TODO add your handling code here:
        if (idNV >= 0 && idNV <= countNV - 10) {
            idNV = idNV + 10;
            trangNV = trangNV + 1;
            filltableNhanVien(idNV);
            lbl_trangNV.setText("" + trangNV);
        }
    }//GEN-LAST:event_btn_SauNVActionPerformed

    private void btn_LamMoiNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiNVActionPerformed
        // TODO add your handling code here:
        lblIDNhanVien.setText("");
        txt_maNV.setText("");
        txt_tenNhanVien.setText("");
        txt_tenDNNV.setText("");
        txt_mkNV.setText("");
        txt_emailNV.setText("");
        txt_sdtNV.setText("");
        txt_idchucvuNV.setText("");
        txt_luongNV.setText("");
    }//GEN-LAST:event_btn_LamMoiNVActionPerformed

    private void btn_ThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNVActionPerformed
        // TODO add your handling code here:
        NhanVien nv = new NhanVien();
        nv.setMaNV(txt_maNV.getText());
        nv.setTenDN(txt_tenDNNV.getText());
        nv.setTenNV(txt_tenNhanVien.getText());
        nv.setMatKhau(txt_mkNV.getText());
        boolean gioiTinh;
        if (rdo_namNV.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }

        boolean trangThai;
        if (rdo_hdNV.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        nv.setgTinh(gioiTinh);
        nv.setEmail(txt_emailNV.getText());
        nv.setSdt(txt_sdtNV.getText());
        nv.setIdChucVu(Integer.parseInt(txt_idchucvuNV.getText()));
        nv.setLuong(Integer.parseInt(txt_luongNV.getText()));
        nv.setTt(trangThai);

        if (checkTrongNhanVien()) {
            if (nhanVienService_IMPL.checkTrungMa(txt_maNV.getText())) {
                JOptionPane.showMessageDialog(this, "Đã có mã nhân viên này rồi ");
            } else {
                int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm nhân viên không");

                if (chon == JOptionPane.YES_OPTION) {
                    if (nhanVienService_IMPL.AddNhanVien(nv)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        coutNhanVien();
                        idNV = countNV - 5;
                        this.filltableNhanVien(idNV);
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
                }
            }

        }
    }//GEN-LAST:event_btn_ThemNVActionPerformed

    private void btn_TKNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TKNVActionPerformed
        // TODO add your handling code here:
        String ma = txt_tkNV.getText();
        mol = (DefaultTableModel) tbl_bangNhanVien.getModel();
        mol.setRowCount(0);
        for (NhanVien nv : nhanVienService_IMPL.gettimNhanVien(ma)) {
            Object[] todata = new Object[]{nv.getIdNV(), nv.getMaNV(), nv.getTenNV(), nv.getTenDN(), nv.getMatKhau(), nv.checkGioiTinhNV(), nv.getEmail(), nv.getSdt(), nv.getIdChucVu(), nv.getLuong(), nv.checktrangThai()};
            mol.addRow(todata);
        }
    }//GEN-LAST:event_btn_TKNVActionPerformed

    private void tbl_bangNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangNhanVienMouseClicked
        // TODO add your handling code here:
        int index = tbl_bangNhanVien.getSelectedRow();
        this.showNhanVien(index);
    }//GEN-LAST:event_tbl_bangNhanVienMouseClicked

    private void btn_themSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themSPActionPerformed
        // TODO add your handling code here:
        SanPham s = new SanPham();
        s.setMaSp(txtMaSp.getText());
        s.setTenSp(txtTensp.getText());
        s.setMoTa(txtMota.getText());
        s.setTrangThai(true);

        if (CheckTrongSp()) {
            if (spd.checkTrungMa(txtMaSp.getText())) {
                JOptionPane.showMessageDialog(this, "Đã có Mã sản phẩm này, Thêm thất bại");
            } else if (spd.checkTrungTen(txtTensp.getText())) {
                JOptionPane.showMessageDialog(this, "Đã có Tên sản phẩm này, Thêm thất bại");
            } else {
                int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm hay không ");
                if (chon == JOptionPane.YES_OPTION) {
                    spd.AddSanPham(s);
                    fillTableSanPham(0);
                } else if (chon == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
                } else if (chon == JOptionPane.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }//GEN-LAST:event_btn_themSPActionPerformed

    private void btndoiTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndoiTTActionPerformed
        // TODO add your handling code here:
        SanPham s = new SanPham();

        s.setTrangThai(false);
        s.setId(Integer.parseInt(lblIDSanPham.getText()));

        int chon = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn ngừng bán sản phẩm này không");
        if (chon == JOptionPane.YES_OPTION) {
            spd.upDateTrangThai(s);
            JOptionPane.showMessageDialog(this, "Đã đổi trạng thái sản phẩm thành Ngừng bán");
            fillTableSanPham(0);
        } else if (chon == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Đã hủy đổi trạng thái sản phẩm");
        } else {
            JOptionPane.showMessageDialog(this, "Đã hủy");
        }
    }//GEN-LAST:event_btndoiTTActionPerformed

    private void tblBangSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangSanPhamMouseClicked
        // TODO add your handling code here:
        int index = tblBangSanPham.getSelectedRow();
        lblIDSanPham.setText(tblBangSanPham.getValueAt(index, 0).toString());
        txtMaSp.setText(tblBangSanPham.getValueAt(index, 1).toString());
        txtTensp.setText(tblBangSanPham.getValueAt(index, 2).toString());
        txtMota.setText(tblBangSanPham.getValueAt(index, 3).toString());
    }//GEN-LAST:event_tblBangSanPhamMouseClicked

    private void btn_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timKiemActionPerformed
        // TODO add your handling code here:
        String tenSp = txtTimSp.getText();
        mol = (DefaultTableModel) tblBangSanPham.getModel();
        mol.setRowCount(0);
        for (SanPham s : spd.TimSanPham(tenSp)) {
            Object[] todata = new Object[]{s.getId(), s.getMaSp(), s.getTenSp(), s.getMoTa(), s.checkTrangThai()};
            mol.addRow(todata);
        }
    }//GEN-LAST:event_btn_timKiemActionPerformed

    private void btn_truocSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocSPActionPerformed
        // TODO add your handling code here:
        if (idSP >= 10) {
            idSP = idSP - 10;
            trangKH = trangKH - 1;
            fillTableSanPham(idSP);
            lbl_trangSP.setText("" + trangSP);
        }

    }//GEN-LAST:event_btn_truocSPActionPerformed

    private void btn_sauSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauSPActionPerformed
        // TODO add your handling code here:
        if (idSP >= 0 && idSP <= countSP - 10) {
            idSP = idSP + 10;
            trangKH = trangKH + 1;
            fillTableSanPham(idSP);
            lbl_trangSP.setText("" + trangKH);
        }
    }//GEN-LAST:event_btn_sauSPActionPerformed

    private void txtMaSpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaSpKeyPressed
        // TODO add your handling code here:
        lblIDSanPham.setText("");
    }//GEN-LAST:event_txtMaSpKeyPressed

    private void btn_newSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newSPActionPerformed
        // TODO add your handling code here:
        txtMaSp.setText("");
        txtTensp.setText("");
        txtMota.setText("");
    }//GEN-LAST:event_btn_newSPActionPerformed

    private void btn_truocHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truocHDActionPerformed
        // TODO add your handling code here:
        if (idHD >= 10) {
            idHD = idHD - 10;
            trangHD = trangHD - 1;
            filltableHoaDonPhanTrang(idHD);
            lbl_trangHD.setText("" + trangHD);
        }
    }//GEN-LAST:event_btn_truocHDActionPerformed

    private void btn_sauHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sauHDActionPerformed
        // TODO add your handling code here:
        if (idHD >= 0 && idHD <= countHD - 10) {
            idHD = idHD + 10;
            trangHD = trangHD + 1;
            filltableHoaDonPhanTrang(idHD);
            lbl_trangHD.setText("" + trangHD);
        }
    }//GEN-LAST:event_btn_sauHDActionPerformed

    private void tbl_HoaDonBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HoaDonBanHangMouseClicked

        int index1 = tbl_HoaDonBanHang.getSelectedRow();
        Object value = tbl_HoaDonBanHang.getValueAt(index1, 1);

        if (value != null) {
            lblMaHoaDon.setText(value.toString());
            idhd = Integer.parseInt(String.valueOf(tbl_HoaDonBanHang.getValueAt(index1, 0)));
            fillGioHang(idhd);
            tong();
            lblMaHoaDon.setText(tbl_HoaDonBanHang.getValueAt(index1, 1).toString());
            lbl_tienthua.setText("");
            lbl_thieu.setText("");
            txt_khachdua.setText("");
            txt_tenkhachtt.setText(tbl_HoaDonBanHang.getValueAt(index1, 2).toString());
            cbo_LoaiKM.setSelectedItem(tbl_HoaDonBanHang.getValueAt(index1, 0).toString());
        } else {

            idhd = Integer.parseInt(String.valueOf(tbl_HoaDonBanHang.getValueAt(index1, 0)));
            fillGioHang(idhd);
            tong();
            lblMaHoaDon.setText(tbl_HoaDonBanHang.getValueAt(index1, 1).toString());
            lbl_tienthua.setText("");
            lbl_thieu.setText("");
            txt_khachdua.setText("");
            txt_tenkhachtt.setText(tbl_HoaDonBanHang.getValueAt(index1, 2).toString());
            cbo_LoaiKM.setSelectedItem(tbl_HoaDonBanHang.getValueAt(index1, 5).toString());
        }

//        txt_khuyenmai.setText(String.valueOf((KhuyenMaiService_IMPL.timLoaiKM(tbl_HoaDonBanHang.getValueAt(index1, 5).toString()))));

    }//GEN-LAST:event_tbl_HoaDonBanHangMouseClicked

    private void txt_maKhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_maKhKeyPressed
        // TODO add your handling code here:
        lblIDKhachHang.setText("");
    }//GEN-LAST:event_txt_maKhKeyPressed

    private void tblBangKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangKhuyenMaiMouseClicked
        // TODO add your handling code here:
        int index = tblBangKhuyenMai.getSelectedRow();
        this.showKhuyenMai(index);
    }//GEN-LAST:event_tblBangKhuyenMaiMouseClicked

    private void btnNewKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewKhuyenMaiActionPerformed
        // TODO add your handling code here:
        lblIdKhuyenMai.setText("");
        txtMaKhuyenMai.setText("");
        txtTenKM.setText("");
        cbo_LoaiKM.setSelectedItem(0);
        txtSoluongKM.setText("");
        txtNgayBDKM.setText("");
        txtNgayKTKM.setText("");
    }//GEN-LAST:event_btnNewKhuyenMaiActionPerformed

    private void btnTimKiemKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKMActionPerformed
        // TODO add your handling code here:
        if (idKM >= 0 && idKM <= countKM - 10) {
            idKM = idKM + 10;
            trangKM = trangKM + 1;
            filltableidKM(idKM);
            lblTrangKM.setText("" + trangKM);
        }
        String ma = txt_TKKM.getText();
        mol = (DefaultTableModel) tblBangKhuyenMai.getModel();
        mol.setRowCount(0);
        for (Khuyenmai nv : KhuyenMaiService_IMPL.gettimKhuyenMai(ma)) {
            Object[] todata = new Object[]{nv.getIdKM(), nv.getMaGiamGiaKM(), nv.getTenKM(), nv.getLoaiKM(), nv.getSoLuongKM(), nv.getNgayBatDauKM(), nv.getNgayKTKM(), nv.checktrangThai()};
            mol.addRow(todata);
        }
    }//GEN-LAST:event_btnTimKiemKMActionPerformed


    private void btn_themKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themKMActionPerformed
        String ngaybdkm1 = txtNgayBDKM.getText();
        String ngayktkm1 = txtNgayKTKM.getText();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date ngaybdkm2 = null;
        Date ngayktkm2 = null;

        try {
            ngaybdkm2 = dateFormat.parse(ngaybdkm1);
            ngayktkm2 = dateFormat.parse(ngayktkm1);

            if (ngaybdkm2.after(ngayktkm2)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                return;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ: dd-MM-yyyy");
            e.printStackTrace();
            return;
        }

// Tiếp tục xử lý khi mọi thứ hợp lệ
        Khuyenmai km = new Khuyenmai();

        km.setMaGiamGiaKM(txtMaKhuyenMai.getText());
        km.setTenKM(txtTenKM.getText());
        km.setLoaiKM(cbo_LoaiKM.getSelectedItem().toString());
        km.setSoLuongKM(Integer.parseInt(txtSoluongKM.getText()));
        km.setNgayBatDauKM(ngaybdkm2);
        km.setNgayKTKM(ngayktkm2);

        if (rdo_hdKM.isSelected()) {
            km.setTrangthai(true);
        } else {
            km.setTrangthai(false);
        }

        if (checkTrongKhuyenMai()) {
            if (KhuyenMaiService_IMPL.checkTrungMa(txtMaKhuyenMai.getText())) {
                JOptionPane.showMessageDialog(this, "Đã có mã khuyến mại này, không thể thêm");
            } else {
                int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khuyến mãi mới không");
                if (chon == JOptionPane.YES_OPTION) {
                    if (KhuyenMaiService_IMPL.AddKM(km)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công");
                        this.fillTableKhuyenMai();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
                }
            }
        }


    }//GEN-LAST:event_btn_themKMActionPerformed

    private void txtMaKhuyenMaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaKhuyenMaiKeyPressed
        // TODO add your handling code here:
        lblIdKhuyenMai.setText("");
    }//GEN-LAST:event_txtMaKhuyenMaiKeyPressed

    private void btnDoiTrangThaiKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiTrangThaiKMActionPerformed
        // TODO add your handling code here:
        Khuyenmai nv = new Khuyenmai();
        nv.setIdKM(Integer.parseInt(lblIdKhuyenMai.getText()));
        boolean trangThai;
        if (rdo_hdKM.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        nv.setTrangthai(trangThai);
        KhuyenMaiService_IMPL.updateTrangThaiKhuyenMai(nv);
        JOptionPane.showMessageDialog(this, "Đổi trạng thái thành công ");
        fillTableKhuyenMai();
    }//GEN-LAST:event_btnDoiTrangThaiKMActionPerformed

    private void btnTruocKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruocKMActionPerformed
        // TODO add your handling code here:
        if (idKM >= 10) {
            idKM = idKM - 10;
            trangKM = trangKM - 1;
            filltableidKM(idKM);
            lblTrangKM.setText("" + trangKM);
        }
    }//GEN-LAST:event_btnTruocKMActionPerformed

    private void btnSauKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSauKMActionPerformed
        // TODO add your handling code here:
        if (idKM >= 0 && idKM <= countKM - 10) {
            idKM = idKM + 10;
            trangKM = trangKM + 1;
            filltableidKM(idKM);
            lblTrangKM.setText("" + trangKM);
        }
    }//GEN-LAST:event_btnSauKMActionPerformed
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // Kiểm tra xem chuỗi có chứa toàn bộ là số hay không
    }
    // kiểu ngày

    public static boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("" + dateFormat);
        dateFormat.setLenient(false);

        try {
            // Cố gắng chuyển đổi chuỗi thành đối tượng Date
            Date date = dateFormat.parse(dateString);
            return true; // Nếu chuyển đổi thành công, chuỗi là ngày hợp lệ
        } catch (ParseException e) {
            return false; // Nếu có lỗi ParseException, chuỗi không phải là ngày hợp lệ
        }
    }
    private void btnTimKiemSoDienThoaiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSoDienThoaiHDActionPerformed

        fillhdct(idhd);
        String dlieu = txtTimKiemTheoTenKhachHangHDON.getText();
        if (isNumeric(dlieu)) {
            // nếu là số 
            System.out.println("Dữ liệu nhập là" + dlieu);
            sdt = dlieu;
            ten = "";
        } else {
            System.out.println("Dữ liệu nhập là" + dlieu);
            sdt = "";
            ten = dlieu;
        }
        ngay = txtTimKiemTheoNgayHDon.getText();
        ngayketthuc = txtTimKiemTheoNgayKTHDon.getText();

        if (isValidDate(ngay) && isValidDate(ngayketthuc)) {
            System.out.println("Chuỗi là ngày hợp lệ.");
        } else if (isValidDate(ngay) && !isValidDate(ngayketthuc)) {
            ngayketthuc = "";
        } else if (isValidDate(ngay) && !isValidDate(ngayketthuc)) {
            ngay = "";
        } else {
            JOptionPane.showMessageDialog(this, "Ô ngày được nhập vào không hợp lệ");
            System.out.println("Chuỗi không phải là ngày hợp lệ.");
            ngay = "";
            ngayketthuc = "";
        }
        if (ten.equals("")) {
            ten = "";
            filltableHoaDon(ten, ngay, ngayketthuc, sdt);
        } else if (ngay.equals("") || ngayketthuc.equals("")) {
            ngay = "";
            ngayketthuc = "";
            filltableHoaDon(ten, ngay, ngayketthuc, sdt);
        } else if (sdt.equals("")) {
            sdt = "";
            filltableHoaDon(ten, ngay, ngayketthuc, sdt);
        } else if (ten.equals("") && sdt.equals("")) {
            ten = "";
            sdt = "";
            filltableHoaDon(ten, ngay, ngayketthuc, sdt);
        } else {
            filltableHoaDon(ten, ngay, ngayketthuc, sdt);
        }


    }//GEN-LAST:event_btnTimKiemSoDienThoaiHDActionPerformed

    private void txtTimKiemTheoTenKhachHangHDONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemTheoTenKhachHangHDONActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTheoTenKhachHangHDONActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            new bangiay.view.ViewDangNhap(ngay, tencv).setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btn_congGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_congGioHangActionPerformed
        int index1 = tbl_HoaDonBanHang.getSelectedRow();
        int rowIndex = tbl_HoaDonBanHang.getSelectedRow();
        int Index = tbl_giohang.getSelectedRow();
        if (rowIndex != -1) {
            // Nếu có hàng được chọn
            String mahd = tbl_HoaDonBanHang.getValueAt(rowIndex, 1).toString();
            int idspct = Integer.parseInt(tbl_giohang.getValueAt(Index, 0).toString());
            // lấy số lượng giỏ hàng
            int soLuongGH = Integer.parseInt(tbl_giohang.getValueAt(Index, 2).toString());

            String maspct = checkMaSpctTheoIDspct(idspct);
            System.out.println("Đã chọn vào bảng. ID hóa đơn là: " + mahd);
            String soluong = JOptionPane.showInputDialog(this, "Nhập số lượng sản phẩm");
            int soLuongSPTang = Integer.parseInt(soluong);
            int slsua = Integer.parseInt(tbl_giohang.getValueAt(Index, 2).toString());
            try {
                HoaDonChiTiet ct = new HoaDonChiTiet();

                if (soLuongSPTang > checkslsp(maspct) + soLuongGH || soLuongSPTang <= 0) {
                    JOptionPane.showMessageDialog(this, "vui lòng kiểm tra số lượng");
                } else if (soLuongSPTang > Integer.parseInt(tbl_giohang.getValueAt(Index, 2).toString())) {
                    System.out.println(maspct);
                    ct.setMaSanPhamChiTiet(maspct);
                    ct.setSoLuong(soLuongSPTang);
                    ct.setMaHoaDon(mahd);
                    chiTietService_IMPL.add(ct, slsua);
                    int indext = tbl_HoaDonBanHang.getSelectedRow();
                    int idhoadon = Integer.parseInt(tbl_HoaDonBanHang.getValueAt(indext, 0).toString());
                    fillGioHang(idhoadon);
                    fillTableSpct(idSPCT);
                    tong();
                } else if (soLuongSPTang < Integer.parseInt(tbl_giohang.getValueAt(Index, 2).toString())) {
                    System.out.println(maspct);
                    ct.setMaSanPhamChiTiet(maspct);
                    ct.setSoLuong(soLuongSPTang);
                    ct.setMaHoaDon(mahd);
                    chiTietService_IMPL.tru(ct, slsua);
                    int indext = tbl_HoaDonBanHang.getSelectedRow();
                    int idhoadon = Integer.parseInt(tbl_HoaDonBanHang.getValueAt(indext, 0).toString());
                    fillGioHang(idhoadon);
                    fillTableSpct(idSPCT);
                    tong();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "xin vui lòng nhập số");
            }
        }
    }//GEN-LAST:event_btn_congGioHangActionPerformed

    private void btn_vangLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vangLaiActionPerformed
        // TODO add your handling code here:
        txt_tenkhachtt.setText("Khách vãng lai");
    }//GEN-LAST:event_btn_vangLaiActionPerformed

    private void btnTkiemMaSPBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTkiemMaSPBanHangActionPerformed
        // TODO add your handling code here:\
        String mau, thuongHieu, loaiGiay, deGiay, chatLieu;

        if (cboMau2.getSelectedItem().equals("Tất cả")) {
            mau = "";
        } else {
            mau = cboMau2.getSelectedItem().toString();
        }

        if (cboLoaiGiay2.getSelectedItem().equals("Tất cả")) {
            loaiGiay = "";
        } else {
            loaiGiay = cboLoaiGiay2.getSelectedItem().toString();
        }

        if (cboThuongHieu2.getSelectedItem().equals("Tất cả")) {
            thuongHieu = "";
        } else {
            thuongHieu = cboThuongHieu2.getSelectedItem().toString();
        }

        if (cboDeGiay2.getSelectedItem().equals("Tất cả")) {
            deGiay = "";
        } else {
            deGiay = cboDeGiay2.getSelectedItem().toString();
        }

        if (cboChatLieu2.getSelectedItem().equals("Tất cả")) {
            chatLieu = "";
        } else {
            chatLieu = cboChatLieu2.getSelectedItem().toString();
        }

        mol = (DefaultTableModel) tblSanPhamBanHang.getModel();
        mol.setRowCount(0);
        for (SanPhamChiTiet s : spdChiTiet_IMPL.locSanPhamChiTiet(loaiGiay, thuongHieu, chatLieu, deGiay, mau)) {
            Object[] todata = new Object[]{
                s.getIdSpct(), s.getMaSpct(), s.getIdsp(), s.getLoaiGiay(), s.getSize(), s.getThuongHieu(), s.getDeGiay(), s.getMauSac(), s.getChatLieu(), s.getGia(), s.getSoLuongSpct(), s.checkTrangThai()
            };
            mol.addRow(todata);
        }


    }//GEN-LAST:event_btnTkiemMaSPBanHangActionPerformed

    private void txt_khachduaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_khachduaKeyReleased
        // TODO add your handling code here:
        try {
            double tt = Double.parseDouble(lbl_thanhtoan.getText());
            double khachDua = Double.parseDouble(txt_khachdua.getText());
            double phaiTra = khachDua - tt;
            long tienPhaiTra = Math.round(phaiTra);
            if (tienPhaiTra >= 0) {
                lbl_tienthua.setText(String.valueOf(tienPhaiTra));
                lbl_thieu.setText("");
            } else {
                lbl_thieu.setText(String.valueOf(0 - tienPhaiTra));
                lbl_tienthua.setText("");
            }

        } catch (NumberFormatException ex) {
        }
    }//GEN-LAST:event_txt_khachduaKeyReleased

    private void btn_maxHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_maxHDActionPerformed

        for (int i = 0; i < countHD - 10; i += 10) {
            trangHD = trangHD + 1;
            idHD = idHD + 10;
        }
        lbl_trangHD.setText("" + trangHD);
        filltableHoaDonPhanTrang(idHD);
    }//GEN-LAST:event_btn_maxHDActionPerformed

    private void btn_minHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_minHDActionPerformed
        // TODO add your handling code here:
        idHD = 0;
        trangHD = 1;
        filltableHoaDonPhanTrang(idHD);
        lbl_trangHD.setText("" + trangHD);

    }//GEN-LAST:event_btn_minHDActionPerformed

    private void txt_khuyenmaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_khuyenmaiActionPerformed


    }//GEN-LAST:event_txt_khuyenmaiActionPerformed

    public int checktontai(String makm) {
        int kq = 0;
        String sql = """
                           select COUNT(*) as 'kq' from khuyenmai WHERE MAGIAMGIA = ?
                   """;

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, makm);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                kq = rs.getInt("kq");
                System.out.println("giá trị trả  về là " + kq);
            }
            return kq;
        } catch (Exception e) {
            return 0;
        }

    }
    private void btnApMaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApMaKhuyenMaiActionPerformed
        String makm = txt_khuyenmai.getText();
        int rowIndex = tbl_HoaDonBanHang.getSelectedRow();

        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn");
        } else {
            // Kiểm tra null cho mã khuyến mãi và chọn hàng hoá đơn
            if (makm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khuyến mãi");
            } else {
                if (checktontai(makm) == 1) {
                    checkSLKM(makm);
                    Connection conn = null;
                    PreparedStatement stm = null;
                    ResultSet rs = null;
                    try {
                        conn = DBConnect.getConnection();
                        String sql = "SELECT LOAIKHUYENMAI, SOLUONG, NGAYBATDAU, NGAYKETTHUC, TRANGTHAI FROM KHUYENMAI WHERE MAGIAMGIA = ?";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, makm);
                        rs = stm.executeQuery();
                        if (rs.next()) {
                            String loaikm = rs.getString("LOAIKHUYENMAI");
                            int soluongma = rs.getInt("SOLUONG");
                            int trangthai = rs.getInt("TRANGTHAI");
                            String ngaybatdau = rs.getString("NGAYBATDAU");
                            String ngayketthuc = rs.getString("NGAYKETTHUC");

                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date ngayBatDauDate = inputFormat.parse(ngaybatdau);
                            Date ngayKetThucDate = inputFormat.parse(ngayketthuc);
                            String ngayBatDauFormatted = outputFormat.format(ngayBatDauDate);
                            String ngayKetThucFormatted = outputFormat.format(ngayKetThucDate);

                            Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = formatter.format(currentDate);

                            if (trangthai == 0) {
                                JOptionPane.showMessageDialog(this, "Khuyến mãi không hoạt động");
                            } else if (ngayKetThucDate.compareTo(currentDate) > 0) {
                                System.out.println("Mã có hiệu lực");

                                if (checkSLKM(makm) > soluongma) {
                                    JOptionPane.showMessageDialog(this, "Mã Khuyến Mãi: " + makm + " đã hết");
                                } else {
                                    String phantramStr = loaikm.replaceAll("[^0-9]", "");
                                    int phantram = Integer.parseInt(phantramStr);

                                    // Kiểm tra chuỗi trước khi chuyển đổi thành số
                                    String tienStr = txt_tongtien.getText().trim(); // Loại bỏ khoảng trắng ở đầu và cuối chuỗi
                                    if (!tienStr.isEmpty()) {
                                        Double tien = Double.parseDouble(tienStr);
                                        double tongtienSauKM = (phantram == 100) ? 0 : tien - (tien * phantram / 100);
                                        long fomarTien = Math.round(tongtienSauKM);
                                        lbl_thanhtoan.setText(String.valueOf(fomarTien));
                                        lbl_loaikm.setText(loaikm);
                                        System.out.println("phần trăm của voucher là : " + phantram);
                                        System.out.println("tiền đã áp voucher là : " + tongtienSauKM);

//                                        double tongtien = Double.parseDouble(lbltongtien.getText());
//                                        double tienduocgiam = tongtien - tongtienSauKM;
////                                        lbl_tienduocgiam.setText(String.valueOf(tienduocgiam));
//                                        System.out.println(tienduocgiam);
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền");
                                    }
                                }
                            } else if (formattedDate.compareTo(ngayBatDauFormatted) < 0) {
                                JOptionPane.showMessageDialog(this, "Khuyến mãi chưa bắt đầu");
                            } else {
                                JOptionPane.showMessageDialog(this, "Khuyến mãi đã kết thúc");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy mã khuyến mãi");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (stm != null) {
                                stm.close();
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tồn tại khuyến mãi ");
                }
            }
        }

    }//GEN-LAST:event_btnApMaKhuyenMaiActionPerformed

    private void cbo_LoaiKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_LoaiKMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_LoaiKMActionPerformed

    private void btn_timKhachTheoSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timKhachTheoSDTActionPerformed
        String sdt = txt_tenkhachtt.getText();
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "SELECT HOTEN  from khachhang  WHERE SODIENTHOAI = ?";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, sdt);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                String tenkh = rs.getString(1);
                txt_tenkhachtt.setText(tenkh);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_timKhachTheoSDTActionPerformed

    private void txt_maKh1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_maKh1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maKh1KeyPressed

    private void btnNewKh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewKh1ActionPerformed
        // TODO add your handling code here:
        txt_maKh1.setText("");
        txt_tenKH1.setText("");
        txt_dienthoaiKH1.setText("");
        txt_emailKH1.setText("");
        txt_diachiKH1.setText("");
    }//GEN-LAST:event_btnNewKh1ActionPerformed

    private void btnThemKh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKh1ActionPerformed
        KhachHang khh = new KhachHang();
        khh.setMaKhachHang(txt_maKh1.getText());
        khh.setTenKhachHang(txt_tenKH1.getText());
        khh.setSoDienThoai(txt_dienthoaiKH1.getText());
        khh.setEmail(txt_emailKH1.getText());
        khh.setDiaChi(txt_diachiKH1.getText());
        boolean gioiTinh;
        if (rdo_namKH1.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        khh.setGioiTinh(gioiTinh);
        boolean trangThai;
        if (rdoDangHoatDongKH1.isSelected()) {
            trangThai = true;
        } else {
            trangThai = false;
        }
        khh.setTrangThai(trangThai);

        if (checkTrongKhachHang1()) {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khách hàng không");
            if (chon == JOptionPane.YES_OPTION) {
                khachHangService_IMPL.addKhachHang(khh);
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                jaddkh.dispose();
                coutKhachHang();
                idKH = countKH - 10;
                filltableKhachHang(idKH);
            } else if (chon == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Bạn đã hủy thêm");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công");
        }
    }//GEN-LAST:event_btnThemKh1ActionPerformed

    private void rdo_nuKH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_nuKH1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_nuKH1ActionPerformed

    private void rdoKhongHoatDongKH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhongHoatDongKH2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKhongHoatDongKH2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MAIN_VIEW31t1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MAIN_VIEW31t1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MAIN_VIEW31t1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MAIN_VIEW31t1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String tennv, tencv = null;
                tennv = "";
                new MAIN_VIEW31t1(tennv, tencv).setVisible(true);
            }
        });
    }

    void filltableNhanVien(long id) {
        mol = (DefaultTableModel) tbl_bangNhanVien.getModel();
        mol.setRowCount(0);
        for (NhanVien nv : nhanVienService_IMPL.getListPT(id)) {
            Object[] todata = new Object[]{nv.getIdNV(), nv.getMaNV(), nv.getTenNV(), nv.getTenDN(), nv.getMatKhau(), nv.checkGioiTinhNV(), nv.getEmail(), nv.getSdt(), nv.checkChucVuNV(), nv.getLuong(), nv.checktrangThai()};
            mol.addRow(todata);
        }
    }

    private void filltableHoaDon(String tenkhs, String ngays, String ngayketthuc, String sdt) {
        DefaultTableModel model = (DefaultTableModel) tblBangHoaDonHD.getModel();
        model.setRowCount(0);

        List<HoaDon> hoaDonList = hoaDonService_IMPL.getHoaDon(tenkhs, ngays, ngayketthuc, sdt);
        if (hoaDonList != null) {
            for (HoaDon h : hoaDonList) {
                Object[] rowData = new Object[]{
                    h.getIdHoaDon(), h.getMaHoaDon(), h.getIdKhachHang(), h.getSoDienThoai(), h.getNgayDat(), h.getGhiChu(), h.getIdKhuyenMai(), h.getIdNhanVien(), h.getTongTien(), h.getTrangThai()
                };

                // Kiểm tra số lượng cột trước khi thêm dữ liệu
                if (rowData.length == model.getColumnCount()) {
                    model.addRow(rowData);
                } else {
                    // Xử lý nếu số lượng cột không khớp
                    System.err.println("Số lượng cột không khớp");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu hoá đơn để hiển thị.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void filltableHoaDonPhanTrang(long idHD) {
        mol = (DefaultTableModel) tblBangHoaDonHD.getModel();
        mol.setRowCount(0);
        for (HoaDon h : hoaDonService_IMPL.getHoaDonPhanTrang(idHD)) {
            Object[] toData = new Object[]{
                h.getIdHoaDon(), h.getMaHoaDon(), h.getIdKhachHang(), h.getSoDienThoai(), h.getNgayDat(), h.getGhiChu(), h.getIdKhuyenMai(), h.getIdNhanVien(), h.getTongTien(), h.trangThaiPhanTrang()
            };
            mol.addRow(toData);
        }
    }

    private void filltableHoaDonBanHang() {
        mol = (DefaultTableModel) tbl_HoaDonBanHang.getModel();
        mol.setRowCount(0);
        for (HoaDon h : hoaDonService_IMPL.getHoaDonBanHang()) {
            Object[] toData = new Object[]{
                h.getIdHoaDon(), h.getMaHoaDon(), h.ktkhach(), h.getNgayDat(), h.getGhiChu(), h.getIdKhuyenMai(), h.getIdNhanVien(), h.getTongTien(), h.getTrangThai()
            };
            mol.addRow(toData);
        }
    }

    void fillhdct(int mahdct) {
        mol = (DefaultTableModel) tblBangHoaDonCTHD.getModel();
        mol.setRowCount(0);

        // Kiểm tra nếu giá trị mahdct hợp lệ
        if (mahdct > 0) {
            List<HoaDonChiTiet> chiTietList = chiTietService_IMPL.getall(mahdct);

            // Kiểm tra nếu danh sách chi tiết không rỗng
            if (chiTietList != null && !chiTietList.isEmpty()) {
                for (HoaDonChiTiet hdct : chiTietList) {
                    Object[] todata = new Object[]{
                        hdct.getIdHoaDonCT(),
                        hdct.getMaHoaDon(),
                        hdct.getTenSP(),
                        hdct.getSoLuong(),
                        hdct.getGia(),
                        hdct.getThanhTien(),};

                    mol.addRow(todata);
                }
            } else {
                // Nếu danh sách chi tiết rỗng, bạn có thể thực hiện một số hành động khác hoặc hiển thị thông báo.
            }
        } else {
            // Nếu giá trị mahdct không hợp lệ, bạn có thể thực hiện một số hành động khác hoặc hiển thị thông báo.
        }
    }

    void filltableKhachHang(long id) {
        mol = (DefaultTableModel) tblBangKhachHang.getModel();
        mol.setRowCount(0);
        for (KhachHang k : khachHangService_IMPL.getList2(id)) {
            Object[] toData = new Object[]{
                k.getIdKhachHang(), k.getMaKhachHang(), k.getTenKhachHang(), k.getSoDienThoai(), k.getEmail(), k.getDiaChi(), k.checkGioiTinh(), k.getTrangThai() ? "Đang hoạt động" : "Không hoạt động "
            };
            mol.addRow(toData);
        }
    }

    void fillTableSanPham(long idSP) {
        mol = (DefaultTableModel) tblBangSanPham.getModel();
        mol.setRowCount(0);
        for (SanPham s : spd.getSanPham(idSP)) {
            Object[] todata = new Object[]{s.getId(), s.getMaSp(), s.getTenSp(), s.getMoTa(), s.checkTrangThai()};
            mol.addRow(todata);
        }
    }

    void fillTableSpct(long id) {
        mol = (DefaultTableModel) tblSanPhamBanHang.getModel();
        mol.setRowCount(0);
        for (SanPhamChiTiet s : spdChiTiet_IMPL.getSPCTBH(id)) {
            Object[] todata = new Object[]{
                s.getIdSpct(), s.getMaSpct(), s.getIdsp(), s.getLoaiGiay(), s.getSize(), s.getThuongHieu(), s.getDeGiay(), s.getMauSac(),
                s.getChatLieu(), s.getGia(), s.getSoLuongSpct(), s.checkTrangThai()
            };
            mol.addRow(todata);
        }
    }

    void fillTableKhuyenMai() {
        mol = (DefaultTableModel) tblBangKhuyenMai.getModel();
        mol.setRowCount(0);
        for (Khuyenmai nv : KhuyenMaiService_IMPL.getKhuyenMai()) {
            Object[] todata = new Object[]{nv.getIdKM(), nv.getMaGiamGiaKM(), nv.getTenKM(), nv.getLoaiKM(), nv.getSoLuongKM(), nv.getNgayBatDauKM(), nv.getNgayKTKM(), nv.checktrangThai()};
            mol.addRow(todata);
        }
    }

    void showTenLoaiKhuyenMai() {
        cbo_LoaiKM.removeAllItems();
        for (String c : KhuyenMaiService_IMPL.getCboLoaiKhuyenMai()) {
            cbo_LoaiKM.addItem(c);
        }
    }

    void filltableidKM(long idKM) {
        mol = (DefaultTableModel) tblBangKhuyenMai.getModel();
        mol.setRowCount(0);
        for (Khuyenmai nv : KhuyenMaiService_IMPL.getListPT(idKM)) {
            Object[] todata = new Object[]{nv.getIdKM(), nv.getMaGiamGiaKM(), nv.getTenKM(), nv.getLoaiKM(), nv.getSoLuongKM(), nv.getNgayBatDauKM(), nv.getNgayBatDauKM(), nv.checktrangThai()};
            mol.addRow(todata);
        }
    }

    void fillGioHang(int idhdc) {
        int index = tbl_HoaDonBanHang.getSelectedRow();
        mol = (DefaultTableModel) tbl_giohang.getModel();
        mol.setRowCount(0);
        for (HoaDonChiTiet s : chiTietService_IMPL.getall(idhd)) {
            float tongtien = s.getGia() * s.getSoLuong();
            Object[] todata = new Object[]{
                s.getIdSpct(), s.getTenSP(), s.getSoLuong(), s.getSize(), s.getMauSac(), s.getDeGiay(), s.getChatLieu(), s.getGia(), tongtien
            };
            mol.addRow(todata);
        }
    }

    public String genMaHoaDon() {
        String chu = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String number = "0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int index = new Random().nextInt(chu.length());
            randomString.append(chu.charAt(index));
        }

        for (int i = 0; i < 3; i++) {
            int indexNum = new Random().nextInt(number.length());
            randomString.append(number.charAt(indexNum));
        }
        return randomString.toString();
    }

    private boolean checkTrongKhachHang() {
        if (txt_maKh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã không được để trống ");
            txt_maKh.requestFocus();
            return false;
        } else if (txt_tenKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống ");
            txt_tenKH.requestFocus();
            return false;
        } else if (txt_dienthoaiKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số diện thoại không được để trống ");
            txt_dienthoaiKH.requestFocus();
            return false;
        } else if (txt_emailKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống ");
            txt_emailKH.requestFocus();
            return false;
        } else if (txt_emailKH.getText().matches("")) {
            JOptionPane.showMessageDialog(null, "^[A-Za-z0-9+_.-]+@(.+)$");
            txt_emailKH.requestFocus();
            return false;
        } else if (txt_diachiKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống ");
            txt_diachiKH.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTrongKhachHang1() {
        if (txt_maKh1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã không được để trống ");
            txt_maKh1.requestFocus();
            return false;
        } else if (txt_tenKH1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống ");
            txt_tenKH1.requestFocus();
            return false;
        } else if (txt_dienthoaiKH1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số diện thoại không được để trống ");
            txt_dienthoaiKH1.requestFocus();
            return false;
        } else if (txt_emailKH1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống ");
            txt_emailKH1.requestFocus();
            return false;
        } else if (txt_emailKH1.getText().matches("")) {
            JOptionPane.showMessageDialog(null, "^[A-Za-z0-9+_.-]+@(.+)$");
            txt_emailKH1.requestFocus();
            return false;
        } else if (txt_diachiKH1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống ");
            txt_diachiKH1.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    void showNhanVien(int index) {
        lblIDNhanVien.setText(tbl_bangNhanVien.getValueAt(index, 0).toString());
        txt_maNV.setText(tbl_bangNhanVien.getValueAt(index, 1).toString());
        txt_tenNhanVien.setText(tbl_bangNhanVien.getValueAt(index, 2).toString());
        txt_tenDNNV.setText(tbl_bangNhanVien.getValueAt(index, 3).toString());
        txt_mkNV.setText(tbl_bangNhanVien.getValueAt(index, 4).toString());
        String gtinh = (tbl_bangNhanVien.getValueAt(index, 5).toString());
        if (gtinh.equals("Nam")) {
            rdo_namNV.setSelected(true);
        } else {
            rdo_nuNV.setSelected(true);
        }
        txt_emailNV.setText(tbl_bangNhanVien.getValueAt(index, 6).toString());
        txt_sdtNV.setText(tbl_bangNhanVien.getValueAt(index, 7).toString());
        txt_idchucvuNV.setText(tbl_bangNhanVien.getValueAt(index, 8).toString());
        txt_luongNV.setText(tbl_bangNhanVien.getValueAt(index, 9).toString());
        String tt = (tbl_bangNhanVien.getValueAt(index, 10).toString());
        if (tt.equals("Hoạt động")) {
            rdo_hdNV.setSelected(true);
        } else {
            rdo_khdNV.setSelected(true);
        }
    }

    void showKhuyenMai(int index) {
        lblIdKhuyenMai.setText(tblBangKhuyenMai.getValueAt(index, 0).toString());
        txtMaKhuyenMai.setText(tblBangKhuyenMai.getValueAt(index, 1).toString());
        txtTenKM.setText(tblBangKhuyenMai.getValueAt(index, 2).toString());
        cbo_LoaiKM.setSelectedItem(tblBangKhuyenMai.getValueAt(index, 3).toString());
        txtSoluongKM.setText(tblBangKhuyenMai.getValueAt(index, 4).toString());
        txtNgayBDKM.setText(tblBangKhuyenMai.getValueAt(index, 5).toString());
        txtNgayKTKM.setText(tblBangKhuyenMai.getValueAt(index, 6).toString());
        String tt = (tblBangKhuyenMai.getValueAt(index, 7).toString());
        if (tt.equals("Hoạt động")) {
            rdo_hdKM.setSelected(true);
        } else {
            rdo_khdKM.setSelected(true);
        }

    }

    private boolean txt_maKM() {
        return txtMaKhuyenMai.getText().trim().isEmpty();
    }

    private boolean txt_tenKM() {
        return txtTenKM.getText().trim().isEmpty();
    }

    private boolean txt_soLuongKM() {
        return txtSoluongKM.getText().trim().isEmpty();
    }

    private boolean txt_NgayBDKM() {
        return txtNgayBDKM.getText().trim().isEmpty();
    }

    private boolean txt_NgayKTKM() {
        return txtNgayKTKM.getText().trim().isEmpty();
    }

    private boolean txt_maNV() {
        return txt_maNV.getText().trim().isEmpty();
    }

    private boolean txt_tenDNNV() {
        return txt_tenDNNV.getText().trim().isEmpty();
    }

    private boolean txt_tenNhanVien() {
        return txt_tenNhanVien.getText().trim().isEmpty();
    }

    private boolean txt_mkNV() {
        return txt_maNV.getText().trim().isEmpty();
    }

    private boolean txt_emailNV() {
        return txt_emailNV.getText().trim().isEmpty();
    }

    private boolean txt_sdtNV() {
        return txt_sdtNV.getText().trim().isEmpty();
    }

    private boolean txt_idchucvuNV() {
        return txt_idchucvuNV.getText().trim().isEmpty();
    }

    private boolean txt_luongNV() {
        return txt_luongNV.getText().trim().isEmpty();
    }

    private boolean checkTrongNhanVien() {
        if (txt_maNV()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống ");
            txt_maNV.requestFocus();
            return false;
        } else if (txt_tenDNNV()) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống ");
            txt_tenDNNV.requestFocus();
            return false;
        } else if (txt_tenNhanVien()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống ");
            txt_tenNhanVien.requestFocus();
            return false;
        } else if (txt_maNV()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhân viên không được để trống ");
            txt_mkNV.requestFocus();
            return false;
        } else if (txt_emailNV()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống ");
            txt_emailNV.requestFocus();
            return false;
        } else if (txt_emailNV()) {
            JOptionPane.showMessageDialog(null, "^[A-Za-z0-9+_.-]+@(.+)$");
            txt_emailNV.requestFocus();
            return false;
        } else if (txt_sdtNV()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống ");
            txt_sdtNV.requestFocus();
            return false;
        } else if (txt_idchucvuNV()) {
            JOptionPane.showMessageDialog(this, "Id chức vụ không được để trống ");
            txt_idchucvuNV.requestFocus();
            return false;
        } else if (txt_luongNV()) {
            JOptionPane.showMessageDialog(this, "Lương không được để trống ");
            txt_luongNV.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    void coutKhachHang() {
        try {

            String sql = "Select count(*) from KHACHHANG where TRANGTHAI = 1";
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                countKH = r.getLong(1);
                System.out.println(countKH);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    void coutNhanVien() {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "Select count(*) from NHANVIEN where TRANGTHAI = 1";
            conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                countNV = r.getLong(1);
                System.out.println(countNV);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    void coutHoaDon() {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "Select count(*) from HOADON Where TRANGTHAI = 1 ";
            conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                countHD = r.getLong(1);
                System.out.println(countHD);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    boolean CheckTrongSp() {
        if (txtMaSp.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không được để trống ");
            return false;
        } else if (txtTensp.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống");
            return false;
        } else if (txtMota.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Mô tả sản phẩm không được để trống");
            return false;
        } else {
            return true;
        }
    }

    void coutDbSp() {
        try {
            String sql = "Select count(*) from sanpham where TrangThai = 1";
            Connection con = DBConnect.getConnection();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                countSP = r.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    void coutKM() {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "Select count(*) from KhuyenMai where TRANGTHAI = 1";
            conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                countKM = r.getLong(1);
                System.out.println(countKM);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private boolean checkTrongKhuyenMai() {
        if (txt_tenKM()) {
            JOptionPane.showMessageDialog(this, "Mã không được để trống ");
            txtMaKhuyenMai.requestFocus();
            return false;
        } else if (txt_tenKM()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống ");
            txtTenKM.requestFocus();
            return false;
        } else if (txt_soLuongKM()) {
            JOptionPane.showMessageDialog(this, "Số lượng khuyến mãi không được để trống ");
            txtSoluongKM.requestFocus();
            return false;
        } else if (txt_NgayBDKM()) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống ");
            txtNgayBDKM.requestFocus();
            return false;
        } else if (txt_NgayKTKM()) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống ");
            txtNgayKTKM.requestFocus();
            return false;
        } else if (rootPaneCheckingEnabled) {

        }
        {
            return true;
        }

    }

    void coutSpct() {
        try {
            String sql = "Select count(*) from sanphamchitiet where TrangThai = 1";
            Connection con = DBConnect.getConnection();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                countSpct = r.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    void countALL() {
        coutHoaDon();
        coutKhachHang();
        coutNhanVien();
        coutDbSp();
        coutKM();
        coutSpct();
    }

    void fillALL() {
        filltableKhachHang(idKH);
        filltableHoaDonPhanTrang(idHD);
        filltableHoaDonBanHang();
        filltableNhanVien(idNV);
        fillTableSanPham(idSP);
        fillTableSpct(idSPCT);
        filltableidKM(idKM);
        showChatLieu1();
        showDegiay1();
        showLoaiGiay1();
        showMauSac1();
        showThuongHieu1();
    }

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        jcam.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 170));
        jcam.setVisible(true);

        exe.execute(this);

    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QRCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            com.google.zxing.Result rs = null;
            BufferedImage img = null;
            if (webcam.isOpen()) {
                if ((img = webcam.getImage()) == null) {
                    continue;
                }

            }
            LuminanceSource source = new BufferedImageLuminanceSource(img);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                rs = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                Logger.getLogger(QRCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (rs != null) {
                System.out.println("mã vừa quét được là :" + rs.getText());
                lbl_maqr.setText(rs.getText());

                // lấy idhoa don 
                int rowIndex = tbl_HoaDonBanHang.getSelectedRow();

                if (rowIndex != -1) {
                    // Nếu có hàng được chọn
                    String mahd = tbl_HoaDonBanHang.getValueAt(rowIndex, 1).toString();
                    System.out.println("Đã chọn vào bảng. ID hóa đơn là: " + mahd);

                    // code sử lý qr
                    String soluong = JOptionPane.showInputDialog(this, "Nhập số lượng sản phẩm");

                    try {
                        // Chuyển đổi chuỗi thành số 
                        int soLuongSPTang = Integer.parseInt(soluong);
                        String maspct = rs.getText();
                        if (soLuongSPTang > checkslsp(maspct) || soLuongSPTang <= 0) {
                            JOptionPane.showMessageDialog(this, "vui lòng kiểm tra số lượng");
                        } else {
                            HoaDonChiTiet ct = new HoaDonChiTiet();
                            ct.setMaSanPhamChiTiet(maspct);
                            ct.setSoLuong(Integer.parseInt(soluong));
                            ct.setMaHoaDon(mahd);
                            int idhoadon = Integer.parseInt(tbl_HoaDonBanHang.getValueAt(rowIndex, 0).toString());
                            chiTietService_IMPL.chon(ct, idhoadon);
                            int indext = tbl_HoaDonBanHang.getSelectedRow();
                            fillGioHang(idhoadon);
                            fillTableSpct(idSPCT);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "xin vui lòng nhập số");
                    }

                } else {
                    // Nếu không có hàng nào được chọn
                    JOptionPane.showMessageDialog(this, "chưa chọn hoá đơn");
                }

            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "none");
        t.setDaemon(true);
        return t;
    }

    public int checkslsp(String maspct) {
        int soluong = 0;
        Connection conn = DBConnect.getConnection();
        try {
            String sql = "SELECT SOLUONG from SANPHAMCHITIET where MASPCT = ? ";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, maspct);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                soluong = rs.getInt(1);

            }
            return soluong;

        } catch (Exception e) {
            return 0;
        }
    }

    public String checkMaSpctTheoIDspct(int idspct) {
        String maspct = null;
        Connection conn = DBConnect.getConnection();
        try {
            String sql = "SELECT MASPCT from SANPHAMCHITIET where IDSANPHAMCHITIET = " + (idspct) + " ";
            PreparedStatement ptm = conn.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                maspct = rs.getString(1);

            }
            return maspct;

        } catch (Exception e) {
            return null;
        }

    }

    void showLoaiGiay1() {
        cboLoaiGiay2.removeAllItems();
        cboLoaiGiay2.addItem("Tất cả");
        for (String c : spdChiTiet_IMPL.getcboLoaiGiay()) {
            cboLoaiGiay2.addItem(c);
        }
    }

    void showThuongHieu1() {
        cboThuongHieu2.removeAllItems();
        cboThuongHieu2.addItem("Tất cả");
        for (String c : spdChiTiet_IMPL.getcboThuongHieu()) {
            cboThuongHieu2.addItem(c);
        }
    }

    void showMauSac1() {
        cboMau2.removeAllItems();
        cboMau2.addItem("Tất cả");
        for (String c : spdChiTiet_IMPL.getcboMauSac()) {
            cboMau2.addItem(c);
        }
    }

    void showDegiay1() {
        cboDeGiay2.removeAllItems();
        cboDeGiay2.addItem("Tất cả");
        for (String c : spdChiTiet_IMPL.getcboDeGiay()) {
            cboDeGiay2.addItem(c);
        }
    }

    void showChatLieu1() {
        cboChatLieu2.removeAllItems();
        cboChatLieu2.addItem("Tất cả");
        for (String c : spdChiTiet_IMPL.getcboChatLieu()) {
            cboChatLieu2.addItem(c);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Home;
    private javax.swing.JButton btnApMaKhuyenMai;
    private javax.swing.JButton btnBieuDo;
    private javax.swing.JButton btnDoiTrangThaiKH;
    private javax.swing.JButton btnDoiTrangThaiKM;
    private javax.swing.JButton btnHuyHoaDonn;
    private javax.swing.JButton btnNewKh;
    private javax.swing.JButton btnNewKh1;
    private javax.swing.JButton btnNewKhuyenMai;
    private javax.swing.JButton btnSauKM;
    private javax.swing.JButton btnSuaKh;
    private javax.swing.JButton btnThemKh;
    private javax.swing.JButton btnThemKh1;
    private javax.swing.JButton btnTimKiemKH;
    private javax.swing.JButton btnTimKiemKM;
    private javax.swing.JButton btnTimKiemSoDienThoaiHD;
    private javax.swing.JButton btnTkiemMaSPBanHang;
    private javax.swing.JButton btnTruocKM;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JButton btn_BANHANG;
    private javax.swing.JButton btn_DoiTTNV;
    private javax.swing.JButton btn_LamMoiNV;
    private javax.swing.JButton btn_QLHD;
    private javax.swing.JButton btn_QLKH;
    private javax.swing.JButton btn_QLKM;
    private javax.swing.JButton btn_QLNV;
    private javax.swing.JButton btn_QLSP;
    private javax.swing.JButton btn_SauNV;
    private javax.swing.JButton btn_SuaNV;
    private javax.swing.JButton btn_THONGKE;
    private javax.swing.JButton btn_TKNV;
    private javax.swing.JButton btn_ThemNV;
    private javax.swing.JButton btn_TruocNv;
    private javax.swing.JButton btn_congGioHang;
    private javax.swing.JButton btn_maxHD;
    private javax.swing.JButton btn_minHD;
    private javax.swing.JButton btn_newSP;
    private javax.swing.JButton btn_nexthoadon;
    private javax.swing.JButton btn_sau;
    private javax.swing.JButton btn_sauHD;
    private javax.swing.JButton btn_sauKH;
    private javax.swing.JButton btn_sauSP;
    private javax.swing.JButton btn_suaKM;
    private javax.swing.JButton btn_suaSP;
    private javax.swing.JButton btn_taohoadon;
    private javax.swing.JButton btn_thanhtoan;
    private javax.swing.JButton btn_themGioHang;
    private javax.swing.JButton btn_themKM;
    private javax.swing.JButton btn_themSP;
    private javax.swing.JButton btn_themkhachhang;
    private javax.swing.JButton btn_timKhachTheoSDT;
    private javax.swing.JButton btn_timKiem;
    private javax.swing.JButton btn_truoc;
    private javax.swing.JButton btn_truocHD;
    private javax.swing.JButton btn_truocKH;
    private javax.swing.JButton btn_truocSP;
    private javax.swing.JButton btn_truochoadon;
    private javax.swing.JButton btn_vangLai;
    private javax.swing.JButton btn_xem;
    private javax.swing.JButton btn_xemctSP;
    private javax.swing.JButton btn_xoa1;
    private javax.swing.JButton btndoiTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JComboBox<String> cboChatLieu2;
    private javax.swing.JComboBox<String> cboDeGiay2;
    private javax.swing.JComboBox<String> cboLoaiGiay2;
    private javax.swing.JComboBox<String> cboMau2;
    private javax.swing.JComboBox<String> cboThuongHieu2;
    private javax.swing.JComboBox<String> cbo_LoaiKM;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JFrame jaddkh;
    private javax.swing.JPanel jcam;
    private javax.swing.JTabbedPane jtab;
    private javax.swing.JLabel lblIDKhachHang;
    private javax.swing.JLabel lblIDKhachHang1;
    private javax.swing.JLabel lblIDNhanVien;
    private javax.swing.JLabel lblIDSanPham;
    private javax.swing.JLabel lblIdKhuyenMai;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblTrangKM;
    private javax.swing.JLabel lbl_chucvu;
    private javax.swing.JLabel lbl_loaikm;
    private javax.swing.JLabel lbl_maqr;
    private javax.swing.JLabel lbl_nameud;
    private javax.swing.JLabel lbl_ngay;
    private javax.swing.JLabel lbl_sotrangspct;
    private javax.swing.JLabel lbl_tennv;
    private javax.swing.JLabel lbl_thanhtoan;
    private javax.swing.JLabel lbl_thieu;
    private javax.swing.JLabel lbl_tienthua;
    private javax.swing.JLabel lbl_trangHD;
    private javax.swing.JLabel lbl_trangKH;
    private javax.swing.JLabel lbl_trangNV;
    private javax.swing.JLabel lbl_trangSP;
    private javax.swing.JLabel lbl_tranghoadon;
    private javax.swing.JLabel lbltongtien;
    private javax.swing.JPanel panelSP;
    private javax.swing.JPanel panelThanhToan;
    private javax.swing.JPanel panelgiohang;
    private javax.swing.JRadioButton rdoDangHoatDongKH;
    private javax.swing.JRadioButton rdoDangHoatDongKH1;
    private javax.swing.JRadioButton rdoKhongHoatDongKH;
    private javax.swing.JRadioButton rdoKhongHoatDongKH2;
    private javax.swing.JRadioButton rdo_hdKM;
    private javax.swing.JRadioButton rdo_hdNV;
    private javax.swing.JRadioButton rdo_khdKM;
    private javax.swing.JRadioButton rdo_khdNV;
    private javax.swing.JRadioButton rdo_namKH;
    private javax.swing.JRadioButton rdo_namKH1;
    private javax.swing.JRadioButton rdo_namNV;
    private javax.swing.JRadioButton rdo_nuKH;
    private javax.swing.JRadioButton rdo_nuKH1;
    private javax.swing.JRadioButton rdo_nuNV;
    private javax.swing.JTable tblBangHoaDonCTHD;
    private javax.swing.JTable tblBangHoaDonHD;
    private javax.swing.JTable tblBangKhachHang;
    private javax.swing.JTable tblBangKhuyenMai;
    private javax.swing.JTable tblBangSanPham;
    private javax.swing.JTable tblSanPhamBanHang;
    private javax.swing.JTable tbl_HoaDonBanHang;
    private javax.swing.JTable tbl_bangNhanVien;
    private javax.swing.JTable tbl_bangthongke;
    private javax.swing.JTable tbl_giohang;
    private javax.swing.JTextField txtMaKhuyenMai;
    public static javax.swing.JTextField txtMaSp;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JTextField txtNgayBDKM;
    private javax.swing.JTextField txtNgayKTKM;
    private javax.swing.JTextField txtSoluongKM;
    public static javax.swing.JTextField txtTenKM;
    private javax.swing.JTextField txtTensp;
    private javax.swing.JTextField txtTimKiemTheoNgayHDon;
    private javax.swing.JTextField txtTimKiemTheoNgayKTHDon;
    private javax.swing.JTextField txtTimKiemTheoTenKhachHangHDON;
    private javax.swing.JTextField txtTimMaSPBanHang;
    private javax.swing.JTextField txtTimSp;
    private javax.swing.JTextField txtTkiemKH;
    private javax.swing.JTextField txt_TKKM;
    private javax.swing.JTextField txt_diachiKH;
    private javax.swing.JTextField txt_diachiKH1;
    private javax.swing.JTextField txt_dienthoaiKH;
    private javax.swing.JTextField txt_dienthoaiKH1;
    private javax.swing.JTextField txt_emailKH;
    private javax.swing.JTextField txt_emailKH1;
    private javax.swing.JTextField txt_emailNV;
    private javax.swing.JTextField txt_idchucvuNV;
    private javax.swing.JTextField txt_khachdua;
    private javax.swing.JTextField txt_khuyenmai;
    private javax.swing.JTextField txt_luongNV;
    private javax.swing.JTextField txt_maKh;
    private javax.swing.JTextField txt_maKh1;
    private javax.swing.JTextField txt_maNV;
    private javax.swing.JTextField txt_mkNV;
    private javax.swing.JTextField txt_ngaybdtk;
    private javax.swing.JTextField txt_ngaykttk;
    private javax.swing.JTextField txt_sdtNV;
    public static javax.swing.JTextField txt_tenDNNV;
    public static javax.swing.JTextField txt_tenKH;
    public static javax.swing.JTextField txt_tenKH1;
    private javax.swing.JTextField txt_tenNhanVien;
    public static javax.swing.JTextField txt_tenkhachtt;
    private javax.swing.JTextField txt_tkNV;
    private javax.swing.JLabel txt_tongtien;
    private javax.swing.JPanel viewbanhang;
    private javax.swing.JPanel viewhoadon;
    private javax.swing.JPanel viewkhachhang;
    private javax.swing.JPanel viewkhuyenmai;
    private javax.swing.JPanel viewnhanvien;
    private javax.swing.JPanel viewsp;
    private javax.swing.JPanel viewthongke;
    // End of variables declaration//GEN-END:variables

    private void tong() {
        float tongtien = 0;
        if (tbl_giohang.getRowCount() > 0) {
            for (int i = 0; i < tbl_giohang.getRowCount(); i++) {
                tongtien = tongtien + Float.valueOf(tbl_giohang.getValueAt(i, 8).toString());
                long format = Math.round(tongtien);
                txt_tongtien.setText("" + format);
                lbl_thanhtoan.setText("" + format);
            }
        } else {
            txt_tongtien.setText("" + tongtien);
        }
    }

    public int checkSLKM(String makm) {
        int soluong = 0;
        String sql = """
                              DECLARE @idvc INT
                                    SET @idvc = (SELECT IDKHUYENMAI FROM KHUYENMAI WHERE MAGIAMGIA = ? )
                                    SELECT COUNT(IDKHUYENMAI) 
                                    FROM HOADON
                                    WHERE IDKHUYENMAI = @idvc;     
                            """;
        try {

            Connection conn = DBConnect.getConnection();

            PreparedStatement ptm = conn.prepareStatement(sql);
            ptm.setString(1, makm);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {

                soluong = rs.getInt(1);

                System.out.println("số voucher đã sửa dụng là : " + soluong);

            }

            return soluong;

        } catch (Exception e) {
            return 0;
        }

    }

    void filltable(String ngaybd, String ngaykt) {
        mol = (DefaultTableModel) tbl_bangthongke.getModel();
        mol.setRowCount(0);
        double tongtien = 0;
        for (ThongKe tk : sevice.getall(ngaybd, ngaykt)) {
            Object[] todata = new Object[]{tk.getNgaykt(), tk.getDoanhthu(), tk.getTensp()};
            mol.addRow(todata);
            tongtien += tk.getDoanhthu();
            lbltongtien.setText(String.valueOf(tongtien));

        }
    }
}
