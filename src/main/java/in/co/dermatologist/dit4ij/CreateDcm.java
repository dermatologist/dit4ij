package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;
import in.co.dermatologist.dicoderma.DicodermaJpg2Dcm;

import in.co.dermatologist.dicoderma.GenderEnum;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.type.numeric.RealType;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Create DCM")
public class CreateDcm implements Command {

    @Parameter
    private Dataset currentData;
    
    @Parameter
    private UIService uiService;

    @Parameter
    private LogService logService;
    

    @Override
    public void run() {
        try{
            File currentFile = new File(currentData.getSource());

            String currentFileName = currentData.getSource();
            int lastDot = currentFileName.lastIndexOf('.');
            String newFileName = currentFileName.substring(0,lastDot) + ".dcm";
            File newFile = new File(newFileName);


            Dicoderma dicoderma = new Dicoderma();
            DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadataFromFile(currentFile);
            String[] dicodermaMetadataAsArray = dicoderma.getModelAsStringArray(dicomSCModel);
            DicodermaJpg2Dcm dicodermaJpg2Dcm = new DicodermaJpg2Dcm();
            dicodermaJpg2Dcm.convertJpgToDcm(currentFile, newFile, dicodermaMetadataAsArray);

            uiService.showDialog(newFileName + " created.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();            
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