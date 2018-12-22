<p align="center">
  <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">
    <img src="https://img.shields.io/badge/Java-8-blue.svg" alt="Java 8">
  </a>
  <a href="https://docs.seleniumhq.org/download/">
    <img src="https://img.shields.io/badge/Selenium-3.141.59-blue.svg">
  </a>
  <a href="https://github.com/bonigarcia/webdrivermanager/">
    <img src="https://img.shields.io/badge/WebDriverManager-3.1.1-blue.svg">
  </a>
  <a href="LICENSE.md">
    <img src="https://img.shields.io/github/license/JakeJMattson/SpamBot.svg">
  </a>
</p>

<p align="justify">
This program allows you to create multiplying automated browsers. 
Create a set of browsers that each navigate to a pre-determined site or perform a certain action.
If any browser in the set is closed, create a new set of browsers that is one larger than the previous set.
They will be displayed dynamically based on the size of the set so that they occupy the entire screen and do not overlap.
</p>

<img src="https://user-images.githubusercontent.com/22604455/43761280-411562ce-99ea-11e8-9ae9-d3739b5b5b03.png" width="100%"/>

## Prerequisites

### Languages
* [Java](https://go.java/index.html?intcmp=gojava-banner-java-com)

### Libraries
* [Selenium](https://www.seleniumhq.org/)

## Getting Started
<p align="justify">
The version numbers for each additional software used to build this project are listed in the badges at the top of the page. These dependencies are handled by Maven during build. If building manually, other versions may work, but this is not guaranteed.
</p>

### Installing Java
<p align="justify">
Visit the <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html">Java Downloads</a> 
page and select the version of your choice.
Run the installer and follow the instructions provided.
</p>

### Setting up Selenium
**Note:** Included as Maven dependency. Below are manual instructions.
<br/>
<p align="justify">
To use Selenium, you will need to download the Java language bindings and a WebDriver binary of your choosing.
Both can be found on the <a href="https://www.seleniumhq.org/download/">Selenium Downloads</a> page. This will require several manual code alterations.
</p>

## Building
This project is built with Maven. To build the `pom.xml` file, please follow the import instructions for your IDE.
* [IntelliJ](https://www.tutorialspoint.com/maven/maven_intellij_idea.htm)
* [Eclipse](https://www.tutorialspoint.com/maven/maven_eclispe_ide.htm)
* [NetBeans](https://www.tutorialspoint.com/maven/maven_netbeans.htm)

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
