package gettingstarted;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class Printer extends AbstractLoggingActor {

	static public Props props() {
		return Props.create(Printer.class, () -> new Printer());
	}

	public Printer() {
	}

	static public class Greeting {
		String message;

		public Greeting(String message) {

			this.message = message;
		}
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Greeting.class, x -> {
			log().info(x.message);
		}).build();
	}

}
