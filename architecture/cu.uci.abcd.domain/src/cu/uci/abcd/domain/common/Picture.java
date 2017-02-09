package cu.uci.abcd.domain.common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.util.AbosImageUtil;

@Entity
@Table(name = "dpicture", schema = "abcdn")
@SequenceGenerator(name = "seq_dpicture", sequenceName = "sq_dpicture", schema = "abcdn", allocationSize = 1)
public class Picture implements Serializable, Row {
	private static final long serialVersionUID = -7540131550320727932L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dpicture")
	@Column(name = "id", nullable = false)
	private Long pictureID;

	@Column(name = "photo", nullable = false)
	private byte[] photo;

	@Column(name = "picturename", nullable = false, length = 20)
	private String pictureName;

	@OneToOne(mappedBy = "photo")
	private Person person;

	public Long getPictureID() {
		return pictureID;
	}

	public void setPictureID(Long pictureID) {
		this.pictureID = pictureID;
	}

	public byte[] getPhoto() {
		return photo;
	}

	private void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public Object getRowID() {
		return getPictureID();
	}

	public void setUrlImage(String urlImage) {
		File file = new File(urlImage);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		setPhoto(bytes);
	}

	public Image getImage() {
		return getImage(100, 100);
	}

	public Image getImage(int width, int height) {
		if (getPhoto() == null) {
			return new Image(Display.getDefault(), AbosImageUtil
					.loadImage(null, Display.getCurrent(),
							"abcdconfig/resources/photo.png", false)
					.getImageData().scaledTo(width, height));
		} else {
			return new Image(Display.getDefault(),
					AbosImageUtil
							.loadImage(null, Display.getCurrent(),
									getUrlImage(), false).getImageData()
							.scaledTo(width, height));
		}
	}

	public String getUrlImage() {
		byte[] bytes = getPhoto();
		final ByteArrayInputStream bStream = new ByteArrayInputStream(bytes);
		ImageInputStream imgStream = null;

		File folder = new File(System.getProperty("java.io.tmpdir"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try {
			imgStream = ImageIO.createImageInputStream(bStream);
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		Iterator<ImageReader> iter = ImageIO.getImageReaders(imgStream);
		final ImageReader imgReader = iter.next();
		String format = null;
		try {
			format = imgReader.getFormatName();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Iterator<?> readers = ImageIO.getImageReadersByFormatName(format);
		ImageReader reader = (ImageReader) readers.next();
		Object source = bis;
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(source);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		java.awt.Image image = null;
		try {
			image = reader.read(0, param);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, null, null);
		String uuid = UUID.randomUUID().toString();
		File imageFile = new File(System.getProperty("java.io.tmpdir") + uuid
				+ "." + format);
		try {
			ImageIO.write(bufferedImage, format, imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageFile.getPath();
	}
}
