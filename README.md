# :pill: DIT4IJ - Dermatology Image Tagger for ImageJ

## About

ImageJ (and its batteries-included version, [Fiji](https://fiji.sc/)) is a popular biomedical imaging software with a modular, extensible architecture. ImageJ can be downloaded from the NIH website [here](https://imagej.nih.gov/ij/download.html). DIT4IJ is an ImageJ plugin that adds DICOM related function for clinical images in Dermatology. It adds certain DICOM tags as a JSON formatted string to the EXIF header that can be used for searching. It also supports converting general images to DICOM (.dcm) resources that can be used with the DICOM supported systems such as PACS. 

## Functions

* **Add** DICOM metadata as EXIF tags.
* **View** DICOM metadata stored in EXIF tags.
* **Search** images based on DICOM metadata stored in EXIF tags.
* **Save** images as DICOM (.dcm) resources

## Build and install

These functions are packaged as a JAVA library - [dicoderma](https://github.com/dermatologist/dicoderma). Clone [dicoderma repo](https://github.com/dermatologist/dicoderma) first and *mvn clean install* locally. Then clone this repository and *mvn clean package*. Save the plugin jar file in the target folder into the folder called "plugins" inside the directory where you installed [ImageJ](https://imagej.nih.gov/ij/download.html) or [Fiji](https://fiji.sc/).

## Author

[Bell Eapen](https://nuchange.ca)

