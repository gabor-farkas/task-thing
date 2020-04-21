package injection;

import lombok.Value;

/**
 * Corresponds to 'TaskWithContext' in the current codebase,
 * but doesn't hold the actual caller context, only the
 * 'instructions' how to build it - so it's rather
 * a static method argument.
 */
@Value
public class TaskWithContextSpecifier {
	
	AsyncTask task;
	
	CallerContextSpecifier callerContextSpecifier;
	
}
