package dprdjepara.multisolusi.info.dprdjepara.model;

import android.graphics.Bitmap;


public class Rapat {
	private String id_rapat;
	private String tanggal;
	private String jam_mulai;
	private String jam_selesai;
	private String nama_rapat;
	private String jenis_rapat;
	private String sub_jenis_rapat;
	private String sifat;
	private String topik;
	private String peserta;
	private String tempat;
	private String kategori;
	private String pimpinan_rapat;
	private String moderator;
	private String narasumber;
	private String catatan_moderator;
	private String hasil;
	private String keterangan;
	//private Bitmap foto;
	
	public Rapat() {
		super();
	}

	public Rapat(String id_rapat, String tanggal, String jam_mulai,String jam_selesai,
			String nama_rapat,String jenis_rapat,String sub_jenis_rapat,String sifat,String topik,String peserta,String tempat,String kategori,String pimpinan_rapat,String moderator,
			String narasumber,String catatan_moderator,String hasil,String keterangan) {
		super();
		this.id_rapat = id_rapat;
		this.tanggal = tanggal;
		this.jam_mulai = jam_selesai;
		this.nama_rapat = nama_rapat;
		this.jenis_rapat = jenis_rapat;
		this.topik = topik;
		this.tempat = tempat;
		this.kategori = kategori;
		this.pimpinan_rapat = pimpinan_rapat;
		this.moderator = moderator;
		this.narasumber = narasumber;
		this.catatan_moderator = catatan_moderator;
		this.hasil = hasil;
		this.keterangan = keterangan;
		//this.foto = foto;
	}

	public String getId_rapat() {
		return id_rapat;
	}

	public void setId_rapat(String id_rapat) {
		this.id_rapat = id_rapat;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getJam_mulai() {
		return jam_mulai;
	}

	public void setJam_mulai(String jam_mulai) {
		this.jam_mulai = jam_mulai;
	}
	
	public String getJam_selesai() {
		return jam_selesai;
	}

	public void setJam_selesai(String jam_selesai) {
		this.jam_selesai = jam_selesai;
	}
	public String getNama_rapat() {
		return nama_rapat;
	}

	public void setNama_rapat(String nama_rapat) {
		this.nama_rapat = nama_rapat;
	}


	public String getJenis_rapat() {
		return jenis_rapat;
	}

	public void setJenis_rapat(String jenis_rapat) {
		this.jenis_rapat = jenis_rapat;
	}


	public String getTopik() {
		return topik;
	}

	public void setTopik(String topik) {
		this.topik = topik;
	}

	public String getTempat() {
		return tempat;
	}

	public void setTempat(String tempat) {
		this.tempat = tempat;
	}

	public String getKategori() {
		return kategori;
	}
	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public String getPimpinan_rapat() {
		return pimpinan_rapat;
	}
	public void setPimpinan_rapat(String pimpinan_rapat) {
		this.pimpinan_rapat = pimpinan_rapat;
	}

	public String getModerator() {
		return moderator;
	}
	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public String getNarasumber() {
		return narasumber;
	}
	public void setNarasumber(String narasumber) {
		this.narasumber = narasumber;
	}

	public String getCatatan_moderator() {
		return catatan_moderator;
	}
	public void setCatatan_moderator(String catatan_moderator) {
		this.catatan_moderator = catatan_moderator;
	}

	public String getHasil() {
		return hasil;
	}
	public void setHasil(String hasil) {
		this.hasil = hasil;
	}


	public String getKeterangan() {
		return keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	//public Bitmap getFoto() {return foto;}
	//public void setFoto(Bitmap foto) {this.foto = foto; }

}