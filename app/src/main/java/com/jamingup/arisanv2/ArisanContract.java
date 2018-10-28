package com.jamingup.arisanv2;

import android.net.Uri;
import android.provider.BaseColumns;


public class ArisanContract implements BaseColumns {
    public static final String CONTENT_AUTHORITY = "com.jamingup.arisanv2";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class Peserta {
        public static final String TABLE_PESERTA = "peserta";
        public static final String COL_ID = BaseColumns._ID;
        public static final String COL_NAME = "name";
        public static final String COL_ALAMAT = "alamat";
        public static final String COL_TELP = "telp";
        public static final String COL_STATUS = "status";
        public static final String COL_PEKAN = "pekan";

        public static final Uri PESERTA_URI = Uri.withAppendedPath(BASE_URI, TABLE_PESERTA);
    }

    public static final class Pemenang {
        public static final String TABLE_PEMENANG = "pemenang";
        public static final String COLPEM_ID = BaseColumns._ID;
        public static final String COL_PEKAN = "pekan";
        public static final String COL_NAMAPEM = "pemenang";
        public static final String COL_KELPEM = "kelompokpemenang";
        public static final String COL_MENDATE = "tanggal";

        public static final Uri PEMENANG_URI = Uri.withAppendedPath(BASE_URI, TABLE_PEMENANG);
    }

    public static final class Kelompok {
        public static final String TABLE_KELOMPOK = "kelompok";
        public static final String COLKEL_ID = BaseColumns._ID;
        public static final String COL_NAMAKEL = "kelompok";
        public static final String COL_NOMINAL = "nominal";
        public static final String COL_JUMPES = "jumlahpeserta";

        public static final Uri KELOMPOK_URI = Uri.withAppendedPath(BASE_URI, TABLE_KELOMPOK);
    }

    public static final class Kocok {
        public static final String TABLE_KOCOK = "kocok";
        public static final String COLKOC_ID = BaseColumns._ID;
        public static final String COL_NAMAKELKOC = "kelompokkocok";
        public static final String COL_NAMAPESKOC = "pesertakocok";
        public static final String COL_STATUSKOC = "statuskocok";

        public static final Uri KOCOK_URI = Uri.withAppendedPath(BASE_URI, TABLE_KOCOK);
    }

    public static final class Tagihan {
        public static final String TABLE_TAGIHAN = "tagihan";
        public static final String COLTAG_ID = BaseColumns._ID;
        public static final String COL_PEKAN = "pekan";
        public static final String COL_NAMAKELTAG = "kelompoktagihan";
        public static final String COL_NAMAPESTAG = "pesertatagihan";
        public static final String COL_STATUSTAG = "statustagihan";

        public static final Uri TAGIHAN_URI = Uri.withAppendedPath(BASE_URI, TABLE_TAGIHAN);
    }
}
