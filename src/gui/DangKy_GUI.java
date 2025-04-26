package gui;

import javax.swing.*;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;

public class DangKy_GUI extends JFrame {
    private JLabel lblTitle, lblMaNV, lblTenNV, lblNgaySinh, lblDienThoai, lblEmail, lblChucVu, lblTenTK, lblMatKhau;
    private JTextField txtMaNV, txtTenNV, txtNgaySinh, txtDienThoai, txtEmail, txtTenTK;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboChucVu;
    private JLabel errMaNV, errTenNV, errNgaySinh, errDienThoai, errEmail, errTenTK, errMatKhau;
    private JButton btnDangKy, btnDangNhap, btnXoaTrang;
    private JPanel pnlLeft, pnlRight;
    private JLabel lblImage;

    public DangKy_GUI() {
        setTitle("Đăng Ký Nhân Viên");
        setSize(1050, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Left panel (for image)
        pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(300, 550));
        pnlLeft.setBackground(new Color(255, 245, 157));
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/login2.jpg"));
            Image scaledImage = imageIcon.getImage().getScaledInstance(300, 550, Image.SCALE_SMOOTH);
            lblImage = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            lblImage = new JLabel("Không tải được ảnh", SwingConstants.CENTER);
            lblImage.setFont(new Font("Arial", Font.PLAIN, 16));
        }
        lblImage.setPreferredSize(new Dimension(300, 550));
        pnlLeft.add(lblImage);
        add(pnlLeft, BorderLayout.WEST);

        // Right panel (for form)
        pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(Color.PINK);
        add(pnlRight, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font lblFont = new Font("Arial", Font.PLAIN, 14);
        Font btnFont = new Font("Arial", Font.PLAIN, 12);

        int row = 0;

        // Title
        lblTitle = new JLabel("ĐĂNG KÝ NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        pnlRight.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0.0;

        // Row 1: Mã nhân viên
        gbc.gridy = row;
        gbc.gridx = 0;
        lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setFont(lblFont);
        lblMaNV.setPreferredSize(new Dimension(120, 25));
        pnlRight.add(lblMaNV, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtMaNV = new JTextField();
        txtMaNV.setPreferredSize(new Dimension(400, 25));
        pnlRight.add(txtMaNV, gbc);

        gbc.gridy = row + 1;
        gbc.gridx = 1;
        errMaNV = new JLabel();
        errMaNV.setForeground(Color.RED);
        errMaNV.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errMaNV, gbc);

        row += 2;

        // Row 2: Tên nhân viên
        gbc.gridy = row;
        gbc.gridx = 0;
        lblTenNV = new JLabel("Tên nhân viên:");
        lblTenNV.setFont(lblFont);
        lblTenNV.setPreferredSize(new Dimension(120, 25));
        pnlRight.add(lblTenNV, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtTenNV = new JTextField();
        txtTenNV.setPreferredSize(new Dimension(400, 25));
        pnlRight.add(txtTenNV, gbc);

        gbc.gridy = row + 1;
        gbc.gridx = 1;
        errTenNV = new JLabel();
        errTenNV.setForeground(Color.RED);
        errTenNV.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errTenNV, gbc);

        row += 2;

        // Row 3: Ngày sinh – Số điện thoại
        gbc.gridy = row;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(lblFont);
        lblNgaySinh.setPreferredSize(new Dimension(120, 25));
        pnlRight.add(lblNgaySinh, gbc);

        gbc.gridx = 1;
        txtNgaySinh = new JTextField();
        txtNgaySinh.setPreferredSize(new Dimension(180, 25));
        pnlRight.add(txtNgaySinh, gbc);

        gbc.gridx = 2;
        lblDienThoai = new JLabel("Số điện thoại:");
        lblDienThoai.setFont(lblFont);
        lblDienThoai.setPreferredSize(new Dimension(110, 25));
        pnlRight.add(lblDienThoai, gbc);

        gbc.gridx = 3;
        txtDienThoai = new JTextField();
        txtDienThoai.setPreferredSize(new Dimension(180, 25));
        pnlRight.add(txtDienThoai, gbc);

        // Error labels
        gbc.gridy = row + 1;
        gbc.gridx = 1;
        errNgaySinh = new JLabel();
        errNgaySinh.setForeground(Color.RED);
        errNgaySinh.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errNgaySinh, gbc);

        gbc.gridx = 3;
        errDienThoai = new JLabel();
        errDienThoai.setForeground(Color.RED);
        errDienThoai.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errDienThoai, gbc);

        row += 2;

        // Row 4: Email – Chức vụ
        gbc.gridy = row;
        gbc.gridx = 0;
        lblEmail = new JLabel("Email:");
        lblEmail.setFont(lblFont);
        lblEmail.setPreferredSize(new Dimension(120, 25));
        pnlRight.add(lblEmail, gbc);

        gbc.gridx = 1;
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(180, 25));
        pnlRight.add(txtEmail, gbc);

        gbc.gridx = 2;
        lblChucVu = new JLabel("Chức vụ:");
        lblChucVu.setFont(lblFont);
        lblChucVu.setPreferredSize(new Dimension(110, 25));
        pnlRight.add(lblChucVu, gbc);

        gbc.gridx = 3;
        cboChucVu = new JComboBox<>(new String[]{"Nhân viên bán vé", "Quản lý", "Kế toán", "Thu ngân"});
        cboChucVu.setPreferredSize(new Dimension(180, 25));
        cboChucVu.setSelectedItem("Nhân viên bán vé");
        pnlRight.add(cboChucVu, gbc);

        gbc.gridy = row + 1;
        gbc.gridx = 1;
        errEmail = new JLabel();
        errEmail.setForeground(Color.RED);
        errEmail.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errEmail, gbc);

        row += 2;

        // Row 5: Tên tài khoản – Mật khẩu
        gbc.gridy = row;
        gbc.gridx = 0;
        lblTenTK = new JLabel("Tên tài khoản:");
        lblTenTK.setFont(lblFont);
        lblTenTK.setPreferredSize(new Dimension(120, 25));
        pnlRight.add(lblTenTK, gbc);

        gbc.gridx = 1;
        txtTenTK = new JTextField();
        txtTenTK.setPreferredSize(new Dimension(180, 25));
        pnlRight.add(txtTenTK, gbc);

        gbc.gridx = 2;
        lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(lblFont);
        lblMatKhau.setPreferredSize(new Dimension(110, 25));
        pnlRight.add(lblMatKhau, gbc);

        gbc.gridx = 3;
        txtMatKhau = new JPasswordField();
        txtMatKhau.setPreferredSize(new Dimension(180, 25));
        pnlRight.add(txtMatKhau, gbc);

        gbc.gridy = row + 1;
        gbc.gridx = 1;
        errTenTK = new JLabel();
        errTenTK.setForeground(Color.RED);
        errTenTK.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errTenTK, gbc);

        gbc.gridx = 3;
        errMatKhau = new JLabel();
        errMatKhau.setForeground(Color.RED);
        errMatKhau.setFont(new Font("Arial", Font.ITALIC, 11));
        pnlRight.add(errMatKhau, gbc);

        row += 2;

        // Row 6: Buttons dùng BoxLayout theo trục X_AXIS
        gbc.gridy = row;
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        Dimension btnSize = new Dimension(80, 25);

        btnDangKy = new JButton("Đăng ký");
        btnDangKy.setFont(btnFont);
        btnDangKy.setPreferredSize(btnSize);

        btnXoaTrang = new JButton("Xóa trắng");
        btnXoaTrang.setFont(btnFont);
        btnXoaTrang.setPreferredSize(btnSize);

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setFont(btnFont);
        btnDangNhap.setPreferredSize(btnSize);

        buttonPanel.add(btnDangKy);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnXoaTrang);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnDangNhap);

        pnlRight.add(buttonPanel, gbc);

        // Action listeners
        btnDangKy.addActionListener(e -> {
            if (validData()) {
                String maNV = txtMaNV.getText().trim();
                String tenNV = txtTenNV.getText().trim();
                LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String dienThoai = txtDienThoai.getText().trim();
                String email = txtEmail.getText().trim();
                String chucVu = cboChucVu.getSelectedItem().toString();
                String tenTK = txtTenTK.getText().trim();
                String matKhau = new String(txtMatKhau.getPassword());

                TaiKhoan tk = new TaiKhoan(tenTK, matKhau);
                NhanVien nv = new NhanVien(maNV, tenNV, dienThoai, email, tk, chucVu, ngaySinh);

                boolean tkOK = new TaiKhoan_DAO().themTaiKhoan(tk);
                boolean nvOK = new NhanVien_DAO().themNhanVien(nv);

                if (tkOK && nvOK) {
                    JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                    dispose();
                    new DangNhap_GUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Vui lòng thử lại!");
                }
            }
        });
        btnDangNhap.addActionListener(e -> {
            // Close the current DangKy_GUI frame
            dispose();
            // Open the DangNhap_GUI frame
            SwingUtilities.invokeLater(() -> {
                DangNhap_GUI loginFrame = new DangNhap_GUI();
                loginFrame.setVisible(true);
            });
        });

        btnXoaTrang.addActionListener(e -> {
            // Clear all text fields
            txtMaNV.setText("");
            txtTenNV.setText("");
            txtNgaySinh.setText("");
            txtDienThoai.setText("");
            txtEmail.setText("");
            txtTenTK.setText("");
            txtMatKhau.setText("");
            // Reset combo box to "Nhân viên bán vé"
            cboChucVu.setSelectedItem("Nhân viên bán vé");
            // Clear all error messages
            errMaNV.setText("");
            errTenNV.setText("");
            errNgaySinh.setText("");
            errDienThoai.setText("");
            errEmail.setText("");
            errTenTK.setText("");
            errMatKhau.setText("");
            // Request focus on Tên nhân viên
            txtTenNV.requestFocus();
        });
    }

    private boolean validData() {
        boolean isValid = true;

        // Clear previous errors
        errMaNV.setText("");
        errTenNV.setText("");
        errNgaySinh.setText("");
        errDienThoai.setText("");
        errEmail.setText("");
        errTenTK.setText("");
        errMatKhau.setText("");

        // Mã nhân viên: bắt đầu bằng "NV" + 3 chữ số
        String maNV = txtMaNV.getText().trim();
        if (!maNV.matches("^NV\\d{3}$")) {
            errMaNV.setText("Mã phải bắt đầu bằng 'NV' và theo sau là 3 chữ số.");
            txtMaNV.requestFocus();
            isValid = false;
        }

        // Tên nhân viên: viết hoa chữ đầu, cho phép tiếng Việt
        String tenNV = txtTenNV.getText().trim();
        if (!tenNV.matches("^([A-ZÀ-Ỹ][a-zà-ỹ]*(\\s)?)+$")) {
            errTenNV.setText("Tên phải viết hoa chữ cái đầu mỗi từ và không chứa ký tự lạ.");
            if (isValid) txtTenNV.requestFocus();
            isValid = false;
        }

        // Ngày sinh: định dạng dd/MM/yyyy
        String ngaySinh = txtNgaySinh.getText().trim();
        if (!ngaySinh.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            errNgaySinh.setText("Ngày sinh phải có định dạng dd/MM/yyyy.");
            if (isValid) txtNgaySinh.requestFocus();
            isValid = false;
        } else {
            try {
                new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinh);
            } catch (Exception e) {
                errNgaySinh.setText("Ngày sinh không hợp lệ.");
                if (isValid) txtNgaySinh.requestFocus();
                isValid = false;
            }
        }

        // Số điện thoại: 10–12 chữ số
        String dienThoai = txtDienThoai.getText().trim();
        if (!dienThoai.matches("^\\d{10,12}$")) {
            errDienThoai.setText("Số điện thoại phải từ 10 đến 12 chữ số.");
            if (isValid) txtDienThoai.requestFocus();
            isValid = false;
        }

        // Email: định dạng chuẩn
        String email = txtEmail.getText().trim();
        if (!email.matches("^[\\w-.]+@[\\w-]+\\.[\\w-.]+$")) {
            errEmail.setText("Email không đúng định dạng.");
            if (isValid) txtEmail.requestFocus();
            isValid = false;
        }

        // Tên tài khoản: không chứa khoảng trắng
        String tenTK = txtTenTK.getText().trim();
        if (tenTK.isEmpty() || tenTK.contains(" ")) {
            errTenTK.setText("Tên tài khoản không được chứa khoảng trắng.");
            if (isValid) txtTenTK.requestFocus();
            isValid = false;
        }

        // Mật khẩu: ít nhất 8 ký tự
        String matKhau = new String(txtMatKhau.getPassword());
        if (matKhau.length() < 8) {
            errMatKhau.setText("Mật khẩu phải từ 8 ký tự trở lên.");
            if (isValid) txtMatKhau.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangKy_GUI().setVisible(true));
    }
}