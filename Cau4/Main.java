import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class MienGiamConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

class MienGiamHoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

class MienGiamHocBong implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, 3_000_000);
    }
}

abstract class SinhVien {
    protected final String ma;
    protected final int soTinChi;
    private MienGiam mienGiam;

    SinhVien(String ma, int soTinChi) {
        this.ma = ma;
        this.soTinChi = soTinChi;
    }

    abstract long hocPhi();

    abstract String maHe();

    void setMienGiam(MienGiam mienGiam) {
        this.mienGiam = mienGiam;
    }

    long soTienGiam() {
        return mienGiam == null ? 0 : mienGiam.tinhGiam(hocPhi());
    }

    long phaiNop() {
        return hocPhi() - soTienGiam();
    }
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

    private static MienGiam taoMienGiam(String maChinhSach) {
        switch (maChinhSach) {
            case "CS":
                return new MienGiamConChinhSach();
            case "HN":
                return new MienGiamHoNgheo();
            case "HB":
                return new MienGiamHocBong();
            default:
                throw new IllegalArgumentException("Ma chinh sach khong hop le");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<SinhVien> danhSach = new ArrayList<>();
        Map<String, SinhVien> theoMa = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String maHe = scanner.next();
            String ma = scanner.next();
            int soTinChi = scanner.nextInt();
            SinhVien sinhVien = taoSinhVien(maHe, ma, soTinChi);
            danhSach.add(sinhVien);
            theoMa.put(ma, sinhVien);
        }

        int m = scanner.nextInt();
        List<String> khongTimThay = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String ma = scanner.next();
            String maChinhSach = scanner.next();
            SinhVien sinhVien = theoMa.get(ma);
            if (sinhVien == null) {
                khongTimThay.add(ma);
            } else {
                sinhVien.setMienGiam(taoMienGiam(maChinhSach));
            }
        }

        for (String ma : khongTimThay) {
            System.out.println("NOT FOUND " + ma);
        }

        Map<String, Integer> soLuongTheoHe = new HashMap<>();
        Map<String, Long> tongPhaiNopTheoHe = new HashMap<>();
        for (SinhVien sinhVien : danhSach) {
            long hocPhi = sinhVien.hocPhi();
            long soTienGiam = sinhVien.soTienGiam();
            long phaiNop = hocPhi - soTienGiam;
            System.out.println(sinhVien.ma + " " + sinhVien.maHe() + " "
                    + hocPhi + " " + soTienGiam + " " + phaiNop);
            soLuongTheoHe.put(sinhVien.maHe(),
                    soLuongTheoHe.getOrDefault(sinhVien.maHe(), 0) + 1);
            tongPhaiNopTheoHe.put(sinhVien.maHe(),
                    tongPhaiNopTheoHe.getOrDefault(sinhVien.maHe(), 0L) + phaiNop);
        }

        for (String maHe : new String[]{"CQ", "CLC", "LT"}) {
            if (soLuongTheoHe.containsKey(maHe)) {
                System.out.println(maHe + " " + soLuongTheoHe.get(maHe) + " "
                        + tongPhaiNopTheoHe.get(maHe));
            }
        }
    }
}
