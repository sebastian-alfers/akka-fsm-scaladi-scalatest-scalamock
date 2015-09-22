# akka-fsm-scaldi-scalatest-scalamock
NPE in scalamock while scheduling message within an actor. Sample project for discussion: https://github.com/paulbutcher/ScalaMock/issues/25#issuecomment-142088237

# tools
- akka FSM http://doc.akka.io/docs/akka/snapshot/scala/fsm.html
- http://scaldi.org/
- http://scalatest.org
- http://scalamock.org/

# reproduce
```bash
sbt ~test
```

# exception
When an FSM actor schedules messages on his own, the following exception is thrown:

```bash
[ERROR] [09/22/2015 10:47:23.750] [TestSystem-akka.actor.default-dispatcher-3] [akka://TestSystem/user/$$a] null
java.lang.NullPointerException
	at org.scalamock.function.FakeFunction.handle(FakeFunction.scala:41)
	at org.scalamock.function.FakeFunction0.apply(FakeFunction.scala:51)
	at ExampleSuite$$anonfun$1$$anonfun$apply$mcV$sp$1$$anon$1.call(ExampleSuite.scala:28)
	at com.test.Example$$anonfun$3.applyOrElse(Example.scala:68)
	at com.test.Example$$anonfun$3.applyOrElse(Example.scala:66)
	at scala.runtime.AbstractPartialFunction.apply(AbstractPartialFunction.scala:36)
	at akka.actor.FSM$class.processEvent(FSM.scala:604)
	at com.test.Example.akka$actor$LoggingFSM$$super$processEvent(Example.scala:28)
	at akka.actor.LoggingFSM$class.processEvent(FSM.scala:734)
	at com.test.Example.processEvent(Example.scala:28)
	at akka.actor.FSM$class.akka$actor$FSM$$processMsg(FSM.scala:598)
	at akka.actor.FSM$$anonfun$receive$1.applyOrElse(FSM.scala:592)
	at akka.actor.Actor$class.aroundReceive(Actor.scala:467)
	at com.test.Example.aroundReceive(Example.scala:28)
	at akka.actor.ActorCell.receiveMessage(ActorCell.scala:516)
	at akka.actor.ActorCell.invoke(ActorCell.scala:487)
	at akka.testkit.CallingThreadDispatcher.process$1(CallingThreadDispatcher.scala:251)
	at akka.testkit.CallingThreadDispatcher.runQueue(CallingThreadDispatcher.scala:284)
	at akka.testkit.CallingThreadDispatcher.dispatch(CallingThreadDispatcher.scala:208)
	at akka.actor.dungeon.Dispatch$class.sendMessage(Dispatch.scala:123)
	at akka.actor.ActorCell.sendMessage(ActorCell.scala:369)
	at akka.actor.Cell$class.sendMessage(ActorCell.scala:290)
	at akka.actor.ActorCell.sendMessage(ActorCell.scala:369)
	at akka.actor.LocalActorRef.$bang(ActorRef.scala:384)
	at akka.actor.Scheduler$$anon$6.run(Scheduler.scala:106)
	at akka.dispatch.TaskInvocation.run(AbstractDispatcher.scala:40)
	at akka.testkit.CallingThreadDispatcher.executeTask(CallingThreadDispatcher.scala:213)
	at akka.dispatch.MessageDispatcher.unbatchedExecute(AbstractDispatcher.scala:144)
	at akka.dispatch.BatchingExecutor$class.execute(BatchingExecutor.scala:123)
	at akka.dispatch.MessageDispatcher.execute(AbstractDispatcher.scala:84)
	at akka.actor.LightArrayRevolverScheduler$TaskHolder.executeTask(Scheduler.scala:467)
	at akka.actor.LightArrayRevolverScheduler$$anon$8.executeBucket$1(Scheduler.scala:419)
	at akka.actor.LightArrayRevolverScheduler$$anon$8.nextTick(Scheduler.scala:423)
	at akka.actor.LightArrayRevolverScheduler$$anon$8.run(Scheduler.scala:375)
	at java.lang.Thread.run(Thread.java:745)
```
