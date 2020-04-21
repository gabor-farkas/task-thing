package injection;

import lombok.Value;

public interface CallerContextSpecifier {
	
	public class CurrentUser implements CallerContextSpecifier {
		
	}
	
	@Value
	public class SystemTaskInGivenDomain implements CallerContextSpecifier {
		String domain;
	}
	
	/**
	 * Let's you assign an arbitrary caller context to the task
	 */
	@Value
	public class GivenContext implements CallerContextSpecifier {
		CallerContext callerContext;
	}
}
