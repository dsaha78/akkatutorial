package gettingstarted;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import gettingstarted.Greater.Greet;
import gettingstarted.Greater.WhoToGreet;

public class AkkaQuickStart {
	
	
	public static void main(String[] args) throws IOException {
		
		final ActorSystem system = ActorSystem.create("ActorSystem");
		
		final ActorRef printerActor = system.actorOf(Printer.props());
		final ActorRef greaterActor = system.actorOf(Greater.props("Hello There !!!", printerActor));
		greaterActor.tell(new WhoToGreet("Bangalore"), ActorRef.noSender());
		greaterActor.tell(new Greet(), ActorRef.noSender());
		
		System.out.println(">>> Press ENTER to exit <<<");
	      System.in.read();
	}

}

