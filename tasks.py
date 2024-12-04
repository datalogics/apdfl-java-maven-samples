from dl_conan_build_tools.tasks import conan
from invoke import Collection, task
from invoke.tasks import Task
import platform
import os
import pathlib

samples_list = [  
                'Annotations/Annotations/',
                'Annotations/InkAnnotations/',
                'Annotations/LinkAnnotations/',
                'Annotations/PolygonAnnotations/',
                'Annotations/PolyLineAnnotations/',
                'ContentCreation/AddElements/',
                'ContentCreation/AddHeaderFooter/',
                'ContentCreation/Clips/',
                'ContentCreation/CreateBookmarks/',
                'ContentCreation/GradientShade/',
                'ContentCreation/MakeDocWithCalGrayColorSpace/',
                'ContentCreation/MakeDocWithCalRGBColorSpace/',
                'ContentCreation/MakeDocWithDeviceNColorSpace/',
                'ContentCreation/MakeDocWithICCBasedColorSpace/',
                'ContentCreation/MakeDocWithIndexedColorSpace/',
                'ContentCreation/MakeDocWithLabColorSpace/',
                'ContentCreation/MakeDocWithSeparationColorSpace/',
                'ContentCreation/NameTrees/',
                'ContentCreation/NumberTrees/',
                'ContentCreation/RemoteGoToActions/',
                'ContentCreation/WriteNChannelTiff/',
                'ContentModification/Actions/',
                'ContentModification/AddCollection/',
                'ContentModification/AddQRCode/',
                'ContentModification/ChangeLayerConfiguration/',
                'ContentModification/ChangeLinkColors/',
                'ContentModification/CreateLayer/',
                'ContentModification/ExtendedGraphicStates/',
                'ContentModification/FlattenTransparency/',
                'ContentModification/LaunchActions/',
                'ContentModification/MergePDF/',
                'ContentModification/PageLabels/',
                'ContentModification/PDFObject/',
                'ContentModification/UnderlinesAndHighlights/',
                'ContentModification/Watermark/',
                'DocumentConversion/ColorConvertDocument/',
                'DocumentConversion/ConvertToOffice/',
                'DocumentConversion/CreateDocFromXPS/',
                'DocumentConversion/FacturXConverter/',
                'DocumentConversion/PDFAConverter/',
                'DocumentConversion/PDFXConverter/',
                'DocumentConversion/ZUGFeRDConverter/',
                'DocumentOptimization/PDFOptimize/',
                'Images/DocToImages/',
                'Images/DrawSeparations/',
                'Images/EPSSeparations/',
                'Images/GetSeparatedImages/',
                'Images/ImageDisplayByteArray',
                'Images/ImageEmbedICCProfile/',
                'Images/ImageExport/',
                'Images/ImageExtraction/',
                'Images/ImageFromBufferedImage/',
                'Images/ImageImport/',
                'Images/ImageResampling/',
                'Images/OutputPreview/',
                'Images/RasterizePage/',
                'InformationExtraction/ListBookmarks/',
                'InformationExtraction/ListFonts/',
                'InformationExtraction/ListInfo/',
                'InformationExtraction/ListLayers/',
                'InformationExtraction/ListPaths/',
                'InformationExtraction/Metadata/',
                'OpticalCharacterRecognition/AddTextToDocument/',
                'OpticalCharacterRecognition/AddTextToImage/',
                'Other/MemoryFileSystem/',
                'Other/StreamIO/',
                'Security/AddRegexRedaction/',
                'Security/Redactions/',
                'Text/AddGlyphs/',
                'Text/AddUnicodeText/',
                'Text/AddVerticalText/',
                'Text/ListWords/',
                'Text/RegexExtractText/',
                'Text/RegexTextSearch/',
                'Text/TextExtract/'
        ]

@task()
def clean_samples(ctx):
    """Cleans files that were generated from building the samples"""
    for sample in samples_list:
        full_path = os.path.join(os.getcwd(), sample)
        with ctx.cd(full_path):
            ctx.run('mvn clean')
            ctx.run('git clean -fdx')

@task()
def build_samples(ctx):
    """Builds the APDFL Java Maven samples"""
    ctx.run('invoke clean-samples')
    for sample in samples_list:
        full_path = os.path.join(os.getcwd(), sample)
        
        with ctx.cd(full_path):
            ctx.run('mvn package')

def remove_last_path_entry():
    # Determine the platform-specific delimiter
    delimiter = ";" if platform.system() == "Windows" else ":"

    path_entries = os.environ["PATH"].split(delimiter)

    # Remove the last entry from the PATH environment variable
    if path_entries:
        path_entries.pop()

    new_path = delimiter.join(path_entries)
    os.environ["PATH"] = new_path

@task()
def run_samples(ctx):
    """Runs the APDFL Java Maven samples"""
    for sample in samples_list:
        if platform.system() == "Windows":
            os.environ["PATH"] += ";"

        full_path = os.path.join(os.getcwd(), sample)
        
        # Add samples' target\lib directory to PATH environment variable
        target_dir = pathlib.Path(full_path, 'target', 'lib')
        if platform.system() == 'Windows':
            os.environ["PATH"] += str(target_dir)

        if 'DocToImages' in sample or 'ImageDisplayByteArray' in sample:
            continue
        else:
            with ctx.cd(full_path):
                sample_name = sample.split("/")[1]
                ctx.run(f'java -Djava.library.path={target_dir} -jar target/{sample_name}-1.0-SNAPSHOT-jar-with-dependencies.jar')
        
        if platform.system() == "Windows":
            remove_last_path_entry()

tasks = []
tasks.extend([v for v in locals().values() if isinstance(v, Task)])

conan_tasks = Collection()

ns = Collection(*tasks)
ns.add_collection(conan_tasks, 'conan')

ns.configure({'run': {'echo': 'true'}})
