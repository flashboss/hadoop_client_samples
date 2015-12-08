package it.vige.hadoop.samples;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class KmsFileService {
	private Configuration conf = new Configuration();
	private Path kmsSitePath = new Path("kms-site.xml");
	private FileSystem fileSystem;

	public KmsFileService() throws IOException {
		fileSystem = FileSystem.get(conf);
		conf.addResource(kmsSitePath);
	}

	public void mkdirs(String folderPath) throws IOException {
		Path path = new Path(folderPath);
		if (!fileSystem.exists(path)) {
			fileSystem.mkdirs(path);
		}
	}

	public void createNewFile(String filepath) throws IllegalArgumentException, IOException {
		File file = new File(filepath);
		boolean out = fileSystem.createNewFile(new Path(file.getName()));
		if (out)
			System.out.println("New file created as " + file.getName());
		else
			System.out.println("File cannot be created : " + file.getName());
	}

	public void createAndSave(String filepath) throws IllegalArgumentException, IOException {
		File file = new File(filepath);
		OutputStream out = fileSystem.create(new Path(file.getName()));
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		byte[] b = new byte[1024];
		int numBytes = in.read(b);
		while (numBytes > 0) {
			out.write(b, 0, numBytes);
			numBytes = in.read(b);
		}
		in.close();
		out.close();
	}

	public void appendToFile(String tofilepath, String fromfilepath) throws IllegalArgumentException, IOException {
		File file = new File(tofilepath);
		OutputStream out = fileSystem.append(new Path(file.getName()));
		InputStream in = new BufferedInputStream(new FileInputStream(new File(fromfilepath)));
		byte[] b = new byte[1024];
		int numBytes = in.read(b);
		while (numBytes > 0) {
			out.write(b, 0, numBytes);
			numBytes = in.read(b);
		}
		in.close();
		out.close();
	}

	public InputStream getFile(String filename) throws IOException {
		Path path = new Path(filename);
		return fileSystem.open(path);
	}

	public boolean deleteFile(String filename) throws IOException {
		Path path = new Path(filename);
		return fileSystem.delete(path, true);
	}

	public void close() throws IOException {
		fileSystem.close();
	}
}
