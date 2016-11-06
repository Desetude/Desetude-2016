Machines
========


This plugin has a group of different machines that can be used to process different items.

----------


Machine recipes
-------------


#### Basic Macerator

![Recipe](https://i.imgur.com/s5rC6uw.png)

Cobblestone | Dispenser | Cobblestone<br>
Cobblestone | &nbsp;&nbsp;&nbsp;Piston&nbsp;&nbsp;&nbsp; | Cobblestone<br>
Cobblestone | Dispenser | Cobblestone

#### Advanced Macerator

![Recipe](https://i.imgur.com/UqY0b0k.png)

Iron Block | Dispenser | Iron Block<br>
Iron Block |&nbsp;&nbsp;&nbsp; Piston &nbsp;&nbsp;&nbsp;| Iron Block<br>
Iron Block | Dispenser | Iron Block

#### Basic Smelter

![Recipe](https://i.imgur.com/4nidcKe.png)

Cobblestone | Furnace | Cobblestone<br>
Cobblestone | &nbsp;&nbsp;Piston&nbsp;&nbsp; | Cobblestone<br>
Cobblestone | Furnace | Cobblestone

#### Advanced Smelter

![Recipe](https://i.imgur.com/hbWgTZ5.png)

Iron Block | Furnace | Iron Block<br>
Iron Block | &nbsp;&nbsp;Piston&nbsp;&nbsp; | Iron Block<br>
Iron Block | Furnace | Iron Block

#### Compressor

![Recipe](https://i.imgur.com/kZFAzRE.png)

Stone | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | Stone<br>
Stone | Iron Block | Stone<br>
Stone | &nbsp;&nbsp;&nbsp;Piston&nbsp;&nbsp;&nbsp; | Stone

----------


Processes
========

###Macerator

Input     | Output
--------- | ------
Iron ore | 2 Iron dust
Gold ore | 2 Gold dust
Emerald ore | 2 Emerald dust
Diamond ore | 2 Diamond dust
Lapis ore | 8 Lapis Lazuli

###Smelter

Input     | Output
--------- | ------
Iron dust | Iron ingot
Gold dust | Gold ingot
Emerald dust | Emerald
Diamond dust | Diamond

Plus every vanilla smelting recipe.

###Compressor

Input     | Output
--------- | ------
4 Clay ball | Clay block
4 Clay brick | Brick block
Clay block | Hardened clay
9 Melon | Melon block
4 Glowstone dust | Glowstone
4 Snow balls | Snow block
4 Netherrack | Nether brick
Snow block | Ice
Ice | Packed Ice
Sand | Sandstone
9 Coal | Coal block
9 Iron ingots | Iron block
9 Diamonds | Diamond block
9 Redstone | Redstone block
9 Lapis Lazuli | Lapis lazuli block
9 Emeralds | Emerald block

How to use
-------------

 1. Craft the machine.
 ![Crafting](https://i.imgur.com/G5sYn4P.png)
 
 2. Place the machine.
![Placing](https://i.imgur.com/Wuz0H3w.png)

 3. Right click the machine.
![Opening](https://i.imgur.com/a1YzVl5.png)

 4. Put your input into the top left slot.
![Input](https://i.imgur.com/aQfD1sj.png)

 5. Put your fuel in the bottom slot (works with any vanilla fuel, even lava).
![Fuel](https://i.imgur.com/GdvfhQT.png)

 6. Progress will show on the top middle bar, if it is not progressing, simply hover over the bar to see why it is not doing so.
![Progress](https://i.imgur.com/fUsmRt7.png)

 7. If there are no issues, you should start recieving output in the top right slot.
 ![Output](https://i.imgur.com/qnKl096.png)

API
===
####Creating a machine

To create a machine you must implement Machine, or you can just simply extend SimpleMachine for a simple implementation. e.g:

   ```java
   public class CompressorMachine extends SimpleMachine {
	    public CompressorMachine(Location location) {
	        super("Compressor", ChatColor.GREEN + "Compressor", 1, location); //Title, formatted name, fuelMultiplier, location
	    }
    }
    ```

 To add a recipes/processes to your machine you must first create a Process using any of the `Process.create` methoods and then in your machine add the recipes with SimpleMachine#addProcesses(Process...).

####Registering a machine
 
 To register a machine simply do `MachineFactory.registerMachine(key, machineSupplier)`

####Giving machines

A machine can be represented by any item, the only way a machine is indentified is by the NBT String tag with the value of the key of the machine. This can be set with the ItemStackBuilder `stringTag(key, value)` method or with the `NBTUtils.getTag(nmsItem).setString(key, value)` function.