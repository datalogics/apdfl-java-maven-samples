from dl_conan_build_tools.tasks import conan
from invoke import Collection, task
from invoke.tasks import Task
import platform
import os
import pathlib
import subprocess

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

        if platform.system() == 'Darwin' and ('ConvertToOffice' in sample or 'CreateDocFromXPS' in sample):
            print(f'{sample} not available on this OS')
            continue

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

def execute_java_sample(target_dir, sample_name, full_path, apdfl_key):
    command = str(f'java -Djava.library.path={target_dir} -jar target/{sample_name}-1.0-SNAPSHOT-jar-with-dependencies.jar')

    process = subprocess.Popen(command, shell=True, cwd=full_path, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    process.stdin.write(apdfl_key.encode() + b'\n')
    process.stdin.flush()
    stdout, stderr = process.communicate()

    if process.returncode == 0:
        print(f"{sample_name} sample ran successfully.")
        print(stdout.decode(errors='ignore'))
    else:
        print(stderr.decode())
        raise RuntimeError(f"{sample_name} sample failed to run.")

@task()
def run_samples(ctx):
    """Runs the APDFL Java Maven samples"""
    apdfl_key = os.environ.get("APDFL_KEY")

    if apdfl_key is None:
        raise ValueError("APDFL_KEY environment variable not set.")

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
        if platform.system() == 'Darwin' and ('ConvertToOffice' in sample or 'CreateDocFromXPS' in sample):
            print(f'{sample} not available on this OS')
            continue
        elif platform.system() == 'Linux' and 'ConvertToOffice' in sample:
            continue
        else:
            sample_name = sample.split("/")[1]
            execute_java_sample(target_dir, sample_name, full_path, apdfl_key)

        if platform.system() == "Windows":
            remove_last_path_entry()

tasks = []
tasks.extend([v for v in locals().values() if isinstance(v, Task)])

conan_tasks = Collection()

ns = Collection(*tasks)
ns.add_collection(conan_tasks, 'conan')

ns.configure({'run': {'echo': 'true'}})
