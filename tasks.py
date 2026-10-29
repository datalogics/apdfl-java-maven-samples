from invoke import Collection, Exit, task
from invoke.tasks import Task
import platform
import os
import pathlib
import shutil
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
                'DocumentConversion/ConvertWordToPDF/',
                'DocumentConversion/CreateDocFromWebPage/',
                'DocumentConversion/CreateDocFromXPS/',
                'DocumentConversion/FacturXConverter/',
                'DocumentConversion/PDFAConverter/',
                'DocumentConversion/PDFXConverter/',
                'DocumentConversion/ZUGFeRDConverter/',
                'DocumentOptimization/PDFOptimize/',
                'Forms/ConvertXFAToAcroForms/',
                'Forms/ExportFormsData/',
                'Forms/FlattenForms/',
                'Forms/ImportFormsData/',
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
                'OpticalCharacterRecognition/OCRDocument/',
                'Other/MemoryFileSystem/',
                'Other/StreamIO/',
                'Security/AddBasicPAdESElectronicSignature/',
                'Security/AddPAdESPolicySignature/',
                'Security/AddDigitalSignatureCMS/',
                'Security/AddDigitalSignatureRFC3161/',
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


# The package sources build-samples can test against:
#   Nightly - apdfl-internal, where maven-builder publishes every night. Needs
#             the RepoFlow credentials in REPOFLOW_USER / REPOFLOW_PASS.
#   Public  - apdfl-public, the anonymous repository the sample POMs name and
#             customers resolve from. This is the pass that fails when a
#             release is approved but not actually usable.
package_sources = ('Nightly', 'Public')

repoflow_url = 'https://repo.datalogics.com/api/maven/datalogics-enterprise'

# Generated settings files, one per package source. build/ is git-ignored.
settings_dir = 'build'


def maven_settings(pkg_source):
    """Writes the settings.xml for pkg_source and returns its absolute path.

    Each pass uses only its own settings, never the node's ~/.m2/settings.xml,
    so a mirror configured on a node cannot change which repository a pass
    tests. Nightly mirrors apdfl-public onto apdfl-internal: the sample POMs
    stay exactly as customers get them, and nothing that only exists on
    apdfl-public can satisfy the nightly build. The credentials are left as
    ${env.*} references so the password is never written to disk.
    """
    if pkg_source == 'Nightly':
        missing = [v for v in ('REPOFLOW_USER', 'REPOFLOW_PASS') if not os.environ.get(v)]
        if missing:
            raise Exit(f'{" and ".join(missing)} not set -- the Nightly packages on '
                       f'apdfl-internal need the RepoFlow credentials')
        body = f"""  <servers>
    <server>
      <id>apdfl-internal</id>
      <username>${{env.REPOFLOW_USER}}</username>
      <password>${{env.REPOFLOW_PASS}}</password>
    </server>
  </servers>
  <mirrors>
    <mirror>
      <id>apdfl-internal</id>
      <mirrorOf>apdfl-public</mirrorOf>
      <url>{repoflow_url}/apdfl-internal</url>
    </mirror>
  </mirrors>
"""
    else:
        body = ''

    os.makedirs(settings_dir, exist_ok=True)
    settings = os.path.abspath(os.path.join(settings_dir, f'settings-{pkg_source.lower()}.xml'))
    with open(settings, 'w', encoding='utf-8', newline='\n') as f:
        f.write('<?xml version="1.0" encoding="UTF-8"?>\n'
                '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0">\n'
                f'{body}</settings>\n')
    return settings


def check_pkg_source(pkg_source):
    # An unrecognized value would otherwise build or run every sample against
    # whatever happened to be resolved last, which passes without testing anything.
    if pkg_source not in package_sources:
        raise Exit(f'unknown package source {pkg_source!r}, '
                   f'expected one of {list(package_sources)}')


def skip_on_mac_intel(sample):
    # The OfficeToPDF plugin ships for macOS on Apple silicon only.
    return (platform.system() == 'Darwin' and
            platform.machine().lower() not in ('arm64', 'aarch64') and
            'ConvertWordToPDF' in sample)


def skip_on_windows_arm(sample):
    # These samples use APDFL features that are not implemented on Windows ARM
    # for APDFL 21 (Forms Extension, Office conversion and the OfficeToPDF
    # plugin).
    unsupported = ('ConvertXFAToAcroForms', 'ExportFormsData', 'FlattenForms',
                   'ImportFormsData', 'ConvertToOffice', 'ConvertWordToPDF')
    is_windows_arm = (platform.system() == 'Windows' and
                      platform.machine().lower() in ('arm64', 'aarch64'))
    return is_windows_arm and any(s in sample for s in unsupported)


@task()
def clean_samples(ctx):
    """Cleans files that were generated from building the samples"""
    for sample in samples_list:
        full_path = os.path.join(os.getcwd(), sample)
        with ctx.cd(full_path):
            ctx.run('mvn clean')
            ctx.run('git clean -fdx')


@task()
def clean_maven_packages(ctx):
    """Removes the APDFL artifacts from the local Maven repository

    The nightly and public packages can share version numbers, and Maven reuses
    an artifact already in the local repository without asking a remote. A copy
    left by the nightly pass would then stand in for one that was never
    published, so the two passes each start without any com.datalogics.pdfl
    artifacts. Everything else stays so third-party dependencies are not
    downloaded twice.
    """
    # Asked of Maven rather than assumed, because CI sets a job-local
    # repository through MAVEN_OPTS.
    result = ctx.run('mvn -q -DforceStdout '
                     'org.apache.maven.plugins:maven-help-plugin:3.5.1:evaluate '
                     '-Dexpression=settings.localRepository', hide='out')
    local_repo = result.stdout.strip()
    if not local_repo or not os.path.isabs(local_repo):
        raise Exit(f'could not determine the local Maven repository, got {local_repo!r}')

    apdfl_dir = os.path.join(local_repo, 'com', 'datalogics', 'pdfl')
    print(f'... removing {apdfl_dir}')
    shutil.rmtree(apdfl_dir, ignore_errors=True)


# Defaulted so a bare `invoke build-samples` still works; invoke never takes an
# argument with a default positionally, so it is `--pkg-source Public`.
@task(pre=[clean_samples],
      help={'pkg_source': f'Packages to build against: {" or ".join(package_sources)}'})
def build_samples(ctx, pkg_source='Nightly'):
    """Builds the APDFL Java Maven samples against the Nightly or Public packages"""
    check_pkg_source(pkg_source)
    settings = maven_settings(pkg_source)

    for sample in samples_list:
        full_path = os.path.join(os.getcwd(), sample)

        if skip_on_windows_arm(sample):
            print(f'{sample} not available on Windows ARM')
            continue

        if skip_on_mac_intel(sample):
            print(f'{sample} not available on this OS')
            continue

        if platform.system() == 'Darwin' and ('ConvertToOffice' in sample or
                                              'CreateDocFromXPS' in sample or
                                              'ConvertXFAToAcroForms' in sample
                                              or 'ExportFormsData' in sample or
                                              'FlattenForms' in sample or
                                              'ImportFormsData' in sample):
            output = ""
            if ('ConvertToOffice' in sample or 'CreateDocFromXPS' in sample):
                output = "not available on this OS"
            else:
                output = "cannot be run in CI on this OS"
            print(f'{sample} {output}')
            continue

        with ctx.cd(full_path):
            ctx.run(f'mvn -s "{settings}" package')


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
    command = [
        'java',
        f'-Djava.library.path={target_dir}',
        '-jar',
        f'target/{sample_name}-1.0-SNAPSHOT-jar-with-dependencies.jar'
    ]
    process = subprocess.Popen(command, shell=False, cwd=full_path,
                               stdin=subprocess.PIPE, stdout=subprocess.PIPE,
                               stderr=subprocess.PIPE)
    process.stdin.write(apdfl_key.encode() + b'\n')
    process.stdin.flush()
    stdout, stderr = process.communicate()

    if process.returncode == 0:
        print(f"{sample_name} sample ran successfully.")
        print(stdout.decode(errors='ignore'))
    else:
        print(stderr.decode())
        raise RuntimeError(f"{sample_name} sample failed to run.")


@task(help={'pkg_source': f'Packages the samples were built against: {" or ".join(package_sources)}'})
def run_samples(ctx, pkg_source='Nightly'):
    """Runs the APDFL Java Maven samples"""
    check_pkg_source(pkg_source)
    apdfl_key = os.environ.get("APDFL_KEY")

    if apdfl_key is None:
        raise ValueError("APDFL_KEY environment variable not set.")

    for sample in samples_list:
        if skip_on_windows_arm(sample):
            print(f'{sample} not available on Windows ARM')
            continue

        if skip_on_mac_intel(sample):
            print(f'{sample} not available on this OS')
            continue

        if platform.system() == "Windows":
            os.environ["PATH"] += ";"

        full_path = os.path.join(os.getcwd(), sample)

        # Add samples' target\lib directory to PATH environment variable
        target_dir = pathlib.Path(full_path, 'target', 'lib')
        if platform.system() == 'Windows':
            os.environ["PATH"] += str(target_dir)

        if 'DocToImages' in sample or 'ImageDisplayByteArray' in sample:
            continue
        if platform.system() == 'Darwin' and ('ConvertToOffice' in sample or
                                              'CreateDocFromXPS' in sample or
                                              'ConvertXFAToAcroForms' in sample
                                              or 'ExportFormsData' in sample or
                                              'FlattenForms' in sample or
                                              'ImportFormsData' in sample):
            output = ""
            if ('ConvertToOffice' in sample or 'CreateDocFromXPS' in sample):
                output = "not available on this OS"
            else:
                output = "cannot be run in CI on this OS"
            print(f'{sample} {output}')
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

ns = Collection(*tasks)
ns.configure({'run': {'echo': 'true'}})
