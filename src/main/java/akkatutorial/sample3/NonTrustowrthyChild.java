package akkatutorial.sample3;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class NonTrustowrthyChild extends AbstractLoggingActor {

	public static class Command{}
	
	private long message = 0l;
	
	{
		receive(ReceiveBuilder.match(Command.class, this::onCommand).build());
	}
	
	private void onCommand(Command command){
		message++;
		
		if(message%2 == 0){
			throw new RuntimeException("Runtime thrown !!!");
		} else {
			log().info("got a command !!!");
		}
	}
	
	public static Props props() {
		return Props.create(NonTrustowrthyChild.class);
	}
	
}
