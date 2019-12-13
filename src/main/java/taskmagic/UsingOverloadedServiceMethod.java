package taskmagic;

import lombok.Value;

public class UsingOverloadedServiceMethod {
	
	interface AsyncTask {
	}
	
	interface ExecutionModeSelector<Starter extends TaskStarter> {
		int getExecutionMode();
		
		Starter createStarter(Context context);
	}
	
	interface RateLimitedTask extends AsyncTask, ExecutionModeSelector<RateLimitedTaskStarter> {
		@Override
		default int getExecutionMode() {
			return 0;
		}
		
		@Override
		default RateLimitedTaskStarter createStarter(Context context) {
			// TODO create starter
			return null;
		}
	}
	
	interface TaskStarter {
		
	}
	
	interface RegularTaskStarter extends TaskStarter {
		void runRegular();
	}
	
	interface RateLimitedTaskStarter extends TaskStarter {
		void runRateLimited();
	}
	
	interface RegularAsyncTask extends AsyncTask, ExecutionModeSelector<RegularTaskStarter> {
		@Override
		default int getExecutionMode() {
			return 1;
		}
		
		@Override
		default RegularTaskStarter createStarter(Context context) {
			// todo create starter
			return null;
		}
	}
	
	@Value
	static class TaskWithContext<T extends AsyncTask, Type extends ExecutionModeSelector> {
		T task;
		Context context;
	}
	
	@Value
	static class Context {
		String namespace;
		String user;
	}
	
	/**
	 * Task class declaration is simpler here
	 */
	static class MyRegularGlobalTask implements RegularAsyncTask, GlobalTask {
		
	}
	
	static class MyRateLimitedUserTask implements RateLimitedTask, UserTask {
		
	}
	
	interface GlobalTask {
	}
	
	interface UserTask {
	}
	
	/**
	 * This solution moves the context creation from thein interfaces to overloaded methos here.
	 * If this solution needs to be scaled for more dimensions, we'll have more nasty method overloads here.
	 */
	public static <Starter extends TaskStarter, Selector extends ExecutionModeSelector<Starter>, T extends GlobalTask & ExecutionModeSelector<Starter>> Starter assignContext(T task) {
		return task.createStarter(new Context("global", "system"));
	}
	
	public static <Starter extends TaskStarter, Selector extends ExecutionModeSelector<Starter>, T extends UserTask & ExecutionModeSelector<Starter>> Starter assignContext(T task, String user) {
		return task.createStarter(new Context("current", user));
	}
	
	/**
	 * Task enqueuing is more verbose here
	 */
	public static void main(String[] args) {
		assignContext(new MyRateLimitedUserTask(), "some-user").runRateLimited();
		assignContext(new MyRegularGlobalTask()).runRegular();
	}
	
}
