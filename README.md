# server.markhome.msscf.msscf.cflib.dbutil
The logical domain server.markhome.msscf is not registered and hosted on the public internet, as this project is currently under internal development and virtually hosted in my own network. This code release is a preview, and I don't expect to be doing a production release until late 2026 to mid 2027. There is an incredible amount of code that needs refreshing and updating to fit my new visions of "how to internet."

CFLib provides the essential foundation classes for the project and projects built using the output source code, but releases will be incrementally done as the existing v2.13 code is refreshed and brought up to modern standards for 3.0.

The CFLib JPA database utility package for Spring Boot is a revisioning of some concepts from a recent development environment, reconsidered and reworked in light of modern massively multi-core servers and the desire to make it all work through normal JPA code decorations and annotations rather than through some "interesting" code for hooking into JPA itself.  Besides, I think the modern JPA approach is more elegant.
