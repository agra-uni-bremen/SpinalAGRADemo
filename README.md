# Demonstrations for the SpinalHDL AGRA Demo

The code is organized in the following directory structure:
```
SpinalAGRADemo
├── hw
│   ├── gen
│   ├── spinal
│   │   ├── fsmdemo
│   │   ├── projectname
│   │   ├── regdemo
│   │   └── sineromdemo
│   ├── verilog
│   └── vhdl
├── project
├── simWorkspace
└── target
```

The `hw` directory contains all source code, generated or provided. Inside `hw/gen` resides the generated Verilog and VHDL code and corresponding initial data for memories. In `hw/spinal` all the SpinalHDL-related code will be placed, directory structures are similar to package structures in Java/Scala. The folders `hw/verilog` and `hw/vhdl` contain source code in Verilog and VHDL, respectively, if code in these languages is utilized within SpinalHDL (e.g., as IP/blackboxes). 
The directories `project` and `target` come from the usage of sbt as build tool. 
Once a design is simulated, corresponding artifacts are placed inside `simWorkspace` as subdirectories. 

Let's start using SpinalHDL!

1. Start an interactive sbt console from this directory:

```bash
~/SpinalAGRADemo$ sbt
```

Eventually, you will have a sbt prompt that looks like this:

```bash
sbt:projectname> 
```

2. The command `show discoveredMainClasses` can show the available runnable parts of this repository:

```bash
sbt:projectname> show discoveredMainClasses
[info] compiling 2 Scala sources to /home/sallar/work/SpinalAGRADemo/target/scala-2.12/classes ...
[info] * fsmdemo.FSMDemoSim
[info] * fsmdemo.FSMDemoVerilog
[info] * projectname.MyTopLevelFormal
[info] * projectname.MyTopLevelSim
[info] * projectname.MyTopLevelVerilog
[info] * projectname.MyTopLevelVhdl
[info] * regdemo.RegDemoVerilog
[info] * regdemo.RegDemoVhdl
[info] * sineromdemo.SineRomDemoSim
[info] * sineromdemo.SineRomDemoVerilog
[success] Total time: 4 s, completed 24.07.2024, 13:22:42
```

3. We can now use the command `runMain` to execute one of those targets. For example `regdemo.RegDemoVerilog`:

```bash
sbt:projectname> runMain regdemo.RegDemoVerilog
[info] running (fork) regdemo.RegDemoVerilog 
[info] [Runtime] SpinalHDL v1.10.2    git head : 279867b771fb50fc0aec21d8a20d8fdad0f87e3f
[info] [Runtime] JVM max memory : 7940,0MiB
[info] [Runtime] Current date : 2024.07.24 13:28:55
[info] [Progress] at 0,000 : Elaborate components
[info] [Progress] at 0,252 : Checks and transforms
[info] [Progress] at 0,347 : Generate Verilog to hw/gen
[info] [Warning] 1 signals were pruned. You can call printPruned on the backend report to get more informations.
[info] [Done] at 0,411
[success] Total time: 1 s, completed 24.07.2024, 13:28:56
```

This will generate the respective Verilog code inside `hw/gen/`. The file will be called `RegDemo.v`.

4. Simulating a design with its testbench works similarly, by calling the runMain command for the respective target. For example `sineromdemo.SineRomDemoSim`:

```bash
sbt:projectname> runMain sineromdemo.SineRomDemoSim
[info] running (fork) sineromdemo.SineRomDemoSim 
[info] [Runtime] SpinalHDL v1.10.2    git head : 279867b771fb50fc0aec21d8a20d8fdad0f87e3f
[info] [Runtime] JVM max memory : 7940,0MiB
[info] [Runtime] Current date : 2024.07.24 13:31:40
[info] [Progress] at 0,000 : Elaborate components
[info] [Progress] at 0,311 : Checks and transforms
[info] [Progress] at 0,439 : Generate Verilog to ./simWorkspace/tmp/job_1
[info] [Done] at 0,507
[info] [Progress] Simulation workspace in /home/sallar/work/SpinalAGRADemo/./simWorkspace/SineRomDemo
[info] [Progress] Verilator compilation started
[info] [info] Found cached verilator binaries
[info] [Progress] Verilator compilation done in 572,406 ms
[info] [Progress] Start SineRomDemo test simulation with seed 495730415
[info] [Done] Simulation done in 58,517 ms
[success] Total time: 2 s, completed 24.07.2024, 13:31:42
```

5. Since we simulated a design with a testbench that also writes a wavetrace (in the FST format), we can take a look at it. GTKWave is an open-source tool that allows this, for example. In a new terminal inside this directory, you can do the following:

```bash
~/SpinalAGRADemo$ gtkwave simWorkspace/SineRomDemo/test/wave.fst
```

Which will open the wavetrace for the testbench of SineRomDemo.
The directory `simWorkspace` contains all simulated designs, which were simulated through SpinalHDL testbenches. Corresponding artifacts like wavetraces, the generated Verilog, or even the Verilator code are contained inside. 

-------------

Below you find some general information on how to setup and run this SpinalHDL project (original README from the [Basic SpinalHDL Project / SpinalTemplateSbt](https://github.com/SpinalHDL/SpinalTemplateSbt))

-------------

# SpinalHDL Base Project

This repository is a base project to help Spinal users set-up project without knowledge about Scala and SBT.


## If it is your are learning SpinalHDL

You can follow the tutorial on the [Getting Started](https://spinalhdl.github.io/SpinalDoc-RTD/master/SpinalHDL/Getting%20Started/index.html) page.

More specifically:

* instructions to install tools can be found on the [Install and setup](https://spinalhdl.github.io/SpinalDoc-RTD/master/SpinalHDL/Getting%20Started/Install%20and%20setup.html#install-and-setup) page
* instructions to get this repository locally are available in the [Create a SpinalHDL project](https://spinalhdl.github.io/SpinalDoc-RTD/master/SpinalHDL/Getting%20Started/Install%20and%20setup.html#create-a-spinalhdl-project) section.


### TL;DR Things have already been set up in my environment, how do I run things to try SpinalHDL?

Once in the `SpinalTemplateSbt` directory, when tools are installed, the commands below can be run to use `sbt`.

```sh
// To generate the Verilog from the example
sbt "runMain projectname.MyTopLevelVerilog"

// To generate the VHDL from the example
sbt "runMain projectname.MyTopLevelVhdl"

// To run the testbench
sbt "runMain projectname.MyTopLevelSim"
```

* The example hardware description is into `hw/spinal/projectname/MyTopLevel.scala`
* The testbench is into `hw/spinal/projectname/MyTopLevelSim.scala`

When you really start working with SpinalHDL, it is recommended (both for comfort and efficiency) to use an IDE, see the [Getting started](https://spinalhdl.github.io/SpinalDoc-RTD/master/SpinalHDL/Getting%20Started/index.html).


## If you want to create a new project from this template

### Change project name

You might want to change the project name, which is currently `projectname`. To do so (let's say your actual project name is `myproject`; it must be all lowercase with no separators):

* Update `build.sbt` and/or `build.sc` by replacing `projectname` by the name of your project `myproject` (1 occurrence in each file). The better is to replace in both (it will always work), but in some contexts you can keep only one of these two files:
    * If you are sure all people only use `sbt`, you can replace only in `build.sbt` and remove `build.sc`
    * If you are sure all people only use `mill`, you can replace only in `build.sc` and remove `build.sbt`
    * Replace in both files for open-source project.
* Put all your scala files into `hw/spinal/myproject/` (remove the unused `hw/spinal/projectname/` folder)
* Start all your scala files with `package myproject`


### Change project structure

You can change the project structure as you want. The only restrictions (from Scala environment) are (let's say your actual project name is `myproject`):

* you must have a `myproject` folder and files in it must start with `package myproject`
* if you have a file in a subfolder `myproject/somepackage/MyElement.scala` it must start with `package myproject.somepackage`.
* `sbt` and `mill` must be run right in the folder containing their configurations (recommended to not move these files)

Once the project structure is modified, update configurations:

* In `build.sbt` and/or `build.sc` (see above) replace `/ "hw" / "spinal"` by the new path to the folder containing the `myproject` folder.
* In the spinal configuration file (if you kept it, by default it is in `projectname/Config.scala`) change the path in `targetDirectory = "hw/gen"` to the directory where you want generated files to be written. If you don't use a config or if it doesn't contain this element, generated files will be written in the root directory.


### Update this README

Of course you can replace/modify this file to help people with your own project!


## Mill Support (Experimental)

The [Mill build tool](https://com-lihaoyi.github.io/mill) can be installed and used instead of `sbt`.

```sh
// To generate the Verilog from the example
mill projectname.runMain projectname.MyTopLevelVerilog

// To generate the VHDL from the example
mill projectname.runMain projectname.MyTopLevelVhdl

// To run the testbench
mill projectname.runMain projectname.MyTopLevelSim
```
