package dprdjepara.multisolusi.info.dprdjepara.model;


public class Dokumen {
	private String id_dokumen;
	private String judul;
	private String tanggal;
	private String skpd;
	private String sumber;
	private String jenis_dokumen;
	private String filetype;
	private String filename;
	private String md5;

	public Dokumen() {
		super();
	}

	public Dokumen(String id_dokumen, String judul, String tanggal, String skpd,String sumber,String jenis_dokumen,String filetype,String filename) {
		super();
		this.id_dokumen = id_dokumen;
		this.judul = judul;
		this.tanggal = tanggal;
		this.skpd = skpd;
		this.skpd = sumber;
		this.jenis_dokumen = jenis_dokumen;
		this.filetype = filetype;
		this.filetype = filename;
	}

	public String getId_dokumen() {
		return id_dokumen;
	}

	public void setId_dokumen(String id_dokumen) {
		this.id_dokumen = id_dokumen;
	}

	public String getJudul() {
		return judul;
	}

	public void setJudul(String judul) {
		this.judul = judul;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}
	
	public String getSkpd() {
		return skpd;
	}
	public void setSkpd(String skpd) {
		this.skpd = skpd;
	}

	public String getSumber() {
		return sumber;
	}
	public void setSumber(String sumber) {
		this.sumber = sumber;
	}

	public String getJenis_dokumen() {
		return jenis_dokumen;
	}
	public void setJenisDokumen(String jenis_dokumen) {
		this.jenis_dokumen = jenis_dokumen;
	}

	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {this.filetype = filetype;}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {this.filename = filename;}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}