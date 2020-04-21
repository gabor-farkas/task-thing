package injection;

interface SecurityContextCreationService {
	CallerContext createCurrentUserContext();
}