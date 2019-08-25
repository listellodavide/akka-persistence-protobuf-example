package io.adiwave.akka.persistenceprotobuf.actors

import akka.persistence._
import akka.actor.ActorLogging

object CounterActor {

  import io.adiwave.akka.persistenceprotobuf.persistence.CounterAggregate._
  import io.adiwave.akka.persistenceprotobuf.persistence.CounterAggregateProto

  trait OperationEvt

  val persistenceId: String = "counter-actor"
  val snapshotInterval: Int = 5

}

class CounterActor extends PersistentActor with ActorLogging {
  import CounterActor._
  import io.adiwave.akka.persistenceprotobuf.persistence.CounterAggregate._
  println("Starting...")

  // Persistent Identifier
  override def persistenceId = CounterActor.persistenceId

  var state: State = State(count = 0)

  def updateState(evt: Evt): Unit = evt match {
    case Evt(Increment(count)) =>
      state = State(count = state.count + count)
    case Evt(Decrement(count)) =>
      state = State( count = state.count - count)
  }

  // Persistent receive on recover mood
  val receiveRecover: Receive = {
    case evt: Evt =>
      println(s"Counter receive ${evt} on recovering mood")
    case SnapshotOffer(_, snapshot: State) =>
      println(s"Counter receive snapshot with data: ${snapshot} on recovering mood")
      state = snapshot

  }

  // Persistent receive on normal mood
  val receiveCommand : Receive = {
    /*
    * @ binds the object being pattern matched to a variable. msg @ GetCharLog will result in msg holding a reference to the
    * GetCharLog object, which isn't very useful. A better example is msg @ Foo(a, b, c), which will result in msg holding
    * a reference to the instance of Foo that is matched on, which lets you forward the received message (for example)
    * without needing to construct another instance of Foo with a, b, and c.
    * Another good example is if you have some specific types
    * you want to match you can do case m @ (_: A | _: B ) => which reads as "case m is something of type A or something of type B"
    * */
    case cmd @ Cmd(op) =>
      println(s"Counter receive ${cmd}")
      persist(Evt(op)) { evt =>
        updateState(evt)
      }

    case "print" =>
      println(s"The current state of counter is ${state}")
  }

}

