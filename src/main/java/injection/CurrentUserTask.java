package injection;

interface CurrentUserTask extends AsyncTask, ConvertibleToTaskWithContextSpecifier {
	
	@Override
	default TaskWithContextSpecifier toTaskWithContextSpecifier() {
		return new TaskWithContextSpecifier(this, new CallerContextSpecifier.CurrentUser());
	}
}