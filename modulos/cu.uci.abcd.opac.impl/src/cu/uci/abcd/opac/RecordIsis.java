package cu.uci.abcd.opac;

import java.awt.Image;

import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.domain.management.library.Library;

public class RecordIsis {

	private Record record;
	private String dataBaseName;
	private Library library;

	private String controlNumber;
	private String title;
	private String author;
	private String publication;
	private int publicationDate;
	private float rating;
	private String recordType;
	private String url;
	private String fileName;
	private String fileType;

	String srcIcon = null;
	Image imageIcon;

	public RecordIsis(Record record, String dataBaseName, Library library) {
		this.record = record;
		this.dataBaseName = dataBaseName;
		this.library = library;
		initializated();
	}

	public RecordIsis(Record record, String dataBaseName) {
		this.record = record;
		this.dataBaseName = dataBaseName;
		initializated();
	}

	private void initializated() {

		controlNumber = "";
		title = "";
		author = "";
		publication = "";
		recordType = "";
		url = "";
		fileName = "";
		fileType = "";

		try {

			controlNumber = record.getField(1).getStringFieldValue();

		} catch (Exception e) {
		}

		try {

			title = record.getField(245).getSubfield(0, "^a");
    
		} catch (Exception e) {
		}

		try {

			author = record.getField(100).getSubfield(0, "^a");

		} catch (Exception e) {

		}

		if (author == null || author == "") {
			try {
				author = record.getField(110).getSubfield(0, "^a");

			} catch (Exception e) {

			}

			if (author == null || author == "")
				try {
					author = record.getField(111).getSubfield(0, "^a");
				} catch (Exception e) {
				}

		}

		try {   

			Field urlArchivo = (Field) record.getField(856);
			url = urlArchivo.getSubfield("u");

			Field nameField = (Field) record.getField(856);
			fileName = nameField.getSubfield("f");

			Field kindField = (Field) record.getField(856);
			fileType = kindField.getSubfield("q");

		} catch (Exception e) {
		}

		try {

			publication = record.getField(260).getSubfield(0, "^a");
			publication += ", " + record.getField(260).getSubfield(0, "^b");

		} catch (Exception e) {
		}

		try {

			String yearParser = record.getField(260).getStringFieldValue().substring(record.getField(260).getStringFieldValue().length() - 4, record.getField(260).getStringFieldValue().length());

			if (yearParser.toCharArray()[0] != '[') {
				if (yearParser.toCharArray()[yearParser.length() - 1] != '.') {
					publicationDate = Integer.parseInt(yearParser);
				} else
					publicationDate = Integer.parseInt(yearParser.substring(0, yearParser.length() - 2));

			} else if (yearParser.toCharArray()[yearParser.length() - 1] != '.')
				publicationDate = Integer.parseInt(yearParser.substring(1, yearParser.length() - 3));
			else
				publicationDate = Integer.parseInt(yearParser.substring(1, yearParser.length() - 1));

		} catch (Exception e) {
		}

		try {

			recordType = record.getField(3006).getStringFieldValue();

		} catch (Exception e) {
		}

		title = title.replaceAll(" +", " ").trim();
		publication = publication.replaceAll(" +", " ").trim();

	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public int getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(int publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getSrcIcon() {
		return srcIcon;
	}

	public void setSrcIcon(String srcIcon) {
		this.srcIcon = srcIcon;
	}

	public Image getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(Image imageIcon) {
		this.imageIcon = imageIcon;
	}

	public String getControlNumber() {
		return controlNumber;
	}

	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

}
