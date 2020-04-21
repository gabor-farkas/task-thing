package injection;

import java.util.Set;

import lombok.Value;

@Value class TaskStartOptions<T extends AsyncTask> {
	TaskWithContextSpecifier taskWithContextSpecifier;
	Set<Object> additonalContexts;
	Set<TaskStartOption> options;
	
	public static <X extends AsyncTask> TaskStartOptions<X> of(X task, CallerContextSpecifier callerContextSpecifier, TaskStartOption... options) {
		// construct
		return null;
	}
	
	public static <X extends AsyncTask> TaskStartOptions<X> of(ConvertibleToTaskWithContextSpecifier task, TaskStartOption... options) {
		// construct
		return null;
	}
	
}