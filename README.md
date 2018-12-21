# Java-project

This project consists in a space game. The galaxy contains :
- 1 origin planet by player
- neutral planets
- the rest of the space

The planets are from a certain distance from each other and produce a certain kind of spaceships, except the neutral. They are bigger than the spaceships.

Rules : At the start of the game, each player (which can be an IA) possess one planet. They can launch a squadron of spaceships to an other planet in order to :
- attack if the planet is controlled by an enemy
- invade a neutral planet
- reinforce the army of the planet if the player controls both 

Each spaceship has a firepower of 1 and a defense value of 1, so one spaceship can destroy one and only one. When you attack a planet, you have to send as many spaceships as the target possess. When they have 0 left, you can invade the planet. 

The game ends when there is only one player left, and you loose when you do not control any planet.


Instructions : You can send your spaceships using the mouse by clicking on your planet, dragging all the way to the destination planet, and releasing. That way, you will send a whole scadron. If you click on it, you can redirect the spaceships to change your target or decide to help one of your planets. Spaceships from different sides never crash between each other.

You can also change the amount of spaceships you want to sen with the keyboard. By pressing the upwards arrow ↑, you can increase the percentage of spaceships you send by 5% ; and by pressing the downwards arrow ↓, you can decrease it by 5%. The percentage by defalut is 50%.
