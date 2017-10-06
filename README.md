# Conway-s-Game-of-Life

![Image](https://zagorskidev.files.wordpress.com/2017/10/zrzut-ekranu-z-2017-10-05-14-05-38.png?w=723)

# How is it done?

Build in Swing. Game of Life is very simple cellular automaton devised by John Horton Conway. Application allows real time modifying game rules, speed, reviving or killing cells. Game allows also mouse wheel zoom and mouse drag moving. Mouse dragging (with right button pressed) moves cells on field instead of moving only view, so you can follow your gliders and other flying structures on and on. Concurrency issues are solved by single block used by every method demanding access to cells collection.

# How does it look like?

You can wath short video of application running.

[![Video](https://img.youtube.com/vi/rALVuPR25yk/0.jpg)](https://youtu.be/rALVuPR25yk)

# How can I run it?

You can download repository and compile or download runnable JAR here: ![runnable JAR](https://drive.google.com/open?id=0B_bwkWjLwn2MOFZpZURiT2YxdlU).

# How can it be improved?

Code could be refactored using some design patterns. Menu panel class should be splitted, now it’s spaghetti, also mouse events handling could be done more clean. Main algorithm could use some heuristics to avoid processing big white sectors, maybe it could be done by storing some metadata about recent iterations.

![Image](https://zagorskidev.files.wordpress.com/2017/10/zrzut-ekranu-z-2017-10-05-14-05-03.png?w=723)
