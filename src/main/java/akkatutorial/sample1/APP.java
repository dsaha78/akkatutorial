package akkatutorial.sample1;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class APP {

	static class Counrter extends AbstractLoggingActor {
		static class Message {
		}

		private int counter = 0;

		private void onMessage(Message message) {

			counter++;
			log().info("incereased the counter " + counter);
		}

		{
			receive(ReceiveBuilder
					.match(Message.class, this::onMessage)
					.build()
			);
		}
		
		public static Props props(){
			return Props.create(Counrter.class);
		}
	}

	public static void main(String[] args) {
		System.out.println("Ener to Terminate !!");
		// StdIn.re
		
		ActorSystem system = ActorSystem.create("System");
		final ActorRef ref = system.actorOf(Counrter.props(),"actor");
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int i = 0; i < 5; i++) {
						ref.tell(new Counrter.Message(), ActorRef.noSender());
					}
					
				}
			}).start();
		}
		
		
		
		//system.terminate();
	}
}
