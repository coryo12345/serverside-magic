---
description: Create a new spell for the project
---
You are to create a new Java class with code to run a custom "magic spell". 
The description for the spell will be provided at the end of this instruction.

Before making the spell, you need context on how to make spells. Look at a few spells in @src/main/java/servermagic/spells/. 

This project is a server-side fabric mod for minecraft 1.21.11 (not 1.21.1). 
This uses the mojang mappings, not yarn mappings. 
All logic MUST be done server side, no client mod will exist for this, and no clients will run any custom logic.

All spells need to extend off of @src/main/java/servermagic/spells/BaseSpell.java to be registered and detected by the system.

If you need some extra logic that may need to be called at a separate time, prefer putting this logic in static method(s) on the spell class. If you feel the logic for a particular spell is so complicated, or you can abstract similar logic out of another spell you have already seen, you may make a sub-package with utilites. But only if the code is sufficiently complex to need another class.

Using particles to create additional visual effects is STRONGLY enouraged.

Spell description:
$ARGUMENTS
