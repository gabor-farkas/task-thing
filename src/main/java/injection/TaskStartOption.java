package injection;

import java.time.Duration;

import lombok.Value;

// this solution hides other stuff otherwise available in AOTaskOptions that generally shouldn't be specialized by callers
interface TaskStartOption {
	
	@Value
	static class Delay implements TaskStartOption {
		Duration delay;
	}
	
}