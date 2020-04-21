package injection;

import java.util.function.Consumer;

import lombok.Value;

@Value
public class MyTask implements CurrentUserTask {
	private String documentId;
	
	// or we can just keep it at the same class - null fields won't get serialized to json
	class Executor implements Consumer<MyTask> {
		@Inject
		private MyService myService;
		
		@Override
		public void accept(MyTask t) {
			myService.myOperationSync(t.getDocumentId());
		}
	}
	
	@Override
	public String getQueue() {
		return "default";
	}
}