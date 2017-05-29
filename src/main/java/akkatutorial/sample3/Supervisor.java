package akkatutorial.sample3;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.AllForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.ReceiveBuilder;
import akkatutorial.sample3.NonTrustowrthyChild;

public class Supervisor extends AbstractLoggingActor {

	{
		final ActorRef child = getContext().actorOf(NonTrustowrthyChild.props(), "child");
		receive(ReceiveBuilder.matchAny(any -> child.forward(any, getContext())).build());
	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		// TODO Auto-generated method stub
		return new AllForOneStrategy(){
			10,
			Duration.create("10 Seconds"),
			DeciderBuilder.match(RuntimeException.class,ex-> restart()).build());
		}
	}
	
	
}