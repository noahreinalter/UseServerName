# Use Server Name

Have you ever used a mod that saves data locally when playing on a server, 
but you use a server that regularly changes IP?
If yes this mod is for you,
as long as you use the same server name in the multiplayer join screen all your data will still be there.

### For server owners
Add the mod to the server so the users don't have to fiddle with configuration and can just connect.
You can change to server name in the config file under ``./config/useservername.json5``.

### For modpack makers
Add the mod for both sides,
and you don't need to adjust the config as the servername is randomly generated on the first lauche of the server.

### For modders
Don't add this mod as a dependency, use the objectShare and use the string you can get from
``(String) objectShare.get(useservername:serverId)``. The string can be ``null``.
It is first initialized when the ``ClientPlayConnectionEvents.INIT`` event is fired but the string can be overwritten
after the firing of the ``ServerPlayConnectionEvents.INIT`` event.

Currently, supported mods:

* [Bobby](https://modrinth.com/mod/bobby)
