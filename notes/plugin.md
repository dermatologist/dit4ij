
## Extend service or command

```
@Plugin(priority=224)
public class SpecialService implements Service { }

```

## services are autowired as below:

```
// This @Parameter notation is 'asking' the Context
  // for an instance of LogService.
  @Parameter
  private LogService logService;

```

## You can manually inject too (If absolutely needed)
```
// Manually create a plugin instance
    // It is not connected to a Context yet
    MyPlugin plugin = new MyPlugin();
 
    // Inject the plugin instance with our Context,
    // so the logService field of the plugin will be
    // populated.
    context.inject(plugin);
 
    // Now that our plugin is injected, we can use
    // it with the knowledge that its parameters
    // have been populated
    plugin.log("Success!");
```

## Commands

When writing Commands you will often declare @Parameters on fields that can not be resolved automatically by the Context—for example, numeric values or file paths. Instead of being instantiated at Context startup as a Service would be, Commands are created and executed on demand.

When a Command is executed, it goes through a series of pre-processing steps to populate its @Parameters using its associated Context. If any parameters are left unresolved and a UI is available, the framework will automatically build and display an appropriate dialog to get user input. In this way, input harvesting is decoupled from functional operation—allowing developers to focus on what's really important without repetition of code. This also means that Commands can typically run headlessly without any extra development effort.

* extend AbstractPreprocessorPlugin
* public interface Converter<I, O> extends HandlerPlugin<ConversionRequest>

## public interface DialogListener

PlugIns or PlugInFilters that want to listen to changes in a GenericDialog without adding listeners for each dialog field should implementthis method. The dialogItemChanged method of a PlugIn or PlugInFilter can and should read the various dialog items by the appropriate GenericDialog methods like getNextNumber (items that are not read in the dialogItemChanged method will not be recorded by the Macro recorder). The PlugIn or PlugInFilter has to be added to the GenericDialog by its addDialogListener method: gd.addDialogListener(this);

GenericDialog gd = new GenericDialog("Select Dicoderma Files");


## public interface PlugIn

Plugins that acquire images or display windows should implement this interface. Plugins that process images should implement the PlugInFilter interface.

## public interface PlugInFilter

ImageJ plugins that process an image should implement this interface. For filters that have a dialog asking for options or parameters as well as for filters that have a progress bar and process stacks the ExtendedPlugInFilter interface is recommended.

* DirectoryChooser dc = new DirectoryChooser("Directory of Dicoderma Files");
* IJ.showStatus("Exploding Dicoderma files...");
* IJ.showProgress(((float)i++)/files.length);

## Annotations

* @Plugin(type = Command.class, menuPath = "Plugins>Gauss Filtering")
* @Plugin(type = Command.class, headless = true, menuPath = "Help>Hello, World!")

## Parameters example

```
@Parameter
	private DatasetService datasetService;

	@Parameter(min = "1")
	private int width = 512;

	@Parameter(min = "1")
	private int height = 512;

	@Parameter(type = ItemIO.OUTPUT)
	private Dataset dataset;
```

## Creating OP
```
	@Plugin(type = Op.class, name = "narf")
	public static class Narf extends AbstractOp {


  @Plugin(type = Op.class, name = "blobs")
public class RandomBlobs<T extends RealType<T>> extends AbstractOp {
```