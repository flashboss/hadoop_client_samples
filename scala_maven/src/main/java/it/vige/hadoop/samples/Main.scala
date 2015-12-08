package it.vige.hadoop.samples
import java.io._
import java.util.Arrays;

object Main {
  def main(args: Array[String]) {
    val testfileName = "testfile.txt"
    val testText = "Example text "
    val appendFile = "testfile1.txt"

    val emptyFile = "empty-file.txt"

    //Create an Empty file in Kms

    println("Creating an empty file")
    KmsFileService.createNewFile(emptyFile)

    //Creating a new file and saving it to Kms

    println("Creating new file")
    val testfile = new File(testfileName)
    val testfileWriter = new BufferedWriter(new FileWriter(testfile))
    println("Writing " + testText + " into the " + testfileName)
    testfileWriter.write(testText)
    testfileWriter.close
    println("Saving the file " + testfileName + " to MaprFs")
    KmsFileService.createAndSave(testfileName)

    //Append to file in Kms

    KmsFileService.appendToFile(testfileName,appendFile)
    println("Appended text from "+ appendFile + " to "+testfileName)

     //Reading a file from Kms

    val outputStream = new FileOutputStream(new File(testfileName))
    val in = KmsFileService.getFile(testfileName)
    var b = new Array[Byte](1024)
    var numBytes = in.read(b)
    while (numBytes > 0) {
      outputStream.write(b, 0, numBytes)
      numBytes = in.read(b)
    }
    outputStream.close
    in.close

    //Deleting a file from Kms

    println("Deleting the file " + emptyFile)
    KmsFileService.deleteFile(emptyFile)

   //Close the FileSystem Handle
     KmsFileService.close
   }
 }
