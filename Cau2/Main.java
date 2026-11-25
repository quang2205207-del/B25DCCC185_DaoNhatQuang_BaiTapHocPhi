import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class SinhVien {
    protected final String ma;
    protected final int soTinChi;

    SinhVien(String ma, int soTinChi) {
        this.ma = ma;
        this.soTinChi = soTinChi;
    }

    abstract long hocPhi();

    abstract String maHe();
}

class SinhVienChinhQuy extends SinhVien {
    SinhVienChinhQuy(String ma, int soTinChi) {
        super(ma, soTinChi);
    }

    @Override
    long hocPhi() {
        return (long) soTinChi * 450_000;
    }

    @Override
    String maHe() {
        return "CQ";
    }
}

class SinhVienChatLuongCao extends SinhVien {
    SinhVienChatLuongCao(String ma, int soTinChi) {
        super(ma, soTinChi);
    }

    @Override
    long hocPhi() {
        return (long) soTinChi * 675_000;
    }

    @Override
    String maHe() {
        return "CLC";
    }
}

class SinhVienLienThong extends SinhVien {
    SinhVienLienThong(String ma, int soTinChi) {
        super(ma, soTinChi);
    }

    @Override
    long hocPhi() {
        return (long) soTinChi * 450_000 + 2_000_000;
    }

    @Override
    String maHe() {
        return "LT";
    }
}

public class Main {
    private static SinhVien taoSinhVien(String maHe, String ma, int soTinChi) {
        switch (maHe) {
            case "CQ":
                return new SinhVienChinhQuy(ma, soTinChi);
            case "CLC":
                return new SinhVienChatLuongCao(ma, soTinChi);
            case "LT":
                return new SinhVienLienThong(ma, soTinChi);
            default:
                throw new IllegalArgumentException("Ma he khong hop le");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<SinhVien> danhSach = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String maHe = scanner.next();
            String ma = scanner.next();
            int soTinChi = scanner.nextInt();
            danhSach.add(taoSinhVien(maHe, ma, soTinChi));
        }

        for (SinhVien sinhVien : danhSach) {
            System.out.println(sinhVien.ma + " " + sinhVien.maHe() + " " + sinhVien.hocPhi());
        }
    }
}
