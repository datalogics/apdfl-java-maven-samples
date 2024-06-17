![Datalogics Adobe PDF Library](https://raw.github.com/datalogics/dl-icons/develop/DLBanner_Nuget.png)

[Documentation](https://docs.datalogics.com/apdfl18/Java/index.html) &nbsp; | &nbsp; [Support](https://www.datalogics.com/tech-support-pdfs/) &nbsp; | &nbsp; [Release Notes](https://docs.datalogics.com/apdfl18/Release_Notes.html) &nbsp; | &nbsp;[Homepage](https://www.datalogics.com)

[![Download a Free Trial](https://img.shields.io/badge/maven%20package-APDFL%20Free%20Trial-blue?style=plastic&logo=apachemaven)](https://central.sonatype.com/artifact/com.datalogics.pdfl/forms-extension)

## Adobe PDF Library Forms Extension

Forms Extension SDK is an Adobe PDF Library addition that helps users who work with PDF forms to efficiently manage their forms processes.  Import and export form data, lock completed forms to prevent editing and provide consistent viewing experiences across all devices. 

Forms Extension Uses:

* Adobe PDF Library customers who need additional forms support 
* Incorporating forms functionality into your own applications 
* Processing PDFs containing XFA form content 
* Using dynamic forms that require customization
* Using database content to populate form fields
* Exporting form data to populate databases 
* Conditionally preventing form editing (for example: after submission) 

Live support from PDF development experts.

Licensing and Pricing options are customized to your usage and requirements. For OEM and SaaS customers we will provide you with a non-license managed software package for easier distribution embedded within your applications. 

## Supported Platforms

- Windows 64 Bit
- Linux 64 Bit

## Free trial & license activation

To activate the free trial:
1. Visit https://www.datalogics.com/pdf-form-functions to obtain an activation key.
2. A prompt will appear on your console when executing Datalogics sample code.

Alternatively, to use an activation key in code, the <em>setLicenseKey()</em> method of the <em>Library</em> class can be set to
a valid activation key <b>prior</b> to instantiating the library.
```
    Library.setLicenseKey("xxxx-xxxx-xxxx-xxxx");
    Library lib = new Library();
```

## Running the Samples
**For 64-bit Intel Windows, 64-bit Intel Linux systems:**
- Clone this repository to your system
- Select a sample you would like to try
- Open the folder containing the sample in [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- From the Maven tab in the right sidebar, run the "Compile" phase
- Run the sample
