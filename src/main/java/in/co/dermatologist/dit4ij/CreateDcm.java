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


    public static void main(final String... args) {
        // Launch ImageJ as usual.
        final ImageJ ij = new ImageJ();
        ij.launch(args);

        // Launch the "OpenImage" command.
        ij.command().run(DisplayDicoderma.class, true);
    }

    @Override
    public void run() {
        try {
            File currentFile = new File(currentData.getSource());

            String currentFileName = currentData.getSource();
            int lastDot = currentFileName.lastIndexOf('.');
            String newFileName = currentFileName.substring(0, lastDot) + ".dcm";
            File newFile = new File(newFileName);


            Dicoderma dicoderma = new Dicoderma();
            DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadataFromFile(currentFile);
            String[] dicodermaMetadataAsArray = dicoderma.getModelAsStringArray(dicomSCModel);

            final List<String> list = new ArrayList<>();
            for (String s : dicodermaMetadataAsArray) {
                logService.info("Processing" + s);
                if (!s.trim().endsWith("=") && !s.trim().endsWith("null"))
                    list.add(s);
                logService.info("Adding" + s);
            }


//            Collections.addAll(list, dicodermaMetadataAsArray);
//            list.remove("TypeOfPatientID=");
            dicodermaMetadataAsArray = list.toArray(new String[0]);
            for (String s : dicodermaMetadataAsArray) {
                logService.info("Included" + s);
            }

            DitJpg2Dcm ditJpg2Dcm = new DitJpg2Dcm();
            ditJpg2Dcm.convertJpgToDcm(currentFile, newFile, dicodermaMetadataAsArray);

            uiService.showDialog(newFileName + " created.");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

}