package gui;

import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class DangKyTaiKhoan_GUI extends JFrame {

    private JLabel lblTitle, lblUsername, lblPassword, lblConfirmPassword, lblImage;
    private JTextField txtUsername;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JLabel errUsername, errPassword, errConfirmPassword;
    private JButton btnRegister, btnLogin, btnExit;
    private JPanel pnlLeft, pnlRight;

    public DangKyTaiKhoan_GUI() {
        setTitle("Đăng Ký Tài Khoản - Rạp Chiếu Phim");
        setSize(670, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Apply consistent UI settings
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TitledBorder.titleColor", Color.LIGHT_GRAY);

        // Panel bên trái (hiển thị ảnh)
        pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(250, 450));
        pnlLeft.setBackground(new Color(45, 45, 45));

        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/login2.jpg"));
            Image scaledImage = imageIcon.getImage().getScaledInstance(250, 450, Image.SCALE_SMOOTH);
            lblImage = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            lblImage = new JLabel("Không tải được ảnh", SwingConstants.CENTER);
            lblImage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblImage.setForeground(Color.WHITE);
        }

        lblImage.setPreferredSize(new Dimension(250, 450));
        pnlLeft.add(lblImage);
        add(pnlLeft, BorderLayout.WEST);

        // Panel bên phải (form đăng ký)
        pnlRight = new JPanel(null);
        pnlRight.setBackground(new Color(45, 45, 45));
        add(pnlRight, BorderLayout.CENTER);

        // Tiêu đề
        lblTitle = new JLabel("ĐĂNG KÝ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setBounds(0, 20, 400, 40);
        lblTitle.setForeground(Color.WHITE);
        pnlRight.add(lblTitle);

        // Nhãn và ô nhập tên tài khoản
        lblUsername = new JLabel("Tên tài khoản:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUsername.setBounds(30, 80, 180, 30);
        pnlRight.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBackground(new Color(60, 60, 60));
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setCaretColor(Color.WHITE);
        txtUsername.setBounds(210, 80, 160, 30);
        pnlRight.add(txtUsername);

        // Error label cho tên tài khoản
        errUsername = new JLabel();
        errUsername.setForeground(Color.RED);
        errUsername.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        errUsername.setBounds(210, 115, 200, 20);
        pnlRight.add(errUsername);

        // Nhãn và ô nhập mật khẩu
        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPassword.setBounds(30, 150, 180, 30);
        pnlRight.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBackground(new Color(60, 60, 60));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBounds(210, 150, 160, 30);
        pnlRight.add(txtPassword);

        // Error label cho mật khẩu
        errPassword = new JLabel();
        errPassword.setForeground(Color.RED);
        errPassword.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        errPassword.setBounds(210, 185, 200, 20);
        pnlRight.add(errPassword);

        // Nhãn và ô nhập xác nhận mật khẩu
        lblConfirmPassword = new JLabel("Xác nhận mật khẩu:");
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblConfirmPassword.setBounds(30, 220, 180, 30);
        pnlRight.add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtConfirmPassword.setBackground(new Color(60, 60, 60));
        txtConfirmPassword.setForeground(Color.WHITE);
        txtConfirmPassword.setCaretColor(Color.WHITE);
        txtConfirmPassword.setBounds(210, 220, 160, 30);
        pnlRight.add(txtConfirmPassword);

        // Error label cho xác nhận mật khẩu
        errConfirmPassword = new JLabel();
        errConfirmPassword.setForeground(Color.RED);
        errConfirmPassword.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        errConfirmPassword.setBounds(210, 255, 200, 20);
        pnlRight.add(errConfirmPassword);

        // Nút Đăng ký
        btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setBackground(new Color(0, 120, 215));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBounds(30, 300, 110, 40);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validData()) {
                    String tenTK = txtUsername.getText().trim();
                    String mk = new String(txtPassword.getPassword());

                    TaiKhoan tk = new TaiKhoan(tenTK, mk);
                    TaiKhoan_DAO dao = new TaiKhoan_DAO();
                    try {
                        if (dao.themTaiKhoan(tk)) {
                            JOptionPane optionPane = new JOptionPane("Đăng ký tài khoản thành công!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            JDialog dialog = optionPane.createDialog("Thông báo");
                            dialog.setPreferredSize(new Dimension(300, 150));
                            dialog.pack();
                            dialog.setVisible(true);
                            dispose();
                            new DangNhap_GUI().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(DangKyTaiKhoan_GUI.this,
                                    "Đăng ký tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(DangKyTaiKhoan_GUI.this,
                                ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DangKyTaiKhoan_GUI.this,
                                "Lỗi kết nối CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        pnlRight.add(btnRegister);

        // Nút Đăng nhập
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(150, 300, 110, 40);
        btnLogin.addActionListener(e -> {
            dispose();
            new DangNhap_GUI().setVisible(true);
        });
        pnlRight.add(btnLogin);

        // Nút Thoát
        btnExit = new JButton("Thoát");
        btnExit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExit.setBackground(new Color(200, 50, 50));
        btnExit.setForeground(Color.WHITE);
        btnExit.setBounds(270, 300, 110, 40);
        btnExit.addActionListener(e -> System.exit(0));
        pnlRight.add(btnExit);

        setVisible(true);
    }

    private boolean validData() {
        boolean isValid = true;
        errUsername.setText("");
        errPassword.setText("");
        errConfirmPassword.setText("");

        // Validate username
        String username = txtUsername.getText().trim();
        if (username.isEmpty()) {
            errUsername.setText("Tên tài khoản không được trống.");
            txtUsername.requestFocus();
            isValid = false;
        } else if (!Pattern.matches("^\\S+$", username)) {
            errUsername.setText("Tên tài khoản không được chứa dấu cách.");
            txtUsername.requestFocus();
            isValid = false;
        } else {
            // Check duplicate username
            TaiKhoan_DAO dao = new TaiKhoan_DAO();
            try {
                TaiKhoan existingAccount = dao.timTaiKhoanTheoTen(username);
                if (existingAccount != null) {
                    errUsername.setText("Tên tài khoản đã tồn tại.");
                    txtUsername.requestFocus();
                    isValid = false;
                }
            } catch (Exception e) {
                errUsername.setText("Lỗi kiểm tra tài khoản: " + e.getMessage());
                txtUsername.requestFocus();
                isValid = false;
            }
        }

        // Validate password
        String password = new String(txtPassword.getPassword());
        if (password.isEmpty()) {
            errPassword.setText("Mật khẩu không được trống.");
            if (isValid) txtPassword.requestFocus();
            isValid = false;
        } else if (!Pattern.matches("^\\S+$", password)) {
            errPassword.setText("Mật khẩu không được chứa dấu cách.");
            if (isValid) txtPassword.requestFocus();
            isValid = false;
        } else if (password.length() < 8) {
            errPassword.setText("Mật khẩu phải từ 8 ký tự trở lên.");
            if (isValid) txtPassword.requestFocus();
            isValid = false;
        }

        // Validate confirm password
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        if (!confirmPassword.equals(password)) {
            errConfirmPassword.setText("Mật khẩu xác nhận không khớp.");
            if (isValid) txtConfirmPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangKyTaiKhoan_GUI());
    }
}
