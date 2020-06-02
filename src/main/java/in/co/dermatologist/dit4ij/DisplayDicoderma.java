package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;
import io.scif.services.DatasetIOService;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import java.io.File;
import java.io.IOException;
import ij.ImagePlus;

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Display")
public class DisplayDicoderma implements Command {

    @Parameter
    private Dataset currentData;
    
    @Parameter
    private UIService uiService;

    @Parameter
	private LogService logService;



    @Override
    public void run() {
        try {
            ImagePlus imp = currentData.getCurrentImage();
            Dicoderma dicoderma = new Dicoderma();
            ij2.ImagePlus imp = WindowManager.getCurrentImage();
            int width = imp.getWidth();
            int  height = imp.getHeight();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadata(bi);
            String toDisplay = dicomSCModel.PatientID + " | " + dicomSCModel.PatientName + " | " + dicomSCModel.PatientSex;
            toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDate);
            toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyTime);
            toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDescription);
            uiService.showDialog(toDisplay);
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