package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imglib2.display.screenimage.awt.ARGBScreenImage;
import net.imglib2.type.numeric.RealType;
import org.apache.commons.imaging.ImageReadException;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
        try {
            // BufferedImageImg bi = new BufferedImageImg();
            //ImgPlus imp = currentData.getImgPlus();

            //final Img<T> image = (Img<T>)currentData.getImgPlus();


            ImgPlus<? extends RealType<?>> img = currentData.getImgPlus();

            ARGBScreenImage argbScreenImage = (ARGBScreenImage) img.firstElement();

//            ImgFactory<T> fac = image.factory();
//            BufferedImageImg bi = (BufferedImageImg) fac.create();
//

            Dicoderma dicoderma = new Dicoderma();

            BufferedImage bim = argbScreenImage.image();
            DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadata(bim);
            String toDisplay = dicomSCModel.PatientID + " | " + dicomSCModel.PatientName + " | " + dicomSCModel.PatientSex;
            toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDate);
            toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyTime);
            toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDescription);
            uiService.showDialog(toDisplay);
        } catch (final IOException | ImageReadException exc) {
            // Use the LogService to report the error.
            logService.error(exc);
        }

    }

    public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = new ImageJ();
		ij.launch(args);

		// Launch the "OpenImage" command.
		ij.command().run(DisplayDicoderma.class, true);
	}

}