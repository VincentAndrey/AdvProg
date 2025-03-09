package library
import org.apache.commons.csv.{CSVFormat, CSVParser}
import java.nio.file.{Files, Paths}
import scala.jdk.CollectionConverters._

trait Book:
    val Title:String
    val Author:String

enum Binding:
    case Paperback, Mass_Market_Paperback, Hardcover, Kindle_Edition, ebook, Webcomic, Revised_Edition, Audible_Audio, Audio_CD, Audiobook, Unknown_Binding, None

class Author(
    val Name: String
)

object Author{
    def apply(s:String) = new Author(s)
}

class Book_few_info(
    val Book_id:Int,
    val Title:String,
    val Author:String,
    val Binding:Binding,
    val Page_number:Option[Int],
    val Publish_year:Option[Int],
    )
    extends Book

class Book_all_infos(
    val Book_id:Int,
    val Title:String,
    val Author:String,
    val Additional_authors:Option[String],
    val ISBN:Int,
    val Average_Rating:Float,
    val Publisher:String,
    val Binding:Binding,
    val Page_number:Option[Int],
    val Publish_year:Option[Int],
    )
    extends Book 

case class case_Book(
    Book_id:Int,
    Title:String,
    Author:String,
    Additional_authors:Option[String],
    ISBN:Int,
    Average_Rating:Float,
    Publisher:Option[String],
    Binding:Binding,
    Page_number:Option[Int],
    Publish_year:Option[Int],
    )

def author_initials(book:Book_few_info)=
    book.Author.take(3)

def parseInt(s: String): Option[Int] = 
    if (s.isEmpty) None else try Some(s.toInt) catch { case _: NumberFormatException => None }
        
def parseFloat(s: String): Option[Float] = 
    if (s.isEmpty) None else try Some(s.toFloat) catch { case _: NumberFormatException => None }

def TestString(s: String): Option[String] =
    if (s.isEmpty()) None else Some(s)

def get_binding(s:String): Binding=
    s match{
        case "Paperback" => Binding.Paperback
        case "Hardcover" => Binding.Hardcover
        case "Mass Market Paperback" =>  Binding.Mass_Market_Paperback
        case "Kindle Edition" => Binding.Kindle_Edition
        case "Audiobook" => Binding.Audiobook 
        case "ebook" => Binding.ebook 
        case "Unknown Binding" => Binding.Unknown_Binding 
        case "Audible Audio" => Binding.Audible_Audio
        case "Audio CD" => Binding.Audio_CD
        case "Revised edition" => Binding.Revised_Edition
        case "Webcomic" => Binding.Webcomic
        case "" => Binding.None 
    }

def readCsvFile(): List[Map[String, String]] =
    val path = Paths.get("03-GoodreadsLibraryExport.csv")
    val reader = Files.newBufferedReader(path)

    val parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())

    val data: List[Map[String, String]] = parser.getRecords.asScala.map { record =>
    record.toMap.asScala.toMap
    }.toList

    return data

def insert_class(entry: Map[String,String]): Book_few_info =
    val book_id = parseInt(entry("Book Id")).getOrElse(0)
    val page_number = parseInt(entry("Number of Pages"))
    val publish_year = parseInt(entry("Year Published"))
    val book = new Book_few_info(
        book_id,        
        entry("Title"),
        entry("Author"),
        get_binding(entry("Binding")),
        page_number,
        publish_year
    )

    return book

def insert_case_class(entry: Map[String,String]): case_Book = 
    // Parse each field, providing default values if empty
    val book_id = parseInt(entry("Book Id")).getOrElse(0)
    val isbn = parseInt(entry("ISBN")).getOrElse(0)
    val avg_rating = parseFloat(entry("Average Rating")).getOrElse(0.0f)
    val page_number = parseInt(entry("Number of Pages"))
    val publish_year = parseInt(entry("Year Published"))

    // Create the case_Book instance
    val book = case_Book(
        book_id,        // Book_id
        entry("Title"),        // Title
        entry("Author"),        // Author
        TestString(entry("Additional Authors")),        // Additional_authors
        isbn,           // ISBN
        avg_rating,     // Average_Rating
        TestString(entry("Publisher")),        // Publisher
        get_binding(entry("Binding")),        // Binding
        page_number,    // Page_number
        publish_year    // Publish_year
        )
    return book 

@main def main =
    var ClassBooksList: List[Book_few_info] = List()
    var CaseBooksList: List[case_Book] = List()
    val data = readCsvFile()
    

    // Print data
    for entry <- data do
        val ClassBook = insert_class(entry)
        val CaseBook = insert_case_class(entry)
        ClassBooksList = ClassBooksList :+ ClassBook
        CaseBooksList = CaseBooksList :+ CaseBook

    Author(data(0)("Author"))