package it.vige.hadoop.samples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public class Main {

	public static void main(String[] args) throws IOException {
		String testfileName = "testfile.txt";
		String testText = "Example text ";
		String appendFile = "testfile1.txt";

		String emptyFile = "empty-file.txt";

		// Create an Empty file in Kms

		System.out.println("Creating an empty file");
		KmsFileService kmsFileService = new KmsFileService();
		kmsFileService.createNewFile(emptyFile);

		// Creating a new file and saving it to Kms

		System.out.println("Creating new file");
		File testfile = new File(testfileName);
		Writer testfileWriter = new BufferedWriter(new FileWriter(testfile));
		System.out.println("Writing " + testText + " into the " + testfileName);
		testfileWriter.write(testText);
		testfileWriter.close();
		System.out.println("Saving the file " + testfileName + " to MaprFs");
		kmsFileService.createAndSave(testfileName);

		// Append to file in Kms

		kmsFileService.appendToFile(testfileName, appendFile);
		System.out.println("Appended text from " + appendFile + " to " + testfileName);

		// Reading a file from Kms

		OutputStream outputStream = new FileOutputStream(new File(testfileName));
		InputStream in = kmsFileService.getFile(testfileName);
		byte[] b = new byte[1024];
		int numBytes = in.read(b);
		while (numBytes > 0) {
			outputStream.write(b, 0, numBytes);
			numBytes = in.read(b);
		}
		outputStream.close();
		in.close();

		// Deleting a file from Kms

		System.out.println("Deleting the file " + emptyFile);
		kmsFileService.deleteFile(emptyFile);

		// Close the FileSystem Handle
		kmsFileService.close();

	}
}
