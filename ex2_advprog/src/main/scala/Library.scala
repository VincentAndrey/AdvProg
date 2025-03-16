package library
import org.apache.commons.csv.{CSVFormat, CSVParser}
import java.nio.file.{Files, Paths}
import scala.jdk.CollectionConverters._

// Type Alias for readability
type CSVEntry = Map[String, String]

case class Author(val Name: String)

object Author:
    def apply(s:String) = new Author(s)

// VA: Parametrized Type: Generic Trait for different book-related entities
trait Identifiable[T]:
    def id: Option[T]

// VA: Covariant Generic Trait
trait Book[+T] extends Identifiable[Int]:
    val title:String
    val author:Author
    
enum Format:
    case Paperback, Mass_Market_Paperback, Hardcover, Kindle_Edition, ebook, Webcomic, Revised_Edition, Audible_Audio, Audio_CD, Audiobook, Unknown_Binding, None


// VA: Case Class extending Generic Book Trait
case class case_Book(
    book_id: Option[Int],
    title: String,
    author: Author,
    additional_authors: Option[String],
    ISBN: Option[String],
    average_Rating: Float,
    publisher: Option[String],
    format: Format,
    page_number: Option[Int],
    publish_year: Option[Int]
) extends Book[Int]: // VA: Extends Covariant Trait
    def id: Option[Int] = book_id

def author_initials(book:case_Book)=
    book.author.Name.take(3)

def parseInt(s: String): Option[Int] = 
    if (s.isEmpty) None 
    else try Some(s.toInt) catch { case _: NumberFormatException => None }
        
def parseFloat(s: String): Option[Float] = 
    if (s.isEmpty) None else try Some(s.toFloat) catch { case _: NumberFormatException => None }

def TestString(s: String): Option[String] =
    if (s.isEmpty()) None else Some(s)

def get_format(s:String): Format=
    s match
        case "Paperback" => Format.Paperback
        case "Hardcover" => Format.Hardcover
        case "Mass Market Paperback" =>  Format.Mass_Market_Paperback
        case "Kindle Edition" => Format.Kindle_Edition
        case "Audiobook" => Format.Audiobook 
        case "ebook" => Format.ebook 
        case "Unknown Binding" => Format.Unknown_Binding 
        case "Audible Audio" => Format.Audible_Audio
        case "Audio CD" => Format.Audio_CD
        case "Revised edition" => Format.Revised_Edition
        case "Webcomic" => Format.Webcomic
        case "" => Format.None 

def readCsvFile(): List[Map[String, String]] =
    val path = Paths.get("03-GoodreadsLibraryExport.csv")
    val reader = Files.newBufferedReader(path)

    val parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())

    val data: List[Map[String, String]] = parser.getRecords.asScala.map { record =>
    record.toMap.asScala.toMap
    }.toList

    return data

def insert_case_class(entry: CSVEntry): case_Book = 
    val book_id = parseInt(entry("Book Id"))
    val avg_rating = parseFloat(entry("Average Rating")).getOrElse(0.0f) 
    val page_number = parseInt(entry("Number of Pages"))
    val publish_year = parseInt(entry("Year Published"))

    val book = case_Book(
        book_id,
        entry("Title"),
        Author(entry("Author")),
        TestString(entry("Additional Authors")),
        TestString(entry("ISBN")),
        avg_rating,
        TestString(entry("Publisher")),
        get_format(entry("Binding")),
        page_number,
        publish_year
        )
    return book 

@main def main =
    var CaseBooksList: List[case_Book] = List()
    val data = readCsvFile()
    
    // Print data
    for entry <- data do
        val CaseBook = insert_case_class(entry)
        CaseBooksList = CaseBooksList :+ CaseBook