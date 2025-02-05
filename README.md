![Datalogics Adobe PDF Library](https://raw.github.com/datalogics/dl-icons/develop/DLBanner_Nuget.png)

[Documentation](https://dev.datalogics.com/adobe-pdf-library/java/getting-started) &nbsp; | &nbsp; [API Reference](https://docs.datalogics.com/apdfl18/Java/index.html) &nbsp; | &nbsp; [Support](https://www.datalogics.com/tech-support-pdfs/) &nbsp; | &nbsp; [Release Notes](https://docs.datalogics.com/apdfl18/Release_Notes.html) &nbsp; | &nbsp;[Homepage](https://www.datalogics.com)

[![Download a Free Trial](https://img.shields.io/badge/maven%20package-APDFL%20Free%20Trial-blue?style=plastic&logo=apachemaven)](https://central.sonatype.com/artifact/com.datalogics.pdfl/pdfl)

## Adobe PDF Library
Built upon Adobe source code used for Acrobat, Datalogics Adobe PDF Library SDK provides stable, reliable code and the flexibility to develop with Java, C#, VB (VB.NET), or C++. APDFL is the most complete SDK for PDF creation, manipulation and management. Best for enterprise/larger organizations of developers and independent software vendors (ISVs) who need to incorporate Adobe's PDF functionality into their own internal or external applications.

Live support from PDF development experts.

Licensing and Pricing options are customized to your usage and requirements. For OEM and SaaS customers we will provide you with a non-license managed software package for easier distribution embedded within your applications.

## Free trial & license activation

To activate the free trial:
1. Visit [Free Trial](https://www.datalogics.com/pdf-sdk-free-trial) to obtain an activation key.
2. A prompt will appear on your console when executing Datalogics sample code.

Alternatively, to use an activation key in code, the <em>setLicenseKey()</em> method of the <em>Library</em> class can be set to
a valid activation key <b>prior</b> to instantiating the library.
```
    Library.setLicenseKey("xxxx-xxxx-xxxx-xxxx");
    Library lib = new Library();
```

## Running the Samples
**For x64 Windows, x64 Linux, or macOS ARM systems:**
Samples can be built and run easily in the [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE.

1. Clone this repository
2. Select a sample you would like to try
3. Open the folder containing the sample in IDE
4. From the Maven tab in the right sidebar, run the *Compile* phase
5. Run the sample by clicking on the **Run** button!

**For other systems:**
See [Using APDFL and Maven on other platforms](using_on_other_platforms.md)
