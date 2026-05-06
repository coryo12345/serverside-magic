# Server magic! ✨

## Building mod jar
1. In the `ui/` directory, `npm install && npm run build`
2. Run `./merge-resourcepack.sh` to build the server resource pack
3. Run `./gradlew build` to pack all resources into a jar located at `build/libs/*.jar`

## Server setup
1. The jar built in the previous section can be put in the `mods/` directory of a server running fabric. 
    * When the server starts, you should see the address the web portal is running on. It will run on port `8080`.
    * Note this mod depends on fabric api (https://modrinth.com/mod/fabric-api)
2. The `server.properties` file needs to be updated, to set a server resourcepack. Set it to: `http://<server-address>:8080/assets/servermagic-resourcepack.zip`
    * OR you can set to the github release (if pack included)
    * set `require-resource-pack` to true
    * No hash checking is calculated at this time, you may do that yourself if you like
3. In the server console (or as an operator player) run:
`/setmagicurl http://<server-address:8080>`

Note: if you are remapping ports in any way you will need to adjust accordingly. `<server-address>` is always the public domain/ip address the server is accessible to users on.

