package in.co.dermatologist.dit4ij;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.Parameter;
import org.scijava.command.Command;

import java.io.File;
import java.io.IOException;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import io.scif.services.DatasetIOService;
import org.scijava.log.LogService;
import org.scijava.ui.UIService;

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Show")
public class ShowDicoderma implements Command {

    @Parameter
    private DatasetIOService datasetIOService;
    
    @Parameter
    private UIService uiService;

    @Parameter
	private LogService logService;

    /** Location on disk of the input image. */
	@Parameter(label = "Image to show")
	private File imageFile;

    	/*
	 * This command will produce an image that will automatically be shown by
	 * the framework. Again, this command is "UI agnostic": how the image is
	 * shown is not specified here.
	 */
	@Parameter(type = ItemIO.OUTPUT)
	private Dataset image;

    @Override
    public void run() {
        Dicoderma dicoderma = new Dicoderma();
        DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadataFromFile(imageFile);
        try {
            image = datasetIOService.open(imageFile.getAbsolutePath());
            uiService.showDialog(dicomSCModel.toString());
		}
		catch (final IOException exc) {
			// Use the LogService to report the error.
			logService.error(exc);
		}
    }

    public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Launch the "OpenImage" command.
		ij.command().run(ShowDicoderma.class, true);
	}

}