package cu.uci.abos.core.util;

public enum ReportType {
	PDF("pdf"), SPREADSHEET("xls"), TXT("txt"), XML("xml"), MODS("mods"), HTML("html"), MARC("marc");

	private String extension;

	private ReportType(String s) {
		extension = s;
	}

	public String getExtension() {
		return extension;
	}
}
