error id: library/
file://<WORKSPACE>/src/main/scala/Library.scala
empty definition using pc, found symbol in pc: library/
semanticdb not found
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
package library
import scala.io.Source
import java.sql.Date
import java.sql.Date

class Book(
    var Book_id:Int,
    var Title:String,
    var Author:String,
    var Author_I_F:String,
    var Additional_authors:String,
    var ISBN:Int,
    var ISBN13:Int,
    var My_Rating:Int,
    var Average_Rating:Float,
    var Publisher:String,
    var Binding:String,
    var Page_number:Int,
    var Publish_year:Int,
    var Original_publication_year:Int,
    var Date_read:Date,
    var Date_added:Date,
    var Bookshelves:String,
    var Bookshelves_position:String,
    var Exclusive_shelves:String)

// @main def hello() = println("Hello, World!")

// object ReaderExample1 {
//   def main(args: Array[String]): Unit = {
//     val f = "03-GoodreadsLibraryExport.csv"
//     val delim = ","
//     val file = Source.fromFile(f)
//     var line = file.getLines()
//     println(line.next().split(delim).map(_.trim).mkString(", "))
//     // for (line <- file.getLines()) {
//     //     val fields = line.split(delim).map(_.trim)
//     //     println(fields.mkString(", "))
//     // }
//     file.close()
//   }
// }
import java.io.File
// import com.github.tototoshi.csv._

object ReaderExample2 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("output.csv"))
    val allRows = reader.allWithHeaders()
    allRows.foreach(println)
    reader.close()
  }
}


```

#### Short summary: 

empty definition using pc, found symbol in pc: library/