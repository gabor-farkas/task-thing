package generichell;

import lombok.Value;

public class UsingGenericChaining {
	
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
	 * Task class declaration looks unnecessarily verbose here.
	 * We have to define the 'Starter' type argument so that 'withContext' can return the proper starter
	 * We define the self as the second type argument so that the type system ensures that you specified the starter corresponding to the task type
	 * 
	 * If this solutions needs to be scaled with a new dimensions, we'll have more type arguments chained in these declarations.
	 */
	static class MyRegularGlobalTask implements RegularAsyncTask, GlobalTask<RegularTaskStarter, MyRegularGlobalTask> {
		
	}
	
	static class MyRateLimitedUserTask implements RateLimitedTask, UserTask<RateLimitedTaskStarter, MyRateLimitedUserTask> {
		
	}
	
	interface GlobalTask<Starter extends TaskStarter, Selector extends ExecutionModeSelector<Starter>> extends ExecutionModeSelector<Starter> {
		default Starter withContext() {
			return createStarter(new Context("global", "system"));
		}
	}
	
	interface UserTask<Starter extends TaskStarter, Selector extends ExecutionModeSelector<Starter>> extends ExecutionModeSelector<Starter> {
		default Starter withUser(String user) {
			return createStarter(new Context("current", user));
		}
	}
	
	/**
	 * Tasks starting looks more convenient here
	 */
	public static void main(String[] args) {
		new MyRateLimitedUserTask().withUser("some-user").runRateLimited();
		new MyRegularGlobalTask().withContext().runRegular();
	}
	
}
