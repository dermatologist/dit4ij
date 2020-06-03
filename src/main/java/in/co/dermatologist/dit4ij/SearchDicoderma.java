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

import org.scijava.ItemIO;
import io.scif.services.DatasetIOService;

import java.io.File;
import java.io.IOException;

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Search")
public class SearchDicoderma<T extends RealType<T>> implements Command {


	@Parameter(type = ItemIO.OUTPUT)
    private Dataset image;

    /**
     * Location on disk of the input image.
     */
    @Parameter(label = "Path to search")
    private String pathToSearch;
    @Parameter(label = "Search Term")
    private String SearchTerm;
    @Parameter
    private UIService uiService;
    @Parameter
    private LogService logService;
    @Parameter
    private DatasetIOService datasetIOService;

    
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
            File dir = new File(pathToSearch);
            File[] directoryListing = dir.listFiles();
            Dicoderma dicoderma = new Dicoderma();
            DicomSCModel dicomSCModel = new DicomSCModel();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    dicomSCModel = dicoderma.getDicodermaMetadataFromFile(child);
                    if (
                            (dicomSCModel.PatientID != null && dicomSCModel.PatientID.contains(SearchTerm)) ||
                            (dicomSCModel.PatientName != null && dicomSCModel.PatientName.contains(SearchTerm)) ||
                            (dicomSCModel.StudyDescription != null && dicomSCModel.StudyDescription.toString().contains(SearchTerm)) ||
                            (dicomSCModel.StudyDate != null && dicomSCModel.StudyDate.contains(SearchTerm)) 

                        )
                        image = datasetIOService.open(child.getAbsolutePath());
    
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
                uiService.showDialog(pathToSearch.toString() + " is not valid.");
    
            }            
        } catch (IOException e) {
            //TODO: handle exception
        }

    }

}