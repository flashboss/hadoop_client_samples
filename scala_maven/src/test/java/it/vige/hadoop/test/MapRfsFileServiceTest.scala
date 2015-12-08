
package it.vige.hadoop.test

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import it.vige.hadoop.samples.MapRfsFileService

class MapRfsFileServiceTest extends FunSpec with ShouldMatchers {
  val testfileName = "testfile.txt"
  val testText = "Example text"

  describe("Using the MapRFS File Service") {

    it("should allow to upload, read and delete a file") {

      val testfile = new File(testfileName)
     println(testfile.delete)
      println(testfile.createNewFile)
      val testfileWriter = new BufferedWriter(new FileWriter(testfile))
      testfileWriter.write(testText)
      testfileWriter.close
      MapRfsFileService.createAndSave(testfileName)
      testfile.delete
      testfile.exists should be(false)

      val outputStream = new FileOutputStream(new File(testfileName))
      val in = MapRfsFileService.getFile(testfileName)
      var b = new Array[Byte](1024)
      var numBytes = in.read(b)
      while (numBytes > 0) {
        outputStream.write(b, 0, numBytes)
        numBytes = in.read(b)
      }
      outputStream.close
      in.close

      val localCheckReader = new BufferedReader(new FileReader(testfileName))
      val checkText = localCheckReader.readLine
      println(checkText)     
      localCheckReader.readLine should be (null)
      localCheckReader.close

      testfile.delete
      //MapRfsFileService.removeFile(testfileName) should be(true)

    }
  }
}

