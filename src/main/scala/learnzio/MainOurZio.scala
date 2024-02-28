package learnzio

import ourzio.*
object MainOurZio extends scala.App:

  Runtime.default.unsafeRunSync(program)

  lazy val program =
    for
      _ <- ourzio.console.putStrLn("─" * 100)
      _ <- ourzio.console.putStrLn("What is your name?") //if i use the object ourConsole.putStrLn("What is your name?") I cannot run? why
      //      name <- zio.Console.readLine
      name <- ourzio.console.getStrLn
      _ <- console.putStrLn(s"Hello, $name!")
      _ <- console.putStrLn("─" * 100)
    yield ()

