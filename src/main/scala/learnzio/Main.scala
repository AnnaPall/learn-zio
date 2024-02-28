package learnzio

//Scala 3 don't use _ to import all

import zio.*
import zio.Console.*

import java.io.IOException

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

//switch to Scala APP trait
object Main extends scala.App:
  val program: ZIO[Any, IOException, Unit] =
    for
      _ <- printLine("─" * 100)
      _ <- printLine("hello world")
      _ <- Console.printLine("─" * 100)
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
  Unsafe.unsafely:
    zio.Runtime.default.unsafe.runToFuture(program)

