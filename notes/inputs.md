* https://github.com/imagej/tutorials/blob/master/maven-projects/widget-demo/src/main/java/WidgetDemo.java

```
// -- Needed services --

	// For opening and saving images.
	@Parameter
	private DatasetIOService datasetIOService;

	// For scaling the image.
	@Parameter
	private OpService ops;

	// For logging errors.
	@Parameter
	private LogService log;

	// -- Inputs to the command --

	/** Location on disk of the input image. */
	@Parameter(label = "Image to load")
	private File inputImage;

	/** Factor by which to scale the image. */
	@Parameter(label = "Scale factor")
	private double factor = 2;

	@Parameter(label = "Scale method", //
		choices = { LANCZOS, N_LINEAR, NEAREST_NEIGHBOR, FLOOR })
	private String method = LANCZOS;

	/** Location on disk to save the processed image. */
	@Parameter(label = "Image to save")
	private File outputImage;

```