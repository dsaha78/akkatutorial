package akkatutorial.sample2;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akkatutorial.StdIn;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class APP {

	static class Alarm extends AbstractLoggingActor {
		// protocol
		static class Activity {
		}

		static class Disable {
			private final String password;

			public Disable(String password) {
				super();
				this.password = password;
			}
		}

		static class Enable {
			private final String password;

			public Enable(String password) {
				super();
				this.password = password;
			}

		}

		private final String password;

		private final PartialFunction<Object, BoxedUnit> enabled;
		private final PartialFunction<Object, BoxedUnit> disabled;

		public Alarm(String passwd) {
			super();
			this.password = passwd;
			enabled = ReceiveBuilder.match(Activity.class, this::onActivity).match(Disable.class, this::onDisable)
					.build();

			disabled = ReceiveBuilder.match(Activity.class, this::onActivity).match(Enable.class, this::onEnable)
					.build();
			receive(disabled);
		}

		private void onEnable(Enable enable) {
			if (this.password.equals(enable.password)) {

				log().info("*Enable alarm disabled !!!");
				getContext().become(enabled);
			} else {
				log().warning("*Enable password not known !!!");
			}
		}

		private void onDisable(Disable disable) {
			if (this.password.equals(disable.password)) {
				log().info("*Disble alarm disabled !!!");
				getContext().become(disabled);
			} else {
				log().warning("*Disble password not known !!!");
			}
		}

		private void onActivity(Activity ignored) {
			log().warning("*Activity Warning Alarm !!!");
		}

		public static Props props(String passwd) {
			return Props.create(Alarm.class, passwd);
		}
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("AlarmSystem");
		ActorRef alarm = system.actorOf(Alarm.props("cat"), "alarm");
		alarm.tell(new Alarm.Activity(), ActorRef.noSender());
		alarm.tell(new Alarm.Enable("dogs"), ActorRef.noSender());
		alarm.tell(new Alarm.Enable("cat"), ActorRef.noSender());
		alarm.tell(new Alarm.Activity(), ActorRef.noSender());
		alarm.tell(new Alarm.Disable("dogs"), ActorRef.noSender());
		alarm.tell(new Alarm.Disable("cat"), ActorRef.noSender());
		alarm.tell(new Alarm.Activity(), ActorRef.noSender());
		
		StdIn.readLine();
		
		system.terminate();

	}
}
