package com.test

import akka.actor.{PoisonPill, LoggingFSM}
import com.test.Example._
import scaldi.{Injectable, Injector}
import Injectable._
import scala.concurrent.duration._

/**
 * Created by Sebastian on 22/09/15.
 */
object Example {

  sealed trait Data

  case class Counter(count: Int = 0) extends Data

  object Play

  sealed trait State

  case object Ping extends State

  case object Pong extends State

}

class Example(mode: String, implicit val inj: Injector) extends LoggingFSM[State, Data] {

  import context.dispatcher

  val service = inject[Service](identified by "fileService")

  override def preStart = {
    println("preStart")
    startWith(Ping, Counter())
  }

  onTransition{
    case Ping -> Pong | Pong -> Ping => {
      stateData match {
        case Counter(value) => {
          if(value == 7) {
            println("done...")
            //self ! PoisonPill
            //context.system.shutdown()
          }
        }
      }
      println("change...")
    }
  }

  when(Ping) {
    case Event(Play, counter: Counter) => {
      println(service.call)
      println(s"ping (${counter.count})")

      schedule //this line will cause the exception


      goto(Pong) using Counter(counter.count+1)
    }
  }

  when(Pong) {
    case Event(Play, counter: Counter) => {
      println(service.call)
      println(s"pong (${counter.count})")

      schedule //this line will cause the exception

      goto(Ping) using Counter(counter.count+1)
    }
  }

  def schedule = context.system.scheduler.scheduleOnce(500 milliseconds, self, Play)

}
