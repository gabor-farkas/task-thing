package injection;

import java.time.Duration;

import injection.TaskStartOption.Delay;

public class MyService {
	
	@Inject
	private QueueService queueService;
	
	@Inject
	private SecurityContextCreationService securityContextCreationService;
	
	public void myOperationAsync(String documentId) {
		queueService.startTask(new MyTask(documentId));
		// applying allowed start options
		queueService.startTask(new MyTask(documentId), new Delay(Duration.ofMinutes(1)));
		// arbitary caller context
		queueService.startTask(TaskStartOptions.of(new MyTask(documentId), new CallerContextSpecifier.GivenContext(securityContextCreationService.createCurrentUserContext()), new Delay(Duration.ofMinutes(1))));
	}
	
	public void myOperationSync(String documentId) {
		System.out.println("Doing operation on: " + documentId);
	}
}