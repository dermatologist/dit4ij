package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            String newFileName = currentFileName.substring(0, lastDot) + ".dcm";
            File newFile = new File(newFileName);


            Dicoderma dicoderma = new Dicoderma();
            DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadataFromFile(currentFile);
            String[] dicodermaMetadataAsArray = dicoderma.getModelAsStringArray(dicomSCModel);

            final List<String> list = new ArrayList<String>();
            for (String s : dicodermaMetadataAsArray) {
                if (s.trim().endsWith("="))
                    break;
                if (s.trim().endsWith("null"))
                    break;
                list.add(s);
            }

            dicodermaMetadataAsArray = list.toArray(new String[list.size()]);
            for (String s : dicodermaMetadataAsArray) {
                logService.info(s);
            }

            DitJpg2Dcm ditJpg2Dcm = new DitJpg2Dcm();
            ditJpg2Dcm.convertJpgToDcm(currentFile, newFile, dicodermaMetadataAsArray);

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