# Tanks
Simple top-down 2D game with the goal of killing other tanks. Project uploaded to Github in case TalTech Gitlab goes down for use in the future.

### Makers
- Kaur Palang
- Magnus Muru

### Functionality
Main goal to learn networking. Game uses _dedicated server_ to calculate player location and statistics. A client is used to display said information to players.

- Tank turns with arrow keys
- Tank turret follows the cursor, fire with Left-Click
- When shot, life is lost (not working as of now)

### Technical info
Client in JavaFX. Dependency management in Gradle.

### Views
##### Title
- List of servers (currently only one server, so no list)
- Instructions
- Settings

### Things learned
- Sockets can be overloaded with info if they get something they are not supposed to pass along.
- JavaFX is not meant for making games (better off using libGDX)
- Networking needs to be built from the ground up to better handle all the functionality, not just as functionality gets added.
