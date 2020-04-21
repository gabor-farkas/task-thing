package injection;

import java.util.HashSet;

/**
 * QueueService is responsible for creating TaskWithContext
 * and the conversion to JsonTask / AOTaskOptions as required
 */
class QueueService {
	void startTask(TaskStartOptions<? extends AsyncTask> options) {
		// we can construct the actual caller context based on the specifier
		// We can either use an if structure based on the given parameter,
		// Or we can add methods to the context specifier themselves that receive the required services
	}
	
	void startTask(ConvertibleToTaskWithContextSpecifier task, TaskStartOption... options) {
		startTask(new TaskStartOptions<AsyncTask>(task.toTaskWithContextSpecifier(), null, new HashSet<>(/* options */)));
	}
	
}