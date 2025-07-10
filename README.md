# Rectangle Puzzle Machine

## About
When I attended University in the last century, my first software development project was: A machine needs to cut a random number of rectangles with random sizes out of big sheets of metal. Develop a software that loads rectangles from a floppy disk and then place them on the big sheet with as few clippings as possible. Use an evolutionary algorithm to find the best solution. So basically start by throwing the rectangles randomly on the sheet of metal and then iteratively move each rectangle randomly a litle bit. Then calculate a fitness score of the solution by punishing overlaps of rectangles and rectangle positions that were out of bounds of the metal sheet. Continue with this solution if it is better than the last or discard it, if it is worse. Run a lot of these experiments in parallel, and select the best result. In C. And without the help of google (since there was no Google back then :-) )
Long story short, our solution back then was really bad. Non-usuable bad. 
This was vexing me every now and then under the shower while reminiscing about the past.
So, since my kids are now into programming as well, on a rainy Sunday afternoon, I gave it another shot. Simplified. With Java.
Results look something like this:


![example picture](./example.png)



##  How to run Rectangle Puzzle Machine
  - set the `numberOfObjects` in [ThreadeApp](./ThreadedApp.java) to desired number of experiments.
  - Run ThreadedApp.
  - The proceddings of the first experiment will be visualized.
  - All other status will be shown in the terminal.
  - After finishing all experiments, the terminal will output the time, the best result score and the experiment with the best result will be visualized.

## Some Benchmark Results

- On an M4 Mac Book Pro with 14 vCores and __112__ experiments, it took __~70s__
- On an AMD 7800X3D Desktop processor running windows 11 and __112__ experiments, it took __~103s__
- On an AMD Ryzen AI 9 HX370 (24threads) processor running windows 11 and __112__ experiments, it took __~148s__
- On a M1 Mac Book Pro with 8 vcores and __112__ experiments, it took __~200s__
- On a laptop with Windows 10/ Java 23  and AMD Ryzen PRO 5850U (16 threads) and __112__ experiments, it took __~261s__
- On an 11th Gen Intel(R) Core(TM) i5-1145G7 running Windows 11 Pro @ 2.60GHz 1.50 GHz and __112__ experiments, it took __~476s__
- On an old Intel i5-6500 Desktop processor running linux and __112__ experiments, it took __~530s__


