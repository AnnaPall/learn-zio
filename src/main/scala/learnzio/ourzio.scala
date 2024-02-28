package learnzio

object ourzio:

  // Another use case for type alias is function types. We can use a type alias to simplify the declaration of a function type to later use it in other methods.
  type Thunk[A] = () => A
  case class ZIO[A](thunk: Thunk[A]):

    //goes from ato zb
    def flatMap[B](azb: A => ZIO[B]): ZIO[B] =
      ZIO.succeed:
        val a: A = thunk()
        val zb: ZIO[B] = azb(a)
        val b: B = zb.thunk()
        b
    // goes from a to b
    def map[B](ab: A => B): ZIO[B] =
      ZIO.succeed:
        val a: A = thunk()
        val b: B = ab(a)
        b
  end ZIO

  object ZIO:
    def succeed[A](a: => A): ZIO[A] = ZIO(() => a)

  object console:
    def putStrLn(line: => String) =
      ZIO.succeed(println(line))

    def getStrLn = ZIO.succeed(scala.io.StdIn.readLine())

  object Runtime:
    object default:
     def unsafeRunSync[A](zio: => ZIO[A]): A =
       zio.thunk()

