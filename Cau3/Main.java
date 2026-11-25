import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class DuLieuKhongHopLeException extends Exception {
    DuLieuKhongHopLeException(String thongBao) {
        super(thongBao);
    }
}

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

class QuanLyHocPhi {
    private final List<SinhVien> danhSach = new ArrayList<>();
    private final List<String> maSinhVienKhongHopLe = new ArrayList<>();
    private final Set<String> maDaNhapHopLe = new HashSet<>();

    void them(String dong) {
        String[] phan = dong.trim().split("\\s+");
        String ma = phan.length > 1 ? phan[1] : "";

        try {
            if (phan.length != 3) {
                throw new DuLieuKhongHopLeException("Dong phai co du ba truong");
            }

            String maHe = phan[0];
            if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT")) {
                throw new DuLieuKhongHopLeException("Ma he khong hop le");
            }

            int soTinChi;
            try {
                soTinChi = Integer.parseInt(phan[2]);
            } catch (NumberFormatException e) {
                throw new DuLieuKhongHopLeException("So tin chi khong phai so nguyen");
            }
            if (soTinChi < 1 || soTinChi > 30) {
                throw new DuLieuKhongHopLeException("So tin chi ngoai gioi han");
            }
            if (maDaNhapHopLe.contains(ma)) {
                throw new DuLieuKhongHopLeException("Ma sinh vien bi trung");
            }

            danhSach.add(taoSinhVien(maHe, ma, soTinChi));
            maDaNhapHopLe.add(ma);
        } catch (DuLieuKhongHopLeException e) {
            maSinhVienKhongHopLe.add(ma);
        }
    }

    private SinhVien taoSinhVien(String maHe, String ma, int soTinChi) {
        switch (maHe) {
            case "CQ":
                return new SinhVienChinhQuy(ma, soTinChi);
            case "CLC":
                return new SinhVienChatLuongCao(ma, soTinChi);
            default:
                return new SinhVienLienThong(ma, soTinChi);
        }
    }

    void inKetQua() {
        for (String ma : maSinhVienKhongHopLe) {
            System.out.println("INVALID " + ma);
        }

        danhSach.sort(Comparator.comparingLong(SinhVien::hocPhi).reversed()
                .thenComparing(sinhVien -> sinhVien.ma));

        long tongHocPhi = 0;
        for (SinhVien sinhVien : danhSach) {
            long hocPhi = sinhVien.hocPhi();
            tongHocPhi += hocPhi;
            System.out.println(sinhVien.ma + " " + sinhVien.maHe() + " " + hocPhi);
        }
        System.out.println("TONG " + tongHocPhi);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());
        QuanLyHocPhi quanLy = new QuanLyHocPhi();

        for (int i = 0; i < n; i++) {
            quanLy.them(scanner.nextLine());
        }
        quanLy.inKetQua();
    }
}
