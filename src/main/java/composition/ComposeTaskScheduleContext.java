package composition;

import static composition.ComposeTaskScheduleContext.Context.withUser;
import static composition.ComposeTaskScheduleContext.SchedulePolicy.RateLimited;
import static composition.ComposeTaskScheduleContext.SchedulePolicy.Regular;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class ComposeTaskScheduleContext {

	interface AsyncTask {
		void exec();
	}

	static class MakeTheDomainsGreater implements AsyncTask {
		@Override
		public void exec() {
		}
	}

	static class SyncUserDrive implements AsyncTask {
		@Override
		public void exec() {
		}
	}

	interface SchedulePolicy {
		static SchedulePolicy RateLimited = new SchedulePolicy() {
		};
		static SchedulePolicy Regular = new SchedulePolicy() {
		};
	}

	@Value
	static class Context {
		String namespace;
		String user;

		static Context withUser(String user) {
			return new Context("current", user);
		}

		static Context global() {
			return new Context("global", "system");
		}
	}

	@Builder
	@Value
	static class TaskExec {
		@NonNull
		AsyncTask asyncTask;
		Context context;
		SchedulePolicy schedulePolicy;
	}

	public static void main(String[] args) {

		TaskExec myRateLimitedUserTask = TaskExec.builder()
				.asyncTask(new SyncUserDrive())
				.context(withUser("john@doe.com"))
				.schedulePolicy(RateLimited)
				.build();

		TaskExec myRegularGlobalTask = TaskExec.builder()
				.asyncTask(new MakeTheDomainsGreater())
				.context(Context.global())
				.schedulePolicy(Regular)
				.build();

		// then post the TaskExec to some executor that will schedule it
		// - SchedulePolicy may be just an enum handled by a schedule dispatcher or may contain the code that schedules the run of the asyncTask
		// - once it is ready for execution, some manager will create the context and call asyncTask.exec()
	}
}
