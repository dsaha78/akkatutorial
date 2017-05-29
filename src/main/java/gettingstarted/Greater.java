package gettingstarted;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import gettingstarted.Printer.Greeting;

public class Greater extends AbstractActor {

	String message;
	ActorRef printActor;
	String greeting;

	/**
	 * this one to create a Greater Actor. or the constructor.
	 * 
	 * @param message
	 * @param actorRef
	 */
	public Greater(String message, ActorRef actorRef) {
		this.message = message;
		this.printActor = actorRef;
	}

	/**
	 * this will be used to create an Actor from othe classes.
	 * 
	 * @param message
	 * @param actorRef
	 * @return
	 */
	static public Props props(String message, ActorRef actorRef) {
		return Props.create(Greater.class, () -> new Greater(message, actorRef));
	}

	// Protocols
	// These are the messages.
	/**
	 * This is a message. this will decide whom to greet
	 */
	static public class WhoToGreet {
		public final String who;

		public WhoToGreet(String who) {
			this.who = who;
		}
	}

	/**
	 * this one calls the printActor for printing the greating
	 */
	static public class Greet {
		public Greet() {
		}
	}
	
	/**
	 * this is the action of the messages.
	 */
	public Receive createReceive(){
		
		return receiveBuilder().match(WhoToGreet.class, wtg -> { this.greeting = message+","+wtg.who;})
				.match(Greater.class, g-> {printActor.tell(new Greeting(greeting), getSelf());})
				.build();
		
		
		
	}
}
