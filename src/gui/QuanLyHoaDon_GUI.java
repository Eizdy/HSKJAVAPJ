//package gui;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.MatteBorder;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//
//public class QuanLyHoaDon_GUI extends JFrame {
//    private JTable tblHoaDon;
//    private DefaultTableModel modelHoaDon;
//    private JTextField txtMaHoaDon, txtNgayLap;
//    private JCheckBox chkMaHoaDon, chkNgayLap;
//    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
//
//    public QuanLyHoaDon_GUI() {
//        setTitle("Hóa đơn - Quản lý Rạp Chiếu Phim");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(1280, 800);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        UIManager.put("Label.foreground", Color.WHITE);
//        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);
//
//        add(createHeader(), BorderLayout.NORTH);
//        add(createSidebar(), BorderLayout.WEST);
//        add(createMainContent(), BorderLayout.CENTER);
//    }
//
//    private JPanel createHeader() {
//        JLabel lbl = new JLabel("QUẢN LÝ HÓA ĐƠN", SwingConstants.CENTER);
//        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
//        lbl.setForeground(Color.WHITE);
//        lbl.setOpaque(true);
//        lbl.setBackground(new Color(30, 30, 30));
//        lbl.setBorder(new MatteBorder(0, 0, 2, 0, new Color(64, 64, 64)));
//
//        JPanel header = new JPanel(new BorderLayout());
//        header.add(lbl);
//        return header;
//    }
//
//    private JPanel createSidebar() {
//        JPanel menu = new JPanel();
//        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
//        menu.setBackground(new Color(25, 25, 25));
//        menu.setPreferredSize(new Dimension(180, 0));
//
//        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hóa đơn", "Bán vé", "Thống kê", "Đăng xuất"};
//
//        for (String item : items) {
//            JButton btn = new JButton(item);
//            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
//            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
//            btn.setBackground(new Color(40, 40, 40));
//            btn.setFocusPainted(false);
//            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
//            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//            btn.addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseEntered(java.awt.event.MouseEvent evt) {
//                    btn.setBackground(new Color(60, 60, 60));
//                }
//
//                public void mouseExited(java.awt.event.MouseEvent evt) {
//                    btn.setBackground(new Color(40, 40, 40));
//                }
//            });
//
//            btn.addActionListener(e -> {
//                dispose();
//                switch (item) {
//                    case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
//                    case "Phim" -> new QuanLyPhim_GUI().setVisible(true);
//                    case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
//                    case "Hóa đơn" -> new QuanLyHoaDon_GUI().setVisible(true);
//                    case "Đăng xuất" -> System.exit(0);
//                    default -> {}
//                }
//            });
//
//            menu.add(Box.createVerticalStrut(10));
//            menu.add(btn);
//        }
//
//        return menu;
//    }
//
//    private JPanel createMainContent() {
//        JPanel main = new JPanel(new BorderLayout(10, 10));
//        main.setBorder(new EmptyBorder(10, 10, 10, 10));
//        main.setBackground(new Color(45, 45, 45));
//
//        String[] cols = {"Mã hóa đơn", "Ngày lập", "Số lượng", "Mã vé", "Giá vé", "Mã lịch chiếu", "Mã ghế", "Mã khách hàng", "Mã nhân viên"};
//        modelHoaDon = new DefaultTableModel(cols, 0);
//        tblHoaDon = new JTable(modelHoaDon);
//        tblHoaDon.setBackground(new Color(60, 60, 60));
//        tblHoaDon.setForeground(Color.WHITE);
//        tblHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//        tblHoaDon.setRowHeight(22);
//
//        JScrollPane scroll = new JScrollPane(tblHoaDon);
//        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách hóa đơn"));
//        main.add(scroll, BorderLayout.CENTER);
//
//        JPanel east = new JPanel();
//        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
//        east.setBackground(new Color(45, 45, 45));
//        east.setPreferredSize(new Dimension(250, 0));
//
//        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
//        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblTieuDe.setForeground(Color.WHITE);
//
//        chkMaHoaDon = new JCheckBox("Theo mã hóa đơn");
//        chkMaHoaDon.setBackground(new Color(45, 45, 45));
//        chkMaHoaDon.setForeground(Color.WHITE);
//        chkMaHoaDon.setSelected(true);
//
//        txtMaHoaDon = new JTextField();
//        txtMaHoaDon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        txtMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
//        txtMaHoaDon.setBackground(new Color(60, 60, 60));
//        txtMaHoaDon.setForeground(Color.WHITE);
//
//        chkNgayLap = new JCheckBox("Theo ngày lập");
//        chkNgayLap.setBackground(new Color(45, 45, 45));
//        chkNgayLap.setForeground(Color.WHITE);
//        chkNgayLap.setSelected(false);
//
//        txtNgayLap = new JTextField();
//        txtNgayLap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        txtNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập (yyyy-MM-dd)"));
//        txtNgayLap.setBackground(new Color(60, 60, 60));
//        txtNgayLap.setForeground(Color.WHITE);
//        txtNgayLap.setEnabled(false);
//
//        chkMaHoaDon.addActionListener(e -> {
//            if (chkMaHoaDon.isSelected()) {
//                chkNgayLap.setSelected(false);
//                txtMaHoaDon.setEnabled(true);
//                txtNgayLap.setEnabled(false);
//            } else {
//                txtMaHoaDon.setEnabled(false);
//            }
//        });
//
//        chkNgayLap.addActionListener(e -> {
//            if (chkNgayLap.isSelected()) {
//                chkMaHoaDon.setSelected(false);
//                txtNgayLap.setEnabled(true);
//                txtMaHoaDon.setEnabled(false);
//            } else {
//                txtNgayLap.setEnabled(false);
//            }
//        });
//
//        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        actionPanel.setBackground(new Color(45, 45, 45));
//        btnTimKiem = new JButton("Tìm kiếm");
//        btnTimKiem.setBackground(new Color(0, 120, 215));
//        btnTimKiem.setForeground(Color.WHITE);
//        btnXoaTrang = new JButton("Xóa trắng");
//        btnXoaTrang.setBackground(new Color(200, 50, 50));
//        btnXoaTrang.setForeground(Color.WHITE);
//        actionPanel.add(btnTimKiem);
//        actionPanel.add(btnXoaTrang);
//
//        east.add(lblTieuDe);
//        east.add(Box.createVerticalStrut(10));
//        east.add(chkMaHoaDon);
//        east.add(txtMaHoaDon);
//        east.add(Box.createVerticalStrut(10));
//        east.add(chkNgayLap);
//        east.add(txtNgayLap);
//        east.add(Box.createVerticalStrut(15));
//        east.add(actionPanel);
//
//        main.add(east, BorderLayout.EAST);
//
//        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
//        south.setBackground(new Color(45, 45, 45));
//
//        btnThem = new JButton("Thêm");
//        btnThem.setBackground(new Color(0, 120, 215));
//        btnThem.setForeground(Color.WHITE);
//
//        btnXoa = new JButton("Xóa");
//        btnXoa.setBackground(new Color(200, 50, 50));
//        btnXoa.setForeground(Color.WHITE);
//
//        btnSua = new JButton("Sửa");
//        btnSua.setBackground(new Color(0, 120, 215));
//        btnSua.setForeground(Color.WHITE);
//
//        btnHienTatCa = new JButton("Hiện tất cả");
//        btnHienTatCa.setBackground(new Color(0, 120, 215));
//        btnHienTatCa.setForeground(Color.WHITE);
//
//        south.add(btnThem);
//        south.add(btnXoa);
//        south.add(btnSua);
//        south.add(btnHienTatCa);
//        main.add(south, BorderLayout.SOUTH);
//
//        return main;
//    }
//
//    public static void main(String[] args) {
//        new QuanLyHoaDon_GUI().setVisible(true);
//    }
//}

//package gui;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.MatteBorder;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.List;
//import dao.HoaDon_DAO;
//import entity.HoaDon;
//
//public class QuanLyHoaDon_GUI extends JFrame {
//    private JTable tblHoaDon;
//    private DefaultTableModel modelHoaDon;
//    private JTextField txtMaHoaDon, txtNgayLap;
//    private JCheckBox chkMaHoaDon, chkNgayLap;
//    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;
//    private HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
//
//    public QuanLyHoaDon_GUI() {
//        setTitle("Hóa đơn - Quản lý Rạp Chiếu Phim");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(1280, 800);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        UIManager.put("Label.foreground", Color.WHITE);
//        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);
//
//        add(createHeader(), BorderLayout.NORTH);
//        add(createSidebar(), BorderLayout.WEST);
//        add(createMainContent(), BorderLayout.CENTER);
//
//        loadDataToTable();
//        addEventListeners();
//    }
//
//    private JPanel createHeader() {
//        JLabel lbl = new JLabel("QUẢN LÝ HÓA ĐƠN", SwingConstants.CENTER);
//        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
//        lbl.setForeground(Color.WHITE);
//        lbl.setOpaque(true);
//        lbl.setBackground(new Color(30, 30, 30));
//        lbl.setBorder(new MatteBorder(0, 0, 2, 0, new Color(64, 64, 64)));
//
//        JPanel header = new JPanel(new BorderLayout());
//        header.add(lbl);
//        return header;
//    }
//
//    private JPanel createSidebar() {
//        JPanel menu = new JPanel();
//        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
//        menu.setBackground(new Color(25, 25, 25));
//        menu.setPreferredSize(new Dimension(180, 0));
//
//        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hóa đơn", "Bán vé", "Thống kê", "Đăng xuất"};
//
//        for (String item : items) {
//            JButton btn = new JButton(item);
//            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
//            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
//            btn.setBackground(new Color(40, 40, 40));
//            btn.setFocusPainted(false);
//            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
//            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//            menu.add(Box.createVerticalStrut(10));
//            menu.add(btn);
//        }
//
//        return menu;
//    }
//
//    private JPanel createMainContent() {
//        JPanel main = new JPanel(new BorderLayout(10, 10));
//        main.setBorder(new EmptyBorder(10, 10, 10, 10));
//        main.setBackground(new Color(45, 45, 45));
//
//        String[] cols = {"Mã hóa đơn", "Ngày lập", "Số lượng", "Mã vé", "Giá vé", "Tổng tiền", "Mã lịch chiếu", "Mã khách hàng", "Mã nhân viên"};
//        modelHoaDon = new DefaultTableModel(cols, 0);
//        tblHoaDon = new JTable(modelHoaDon);
//        tblHoaDon.setBackground(new Color(60, 60, 60));
//        tblHoaDon.setForeground(Color.WHITE);
//        tblHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//        tblHoaDon.setRowHeight(22);
//
//        JScrollPane scroll = new JScrollPane(tblHoaDon);
//        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách hóa đơn"));
//        main.add(scroll, BorderLayout.CENTER);
//
//        JPanel east = new JPanel();
//        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
//        east.setBackground(new Color(45, 45, 45));
//        east.setPreferredSize(new Dimension(250, 0));
//
//        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
//        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
//        lblTieuDe.setForeground(Color.WHITE);
//
//        chkMaHoaDon = new JCheckBox("Theo mã hóa đơn");
//        chkMaHoaDon.setBackground(new Color(45, 45, 45));
//        chkMaHoaDon.setForeground(Color.WHITE);
//        chkMaHoaDon.setSelected(true);
//
//        txtMaHoaDon = new JTextField();
//        txtMaHoaDon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        txtMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
//        txtMaHoaDon.setBackground(new Color(60, 60, 60));
//        txtMaHoaDon.setForeground(Color.WHITE);
//
//        chkNgayLap = new JCheckBox("Theo ngày lập");
//        chkNgayLap.setBackground(new Color(45, 45, 45));
//        chkNgayLap.setForeground(Color.WHITE);
//        chkNgayLap.setSelected(false);
//
//        txtNgayLap = new JTextField();
//        txtNgayLap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        txtNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập (yyyy-MM-dd)"));
//        txtNgayLap.setBackground(new Color(60, 60, 60));
//        txtNgayLap.setForeground(Color.WHITE);
//        txtNgayLap.setEnabled(false);
//
//        east.add(lblTieuDe);
//        east.add(Box.createVerticalStrut(10));
//        east.add(chkMaHoaDon);
//        east.add(txtMaHoaDon);
//        east.add(Box.createVerticalStrut(10));
//        east.add(chkNgayLap);
//        east.add(txtNgayLap);
//        east.add(Box.createVerticalStrut(15));
//
//        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        actionPanel.setBackground(new Color(45, 45, 45));
//        btnTimKiem = new JButton("Tìm kiếm");
//        btnTimKiem.setBackground(new Color(0, 120, 215));
//        btnTimKiem.setForeground(Color.WHITE);
//        btnXoaTrang = new JButton("Xóa trắng");
//        btnXoaTrang.setBackground(new Color(200, 50, 50));
//        btnXoaTrang.setForeground(Color.WHITE);
//        actionPanel.add(btnTimKiem);
//        actionPanel.add(btnXoaTrang);
//
//        east.add(actionPanel);
//
//        main.add(east, BorderLayout.EAST);
//
//        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
//        south.setBackground(new Color(45, 45, 45));
//
//        btnThem = new JButton("Thêm");
//        btnThem.setBackground(new Color(0, 120, 215));
//        btnThem.setForeground(Color.WHITE);
//
//        btnXoa = new JButton("Xóa");
//        btnXoa.setBackground(new Color(200, 50, 50));
//        btnXoa.setForeground(Color.WHITE);
//
//        btnSua = new JButton("Sửa");
//        btnSua.setBackground(new Color(0, 120, 215));
//        btnSua.setForeground(Color.WHITE);
//
//        btnHienTatCa = new JButton("Hiện tất cả");
//        btnHienTatCa.setBackground(new Color(0, 120, 215));
//        btnHienTatCa.setForeground(Color.WHITE);
//
//        south.add(btnThem);
//        south.add(btnXoa);
//        south.add(btnSua);
//        south.add(btnHienTatCa);
//        main.add(south, BorderLayout.SOUTH);
//
//        return main;
//    }
//
//    private void loadDataToTable() {
//        modelHoaDon.setRowCount(0);
//        List<HoaDon> list = hoaDonDAO.layTatCaHoaDon();
//        for (HoaDon hd : list) {
//            modelHoaDon.addRow(new Object[]{
//                hd.getMaHoaDon(),
//                hd.getNgayLap(),
//                hd.getSoLuong(),
//                hd.getVe().getMaVe(),
//                hd.getVe().getGiaVe(),
//                hd.getVe().getGiaVe() * hd.getSoLuong(),
//                hd.getVe().getLichChieu().getMaLichChieu(),
//                (hd.getVe().getKhachHang() != null) ? hd.getVe().getKhachHang().getMaKhachHang() : "NULL",
//                hd.getMaNV().getMaNhanVien()
//            });
//        }
//    }
//
//    private void addEventListeners() {
//        btnHienTatCa.addActionListener(e -> loadDataToTable());
//
//        btnXoaTrang.addActionListener(e -> {
//            txtMaHoaDon.setText("");
//            txtNgayLap.setText("");
//        });
//
//        btnTimKiem.addActionListener(e -> {
//            if (chkMaHoaDon.isSelected()) {
//                String maHD = txtMaHoaDon.getText().trim();
//                if (!maHD.isEmpty()) {
//                    HoaDon hd = hoaDonDAO.timHoaDonTheoMa(maHD);
//                    modelHoaDon.setRowCount(0);
//                    if (hd != null) {
//                        modelHoaDon.addRow(new Object[]{
//                            hd.getMaHoaDon(),
//                            hd.getNgayLap(),
//                            hd.getSoLuong(),
//                            hd.getVe().getMaVe(),
//                            hd.getVe().getGiaVe(),
//                            hd.getVe().getGiaVe() * hd.getSoLuong(),
//                            hd.getVe().getLichChieu().getMaLichChieu(),
//                            (hd.getVe().getKhachHang() != null) ? hd.getVe().getKhachHang().getMaKhachHang() : "NULL",
//                            hd.getMaNV().getMaNhanVien()
//                        });
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn.");
//                    }
//                }
//            } else if (chkNgayLap.isSelected()) {
//                String ngayLap = txtNgayLap.getText().trim();
//                if (!ngayLap.isEmpty()) {
//                    List<HoaDon> list = hoaDonDAO.getHoaDonTheoNgay(ngayLap);
//                    modelHoaDon.setRowCount(0);
//                    for (HoaDon hd : list) {
//                        modelHoaDon.addRow(new Object[]{
//                            hd.getMaHoaDon(),
//                            hd.getNgayLap(),
//                            hd.getSoLuong(),
//                            hd.getVe().getMaVe(),
//                            hd.getVe().getGiaVe(),
//                            hd.getVe().getGiaVe() * hd.getSoLuong(),
//                            hd.getVe().getLichChieu().getMaLichChieu(),
//                            (hd.getVe().getKhachHang() != null) ? hd.getVe().getKhachHang().getMaKhachHang() : "NULL",
//                            hd.getMaNV().getMaNhanVien()
//                        });
//                    }
//                }
//            }
//        });
//
//        btnXoa.addActionListener(e -> {
//            int row = tblHoaDon.getSelectedRow();
//            if (row >= 0) {
//                String maHD = modelHoaDon.getValueAt(row, 0).toString();
//                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hóa đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//                if (confirm == JOptionPane.YES_OPTION) {
//                    if (hoaDonDAO.xoaHoaDon(maHD)) {
//                        modelHoaDon.removeRow(row);
//                        JOptionPane.showMessageDialog(this, "Xóa thành công.");
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Xóa thất bại.");
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa.");
//            }
//        });
//
//        btnThem.addActionListener(e -> {
//            new ThemHoaDon_GUI(this).setVisible(true);
//        });
//
//        btnSua.addActionListener(e -> {
//            int row = tblHoaDon.getSelectedRow();
//            if (row >= 0) {
//                String maHD = modelHoaDon.getValueAt(row, 0).toString();
//                new SuaHoaDon_GUI(this, maHD).setVisible(true);
//            } else {
//                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa.");
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        new QuanLyHoaDon_GUI().setVisible(true);
//    }
//}

package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.HoaDon_DAO;
import entity.HoaDon;

public class QuanLyHoaDon_GUI extends JFrame {
    private JTable tblHoaDon;
    private DefaultTableModel modelHoaDon;
    private JTextField txtMaHoaDon, txtNgayLap;
    private JCheckBox chkMaHoaDon, chkNgayLap;
    private JButton btnTimKiem, btnXoaTrang, btnHienTatCa;
    private HoaDon_DAO hoaDonDAO = new HoaDon_DAO();

    public QuanLyHoaDon_GUI() {
        setTitle("Hóa đơn - Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        loadDataToTable();
        addEventListeners();
    }

    private JPanel createHeader() {
        JLabel lbl = new JLabel("QUẢN LÝ HÓA ĐƠN", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(30, 30, 30));
        lbl.setBorder(new MatteBorder(0, 0, 2, 0, new Color(64, 64, 64)));

        JPanel header = new JPanel(new BorderLayout());
        header.add(lbl);
        return header;
    }

    private JPanel createSidebar() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(25, 25, 25));
        menu.setPreferredSize(new Dimension(180, 0));

        String[] items = {"Trang chủ", "Phim", "Suất chiếu", "Nhân viên", "Hoá đơn", "Bán vé", "Đăng xuất"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(item.equals("Đăng xuất") ? Color.RED : Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(60, 60, 60));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(40, 40, 40));
                }
            });

            btn.addActionListener(e -> {
                dispose();
                switch (item) {
                    case "Trang chủ" -> new TrangChuRapChieuPhim_GUI().setVisible(true);
                    case "Phim" -> new QuanLyPhim_GUI().setVisible(true);
                    case "Suất chiếu" -> new SuatChieu_GUI().setVisible(true);
                    case "Nhân viên" -> new QuanLyNhanVien_GUI().setVisible(true);
                    case "Hoá đơn" -> new QuanLyHoaDon_GUI().setVisible(true);
                    case "Bán vé" -> new QuanLyBanVe_GUI().setVisible(true);
                    case "Đăng xuất" -> System.exit(0);
                    default -> {}
                }
            });

            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }
        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        String[] cols = {"Mã hóa đơn", "Ngày lập", "Số lượng", "Mã vé", "Giá vé", "Tổng tiền", "Mã lịch chiếu", "Mã khách hàng", "Mã nhân viên"};
        modelHoaDon = new DefaultTableModel(cols, 0);
        tblHoaDon = new JTable(modelHoaDon);
        tblHoaDon.setBackground(new Color(60, 60, 60));
        tblHoaDon.setForeground(Color.WHITE);
        tblHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblHoaDon.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(tblHoaDon);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách hóa đơn"));
        main.add(scroll, BorderLayout.CENTER);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(new Color(45, 45, 45));
        east.setPreferredSize(new Dimension(250, 0));

        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTieuDe.setForeground(Color.WHITE);

        chkMaHoaDon = new JCheckBox("Theo mã hóa đơn");
        chkMaHoaDon.setBackground(new Color(45, 45, 45));
        chkMaHoaDon.setForeground(Color.WHITE);
        chkMaHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkMaHoaDon.setPreferredSize(new Dimension(200, 30));
        chkMaHoaDon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        chkMaHoaDon.setAlignmentX(Component.CENTER_ALIGNMENT);
        chkMaHoaDon.setSelected(true);

        txtMaHoaDon = new JTextField();
        txtMaHoaDon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        txtMaHoaDon.setBackground(new Color(60, 60, 60));
        txtMaHoaDon.setForeground(Color.WHITE);
        txtMaHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        chkNgayLap = new JCheckBox("Theo ngày lập");
        chkNgayLap.setBackground(new Color(45, 45, 45));
        chkNgayLap.setForeground(Color.WHITE);
        chkNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkNgayLap.setPreferredSize(new Dimension(200, 30));
        chkNgayLap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        chkNgayLap.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtNgayLap = new JTextField();
        txtNgayLap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập (yyyy-MM-dd)"));
        txtNgayLap.setBackground(new Color(60, 60, 60));
        txtNgayLap.setForeground(Color.WHITE);
        txtNgayLap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNgayLap.setEnabled(false);

        east.add(lblTieuDe);
        east.add(Box.createVerticalStrut(10));
        east.add(chkMaHoaDon);
        east.add(txtMaHoaDon);
        east.add(Box.createVerticalStrut(10));
        east.add(chkNgayLap);
        east.add(txtNgayLap);
        east.add(Box.createVerticalStrut(15));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(new Color(45, 45, 45));
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 120, 215));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnXoaTrang = new JButton("Xóa trắng");
        btnXoaTrang.setBackground(new Color(200, 50, 50));
        btnXoaTrang.setForeground(Color.WHITE);
        btnXoaTrang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnXoaTrang.setCursor(new Cursor(Cursor.HAND_CURSOR));

        actionPanel.add(btnTimKiem);
        actionPanel.add(btnXoaTrang);

        east.add(actionPanel);

        main.add(east, BorderLayout.EAST);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        south.setBackground(new Color(45, 45, 45));

        btnHienTatCa = new JButton("Hiện tất cả");
        btnHienTatCa.setBackground(new Color(0, 120, 215));
        btnHienTatCa.setForeground(Color.WHITE);
        btnHienTatCa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnHienTatCa.setPreferredSize(new Dimension(120, 35));
        btnHienTatCa.setCursor(new Cursor(Cursor.HAND_CURSOR));

        south.add(btnHienTatCa);
        main.add(south, BorderLayout.SOUTH);

        return main;
    }

    private void loadDataToTable() {
        modelHoaDon.setRowCount(0);
        List<HoaDon> list;
        try {
            list = hoaDonDAO.layTatCaHoaDon();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (HoaDon hd : list) {
            double giaVe = hd.getVe().getGiaVe();
            double tongTien = giaVe * hd.getSoLuong();
            String maLichChieu = (hd.getVe().getLichChieu() != null) ? hd.getVe().getLichChieu().getMaLichChieu() : "N/A";
            String maKhachHang = (hd.getVe().getKhachHang() != null) ? hd.getVe().getKhachHang().getMaKhachHang() : "NULL";

            modelHoaDon.addRow(new Object[]{
                    hd.getMaHoaDon(),
                    hd.getNgayLap(),
                    hd.getSoLuong(),
                    hd.getVe().getMaVe(),
                    giaVe,
                    tongTien,
                    maLichChieu,
                    maKhachHang,
                    hd.getMaNV().getMaNV()
            });
        }
    }

    private void addEventListeners() {
        btnHienTatCa.addActionListener(e -> loadDataToTable());

        btnXoaTrang.addActionListener(e -> {
            txtMaHoaDon.setText("");
            txtNgayLap.setText("");
        });

        btnTimKiem.addActionListener(e -> {
            if (chkMaHoaDon.isSelected()) {
                String maHD = txtMaHoaDon.getText().trim();
                if (!maHD.isEmpty()) {
                    HoaDon hd;
                    try {
                        hd = hoaDonDAO.timHoaDonTheoMa(maHD);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    modelHoaDon.setRowCount(0);
                    if (hd != null) {
                        double giaVe = hd.getVe().getGiaVe();
                        double tongTien = giaVe * hd.getSoLuong();
                        String maLichChieu = (hd.getVe().getLichChieu() != null) ? hd.getVe().getLichChieu().getMaLichChieu() : "N/A";
                        String maKhachHang = (hd.getVe().getKhachHang() != null) ? hd.getVe().getKhachHang().getMaKhachHang() : "NULL";

                        modelHoaDon.addRow(new Object[]{
                                hd.getMaHoaDon(),
                                hd.getNgayLap(),
                                hd.getSoLuong(),
                                hd.getVe().getMaVe(),
                                giaVe,
                                tongTien,
                                maLichChieu,
                                maKhachHang,
                                hd.getMaNV().getMaNV()
                        });
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            } else if (chkNgayLap.isSelected()) {
                String ngayLap = txtNgayLap.getText().trim();
                if (!ngayLap.isEmpty()) {
                    List<HoaDon> list;
                    try {
                        list = hoaDonDAO.getHoaDonTheoNgay(ngayLap);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    modelHoaDon.setRowCount(0);
                    for (HoaDon hd : list) {
                        double giaVe = hd.getVe().getGiaVe();
                        double tongTien = giaVe * hd.getSoLuong();
                        String maLichChieu = (hd.getVe().getLichChieu() != null) ? hd.getVe().getLichChieu().getMaLichChieu() : "N/A";
                        String maKhachHang = (hd.getVe().getKhachHang() != null) ? hd.getVe().getKhachHang().getMaKhachHang() : "NULL";

                        modelHoaDon.addRow(new Object[]{
                                hd.getMaHoaDon(),
                                hd.getNgayLap(),
                                hd.getSoLuong(),
                                hd.getVe().getMaVe(),
                                giaVe,
                                tongTien,
                                maLichChieu,
                                maKhachHang,
                                hd.getMaNV().getMaNV()
                        });
                    }
                    if (list.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn cho ngày " + ngayLap + ".", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày lập (yyyy-MM-dd).", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        chkMaHoaDon.addActionListener(e -> {
            if (chkMaHoaDon.isSelected()) {
                chkNgayLap.setSelected(false);
                txtMaHoaDon.setEnabled(true);
                txtNgayLap.setEnabled(false);
            }
        });

        chkNgayLap.addActionListener(e -> {
            if (chkNgayLap.isSelected()) {
                chkMaHoaDon.setSelected(false);
                txtNgayLap.setEnabled(true);
                txtMaHoaDon.setEnabled(false);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyHoaDon_GUI().setVisible(true));
    }
}