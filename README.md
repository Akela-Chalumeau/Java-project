# Java-project

This project consists in a space game. The galaxy contains :
- 1 origin planet by player
- neutral planets
- the rest of the space

The planets are from a certain distance from each other and produce a certain kind of spaceships, except the neutral. They are bigger than the spaceships. At the beggining of the game, we check if the planets we create intersect each other.

Rules : At the start of the game, each player (which can be an IA) possess one planet. They can launch a squadron of spaceships to an other planet in order to :
- attack if the planet is controlled by an enemy
- invade a neutral planet
- reinforce the army of the planet if the player controls both 

Each spaceship has a firepower of 1 and a defense value of 1, so one spaceship can destroy one and only one. When you attack a planet, you have to send as many spaceships as the target possess. When they have 0 left, you captured the planet. 

The game ends when there is only one player left, and you loose when you do not control any planet.


Instructions : You can send your spaceships using the mouse by clicking on your planet, dragging all the way to the destination planet, and releasing. That way, you will send a whole squadron. If you click on it, you can redirect the spaceships to change your target or decide to help one of your planets. Spaceships from different sides never crash between each other.

You can also change the amount of spaceships you want to sen with the keyboard. By pressing the upwards arrow ↑(or Z), you can increase the percentage of spaceships you send by 5% ; and by pressing the downwards arrow ↓ (or S), you can decrease it by 5%. The percentage by defalut is 50%.


Possible improvements : 
- a save and load option (tried with "escape" and "enter")
- a path finding
- make the spaceships gravitate around their planets to send them one by one, without making them cover
- pirate spaceships
- planets with a changing production rate
- improve the management of the squadrons, wich are controlled by a collection of table
- power for spaceships and planets
- better IA
