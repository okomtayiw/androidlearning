package dprdjepara.multisolusi.info.dprdjepara.model;


public class Edokumen {
	private String id;
	private String nomor;
	private String tahun;
	private String tentang;
	private String filename;
	private String filetype;
	private String kategori;
	private String md5;

	public Edokumen() {
		super();
	}

	public Edokumen(String id, String nomor, String tahun, String tentang, String filename,String filetype,String kategori) {
		super();
		this.id = id;
		this.nomor = nomor;
		this.tahun = tahun;
		this.tentang = tentang;
		this.filename = filename;
		this.filetype = filetype;
		this.kategori = kategori;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	public String getTahun() {
		return tahun;
	}

	public void setTahun(String tahun) {
		this.tahun = tahun;
	}
	
	public String getTentang() {
		return tentang;
	}
	public void setTentang(String tentang) {
		this.tentang = tentang;
	}

	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {this.filetype = filetype;}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename= filename;
	}

	public String getKategori() {
		return kategori;
	}
	public void setKategori(String kategori) {this.kategori = kategori;}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}