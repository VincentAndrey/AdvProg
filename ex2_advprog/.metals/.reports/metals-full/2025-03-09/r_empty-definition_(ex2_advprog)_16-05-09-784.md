error id: 
file://<WORKSPACE>/src/main/scala/Library.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
package library
import scala.io.Source
import java.sql.Date
import java.sql.Date
import java.io.File

trait Book:
    val Book_id:Int
    val Title:String
    val Author:String
    val Additional_authors:String
    val ISBN:Int
    val Average_Rating:Float
    val Publisher:String
    val Binding:String
    val Page_number:Int
    val Publish_year:Int

class Books(Book_id:Int,
    Title:String,
    Author:String,
    Additional_authors:String,
    ISBN:Int,
    Average_Rating:Float,
    Publisher:String,
    Binding:String,
    Page_number:Int,
    Publish_year:Int,
    )


case class case_Book(
    Book_id:Int,
    Title:String,
    Author:String,
    Additional_authors:Option[String],
    ISBN:Option[Int],
    Average_Rating:Float,
    Publisher:Option[String],
    Binding:Option[String],
    Page_number:Option[Int],
    Publish_year:Option[Int],
    )

// @main def readCsvFile =
//     val bufferedSource = io.Source.fromFile("03-GoodreadsLibraryExportShort.csv")
//     for line <- bufferedSource.getLines.drop(1) do
//         val cols = line.split(",").map(_.trim)
//         val cols(1)=case_Book(cols(0).toInt,cols(1),cols(2),cols(3),cols(4).toInt,cols(5).toFloat,cols(6),cols(7),cols(8).toInt,cols(9).toInt)
//         println(s"${cols(0)}, ")
//     bufferedSource.close

@main def readCsvFile =
  val bufferedSource = io.Source.fromFile("03-GoodreadsLibraryExportShort.csv")

  // List to store all books
  var booksList: List[case_Book] = List()

  // Process each line from the CSV
  for line <- bufferedSource.getLines.drop(1) do
    val cols = line.split(",").map(_.trim)

    // Try parsing integers and floats with defaults to avoid NumberFormatException
    def parseInt(s: String): Option[Int] = 
      if (s.isEmpty) None else try Some(s.toInt) catch { case _: NumberFormatException => None }
    
    def parseFloat(s: String): Option[Float] = 
      if (s.isEmpty) None else try Some(s.toFloat) catch { case _: NumberFormatException => None }

    // Parse each field, providing default values if empty
    val book_id = parseInt(cols(0)).getOrElse(0)
    val isbn = parseInt(cols(4)).getOrElse(0)
    val avg_rating = parseFloat(cols(5)).getOrElse(0.0f)
    val page_number = parseInt(cols(8)).getOrElse(0)
    val publish_year = parseInt(cols(9)).getOrElse(0)

    // Create the case_Book instance
    val books = case_Book(
      cols(0).toInt,  // Book_id
      cols(1),        // Title
      cols(2),        // Author
      Some(cols(3)),        // Additional_authors
      Some(cols(4).toInt),  // ISBN
      cols(5).toFloat, // Average_Rating
      Some(cols(6)),        // Publisher
      Some(cols(7)),        // Binding
      Some(cols(8).toInt),  // Page_number
      Some(cols(9).toInt)   // Publish_year
    )

//     // Add the book to the list
//     booksList = booksList :+ books

//   // Print all books
//   booksList.foreach { book =>
//     println(s"Book: ${book.Title}, Author: ${book.Author}")
//   }

  bufferedSource.close

```

#### Short summary: 

empty definition using pc, found symbol in pc: 