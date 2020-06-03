# TrafficSimulator
Java program developed using Eclipse during my Grade 12 Computer Science course during High School (spring 2019).

## Run Instructions
Download and extract the [TrafficSimulator.zip](https://github.com/justinbauer-1/TrafficSimulator/blob/master/TrafficSimulator.zip) folder and run the `TrafficSimulator.jar` file to see the program in action (need to have a JDK installed). Alternatively, you can run the AnimationFrame.java file to start the program by your own means.

## Program Controls
* Arrow keys up and down - Increase or decrease the number of cars
* Arrow keys left and right - Increase or decrease the speed of the cars
* Pause button - Top left corner of window
* F1 and F2 - Zoom in and out
* Ctrl + s - Disable spawn limit (warning program will lag or even crash with more than 100 cars) 
* Ctrl + t - Enable turning (warning cars will collide, blocking other cars from moving) 
* Type Lightning McQueen's catchphrase for a secret Easter Egg

## Background
I created this project due to my interest in self driving vehicles as well as my love for video games. Combining the ideas of Tesla's autopilot and the driving NPCs in the video game Grand Theft Auto, this program allowed me to apply the programming skills I learned during high school to several things that interested me. This project was for my final assignment in my Grade 12 Computer Science class and I had totally flexibility. My final grade on this project was 100% and my teacher said they were very impressed with my product.

## Building the project
I started off with the base code provided by my teacher. This code created the animated window keeping it updated and applying the graphics. I first created the map in a csv file and correlating image tiles. Using a csv reader, the layout in the csv file was combined with the image tiles to create a road map. From there I created a basic car and controls to drive it around in the map. 

After that I worked on spawning several instances of the car around the map in random locations on the road. Once spawned the cars would readjust their positions to be in the centre of their lanes. After that I had the cars drive by themselves going straight along their paths within their lanes. After that I developed turning at intersections so the cars could either turn left or right. This was later disabled since cars would often crash causing blockages in intersections. 

Using modified collision detection algorithms, I added the ability for cars to sense each other so they can avoid collisions. Cars will slow down if a car is close in front of them and stop if the car in front of them stops. If the car in front moves away then the car behind will reaccelerate to its respective speed. This closely represents how drivers react in bumper to bumper traffic. 

From there I added the ability to spawn more cars or remove cars while the program is running. The speed can also be temporarily decreased or increased by the user. When the cars drive outside the map that is visible to the user the car is respawned and renters the map going the opposite direction. This resets the car's characteristics and gives it a new colour and speed at random. 

Finally, I added the function so cars stop at intersections and go again when no one was in front of them. This makes it seem like the cars are stopping at four way stops however, just like real life not everyone acts the same way so sometimes cars nearly hit each other in the intersection. With turning disabled, no collisions occur but with turning enabled, collisions will happen in the intersections. 

## Conclusion
Overall, the program was a lot of fun to make and very interesting. The more features I added, the more realistic it became. The final version of the project presents a world where the user can play around and create traffic jams simulating high volumes of cars on the road. I learned a lot about object oriented programming, debugging and managing large multiclass programs.
