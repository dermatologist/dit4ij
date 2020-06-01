```
	/*
	 * This first @Parameter is a core ImageJ service (and thus @Plugin). The
	 * context will provide it automatically when this command is created.
	 */

	@Parameter
	private DatasetIOService datasetIOService;

	/*
	 * In this command, we will be using functions that can throw exceptions.
	 * Best practice is to log these exceptions to let the user know what went
	 * wrong. By using the LogService to do this, we let the framework decide
	 * the best place to display thrown errors.
	 */
	@Parameter
	private LogService logService;

	/*
	 * We need to know what image to open. So, the framework will ask the user
	 * via the active user interface to select a file to open. This command is
	 * "UI agnostic": it does not need to know the specific user interface
	 * currently active.
	 */
	@Parameter
	private File imageFile;

	/*
	 * This command will produce an image that will automatically be shown by
	 * the framework. Again, this command is "UI agnostic": how the image is
	 * shown is not specified here.
	 */
	@Parameter(type = ItemIO.OUTPUT)
	private Dataset image;

	/*
	 * The run() method is where we do the actual 'work' of the command. In this
	 * case, it is fairly trivial because we are simply calling ImageJ Services.
	 */
	@Override
	public void run() {
		try {
			image = datasetIOService.open(imageFile.getAbsolutePath());
		}
		catch (final IOException exc) {
			// Use the LogService to report the error.
			logService.error(exc);
		}
	}
```