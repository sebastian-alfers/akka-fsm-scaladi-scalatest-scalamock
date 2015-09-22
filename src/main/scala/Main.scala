package com.test

import akka.actor.{Props, ActorSystem}
import com.test.Example
import com.test.Example.Play
import scaldi.Module

object Main extends App{

  // wire up params for production
  class Dependencies extends Module {
    val service = new File("localhost", 8080)
    binding identifiedBy "fileService" to service
  }
  val inj = new Dependencies()

  lazy val system = ActorSystem("testSystem")

  println("started...")


  val props = Props(classOf[Example], inj)
  val actor = system.actorOf(props)

  actor ! Play
  actor ! Play
  actor ! Play
  actor ! Play
  actor ! Play
  actor ! Play
  actor ! Play
  actor ! Play

}

trait Service {
  val host: String
  val port: Int

  def call: String
}

class File(override val host: String, override val port: Int) extends Service{
  def call = {
    //call external service
    "success"
  }
}