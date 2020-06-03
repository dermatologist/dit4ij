package in.co.dermatologist.dit4ij;

import in.co.dermatologist.dicoderma.Jpg2Dcm;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.UID;
import org.dcm4che3.data.VR;
import org.dcm4che3.imageio.codec.XPEGParser;
import org.dcm4che3.imageio.codec.jpeg.JPEG;
import org.dcm4che3.imageio.codec.jpeg.JPEGParser;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.io.SAXReader;
import org.dcm4che3.tool.common.CLIUtils;
import org.dcm4che3.util.StreamUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;


public class DitJpg2Dcm extends Jpg2Dcm {
//    org.dcm4che3.tool.jpg2dcm.Jpg2Dcm main = new Jpg2Dcm();

    public void convertJpgToDcm(File srcFile, File destFile, String[] dicodermaMetadataAsArray)
            throws IOException, ParserConfigurationException, SAXException {

        Path srcFilePath = srcFile.toPath();
        Path destFilePath = destFile.toPath();
        setNoAPPn(false);
        setPhoto(false);
        Attributes staticMetadata = new Attributes();
        CLIUtils.addAttributes(staticMetadata, dicodermaMetadataAsArray);
        supplementMissingUIDs(staticMetadata);
        supplementMissingValue(staticMetadata, Tag.SeriesNumber, "999");
        supplementMissingValue(staticMetadata, Tag.InstanceNumber, "1");
        supplementType2Tags(staticMetadata);


        // ContentType fileType = ContentType.probe(srcFilePath);

        //@TODO: Check for Image Type
        //ContentType fileType = ContentType.IMAGE_JPEG;

        // ClassLoader classLoader = getClass().getClassLoader();
        // URL resource = classLoader.getResource("secondaryCaptureImageMetadata.xml");
        // String SCImageMetadataFile = resource.getFile();

        String scString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
                scString = scString + "<NativeDicomModel xml:space=\"preserve\">";
                scString = scString + "<DicomAttribute keyword=\"PatientID\" tag=\"00100020\" vr=\"LO\"/>";
                scString = scString + "<DicomAttribute keyword=\"PatientName\" tag=\"00100010\" vr=\"PN\"/>";
                scString = scString + "<DicomAttribute keyword=\"PatientBirthDate\" tag=\"00100030\" vr=\"DA\"/>";
                scString = scString + "<DicomAttribute keyword=\"PatientSex\" tag=\"00100040\" vr=\"CS\"/>";
                scString = scString + "<DicomAttribute keyword=\"StudyDate\" tag=\"00080020\" vr=\"DA\"/>";
                scString = scString + "<DicomAttribute keyword=\"StudyTime\" tag=\"00080030\" vr=\"TM\"/>";
                scString = scString + "<DicomAttribute keyword=\"ReferringPhysicianName\" tag=\"00080090\" vr=\"PN\"/>";
                scString = scString + "<DicomAttribute keyword=\"StudyID\" tag=\"00200010\" vr=\"SH\"/>";
                scString = scString + "<DicomAttribute keyword=\"AccessionNumber\" tag=\"00080050\" vr=\"SH\"/>";
                scString = scString + "<DicomAttribute keyword=\"SeriesNumber\" tag=\"00200011\" vr=\"IS\"/>";
                scString = scString + "<DicomAttribute keyword=\"ConversionType\" tag=\"00080064\" vr=\"CS\">";
                    scString = scString + "<Value number=\"1\">SI";
                    scString = scString + "</Value>";
                scString = scString + "</DicomAttribute>";
                scString = scString + "<DicomAttribute keyword=\"InstanceNumber\" tag=\"00200013\" vr=\"IS\"/>";
                scString = scString + "<DicomAttribute keyword=\"PatientOrientation\" tag=\"00200020\" vr=\"CS\"/>";
                scString = scString + "</NativeDicomModel>";
        InputStream targetStream = new ByteArrayInputStream(scString.getBytes());
        Attributes fileMetadata = SAXReader.parse(targetStream);
        fileMetadata.addAll(staticMetadata);
        supplementMissingValue(fileMetadata, Tag.SOPClassUID, UID.SecondaryCaptureImageStorage);
        try (SeekableByteChannel channel = Files.newByteChannel(srcFilePath);
             DicomOutputStream dos = new DicomOutputStream(destFilePath.toFile())) {
            XPEGParser parser = new JPEGParser(channel);
            parser.getAttributes(fileMetadata);
            dos.writeDataset(fileMetadata.createFileMetaInformation(parser.getTransferSyntaxUID()), fileMetadata);
            dos.writeHeader(Tag.PixelData, VR.OB, -1);
            dos.writeHeader(Tag.Item, null, 0);
            if (noAPPn && parser.getPositionAfterAPPSegments() > 0) {
                copyPixelData(channel, parser.getPositionAfterAPPSegments(), dos,
                        (byte) 0xFF, (byte) JPEG.SOI);
            } else {
                copyPixelData(channel, parser.getCodeStreamPosition(), dos);
            }
            dos.writeHeader(Tag.SequenceDelimitationItem, null, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(MessageFormat.format(rb.getString("converted"), srcFilePath, destFilePath));
    }

}