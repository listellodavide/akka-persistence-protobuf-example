package io.adiwave.akka.persistenceprotobuf.persistence

object CounterAggregate {
    sealed trait OperationEvt {
      val count: Int   // the operation can be only increments / decrements operations
    }

    case class Increment(override val count: Int) extends  OperationEvt
    case class Decrement(override val count: Int) extends OperationEvt

    case class Cmd(op: OperationEvt) // this is a command

    case class Evt(op: OperationEvt) // this is an Event

    case class State(count: Int) // this is a State
  }
