import akka.actor.ActorSystem
import akka.testkit.{TestFSMRef, ImplicitSender, TestKit}
import com.test.Example.{Pong, Play, Ping}
import com.test.{Example, Service}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import scaldi.Module

/*
class FileMock extends Service{
  override val host = "test"
  override val port = 123
  def call = "mock"
}
*/

class ExampleSuite(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender with WordSpecLike with ScalaFutures
  with Matchers with BeforeAndAfterAll with MockFactory with Eventually {

  def this() = this(ActorSystem("TestSystem"))

  "this" should {
    "not throw an exception" in {

      val fileMock = mock[Service]
      (fileMock.call _).expects().returning("mocked").repeat(0 until 7)

      // wire up params for production
      class MockDependencies extends Module {
        val service = fileMock
        binding identifiedBy "fileService" to service
      }

      val inj = new MockDependencies

      val actor = TestFSMRef(new Example("test", inj))

      actor.stateName should be(Ping)
      actor ! Play
      actor.stateName should be(Pong)
      /*
      val storageMock = getOffsetStorageMock(isAlreadyInitialized = false, initializeOffsetSuccess = false)
      val fsm = getFsm(storageMock)

      fsm.stateData should be(NoOffset)
      */
    }
  }

}
