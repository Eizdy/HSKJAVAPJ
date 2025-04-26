package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.regex.Pattern;

public class QuanLyNhanVien_GUI extends JFrame {
    private JTable tblNhanVien;
    private DefaultTableModel modelNhanVien;
    private JTextField txtMaNV, txtHoTen, txtSoDienThoai, txtEmail;
    private JCheckBox chkMaNV, chkHoTen;
    private JButton btnTimKiem, btnXoaTrang, btnThem, btnXoa, btnSua, btnHienTatCa;

    public QuanLyNhanVien_GUI() {
        setTitle("Nhân viên - Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JLabel lbl = new JLabel("HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM", SwingConstants.CENTER);
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

        String[] items = {"Trang chủ", "Phim", "Suất chiếu","Nhân viên","Hoá đơn", "Bán vé", "Đăng xuất"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(40, 40, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

            if (item.equals("Đăng xuất")) btn.setForeground(Color.RED);
            menu.add(Box.createVerticalStrut(10));
            menu.add(btn);
        }
        return menu;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(45, 45, 45));

        String[] cols = {"Mã nhân viên", "Họ tên", "Số điện thoại", "Email", "Ngày sinh", "Chức vụ", "Tài khoản"};
        modelNhanVien = new DefaultTableModel(cols, 0);
        tblNhanVien = new JTable(modelNhanVien);
        tblNhanVien.setBackground(new Color(60, 60, 60));
        tblNhanVien.setForeground(Color.WHITE);
        tblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNhanVien.setRowHeight(22);
        JScrollPane scroll = new JScrollPane(tblNhanVien);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Danh sách nhân viên"));
        main.add(scroll, BorderLayout.CENTER);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setBackground(new Color(45, 45, 45));
        east.setPreferredSize(new Dimension(250, 0));

        JLabel lblTieuDe = new JLabel("TRA CỨU", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTieuDe.setForeground(Color.WHITE);

        chkMaNV = new JCheckBox("Tra cứu theo mã nhân viên");
        chkMaNV.setBackground(new Color(45, 45, 45));
        chkMaNV.setForeground(Color.WHITE);
        chkMaNV.setSelected(true);

        txtMaNV = new JTextField();
        txtMaNV.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtMaNV.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        txtMaNV.setBackground(new Color(60, 60, 60));
        txtMaNV.setForeground(Color.WHITE);
        txtMaNV.setEnabled(true); // ban đầu được bật

        chkHoTen = new JCheckBox("Tra cứu theo họ tên");
        chkHoTen.setBackground(new Color(45, 45, 45));
        chkHoTen.setForeground(Color.WHITE);
        chkHoTen.setSelected(false);

        txtHoTen = new JTextField();
        txtHoTen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtHoTen.setBorder(BorderFactory.createTitledBorder("Họ tên"));
        txtHoTen.setBackground(new Color(60, 60, 60));
        txtHoTen.setForeground(Color.WHITE);
        txtHoTen.setEnabled(false); // ban đầu bị tắt

        // Sự kiện chọn checkbox
        chkMaNV.addActionListener(e -> {
            if (chkMaNV.isSelected()) {
                chkHoTen.setSelected(false);
                txtMaNV.setEnabled(true);
                txtHoTen.setEnabled(false);
            } else {
                txtMaNV.setEnabled(false);
            }
        });

        chkHoTen.addActionListener(e -> {
            if (chkHoTen.isSelected()) {
                chkMaNV.setSelected(false);
                txtHoTen.setEnabled(true);
                txtMaNV.setEnabled(false);
            } else {
                txtHoTen.setEnabled(false);
            }
        });

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBackground(new Color(45, 45, 45));
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setBackground(new Color(0, 120, 215));
        btnTimKiem.setForeground(Color.WHITE);
        btnXoaTrang = new JButton("Xoá trắng");
        btnXoaTrang.setBackground(new Color(200, 50, 50));
        btnXoaTrang.setForeground(Color.WHITE);
        actionPanel.add(btnTimKiem);
        actionPanel.add(btnXoaTrang);
        
        btnXoaTrang.addActionListener(e -> {
            txtMaNV.setText("");
            txtHoTen.setText("");
        });

        east.add(lblTieuDe);
        east.add(Box.createVerticalStrut(10));
        east.add(chkMaNV);
        east.add(txtMaNV);
        east.add(Box.createVerticalStrut(10));
        east.add(chkHoTen);
        east.add(txtHoTen);
        east.add(Box.createVerticalStrut(15));
        east.add(actionPanel);

        main.add(east, BorderLayout.EAST);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        south.setBackground(new Color(45, 45, 45));
        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(0, 120, 215));
        btnThem.setForeground(Color.WHITE);
        btnThem.addActionListener(e -> showAddEmployeePopup());

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(new Color(200, 50, 50));
        btnXoa.setForeground(Color.WHITE);

        btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(0, 120, 215));
        btnSua.setForeground(Color.WHITE);

        btnHienTatCa = new JButton("Hiện tất cả");
        btnHienTatCa.setBackground(new Color(0, 120, 215));
        btnHienTatCa.setForeground(Color.WHITE);

        south.add(btnThem);
        south.add(btnXoa);
        south.add(btnSua);
        south.add(btnHienTatCa);
        main.add(south, BorderLayout.SOUTH);
        

        return main;
    }



    private void showAddEmployeePopup() {
        JDialog dialog = new JDialog(this, "Thêm Nhân Viên Mới", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("THÊM NHÂN VIÊN MỚI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(30, 30, 30));
        lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        dialog.add(lblTitle, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        textPanel.setBackground(new Color(45, 45, 45));
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField txtMaNV = createTextField("Mã nhân viên");
        JTextField txtHoTen = createTextField("Họ tên");
        JTextField txtSoDienThoai = createTextField("Số điện thoại");
        JTextField txtEmail = createTextField("Email");
        JTextField txtNgaySinh = createTextField("Ngày sinh");
        JTextField txtChucVu = createTextField("Chức vụ");
        JTextField txtTaiKhoan = createTextField("Tài khoản");

        textPanel.add(txtMaNV);
        textPanel.add(txtHoTen);
        textPanel.add(txtSoDienThoai);
        textPanel.add(txtEmail);
        textPanel.add(txtNgaySinh);
        textPanel.add(txtChucVu);
        textPanel.add(txtTaiKhoan);

        JScrollPane scrollPane = new JScrollPane(textPanel);
        scrollPane.setBackground(new Color(45, 45, 45));
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(0, 120, 215));
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            String maNV = txtMaNV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();
            String email = txtEmail.getText().trim();
            String ngaySinh = txtNgaySinh.getText().trim();
            String chucVu = txtChucVu.getText().trim();
            String taiKhoan = txtTaiKhoan.getText().trim();

            if (maNV.isEmpty() || hoTen.isEmpty() || soDienThoai.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ các trường bắt buộc!");
                return;
            }

            if (!isValidMaNV(maNV)) {
                JOptionPane.showMessageDialog(dialog, "Mã nhân viên phải có định dạng NV + số (tối thiểu 3 chữ số sau NV), ví dụ: NV001.");
                return;
            }

            if (!isValidHoTen(hoTen)) {
                JOptionPane.showMessageDialog(dialog, "Họ tên chỉ được chứa chữ cái và khoảng trắng.");
                return;
            }

            if (!isValidSoDienThoai(soDienThoai)) {
                JOptionPane.showMessageDialog(dialog, "Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số.");
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(dialog, "Định dạng email không hợp lệ!");
                return;
            }

            modelNhanVien.addRow(new Object[]{
                maNV, hoTen, soDienThoai, email, ngaySinh, chucVu, taiKhoan
            });
            JOptionPane.showMessageDialog(dialog, "Đã thêm nhân viên: " + hoTen);
            dialog.dispose();
        });

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(200, 50, 50));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JTextField createTextField(String title) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createTitledBorder(title));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        return field;
    }

    private boolean isValidMaNV(String maNV) {
        return maNV.matches("^NV\\d{3,}$");
    }

    private boolean isValidHoTen(String hoTen) {
        return hoTen.matches("^[\\p{L} ]+$");
    }

    private boolean isValidSoDienThoai(String sdt) {
        return sdt.matches("^0\\d{9}$");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private JPanel createFooter() {
        JLabel lbl = new JLabel("Nhóm Quản lý Rạp Chiếu Phim", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setBackground(new Color(30, 30, 30));
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel footer = new JPanel(new BorderLayout());
        footer.add(lbl);
        return footer;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QuanLyNhanVien_GUI().setVisible(true));
    }
} 