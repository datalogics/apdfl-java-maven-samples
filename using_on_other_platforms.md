# Using APDFL and Maven on non license managed platforms

## Installing APDFL for non license managed platforms

[Maven Central](https://central.sonatype.com/artifact/com.datalogics.pdfl/pdfl) offers self-activating license managed APDFL packages for 64-bit Windows, 64-bit Intel Linux, and Mac systems running Mac ARM. The samples in this repo will automatically download the APDFL package for your platform via Maven and obtain a trial license.

Customers can also obtain APDFL without license management for the same three platforms, plus those listed below. You will receive a ZIP file with the APDFL components for your platform, plus a POM file (`create-artifacts.xml`) that you can use to install APDFL to your local Maven cache, or deploy to private Maven repo on your network. To install the components to your local cache, unzip the file and run the command

```
./mvnw -f create-artifacts.xml install
```

## Running the samples on non license managed platforms

After you've installed APDFL for your platform to your Maven cache, you can run any sample in this repository by updating its POM to find the package for your platform.

1. Open the `pom.xml` for the sample you want to try
2. Copy the complete `<profile>` section for your platform from below
3. Paste it into the `<profiles>` block of the `pom.xml`

### **Mac Intel (64-bit)**
```
    <profile>
      <id>MacIntel</id>
      <activation>
        <os>
          <family>mac</family>
          <arch>x86_64</arch>
        </os>
      </activation>
      <properts>
        <jni.classifr>mac-x86-64-jni</jni.classifr>
      </properties>
    </profile>
```

### **Linux ARM (64-bit)**
```
    <profile>
      <id>LinuxArm</id>
      <activation>
        <os>
          <name>Linux</name>
          <arch>aarch64</arch>
        </os>
      </activation>
      <properties>
        <jni.classifier>linux-arm-64-jni</jni.classifier>
      </properties>
    </profile>
```

### **Sparc Solaris (64-bit)**
```
    <profile>
      <id>SparcSolaris64</id>
      <activation>
        <os>
          <name>sunos</name>
          <arch>sparcv9</arch>
        </os>
      </activation>
      <properties>
        <jni.classifier>sparcsolaris-64-jni</jni.classifier>
      </properties>
    </profile>
```

### **Windows Intel (32-bit)**
```
    <profile>
      <id>Windows32</id>
      <activation>
        <os>
          <family>windows</family>
          <arch>x86</arch>
        </os>
      </activation>
      <properties>
        <jni.classifier>win-x86-32-jni</jni.classifier>
      </properties>
    </profile>
```

### **Linux Intel (32-bit)**
```
    <profile>
      <id>Linux32</id>
      <activation>
        <os>
          <name>Linux</name>
          <arch>x86</arch>
        </os>
      </activation>
      <properties>
        <jni.classifier>linux-x86-32-jni</jni.classifier>
      </properties>
    </profile>
```
### **Windows ARM (64-bit)**
```
    <profile>
      <id>WindowsArm</id>
      <activation>
        <os>
          <family>windows</family>
          <arch>aarch64</arch>
        </os>
      </activation>
      <properties>
        <jni.classifier>win-arm-64-jni</jni.classifier>
      </properties>
    </profile>
```
