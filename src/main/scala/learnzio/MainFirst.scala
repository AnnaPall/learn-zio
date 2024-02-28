package learnzio

//Scala 3 don't use _ to import all

import zio.*
import zio.Console.*
import scala.Console

import java.io.IOException

object ourConsole:
  def putStrLn(line: String) = ZIO.succeed(zio.Console.printLine(line))
  val getStrln =
     scala.io.StdIn.readLine()

//Version1
//APP trait that shadows the Scala App trait
//object Main extends App:
//
//  val program =
//    for
//      _ <- printLine("─" * 100)
//      _ <- printLine("hello world")
//      _ <- Console.printLine("─" * 100)
//    yield ()
//  def run(ars: List[String]): URIO[Any, ExitCode] = program.exitCode

//Version 2
//switch to Scala APP trait
//object Main extends scala.App:
//
////  val trace = s"[${Console.BLUE}TRACE${Console.RESET}]"
//  val program: ZIO[Any, IOException, Unit] =
//    for
//      _ <- zio.Console.printLine("─" * 100)
//      _ <- printLine("What is your name?")
//      name <- zio.Console.readLine
//      _ <- zio.Console.printLine(s"Hello, $name!")
//    yield ()

//Version 3
object MainFirst extends scala.App:

  Unsafe.unsafely:
    zio.Runtime.default.unsafe.run(program)

  // if i don't put lazy, it will fail, we are referring on program earlier than defined
  //  val trace = s"[${Console.BLUE}TRACE${Console.RESET}]"
  lazy val program =
    for
      _ <- ZIO.succeed(zio.Console.printLine("─" * 100))
      _ <- printLine("What is your name?") //if i use the object ourConsole.putStrLn("What is your name?") I cannot run? why
//      name <- zio.Console.readLine
      name <- ZIO.succeed("Anna")
      _ <- zio.Console.printLine(s"Hello, $name!")
      _ <- ZIO.attempt(throw RuntimeException("boom")) //ZIO.attempt instead of ZIO.effect — In ZIO 2.0 all ZIO constructors like ZIO.effect* that create a ZIO from a side effect are deprecated and renamed to the ZIO.attempt* version. For example, when we are reading from a file, it's more meaningful to say we are attempting to read from a file instead of saying we have an effect of reading from a file.
      _ <- ZIO.succeed(zio.Console.printLine("─" * 100))
    yield ()

  // built in interpreter, lazy val
  // technically it is a thread pool behind it, normally one need to be careful with creating such an object. ZIO => lazy val,
  // you have only one runtime
  // We need to bring an unsafe instance into scope
  // docs:
  /*
  To run an unsafe operator, we need implicit value of Unsafe in scope.
  This works particularly well in Scala 3 due to its support for implicit function types championed by Martin Odersky.
  In Scala 3 we can use the Unsafe.unsafely operator to create a block of code in which we can freely call unsafe operators:

  Unsafe.unsafely {
    Runtime.default.unsafe.run(Console.printLine("Hello, World!"))
  }

  If we want to support Scala 2 we need to use a slightly more verbose syntax with unsafe
  and a lambda that takes an implicit value of Unsafe:

  import zio._

  Unsafe.unsafe { implicit unsafe =>
    Runtime.default.unsafe.run(Console.printLine("Hello, World!"))
  }

  Scala 2	runtime.unsafeRun(x)	Unsafe.unsafe { implicit unsafe => runtime.unsafe.run(x).getOrThrowFiberFailure() }
  Scala 3	runtime.unsafeRun(x)	Unsafe.unsafely { runtime.unsafe.run(x).getOrThrowFiberFailure() }
   */
//  Unsafe.unsafely:
//    zio.Runtime.default.unsafe.run(program)

  // Why is this exiting the program?
//  Unsafe.unsafely:
//    zio.Runtime.default.unsafe.runToFuture(program)
