package cu.uci.abcd.domain.util;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

import cu.uci.abcd.domain.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;


public class Auxiliary {

	public static String FormatDate(Date date){
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}
	
	public static synchronized int getAge(Date bd){
		
		Calendar c =Calendar.getInstance();
		c.setTime(new java.util.Date());
		
		 int cy = c.get(Calendar.YEAR);
		 int cm = c.get(Calendar.MONTH);
		 int cd = c.get(Calendar.DAY_OF_MONTH);
		 c.setTime(bd);
		 int edad = cy - c.get(Calendar.YEAR);
		 if(cm < cy - c.get(Calendar.MONTH))
			 edad = edad - 1;
		 else
			 if(cm == cy - c.get(Calendar.MONTH) && cd <  c.get(Calendar.DAY_OF_MONTH)){
				edad = edad - 1;
			 }
		return edad + 1;
}
	
	public static String FormatMilitareHour(Timestamp timestamp){
		int hour = Integer.parseInt(timestamp.toString().substring(11, 13));
		String minutes = timestamp.toString().substring(14, 16);	
		
		//int hours = (hour>12)?hour-12: hour;
		return hour +  ":" + minutes; 
				//+ " " + getAMPM(timestamp.getHours(), minutes);
	}
	
	@SuppressWarnings("deprecation")
	public static String FormatHour(Timestamp timestamp){
		int hour = Integer.parseInt(timestamp.toString().substring(11, 13));
		String minutes = timestamp.toString().substring(14, 16);	
		int hours = (hour>12)?hour-12: hour;
		return hours +  ":" + minutes + " " + getAMPM(timestamp.getHours(), minutes);
	}
	
	public static String getAMPM(int hour, String minutes){
			return (hour<12)?"AM":"PM";
	}
	
	public static String getHourToString(Timestamp timestamp){
		return Integer.parseInt(timestamp.toString().substring(11, 13)) +":"+ timestamp.toString().substring(14, 16);
	}
	
	
	public static ImageData convertToSWT(BufferedImage bufferedImage) {
	    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	        DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
	        PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(),
	            colorModel.getBlueMask());
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	            colorModel.getPixelSize(), palette);
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	          for (int x = 0; x < data.width; x++) {
	            raster.getPixel(x, y, pixelArray);
	            int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
	            data.setPixel(x, y, pixel);
	          }
	        }
	        return data;
	      } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	        IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
	        int size = colorModel.getMapSize();
	        byte[] reds = new byte[size];
	        byte[] greens = new byte[size];
	        byte[] blues = new byte[size];
	        colorModel.getReds(reds);
	        colorModel.getGreens(greens);
	        colorModel.getBlues(blues);
	        RGB[] rgbs = new RGB[size];
	        for (int i = 0; i < rgbs.length; i++) {
	          rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
	        }
	        PaletteData palette = new PaletteData(rgbs);
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	            colorModel.getPixelSize(), palette);
	        data.transparentPixel = colorModel.getTransparentPixel();
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[1];
	        for (int y = 0; y < data.height; y++) {
	          for (int x = 0; x < data.width; x++) {
	            raster.getPixel(x, y, pixelArray);
	            data.setPixel(x, y, pixelArray[0]);
	          }
	        }
	        return data;
	      }
	      return null;
	    }
	
	
	public static String getMonth(int month) {
		switch (month) {
		case 1:
         return MessageUtil.unescape(AbosMessages.get().MONTH_JANUARY);
		case 2:
			return MessageUtil.unescape(AbosMessages.get().MONTH_FEBRUARY);
		case 3:
			return MessageUtil.unescape(AbosMessages.get().MONTH_MARCH);
		case 4:
			return MessageUtil.unescape(AbosMessages.get().MONTH_APRIL);
		case 5:
			return MessageUtil.unescape(AbosMessages.get().MONTH_MAY);
		case 6:
			return MessageUtil.unescape(AbosMessages.get().MONTH_JUNE);
		case 7:
			return MessageUtil.unescape(AbosMessages.get().MONTH_JULY);
		case 8:
			return MessageUtil.unescape(AbosMessages.get().MONTH_AUGUST);
		case 9:
			return MessageUtil.unescape(AbosMessages.get().MONTH_SEPTEMBER);
		case 10:
			return MessageUtil.unescape(AbosMessages.get().MONTH_OCTOBER);
		case 11:
			return MessageUtil.unescape(AbosMessages.get().MONTH_NOVEMBER);
		case 12:
			return MessageUtil.unescape(AbosMessages.get().MONTH_DICEMBER);
		}
		return null;

	}
	
}
