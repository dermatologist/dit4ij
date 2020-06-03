package in.co.dermatologist.dit4ij;
import in.co.dermatologist.dicoderma.DicomSCModel;

public class Util {

    public static String DisplayDicoderma(DicomSCModel dicomSCModel){
        String toDisplay = dicomSCModel.PatientID + " | " + dicomSCModel.PatientName + " | " + dicomSCModel.PatientSex;
        toDisplay = toDisplay.concat(" | " + dicomSCModel.PatientBirthDate);
        toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDescription);
        toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyDate);
        toDisplay = toDisplay.concat(" | " + dicomSCModel.StudyTime);
        return toDisplay;

    }

}