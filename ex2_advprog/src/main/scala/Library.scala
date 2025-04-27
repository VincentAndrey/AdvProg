package library
import org.apache.commons.csv.{CSVFormat, CSVParser}
import java.nio.file.{Files, Paths}
import scala.jdk.CollectionConverters._


type CSVEntry = Map[String, String]

case class Author(val Name: String)

object Author:
    def apply(s:String) = new Author(s)


trait Identifiable[T]:
    def id: Option[T]


trait Book[+T] extends Identifiable[Int]:
    val title:String
    val author:Author
    
enum Format:
    case Paperback, Mass_Market_Paperback, Hardcover, Kindle_Edition, ebook, Webcomic, Revised_Edition, Audible_Audio, Audio_CD, Audiobook, Unknown_Binding, None

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
) extends Book[Int]:
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

// VA: Ex6 -> Extension Method for List[case_Book]
trait BookCollectionOps[A]:
  extension (list: List[A])
    def averageRating(using ev: A <:< case_Book): Float =
      val (total, count) = list.foldLeft((0.0f, 0)) {
        case ((sum, n), book) => (sum + ev(book).average_Rating, n + 1)
      }
      if (count == 0) 0.0f else total / count

given BookCollectionOps[case_Book] with {}

// VA: Ex6 -> Page Combiner (Monoid composition)
trait PageCombiner[A]:
  def zero: A
  def combine(x: A, y: A): A

object PageCombiner:
  given PageSum: PageCombiner[Int] with
    def zero: Int = 0
    def combine(x: Int, y: Int): Int = x + y

def sumPages[A](list: List[A])(using combiner: PageCombiner[A]): A =
  list.foldLeft(combiner.zero)(combiner.combine)

// VA: Ex6 -> Safe Average Pages (Monad with Option) ===
def safeAveragePages(books: List[case_Book]): Option[Double] =
  val pages: List[Int] = books.flatMap(_.page_number)
  if pages.isEmpty then None
  else Some(pages.sum.toDouble / pages.size)


@main def main =
    val data = readCsvFile()

    //VA: Ex5 -> changed to val for immutability and functional mapping
    val caseBooksList: List[case_Book] = data.map(insert_case_class)

    //VA: Ex5 -> use of collection with filter to get the highest rated books
    val highlyRatedBooks = caseBooksList.filter(_.average_Rating > 4.5)
    // println("Highly Rated Books (> 4.5):")
    // highlyRatedBooks.foreach(b => println(s"${b.title} by ${b.author.Name}"))

    //VA: Ex5 -> Use of groupBy to sorts by type of format
    val booksByFormat = caseBooksList.groupBy(_.format)
    // println("Books Grouped by Format:")
    // booksByFormat.foreach { case (format, books) =>
    //     println(s"\n$format:")
    //     books.foreach(book => println(s"- ${book.title}"))
    // }

    //VA: Ex5 -> Use of foldLeft to calculate the average rating
    val (total, count) = caseBooksList.foldLeft((0.0f, 0)) {
        case ((sum, n), book) => (sum + book.average_Rating, n + 1)
    }
    val avg = total / count
    // println(f"Average Rating: $avg%.2f")

    //VA: Ex5 -> Use of flatMap to get all authors and additional authors
    val allAuthorsFlat = caseBooksList.flatMap { book =>
        val main = List(book.author.Name)
        val additional = book.additional_authors.toList.flatMap(_.split(", ").toList)
        main ++ additional
    }
    // allAuthorsFlat.distinct.sorted.foreach(println)


