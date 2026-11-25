import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class SinhVien {
    private final String ma;
    private final String hoTen;
    private final String ngaySinh;

    SinhVien(String ma, String hoTen, String ngaySinh) {
        this.ma = ma.trim();
        this.hoTen = chuanHoaHoTen(hoTen);
        this.ngaySinh = chuanHoaNgaySinh(ngaySinh);
    }

    private static String chuanHoaHoTen(String hoTen) {
        String[] tu = hoTen.trim().toLowerCase(Locale.forLanguageTag("vi-VN")).split("\\s+");
        StringBuilder ketQua = new StringBuilder();
        for (String phan : tu) {
            if (phan.isEmpty()) {
                continue;
            }
            if (ketQua.length() > 0) {
                ketQua.append(' ');
            }
            ketQua.append(phan.substring(0, 1).toUpperCase(Locale.forLanguageTag("vi-VN")));
            ketQua.append(phan.substring(1));
        }
        return ketQua.toString();
    }

    private static String chuanHoaNgaySinh(String ngaySinh) {
        String[] phan = ngaySinh.trim().split("/");
        int ngay = Integer.parseInt(phan[0]);
        int thang = Integer.parseInt(phan[1]);
        int nam = Integer.parseInt(phan[2]);
        return String.format(Locale.ROOT, "%02d/%02d/%04d", ngay, thang, nam);
    }

    @Override
    public String toString() {
        return ma + " " + hoTen + " " + ngaySinh;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());
        List<SinhVien> danhSach = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String ma = scanner.nextLine();
            String hoTen = scanner.nextLine();
            String ngaySinh = scanner.nextLine();
            danhSach.add(new SinhVien(ma, hoTen, ngaySinh));
        }

        for (SinhVien sinhVien : danhSach) {
            System.out.println(sinhVien);
        }
    }
}
