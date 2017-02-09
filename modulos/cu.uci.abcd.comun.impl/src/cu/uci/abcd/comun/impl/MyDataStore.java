package cu.uci.abcd.comun.impl;


public class MyDataStore {
	public static StringBuffer data = new StringBuffer();

	public static void putData(String auxData) {
		data.append(auxData + "\n");
	}

	public static byte[] getData() {
		data.trimToSize();
		return data.toString().getBytes();
	}
}
