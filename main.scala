import java.net.{ServerSocket, Socket}
import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import scala.io.StdIn

object BasicHttpServer {
  def main(args: Array[String]): Unit = {
    val port = 8080
    val server = new ServerSocket(port)
    println(s"Server is running on http://localhost:$port")
    
    while (true) {
      val socket = server.accept()
      handleRequest(socket)
    }
  }

  def handleRequest(socket: Socket): Unit = {
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val out = new PrintWriter(socket.getOutputStream, true)

    // Read the request
    val request = in.readLine()
    println(s"Received request: $request")

    // Send response
    val json = """{"message": "hello"}"""

    val response = s"""HTTP/1.1 200 OK
         |Content-Type: application/json
         |Content-Length: ${json.length}
         |
         |$json""".stripMargin
    out.println(response)

    // Close the connection
    in.close()
    out.close()
    socket.close()
  }
}
