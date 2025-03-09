file://<WORKSPACE>/src/main/scala/Library.scala
### java.lang.AssertionError: assertion failed

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.1.0
Classpath:
<WORKSPACE>/.bloop/ex2_advprog/bloop-bsp-clients-classes/classes-Metals-ttnOlHvWRduanNaj0S64rQ== [exists ], <HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.3/semanticdb-javac-0.10.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/tototoshi/scala-csv_3/2.0.0/scala-csv_3-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:
-Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
offset: 260
uri: file://<WORKSPACE>/src/main/scala/Library.scala
text:
```scala
package library
import scala.io.Source
import java.sql.Date
import java.sql.Date
import java.io.File
import com.github.tototoshi.csv._

class Book(
    var Book_id:Int,
    var Title:String,
    var Author:String,
    var Author_I_F:String,
    var Additional_@@authors:String,
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


object ReaderExample2 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("output.csv"))
    val allRows = reader.allWithHeaders()
    allRows.foreach(println)
    reader.close()
  }
}


```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:11)
	dotty.tools.dotc.interactive.Interactive$.contextOfPath(Interactive.scala:289)
	scala.meta.internal.pc.HoverProvider$.hover(HoverProvider.scala:97)
	scala.meta.internal.pc.ScalaPresentationCompiler.hover$$anonfun$1(ScalaPresentationCompiler.scala:368)
```
#### Short summary: 

java.lang.AssertionError: assertion failed