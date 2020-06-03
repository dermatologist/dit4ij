package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Dicoderma;
import in.co.dermatologist.dicoderma.DicomSCModel;
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

@Plugin(type = Command.class, menuPath = "Plugins>Dicoderma>Add")
public class AddDicoderma<T extends RealType<T>> implements Command {

    @Parameter
    private Dataset currentData;
    
    @Parameter
    private UIService uiService;

    @Parameter
    private LogService logService;
    
	@Parameter(label = "Patient ID")
	private String PatientID;
    
    @Parameter(label = "Patient Name")
	private String PatientName;

    @Parameter(label = "Gender", //
            choices = {"M", "F"})
    private String PatientSex = "M";

    @Parameter(label = "Date of Birth")
    private String PatientBirthDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Parameter(label = "Date")
    private String StudyDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Parameter(label = "Time")
    private String StudyTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
    
    @Parameter(label = "Tag")
    private String StudyDescription;

    @Override
    public void run() {
        try{
            File currentFile = new File(currentData.getSource());

            String currentFileName = currentData.getSource();
            int lastDot = currentFileName.lastIndexOf('.');
            String newFileName = currentFileName.substring(0,lastDot) + 
                        "-" + StudyDate + currentFileName.substring(lastDot);
            File newFile = new File(newFileName);


            Dicoderma dicoderma = new Dicoderma();
            DicomSCModel dicomSCModel = dicoderma.getDicodermaMetadataFromFile(currentFile);
            dicomSCModel.PatientID = PatientID;
            dicomSCModel.PatientName = PatientName;
            if ("M".equals(PatientSex))
                dicomSCModel.PatientSex = GenderEnum.MALE;
            else
                dicomSCModel.PatientSex = GenderEnum.FEMALE;
            dicomSCModel.PatientBirthDate = PatientBirthDate;
            dicomSCModel.StudyDate = StudyDate;
            dicomSCModel.StudyTime = StudyTime;
            dicomSCModel.StudyDescription = StudyDescription;
            dicoderma.putDicomModelToFile(currentFile, newFile, dicomSCModel);
            uiService.showDialog(Util.DisplayDicoderma(dicomSCModel));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (ImageWriteException e) {
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