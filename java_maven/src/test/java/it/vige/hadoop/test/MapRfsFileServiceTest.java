package it.vige.hadoop.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.junit.Test;

import it.vige.hadoop.samples.MapRfsFileService;

public class MapRfsFileServiceTest {
	String testfileName = "testfile.txt";
	String testText = "Example text";

	@Test
	public void test() throws IOException {

		File testfile = new File(testfileName);
		System.out.println(testfile.delete());
		System.out.println(testfile.createNewFile());
		Writer testfileWriter = new BufferedWriter(new FileWriter(testfile));
		testfileWriter.write(testText);
		testfileWriter.close();
		MapRfsFileService mapRfsFileService = new MapRfsFileService();
		mapRfsFileService.createAndSave(testfileName);
		testfile.delete();
		assertFalse(testfile.exists());

		OutputStream outputStream = new FileOutputStream(new File(testfileName));
		InputStream in = mapRfsFileService.getFile(testfileName);
		byte[] b = new byte[1024];
		int numBytes = in.read(b);
		while (numBytes > 0) {
			outputStream.write(b, 0, numBytes);
			numBytes = in.read(b);
		}
		outputStream.close();
		in.close();

		BufferedReader localCheckReader = new BufferedReader(new FileReader(testfileName));
		String checkText = localCheckReader.readLine();
		System.out.println(checkText);
		assertNull(localCheckReader.readLine());
		localCheckReader.close();

		testfile.delete();

	}

}
