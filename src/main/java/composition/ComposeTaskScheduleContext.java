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

	interface TaskContextAnnotation {

	}

	interface GlobalTask extends TaskContextAnnotation {

	}

	interface UserTask extends TaskContextAnnotation {

	}

	interface TaskScheduleAnnotation {

	}

	interface RegularTask extends TaskScheduleAnnotation {

	}

	interface RateLimitedTask extends TaskScheduleAnnotation {

	}

	static class MakeTheDomainsGreater implements AsyncTask, GlobalTask {
		@Override
		public void exec() {
		}
	}

	static class SyncUserDrive implements AsyncTask, UserTask, RegularTask {
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

		// Must specify the user; schedule policy choice hard coded, but may need to define schedule parameters
		TaskExec myRegularUserTask = TaskExec.builder()
				.asyncTask(new SyncUserDrive())
				.context(withUser("john@doe.com"))
				.build();

		// No TaskScheduleAnnotation, so must specify a schedule policy
		TaskExec myGlobalTaskRateLimited = TaskExec.builder()
				.asyncTask(new MakeTheDomainsGreater())
				.schedulePolicy(RateLimited)
				.build();

		// No TaskScheduleAnnotation, so can specify any schedule policy
		TaskExec myGlobalTaskRegular = TaskExec.builder()
				.asyncTask(new MakeTheDomainsGreater())
				.schedulePolicy(Regular)
				.build();

		// then post the TaskExec to some executor that will schedule it
		// - SchedulePolicy may be just an enum handled by a schedule dispatcher or may contain the code that schedules the run of the asyncTask
		// - once it is ready for execution, some manager will create the context and call asyncTask.exec()
	}
}
