package in.co.dermatologist.dit4ij;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.Parameter;
import org.scijava.command.Command;

import java.io.File;
import java.io.IOException;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;

import net.imagej.ImageJ;
import io.scif.services.DatasetIOService;
import org.scijava.log.LogService;


@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Show")
public class ShowDicoderma implements Command {

    @Parameter
    private DatasetIOService datasetIOService;
    

    @Parameter
	private LogService logService;

    /** Location on disk of the input image. */
	@Parameter(label = "Image to show")
	private File imageFile;


    @Override
    public void run() {
        DicomSCModel dicomSCModel = new DicomSCModel();
        Dicoderma dicoderma = new Dicoderma();
        dicomSCModel.PatientName = "Mickey Mouse";
        try {
			image = datasetIOService.open(imageFile.getAbsolutePath());
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