package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import java.io.File;

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Display")
public class DisplayDicoderma<T extends RealType<T>> implements Command {

    @Parameter
    private Dataset currentData;
    
    @Parameter
    private UIService uiService;

    @Parameter
	private LogService logService;



    @Override
    public void run() {
        File currentFile = new File(currentData.getSource());
        Dicoderma dicoderma = new Dicoderma();
        DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadataFromFile(currentFile);
        String toDisplay = dicomSCModel.PatientID + " | " + dicomSCModel.PatientName + " | " + dicomSCModel.PatientSex;
        toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDate);
        toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyTime);
        toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDescription);
        uiService.showDialog(toDisplay);
    }

    public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Launch the "OpenImage" command.
		ij.command().run(DisplayDicoderma.class, true);
	}

}