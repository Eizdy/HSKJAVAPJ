package gui;

import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhap_GUI extends JFrame {

    private JLabel lblTitle, lblUsername, lblPassword, lblImage;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel errUsername, errPassword;
    private JButton btnLogin, btnRegister, btnExit;
    private JPanel pnlLeft, pnlRight;

    public DangNhap_GUI() {
        setTitle("Đăng Nhập Rạp Chiếu Phim");
        setSize(650, 450);
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

        // Panel bên phải (form đăng nhập)
        pnlRight = new JPanel(null);
        pnlRight.setBackground(new Color(45, 45, 45));
        add(pnlRight, BorderLayout.CENTER);

        // Tiêu đề
        lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setBounds(0, 30, 400, 40);
        pnlRight.add(lblTitle);

        // Nhãn và ô nhập tên tài khoản
        lblUsername = new JLabel("Tên tài khoản:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUsername.setBounds(30, 100, 120, 30);
        pnlRight.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBackground(new Color(60, 60, 60));
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setCaretColor(Color.WHITE);
        txtUsername.setBounds(150, 100, 200, 30);
        pnlRight.add(txtUsername);

        // Error label cho tên tài khoản
        errUsername = new JLabel();
        errUsername.setForeground(Color.RED);
        errUsername.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        errUsername.setBounds(150, 135, 200, 20);
        pnlRight.add(errUsername);

        // Nhãn và ô nhập mật khẩu
        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPassword.setBounds(30, 170, 120, 30);
        pnlRight.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBackground(new Color(60, 60, 60));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBounds(150, 170, 200, 30);
        pnlRight.add(txtPassword);

        // Error label cho mật khẩu
        errPassword = new JLabel();
        errPassword.setForeground(Color.RED);
        errPassword.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        errPassword.setBounds(150, 205, 200, 20);
        pnlRight.add(errPassword);

        // Nút Đăng nhập
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(30, 250, 110, 35);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validData()) {
                    String tenTK = txtUsername.getText().trim();
                    String mk = new String(txtPassword.getPassword());

                    TaiKhoan_DAO dao = new TaiKhoan_DAO();
                    try {
                        TaiKhoan tk = dao.dangNhap(tenTK, mk);
                        if (tk != null) {
                            JOptionPane.showMessageDialog(DangNhap_GUI.this, "Đăng nhập thành công!");
                            dispose();
                            new TrangChuRapChieuPhim_GUI().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(DangNhap_GUI.this, 
                                "Tên tài khoản hoặc mật khẩu không đúng!", 
                                "Lỗi", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(DangNhap_GUI.this, 
                            ex.getMessage(), 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DangNhap_GUI.this, 
                            "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        pnlRight.add(btnLogin);

        // Nút Đăng ký
        btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setBackground(new Color(0, 120, 215));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBounds(145, 250, 100, 35);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new DangKyTaiKhoan_GUI().setVisible(true));
            }
        });
        pnlRight.add(btnRegister);

        // Nút Thoát
        btnExit = new JButton("Thoát");
        btnExit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExit.setBackground(new Color(200, 50, 50));
        btnExit.setForeground(Color.WHITE);
        btnExit.setBounds(255, 250, 100, 35);
        btnExit.addActionListener(e -> System.exit(0));
        pnlRight.add(btnExit);

        setVisible(true);
    }

    private boolean validData() {
        boolean isValid = true;
        errUsername.setText("");
        errPassword.setText("");

        String username = txtUsername.getText().trim();
        if (username.isEmpty()) {
            errUsername.setText("Tên tài khoản không được trống.");
            txtUsername.requestFocus();
            isValid = false;
        }

        String password = new String(txtPassword.getPassword());
        if (password.length() < 8) {
            errPassword.setText("Mật khẩu phải từ 8 ký tự.");
            if (isValid) txtPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangNhap_GUI());
    }
}