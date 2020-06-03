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
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Search")
public class SearchDicoderma<T extends RealType<T>> implements Command {

    @Parameter(label = "Gender", //
            choices = {"M", "F"})
    private final String PatientSex = "M";
    @Parameter(label = "Date")
    private final String StudyDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    @Parameter(label = "Time")
    private final String StudyTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
    @Parameter
    private Dataset currentData;
    @Parameter
    private UIService uiService;
    @Parameter
    private LogService logService;
    /**
     * Location on disk of the input image.
     */
    @Parameter(label = "Path to search")
    private Path pathToSearch;
    @Parameter(label = "Patient ID")
    private String PatientID;
    @Parameter(label = "Patient Name")
    private String PatientName;
    @Parameter(label = "Tag")
    private String StudyDescription;

    public static void main(final String... args) {
        // Launch ImageJ as usual.
        final ImageJ ij = new ImageJ();
        ij.launch(args);

        // Launch the "OpenImage" command.
        ij.command().run(DisplayDicoderma.class, true);
    }

    @Override
    public void run() {
        File dir = new File(pathToSearch.toString());
        File[] directoryListing = dir.listFiles();
        Dicoderma dicoderma = new Dicoderma();
        DicomSCModel dicomSCModel = new DicomSCModel();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Do something with child
                dicomSCModel = dicoderma.getDicodermaMetadataFromFile(child);
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
            uiService.showDialog(pathToSearch.toString() + " is not valid.");

        }
    }

}