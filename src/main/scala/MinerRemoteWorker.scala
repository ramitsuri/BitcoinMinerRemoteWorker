/**
 * Created by ramit on 9/12/2015.
 */
import java.security.MessageDigest
import akka.actor.{ActorSelection, Props, ActorSystem, Actor}

case class Hash(stringToHash: String, numberOfZeros: Int)
case class Start(address: String)
case class RemoteConnect()
case class Done(hashedString: String, stringHashed: String)

class MinerRemoteWorker extends Actor{
  var remote : ActorSelection = null
  def receive = {

    case Start(address) => {
      remote = context.actorSelection("akka.tcp://MinerSystem@"+address+"/user/ParentActor")
      println("started")
      remote ! RemoteConnect()
    }

    case Hash(stringToHash, numberOfZeros) => {
      val messageDigest = MessageDigest.getInstance("SHA-256")
      messageDigest.update(stringToHash.getBytes("UTF-8"))
      var hashedString = messageDigest.digest().map("%02X" format _).mkString
      var zeroString=""
      for(i<-1 to numberOfZeros){
        zeroString +="0"
      }
      if(hashedString.startsWith(zeroString)){
        hashedString+=" from Remote "
        sender() ! Done(hashedString, stringToHash)
      }
    }
  }
}

object RemoteWorker extends App{
  override def main(args: Array[String]) {
    val system = ActorSystem("RemoteWorkerSystem")
    val minerRemoteWorkerActor = system.actorOf(Props[MinerRemoteWorker], name = "MinerRemoteWorker")
    minerRemoteWorkerActor ! Start(args(0))
  }
}
